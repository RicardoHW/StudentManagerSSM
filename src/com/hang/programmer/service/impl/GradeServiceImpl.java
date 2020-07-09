package com.hang.programmer.service.impl;

import com.hang.programmer.dao.GradeDao;
import com.hang.programmer.pojo.Grade;
import com.hang.programmer.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/16 16:04
 * @Description:
 */

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeDao gradeDao;
    @Override
    public int add(Grade grade) {
        return gradeDao.add(grade);
    }

    @Override
    public int edit(Grade grade) {
        return gradeDao.edit(grade);
    }

    @Override
    public int delete(String ids) {
        return gradeDao.delete(ids);
    }

    @Override
    public List<Grade> findList(Map<String, Object> queyMap) {
        return gradeDao.findList(queyMap);
    }

    @Override
    public List<Grade> findAll() {
        return gradeDao.findAll();
    }

    @Override
    public int getToTal(Map<String, Object> queyMap) {
        return gradeDao.getToTal(queyMap);
    }
}
