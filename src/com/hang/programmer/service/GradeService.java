package com.hang.programmer.service;

import com.hang.programmer.pojo.Grade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/16 16:04
 * @Description:
 */
@Service
public interface GradeService {
    public int add(Grade grade);
    public int edit(Grade grade);
    public int delete(String ids);
    public List<Grade> findList(Map<String,Object> queyMap);
    public List<Grade> findAll();
    public int getToTal(Map<String,Object> queyMap);
}
