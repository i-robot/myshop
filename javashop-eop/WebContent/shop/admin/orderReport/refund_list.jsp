<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div class="grid">
	
<form method="POST">
<grid:grid from="webpage"   >

	<grid:header>
		<grid:cell >流水号</grid:cell>
		<grid:cell>退款类型</grid:cell>
		<grid:cell>退款方式</grid:cell>
		<grid:cell >退款日期</grid:cell>
		<grid:cell >退款金额</grid:cell>
		<grid:cell >退款人</grid:cell>
		<grid:cell>查看详细</grid:cell> 
	 
	</grid:header>

	<grid:body item="payLog">
		<grid:cell>${payLog.sn }</grid:cell>
		<grid:cell>
			<c:if test="${payLog.type==1}">在线支付</c:if>
			<c:if test="${payLog.type==2}">线下支付</c:if>
		</grid:cell>
		<grid:cell>${payLog.pay_method}  </grid:cell>
		<grid:cell>
		<html:dateformat pattern="yy-MM-dd hh:mm:ss" time="${ payLog.pay_date*1000 }"></html:dateformat>
		</grid:cell>
		<grid:cell> ￥<fmt:formatNumber value="${payLog.money}" maxFractionDigits="2" /> </grid:cell>
		<grid:cell>${payLog.op_user }</grid:cell>
 		<grid:cell><a href="javascript:;" refundid="${payLog.refund_id }" class="showdetail"><img class="modify" src="images/transparent.gif" ></a></grid:cell>
	</grid:body>

</grid:grid>

</form>	
<div id="refund_detail"></div>
<script type="text/javascript">
$(function(){
	Eop.Dialog.init({id:"refund_detail",modal:false,title:"退款单详细", width:"650px"});
	$(".showdetail").click(function(){
		var refund_id = $(this).attr("refundid");
		$("#refund_detail").load("orderReport!refundDetail.do?id="+ refund_id+"&ajax=yes");
		Eop.Dialog.open("refund_detail");
	});
});
</script>
</div>


