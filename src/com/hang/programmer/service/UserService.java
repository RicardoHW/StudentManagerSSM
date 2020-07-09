package com.hang.programmer.service;

import com.hang.programmer.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/15 9:24
 * @Description:
 */

public interface UserService {
    //查找用户名
    public User findByLoginName(String loginName);
    //用户名密码登录
    public User login(String loginName,String passWord);
    //添加用户
    public int add(User user);
    public int edit(User user);
    public int delete(String ids);
    public List<User> findList(Map<String,Object> queyMap);
    public int getToTal(Map<String,Object> queyMap);


}
