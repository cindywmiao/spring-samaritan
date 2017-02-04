package com.coupang.samaritan.domain.model;

import lombok.Data;

/**
 * Created by frank.fu on 12/14/16.
 */
@Data
public class ContentSummaryAttribute {
        public int directoryCount;
        public int fileCount;
        public double length;
        public int quota;
        public double spaceConsumed;
        public double spaceQuota;
}
