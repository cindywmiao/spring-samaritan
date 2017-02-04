package com.coupang.samaritan.domain.service;

import com.coupang.samaritan.domain.resource.HDFSFile;
import com.coupang.samaritan.domain.resource.MatchEnum;
import com.coupang.samaritan.domain.resource.SubFile;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wuzonghan on 16/12/14.
 */
@Service
@Slf4j
public class HDFSStatusService {

	@Autowired
	RestTemplate restTemplate;

	private static final String FILE_NOT_FOUND_EXCEPTION = "FileNotFoundException";

	private static final String BASE_URL = "http://10.10.129.142:14000/webhdfs/v1";

	private static final String OP_LIST_STATUS = "?op=LISTSTATUS";

	private static final String OP_GET_FILE_STATUS = "?op=GETFILESTATUS";

	private static final String USER_NAME = "&user.name=mercury";

	public HDFSFile statusFileExactPath(String filePath) {
		try {
			final JSONObject responseJson = new JSONObject(this.restTemplate.getForObject(
				BASE_URL + filePath + OP_GET_FILE_STATUS + USER_NAME, String.class));
			final JSONObject dataJson = responseJson.getJSONObject("FileStatus");
			HDFSFile result = new HDFSFile(BASE_URL + filePath,
				"DIRECTORY".equals(dataJson.get("type").toString()),
				true,
				Long.valueOf(dataJson.get("length").toString()),
				Lists.newArrayList(),
				new Date(Long.valueOf(dataJson.get("modificationTime").toString())));
			if (result.getIsDir()) {
				result.getContainFiles().addAll(this.listSubFiles(filePath));
			}
			return result;
		} catch (HttpClientErrorException e) {
			final JSONObject responseJson = new JSONObject(e.getResponseBodyAsString());
			if (FILE_NOT_FOUND_EXCEPTION.equals(
				responseJson.getJSONObject("RemoteException").get("exception").toString())) {
				return new HDFSFile(BASE_URL + filePath, false, false, 0L, null, null);
			}
		}
		return null;
	}

	public List<SubFile> listSubFiles(String filePath) {
		List<SubFile> result = Lists.newArrayList();
		final JSONObject responseJson = new JSONObject(this.restTemplate.getForObject(
			BASE_URL + filePath + OP_LIST_STATUS + USER_NAME, String.class));
		final JSONArray dataJsonArray = responseJson.getJSONObject(
			"FileStatuses").getJSONArray("FileStatus");
		dataJsonArray.forEach((Object jsonObject) -> {
            SubFile subFile = new SubFile();
            subFile.setPathSuffix(((JSONObject)jsonObject).get("pathSuffix").toString());
            subFile.setModifiedTime(((JSONObject)jsonObject).getLong("modificationTime"));
			result.add(subFile);
		});
		return result;

	}

	// match the files in real-time
	public Map<String, HDFSFile> regexMatchListStatus(String filePath) {
		final List<String> splitPaths = Lists.newArrayList(filePath.split("/"));
		final Map<String, HDFSFile> result = Maps.newHashMap();
		this.visitNextDepthPath("/", 0, splitPaths.stream().filter((String path) -> path.length() > 0 ).collect(Collectors.toList()), result);
		return result;
	}

	// assume oneBasePath exist already
	private void visitNextDepthPath(String oneBasePath, int depth, List<String> splitPaths, Map<String, HDFSFile> result) {
		final Set<String> gotPaths = Sets.newHashSet();
		final Map<String, HDFSFile> allFilesThisDepth = Maps.newHashMap();
		final Map<String, String> path2File = Maps.newHashMap();
		this.listSubFiles(oneBasePath).parallelStream().forEach((SubFile subFile) -> {
			try {
				allFilesThisDepth.put(subFile.getPathSuffix(), this.statusFileExactPath(oneBasePath + "/" + subFile));
			} catch (Exception e) {
				// for some permission deny case, ignore this file option
			}
		});
		// to match
		MatchEnum.findEnum(splitPaths.get(depth)).findTargets(allFilesThisDepth, splitPaths.get(depth)).forEach((String file) -> {
			gotPaths.add(oneBasePath.equals("/") ? oneBasePath + file : oneBasePath + "/" + file);
			path2File.put(oneBasePath.equals("/") ? oneBasePath + file : oneBasePath + "/" + file, file);
		});
		// end of recurring or visit next depth again
		if (depth == splitPaths.size() - 1 ) {
			gotPaths.forEach((String path) -> {
				result.put(path, allFilesThisDepth.get(path2File.get(path)));
			});
			return ;
		} else {
			gotPaths.forEach((String path) -> {
				this.visitNextDepthPath(path, depth + 1, splitPaths, result);
			});
		}
	}

