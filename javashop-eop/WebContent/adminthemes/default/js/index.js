/**
 * 后台界面构建js
 * @author kingapex
 */
var BackendUi={
	menu:undefined,
	init:function(menu){
		Eop.AdminUI.init({wrapper:$("#right_content")});
		
		$(".desktop a").click(function(){
			Eop.AdminUI.load($(this));
			return false;
		});
		
		
		this.menu =menu;
		this.autoHeight();
		var self =this;
		$(window).resize(function(){self.autoHeight();});
	},
	disMenu:function(){
		this.disSysMenu();
		this.disAppMenu();
	},
	
	/**
	 * 显示系统菜单
	 */
	disSysMenu:function(){
		var self =this;
		var menu = this.menu;
		$.each(menu.sys,function(k,v){
			var link = self.createLink(v);
			 $("<li/>").appendTo( $(".sysmenu>ul") ).append(link);
			 if(v.target!='_blank'){
				link.click(function(){
						Eop.AdminUI.load($(this));
						return false;
					});
			 }
		});		
	},
	/**
	 * 显示应用菜单
	 */
	disAppMenu:function(){
		var self=this;
		var menu = this.menu;
		var i=0;
		$.each(menu.app,function(k,v){
			if(founder ==1 && (v.id==237 || v.id==244 ||v.id==266)){}else{
				var link = $("<a  target='"+v.target+"' href='"+ v.url +"' >" + v.text + "</a>");
				$("<li><span></span></li>").appendTo($(".appmenu>ul")).children("span").append(link);
				var children = v.children;
				 
					link.click(function(){
						if(children)
							self.disAppChildren(children);
						Eop.AdminUI.load($(this));
						$(".appmenu li").removeClass("current");
						$(this).parent().parent().addClass("current");
						return false;
					});
	
					if(i==0){ 
						var href= link.attr("href");
						var target=link.attr("target");
						link.attr("href",app_path+"/core/admin/index.do");
						link.removeAttr("target");
						link.click();
						link.attr("href",href);
						link.attr("target",target);
					}
					i++;
			}
		});			
	},
	/**
	 * 显示应用的子菜单
	 */
	disAppChildren:function(children){
		var self= this;
		var leftMenu = $("#leftMenus dl");
		leftMenu.empty();
		$.each(children,function(k,v){
			leftMenu.append($("<dt> <a class=\"folder\">"+this.text+"</a></dt>"));
			if(this.children){
				$.each(this.children,function(k,v){
					var link = self.createLink(v);
					leftMenu.append($("<dd></dd>").append(link));
					link.click(function(){
						Eop.AdminUI.load($(this));
						return false;
					});
				});
			}
		});
	},
	createLink:function(v){
		var link = $("<a  target='"+v.target+"' href='"+ v.url +"' >" + v.text + "</a>");
		return link;
	},
	autoHeight:function(){
		var height= $(window).height()-100;
		$("#leftMenus").height(height);
		$("#right_content").height(height);
		
	}

};

$(function(){
	BackendUi.init(menu);
	BackendUi.disMenu();
})
