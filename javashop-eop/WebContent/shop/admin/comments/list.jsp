<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Comments.js"></script>

<div class="grid">

	<div class="toolbar">
	
		<ul>
			<li><a href="javascript:;" id="delBtn">删除</a></li>
		</ul>
		
		<div style="clear:both"></div>
	</div>
<form method="POST">
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell >商品名称</grid:cell>
	<grid:cell ><c:if test="${type == 1}">评论</c:if><c:if test="${type == 2}">咨询</c:if>人</grid:cell> 
	<grid:cell >发表时间</grid:cell>	
	<grid:cell ><c:if test="${type == 1}">评分</c:if></grid:cell>	
	<grid:cell >显示状态</grid:cell> 
	<grid:cell  width="200px">操作</grid:cell>
	</grid:header>

  <grid:body item="comments">
  		<grid:cell><input type="checkbox" name="id" value="${comments.comment_id }" />${comments.comment_id } </grid:cell>
        <grid:cell ><a href="../../goods-${comments.goods_id }.html" target="_blank">${comments.name}</a></grid:cell>
        <grid:cell><c:if test="${comments.uname != null && comments.uname != '' }">${comments.uname }</c:if><c:if test="${comments.uname == null || comments.uname == '' }">游客</c:if></grid:cell>  
        <grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm" time="${comments.dateline}"></html:dateformat></grid:cell>        
        <grid:cell><c:if test="${type==1}">${comments.grade }</c:if></grid:cell>        
        <grid:cell > 
        	<c:if test="${comments.status == 0 }">未审核&nbsp;
        	</c:if>
        	<c:if test="${comments.status == 1 }">审核通过&nbsp;</c:if>
        	<c:if test="${comments.status == 2 }">审核拒绝&nbsp;</c:if>
        </grid:cell>
        <grid:cell><a href="comments!detail.do?comment_id=${comments.comment_id}"   ><img class="modify" src="images/transparent.gif" ></a></grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
</div>