	public Map<String, HDFSFile> statusFilesRegexPath(String regex) {
		Map<String, HDFSFile> result = Maps.newHashMap();
		return result;
	}

	public String test() {
		final String uri = "http://10.10.129.142:14000/webhdfs/v1" +
			"/user/mercury/Fu/part-m-00000" + "?op=LISTSTATUS" + "&user.name=mercury";

		try {
			String result = restTemplate.getForObject(uri, String.class);
			return result;
 		} catch (HttpClientErrorException e) {
			return e.getResponseBodyAsString();
		}
	}

	// for demo only
	public boolean checkPathMatch(String pathMatch, String conditionSymbol, Integer conditionValue) {
		if (!pathMatch.contains("@")) { // single file
			return statusFileExactPath(pathMatch).getIsExist();
		}  else {    // with parameters
            int indexOfLatestDir = pathMatch.indexOf("@latest_dir@");
            if(-1 != indexOfLatestDir) {    // todo: current only support one @latest_dir@  and parentDir should not contains parameter
                String parentDir = pathMatch.substring(0, indexOfLatestDir);
                HDFSFile file = statusFileExactPath(parentDir);
                SubFile latestSubFile = file.getContainFiles().stream().reduce((subFile1, subFile2) ->
                    subFile1.getModifiedTime() > subFile2.getModifiedTime() ? subFile1 : subFile2
                ).get();
                pathMatch = pathMatch.replace("@latest_dir@", latestSubFile.getPathSuffix());
                log.info("pathMatch was modified as: {}", pathMatch);
            }
            List<String> splitPaths = Lists.newArrayList(pathMatch.split("/"));
			List<List<String>> paths =  splitPaths.stream().map(path -> {
				List<String> list = Lists.newArrayList();
				if (path.equals("@today_yyyyMMdd@")) {
					list.add(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
				} else if (path.contains("@[")) {
					int index1 = path.indexOf("@[");
					int index2 = path.indexOf("]@");
					String prefix = path.substring(0, index1);
					String suffix = path.substring(index2 + 2, path.length());
					String replace = path.substring(index1 + 2, index2);
					if (path.contains("~")) {  // @[00~99]@
						String[] replaceArray =  replace.split("~");
						String start = replaceArray[0];
						String end = replaceArray[1];
						int endLength = end.length();
						int startInt = Integer.parseInt(start);
						int endInt = Integer.parseInt(end);
						for(int i = startInt; i <= endInt; i++) {
							list.add(prefix + String.format("%0" + endLength + "d", i) + suffix);
						}
					} else {    // @[aaa,bbb,ccc]@
						String[] replaceArray =  replace.split(",");
						for(String str : replaceArray){
							list.add(prefix + str + suffix);
						}
					}
				} else {
					list.add(path);
				}
				return list;
			}).collect(Collectors.toList());

			List<String> absolutePaths = Lists.newArrayList();
			constructAbsolutePaths("", 1, paths, absolutePaths);   // depth 0 is always ""
			for(String absolutePath : absolutePaths) {   // use parallel if there is performance bottleneck
				HDFSFile file = statusFileExactPath(absolutePath);
				if (!file.getIsExist()) {
                    log.info("file: {} is not exist", file.getFilePathUri());
					return false;
				} else {
                    double fileSizeInMb = file.getFileSize()  / 1048576;
					switch (conditionSymbol) {
						case ">":
							if (conditionValue > fileSizeInMb) {
								log.info("failed on condition checking, path: {}, size: {}, condition symbol: {}, value: {}",
										file.getFilePathUri(), file.getFileSize(), conditionSymbol, conditionValue);
								return false;
							}
                            break;
						case "<":
							if (conditionValue < fileSizeInMb) {
								log.info("failed on condition checking, path: {}, size: {}, condition symbol: {}, value: {}",
										file.getFilePathUri(), file.getFileSize(), conditionSymbol, conditionValue);
								return false;
							}
                            break;
						default:
							throw new RuntimeException("unsupported symbol");
					}
				}
			}
		}

		return true;
	}


	private void constructAbsolutePaths(String prefix, int depth, List<List<String>> paths, List<String> absolutePaths) {
		if (depth == paths.size() - 1) {
			for (String path : paths.get(depth)) {
				absolutePaths.add(prefix + "/" +path);
			}
		} else {
			for (String path : paths.get(depth)) {
				constructAbsolutePaths(prefix + "/" + path, depth + 1, paths, absolutePaths);
			}
		}
	}
}
