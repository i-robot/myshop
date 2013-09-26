<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
      <tbody>        
          <tr>
            <th><span>未处理订单：</span></th>
            <td><span><a <c:if test="${orderss.wait > 0 }"> href="${ctx }/shop/admin/order!search.do?status=-99" class="imp" target="ajax"</c:if>>${ orderss.wait}</a>个</span></td>
          </tr>
          <tr>
            <th><span>待发货订单：</span></th>
            <td><span ><a <c:if test="${orderss.allocation_yes > 0 }"> href="${ctx }/shop/admin/order!search.do?status=4" class="imp" target="ajax"</c:if>>${ orderss.allocation_yes}</a>个</span></td>
          </tr>
          <tr>
            <th><span>待支付订单：</span></th>
            <td><span><a <c:if test="${orderss.not_pay > 0 }"> href="${ctx }/shop/admin/order!search.do?paystatus=0&status=-100" class="imp" target="ajax"</c:if>>${ orderss.not_pay}</a>个</span></td>
          </tr>   
          <tr>
            <th><span>已成交订单数：</span></th>
            <td><span><a <c:if test="${orderss.complete > 0 }"> href="${ctx }/shop/admin/order!search.do?status=7" class="imp" target="ajax"</c:if>>${ orderss.complete}</a>个</span></td>
          </tr>    
        </tbody>            
 </table>