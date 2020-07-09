package com.hang.programmer.service.impl;

import com.hang.programmer.dao.UserDao;
import com.hang.programmer.pojo.User;
import com.hang.programmer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/15 9:28
 * @Description:
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User login(String loginName,String passWord) {

        return userDao.login(loginName,passWord);
    }

    @Override
    public User findByLoginName(String loginName) {


        return  userDao.findByLoginName(loginName);
    }


    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public int edit(User user) {
        return userDao.edit(user);
    }

    @Override
    public int delete(String ids) {
        return userDao.delete(ids);
    }

    @Override
    public List<User> findList(Map<String,Object> queyMap) {
        return userDao.findList(queyMap);
    }

    @Override
    public int getToTal(Map<String, Object> queyMap) {
        return userDao.getToTal(queyMap);
    }
}
