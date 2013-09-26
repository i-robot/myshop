<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Brand.js"></script>

<div class="grid">
	<form action="brand!search.do" method="post">
		<div class="toolbar" style="height: 50px">
			<div style="width: 100%; float: left; height: 25px;">
				<ul>
					<li><a href="brand!add.do">添加</a></li>
					<li><a href="javascript:;" id="delBtn">删除</a></li>
					<li><a href="brand!trash_list.do">回收站</a></li>
				</ul>
			</div>
			<div class="help_icon" helpid="goodscat"></div>
			<div id="searchcbox" style="margin-left: 10px">
				品牌名称:<input type="text" style="width: 150px" name="brandname" value="${brandname }" /> 
				商品类型： <select name="type_id" style="width: 150px">
					<option	<c:if test="${type_id == -100 }">selected="selected" </c:if> value="-100">所有类型</option>
					<c:forEach items="${brand_types}" var="brand_type">
						<option	<c:if test="${type_id == brand_type.type_id }">selected="selected" </c:if>	value="${brand_type.type_id}">${brand_type.name}</option>
					</c:forEach>
				</select> <input type="submit" name="submit" value="搜索">
			</div>
			<div style="clear: both"></div>
		</div>
		<grid:grid from="webpage">

			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="name" width="250px">品牌名称</grid:cell>
				<grid:cell sort="url">品牌网址</grid:cell>
				<grid:cell width="100px">操作</grid:cell>
			</grid:header>

			<grid:body item="brand">
				<grid:cell>
					<input type="checkbox" name="id" value="${brand.brand_id }" />${brand.brand_id } </grid:cell>
				<grid:cell>${brand.name } </grid:cell>
				<grid:cell>${brand.url} <input type="hidden"
						name="filePath" value="${brand.logo }" />
				</grid:cell>
				<grid:cell>
					<a href="brand!edit.do?brand_id=${brand.brand_id }"><img
						class="modify" src="images/transparent.gif"> </a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>