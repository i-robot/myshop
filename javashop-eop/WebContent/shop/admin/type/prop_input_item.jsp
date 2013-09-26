<%@ page contentType="text/html;charset=UTF-8" %>
<tr id="tr_prop">
       <td width="20%"><input type="text" name="propnames"  maxlength="60" value=""  dataType="string" isrequired="true"/></td>
       <td width="20%">&nbsp;
	   <select name="proptypes">
	   
	   <optgroup label="输入项">
	   	<option value="1" >输入项 可搜索 </option>
		<option value="2">输入项 不可搜索 </option>
		</optgroup> 	

		<optgroup label="选择项">
	   	<option value="3">选择项 渐进式搜索 </option>
	   	<option value="4">选择项 普通搜索 </option>
		<option value="5">选择项 不可搜索 </option>
		</optgroup>
		
	   </select>
	   </td>
       <td width="30%">&nbsp;<input type="text" name="options"  value="" style="width:90%" /></td>
	       <td>&nbsp;<input type="text" name="unit" style="width:80px" /></td>
		   <td>&nbsp;
		       
		       <select name="required">
			       	<option value="0">否</option>
			       	<option value="1">是</option>		       	
		       </select>
		       
		   </td>
		        
	       <td>&nbsp;
	       
		       <select name="datatype">
			       	<option value="">无</option>
			       	<option value="int">整数</option>
			       	<option value="float">浮点数</option>
		       </select>
	       
		   </td> 
       <td ><a href="javascript:;"   ><img class="delete" src="images/transparent.gif" ></a></td>
</tr>