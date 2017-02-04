package com.coupang.samaritan.domain.sustaining;

import lombok.Data;

/**
 * Created by changhua.wu on 12/15/16.
 */
@Data
public class SkusCheckStatus {

	String sku;  //0
	String inRoqResult; //1
	String isApendSku;//2
	String inTip;   //3
	String inForecast;  //4
	String inVendorData;   //5


	//TODO:
	String inSkuCategory;


	public static SkusCheckStatus createMapObject(String line) {

		String[] p = line.split(",");
		SkusCheckStatus data = new SkusCheckStatus();

		if(line.length()>5) {
			data.setSku(p[0]);
			data.setInRoqResult(p[1]);
			data.setIsApendSku(p[2]);
			data.setInTip(p[3]);
			data.setInForecast(p[4]);
			data.setInVendorData(p[5]);
		}
		return data;
	}

}
