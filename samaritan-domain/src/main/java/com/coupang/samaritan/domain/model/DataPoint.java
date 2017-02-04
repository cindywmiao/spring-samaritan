package com.coupang.samaritan.domain.model;

import lombok.Data;

/**
 * Created by frank.fu on 12/15/16.
 */
@Data
public class DataPoint implements Comparable<DataPoint>{
    public double y;
    public String type;
    public String indexLabel;
    public int compareTo(DataPoint st){
        if(y==st.y)
            return 0;
        else if(y>st.y)
            return 1;
        else
            return -1;
    }
}
