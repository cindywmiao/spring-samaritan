package com.coupang.samaritan.web.controller.api;

import com.coupang.samaritan.domain.service.ProcessHdfs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class PieChartController {

    @Autowired
    ProcessHdfs processHdfs;

    @RequestMapping(value = {"/getAllData","/getAllData/**"}, method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE  )
    public String diplay(HttpServletRequest httpServletRequest){
        return processHdfs.getFileList(httpServletRequest.getServletPath().replace("/api/getAllData",""));
    }


}
