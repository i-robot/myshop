var OrderStatus={};

//订单状态
OrderStatus.ORDER_CHANGED=-7;//已换货
OrderStatus.ORDER_CHANGE_REFUSE=-6;//换货被拒绝
OrderStatus.ORDER_RETURN_REFUSE=-5;//退货被拒绝
OrderStatus.ORDER_CHANGE_APPLY=-4;//申请换货	
OrderStatus.ORDER_RETURN_APPLY=-3; // 申请退货
OrderStatus.ORDER_CANCEL_SHIP = -2; // 退货
OrderStatus.ORDER_CANCEL_PAY = -1; // 退款
OrderStatus.ORDER_NOT_PAY = 0; // 未付款
OrderStatus.ORDER_PAY = 1; // 已支付待确认
OrderStatus.ORDER_PAY_CONFIRM = 2; // 已确认支付
OrderStatus.ORDER_ALLOCATION  = 3; //配货中
OrderStatus.ORDER_ALLOCATION_YES  =4; //配货完成，待发货
OrderStatus.ORDER_SHIP = 5; // 已发货
OrderStatus.ORDER_ROG = 6; // 已收货
OrderStatus.ORDER_COMPLETE = 7; // 已完成
OrderStatus.ORDER_CANCELLATION =8; // 作废
OrderStatus.ORDER_CONFIRM = 9; //订单已确认

//付款状态
OrderStatus.PAY_NO= 0;   //未付款
OrderStatus.PAY_YES= 1; //已付款待确认
OrderStatus.PAY_CONFIRM= 2; //已确认付款
OrderStatus.PAY_CANCEL =3; //已经退款
OrderStatus.PAY_PARTIAL_REFUND = 4; //部分退款
OrderStatus.PAY_PARTIAL_PAYED =5 ;//部分付款


//货运状态
OrderStatus.SHIP_ALLOCATION_NO= 0;  //	0未配货
OrderStatus.SHIP_ALLOCATION_YES= 1;  //	1配货中
OrderStatus.SHIP_NO= 2;  //	0未发货
OrderStatus.SHIP_YES= 3;//	1已发货
OrderStatus.SHIP_CANCEL= 4;//	2.已退货
OrderStatus.SHIP_PARTIAL_SHIPED= 5; //	4 部分发货
OrderStatus.SHIP_PARTIAL_CANCEL= 6;// 3 部分退货	
OrderStatus.SHIP_PARTIAL_CHANGE= 7;// 5部分换货	
OrderStatus.SHIP_CHANED= 8;// 6已换货	



