package com.hang.programmer.utils;

import com.hang.programmer.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/21 15:39
 * @Description:
 */
public class UserUtil {
    public static Object getUserType(HttpServletRequest request){
        return request.getSession().getAttribute("userType");
    }
    public static Object getUserNow(HttpServletRequest request){
        return request.getSession().getAttribute("user");
    }
}
