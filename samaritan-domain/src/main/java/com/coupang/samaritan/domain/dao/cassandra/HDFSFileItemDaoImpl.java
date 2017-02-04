package com.coupang.samaritan.domain.dao.cassandra;

import com.coupang.samaritan.domain.dao.HDFSFileItemDao;
import com.coupang.samaritan.domain.monitor.impl.HDFSFileItem;
import org.springframework.stereotype.Repository;

import java.util.Collection;

// save the monitored item definition into db.
// why Cassandra? just for practice

@Repository
public class HDFSFileItemDaoImpl implements HDFSFileItemDao{

    @Override
    public void save(HDFSFileItem hdfsFileItem) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void update(HDFSFileItem hdfsFileItem) {

    }

    @Override
    public HDFSFileItem find(String id) {
        return null;
    }

    @Override
    public Collection<HDFSFileItem> find(int page, int limit) {
        return null;
    }

}
