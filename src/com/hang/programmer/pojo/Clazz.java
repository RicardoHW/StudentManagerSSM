package com.hang.programmer.pojo;

import org.springframework.stereotype.Component;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 10:42
 * @Description: 班级实体类
 */

@Component
public class Clazz {
    private int  id;
    private String name;
    private int gradeId;
    private String gradeName;
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

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
