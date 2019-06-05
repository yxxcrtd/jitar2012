$(function(){
    $(".video>ul>li").hover(function(){
        $(this).find(".videoPlay,.videoPlayBg").show();
    },function(){
        $(this).find(".videoPlay,.videoPlayBg").hide();
    });
	$(".secVideoList>li").hover(function(){
        $(this).find(".secVideoPlay,.secVideoBg").show();
    },function(){
        $(this).find(".secVideoPlay,.secVideoBg").hide();
    });
	$(".secVideoBigImg").hover(function(){
        $(this).find(".secVideoBimgBg,.secVideoBimgPlay").show();
    },function(){
        $(this).find(".secVideoBimgBg,.secVideoBimgPlay").hide();
    });
	var logged = $(".loggedCont");
	logged.hover(function(){
		$(this).addClass("loggedContH").find("ul").show();
		var loggedWidth = logged.width();
		if(loggedWidth >=100){
			$(".loggedList").width(loggedWidth);
		}
	},function(){
		$(this).removeClass("loggedContH").find("ul").hide();
	});
});