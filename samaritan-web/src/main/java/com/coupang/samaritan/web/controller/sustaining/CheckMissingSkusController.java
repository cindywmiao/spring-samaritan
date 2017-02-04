package com.coupang.samaritan.web.controller.sustaining;

import com.coupang.samaritan.domain.sustaining.SkuCheckRespData;
import com.coupang.samaritan.domain.sustaining.SkusCheckDistribute;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by changhua.wu on 12/15/16.
 */


@RestController
@RequestMapping("/sustaining/checkMissingSkus")
@Slf4j
public class CheckMissingSkusController {


	@RequestMapping(path = "/getResultHeader", method = RequestMethod.GET)
	@ResponseBody
	public String  getResultHeader() {


		List<String> headerColumnList = new ArrayList<String>() {{
			add("sku");
			add("inRoqResult");
			add("isApendSku");
			add("inTip");
			add("inForecastNational");
			add("inVendorData");
			add("inSkuCategory"); // TODO:
			add("isLive");
			add("isNormal");
		}};


		String jsonResult = DataConvertUtil.convertObjectToJson( headerColumnList );
		return jsonResult;
	}




	@RequestMapping(path = "/getResultContent/{workflowId}", method = RequestMethod.GET)
	@ResponseBody
	public String  getResultContent(@PathVariable("workflowId") String workflowId) {


		try {

			File skuCheckResultFile = ResourceUtils.getFile("classpath:public/sustaining/missing_roq_skus_reason.csv");

			List<String> csvLines = FileUtils.readLines(skuCheckResultFile, StandardCharsets.UTF_8);
			List<String[]> elements = Lists.newArrayList();

			AtomicInteger countMissingInRoqResult = new AtomicInteger(0);
			AtomicInteger countIsAppendSku = new AtomicInteger(0);
			AtomicInteger countMissingInTip = new AtomicInteger(0);
			AtomicInteger countMissingInForecast = new AtomicInteger(0);
			AtomicInteger countMissingInVendorData = new AtomicInteger(0);

			boolean firstLine = true;
			for(String line : csvLines) {
				if(!firstLine) {
					String[] fields = line.split(",");
					elements.add( fields );

					if("N".equals(fields[1])) {
						countMissingInRoqResult.getAndIncrement();
					}

					if("Y".equals(fields[2])) {
						countIsAppendSku.getAndIncrement();
					}

					if("N".equals(fields[3])) {
						countMissingInTip.getAndIncrement();
					}

					if("N".equals(fields[4])) {
						countMissingInForecast.getAndIncrement();
					}

					if("N".equals(fields[5])) {
						countMissingInVendorData.getAndIncrement();
					}
				}
				firstLine = false;
			}

			SkusCheckDistribute distribute = new SkusCheckDistribute();
			distribute.setMissingInForecast( countMissingInForecast.get() );
			distribute.setMissingInRoqResult( countMissingInRoqResult.get());
			distribute.setMissingInTip( countMissingInTip.get());
			distribute.setMissingInVendorData( countMissingInVendorData.get() );
			distribute.setCountIsAppendSku( countIsAppendSku.get() );


			SkuCheckRespData  respData = new SkuCheckRespData();
			respData.setDetailDatas( elements );
			respData.setDistribute( distribute );

			String jsonResult = DataConvertUtil.convertObjectToJson( respData );
			return jsonResult;


		} catch (FileNotFoundException e) {
			log.error("read result csv failed, error:", e);
		} catch (IOException e) {
			log.error("read result csv failed, error:", e);
		}

		return "";

	}



}
