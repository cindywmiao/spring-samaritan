package com.coupang.samaritan.domain.service;


import com.google.gson.Gson;
import com.coupang.samaritan.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by frank.fu on 12/14/16.
 */
@Service
public class ProcessHdfs {

    public static String basicUrl = "http://10.10.129.142:14000/webhdfs/v1/user/mercury";

    @Autowired
    HdfsConfig hdfsConfig;
    public String getFileList(String filename) {

        String url = basicUrl + "/" + filename;
        FileStatuses fileStatuses = new Gson().fromJson(hdfsConfig.getFileList("mercury", url), FileStatuses.class);
        ArrayList<File> files = fileStatuses.FileStatuses.FileStatus;
        PieChart pieChart = new PieChart();
        DataPoint dataPoint=null;
        if (files != null) {
            for (File file : files) {
                dataPoint=new DataPoint();
                if (file.getType().equals("DIRECTORY")) {
                    new HdfsConfig().getContentSummary("mercury", url + "/" + file.getPathSuffix().trim());
                    double length = new Gson().fromJson(hdfsConfig.getContentSummary("mercury", url + "/" + file.getPathSuffix().trim()), ContentSummary.class).getContentSummary().getLength();
                    dataPoint.setY(length);
                    dataPoint.setIndexLabel(file.getPathSuffix().trim());
                    dataPoint.setType("Directory");
                } else {
                    dataPoint.setY(file.getLength() / 131072);
                    dataPoint.setIndexLabel(file.getPathSuffix().trim());
                    dataPoint.setType("File");
                }
                pieChart.getDataPoints().add(dataPoint);
            }
        }
        return new Gson().toJson(pieChart);
    }
}
