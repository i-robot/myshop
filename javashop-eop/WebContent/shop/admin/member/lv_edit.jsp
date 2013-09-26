<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<div class="input">
 <form class="validate" method="post" action="member!saveEditLv.do" name="theForm" id="theForm"  onsubmit="javascript:return check();">
 <input type="hidden" name="lv.lv_id"  value="${lv.lv_id }" />
 
   <table cellspacing="1" cellpadding="3" width="100%">
     <tr>
       <th><label class="text">等级名称</label></th>
       <td width="27%"><input type="text" name="lv.name" id="name" maxlength="60" value="${lv.name }" dataType="string" isrequired="true"/>       </td>
       <td width="58%" align="left"><div id="nameTip" style="width:80px;"></div></td>
     </tr>
    <tr>
       <th><label class="text">所需积分</label></th>
       <td width="27%"><input type="text" name="lv.point" id="name"  style="width:80px" value="${lv.point}" dataType="int" isrequired="true"/>       </td>
       <td width="58%" align="left"><div id="nameTip" style="width:80px;"></div></td>
     </tr>    

     <tr>
       <th><label class="text">优惠百分比</label></th>
       <td  >
       <input type="text" name="lv.discount" id="discount" style="width:50px;" value="${lv.discount}" />%&nbsp;&nbsp;
		</td>
       <td   align="left"><div id="default_lvTip"  >如果输入80，表示该会员等级以销售价80%的价格购买。</div></td>
     </tr>

     <tr>
       <th><label class="text">默认等级</label></th>
       <td  >
       <input type="radio" name="lv.default_lv"  value="0" <c:if test="${lv.default_lv == 0}">checked</c:if>  />否&nbsp;&nbsp;<input type="radio" name="lv.default_lv"  <c:if test="${lv.default_lv == 1}">checked</c:if>  value="1" />是
       </td>
       <td   align="left"><div id="default_lvTip" style="width:80px;"></div></td>
     </tr>
      <!--tr>
       <th><label class="text">优惠百分比</label></th>
       <td   align="left" colspan="2">
       <div id="default_lvTip"  >如果输入80，表示该会员等级以销售价80%的价格购买。0为不优惠</div>
        <div class="grid">
			<form>
			<div id="cat_list">
			<table>
				<thead>
					<tr>
					<th width=20>ID</th> 
					<th width=100>分类名称</th> 
					<th>优惠百分比</th> 
					</tr>
				</thead> 
				<tbody>
					<c:forEach items="${catDiscountList }" var="item">
					<tr>
					<td>${item.cat_id}</td>
				    <td style="padding-left:0px;font-weight: bold;">${item.name}</td>
					<td>
						  <input type="hidden" name="cat_ids" value="${item.cat_id }"/>
					     <input type="text" name="cat_discounts" style="width:50px" value="${item.discount }"/>
				    </td> 
					</tr>
					</c:forEach>
				</tbody>
			</table>	
				
			</div>
       </td>
     </tr-->    
   </table>
<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="submit" type="submit"	  value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>    
 </form>
 <script type="text/javascript">
$("form.validate").validate();
function check(){
	var valueArr = document.getElementsByName("cat_discounts") ;
    for (var i=0;i<valueArr.length;i++ )
    {           
        var v  = valueArr[i].value;
        if(isNaN(v)){
        	alert("优惠值必须是数字");
        	return false;
        }
        
    }
}
</script>
</div>