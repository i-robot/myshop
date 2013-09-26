<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript"  >
loadScript("js/GoodsTag.js");
</script>
<style>
#tagspan{
	position: absolute;
	display:none;
}
#searchcbox{float:left}
#searchcbox div{float:left;margin-left:10px}
</style>
<div class="toolbar">
	<strong>${tag.tag_name}</strong>
</div>
<div class="grid">
<form action="goodsShow!search.do" method="get">
<input type="hidden" name="tagid" value="${tagid }"/>

<input type="hidden" name="tagids" value=""/>
	<div class="toolbar" style="height:50px">
	<div style="width:100%;float:left;height:25px;">
		<ul>
			<li><a href="javascript:;" id="btnAdd">添加选中</a></li>
		</ul> 
	</div>
	
	<div id="searchcbox" style="margin-left:10px">
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<div>商品名称:<input type="text" style="width:150px" name="name" value="${name }"/></div>
		
		<div>商品编号:<input type="text" style="width:150px" name="sn" value="${sn }"/></div>
		<div id="searchCat"></div>
		<input type="submit" name="submit" value="再次搜索">
	</div>
 		
<div style="clear:both"></div>
	</div>
</form>	
<form id="gridform">
<input type="hidden" name="tagid" value="${tagid }"/>
<input type="hidden" name="catid" value="${catid }"/>
<grid:grid  from="webpage">

	<grid:header>
		<grid:cell  width="50px"><input type="checkbox" id="toggleChk"/></grid:cell> 
		<grid:cell sort="sn" width="120px">商品编号</grid:cell> 
		<grid:cell sort="name" >商品名称</grid:cell>
		<grid:cell sort="cat_id"  width="100px">分类</grid:cell> 
		<c:if test="${isStoreManager==false}" >
		<th>销售价格</th>
		</c:if>
		<grid:cell sort="market_enable">上架</grid:cell>
		<grid:cell sort="brand_id">品牌</grid:cell>
		<grid:cell sort="last_modify">上架时间</grid:cell>
	</grid:header>


  <grid:body item="goods" >
  
  		<grid:cell><input type="checkbox" name="id" value="${goods.goods_id }" />${goods.goods_id }</grid:cell>
        <grid:cell>&nbsp;${goods.sn } </grid:cell>
        <grid:cell>&nbsp;<a href="../../goods-${goods.goods_id }.html" target="_blank" >${goods.name }</a></grid:cell> 
        <grid:cell>&nbsp;${goods.cat_name} </grid:cell> 
        
        <c:if test="${isStoreManager==false}" >
        <td>&nbsp;<fmt:formatNumber value="${goods.price}" type="currency" pattern="￥.00"/> </td> 
        </c:if>
        
        <grid:cell>&nbsp;<c:if test="${goods.market_enable==0}" >否</c:if> <c:if test="${goods.market_enable==1}" >是</c:if></grid:cell>
        <grid:cell>&nbsp;${goods.brand_name} </grid:cell> 

        <grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" time="${goods.last_modify}"></html:dateformat> </grid:cell>
         
  </grid:body>
  
</grid:grid>
</form>
<div style="clear:both;padding-top:5px;"></div>
</div>
<script type="text/javascript">
$(function(){
	$.ajax({
		url:basePath+'goods!getCatTree.do?ajax=yes',
		type:"get",
		dataType:"html",
		success:function(html){
			 
			var serachCatSel =$(html).appendTo("#searchCat");
			serachCatSel.removeClass("editinput").attr("name","catid");
			serachCatSel.children(":first").before("<option value=\"\" selected>全部类别</option>");
			<c:if test="${catid!=null}">serachCatSel.val(${catid})</c:if>
		},
		error:function(){
			alert("获取分类树出错");
		}
	});
});
</script>