var OrderDetail ={
		orderid : undefined,
		orderStatus:undefined,
		payStatus:undefined,
		shipStatus:undefined,
		isCod:false,
		init:function(orderid,orderStatus,payStatus,shipStatus,isCod){
		//alert(orderid);
			//初始化订单的状态
			this.orderStatus = orderStatus;
			this.payStatus = payStatus;
			this.shipStatus = shipStatus;
			this.isCod = isCod;
			
			Eop.Dialog.init({id:"order_dialog",modal:false,title:"订单操作",width:"800px"});
			
			var self = this; 
			this.orderid = orderid;
			new Tab(".order_detail");
			this.bindFlowEvent();
		},
		
		/**
		 * 绑定订单流程按钮事件
		 */
		bindFlowEvent:function(){
			var self =this;
			$("#order_dialog .submitBtn").attr("disabled",false); 
			//付款事件绑定 确认付款
			$("#pay").unbind("click");
			$("#pay").bind("click",function(){
				Eop.Dialog.open("order_dialog");
				
				$("#order_dialog .con").load(basePath+"payment!showPayDialog.do?ajax=yes&orderId="+self.orderid,function(){
					$("#order_dialog .submitBtn").unbind("click");
					$("#order_dialog .submitBtn").bind("click",function(){
						self.pay(self.orderid);
					});
				}); 
				
			});
	
			
			
			//退款事件绑定
			$("#refund").unbind("click");
			$("#refund").bind("click",function(){
				Eop.Dialog.open("order_dialog");
				$("#order_dialog .con").load(basePath+"payment!showRefundDialog.do?ajax=yes&orderId="+self.orderid,function(){
					$("#pay_date").datepicker();
					$("#onlineRdio").unbind("click");
					$("#onlineRdio").bind("click",function(){
						$(".online_box").show().find("input,select").attr("disabled",false);
						$(".offline_box").hide().find("input,select").attr("disabled",true);
					});
					
					$("#offlineRdio").unbind("click");
					$("#offlineRdio").bind("click",function(){
						$(".offline_box").show().find("input,select").attr("disabled",false);
						$(".online_box").hide().find("input,select").attr("disabled",true);
					});
					
					
					
					$("#order_dialog .submitBtn").unbind("click");
					$("#order_dialog .submitBtn").bind("click",function(){
						self.refund();
					});
				}); 
			});

			//配货事件绑定
			$("#allocation").unbind("click");
			$("#allocation").bind("click",function(){
				Eop.Dialog.open("order_dialog");
				$("#order_dialog .con").load(basePath+"ship!showAllocationDialog.do?ajax=yes&orderId="+self.orderid,function(){
					
					$("#allo_box .opbtn").click(function(){
						var box = $(this).parents("tr").next().find(".allo_items");
						var itemid = $(this).attr("itemid");
						self.loadProductStore(box, itemid);
						
					});
					
					$("#order_dialog .submitBtn").unbind("click");
					$("#order_dialog .submitBtn").bind("click",function(){
						 self.allocation();
					});
					
				}); 
			});			
			
			
			
			//发货事件绑定
			$("#shipping").unbind("click");
			$("#shipping").bind("click",function(){
				Eop.Dialog.open("order_dialog");
				$("#order_dialog .con").load(basePath+"ship!showShipDialog.do?ajax=yes&orderId="+self.orderid,function(){
					$("#order_dialog .submitBtn").unbind("click");
					$("#order_dialog .submitBtn").bind("click",function(){
						 self.ship();
					});
					
					$(".view_allo_btn").click(function(){
						var itemid = $(this).attr("itemid");
						$(this).next().show().find(".all_content").load(basePath+"ship!viewProductStore.do?ajax=yes&itemid="+itemid);
					});	
					
					$("#order_dialog .close").click(function(){
						$(this).parent().parent().hide();
					});
					
					
				}); 
			});			
			
			//退货事件绑定
			$("#returned").unbind("click");
			$("#returned").bind("click",function(){
				Eop.Dialog.open("order_dialog");
				$("#order_dialog .con").load(basePath+"ship!showReturnDialog.do?ajax=yes&orderId="+self.orderid,function(){
				 
					$("#order_dialog .submitBtn").unbind("click");
					$("#order_dialog .submitBtn").bind("click",function(){
						 self.returned();
					});
				}); 
			});	
			
			//换货事件绑定
			$("#changed").unbind("click");
			$("#changed").bind("click",function(){
				Eop.Dialog.open("order_dialog");
				$("#order_dialog .con").load(basePath+"ship!showChangeDialog.do?ajax=yes&orderId="+self.orderid,function(){
					$("#order_dialog .submitBtn").unbind("click");
					$("#order_dialog .submitBtn").bind("click",function(){
						 self.changed();
					});
				}); 
			});	
						
			

			//完成事件绑定
			$("#complete").unbind("click");
			$("#complete").bind("click",function(){
				if(confirm("完成 操作会使该订单归档且不允许再做任何操作，确定要执行吗？")){
					self.complete(self.orderid);
				}
			});	
			
			//作废事件绑定
			$("#cancel").unbind("click");
			$("#cancel").bind("click",function(){
				if(confirm("作废操作会使该订单归档且不允许再做任何操作，确定要执行吗？")){
					self.cancel(self.orderid);
				}
			});	
			
			//确认订单绑定
			$("#confirmorder").unbind("click");
			$("#confirmorder").bind("click",function(){
				if(confirm("确认要确认此订单吗？")){
					self.confirmOrder(self.orderid);
				}
			});
			
			this.initBtnStatus();
		}
		,
		/**
		 * 加载一个配货项的输入页面
		 * @param box
		 * @param itemid
		 */
		loadProductStore:function(box,itemid){
			box.load(basePath+"ship!getProductStore.do?ajax=yes&itemid="+itemid ,function(){
				//通过input.depotid样式找到点击的配货仓库复选框
				$(this).find("input.depotid").click(function(){
					var li = $(this).parent();
					if( parseInt( li.attr("store") ) == 0  ) {
						alert("此仓库库存为0，不能配货");
						return false;
					}
					li.find("input[type=text],input[type=hidden]").attr("disabled",!this.checked);
					
					if(this.checked){
						li.addClass("selected");
					}else{
						li.removeClass("selected");
					}
					
				});
			});
		},
		/**
		 * 检测配货情况是否合法
		 * @returns {Boolean}
		 */
		checkAlloNum:function(){
			var result =true;
			if($("#depotid").val() == 0){
				alert("请选择发货仓库");
				result=false;
				return;
			}
			$(".allo_items").each(function(i,v){
				
				var item = $(this);				
				var num = parseInt(item.attr("num")); //此商品需发货的数量
				var goodsname = item.attr("goodsname"); //此商品名称
				var goods_allo_num =0 ; //本商品的配货量
				//检测这个商品的配货情况
				item.find("ul:first>li.selected").each(function(i,v){
					var li = $(this);
					var depotname= li.attr("depotname"); //库房名称
					var store = parseInt( li.attr("store") );//库存
					var allo_num = parseInt( li.find("input.num").val() ); //配货数量
					if(allo_num>store){ //配货量大于库存
						alert("商品["+goodsname+"]配货仓库["+depotname+"]库存不足->库存["+store+"]配货量["+allo_num+"]");
						result=false;
						return;
					}else{
						goods_allo_num+=allo_num;
					}
					
				});
				
				if(result && goods_allo_num!=num){ //配货量不等于本商品发货量
					alert("商品["+goodsname+"]配货量不等于发货量->发货量["+num+"]配货量["+goods_allo_num+"]");
					result=false;
					return ;
				}
			});
			
			
			
			return result;
			
		}
		,
		/**
		 * 初始化按钮状态
		 */
		initBtnStatus:function(){
			
			//1.订单为货到付款时：已收货后才可用
			//2.非货到付款时：订单已支付待确认才可用
			if( this.orderStatus == OrderStatus.ORDER_PAY || (this.isCod && this.orderStatus == OrderStatus.ORDER_SHIP)){
				$("#pay").attr("disabled",false); 
			}else{
				$("#pay").attr("disabled",true); 
			}
			
			//已确认付款退款按钮可用		
			//对于货到到付款来说，若状态为已退货，则退款按钮可用
			if(   this.payStatus == OrderStatus.PAY_CONFIRM  ||(this.orderStatus == OrderStatus.ORDER_CANCEL_SHIP && this.isCod==true)
			   )
			{ 
				$("#refund").attr("disabled",false); 
			}else
			{
				$("#refund").attr("disabled",true); 
			}
			
			
			//货到付款 订单确认才可用
			//非货到付款 付款已确认
			if(this.orderStatus == OrderStatus.ORDER_CONFIRM || (this.isCod==false && this.orderStatus == OrderStatus.ORDER_PAY_CONFIRM)){
				$("#allocation").attr("disabled",false); 
			}else{
				$("#allocation").attr("disabled",true); 
			}
					
			
			//配货中或部分已发货发货按钮可用，其它情况（已发货、部分退货、已退货）发货按钮禁用
			//if( this.shipStatus == OrderStatus.SHIP_ALLOCATION_YES ||   this.shipStatus == OrderStatus.SHIP_PARTIAL_SHIPED){
			if(this.orderStatus == OrderStatus.ORDER_ALLOCATION_YES ||   this.shipStatus == OrderStatus.SHIP_PARTIAL_SHIPED){
				$("#shipping").attr("disabled",false); 
			}else{
				$("#shipping").attr("disabled",true); 
			}
			
			//已发货 或 部分已发货 或部分已退货 退货按钮可用
			//其它状态都禁用(未发货、已换货)	禁用按钮		
			if(  this.shipStatus == OrderStatus.SHIP_YES || this.shipStatus == OrderStatus.SHIP_PARTIAL_SHIPED || this.shipStatus == OrderStatus.SHIP_PARTIAL_CANCEL){ 
				$("#returned").attr("disabled",false); 
			}else{
				$("#returned").attr("disabled",true); 
			}
			
			//已发货 或 部分已发货 或部分已退货 换货按钮可用
			//其它状态都禁用(未发货、已退货,已换货)	禁用按钮		
			if(  this.shipStatus == OrderStatus.SHIP_YES || this.shipStatus == OrderStatus.SHIP_PARTIAL_SHIPED || this.shipStatus == OrderStatus.SHIP_PARTIAL_CANCEL){ 
				$("#changed").attr("disabled",false); 
			}else{
				$("#changed").attr("disabled",true); 
			}
			
						
			
			//订单状态为完成或作废，则禁用 所有钮
			if(this.orderStatus == OrderStatus.ORDER_COMPLETE ||this.orderStatus == OrderStatus.ORDER_CANCELLATION ){
				$(".toolbar input").attr("disabled",true); 
				$("#nextorder input").removeAttr("disabled"); 
			}else{
				$("#cancel").attr("disabled",false);  
				$("#complete").attr("disabled",false);  				
			}
			
			//确认订单
			//订单付款方式为货到付款（支付方式id为2）时且订单状态为未付款
			if(this.isCod && this.orderStatus == OrderStatus.ORDER_NOT_PAY){
				$("#confirmorder").attr("disabled",false);
			}else{
				$("#confirmorder").attr("disabled",true);
			}
		}
		
		,
		
		/**
		 * 支付
		 */
		pay:function(orderId){
			var self= this;
			var options = {
					url:basePath+"payment!pay.do?ajax=yes&orderId"+orderId,
					type:"post",
					dataType:"json",
					success: function(responseText) { 
						if(responseText.result==1){
							alert(responseText.message);
							Eop.Dialog.close("order_dialog");
							self.refresh(responseText);
						}
						if(responseText.result==0){
							alert(responseText.message);
						}						
					},
					error:function(){
						alert("出错了:(");
					}
			};
			$('#order_form').ajaxSubmit(options); 
		},
		
		/**
		 * 退款
		 */
		refund:function(){
			var self= this;
			var options = {
					url:basePath+"payment!cancel_pay.do?ajax=yes",
					type:"post",
					dataType:"json",
					success: function(responseText) { 
						if(responseText.result==1){
							alert(responseText.message);
							Eop.Dialog.close("order_dialog");
							self.payStatus = responseText.payStatus;
							self.bindFlowEvent();
						}
						if(responseText.result==0){
							alert(responseText.message);
						}						
					},
					error:function(){
						alert("出错了:(");
					}
			};
			$('#order_form').ajaxSubmit(options); 
		},
		
		
		/**
		 * 添加付款记录
		 */
		paylog:function(){
			var self= this;
			var options = {
					url:basePath+"payment!pay_log.do?ajax=yes",
					type:"post",
					dataType:"json",
					success: function(responseText) {
						if(responseText.result==1){
							alert(responseText.message);
							$("#order_dialog .con").load(basePath+"payment!showPayDialog.do?ajax=yes&orderId="+self.orderid,function(){
								$("#order_dialog .submitBtn").unbind("click");
								$("#order_dialog .submitBtn").bind("click",function(){
									self.pay(self.orderid);
								});
							});
						}
						if(responseText.result==0){
							alert(responseText.message);
						}						
					},
					error:function(){
						alert("出错了:(");
					}
			};
			$('#order_form').ajaxSubmit(options); 
		},
		
		refresh:function(responseText){
			var self=this;
			
			self.orderStatus = responseText.orderStatus;
			self.payStatus = responseText.payStatus;
			self.shipStatus = responseText.shipStatus;
			self.bindFlowEvent();
		},
		/**
		 * 配货
		 */
		allocation:function(){
			var self= this;
			
			if( !self.checkAlloNum() ){
				
				return ;
			}
			$("#order_dialog .submitBtn").attr("disabled",true);
			var options = {
					url:basePath+"ship!allocation.do?ajax=yes",
					type:"post",
					dataType:"json",
					success: function(responseText) { 
						if(responseText.result==1){
							alert(responseText.message);
							Eop.Dialog.close("order_dialog");
							self.refresh(responseText);
						}
						if(responseText.result==0){
							alert(responseText.message);
						}						
					},
					error:function(){
						alert("出错了:(");
					}
			};
			$('#order_form').ajaxSubmit(options); 
		}
		,
		/**
		 * 发货
		 */
		ship:function(){
			$("#order_dialog .submitBtn").attr("disabled",true); 
			var self= this;
			var options = {
					url:basePath+"ship!ship.do?ajax=yes",
					type:"post",
					dataType:"json",
					success: function(responseText) { 
						if(responseText.result==1){
							alert(responseText.message);
							Eop.Dialog.close("order_dialog");
							self.refresh(responseText);
						}
						if(responseText.result==0){
							alert(responseText.message);
						}						
					},
					error:function(){
						alert("出错了:(");
					}
			};
			$('#order_form').ajaxSubmit(options); 
		},
		/**
		 * 退货
		 */
		returned:function(){
			var flag =true;
			$("input[name=numArray]").each(function(i,v){
				if($.trim( v.value )==''){
					flag =false;
				}else{
					if(!isdigit(v.value) ){
						flag=false;
					}else if(parseInt(v.value)<0){
						flag=false;
					}
				}
				
				
			});
			
			if(!flag){
				alert("请输入正确的退货数量");
				return;
			}
			$("#order_dialog .submitBtn").attr("disabled",true);
			var self= this;
			var options = {
					url:basePath+"ship!returned.do?ajax=yes",
					type:"post",
					dataType:"json",
					success: function(responseText) { 
						if(responseText.result==1){
							alert(responseText.message);
							Eop.Dialog.close("order_dialog");
							self.shipStatus = responseText.shipStatus;
							self.bindFlowEvent();
						}
						if(responseText.result==0){
							alert(responseText.message);
						}						
					},
					error:function(){
						alert("出错了:(");
					}
			};
			$('#order_form').ajaxSubmit(options); 
		},
		/**
		 * 换货
		 */
		changed:function(){
			var self= this;
			var options = {
					url:basePath+"ship!change.do?ajax=yes",
					type:"post",
					dataType:"json",
					success: function(responseText) { 
						if(responseText.result==1){
							alert(responseText.message);
							Eop.Dialog.close("order_dialog");
							self.shipStatus = responseText.shipStatus;
							self.bindFlowEvent();
						}
						if(responseText.result==0){
							alert(responseText.message);
						}						
					},
					error:function(){
						alert("出错了:(");
					}
			};
			$('#order_form').ajaxSubmit(options); 
		},
		/**
		 * 完成
		 */
		complete:function(orderId){
			var self = this;
			$.ajax({
				url:basePath+'order!complete.do?ajax=yes&orderId='+orderId,
				dataType:"json",
				success:function(responseText){
					if(responseText.result==1){
						alert(responseText.message);
						self.orderStatus = responseText.orderStatus;
						self.bindFlowEvent();
					}
					if(responseText.result==0){
						alert(responseText.message);
					}						
				},
				error:function(){
					alert("出错了:(");
				}
			});
		},
		/**
		 * 作废
		 */
		cancel:function(orderId){
			var self = this;
			$.ajax({
				url:basePath+'order!cancel.do?ajax=yes&orderId='+orderId,
				dataType:"json",
				success:function(responseText){
					if(responseText.result==1){
						alert(responseText.message);
						self.orderStatus = responseText.orderStatus;
						self.bindFlowEvent();
					}
					if(responseText.result==0){
						alert(responseText.message);
					}						
				},
				error:function(){
					alert("出错了:(");
				}
			});
		}
		,
		/**
		 * 订单确认
		 * @param orderId
		 */
		confirmOrder:function(orderId){
			var self = this;
			$.ajax({
				url:basePath+'order!confirmOrder.do?ajax=yes&orderId='+orderId,
				dataType:"json",
				success:function(responseText){
					if(responseText.result==1){
						alert(responseText.message);
						self.orderStatus = responseText.orderStatus;
						self.bindFlowEvent();
					}
					if(responseText.result==0){
						alert(responseText.message);
					}		
				},
				error:function(){
					alert("出错了:(");
				}
			});
		}
		,
		saveRemark:function(orderId){
			var self = this;
			$("#remark_form").ajaxSubmit({
				url:basePath+'order!saveRemark.do?ajax=yes',
				dataType:"json",
				type:'POST',
				success:function(responseText){
					if(responseText.result==1){
						alert(responseText.message);
					}
					if(responseText.result==0){
						alert(responseText.message);
						self.bindFlowEvent();
						self.showRemark(orderId);
					}						
				},
				error:function(){
					alert("出错了:(");
				}
			});
		}
	
};

function isdigit(s)
{
var r,re;
re = /\d*/i; //\d表示数字,*表示匹配多个数字
r = s.match(re);
return (r==s);
}