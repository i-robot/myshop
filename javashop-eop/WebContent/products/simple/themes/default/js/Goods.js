/**
 * 商品操作js
 */
var Goods={
	init:function(scroll){
		var self = this;
		$(".Numinput").numinput({name:"num"});	
		Favorite.init();
		
		$("#addbutton").click(function(){	
			
			var $this= $(this); 
			self.addToCart($this);
			
		});
		 
		//相册缩略图滑动
		if(scroll){
			$("#detail_wrapper .gallery .thumblist").jCarouselLite({
		        btnNext: ".left-control",
		        btnPrev: ".right-control",
				visible:4
		    });
		}
		
		//相册放大切换
		$("#detail_wrapper .gallery .thumblist li").click(function(){
			var $this = $(this);
			var img  = $this.children("img");
			var zoom = document.getElementById('zoom'); //get the reference to our zoom object
		    MagicZoom.update(zoom, img.attr("big"), img.attr("small"), 'show-title: false'); 
			$this.addClass("selected").siblings().removeClass("selected");
		});	 

		
	},
	addToCart:function(btn){
		var self = this;
		$.Loading.show("请稍候...");
		btn.attr("disabled",true);

		var options={
				url:"widget?type=shop_cart&action=add&ajax=yes",
				dataType:"json",		
				success:function(result){
					$.Loading.hide();
					if(result.result==1){
						self.showAddSuccess();
					}else{
						$.dialog.alert("发生错误:"+result.message) 
					}
					btn.attr("disabled",false);
				},
				error:function(){
					$.Loading.hide();
					$.alert("抱歉,发生错误")
					btn.attr("disabled",false);
				}
		}
		
		$("#goodsform").ajaxSubmit(options);		
	},
	
	showAddSuccess:function(){
		var html = $(".add_success_msg").html();
		$.dialog({ title:'提示信息',content:html,lock:true,init:function(){
			var self = this;
			$(".ui_content .btn").jbtn();
			$(".ui_content .returnbuy_btn").click(function(){
				self.close();
			});

			$(".ui_content .checkout_btn").click(function(){
				location.href="cart.html";
			});
			
		}});
	}

};
