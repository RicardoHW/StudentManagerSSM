package com.hang.programmer.page;

import org.springframework.stereotype.Component;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/16 10:50
 * @Description: 分页类封装
 */
@Component
public class Page {
    private Integer page; //当前页
    private Integer rows; //每页显示数量
    private Integer offset;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer row) {
        this.rows = row;
    }

    public Integer getOffset() {
        this.offset = (page-1) *rows;
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = (page-1) *rows;
    }
}
