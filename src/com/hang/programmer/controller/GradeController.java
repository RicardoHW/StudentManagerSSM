package com.hang.programmer.controller;

import com.hang.programmer.page.Page;
import com.hang.programmer.pojo.Grade;
import com.hang.programmer.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/15 15:58
 * @Description: 年级
 */
@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 年级列表页
     * @param
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "grade/grade_list";
    }

    /**
     * 获取年级列表
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            Page page
    ){
        Map<String,Object> ret = new HashMap<String,Object>();
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("name","%"+name+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        ret.put("rows",gradeService.findList(queryMap));
        ret.put("total", gradeService.getToTal(queryMap));
        return ret;
    }
    /**
     * 添加年级信息
     * @param
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Grade grade){
        Map<String,String> ret = new HashMap<String,String>();

        if (StringUtils.isEmpty(grade.getName())){
            ret.put("type","error");
            ret.put("msg","年纪名称不能为空！");
            return ret;
        }
        if (gradeService.add(grade)<=0){
            ret.put("type","error");
            ret.put("msg","年纪添加失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","年纪添加成功！");
        return ret;
    }

    /**
     * 修改年级信息
     * @param grade
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Grade grade) {
        System.out.println("121312");
        Map<String, String> ret = new HashMap<String, String>();
        if (StringUtils.isEmpty(grade.getName())){
            ret.put("type","error");
            ret.put("msg","年纪名称不能为空！");
            return ret;
        }
        if (gradeService.edit(grade)<=0){
            ret.put("type","error");
            ret.put("msg","年纪修改失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","年纪修改成功！");
        return ret;
    }

    /**
     * 删除年级信息
     * @param
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(
            @RequestParam(value = "ids[]",required = true) Integer[] ids
    ) {
        Map<String, String> ret = new HashMap<String,String>();

        if (ids == null ||ids.length ==0){
            ret.put("type","error");
            ret.put("msg","请选择要删除的数据！");
            return ret;
        }
        String idsString = "";
        for (Integer id : ids) {
            idsString += id + ",";
        }
        idsString = idsString.substring(0,idsString.length()-1);

        try {
            if (gradeService.delete(idsString) <=0){
                ret.put("type","error");
                ret.put("msg","删除失败！");
                return ret;
            }
        } catch (Exception e){
            ret.put("type","error");
            ret.put("msg","该年级下存在班级信息，请勿冲动！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","删除成功！");
        return ret;
    }
}
