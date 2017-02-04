package com.coupang.samaritan.domain.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Created by wuzonghan on 16/12/14.
 */
public enum MatchEnum {

	// match lastest date
	LATEST_DAY("@latest_yyyyMMdd@", (allFiles, value) -> {
		return Lists.newArrayList(allFiles.keySet().parallelStream().filter((fileName) -> {
			try {
				return new SimpleDateFormat("yyyyMMdd").parse(fileName).getTime() > 0L;
			} catch (ParseException e) {
				return false;
			}
		}).sorted((a, b) -> {
			return b.compareTo(a);
		}).findFirst().get());
	}),
	// match today date
	TODAY_DAY("@today_yyyyMMdd@", (allFiles, value) -> {
		return Lists.newArrayList(allFiles.keySet().parallelStream().filter((fileName) -> {
			try {

				return new SimpleDateFormat("yyyyMMdd").parse(fileName).getTime() ==
						Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
			} catch (ParseException e) {
				return false;
			}
		}).collect(Collectors.toList()));
	}),
	// match last modified file name
	LATEST_MODIFIED_FILE("@latest_modified_file@", (allFiles, value) -> {
		return Lists.newArrayList(allFiles.keySet().parallelStream().sorted((a, b) -> {
			return allFiles.get(a).getModificationTime().compareTo(
				allFiles.get(b).getModificationTime());
		}).findFirst().get());
	}),
	// match all names
	ALL("@a@", (allFiles, value) -> {
		final Set<String> options = Sets.newHashSet(value.split("@a@"));
		return allFiles.keySet().parallelStream().filter((fileName) -> {
			Set<String> optionsMutableSet = new HashSet<String>(options);
			return !optionsMutableSet.stream().filter((option) -> {
				return fileName.contains(option);
			}).collect(Collectors.toSet()).isEmpty();
		}).collect(Collectors.toList());
	}),
	// match all date names
	ALL_DAY("@a_yyyyMMdd@", (allFiles, value) -> {
		final Set<String> options = Sets.newHashSet(value.split("@a_yyyyMMdd@"));
		return allFiles.keySet().parallelStream().filter((fileName) -> {
			String toVerify = fileName;
			for (String option : options) {
				if (!toVerify.contains(option))
					return false;
				toVerify = toVerify.replace(option, "");
			}
			try {
				return new SimpleDateFormat("yyyyMMdd").parse(toVerify).getTime() > 0L;
			} catch (ParseException e) {
				return false;
			}
		}).collect(Collectors.toList());
	}),
	// select name from array list
	ARRAY_SELECT("@[,]@", (allFiles, value) -> {
		final List<String> splitStrings = Lists.newArrayList(value.split(","));
		final String leftEnd = splitStrings.get(0).substring(0, splitStrings.get(0).indexOf("@"));
		final String rightEnd = splitStrings.get(splitStrings.size() - 1).substring(
			splitStrings.get(splitStrings.size() - 1).indexOf("@") + 1);
		final String first = splitStrings.get(0).substring(splitStrings.get(0).indexOf("[") + 1);
		final String end = splitStrings.get(splitStrings.size() - 1).substring(
			0, splitStrings.get(splitStrings.size() - 1).indexOf("]"));
		splitStrings.remove(0);
		splitStrings.add(0, first);
		splitStrings.remove(splitStrings.size() - 1);
		splitStrings.add(end);
		final Set<String> validTargetFileNames = new HashSet<String>();
		splitStrings.forEach((s) -> {
			validTargetFileNames.add(leftEnd + s + rightEnd);
		});
		return allFiles.keySet().parallelStream().filter((fileName) -> {
			return validTargetFileNames.contains(fileName);
		}).collect(Collectors.toList());
	}),
	// select name from a number range
	ARRAY_RANGE("@[~]@", (allFiles, value) -> {
		final List<String> splitStrings = Lists.newArrayList(value.split("~"));
		final String leftEnd = splitStrings.get(0).substring(0, splitStrings.get(0).indexOf("@"));
		final String rightEnd = splitStrings.get(splitStrings.size() - 1).substring(
			splitStrings.get(splitStrings.size() - 1).indexOf("@") + 1);
		final String first = splitStrings.get(0).substring(splitStrings.get(0).indexOf("[") + 1);
		final String end = splitStrings.get(splitStrings.size() - 1).substring(
			0, splitStrings.get(splitStrings.size() - 1).indexOf("]"));
		final int numberLength = first.length();
		splitStrings.remove(0);
		splitStrings.add(0, first);
		splitStrings.remove(splitStrings.size() - 1);
		splitStrings.add(end);
		final Set<String> validTargetFileNames = new HashSet<String>();
		final int startCursor = Integer.valueOf(splitStrings.get(0));
		final int endCursor = Integer.valueOf(splitStrings.get(1));
		int cursor = startCursor;
		while (cursor <= endCursor) {
			String numberInString = String.valueOf(cursor++);
			for (int i = 0; i < numberLength - numberInString.length(); i++) {
				numberInString = "0" + numberInString;
			}
			validTargetFileNames.add(leftEnd + numberInString + rightEnd);
		}
		return allFiles.keySet().parallelStream().filter((fileName) -> {
			return validTargetFileNames.contains(fileName);
		}).collect(Collectors.toList());
	}),
	DEFAULT("", (allFiles, value) -> {
		return allFiles.keySet().stream().filter((fileName) -> {
			return fileName.equals(value);
		}).collect(Collectors.toList());
	});

	private String key;

	private BiFunction<Map<String, HDFSFile>, String, List<String>> function;

	MatchEnum(String key, BiFunction<Map<String, HDFSFile>, String, List<String>> function) {
		this.key = key;
		this.function = function;
	}

	public List<String> findTargets(Map<String, HDFSFile> allFiles, String value) {
		return this.function.apply(allFiles, value);
	}

	public String getKey() {
		return this.key;
	}

	public static MatchEnum findEnum(String value) {
		if (value.contains(MatchEnum.TODAY_DAY.getKey())) {
			return MatchEnum.TODAY_DAY;
		}
		if (value.equals(MatchEnum.LATEST_DAY.getKey())) {
			return MatchEnum.LATEST_DAY;
		}
		if (value.equals(MatchEnum.LATEST_MODIFIED_FILE.getKey())) {
			return MatchEnum.LATEST_MODIFIED_FILE;
		}
		if (value.contains(MatchEnum.ALL.getKey())) {
			return MatchEnum.ALL;
		}
		if (value.contains(MatchEnum.ALL_DAY.getKey())) {
			return MatchEnum.ALL_DAY;
		}
		if (value.indexOf("@[") == value.lastIndexOf("@[")
			&& value.indexOf("]@") == value.lastIndexOf("]@")
			&& value.indexOf("@[") < value.indexOf(",")
			&& value.lastIndexOf(",") < value.indexOf("]@")) {
			return MatchEnum.ARRAY_SELECT;
		}
		if (value.indexOf("@[") == value.lastIndexOf("@[")
			&& value.indexOf("]@") == value.lastIndexOf("]@")
			&& value.indexOf("~") == value.lastIndexOf("~")
			&& value.indexOf("@[") < value.lastIndexOf("~")
			&& value.lastIndexOf("~") < value.lastIndexOf("]@")) {
			if (isNumeric(value.substring(value.indexOf("@[") + 2, value.indexOf("~")))
				&& isNumeric(value.substring(value.indexOf("~") + 1, value.indexOf("]@")))) {
				return MatchEnum.ARRAY_RANGE;
			}
			;
		}
		return MatchEnum.DEFAULT;
	}
}
