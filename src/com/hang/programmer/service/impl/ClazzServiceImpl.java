package com.hang.programmer.service.impl;

import com.hang.programmer.dao.ClazzDao;
import com.hang.programmer.pojo.Clazz;
import com.hang.programmer.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 10:46
 * @Description:
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;
    @Override
    public int add(Clazz clazz) {
        return clazzDao.add(clazz);
    }

    @Override
    public int edit(Clazz clazz) {
        return clazzDao.edit(clazz);
    }

    @Override
    public int delete(String ids) {
        return clazzDao.delete(ids);
    }

    @Override
    public List<Clazz> findList(Map<String, Object> queyMap) {
        return clazzDao.findList(queyMap);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzDao.findAll();
    }

    @Override
    public int getToTal(Map<String, Object> queyMap) {
        return clazzDao.getToTal(queyMap);
    }
}
