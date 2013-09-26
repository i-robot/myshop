var Order={
	init:function(){
		var self = this;
		$(".cancelBtn").click(function(){
			var sn = $(this).attr("sn");
			var dialog = $.dialog({
				title:"请输入取消原因",width:400,lock:true
			});

		 
			$.ajax({
			    url:'cancel_order.html?widgetid=/cancel_order.html&action=cancel&sn='+sn+"&ajax=yes",
			    success:function(html){
			    	dialog.content(html);
			    	$(".ui_content .btn").jbtn().click(function(){
			    		self.cancelOrder();
			    	});
			    },
			    cache:false
			});
		});
		
		
		$(".rogBtn").click(function(){
			var orderId = $(this).attr("orderid");
			if( confirm( "请您确认已经收到货物再执行此操作！" ) ){
				$.Loading.show("请稍候..."); 
				$.ajax({
					url:"widget.do?type=member_order&ajax=yes&action=confirmRog&orderId="+orderId+"&rmd="+new Date().getTime(),
					dataType:"json",
					success:function(result){
						if(result.result==1){
							location.reload();
						}else{
							 
							$.alert(result.message);
							$.Loading.hide();
						}
						
					},
					error:function(){
						$.alert("出错了:(");
					}
				});	
						
			}
			
		});
		
				
		$(".thawBtn").click(function(){
			var orderid = $(this).attr("orderid");
			$.confirm("提前解冻积分后，被冻结积分相关的订单商品，将不能进行退换货操作。确认要解冻吗？",
				function(){
					$.Loading.show("请稍候..."); 
					$.ajax({
						url:"widget.do?type=member_order&ajax=yes&action=thaw&orderid="+orderid,
						dataType:"json",
						success:function(result){
							if(result.result==1){
								location.reload();
							}else{
								$.alert(result.message);
							}
						},error:function(){
							$.alert("抱歉，解冻出错现意外错误");
						}
					});
				}	
			);
		
		});
		
	},
	 cancelOrder:function(){
		
		$.Loading.show("正在取消您的订单，请稍候..."); 
		var options = {
				url : "widget?type=member_order&action=savecancel&ajax=yes",
				type : "POST",
				dataType : 'json',
				success : function(result) {
	 				if(result.result==1){
		 				 location.reload();
		 			}else{
		 				$.Loading.hide();
		 				$.alert(result.message);
			 		} 
				},
				error : function(e) {
					$.Loading.hide();
					$.alert("出现错误 ，请重试");
				}
		};
		
		$("#cancelForm").ajaxSubmit(options);
		
	}
}