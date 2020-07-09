package com.hang.programmer.controller;

import com.hang.programmer.pojo.Student;
import com.hang.programmer.pojo.User;
import com.hang.programmer.service.StudentService;
import com.hang.programmer.service.UserService;
import com.hang.programmer.utils.CpachaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/14 9:16
 * @Description: 系统主页控制器
 */

@RequestMapping("/system")
@Controller
public class SystemController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model){
        model.setViewName("system/index");
        return model;
    }

    /**
     * 登录页
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model ){
        model.setViewName("system/login");
        return model;
    }

    /**
     * 注销登录
     * @return
     */
    @RequestMapping(value = "/login_out", method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        return "redirect:login";
    }

    /**
     * 登陆表单提交
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> login(
            @RequestParam(value = "passWord",required = true) String passWord,
            @RequestParam(value = "loginName",required = true) String loginName,
            @RequestParam(value = "vcode",required = true) String vcode,
            @RequestParam(value = "type",required = true) int type,
            HttpServletRequest request,HttpServletResponse response
    ){
       Map<String,String> ret = new HashMap<String, String>();
       if (StringUtils.isEmpty(loginName)){
           ret.put("type","error");
           ret.put("msg","用户名不能为空" );
           return ret;
       }
        if (StringUtils.isEmpty(passWord)){
            ret.put("type","error");
            ret.put("msg","密码不能为空" );
            return ret;
        }
        //todo 验证码
        /*if (StringUtils.isEmpty(vcode)){
            ret.put("type","error");
            ret.put("msg","验证码不能为空" );
            return ret;
        }*/

        String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
        if (StringUtils.isEmpty(loginCpacha)){
            ret.put("type","error");
            ret.put("msg","长时间未操作，会话已失效，请刷新后重试！" );
            return ret;
        }
        //todo 验证码
        /*if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
            ret.put("type","error");
            ret.put("msg","验证码错误！" );
            return ret;
        }*/
        request.getSession().setAttribute("loginCpacha",null);//清空

        //从数据库查找用户

        if (type == 1){
            //管理员
            User user = userService.login(loginName,passWord);
            if (user == null){
                ret.put("type","error");
                ret.put("msg","用户名或密码错误！" );
                return ret;
            }
            request.getSession().setAttribute("user",user);
        }
        if (type == 2 ){
            //学生
            Student student = studentService.findByName(loginName);
            if (student == null){
                ret.put("type","error");
                ret.put("msg","用户名错误！" );
                return ret;
            }
            if (!passWord.equals(student.getPassWord())){
                ret.put("type","error");
                ret.put("msg","密码错误！" );
                return ret;
            }
            request.getSession().setAttribute("user",student);
        }
        request.getSession().setAttribute("userType",type);
        ret.put("type","success");
        ret.put("msg","登陆成功" );
        return ret;
    }


    /**
     * 显示验证码
     * @param request
     * @param response
     * @param vl 验证码个数
     * @param w  图片宽度
     * @param h  图片高度
     */
    @RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
    public void getCpacha(HttpServletRequest request,HttpServletResponse response,
                          @RequestParam(value="vl",defaultValue = "4",required = false) Integer vl,
                          @RequestParam(value="w",defaultValue = "98",required = false) Integer w,
                          @RequestParam(value="h",defaultValue = "33",required = false) Integer h
    ){
       CpachaUtil cpachaUtil = new CpachaUtil(vl,w,h);
       String generatorVCode = cpachaUtil.generatorVCode();
       request.getSession().setAttribute("loginCpacha",generatorVCode);
       BufferedImage generatorVCodeImage = cpachaUtil.generatorVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorVCodeImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
