package com.hang.programmer.dao;

import com.hang.programmer.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/15 9:23
 * @Description:
 */
@Repository
public interface UserDao {
    public User findByLoginName(String loginName);
    public User login(@Param("loginName") String loginName,@Param("passWord") String passWord);
    public int add(User user);
    public int edit(User user);
    public int delete(String ids);
    public List<User> findList(Map<String,Object> queyMap);
    public int getToTal(Map<String,Object> queyMap);
}
