<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/Goods.js");
loadScript("js/GoodsStore.js");

$(function(){
	$("#searchBtn").click(function(){
		$("#searchcbox").show();
	});
	
	$("#closeBtn").click(function(){
		$("#searchcbox").hide();
	});
});
</script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin: 10px
}
#searchcbox .form-table th{width:auto}
</style>
<div class="grid"> 
	<form action="goods!list.do" method="get">
		<input type="hidden" name="optype" value="${optype }" /> <input
			type="hidden" name="depotid" value="${depotid }" /> <input
			type="hidden" name="cat_id" value="${catid }" />
		<div class="toolbar" >
		
				<div style="width: 100%; float: left; height: 25px;">
					<ul>
						<html:permission actid="goods">
							<li><a href="goods!selectCat.do">添加</a>
							</li>
							<li><a href="javascript:;" id="delBtn">删除</a>
							</li>
							<li><a href="goods!trash_list.do">回收站</a>
							</li>
							
							<li><a href="javascript:;" id="searchBtn">高级搜索</a>
							</li>
						</html:permission>
					</ul>
				</div>
			

			<div id="searchcbox" style="left:10px;width:400px;display:none;position: absolute;background-color: #FFF;border:1px solid #ccc" >
			<div class="input">
			<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
				<tr>
					<th><label class="text">商品名称:</label></th>
					<td><input type="text" style="width: 130px" name="name" value="${name }" /></td>
				</tr>
				<tr>
					<th><label class="text">商品编号:</label></th>
					<td><input type="text" style="width: 130px" name="sn" value="${sn }" /></td>
				</tr>

				<tr>
					<th><label class="text">类别:</label></th>
					<td><div id="searchCat"></div></td>
				</tr>
				
		 
			
				<tr>
					<th><label class="text">品牌:</label></th>
					<td>
					<select name="brand_id" style="width: 130px">
					<option value="">全部品牌</option>
						<c:forEach items="${brandList}" var="brand">
							<option <c:if  test="${brand_id == brand.brand_id }"> selected="selected"</c:if> value="${brand.brand_id}">${brand.name}</option>
						</c:forEach>
					</select>					
					</td>
				</tr>
				
				<tr>
					<th><label class="text"><input type="checkbox" id="tag_chek">标签:</label></th>
					<td>

					<div id="tagspan">
						<select id="tagsel" name="tagids" multiple="multiple">
							<c:forEach items="${tagList}" var="tag">
								<option value="${tag.tag_id }">${tag.tag_name }</option>
							</c:forEach>
						</select>
					</div>
					</td>
				</tr>	
				<tr>
					<th><label class="text">是否上架:</label></th>
					<td>
					<input type="radio" <c:if  test="${is_market == null || is_market == -1 }"> checked="checked" </c:if> name="is_market" value="-1" />全部
					<input type="radio" <c:if  test="${is_market == 1 }"> checked="checked" </c:if> name="is_market" value="1" />是
					<input type="radio" <c:if  test="${is_market == 0 }"> checked="checked" </c:if> name="is_market" value="0" />否					
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
	</form>
	<form id="gridform">
		<grid:grid from="webpage">

			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="sn" width="120px">商品编号</grid:cell>
				<grid:cell sort="name">商品名称</grid:cell>
				<grid:cell sort="cat_id" width="100px">分类</grid:cell>
				<c:if test="${isStoreManager==false}">
					<th>销售价格</th>
				</c:if>
				<grid:cell sort="market_enable">上架</grid:cell>
				<grid:cell sort="brand_id">品牌</grid:cell>
				<grid:cell sort="last_modify">上架时间</grid:cell>
				
				<!-- 
				<grid:cell width="70px">
				<html:permission actid="goods_warn">
				报警设置
				</html:permission>
				</grid:cell>
				 -->
				
				<grid:cell width="50px">操作</grid:cell>
			</grid:header>


			<grid:body item="goods">

				<grid:cell>
					<input type="checkbox" name="id" value="${goods.goods_id }" />${goods.goods_id }</grid:cell>
				<grid:cell>&nbsp;${goods.sn } </grid:cell>
				<grid:cell>&nbsp;
        
        <c:if test="${optype==null || optype=='' }">
						<a href="../../goods-${goods.goods_id }.html"
							target="_blank">${goods.name }</a>
					</c:if>

					<c:if test="${optype!=null &&  optype!='' }">
       	 	${goods.name }
        </c:if>

				</grid:cell>
				<grid:cell>&nbsp;${goods.cat_name} </grid:cell>

				<c:if test="${isStoreManager==false}">
					<td>&nbsp;<fmt:formatNumber value="${goods.price}"
							type="currency" pattern="￥.00" /></td>
				</c:if>

				<grid:cell>&nbsp;<c:if test="${goods.market_enable==0}">否</c:if>
					<c:if test="${goods.market_enable==1}">是</c:if>
				</grid:cell>
				<grid:cell>&nbsp;${goods.brand_name} </grid:cell>

				<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd"
						time="${goods.last_modify}"></html:dateformat>
				</grid:cell>
				<!-- 
				<grid:cell>
					<html:permission actid="goods_warn">
					<input goodsid="${ goods.goods_id }" type="button" name="warn_num" value="报警设置"  style="border: 0 none;cursor: pointer;height: 17px;line-height: 24px;margin: 3px 0 0 3px;"  />
					</html:permission>
				</grid:cell>
				-->
				<grid:cell>


					<c:if test="${optype=='mng' }">
						<a href="javascript:;"><img goodsid="${ goods.goods_id }"
							class="modify sorebtn" src="images/transparent.gif">
						</a>
					</c:if>
					<c:if test="${optype=='ship' }">
						<a href="javascript:;"><img goodsid="${ goods.goods_id }"
							class="modify ship" src="images/transparent.gif">
						</a>
					</c:if>
					<c:if test="${optype=='stock' ||  optype=='alerm'}">
						<a href="javascript:;"><img goodsid="${ goods.goods_id }"
							id="stock_task_img${ goods.goods_id }" class="modify stock"
							src="images/transparent.gif"> </a>
					</c:if>

					<c:if test="${optype== null ||optype=='' }">
						<a href="goods!edit.do?goods_id=${goods.goods_id }"> <img
							class="modify" src="images/transparent.gif">
						</a>
					</c:if>



				</grid:cell>

			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
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
			//<c:if test="${catid!=null}">serachCatSel.val(${catid})</c:if>
		},
		error:function(){
			alert("获取分类树出错");
		}
	});

	$("#tag_chek").click(function(){
		if(this.checked)
			$("#tagspan").show();
		else{
			$("#tagspan").hide();
			$("#tagsel option").attr("selected",false);
		}
	});
});

</script>
