var Cart={
		init:function(staticserver){
			
			$.ajaxSetup({ cache:false,		error:function(XMLHttpRequest, textStatus){       
			    
			} });
			
			function changeEvent(input){
				self.numChange(input);
			}
			
			
			$(".Numinput").numinput({name:"num",onChange:changeEvent});	
			var self=this;			
			this.bindEvent();
			self.refreshTotal();
			
		},
		bindEvent:function(){
			var self=this;
			//删除商品
			$("#cart_wrapper .delete").click(function(){
				var cartid = $(this).parents("tr").attr("itemid");
				$.confirm("确定不购买此商品？",
					function(){
						self.deleteGoodsItem(cartid);
					}	
				);
				
			
			});
			

			$("#cart_wrapper .clean_btn").click(function(){

				$.confirm("确认清空？",
					function(){
						self.clean();
					}	
				);
				
				
			});

			
			$("#cart_wrapper .returnbuy_btn").click(function(){
				location.href="index.html";
			});

			$("#cart_wrapper .checkout_btn").click(function(){
				
				if(isLogin){
					location.href="checkout.html";
				}else{
					self.showLoginWarnDlg();					
				}
				
				
			});
			
		},
		showLoginWarnDlg:function(btnx,btny){
			var html = $("#login_tip").html();
			$.dialog({ title:'提示信息',content:html,lock:true,init:function(){
				
				$(".ui_content .btn").jbtn();
				$(".ui_content .to_login_btn").click(function(){
					 location.href="login.html?forward=checkout.html";
				});

				$(".ui_content .to_checkout_btn").click(function(){
					location.href="checkout.html";
				});
				
			}});
			
		}
		,
		
		//删除一个购物项
		deleteGoodsItem:function(itemid){
			var self=this;
			$.Loading.show("请稍候...");
			$.ajax({
				url:"widget?type=shop_cart&ajax=yes&action=delete",
				data:"cartid="+itemid,
				dataType:"json",
				success:function(result){
					if(result.result==1){
						self.refreshTotal();
						
						//如果存在头信息更新相应数据
						if(typeof CartBar != "undefined"){
							CartBar.loadNum();
						}
						
						self.removeItem(itemid);
					}else{
						$.alert(result.message);
					}	
					$.Loading.hide();
				},
				error:function(){
					$.Loading.hide();
					$.alert("出错了:(");
				}
			});
		},
		
		
		removeItem:function(itemid){
			$("#cart_wrapper tr[itemid="+itemid+"]").remove();
		}
		,
		clean:function(){
			$.Loading.show("请稍候...");
			var self=this;
			$.ajax({
				url:"widget?type=shop_cart&ajax=yes&action=clean",
				dataType:"json",
				success:function(result){
					$.Loading.hide();
					if(result.result==1){
						location.href='cart.html';
					}else{
						$.alert("清空失败:"+result.message);
					}				 
				},
				error:function(){
					$.Loading.hide();
					$.alert("出错了:(");
				}
			});		
		}
		,numChange:function(input){
			
			var tr = input.parents("tr");
			var itemid = tr.attr("itemid");
			var productid = tr.attr("productid");
			var num= input.val(); 
			this.updateNum(itemid, num,productid);
		},
		updateNum:function(itemid,num,productid){
			var self = this;
			$.ajax({
				url:"widget?type=shop_cart&ajax=yes&action=update",
				data:"cartid="+itemid +"&num="+num +"&productid="+productid,
				dataType:"json",
				success:function(result){
					if(result.result==0){
						if(result.store>=num){
						self.refreshTotal();
						var price = parseFloat($("tr[itemid="+itemid+"]").attr("price"));
						//price =price* num;
						price =self.changeTwoDecimal_f(price* num);
						$("tr[itemid="+itemid+"] .itemTotal").html("￥"+price);
						}else{
							input.val(result.store);
							alert("抱歉！您所选选择的货品库存不足。");
						}
					}else{
						alert("更新失败");
					}
				},
				error:function(){
					alert("出错了:(");
				}
			});		
		},
		
		/**
		 * 刷新价格
		 */
		refreshTotal:function(){
		//	alert("refresh total");
			$.ajax({
				url:"cart.html?widgetid=cart&ajax=yes&action=getTotal&rmd="+new Date().getTime(),
				dataType:"html",
				success:function(totalHtml){
				 
					$("#cart_wrapper .total_wrapper").html(totalHtml);
				},
				error:function(){
					//alert("出错了:(");
				}
			});			
		},
		changeTwoDecimal_f:function(x) {
	        var f_x = parseFloat(x);
	        if (isNaN(f_x)) {
	            alert('参数为非数字，无法转换！');
	            return false;
	        }
	        var f_x = Math.round(x * 100) / 100;
	        var s_x = f_x.toString();
	        var pos_decimal = s_x.indexOf('.');
	        if (pos_decimal < 0) {
	            pos_decimal = s_x.length;
	            s_x += '.';
	        }
	        while (s_x.length <= pos_decimal + 2) {
	            s_x += '0';
	        }
	        return s_x;
	    }
};

$(function(){
	Cart.init();
});