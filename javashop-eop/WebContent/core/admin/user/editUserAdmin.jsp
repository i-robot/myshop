<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
#repwdtr,#pwdtr{display:none}
</style>
<div class="input">
<form action="userAdmin!editSave.do"  class="validate" method="post" name="theForm" id="theForm">
<input type="hidden" name="adminUser.userid" value="${adminUser.userid }" />
<table  class="form-table">
<c:if test="${multiSite==1}">
	<tr>
		<th><label class="text">站点：</label></th>
		<td ><select name="adminUser.siteid" id="adminUserSite"/>
		</td>
	</tr>
<script>
$(function(){

	$.ajax({
		 type: "GET",
		 url: "../multiSite!listJson.do",
		 data:   "ajax=yes",
		 dataType:'json',
		 success: function(result){
			 if(result.result==0){
				 $("#adminUserSite").selectTree(result.data);
				 $("#adminUserSite").val(${siteid});
			 }else{
				 alert("站点列表获取失败，请重试");
			 }
	     },
	     error:function(){
			alert("站点列表获取失败");
		 }
	}); 
});
</script>
</c:if>
	<tr>
		<th><label class="text">用户名：</label></th>
		<td><input type="text" name="adminUser.username" value="${adminUser.username }"  dataType="string" isrequired="true" /></td>
	</tr>
	<tr>
		<th> </th>
		<td> <input type="checkbox" name="updatePwd" id="updatePwd" value="yes"  />同时修改密码 </td>
	</tr>
	<tr id="pwdtr">
		<th><label class="text">密码：</label></th>
		<td><input type="password" name="newPassword" id="pwd"  /></td>
	</tr>
	<tr id="repwdtr">
		<th><label class="text">确认密码：</label></th>
		<td><input type="password" id="repwd" dataType="string"  /></td>
	</tr>
	<tr>
		<th><label class="text">类型：</label></th>
		<td>
			<input type="radio"  name="adminUser.founder" value="0" id="notSuperChk" checked="true">普通管理员&nbsp;&nbsp;
			<input type="radio"  name="adminUser.founder" value="1" id="superChk">超级管理员
		</td>
	</tr>
	<tr id="roletr">
		<th><label class="text">角色：</label></th>
		<td>
			<ul style="width:100%" id="rolesbox">
				<c:forEach var="role" items="${roleList }">
				<li style="width:33%;display:block"><input type="checkbox" name="roleids" value="${role.roleid }"  />
					${role.rolename }&nbsp;</li>
				</c:forEach>
			</ul> 
		</td>		
	</tr>
		
	<c:forEach items="${htmlList }" var="html">${html }</c:forEach>
	
	<tr>
		<th><label class="text">状态：</label></th>
		<td>
			<input type="radio"  name="adminUser.state" value="1" checked=true>启用&nbsp;&nbsp;
			<input type="radio"  name="adminUser.state" value="0">禁用 
		</td>
	</tr>
	<tr>
		<th><label class="text">姓名：</label></th>
		<td><input type="text" name="adminUser.realname" value="${adminUser.realname }"  /></td>
	</tr>
	<tr>
		<th><label class="text">编号：</label></th>
		<td><input type="text" name="adminUser.userno"  value="${adminUser.userno }" /></td>
	</tr>
	<tr>
		<th><label class="text">部门：</label></th>
		<td><input type="text" name="adminUser.userdept" value="${adminUser.userdept }"  /></td>
	</tr>
	<tr>
		<th><label class="text">备注：</label></th>
		<td><input type="text" name="adminUser.remark"  value="${adminUser.remark }" /></td>
	</tr>

</table>

<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="submit" type="submit"	  value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>

<script type="text/javascript">

function checkpwd(){
	 if( $("#updatePwd").get(0).checked )  {
		if(  $("#repwd").val() !=   $("#pwd").val() ){
			return "两次密码不一至";
		}
	 }
	return true;
}



$(function(){
	$("form.validate").validate();
	$("#notSuperChk").click(function(){
		if(this.checked)
		$("#roletr").show();
	});
	$("#superChk").click(function(){
		if(this.checked)
		$("#roletr").hide();
	});	
	$("#updatePwd").click(function(){
		if(this.checked){
			$("#pwdtr").show();
			$("#repwdtr").show();			
		}else{
			$("#pwdtr").hide();
			$("#repwdtr").hide();
		}
	});

	<c:forEach var="userRole" items="${userRoles }">
		$("#rolesbox input[value='${userRole.roleid}']").attr("checked",true);
	</c:forEach>
	<c:if test="${adminUser.founder==1}" >
 	$("#superChk").click()
</c:if>
<c:if test="${adminUser.founder==0}" >
	$("#notSuperChk").click();
</c:if>
});
</script>

</form>
</div>