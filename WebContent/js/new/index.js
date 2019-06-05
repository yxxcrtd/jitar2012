$(function(){
	$(window).scroll(function(){
		$("#topNav").css("position","fixed");
	});
});
//统计输入字数
String.prototype.gblen = function() { 
	var len = 0; 
	for (var i=0; i<this.length; i++) { 
		if (this.charCodeAt(i)>127 || this.charCodeAt(i)==94) { 
			len += 2; 
		} else { 
			len ++; 
		} 
	}
	return len;
};

function inform(){
	$('#id').report($("a.o0"),$(".report"),$(".close,.reportCancel,.closeTips"),$(".reportSub"));
}

function operator(){
	$("a.o2").click(function(){
	  var needToggled = false;
	  var objPraise = $(this);
		var o2Tips = $("a.o2").text();		
		if(o2Tips == "赞"){
		  //代码实现赞
		  $.get(operateUrl + "praise",function(msg){		  
		  if(msg.code == 0){
		    needToggled = true;
		    objPraise.text("取消赞").addClass("hover");
		    if(msg.message == "1"){
		      //只有自己赞
		      $(".operateCont .praise a").text("我觉得的很赞！");
		      $(".operateCont ul").html('<li><a title="' + loginUser.trueName + '" href="' + JITAR_ROOT + 'go.action?loginName=' + loginUser.loginName +'" target="_blank"><img width="30" src="' + loginUser.userIcon +'" onerror="this.src=\'' + JITAR_ROOT + 'images/default.gif\'" /></a></li>');
		    }else
		      {
		        $(".operateCont .praise a").text("我和其他" + ($(msg.data).length-1) + "个人觉得的很赞！");
		      }
		      $(".operateCont ul").html('');
		      $(msg.data).each(function(){
		        $(".operateCont ul").append('<li><a title="'+ this.trueName +'" href="' + JITAR_ROOT + 'go.action?loginName='+ this.loginName +'" target="_blank"><img width="30" src="' + SSOServerUrl + 'upload/' + this.userIcon +'" onerror="this.src=\'' + JITAR_ROOT + 'images/default.gif\'" /></a></li>');
		      });
		   }
		   else{
		     needToggled = false;
		     showMessageBox(msg.message);		     
		     //objPraise.text(msg.message).addClass("hover");
		   }
		  if(needToggled){
		    $(".operateCont").toggle(100).children(".praise").show();
	      if($(".praise").is(":visible")){
	       $(".operateCont ul").hide();
	      }
	    }
		  },"json");			
		}else{
		//代码实现赞
		  $.get(operateUrl + "unpraise",function(msg){
	      if(msg.code == 0){
	        needToggled=true;
	        objPraise.text("赞").removeClass("hover");
	        if(msg.message == ""){
	          //只有自己赞
	          $(".operateCont .praise a").text("还没有人赞！");
	          $(".operateCont ul").html("");	          
	        }
	        else{
	          $(".operateCont .praise a").text($(msg.data).legth + "个人觉得很赞！");
            $(".operateCont ul").html("");            
	          $(msg.data).each(function(){
              $(".operateCont ul").append('<li><a title="'+ this.trueName +'" href="' + JITAR_ROOT + 'go.action?loginName='+ this.loginName +'" target="_blank"><img width="30" src="' + SSOServerUrl + this.userIcon +'"  onerror="this.src=\'' + JITAR_ROOT + 'images/default.gif\'"/></a></li>');
            });
	        }
	       }
	       else{
	         needToggled=false;
	         showMessageBox(msg.message);  
	         //objPraise.text(msg.message).addClass("hover");
	       }
	      if(needToggled){
	        $(".operateCont").toggle(100).children(".praise").show();
	        if($(".praise").is(":visible")){
	          $(".operateCont ul").hide();
	        }
	      }
	      },"json");  
		}		
	});
	$(".praise").click(function(){
		$(".operateCont ul").toggle(100);
	});
	
	//收藏
	$("a.o3").click(function(){
	  var obj = $(this);
		var o3Tips = $("a.o3").children("b").text();
		
		if(o3Tips == "收藏"){
		  $.get(operateUrl + "favorite",function(msg){
		    if(msg.code == 0){
		      $("a.o3 span.operateTips").show().children("span").text("收藏成功！").animate({"display":"block"}, 3000,function(){
		        $("a.o3 span.operateTips").hide();
		        });
		        obj.children("b").text("取消收藏");
		        obj.toggleClass("hover");
		    }
		    else{
		      showMessageBox(msg.message);
		      /*
		      $("a.o3 span.operateTips").show().children("span").text("收藏失败！").animate({"display":"block"}, 3000,function(){
		        $("a.o3 span.operateTips").hide();
		        });
		      */
		    }		    		    
		  });			
		}else{
		  $.get(operateUrl + "unfavorite",function(msg){
        if(msg.code == 0){
          $("a.o3 span.operateTips").show().children("span").text("取消成功！").animate({"display":"block"}, 3000,function(){
            $("a.o3 span.operateTips").hide();
            });
            obj.children("b").text("收藏");
            obj.toggleClass("hover");
        }
        else{
          $("a.o3 span.operateTips").show().children("span").text("取消失败！").animate({"display":"block"}, 3000,function(){
            $("a.o3 span.operateTips").hide();
            });
        }               
      });
		}
	});
}

