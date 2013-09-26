<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript"> 
$(function(){
	 
 	 $(".goods_input .tab>li").click(function(){
 	 	 var tabid=$(this).attr("tabid");
 	 	
 	 	 $(".goods_input .tab>li").removeClass("active");
 	 	 $(this).addClass("active");
 	 	 $(".tab-page .tab-panel").hide();
 	 	 $(".tab-panel[tabid="+tabid+"]").show();
 	 });

 	 
	 $("form.validate").validate({onShowError:function(el,msg){
		 $(".tab-page .tab-panel").hide();
		var tabpanel =  el.parents(".tab-panel").show();
		var tabid  = tabpanel.attr("tabid");
		tabid = tabid.replace('tab_','');
	
		$(".goods_input .tab>li").removeClass("active");
		$(".goods_input .tab>li[tabid="+tabid+"]").addClass("active");
		$(".goods_input .tab-panel[tabid=tab_"+tabid+"]").show();
		
		return true;
	 }});
	 $("input[type=text]").attr("autocomplete","off");
 
	
	 $("input[type=submit]").click(function(){
		 $.Loading.text="正在生成缩略图，请稍候...";
	 });
 
});
	
</script>
<div id="memberpricedlg"></div>
<div class="input">

<form method="post" name="theForm" action="${actionName }" id="theForm" class="validate">

<c:if test="${is_edit }">
	<input type="hidden" name="isedit" value="yes" />
	<input type="hidden" name="goods.goods_id" value="${goodsView.goods_id  }" />
</c:if>

<div style="display: block;" class="goods_input">

	<div class="tab-bar" style="position: relative;">
	<ul class="tab">
	 
		<c:forEach var="tab" items="${pluginTabs}" varStatus="status">
			<li tabid="${tab.key }" <c:if test="${status.index==0 }">class="active"</c:if>>${tab.value }</li>
		</c:forEach>
	</ul>
	</div>


	<div class="tab-page">
	 	  <c:forEach var="content" items="${pluginHtmls}" varStatus="status">
	 	  	<div tabid="${content.key }" <c:if test="${status.index!=0 }">style="display:none"</c:if>  class="tab-panel">
	 	  		${content.value }
	 	  	</div>
	 	  </c:forEach>
	</div>

	<div class="submitlist" align="center"   style="width:100%;float:left">
	 <table>
	 <tr><td >
	  <input name="submit" type="submit"	  value=" 确    定   " class="submitBtn" />
	   </td>
	   </tr>
	 </table>
	</div>

</div>

</form>
</div>
