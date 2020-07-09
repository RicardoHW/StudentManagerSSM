package com.hang.programmer.controller;

import com.hang.programmer.page.Page;
import com.hang.programmer.pojo.Clazz;
import com.hang.programmer.pojo.Student;
import com.hang.programmer.service.ClazzService;
import com.hang.programmer.service.StudentService;
import com.hang.programmer.utils.UserUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: Ricardo
 * @Date: 2020/5/18 17:03
 * @Description: 学生信息管理
 */


@Controller
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentService studentService;
    @Autowired
    private ClazzService clazzService;

    /**
     * 学生列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("student/student_list");

        List<Clazz> clazzList = clazzService.findAll();
        model.addObject("clazzList",clazzList);
        model.addObject("clazzListJson", JSONArray.fromObject(clazzList));
        return model;
    }

    /**
     * 获取学生列表
     * @param
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "userName",required = false,defaultValue = "") String userName,
            @RequestParam(value = "clazzId",required = false) Integer clazzId,
            HttpServletRequest request, HttpServletResponse response,
            Page page
    ){
        Map<String,Object> ret = new HashMap<String,Object>();
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("userName","%"+userName+"%");
        Object attribute = UserUtil.getUserType(request);

        if ("2".equals(attribute.toString())){
            //是学生
            Student loginedStudent = (Student) UserUtil.getUserNow(request);
            queryMap.put("userName","%"+loginedStudent.getUserName()+"%");
        }
        if (clazzId != null){
            queryMap.put("clazzId",clazzId);
        }
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        ret.put("rows",studentService.findList(queryMap));
        ret.put("total", studentService.getToTal(queryMap));
        return ret;
    }
  /*  @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(Student student,Page page
    ){
        Map<String,Object> ret = new HashMap<String,Object>();

        ret.put("rows",studentService.findList(student));
        //ret.put("total", studentService.getToTal(student));
        return ret;
    }*/

    /**
     * 文件上传
     * @param photo
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadPhoto(MultipartFile photo,
              HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> ret = new HashMap<String, String>();

        response.setCharacterEncoding("utf-8");
        if (photo == null){
            //文件未选择
            ret.put("type", "error");
            ret.put("msg", "请选择文件！");
            return ret;
        }

        if (photo.getSize() > 10485760){
            ret.put("type", "error");
            ret.put("msg", "文件过大，请选择小于10M的文件！");
            return ret;
        }
        //获取文件后缀
        String suffix = photo.getOriginalFilename().substring(
                photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
        //判断是否为图片文件
        if (!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())){
           ret.put("type", "error");
           ret.put("msg", "文件格式不支持，请选择jpg,png,gif,jpeg格式的图片文件！");
           return ret;
        }
        String savePath = request.getServletContext().getRealPath("/") + "upload\\";
        System.out.println(savePath);

        File savePathFile = new File(savePath);
        if (!savePathFile.exists()){
            savePathFile.mkdir(); //如果不存在，则创建文件夹
        }
        //把文件转存到这个文件夹下
        String fileName = UUID.randomUUID().toString() + "." + suffix;
        System.out.println(fileName);
        photo.transferTo(new File(savePath + fileName));
        ret.put("type","success");
        ret.put("msg","图片上传成功！");
        ret.put("src",request.getServletContext().getContextPath()+"/upload/" + fileName);
        return ret;
    }


        /**
         * 添加学生信息
         * @param
         * @return
         * */
        @RequestMapping(value = "/add",method = RequestMethod.POST)
        @ResponseBody
        public Map<String,String> add(Student student){
            Map<String,String> ret = new HashMap<String,String>();

            if (StringUtils.isEmpty(student.getUserName())){
                ret.put("type","error");
                ret.put("msg","学生姓名不能为空！");
                return ret;
            }
            if (StringUtils.isEmpty(student.getPassWord())){
                ret.put("type","error");
                ret.put("msg","密码不能为空！");
                return ret;
            }
            if (student.getClazzId() == 0){
                ret.put("type","error");
                ret.put("msg","请选择所属班级！");
                return ret;
            }
            if (studentService.findByName(student.getUserName()) != null ){
                ret.put("type","error");
                ret.put("msg","添加失败,该学生已存在！");
                return ret;
            }
            if (studentService.add(student)<=0){
                ret.put("type","error");
                ret.put("msg","学生添加失败！");
                return ret;
            }
            ret.put("type","success");
            ret.put("msg","学生添加成功！");
            return ret;
        }


        /**
         * 修改学生信息
         * @param student
         * @return
         */
        @RequestMapping(value = "/edit",method = RequestMethod.POST)
        @ResponseBody
        public Map<String,String> edit(Student student) {
            Map<String, String> ret = new HashMap<String, String>();
            if (StringUtils.isEmpty(student.getUserName())){
                ret.put("type","error");
                ret.put("msg","学生名称不能为空！");
                return ret;
            }
            if (null == student.getClazzId() ){
                ret.put("type","error");
                ret.put("msg","所属班级不能为空！");
                return ret;
            }
            Student  existStudent = studentService.findByName(student.getUserName());
            if (existStudent != null ){
                if (student.getId() != existStudent.getId()){
                    ret.put("type","error");
                    ret.put("msg","修改失败,该用户已存在！");
                    return ret;
                }
            }

            if (studentService.edit(student)<=0){
                ret.put("type","error");
                ret.put("msg","学生修改失败！");
                return ret;
            }
            ret.put("type","success");
            ret.put("msg","学生修改成功！");
            return ret;
        }


        /**
         * 删除学生信息
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
                if (studentService.delete(idsString) <=0){
                    ret.put("type","error");
                    ret.put("msg","删除失败！");
                    return ret;
                }
            } catch (Exception e){
                ret.put("type","error");
                ret.put("msg","该学生下存在学生信息，请勿冲动！");
                return ret;
            }
            ret.put("type","success");
            ret.put("msg","删除成功！");
            return ret;
        }

        public static final void dd(){
            System.out.println("dgvgxg");
        }

        public void ss(){
        }

}