$(function(){
	//点击按钮弹出
	function clickShow(obj,targetObj){
		obj.click(function(event){
			event.stopPropagation();
			targetObj.toggle();
			var id = targetObj.attr("id");
			if(id == 'secSelectWrap1' || id == 'secSelectWrap2' || id == 'secSelectWrap3'){
				var scrollC = targetObj.children("ul");
				scrollBarShow(scrollC);
			}
			//隐藏其他弹出框
			$(".topSearchSelect,.loginBox,#secSelectWrap1,#secSelectWrap2,#secSelectWrap3").not(targetObj).hide();
		});		
		targetObj.click(function(event){
			event.stopPropagation();
		});
	}
	clickShow($(".loginBtn"), $(".loginBox"));
	clickShow($(".topSearchOption"), $(".topSearchSelect"));
	clickShow($("#secInput1").parent(),$("#secSelectWrap1"));
	clickShow($("#secInput2").parent(),$("#secSelectWrap2"));
	clickShow($("#secInput3").parent(),$("#secSelectWrap3"));
	document.onclick = function(){
		$(".topSearchSelect,.loginBox,#secSelectWrap1,#secSelectWrap2,#secSelectWrap3").hide();
	};
	
	//搜索下拉赋值及鼠标滑动效果
	var $option = $(".topSearchOption");
	var $select = $(".topSearchSelect");
    $option.hover(function(){
        $(this).find("span").addClass("arrow3");
    },function(){
        $(this).find("span").removeClass("arrow3");
    });
	$select.children("li").children("a").click(function(){
		var value = $(this).text();
		$option.children("em").text(value);
		$select.hide();		
	});
	//登录后状态效果
	var logged = $(".loggedCont");
	logged.hover(function(){
		$(this).addClass("loggedContH");
		var loggedWidth = logged.width();
		if(loggedWidth >=100){
			$(".loggedList").width(loggedWidth);
		}
	},function(){
		$(this).removeClass("loggedContH");
	});
    //顶部学科导航
    $(".topSubject").hover(function(){
        $(this).children(".topSubjectBox").show().css("opacity",0.9).end().children(".subjectText").addClass("subjectTextH");
    },function(){
        $(this).children(".topSubjectBox").hide().end().children(".subjectText").removeClass("subjectTextH");
    });    
    //导航滑动效果
    $(".nav").find("li").hover(function(){
        $(this).addClass("hover");
    },function(){
        $(this).removeClass("hover");
    });
	//焦点图背景透明
	$(".indImg li div p,.imageShowBg,.imgBg").css("opacity",0.5);
  //滑动门
  function sliding(obj,target){
      var obj = $("."+obj).children("h3").children("a.sectionTitle");
      obj.each(function(index){
          $(this).hover(function(){
              if(index == 0){
                  $(this).removeClass("prevActive").addClass("active").siblings("a").removeClass("active prevActive");
                  $(this).siblings(".more").hide().eq(index).show();
                  $(this).parent().siblings("."+target).hide().eq(index).show();
              }else{
                  $(this).removeClass("prevActive").addClass("active").siblings("a").removeClass("active prevActive").end().prev("a").addClass("prevActive");
                  $(this).siblings(".more").hide().eq(index).show();
                  $(this).parent().siblings("."+target).hide().eq(index).show();
              }
          });
      });
    }
    sliding("articleWrap","article");
    sliding("stadioWrap","stadio");
    sliding("naturalWrap","natural");
    sliding("photoWrap","photo");
    sliding("msWrap","stadio");
    sliding("videoWrap","video");
    sliding("teamWrap","team");
    sliding("secRightC2","secTop");
	  sliding("resourcesWrap","resourcesTop");
   // sliding("secRightW","listCont");
    //图片加内边框效果
    $(".imgList>li:not('.tag')").hover(function(){
        $(this).children(".imgListBg").show();
    },function(){
        $(this).children(".imgListBg").hide();
    });
    //图片遮罩层显示效果
    $("div.photoStyle1,div.photoStyle2,div.photoStyle3").hover(function(){
        var $Div = $(this).children(".tx");
        var scrollHeight = $Div.outerHeight();
        $Div.animate({"margin-top":-scrollHeight}, 300);
    },function(){
        var $Div = $(this).children(".tx");
        $Div.animate({"margin-top":0}, 300);
    });
    reBindClick();
	
	//两次绑定事件
	function reBindClick(){
		 //二级栏目搜索下拉
		var selectTarget = $(".secSearchSelectWrap");
		selectTarget.find("a").click(function(){
			var value = $(this).text();
			var $wrap = $(this).parent().parent().parent().parent();
			$wrap.siblings(".secSearchText").val(value).end().hide();
        });
	}

	//滚动条
	function scrollBarShow(obj){
		obj.jscroll({ W:"7px"
			,BgUrl:"url(skin/default/images/s_bg3.gif)"
			,Bg:"right 0 repeat-y"
			,Bar:{Bd:{Out:"#989c9e",Hover:"#a3a8aa"}
			,Bg:{Out:"-21px 0 repeat-y",Hover:"-28px 0 repeat-y",Focus:"-35px 0 repeat-y"}}
			,Btn:{btn:true
				,uBg:{Out:"0 0",Hover:"-7px 0",Focus:"-14px 0"}
				,dBg:{Out:"0 -8px",Hover:"-7px -8px",Focus:"-14px -8px"}
			}
			,Fn:function(){}
		});
	}
    //二级栏目下左侧导航效果
    $(".leftNavF").click(function(){
        $(this).children("span.leftNavIcon").toggleClass("leftnavIconH").siblings("span.folder").toggleClass("folderH");
        $(this).next("ul").slideToggle();
    });
	$(".leftNav").find("li").live("click",function(e){
		e.stopPropagation();
		$(this).children("span.leftNavIcon").toggleClass("leftnavIconH").siblings("span.liFolder").toggleClass("liFolderH");
		$(this).children("ul").slideToggle();
	});
	
	
	
	//鼠标滑过及选中状态
	function hoverShowBg(obj,wrap){
		$("body").append('<span class="leftNavBg"></span><span class="leftNavBg1"></span>');
		obj.each(function(index){
			$(this).hover(function(){
				var y = $(this).offset();
				var x = wrap.offset();
				$(".leftNavBg").show().css({"left":x.left+6,"top":y.top});
			},function(){
				$(".leftNavBg").hide();
			});
			$(this).click(function(){
				var y = $(this).offset();
				var x = wrap.offset();
				$(".leftNavBg1").show().css({"left":x.left+6,"top":y.top});
			});
		});
	}
	hoverShowBg($(".leftNavF"),$(".leftNav"));
	hoverShowBg($(".leftNavText"),$(".leftNav"));
	//文字背景透明
	$(".secVideoListTextBg,.secVideoBg,.videoListBg").css("opacity",0.6);
	operator();
	
	//转载
  $("a.o4").click(function(){
    var obj = $(this);
    var o4Tips = $("a.o4").children("b").text();    
    $.get(operateUrl + "transfer",function(msg){
      if(msg.code == 0){
        obj.toggleClass("hover");
        $("a.o4 span.operateTips").show().children("span").text("转载成功！").animate({"display":"block"}, 3000,function(){
          $("a.o4 span.operateTips").hide();
          });
      }
      else{
        showMessageBox(msg.message);
        /*
        $("a.o4 span.operateTips").show().children("span").text("转载失败！").animate({"display":"block"}, 3000,function(){
          $("a.o4 span.operateTips").hide();
          });
        */
      }               
    }); 
  });
	
	//回复框输入字数
	var len = 0;
	function gbLen(obj,target){		
		obj.keyup(function(){
			/*
			var str = obj.text();
			var reg = /[^\u4e00-\u9fa5]/;
			if(reg.test(str)){
				len +=2;
			}else{
				len++;
			}
			len2 = parseInt(300-len);
			target.text(len2);
			*/
			var lenE = obj.val().length;
			var lenC = 0;
			var CJK = obj.val().match(/[\u4E00-\u9FA5\uF900-\uFA2D]/g);
			if (CJK != null) lenC += CJK.length;
			if(600 - lenC - lenE >0){
				target.text(parseInt((600 - lenC - lenE)/2));
			}else{
				target.text(0);
			}
			if (parseInt(target.text()) <= 0) {
				var tmp = 0;
				var cut = obj.val();
				var i=0;
				var s = "";
				for (i=0; i<cut.length; i++){
					tmp += /[\u4E00-\u9FA5\uF900-\uFA2D]/.test(cut.charAt(i)) ? 2 : 1;
					if (tmp > 600) break;
					s += cut.charAt(i);
				}
				obj.val(s);
			}
			
		});
	}
	gbLen($(".textAreaCenter"),$("#textNum"));
	gbLen($(".replyAreaCenter"),$("#replyTextNum"));
	//右侧新闻轮播效果
	function slide(obj,wrap){
		var time = 300;
		var distance = wrap.children("ul").width();
		obj.each(function(i) {
        $(this).hover(function(){
				if($(this).hasClass("active")) return false;
				$(this).addClass("active").siblings().removeClass("active");
				wrap.css("position","relative").animate({left:-distance*i},time);
			});
        });
	}
	slide($(".textSlide a"),$(".textWrap"));
	slide($(".videoS1 a"),$(".videoW1"));
	slide($(".videoS2 a"),$(".videoW2"));
	//看过该文章的人轮播
//看过该文章的人轮播
  function slideRead(next,prev,wrap,num){
    var i =0, time =600;
    var distance = wrap.children("ul").width();
    next.click(function(){
      if(i>=num-1){ 
        i=-1; 
      }
      i++;
      wrap.css("position","relative").animate({left:-distance*i},time); 
    });
    prev.click(function(){
      i--;
      if(i<0) i=num-1;
      wrap.css("position","relative").animate({left:-distance*i},time);
      
    });
  };
  //slideRead($(".ImgSlideNext"),$(".ImgSlidePrev"),$(".readThis"),3);
    function slideRead2(next,prev,wrap){
        var i = 0, time = 500,
            oldNext = "."+next,
            newNext = next+"_on",
            oldPrev = "."+prev,
            newPrev = prev+"_on",
            wrap = "."+wrap,
            distance = $(wrap).children("ul").width();
    if($(wrap).children("ul").length<2){
      $(oldNext).hide();
      $(oldPrev).hide();
    }
        $("."+newNext).live("click",function(){
            i = 0;
            $(this).removeClass(newNext);
            $(this).siblings("a").addClass(newPrev);
            $(wrap).css("position","relative").animate({left:-distance*i},time);
        });
        $("."+newPrev).live("click",function(){
            i=1;
            $(this).removeClass(newPrev);
            $(this).siblings("a").addClass(newNext);
            $(wrap).css("position","relative").animate({left:-distance*i},time);
        });
    };
    slideRead2("ImgSlideNext","ImgSlidePrev","readThis");
	
	//右侧点击展开更多
	$(".unFold").click(function(){
		$(this).parent("p").prev("p").css("height","auto");
		$(this).hide().siblings(".unFold1").css("display","block");
	});
	$(".unFold1").click(function(){
		$(this).parent("p").prev("p").removeAttr("style");
		$(this).hide().siblings(".unFold").css("display","block");
	});
	//说明更多
	$(".summaryBtn").click(function(){
		$(this).hide().siblings("a").show().parent().prev("p").css("height","auto");
	});
	$(".summaryBtn1").click(function(){
		$(this).hide().siblings("a").show().parent().prev("p").removeAttr("style");
	});
	
//举报弹出
  function rule(){
    bodyWidth = $("body").width(),
    bodyHeight = $("body").height()+75,
    screenWidth = $(window).width()/2,
    screenHeight = $(window).height()/2,
    thisWidth = $(".report").width()/2,
    thisHeight = $(".report").height()/2,
    scrollHeight = $("body").scrollTop();
  }
  
  //举报选择的内容
  var reportContent = "";  
  $.fn.report= function(obj,target,colse,submt){
    //获取举报选择的内容。
    $(".reportCont span label").bind("click",function(){
      reportContent = $(this).attr("data");
     });
    
    //设置默认值
    reportContent = $(".reportCont span label[class='reportChecked']").attr("data");    
    rule();
    obj.click(function(){
     //检查是否登录
      $.get(JITAR_ROOT + "checkLogin.action?t=" + (new Date()).getTime(), function( data ) {
        var logined = $.trim(data);
        if(logined == "0"){
          showMessageBox("请先登录。");
        }
        else{
          $("body").append('<div id="layoutBg"></div>');
          $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
          target.show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
        }
      });
    });
    window.onresize = function(){
      rule();
      target.css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight});
    };
    target.find("label").click(function(){
      $(this).addClass("reportChecked").parent("span").siblings("span").children("label").removeClass("reportChecked").addClass("reportUnCheck");
    });
    colse.click(function(){
      $("#layoutBg").remove();
      $(".report,.reportTips").hide();
    });
    /*
    submt.click(function(){
      target.hide();
      $(".reportTips").show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
      if($(".reportTips:visible")){
        setTimeout('$(".reportTips").hide();$("#layoutBg").remove();', 1000);
      }
    });
    */
  };
  
  $('#id').report($("a.o0"),$(".report"),$(".close,.reportCancel,.closeTips"),$(".reportSub"));
  
