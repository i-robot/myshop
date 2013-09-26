<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Comments.js"></script>
<style>
*{margin:0;padding:0; font-weight:inherit;font-style:inherit;font-size:100%;font-family:inherit;vertical-align:baseline;}
td, th {
padding:1px 4px;
}
.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}

tableform h2 span{color:#777;font-weight:normal;}
.tableform h3 span{font-weight:normal;float:right;}
.tableform h3 .assis{font-weight:normal;color:#000;}
.tableform h3 span a{color:#777;font-weight:normal;}
.division {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
width: 1236px;
word-wrap: break-word;

}
.tableform .division td a{padding:2px 5px 0 5px;margin:0 3px;color:#000;line-height:20px;}
.tableform .intro{color:#369;padding-left:15px;}
</style>
<script type="text/javascript">

$.Validator.options={lang:{required:'必须!'}};
$(function(){
	
$("#comments_commit").click(function(){
	if( $.trim( $("#recomment").val())=='' ){alert("请输入回复内容"); return false;}
	$("#comments_commit").attr("disabled",true); 
	$.Loading.show('正在回复留言，请稍侯...');
	var options = { 
			url : "comments!add.do?ajax=yes",
			type : "POST",
			dataType : 'json',
			success : function(result) {
				$.Loading.hide();				
 				if(result.state==0){
					$("#comments_commit").attr("disabled",false); 
					$("#commentscontent").html("");
					location.reload();
		 		}else{
			 		alert(result.message);
			 		$("#comments_commit").attr("disabled",false); 
			 	}
			},
			error : function(e) {
				$.Loading.hide();
				alert("出现错误 ，请重试");
				$("#comments_commit").attr("disabled",false); 
			}
	};

	$('#addcomment').ajaxSubmit(options);	
});


$(".hideLink").click(function(){
	var comment_id = $(this).attr("comment_id");
	$.ajax({
		url:'comments!hide.do?ajax=yes&comment_id='+comment_id,
		dataType:'json',
		success:function(result){
			if(result.result==0){
				alert("操作成功");
				parent.ShortMsg.checkNewMsg();
				location.reload();
			}
			
		}
	});
});


$(".showLink").click(function(){
	var comment_id = $(this).attr("comment_id");
	$.ajax({
		url:'comments!show.do?ajax=yes&comment_id='+comment_id,
		dataType:'json',
		success:function(result){
			if(result.result==0){
				alert("操作成功");
				parent.ShortMsg.checkNewMsg();
				location.reload();
			}
			
		}
	});
});

$(".deleteLink").click(function(){
	var comment_id = $(this).attr("comment_id");
	$.ajax({
		url:'comments!deletealone.do?comment_id='+comment_id,
		dataType:'html',
		success:function(result){
			 
			parent.ShortMsg.checkNewMsg();
				location.reload();
			 
			
		}
	});
});

//

});


</script>
<div class="input">
<br/>
<table>
	<tr>
		<td class="tableform">
			<h5>评论人：
			<c:if test="${member != null}">${member.uname}</c:if>
			<c:if test="${member == null}">游客</c:if>
			</h5>
			<h5>发表时间：[<html:dateformat pattern="yyyy-MM-dd HH:mm" time="${memberComment.dateline }"></html:dateformat>]</h5>
			<h5>当前状态：<c:if test="${memberComment.status == 0 }">未审核</c:if>
        	<c:if test="${memberComment.status == 1 }">审核通过</c:if>
        	<c:if test="${memberComment.status == 2 }">审核拒绝</c:if></h5>
			<h5>操作：
				<span>
					[<a href="comments!deletealone.do?comment_id=${memberComment.comment_id }" onclick="javascript:return confirm('您确认要删除此条信息？');">删除</a>]
					<c:if test="${memberComment.status == 0 }">[<input type="button" class="chide"  comment_id="${memberComment.comment_id }" value="拒绝" />
        			<input type="button" class="cshow"  comment_id="${memberComment.comment_id }" value="通过" />]
        			</c:if>
				</span>
			</h5>
				<div class="division">
				${memberComment.content }
				<c:if test="${memberComment.img != null && memberComment.img != ''}">
				<br/>
				<a href="${memberComment.imgPath}" target="_blank"><img src="${memberComment.imgPath}" width="500"/></a>
				</c:if>
				</div>
			<c:if test="${memberComment.replystatus==0}">	
				<div class="division">
				<form  method="post" action="comments!add.do" class="validate" id="addcomment">
				<table>
					<tr>
						<th>
							<input type="hidden" name="comment_id" value="${memberComment.comment_id }" />
							<h5>回复用户评论：</h5>
							<textarea rows="7" cols="70" id="recomment" name="reply" style="border: solid #069 1px;" id="commentscontent"></textarea>
						</th>
					</tr>
					
				</table>
				<div class="submitlist" align="center">
				 <table>
				 <tr><td >
				  <a href="javascript:;" id="comments_commit">回复</a>
				   </td>
				   </tr>
				 </table>
				</div>
			</form>
			</div>
			</c:if>
			<c:if test="${memberComment.replystatus==1}">
			<div class="tableform">
				<h5>管理员于[<html:dateformat pattern="yyyy-MM-dd HH:mm" time="${memberComment.replytime }"></html:dateformat>]回复：</h5>
				<div class="division"> 
				${memberComment.reply}
				</div>
			</div>
			</c:if>
		</td>
	</tr>
</table>
</div>
