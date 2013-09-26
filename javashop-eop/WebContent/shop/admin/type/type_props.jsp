<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="js/GoodsType.js"></script>
<script type="text/javascript">$(function(){Prop.init();})</script>
<div class="input">
	<div style="text-align:center">
	<div class="list-div" id="listDiv"   style="width:100%;text-align:left">
	
		<div class="toolbar">
			<ul>
			<li><a href="javascript:;" id="propAddBtn">新增属性</a></li>
		 	</ul>
			<div style="clear:both"></div>
		</div>
	 <form method="post" action="type!saveProps.do" name="theForm" id="theForm"  >
		<input type="hidden" name="is_edit" value="${is_edit }" />
	   <table  id="props_table" cellpadding="3" cellspacing="1" style="width:100%"  >
	   <tbody>
	     <tr>
	       <th>属性名</th>
	       <th>类型</th>
	       <th>选择项(逗号分隔)</th>
	       <th>单位</th>
	       <th>必填</th>
	       <th>校验</th>
	       <th>&nbsp;</th>
	     </tr>
	
	
	     
	<c:if test="${goodsType!=null}" >     
	          
	<c:forEach items="${goodsType.propList }" var="prop" varStatus="pStatus">
	
	     <tr >
	       <td width="20%"  ><input type="text" name="propnames"  maxlength="60" value="${prop.name }"  dataType="string" isrequired="true"/></td>
	       <td width="20%">&nbsp;
		   <select name="proptypes">
		   
		   <optgroup label="输入项">
		   	<option value="1" <c:if test="${prop.type==1 }">selected</c:if>>输入项 可搜索 </option>
			<option value="2" <c:if test="${prop.type==2 }">selected</c:if>>输入项 不可搜索 </option>
			</optgroup> 	
	
			<optgroup label="选择项">
		   	<option value="3" <c:if test="${prop.type==3 }">selected</c:if>>选择项 渐进式搜索 </option>
		   	<option value="4" <c:if test="${prop.type==4 }">selected</c:if>>选择项 普通搜索 </option>
			<option value="5" <c:if test="${prop.type==5 }">selected</c:if>>选择项 不可搜索 </option>
			</optgroup>
			
		   </select>
		   </td>
		   <td width="30%">&nbsp;<input type="text" name="options" style="width:300px" value="${prop.options }" /></td>
		   
	       <td >&nbsp;<input type="text" name="unit" style="width:80px" value="${prop.unit }" /></td>
		   <td>&nbsp;
		       
		       <select name="required">
			       	<option value="0" <c:if test="${prop.required==0}" >selected</c:if>>否</option>
			       	<option value="1" <c:if test="${prop.required==1}" >selected</c:if>>是</option>		       	
		       </select>
		       
		   </td>
		        
	       <td>&nbsp;
	       
		       <select name="datatype">
			       	<option value="">无</option>
			       	<option value="int" <c:if test="${prop.datatype=='int'}" >selected</c:if>>整数</option>
			       	<option value="float" <c:if test="${prop.datatype=='float'}" >selected</c:if>>浮点数</option>
		       </select>
	       
		   </td>

	       <td><a href="javascript:;"  ><img class="delete" src="images/transparent.gif" ></a></td>
	     </tr>
	</c:forEach>
	</c:if>
	<c:if test="${(goodsType.props==null || goodsType.props=='') && is_edit==1}">
	 <tr id="tr_prop">
	       <td width="20%"  ><input type="text" name="propnames"  maxlength="60" value=""  dataType="string" isrequired="true"/></td>
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
	       <td width="30%">&nbsp;<input type="text" name="options"  value="" /></td>
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
	       
	       <td><a href="javascript:;"   ><img class="delete" src="images/transparent.gif" ></a></td>
	     </tr>
	</c:if>
	 
	     </tbody>
	   </table>
	   
<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="submit" type="submit"	  value=" 下一步   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
	   
	 </form>
	</div>
	</div>
</div>