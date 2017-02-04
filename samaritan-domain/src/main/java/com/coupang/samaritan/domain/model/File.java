package com.coupang.samaritan.domain.model;

import lombok.Data;

/**
 * Created by frank.fu on 12/14/16.
 */
@Data
public class File implements Comparable<File>{
    public String pathSuffix;
    public String type;
    public long length;
    public String owner;
    public String group;
    public int permission;
    public String accessTime;
    public long modificationTime;
    public long blockSize;
    public int replication;
    public int compareTo(File st){
        if(length==st.length)
            return 0;
        else if(length>st.length)
            return -1;
        else
            return 1;
    }
}