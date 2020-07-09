package com.hang.programmer.service.impl;

import com.hang.programmer.dao.StudentDao;
import com.hang.programmer.pojo.Student;
import com.hang.programmer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 17:09
 * @Description:
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public Student findByName(String name) {
        return studentDao.findByName(name);
    }

    @Override
    public int add(Student student) {
        return studentDao.add(student);
    }

    @Override
    public int edit(Student student) {
        return studentDao.edit(student);
    }

    @Override
    public int delete(String ids) {
        return studentDao.delete(ids);
    }

    @Override
    public List<Student> findList(Map<String,Object> queyMap) {
        return studentDao.findList(queyMap);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public int getToTal(Map<String, Object> queyMap) {
        return studentDao.getToTal(queyMap);
    }
}
