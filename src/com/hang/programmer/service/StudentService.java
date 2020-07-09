package com.hang.programmer.service;

import com.hang.programmer.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 17:08
 * @Description:
 */
@Service
public interface StudentService {
    public Student findByName(String name);
    public int add(Student student);
    public int edit(Student student );
    public int delete(String ids);
   // public List<Student> findList(Student student);
   public List<Student> findList(Map<String,Object> queyMap);
    public List<Student> findAll();
    public int getToTal(Map<String,Object> queyMap);
}
