<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div class="grid">
	
<form method="POST">


<grid:grid from="webpage"  >

	<grid:header>
		<grid:cell >流水号</grid:cell>
		<grid:cell>支付类型</grid:cell>
		<grid:cell>支付方式</grid:cell>
		<grid:cell >付款日期</grid:cell>
		<grid:cell >付款金额</grid:cell>
		<grid:cell >付款人</grid:cell>
		<grid:cell >状态</grid:cell>
	 	<grid:cell >查看详细</grid:cell>
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
		<grid:cell>${payLog.pay_user }</grid:cell>
		<grid:cell>
			<c:if test="${ payLog.status==1}" >已确认</c:if>
			<c:if test="${payLog.status==0 }">未确认</c:if>
		</grid:cell>
		<grid:cell><a href="javascript:;" paymentid="${payLog.payment_id }" class="showdetail"><img class="modify" src="images/transparent.gif" ></a></grid:cell>
	</grid:body>

</grid:grid>


</form>	
<div id="pay_detail"></div>
<script type="text/javascript">
$(function(){
	Eop.Dialog.init({id:"pay_detail",modal:false,title:"收款单详细", width:"650px"});
	$(".showdetail").click(function(){
		var payment_id = $(this).attr("paymentid");
		$("#pay_detail").load("orderReport!paymentDetail.do?id="+ payment_id+"&ajax=yes");
		Eop.Dialog.open("pay_detail");
	});
});
</script>
</div>


