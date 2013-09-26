Favorite={
	init:function()	{
	 
		var self =this;
		//收藏
		$(".favorite").click(function(){
			var currgoodsid = $(this).attr("goodsid");
	 
			if(!isLogin){
				$.LoginDialog.open({loginSuccess:function(){
				   self.doFavorite(currgoodsid);
				}});
			}else{
				 self.doFavorite(currgoodsid);
			}
			return false;
		});
		
		$(".gnotifybtn").click(function(){
			var currgoodsid = $(this).attr("goodsid");
			 self.gnotify(currgoodsid);			
			return false;			
		});
	},
	gnotify:function(currgoodsid){
		var self =this;
		if(!isLogin){
			$.LoginDialog.open({loginSuccess:function(){
			   self.doGnotify(currgoodsid);
			}});
		}else{
			 self.doGnotify(currgoodsid);
		}
	}
	,
	doGnotify:function(currgoodsid){
		$.Loading.show("正在请求...");
		$.ajax( { 
			url:'widget?type=member_gnotify&action=add&ajax=yes&goodsid='+currgoodsid,
			dataType:'json',
			success:function(result){
				$.Loading.hide();
				$.alert(result.message);
			} 
		});		
	}
	,
	doFavorite:function(currgoodsid){
		$.Loading.show("正在收藏...");
		$.ajax( { 
			url:'widget?type=member_favorite&action=favorite&ajax=yes&goodsid='+currgoodsid,
			dataType:'json',
			success:function(result){
				$.Loading.hide();
				$.alert(result.message);
			} 
		});
	}
};