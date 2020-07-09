package com.hang.programmer.service;

import com.hang.programmer.pojo.Clazz;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 10:45
 * @Description:
 */
@Service
public interface ClazzService {
    public int add(Clazz clazz);
    public int edit(Clazz clazz );
    public int delete(String ids);
    public List<Clazz> findList(Map<String,Object> queyMap);
    public List<Clazz> findAll();
    public int getToTal(Map<String,Object> queyMap);
}
