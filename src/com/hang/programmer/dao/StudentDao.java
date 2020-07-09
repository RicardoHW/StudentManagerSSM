package com.hang.programmer.dao;

import com.hang.programmer.pojo.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 17:07
 * @Description:
 */
@Repository
public interface StudentDao {
    public Student findByName(String name);
    public int add(Student student);
    public int edit(Student student );
    public int delete(String ids);
    public List<Student> findList(Map<String,Object> queyMap);
    public List<Student> findAll();
    public int getToTal(Map<String,Object> queyMap);
}