//点击举报执行的代码，各详情页通用
  $(".reportSub").bind("click",function(){
    $.get(operateUrl + "report&reportContent=" + encodeURIComponent(reportContent),function(msg){
       if(msg.code == 0){
       $(".report").hide();
       $(".reportTips").show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
       if($(".reportTips:visible")){
         setTimeout('$(".reportTips").hide();$("#layoutBg").remove();', 1000);
       }
       }
       else{         
         showMessageBox(msg.message);
       }
       });
  });  
  
  function delCom(obj,target,colse){
    rule();
    obj.click(function(){
      $("body").append('<div id="layoutBg"></div>');
      $("#layoutBg").css({"width":bodyWidth,"height":bodyHeight});
      target.show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
    });
    window.onresize = function(){
      rule();
      target.css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight});
    };
    colse.click(function(){
      $("#layoutBg").remove();
      $(".deleteTips").hide();
    });
  }
  delCom($(".removeBtn"),$(".deleteTips"),$(".close,.reportCancel"));
});

//底部固定
function reHeight(){
    $("#footer").removeAttr("style");
    var sHeight = $(window).height();
    var cHeight = $("body").height();
    var fHeight = $("#footer").outerHeight();
    if(cHeight < sHeight){
        $("#footer").css({"bottom":0,"position":"absolute"});
    }
}

