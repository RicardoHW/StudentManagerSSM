package com.hang.programmer.dao;

import com.hang.programmer.pojo.Grade;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/16 16:05
 * @Description:
 */
@Repository
public interface GradeDao {
    public int add(Grade grade);
    public int edit(Grade grade);
    public int delete(String ids);
    public List<Grade> findList(Map<String,Object> queyMap);
    public List<Grade> findAll();
    public int getToTal(Map<String,Object> queyMap);
}
