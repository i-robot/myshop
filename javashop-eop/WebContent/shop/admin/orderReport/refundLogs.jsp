<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div class="grid">
	
<form method="POST">
<grid:grid from="refundLogsList"   >

	<grid:header>
		<grid:cell >流水号</grid:cell>
		<grid:cell>退款类型</grid:cell>
		<grid:cell>退款方式</grid:cell>
		<grid:cell >退款日期</grid:cell>
		<grid:cell >退款金额</grid:cell>
		<grid:cell >退款人</grid:cell>
	 
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
	</grid:body>

</grid:grid>

</form>	

</div>