$(reHeight);
$(window).resize(reHeight);
$(window).bind("keyup",reHeight);
$(window).bind("click",reHeight);
$(window).bind("load",reHeight);
$(window).bind("scroll",reHeight);

/**/
//点击回复按钮弹出回复框
var lastId = null;
function replay(id,parentId){
  if(lastId !=null && $("#textareaReply" + lastId).length > 0){
    $("#textareaReply" + lastId).remove();
  }
  //避免重复添加
  if($("#textareaReply" + id).length == 0){
    lastId = id;
  	html  = '<dl id="textareaReply' + id + '">';
  	html += '<dt><img width="50" src="' + loginUser.userIcon + '" onerror="this.src=\'' + JITAR_ROOT + 'images/default.gif\'" /></dt>';
  	html += '<dd>';
  	html += '<div class="replyArea">';
  	html += '<span class="replyAreaLeft"></span>';
  	html += '<textarea class="replyAreaCenter" placeholder="回复" id="replyAreaCenter' + id + '" count="300" ondrop="return false;" onkeypress="return charLimit(this)" onkeyup="return characterCount(this)"></textarea>';
  	html += '<span class="replyAreaRight"></span>';
  	html += '</div>';
  	html += '<p class="textAreaTips mt10"><a href="javascript:void(0);" class="publishBtn" onclick="replyComment(' + id + ','+ parentId +');return false;">发表</a>还可以输入<span id="textNumreplyAreaCenter' + id + '">300</span>个字</p>';
  	html += '</dd>';
  	html += '<span class="replyArrow"></span>';
  	html += '</dl>';
  	var id = "#r"+id;
  	$(id).after(html);
  }
  else{
    $("#textareaReply" + id).remove();
    }
}

