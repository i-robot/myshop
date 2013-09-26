<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
div{display:block}
.cate-wrapper{width:100%;padding:10px;margin:auto;
}
#cate-container {
	border: 1px solid #D5E4FA;
	background: #F5F9FF;
	height: 298px;
}
.cate-wrapper  .nav{
	font-size:14px; background: none repeat scroll 0 0 #F1F7FD;
    font-weight: bold;
    height: 25px;
    overflow: hidden;
    padding: 10px 0 5px 10px;
    text-align: left;}
#cate-container .cat_box {
	display: block;
	float: left;
	margin-left: 5px;
	width: 223px;
	border-right: 2px solid #D5E4FA;
	overflow-y: auto;
	overflow-x: hidden;
	height: 100%
}

#cate-container .cat_box li {
	padding: 0 16px 0 14px;
	cursor: pointer;
	height: 20px;
	line-height: 20px;
	overflow: hidden;
}

#cate-container li.selected {
	border: 1px solid #9CD7FC;
	background-color: #DFF1FB;
}

#cate-path{
	color: #444;
	padding-top: 9px;
	position: relative;
	line-height: 22px;
	height: 54px;
	display:block;
}

#cate-path dl {
background-color: #FFFAF2;
border: 1px solid #FEDBAB;
padding: 4px 27px; 
}

#cate-path dt, #cate-path dd {
	float: left;
	 margin: 0;
    padding: 0;
}
 
#cate-path dt {
font-weight: 900;
}

#cate-path li {
float: left;
 margin: 0;
    padding: 0;
}

ul, ol {
 list-style: none outside none;
  margin: 0;
    padding: 0;
}

.clearfix { 
	display: block;
	height: 0;
	clear: both;
	height:22px
}
#cateBottom{

padding: 5px 0 24px;
text-align: center;
    
}
</style>

<div class="cate-wrapper">
<div class="nav">选择分类</div>
<div id="cate-container">
 
</div>

<div id="cate-path">
    <dl>
        <div class="clearfix">
            <dt>您当前选择的是：</dt>
            <dd><ol class="category-path" ></ol></dd>
        </div> 
     </dl>

</div>

<div id="cateBottom">

<input type="button" value="下一步" id="nextBtn" disabled="disabled" />
</div>
</div>
<script>
 

var CatLoader={
 
	init:function(){
		var self = this;
		$("#nextBtn").attr("disabled",true);
		this.loadChildren(0);
		
		$("#nextBtn").click(function(){
			var catid  = self.getSelectedCatId();
			if(!catid){
				alert("请选择商品的分类");
				
			}else{
				location.href="goods!add.do?catid="+catid;
			}
			
		});
	},
	loadChildren:function(catid){
		var self = this;
		$.ajax({
			url:'cat!getChildJson.do?ajax=yes&cat_id='+catid, 
			dataType:'json',
			success:function(result){
				if(result.result==1){
					self.appendCatList(result.data,catid);
					self.refreshPath();
				}else{
					alert("加载分类出错["+result.message+"]");
				}
			},error:function(){
				alert("加载分类出错");
			}
			
		});
	}
	,
	appendCatList:function(catJson,catid){
		if(catJson.length==0) return ;
		var self= this;
		var selHtml ="<div class='cat_box'  id='box_"+catid+"'>";
		$.each(catJson,function(i,cat){
			selHtml+="<li catid="+cat.cat_id+" >";
			selHtml+=cat.name;
			selHtml+="</li>";
		});
		selHtml+="</div>";
		$(selHtml).appendTo( $("#cate-container") )
		.find("li").click(function(){
			
			var $this= $(this);
			self.removeChildBox( $this.parent().attr("id")  );
			self.loadChildren($this.attr("catid"));
			
			$this.siblings().removeClass("selected");
			$this.addClass("selected");
			
			$("#nextBtn").attr("disabled",false);
		});
	 
	},
	removeChildBox:function(boxid){
		var flag= false;
		$("#cate-container .cat_box").each(function(){
			var $this= $(this);
			if(flag){
				$this.remove();
			}
			if($this.attr("id")== boxid){
				flag = true;
			}
		});
	},
	refreshPath:function(){
		var pathbox =$("#cate-path .category-path").empty();
		var html ="";
		$("#cate-container li.selected").each(function(i,v){
			if(i!=0) html+="<li>&nbsp;&gt;&nbsp;</li>";
			var name = $(this).text();
			html+=("<li>"+name+"</li>"); 
		});
		pathbox.append(html);
	},
	getSelectedCatId:function(){
		var catEl=  $("#cate-container li.selected:last");
		if(catEl.length==0){
			return false;
		}else{
			return catEl.attr("catid");
		}
		
	}
		
};



$(function(){
	CatLoader.init();
});
</script>