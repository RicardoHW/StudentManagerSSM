<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生列表</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
		//todo
		var clazzList = ${clazzListJson};
		$(function() {

			var table;
			//datagrid初始化
			$('#dataList').datagrid({
				title:'学生列表',
				iconCls:'icon-more',//图标
				border: true,
				collapsible:false,//是否可折叠的
				fit: true,//自动大小
				method: "post",
				url:"get_list?t="+new Date().getTime(),
				idField:'id',
				singleSelect:false,//是否单选
				pagination:true,//分页控件
				rownumbers:true,//行号
				sortName:'id',
				sortOrder:'ASC',
				remoteSort: false,
                //checkOnSelect: false,
                //selectOnCheck: false,
				columns: [[
					{field:'chk',checkbox: true,width:50},
					{field:'id',title:'ID',width:50, sortable: true},
                    {field:'userName',title:'姓名',width:100 ,sortable: true},
					{field:'sex',title:'性别',width:50,
                        formatter:function (value) {
                            if (value ==0 ) {
                                return "女"
							} else {
                                return "男"
							}
                        }
					},
					{field:'age',title:'年龄',width:50},
					{field:'stuNum',title:'学号',width:150 },
					{field:'clazzName',title:'所属班级',width:100},
					{field:'passWord',title:'密码',width:150},
                    {field:'photo',title:'图片',width:100,
                        formatter:function(value){
							return '<img src='+value+' width="50px"/>';
						}
					},
					{field:'remark',title:'备注',width:260 }
				]],
				toolbar: "#toolbar"
			});
			//设置分页控件
			var p = $('#dataList').datagrid('getPager');
			$(p).pagination({
				pageSize: 10,//每页显示的记录条数，默认为10
				pageList: [10,20,30,50,100],//可以设置每页记录条数的列表
				beforePageText: '第',//页数文本框前显示的汉字
				afterPageText: '页    共 {pages} 页',
				displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			});
			//设置工具类按钮
			// 添加
			$("#add").click(function(){
				table = $("#addTable");
				$("#addDialog").dialog("open");
			});
			//修改
			$("#edit").click(function(){
				table = $("#editTable");
				var selectRows = $("#dataList").datagrid("getSelections");
				if(selectRows.length != 1){
					$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
				} else{
					$("#editDialog").dialog("open");
				}
			});
			//删除
			$("#delete").click(function(){
				var selectRows = $("#dataList").datagrid("getSelections");
				var selectLength = selectRows.length;
				if(selectLength == 0){
					$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
				} else{
					var ids = [];
					$(selectRows).each(function(i, row){
						ids[i] = row.id;
					});
					$.messager.confirm("消息提醒", "如果学生下存在学生信息则无法删除，须先删除学生下属的学生信息！", function(r){
						if(r){
							$.ajax({
								type: "post",
								url: "delete",
								data: {ids: ids},
								dataType: 'json',
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒","删除成功!","info");
										//刷新表格
										$("#dataList").datagrid("reload");
										$("#dataList").datagrid("uncheckAll");
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					});
				}
			});
			//设置添加窗口
			$("#addDialog").dialog({
				title: "添加学生信息",
				width: 350,
				height: 500,
				iconCls: "icon-add",
				modal: true,
				collapsible: false,
				minimizable: false,
				maximizable: false,
				draggable: true,
				closed: true,
				buttons: [
					{
						text:'添加',
						plain: true,
						iconCls:'icon-user_add',
						handler:function(){
							var validate = $("#addForm").form("validate");
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{

								var data = $("#addForm").serialize();

								$.ajax({
									type: "post",
									url: "add",
									data: data,
									dataType: 'json',
									success: function(data){
										if(data.type == "success"){
											$.messager.alert("消息提醒","添加成功!","info");
											//关闭窗口
											$("#addDialog").dialog("close");
											//清空原表格数据
											$("#add_userName").textbox('setValue', "");
                                            $("#add_age").textbox('setValue', "");
                                            $("#add_stuNum").textbox('setValue', "");
                                            $("#add_passWord").textbox('setValue', "");
											$("#add_remark").textbox('setValue', "");
											//重新刷新页面数据
											$('#dataList').datagrid("reload");
										} else{
											$.messager.alert("消息提醒",data.msg,"warning");
											return;
										}
									}
								});
							}
						}
					},
				],
				onClose: function(){
                    $("#add_userName").textbox('setValue', "");
                    $("#add_age").textbox('setValue', "");
                    $("#add_stuNum").textbox('setValue', "");
                    $("#add_passWord").textbox('setValue', "");
                    $("#add_remark").textbox('setValue', "");
				}
			});

			//编辑学生信息
			$("#editDialog").dialog({
				title: "修改学生信息",
				width: 350,
				height: 500,
				iconCls: "icon-edit",
				modal: true,
				collapsible: false,
				minimizable: false,
				maximizable: false,
				draggable: true,
				closed: true,
				buttons: [
					{
						text:'提交',
						plain: true,
						iconCls:'icon-edit',
						handler:function(){
							var validate = $("#editForm").form("validate");
							if(!validate){
								$.messager.alert("消息提醒","请检查你输入的数据!","warning");
								return;
							} else{
								var data =  $("#editForm").serialize();

								$.ajax({
									type: "post",
									url: "edit",
									data: data,
									dataType: 'json',
									success: function(data){
										if(data.type == "success"){
											$.messager.alert("消息提醒","修改成功!","info");
											//关闭窗口
											$("#editDialog").dialog("close");
											//重新刷新页面数据
											$('#dataList').datagrid("reload");

										} else{
											$.messager.alert("消息提醒",data.msg,"warning");
											return;
										}
									}
								});
							}
						}
					}
				],
				onBeforeOpen: function(){
					var selectRow = $("#dataList").datagrid("getSelected");
					//设置值
					$("#edit_id").val(selectRow.id);
					$("#edit_userName").textbox('setValue', selectRow.userName);
					$("#edit_clazzId").combobox('setValue',selectRow.clazzId);
                    $("#edit_clazzId2").combobox('setValue',selectRow.clazzId);
                    $("#edit_age").textbox('setValue', selectRow.age);
                   	$("#edit_sex").combobox('setValue', selectRow.sex);
                    $("#edit_stuNum").textbox('setValue',selectRow.stuNum) ;
                    $("#edit_stuNum2").textbox('setValue',selectRow.stuNum);
					$("#edit_passWord").textbox('setValue', selectRow.passWord);
					$("#edit_remark").textbox('setValue', selectRow.remark);
				}
			});

			//搜索按钮
			$("#search-btn").click(function () {
			$("#dataList").datagrid('reload',{
			    userName:$("#search-userName").textbox('getValue'),
                clazzId:$("#search-clazzId").combobox('getValue')
			});
        });

			//上传图片按钮
			$("#upload-btn").click(function () {
			    if ($("#add-upload-photo").filebox("getValue") == '') {
                    $.messager.alert("消息提醒","请选择图片文件！","warning");
                    return;
				}
				$("#photoForm").submit();
                $("#add-upload-photo").textbox("setValue","");
            });
            $("#edit-upload-btn").click(function () {
                if ($("#edit-upload-photo").filebox("getValue") == '') {
                    $.messager.alert("消息提醒","请选择图片文件！","warning");
                    return;
                }
                $("#editphotoForm").submit();
                $("#edit-upload-photo").textbox("setValue","");
            });

            $("#show_photo").click(function () {
                $("#showDialog").dialog("open");
            })
		});
        function uploaded(e) {
            var data = $(window.frames["photo_target"].document).find("body pre").text();

            if (data == "") {
                return;
            }
            data = JSON.parse(data);
			if (data.type == "success") {
				$.messager.alert("消息提醒","图片上传成功！","info");
				$("#photo-preview").attr("src",data.src);
				$("#add_photo").val(data.src);
                $("#edit-photo-preview").attr("src",data.src);
                $("#edit_photo").val(data.src);
			}else {
				$.messager.alert("消息提醒",data.msg,"warning");
			}
        }
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0" ></table>
	<!-- 工具栏 -->
	<div id="toolbar">
		<c:if test="${userType ==1}">
			<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div>
			<c:if test="${userType ==1}">
				<a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>
			</c:if>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			所属班级：<select   id="search-clazzId" class="easyui-combobox" style="width: 100px">
				<option value="">全部</option>
				<c:forEach items="${clazzList}" var="clazz">
					<option value="${clazz.id}">${clazz.name}</option>
				</c:forEach>
			</select>
			学生名称：<input   id="search-userName" class="easyui-textbox"/>
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	</div>
	
	<!-- 添加窗口 -->
	<div id="addDialog" style="padding: 10px;">
		<form id="photoForm" method="post" enctype="multipart/form-data" action="upload_photo" target="photo_target">
			<table id="addTable1"  cellpadding="8" >
				<tr>
					<td style="width:60px">预览头像：</td>
					<td>
						<img id="photo-preview" alt="照片" style="max-width: 50px; max-height: 50px;" title="图片" src="${pageContext.servletContext.contextPath}/photo/student.jpg" />
					</td>
				</tr>
				<tr>
					<td style="width:60px">学生头像：</td>
					<td>
						<input id="add-upload-photo" class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:100px;">
						<a id="upload-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">上传图片</a>
					</td>
				</tr>
			</table>
		</form>
		<form id="addForm" method="post">
	    	<table id="addTable2"  cellpadding="8" >
				<input id="add_photo" type="hidden" name="photo" value="${pageContext.servletContext.contextPath}/photo/student.jpg">
				<tr>
	    			<td style=" width:60px" >学生姓名:</td>
	    			<td >
	    				<input id="add_userName"  class="easyui-textbox" style="width: 200px; height: 30px;"
							   type="text" name="userName" data-options="required:true, missingMessage:'请输入学生姓名'" />
	    			</td>
	    		</tr>
				<tr>
					<td style="width:55px">年龄:</td>
					<td >
						<input id="add_age"  class="easyui-textbox" style="width: 200px; height: 30px;"
							   type="text" name="age" data-options="required:true, missingMessage:'请输入年龄'" />
					</td>
				</tr>
				<tr>
					<td>性别:</td>
					<td >
						<select id="add_sex"  class="easyui-combobox" style="width: 200px; "
								name="sex" data-options="required:true,  missingMessage:'请选择性别'" >
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style=" width:60px" >学号:</td>
					<td >
						<input id="add_stuNum"  class="easyui-textbox" style="width: 200px; height: 30px;"
							   type="text" name="stuNum" data-options="required:true, missingMessage:'请输入学号'" />
					</td>
				</tr>
				<tr>
					<td style="width:55px">登陆密码:</td>
					<td >
						<input id="add_passWord"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password"
							   name="passWord" data-options="required:true, missingMessage:'请输入登陆密码'" />
					</td>
				</tr>
				<tr>
					<td>所属班级:</td>
					<td >
						<select id="add_clazzId"  class="easyui-combobox" style="width: 200px; "
								name="clazzId" data-options="required:true,  missingMessage:'请选择所属班级'" >
							<c:forEach items="${clazzList}" var="clazz">
								<option value="${clazz.id}">${clazz.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
	    		<tr>
	    			<td>备注:</td>
					<td ><input id="add_remark"  class="easyui-textbox" data-options="multiline:true" style="width: 200px; height: 100px;" name="remark" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改窗口 -->
	<div id="editDialog" style="padding: 10px">
		<form id="editphotoForm" method="post" enctype="multipart/form-data" action="upload_photo" target="photo_target">
			<table id="editTable1"  cellpadding="8" >
				<tr>
					<td style="width:60px">预览头像：</td>
					<td>
						<img id="edit-photo-preview" alt="照片" style="max-width: 50px; max-height: 50px;" title="图片" src="${pageContext.servletContext.contextPath}/photo/student.jpg" />
					</td>
				</tr>
				<tr>
					<td style="width:60px">学生头像：</td>
					<td>
						<input id="edit-upload-photo" class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:100px;">
						<a id="edit-upload-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">上传图片</a>
					</td>
				</tr>
			</table>
		</form>
    	<form id="editForm" method="post">
			<input type="hidden" name="id" id="edit_id"/>
			<input id="edit_photo" type="hidden" name="photo" value="${pageContext.servletContext.contextPath}/photo/student.jpg">
			<table id="editTable" border=0 cellpadding="8" >
				<tr>
					<td style=" width:60px" >学生姓名:</td>
					<td >
						<input id="edit_userName"  class="easyui-textbox" style="width: 200px; height: 30px;"
							   type="text" name="userName" data-options="required:true, missingMessage:'请输入学生姓名'" />
					</td>
				</tr>
				<tr>
					<td style="width:55px">年龄:</td>
					<td >
						<input id="edit_age"  class="easyui-textbox" style="width: 200px; height: 30px;"
							   type="nunber" name="age" data-options="required:true, missingMessage:'请输入年龄'" />
					</td>
				</tr>
				<tr>
					<td>性别:</td>
					<td >
						<select id="edit_sex"  class="easyui-combobox" style="width: 200px; "
								name="sex" data-options="required:true,  missingMessage:'请选择性别'" >
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</td>
				</tr>
				<c:if test="${userType ==1}">
					<tr>
						<td style=" width:60px" >学号:</td>
						<td >
							<input id="edit_stuNum"  class="easyui-textbox" style="width: 200px; height: 30px;"
								   type="text" name="stuNum" data-options="required:true, missingMessage:'请输入学号'" />
						</td>
					</tr>
					<tr>
					<td>所属班级:</td>
					<td >
						<select id="edit_clazzId"  class="easyui-combobox" style="width: 200px; "
								name="clazzId" data-options="required:true,  missingMessage:'请选择所属班级'" >
							<c:forEach items="${clazzList}" var="clazz">
								<option value="${clazz.id}">${clazz.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				</c:if>
				<c:if test="${userType ==2}">
					<tr>
						<td style=" width:60px" >学号:</td>
						<td >
							<input id="edit_stuNum2"  class="easyui-textbox" style="width: 200px; height: 30px;" readonly
								   type="text" name="stuNum" data-options="required:true, missingMessage:'请输入学号'" />
						</td>
					</tr>
					<tr>
						<td>所属班级:</td>
						<td >
							<select id="edit_clazzId2"  class="easyui-combobox" style="width: 200px; " readonly
									name="clazzId" data-options="required:true,  missingMessage:'请选择所属班级'" >
								<c:forEach items="${clazzList}" var="clazz">
									<option value="${clazz.id}">${clazz.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
				<tr>
					<td style="width:55px">登陆密码:</td>
					<td >
						<input id="edit_passWord"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password"
							   name="passWord" data-options="required:true, missingMessage:'请输入登陆密码'" />
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td ><input id="edit_remark"  class="easyui-textbox" data-options="multiline:true" style="width: 200px; height: 100px;" name="remark" /></td>
				</tr>
	    	</table>
	    </form>
	</div>

	<!-- 提交表单处理iframe框架 -->
	<div>
		<iframe id="photo_target" name="photo_target"  onload="uploaded(this)"></iframe>
	</div>

</body>
</html>