//更多按钮位置判断
/*
$(".more1P").each(function(i){
    var pH = $(this).siblings("p").height();
    if(pH < 25){
        $(".more1P").eq(i).css("margin-top",0);
    }
});
*/

//字数限制
function charLimit(el) {
  var maxLength=parseInt($(el).attr("count"));
  if (el.value.length > maxLength) return false;
  return true;
}
function characterCount(el) {
  var maxLength=parseInt($(el).attr("count"));
  if (el.value.length > maxLength){
   el.value = el.value.substring(0,maxLength);
   }
  $('#textNum' + el.id).html( maxLength - el.value.length);
  //$(el).css("height", el.scrollHeight + "px");  
  return true;
}

function showMessageBox(message){
  $("#messageText").html(message);
  commonMessageBoxBackBox = $("#commonMessageBoxBack");
  messageBox = $("#commonMessageBox");
  commonMessageBoxBackBox.show().css({"width":bodyWidth,"height":bodyHeight});
  messageBox.show().css({"left":screenWidth-thisWidth,"top":screenHeight-thisHeight,"position":"fixed"});
  $("#commonMessageBox h3 a").one("click",hideMessageBox);
  $("#commonMessageBox .okBtn").one("click",hideMessageBox);
}
function hideMessageBox(){
  $("#commonMessageBoxBack").hide();
  $("#commonMessageBox").hide();
}

