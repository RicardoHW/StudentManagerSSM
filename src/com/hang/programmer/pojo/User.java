package com.hang.programmer.pojo;

import org.springframework.stereotype.Component;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/15 9:17
 * @Description: 用户实体类
 */

@Component
public class User {
    private Integer id;  //用户id 主键、自增
    private String loginName;
    private String passWord;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
