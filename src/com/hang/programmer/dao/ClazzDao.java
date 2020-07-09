package com.hang.programmer.dao;

import com.hang.programmer.pojo.Clazz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 10:50
 * @Description:
 */
@Repository
public interface ClazzDao {
    public int add(Clazz clazz);
    public int edit(Clazz clazz);
    public int delete(String ids);
    public List<Clazz> findList(Map<String,Object> queyMap);
    public List<Clazz> findAll();
    public int getToTal(Map<String,Object> queyMap);
}
