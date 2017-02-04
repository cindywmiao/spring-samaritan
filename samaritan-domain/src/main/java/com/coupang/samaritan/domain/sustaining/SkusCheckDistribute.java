package com.coupang.samaritan.domain.sustaining;

import lombok.Data;

/**
 * Created by changhua.wu on 12/15/16.
 */
@Data
public class SkusCheckDistribute {

	int missingInRoqResult = 0;
	int countIsAppendSku = 0;
	int missingInTip = 0;
	int missingInForecast = 0;
	int missingInVendorData = 0;
	int totalMissingSkus = 0;


}
