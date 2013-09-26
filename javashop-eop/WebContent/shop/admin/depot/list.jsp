<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
	<div class="toolbar">
		<ul><li>仓库管理</li><li><a href="depot!add.do">添加</a></li></ul>
		<div style="clear:both"></div>
	</div>
	
<table>
	<thead>
		<tr>
		<th>仓库名称</th>
		<th>操作	</th>
		</tr>
		<c:forEach items="${depotList }" var="depot">
		<tr>
			<td>${depot.name }</td>
			<td>
				<a href="depot!edit.do?id=${depot.id }"><img class="modify" src="images/transparent.gif"></a>&nbsp;
				<a href="javascript:;" roomid="${depot.id }"><img class="delete" src="images/transparent.gif"></a>
			</td>
		</tr>
		</c:forEach>
		
	</thead> 
</table>	
<div style="clear:both;padding-top:5px;"></div>
<script>
$(function(){
	$(".delete").click(function(){
		if(confirm("如果删除此仓库，将删除相关所有库存数据，确定删除吗？")){
			$.Loading.show('正在删除仓库，请稍侯...');
			var that =this;
			var roomid = $(this).parent("a").attr("roomid");
			var options = {
				url : "depot!delete.do?ajax=yes&id="+roomid,
				type : "POST",
				dataType : 'json',
				success : function(result) {	
					$.Loading.hide();
					if(result.result==0){
						alert(result.message);
						$(that).parent("a").parent("td").parent("tr").remove();
					}else{
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};
			$.ajax(options);
		}
	});
});
</script>
</div>