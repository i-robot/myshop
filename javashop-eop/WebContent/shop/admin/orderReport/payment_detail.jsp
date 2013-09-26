<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="infoPanel" style="">
<div class="infoContent" container="true"
	style="visibility: visible; opacity: 1;">
<div class="input">
<table class="form-table">
	<tr>
		<th>订单id：</th>
		<td>${payment.order_id }</td>
	</tr>
	<tr>
		<th>订单编号：</th>
		<td>${payment.order_sn }</td>
	</tr>
	<tr>
		<th>客户编号：</th>
		<td>${payment.member_id }</td>
	</tr>
	<tr>
		<th>支付方式：</th>
		<td>${payment.pay_method }</td>
	</tr>
	<tr>
		<th>流水号：</th>
		<td>${payment.sn}</td>
	</tr>
	<tr>
		<th>生成时间：</th>
		<td><html:dateformat pattern="yyyy-MM-dd" time="${payment.create_time }"></html:dateformat></td>
	</tr>
	<tr>
		<th>支付类型：</th>
		<td><c:if test="${payment.type==1 }">在线支付</c:if><c:if test="${payment.type=='offline' }">线下支付</c:if></td>
	</tr>
	<tr>
		<th>付款人：</th>
		<td>${payment.pay_user }</td>
	</tr>
	<tr>
		<th>付款金额：</th>
		<td><fmt:formatNumber value="${payment.money }" type="currency" pattern="￥.00"/></td>
	</tr>
</table>

<table cellspacing="0" cellpadding="0" border="0" style="margin: 0pt;"
	class="tableAction">
	<tbody>
		<tr>
			<td>备注：${payment.remark }</td>
		</tr>
	</tbody>
</table>

</div>
</div>
</div>