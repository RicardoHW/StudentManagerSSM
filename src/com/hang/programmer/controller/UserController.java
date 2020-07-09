package com.hang.programmer.controller;

import com.hang.programmer.page.Page;
import com.hang.programmer.pojo.User;
import com.hang.programmer.service.UserService;
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
 * @Date: 2020/5/15 14:19
 * @Description: 用户（管理员）控制器
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    public UserService userService;

    /**
     * 用户管理列表页
     * @return
     */
    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("user/user_list");
    }

    /**
     * 获取用户列表
     * @param loginName
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "loginName",required = false,defaultValue = "") String loginName,
           Page page
    ){
        Map<String,Object> ret = new HashMap<String,Object>();
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("loginName","%"+loginName+"%");
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        ret.put("rows",userService.findList(queryMap));
        ret.put("total", userService.getToTal(queryMap));
        return ret;
    }

    /**
     * 添加用户（管理员）
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(User user){
        Map<String,String> ret = new HashMap<String,String>();
        if (user == null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错，请联系开发人员！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getLoginName())){
            ret.put("type","error");
            ret.put("msg","用户名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getPassWord())){
            ret.put("type","error");
            ret.put("msg","密码不能为空！");
            return ret;
        }
        User  existUser = userService.findByLoginName(user.getLoginName());
        if (existUser != null ){
            ret.put("type","error");
            ret.put("msg","添加失败,该用户已存在！");
            return ret;
        }
        int temp =userService.add (user);
        if ( temp <=0){
            ret.put("type","error");
            ret.put("msg","添加失败！");
            return ret;
        }

        ret.put("type","success");
        ret.put("msg","添加成功！");
        return ret;
    }

    /**
     * 编辑用户（管理员）
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(User user){
        Map<String,String> ret = new HashMap<String,String>();
        if (user == null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错，请联系开发人员！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getLoginName())){
            ret.put("type","error");
            ret.put("msg","用户名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(user.getPassWord())){
            ret.put("type","error");
            ret.put("msg","密码不能为空！");
            return ret;
        }
        User  existUser = userService.findByLoginName(user.getLoginName());
        if (existUser != null ){
            if (user.getId() != existUser.getId()){
                ret.put("type","error");
                ret.put("msg","修改失败,该用户已存在！");
                return ret;
            }
        }
        if (  userService.edit(user) <=0){
            ret.put("type","error");
            ret.put("msg","修改失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功！");
        return ret;
    }

    /**
     * 删除用户
     * @param
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(
            @RequestParam(value = "ids[]",required = true) Integer[] ids
    ) {
        Map<String, String> ret = new HashMap<String,String>();

        if (ids == null){
            ret.put("type","error");
            ret.put("msg","请选择要删除的数据！");
            return ret;
        }
        String idsString = "";
        for (Integer id : ids) {
            idsString += id + ",";
        }
        idsString = idsString.substring(0,idsString.length()-1);
        if (userService.delete(idsString) <=0){
            ret.put("type","error");
            ret.put("msg","删除失败！");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","删除成功！");
        return ret;
    }
}
