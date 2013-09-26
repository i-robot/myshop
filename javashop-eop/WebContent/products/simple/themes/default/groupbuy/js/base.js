/**
 * 所有团购页面的初始化操作
 */
$(function(){
    initGotop();
    
    //设置导航条样式
    setNavStyle();
    
    $.fn.jbtn = function(options){  
        
        return this.each(function() {    
        
            $(this).unbind("hover").hover(

                function ()
                {   
                    var $this = $(this);   
                    $this.addClass("hover");
                },
                function ()
                {   
                    var $this = $(this);   
                    $this.removeClass("hover");     
                }
                
            );  
        });    
        
    };
    
    /**
     * 对话框
     */
    $.Loading = $.Loading || {};
    $.Loading.show=function(text){
        $.blockUI({ 
             css: { top: '10px'} , 
             message:text,
             showOverlay:false
        }); 
    };

    $.Loading.hide=function(){
        $.unblockUI();
    };

    $.alert=function(text){
        $.dialog.alert(text);
    };

    $.confirm=function(text,yes,no){     
        $.dialog.confirm(text,yes,no);
    };
    
});

/**
 * 设置导航条样式
 */
function setNavStyle(){
    var url = window.location.href;
    url = url.substring(url.lastIndexOf("/"));
    $("ul.nav li").each(function(){
        var $this = $(this);
        var href = $this.find("a").attr("href");
        href = href.substring(href.lastIndexOf("/"));
        
        var split = href.lastIndexOf(".");
        var name = href.substring(0, split);
        var extension = href.substring(split);
        var re = new RegExp(name+"(.*)"+extension, "ig");
        if(re.test(url)){
            $this.attr("class", "hover");
            console.info(11111);
            return;
        }
    });
}

/**
 * 回顶部
 */
function initGotop() {
    var gotop = $("#go-top");
    var $body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');
    
    gotop.click(function(){
        $body.animate({scrollTop : 0}, $body.scrollTop()/2);
    });
    
    $(document).scroll(function(event){
        var windowHeight = $(window).height();
        if($body.height() > windowHeight && $body.scrollTop() > windowHeight/3) {
            gotop.show();
        } else {
            gotop.hide();
        }
    });
}

/**
 * 加入收藏夹
 * @param title
 * @param url
 * @returns {Boolean}
 */
function addFavorite(title, url) {
    if (window.sidebar) {
        window.sidebar.addPanel(title, url, "");
    } else if (document.all) {
        window.external.AddFavorite(url, title);
    } else {
        return true;
    }
}

/**
 * 团购计时器
 * @param selector 显示时间元素的选择器
 * @param nowTime  服务器当前时间秒数
 * @returns
 */
var GroupTimeout = function(selector, nowTime) {
    this.selector = selector;
    this.nowTime = nowTime;
    this.intervalArr = [];
    var _this = this;
    window.setInterval(function(){_this.nowTime++}, 1000); //增加服务器当前时间秒数
}; 

GroupTimeout.prototype.click = function(span, i) {
    var lag = span.attr("endTime") - this.nowTime;
    if(lag > 0) {
        var second = Math.floor(lag % 60);     
        var minite = Math.floor((lag / 60) % 60);
        var hour = Math.floor((lag / 3600) % 24);
        var day = Math.floor((lag / 3600) / 24);
        span.html("<strong>"+day+"</strong>天<strong>"+hour+"</strong>小时<strong>"+minite+"</strong>分<strong>"+second+"</strong>秒");
    } else {
        if(this.intervalArr[i]) { 
            window.clearInterval(this.intervalArr[i]);
            this.intervalArr[i] = null;
        }
        span.html("团购已经结束啦！");
    };    
};

GroupTimeout.prototype.run = function() {
    var _this = this;
    $(this.selector).each(function(i){
        var span = $(this);
        var lag = span.attr("endTime") - _this.nowTime;
        var hour = lag / 3600;
        if(hour <= 24) {
            _this.intervalArr[i] = window.setInterval(function(){ _this.click(span, i); }, 1000);
        } else {
            span.html("<strong>"+Math.floor(hour/24)+"</strong>天以上");
        }
    });
};