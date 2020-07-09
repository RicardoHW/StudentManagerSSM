package com.hang.programmer.pojo;

import org.springframework.stereotype.Component;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/16 16:02
 * @Description: 年级实体
 */

@Component
public class Grade {
    private int  id;
    private String name;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
