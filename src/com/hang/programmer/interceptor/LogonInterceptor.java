package com.hang.programmer.interceptor;

import com.hang.programmer.pojo.User;
import com.hang.programmer.utils.UserUtil;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/15 13:52
 * @Description: 登录拦截器
 */
public class LogonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String url= request.getRequestURI();
        //System.out.println("进入拦截器，url = " + url);
        Object user = UserUtil.getUserNow(request);
        if (user == null){
            //表示未登录或登录失效
            System.out.println("未登录或登录失效,url = " +url);
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                //ajax 请求
                Map<String,String> ret = new HashMap<String, String>();
                ret.put("type","error");
                ret.put("msg","登陆状态已失效，请重新登录！");
                response.getWriter().write(JSONObject.fromObject(ret).toString());
                return false;
            }
            //跳到登陆页面
            response.sendRedirect(request.getContextPath() + "/system/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
