package com.hang.programmer.controller;

import com.hang.programmer.page.Page;
import com.hang.programmer.pojo.Clazz;
import com.hang.programmer.pojo.Grade;
import com.hang.programmer.service.ClazzService;
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
import java.util.List;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 10:35
 * @Description: 班级信息管理
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private GradeService gradeService;

    /**
     * 班级列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("clazz/clazz_list");
        List<Grade> findAll = gradeService.findAll();
        model.addObject("gradeList",findAll); //下拉框的值
        return model;
    }

    /**
     * 获取班级列表
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",required = false,defaultValue = "") String name,
            @RequestParam(value = "gradeId",required = false) Integer gradeId,
            Page page
    ){
        Map<String,Object> ret = new HashMap<String,Object>();
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("name","%"+name+"%");
        if (gradeId != null){
            queryMap.put("gradeId",gradeId);
        }
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        ret.put("rows",clazzService.findList(queryMap));
        ret.put("total", clazzService.getToTal(queryMap));
        return ret;
    }
    /**
     * 添加班级信息
     * @param
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Clazz clazz){
        Map<String,String> ret = new HashMap<String,String>();

        if (StringUtils.isEmpty(clazz.getName())){
            ret.put("type","error");
            ret.put("msg","班级名称不能为空！");
            return ret;
        }
        if (clazz.getGradeId() == 0){
            ret.put("type","error");
            ret.put("msg","请选择所属年级！");
            return ret;
        }
        if (clazzService.add(clazz)<=0){
            ret.put("type","error");
            ret.put("msg","班级添加失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","班级添加成功！");
        return ret;
    }

    /**
     * 修改班级信息
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Clazz clazz) {
        Map<String, String> ret = new HashMap<String, String>();
        if (StringUtils.isEmpty(clazz.getName())){
            ret.put("type","error");
            ret.put("msg","班级名称不能为空！");
            return ret;
        }
        if (clazz.getGradeId() == 0){
            ret.put("type","error");
            ret.put("msg","所属年级不能为空！");
            return ret;
        }
        if (clazzService.edit(clazz)<=0){
            ret.put("type","error");
            ret.put("msg","班级修改失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","班级修改成功！");
        return ret;
    }

    /**
     * 删除班级信息
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
            if (clazzService.delete(idsString) <=0){
                ret.put("type","error");
                ret.put("msg","删除失败！");
                return ret;
            }
        } catch (Exception e){
            ret.put("type","error");
            ret.put("msg","该班级下存在学生信息，请勿冲动！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","删除成功！");
        return ret;
    }
}

