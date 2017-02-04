package com.coupang.samaritan.domain.sustaining;

import lombok.Data;

import java.util.List;

/**
 * Created by changhua.wu on 12/15/16.
 */
@Data
public class SkuCheckRespData {

	SkusCheckDistribute distribute;

	List<String[]>      detailDatas;

}
