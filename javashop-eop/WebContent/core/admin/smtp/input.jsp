<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">

<c:if test="${isedit==1 }" >
<form method="post" action="smtp!saveEdit.do" class="validate" >
<input type="hidden" name="smtp.id" value="${smtp.id }"/>
</c:if>

<c:if test="${isedit==0 }" >
<form method="post" action="smtp!saveAdd.do" class="validate" >
</c:if>

<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
	<tr>
		<th><label class="text">host：</label></th>
		<td>
		<input type="text" name="smtp.host" id="name" maxlength="60" value="${smtp.host }"  isrequired="true" />
		</td>
	</tr>
	<tr>
		<th><label class="text">用户名：</label></th>
		<td>
		<input type="text" name="smtp.username"  size="40"  value="${smtp.username }"  isrequired="true"  />
		</td>
	</tr>
	<tr>
		<th><label class="text">密码：</label></th>
		<td>
		<input type="text" name="smtp.password"  size="40"   value="${smtp.password }"  isrequired="true"  />
		</td>
	</tr>
	<tr>
		<th><label class="text">每日最大发信数：</label></th>
		<td><input type="text" name="smtp.max_count"  size="20" value="${smtp.max_count }"  isrequired="true" dataType="int"/>封</td>
	</tr>

	<tr>
		<th><label class="text">from：</label></th>
		<td>
		<input type="text" name="smtp.mail_from"  size="40"   value="${smtp.mail_from }"  isrequired="true"  />
		</td>
	</tr>
	
		
	<c:if test="${isedit==1 }" >
	
	<tr>
		<th><label class="text">今日已发</label></th>
		<td>${smtp.send_count }封</td>
	</tr>
	
	</c:if>
	
</table>

<div class="submitlist" align="center">
<table>
	<tr>
		<td><input type="submit" name="submit" value=" 确    定   " class="submitBtn" />
		</td>
	</tr>
</table>
</div>
</form>
</div>

<script type="text/javascript">
	$(function() {
		$("form.validate").validate();
	});
</script>