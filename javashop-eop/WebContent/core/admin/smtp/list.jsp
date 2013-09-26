<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Smtp.js"></script>

<div class="grid">
	
	<div class="toolbar" >
		<div style="width: 100%; float: left; height: 25px;">
			<ul>
				<li><a href="smtp!add.do">添加</a></li>
				<li><a href="javascript:;" id="delBtn">删除</a></li>
			</ul>
		</div>
		<div style="clear: both"></div>
	</div>

	<form method="POST">
		<grid:grid from="smtpList">

			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox"  id="toggleChk" />
				</grid:cell>
				<grid:cell width="250px">host</grid:cell>
				<grid:cell width="200px">用户名</grid:cell>
				<grid:cell >密码</grid:cell>
				<grid:cell >今日发信数</grid:cell>
				<grid:cell >最大发信数</grid:cell>
				<grid:cell >from</grid:cell>
				<grid:cell width="100px">操作</grid:cell>
			</grid:header>

			<grid:body item="smtp">
				<grid:cell>
					<input type="checkbox" name="idAr" value="${smtp.id }" />${smtp.id  }
				</grid:cell>
				<grid:cell>${smtp.host} </grid:cell>
				<grid:cell>${smtp.username}</grid:cell>
				<grid:cell>${smtp.password} </grid:cell>
				<grid:cell>${smtp.send_count}</grid:cell>		
				<grid:cell>${smtp.max_count}</grid:cell>	
				<grid:cell> 
					 ${smtp.mail_from }
				</grid:cell>							
				<grid:cell>
					<a href="smtp!edit.do?id=${smtp.id }"><img class="modify" src="images/transparent.gif"> </a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>