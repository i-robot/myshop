<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.pluginList,.widgetList{
	width:300px;

}
.pluginList li,.widgetList li{ width:250px; background-color: #FFFFCC;text-align:left;margin-left:10px}
.imgTree{cursor: pointer;margin-right:5px}
-->
</style>
<div class="grid">
	
	<div class="toolbar" >
		<div style="width: 100%; float: left; height: 25px;">
		  组件管理
		</div>
		<div style="clear: both"></div>
	</div>

	<form method="POST">
		<grid:grid from="componentList">

			<grid:header>
				<grid:cell width="250px">名称</grid:cell>
				<grid:cell width="150px">安装状态</grid:cell>
				<grid:cell width="150px">启用状态</grid:cell>
				<grid:cell >操作</grid:cell>
			</grid:header>

			<grid:body item="component">
				<grid:cell>
				<b><img class="imgTree"   src="images/sitemapclosed.gif" status="close">${component.name }</b>
				
				<div class="pluginList" style="display:none">
					<ul>
						<c:forEach items="${component.pluginList }" var="plugin">
							<li>${plugin.name }</li>						
						</c:forEach>
					</ul>
				</div>
				
				<div class="widgetList" style="display:none">
					<ul>
						<c:forEach items="${component.widgetList }" var="widget">
							<li>${widget.name }</li>						
						</c:forEach>
					</ul>
				</div>
				
				</grid:cell>
				
				<grid:cell>
					<c:if test="${component.install_state==1 }">已安装</c:if>
					<c:if test="${component.install_state==0 }">未安装</c:if>
					<c:if test="${component.install_state==2 }">错误:${component.error_message }</c:if>
				</grid:cell>
				
				
				<grid:cell>
					<c:if test="${component.enable_state==1 }">已启用</c:if>
					<c:if test="${component.enable_state==0 }">已停用</c:if>	
					<c:if test="${component.enable_state==2 }">错误</c:if>			
				</grid:cell>
				
				<grid:cell>
					<c:if test="${component.enable_state ==2}">${ component.error_message}</c:if>
				
					<c:if test="${component.install_state==1 }"> 
						<c:if test="${component.enable_state==0 }">
							<a componentid="${component.componentid }" class="start"  href="javascript:;">启用</a>&nbsp;&nbsp;
							<a componentid="${component.componentid }" class="uninstall"  href="javascript:;">卸载</a>
						</c:if>		
						<c:if test="${component.enable_state==1 }">
							<a componentid="${component.componentid }" class="stop" href="javascript:;">停用</a>
						</c:if>	
					</c:if>
					<c:if test="${component.install_state==0 }"> 
						<a componentid="${component.componentid }" class="install" href="javascript:;">安装</a>
					</c:if>
				</grid:cell>
				
			
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>

<script>

function request(requrl){
	$.ajax({
		url:requrl+"&ajax=yes",
		dataType:"json",
		success:function(result){
			if(result.result==1){
				alert("操作成功"); 
				location.reload();
			}else{
				alert(result.message);
			}
		},error:function(){
			alert("启动组件出现错误");
		}
	});
}
$(function(){
	
	$(".imgTree").click(function(){
		var $this = $(this);
		var status = $this.attr("status");
		if(status=='close')  {
			$this.parent().next().show().next().show();
			$this.attr("status","open");
			$this.attr("src","images/sitemapopened.gif");
			
		}else if(status=='open'){
			$this.attr("status","close");
			$this.attr("src","images/sitemapclosed.gif");
			$this.parent().next().hide().next().hide();
		}
			
	});
	
	
	$(".start").click(function(){
		request("component!start.do?componentid="+$(this).attr("componentid") );
	});
	$(".stop").click(function(){
		if(confirm( "确认停用此组件吗?" )){
			request("component!stop.do?componentid="+$(this).attr("componentid") );
		}
		
	});
	
	$(".install").click(function(){
		request("component!install.do?componentid="+$(this).attr("componentid") );
	});
	
	$(".uninstall").click(function(){
		
		if(confirm( "确认卸载此组件吗?此操作可能会破坏现有数据，且无法恢复!" )){
			request("component!unInstall.do?componentid="+$(this).attr("componentid") );
		}
		
	});
	
});
</script>