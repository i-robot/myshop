<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="input">
<form class="validate" action="region!saveEdit.do" method="POST">
<div class="division">
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="form-table">
    <tbody><tr>
      <th>地区名称:<input type="hidden" name="regions.region_id" value="${region_id }"/><input type="hidden" name="regions.region_grade" value="${regiongrade }"/>
      </th>
      <td><input type="text" maxlength="20" name="regions.local_name" value="${regions.local_name }" isrequired="true"></td>
    </tr>
    <tr>
      <th>是否支持货到付款:
      </th>
      <td>
      <select name="regions.cod" id="cod">
     	<option value="0">不支持</option>
     	<option value="1">支持</option>
      </select></td>
    </tr>
    </tbody></table>
    <div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="submit" type="submit"	  value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>
  </div>
  </form>
  <script type="text/javascript">
$("form.validate").validate();
$("#cod").val("${regions.cod}");
</script>
</div>