<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
<form method="post" action="depot!saveEdit.do" class="validate">
<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
	<tr>
		<th><label class="text">仓库名称：</label></th>
		<td>
		<input type="hidden" name="room.id" value="${room.id }"/>
		<input type="text" name="room.name" maxlength="60"
			value="${room.name }" dataType="string" isrequired="true" /></td>
	</tr>

</table>
<div class="submitlist" align="center">
<table>
	<tr>
		<td><input type="submit" value=" 确    定   " class="submitBtn" />
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