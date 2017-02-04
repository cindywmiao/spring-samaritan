package com.coupang.samaritan.domain.dao;


import com.coupang.samaritan.domain.monitor.impl.HDFSFileItem;

import java.util.Collection;

public interface HDFSFileItemDao {

    void save(HDFSFileItem hdfsFileItem);

    void delete(String id);

    void update(HDFSFileItem hdfsFileItem);

    HDFSFileItem find(String id);

    Collection<HDFSFileItem> find(int page, int limit);

}
