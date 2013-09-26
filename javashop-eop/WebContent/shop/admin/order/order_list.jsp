<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="../shop/admin/js/Order.js"></script>

<div class="grid">
	<form action="order!search.do" method="post">
		<div class="toolbar"  >
		
			<div style="width: 100%; float: left; height: 25px;">
				<ul>
				<html:permission actid="order">
					<li><a href="javascript:;" id="delBtn">删除</a>
					</li>
					<li><a href="order!trash_list.do">回收站</a>
					</li>
				</html:permission>
					<li><a href="javascript:;" id="searchBtn">高级搜索</a>
					</li>
					
				</ul>
			</div>
	
			<div id="searchcbox"  style="display:none;position: absolute;background-color: #FFF;border:1px solid #ccc">
				
			<div class="input">
		 
			<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
				<tr>
					<th><label class="text">订单号:</label></th>
					<td><input type="text" style="width: 150px" name="sn" value="${sn }" /></td>
				</tr>
				<tr>
					<th><label class="text">物流单号:</label></th>
					<td><input type="text" style="width: 150px" name="logi_no" value="${logi_no }" /></td>
				</tr>
				<tr>
					<th><label class="text"> 会员用户名：</label></th>
					<td><input type="text" style="width: 150px" name="uname" value="${uname }" /> </td>
				</tr>
				<tr>
					<th><label class="text">收货人姓名：</label></th>
					<td><input type="text" style="width: 150px" name="ship_name" value="${ship_name }" /></td>
				</tr>
				<tr>
					<th><label class="text">订单状态：</label></th>
					<td>
					 <select name="status" style="width: 150px;height:25px;font-size:14px;font-weight: bold;">
					<option <c:if test="${status == -100 }">selected="selected" </c:if> value="-100">所有状态</option>
					<option <c:if test="${status == -99 }">selected="selected" </c:if> value="-99">等待处理订单</option>
					<option  <c:if test="${status == -7 }">selected="selected" </c:if>  value="-7">已换货</option>
					<option  <c:if test="${status == -6 }">selected="selected" </c:if>  value="-6">换货被拒绝</option>
					<option  <c:if test="${status == -5 }">selected="selected" </c:if>  value="-5">退货被拒绝</option>
					<option  <c:if test="${status == -4 }">selected="selected" </c:if>  value="-4">申请换货</option>
					<option  <c:if test="${status == -3 }">selected="selected" </c:if>  value="-3">申请退货</option>
					<option <c:if test="${status == -2 }">selected="selected" </c:if>  value="-2">退货</option>
					<option <c:if test="${status == -1 }">selected="selected" </c:if>  value="-1">退款</option>
					<option <c:if test="${status == 0 }">selected="selected" </c:if>  value="0">未付款（新订单）</option>
					<option <c:if test="${status == 1 }">selected="selected" </c:if>  value="1">已支付、待确认</option>
					<option <c:if test="${status == 2 }">selected="selected" </c:if>  value="2">已确认支付</option>
					<option <c:if test="${status == 3 }">selected="selected" </c:if>  value="3">配货中</option>
					<option <c:if test="${status == 4 }">selected="selected" </c:if>  value="4">配货完成，待发货</option>
					<option <c:if test="${status == 5 }">selected="selected" </c:if>  value="5">已发货</option>
					<option <c:if test="${status == 6 }">selected="selected" </c:if>  value="6">已收货</option>
					<option <c:if test="${status == 7 }">selected="selected" </c:if>  value="7">已完成</option>
					<option <c:if test="${status == 8 }">selected="selected" </c:if>  value="8">作废</option>
					<option <c:if test="${status == 9 }">selected="selected" </c:if>  value="9">订单已确认</option>
				</select>
					</td>
				</tr>
				
					<tr>
					<th><label class="text">付款状态：</label></th>
					<td>
				<select name="paystatus" style="width: 150px;height:25px;font-size:14px;font-weight: bold;">
				<option <c:if test="${paystatus == -100 }">selected="selected" </c:if> value="-100">所有状态</option>
				<option <c:if test="${paystatus == 0 }">selected="selected" </c:if> value="0">未付款</option>
				<option <c:if test="${paystatus == 1 }">selected="selected" </c:if> value="1">已付款待确认</option>
				<option <c:if test="${paystatus == 2 }">selected="selected" </c:if> value="2">已确认付款</option>
				<option <c:if test="${paystatus == 3 }">selected="selected" </c:if> value="3">已退款</option>
				<option <c:if test="${paystatus == 4 }">selected="selected" </c:if> value="4">部分退款</option>
				<option <c:if test="${paystatus == 5 }">selected="selected" </c:if> value="5">部分付款</option>
				</select>
					</td>
				</tr>
 
			</table>
			<div class="submitlist" align="center">
			<table>
				<tr>  
					<td>
					<input type="submit" name="submit" value="搜索" class="submitBtn" />
					&nbsp;&nbsp;<input type="button" id="closeBtn"  value="取消" class="submitBtn" />
					</td>
				</tr>
			</table>
			</div>
		 
			</div>
				
				
			</div>
			
			<div style="clear: both"></div>
		</div>


		<grid:grid from="webpage">

			<grid:header>
				<grid:cell width="30px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="sn" width="110px">订单号&nbsp;&nbsp;<span
						class="help_icon" helpid="order_showdetail"></span>
				</grid:cell>
				<grid:cell>下单日期</grid:cell>
				<grid:cell sort="status">订单状态</grid:cell>
				<grid:cell>订单总额</grid:cell>
				<grid:cell>收货人</grid:cell>
				<grid:cell>付款状态</grid:cell>
				<grid:cell>发货状态</grid:cell>
				<grid:cell>配送方式</grid:cell>
				<grid:cell>支付方式</grid:cell>
				<grid:cell>操作</grid:cell>
				<grid:cell>打印</grid:cell>
			</grid:header>

			<grid:body item="order">
				<grid:cell>
					<input type="checkbox" name="id" value="${order.order_id }" />
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>
					<a href="order!detail.do?orderId=${order.order_id}&sn=${sn}&logi_no=${logi_no}&uname=${uname}&ship_name=${ship_name}&status=${status}" title="订单详细">
						${order.sn } </a>
					<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>
					<html:dateformat pattern="yyyy-MM-dd" time="${order.create_time}"></html:dateformat>
					<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>${order.orderStatus}<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>
					<fmt:formatNumber value="${order.order_amount}" type="currency"
						pattern="￥.00" />
					<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>${order.ship_name}<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>${order.payStatus}<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>${order.shipStatus}<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>${order.shipping_type}<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${order.status<3}">
						<B>
					</c:if>${order.payment_name}<c:if test="${order.status<3}">
						</B>
					</c:if>
				</grid:cell>
				<grid:cell>
				<html:permission actid="order,customer_service">
					<a href="order!detail.do?orderId=${order.order_id}&sn=${sn}&logi_no=${logi_no}&uname=${uname}&ship_name=${ship_name}&status=${status}" title="订单详细"><img
						class="modify" src="images/transparent.gif"> </a>
				</html:permission>
				</grid:cell>
				<grid:cell>
				<html:permission actid="order">
					<button title="购物清单打印" class="p_prted" id="orderprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!order_prnt.do?orderId=${order.order_id }'); return false;">购</button>
					<c:if test="${order.ship_status>=1 }">
					<button title="配货单打印" class="p_prted" id="deliveryprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!delivery_prnt.do?orderId=${order.order_id }');return false;">配</button>
					<button title="联合打印" class="p_prted" id="globalprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!global_prnt.do?orderId=${order.order_id }');return false;">合</button>
					</c:if>
					<button title="快递单打印" class="p_prted" id="shipprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!ship_prnt_step1.do?orderId=${order.order_id }');return false;">递</button>
				</html:permission>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<script type="text/javascript">
$(function(){
	$("#searchBtn").click(function(){
		$("#searchcbox").show();
	});
	
	$("#closeBtn").click(function(){
		$("#searchcbox").hide();
	});
});
</script>
