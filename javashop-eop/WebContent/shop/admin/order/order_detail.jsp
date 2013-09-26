<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="../shop/admin/js/OrderDetail.js"></script>
 
<style>
<!--
.box {width:250px;display:block;float:left}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

.division table {
	margin: 0;
	padding: 0;
	width:100%
}

.orderdetails_basic th {
	color: #336699;
	text-align: left;
	white-space: nowrap;
}

.division th {
	background: none repeat scroll 0 0 #E2E8EB;
	border-right: 1px solid #CCCCCC;
	font-size: 14px;
	text-align: right;
	white-space: nowrap;
	width: 140px;
}

.division th,.division td {
	border-color: #FFFFFF #DBE2E7 #DDDDDD #FFFFFF;
	border-left: 1px solid #FFFFFF;
	border-right: 1px solid #DBE2E7;
	border-style: solid;
	border-width: 1px;
	padding: 5px;
	vertical-align: top;
}

.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

h5 {
	font-size: 1em;
	font-weight: bold;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}

#order_dialog .con {
	background: none repeat scroll 0 0 #FFFFFF;
	overflow-x: hidden;
	overflow-y: auto;
	height: 400px;
	visibility: visible;
	opacity: 1;
	position: relative;
}

.allo_box {
	background: none repeat scroll 0 0 #EEEEEE;
	border: 1px solid #CCCCCC;
	position: absolute;
	width: 300px;
	display: none;
}

.allo_box li {
	line-height: 30px;
	border-bottom: 1px solid #ccc
}

.allo_items li.selected {
	background-color: #00EE76
}

;
.close_box a {
	float: right
}
-->
</style>
<div class="toolbar">
		<form id="nextForm" action="" method="post">
	<c:if test="${ord.isCod }">
		<html:permission actid="order,customer_service">
			<input type="button" id=confirmorder value="确认订单" />
		</html:permission>
	</c:if>

	<html:permission actid="finance">
		<input type="button" id="pay" value="确认收款" />
		<input type="button" id="refund" value="退款" />
	</html:permission>

	<html:permission actid="allocation">
		<input type="button" id="allocation" value="配货" />
	</html:permission>

	<html:permission actid="depot_ship">
		<input type="button" id="shipping" value="发货" />
		<input type="button" id="returned" value="退货" />
	</html:permission>
	<html:permission actid="order">
		<input type="button" id="cancel" value="作废" />
	</html:permission>
	<span style="float:right">
	<html:permission actid="order">
					<button title="购物清单打印" class="p_prted" id="orderprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!order_prnt.do?orderId=${orderId }'); return false;">购</button>
					<c:if test="${ord.ship_status>=1 }">
					<button title="配货单打印" class="p_prted" id="deliveryprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!delivery_prnt.do?orderId=${orderId }');return false;">配</button>
					<button title="联合打印" class="p_prted" id="globalprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!global_prnt.do?orderId=${orderId }');return false;">合</button>
					</c:if>
					<button title="快递单打印" class="p_prted" id="shipprntBtn"
						onclick="javascript:window.open('../shop/admin/orderPrint!ship_prnt_step1.do?orderId=${orderId }');return false;">递</button>
				</html:permission>
	</span>
	<span id="nextorder">
		<input type="hidden" name="orderId" value="${orderId}"> 
		<input type="hidden" name="sn" value="${sn}">
		<input type="hidden" name="logi_no" value="${logi_no}"> 
		<input type="hidden" name="uname" value="${uname}">
		<input type="hidden" name="ship_name" value="${ship_name}">
		<input type="hidden" name="status" value="${status}">
		<input type="hidden" id="alert_null" value="${alert_null}">
		<input id="nextvalue" type="hidden" name="next" value="">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="previous" type="button" value="上一订单">
		<input id="next" type="button"	value="下一订单">
		</span>
		<span class="help_icon" helpid="order_opration"></span>
	</form>
	<div style="clear: both"></div>
</div>

<div style="display: block;" class="order_detail">

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
</div>


<div id="order_dialog">
	<form id="order_form">
		<input type="hidden" id="orderid" name="orderId" value="${orderId }" />
		<div class="con"></div>
	</form>
	<div class="footContent">
		<div style="width: 200px; height: 40px; margin: 0pt auto;"
			class="mainFoot">
			<table style="margin: 0pt auto; width: auto;">
				<tbody>
					<tr>
						<td><b class="save">
								<button class="submitBtn">提交</button> </b>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>


<script>


$(function(){
	if($("#alert_null").val() == 'kong'){
		alert("已经是最后一条！");
	}
	OrderDetail.init(${orderId},${ord.status},${ord.pay_status},${ord.ship_status},${ord.isCod});
	$("#previous").click( function(){
		$("#nextvalue").val('previous');
		$("#nextForm").attr("action","order!nextDetail.do?rand="+new Date().getTime());
		$("#nextForm").submit(); 
		});
	$("#next").click( function(){
		$("#nextvalue").val('next');
		$("#nextForm").attr("action","order!nextDetail.do?rand="+new Date().getTime());
		$("#nextForm").submit(); 
		});
});
 
</script>