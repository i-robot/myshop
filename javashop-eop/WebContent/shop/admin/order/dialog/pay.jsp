<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
$("#paylog").unbind("click");
$("#paylog").bind("click",function(){
	$("#order_dialog .con").load(basePath+"payment!showPaylogDialog.do?ajax=yes&orderId="+OrderDetail.orderid,function(){
		$("#order_dialog .submitBtn").unbind("click");
		$("#order_dialog .submitBtn").bind("click",function(){
			OrderDetail.paylog();
		});
	}); 
	
});
</script>
<div class="division">
<table width="100%">
  <tbody><tr>
    <th>订单号：</th>
    <td>${ord.sn } 【${ord.payStatus}】</td>
    <th>下单日期：</th>
    <td><html:dateformat pattern="yy-MM-dd hh:mm:ss" time="${ord.create_time}"></html:dateformat></td>
      </tr>
  <tr> 
  
    <th>订单总金额：</th>
    <td >￥<fmt:formatNumber value="${ord.order_amount}" maxFractionDigits="2" /></td>
    
    <th>已收金额：</th>
    <td> ￥<fmt:formatNumber value="${ord.paymoney}" maxFractionDigits="2" /> </td>
    
    </tr>
    </tbody></table>
</div> 
  
<div class="grid">

<grid:grid from="payLogList"  >

	<grid:header>
		<grid:cell>支付类型</grid:cell>
		<grid:cell>支付方式</grid:cell>
		<grid:cell >付款日期</grid:cell>
		<grid:cell >交易流水号</grid:cell>
		<grid:cell >付款金额</grid:cell>
		<grid:cell >状态</grid:cell>
	 
	</grid:header>
	
	

	<grid:body item="payLog">
		<grid:cell>
			<c:if test="${payLog.type==1}">在线支付</c:if>
			<c:if test="${payLog.type==2}">线下支付</c:if>
		</grid:cell>
		<grid:cell>${payLog.pay_method}  </grid:cell>
		<grid:cell>
		
 
		<html:dateformat pattern="yy-MM-dd hh:mm:ss" time="${ payLog.pay_date*1000 }"></html:dateformat>
		
		</grid:cell>
		<grid:cell>${payLog.sn }</grid:cell>
		<grid:cell> ￥<fmt:formatNumber value="${payLog.money}" maxFractionDigits="2" /> </grid:cell>
		<grid:cell>
			<c:if test="${ payLog.status==1}" >已确认</c:if>
			<c:if test="${payLog.status==0 }">未确认</c:if>
		</grid:cell>
	</grid:body>
	
	
<c:if test="${ord.isCod }">
   <!--<grid:cell>
     <input type="button" id="paylog" value="添加收款" />
     </grid:cell>--> 
     </c:if>
     
     
</grid:grid>
		
</div>		



 
