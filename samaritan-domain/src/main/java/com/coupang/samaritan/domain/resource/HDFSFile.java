package com.coupang.samaritan.domain.resource;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HDFSFile {

    private String filePathUri;

    private Boolean isDir;

    private Boolean isExist;

    private Long fileSize;

    private List<SubFile> containFiles;

    private Date modificationTime;

}