$.extend({
  setSearch:function(){
    var text = $('.topSearchInput').val();//搜索关键字
    var flag = true;
    if(null==text||text.replace(/\ /g,'').length==0){
      flag = false;
      alert('请输入查询关键字');
      $('.topSearchInput').siblings("a:last").removeAttr('href');
    }
    if(flag){
      var $option = $('.topSearchOption');
      var $topSearchBtn = $('.topSearchBtn');
      var text = $option.children("em").text();
      var $topSearchInput = $('.topSearchInput').val();//搜索关键字
      if(text=='协作组'){
        $topSearchBtn.attr('href','groups.action?type=search&k='+$topSearchInput+"&oc=g");
      }else if(text=='工作室'){
        $topSearchBtn.attr('href','blogList.action?type=search&k='+$topSearchInput+"&oc=b");
      }else if(text=='文章'){
        $topSearchBtn.attr('href','articles.action?type=search&k='+$topSearchInput+"&oc=a");
      }else if(text=='资源'){
        $topSearchBtn.attr('href','resources.action?type=search&k='+$topSearchInput+"&oc=r");
      }else if(text=='图片'){
        $topSearchBtn.attr('href','showPhotoSearch.action?type=search&k='+$topSearchInput);
      }else if(text=='视频'){
        $topSearchBtn.attr('href','video_list.action?type=search&k='+$topSearchInput);
      }
    }
  }
});
