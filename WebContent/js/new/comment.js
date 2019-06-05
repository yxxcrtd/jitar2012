//为了操作方便，提取为公用变量
var commentId = 0, isReply = 0;
/* 绑定删除事件 */
function bindDeleteEvent(){
 //删除以前绑定的事件，否则，可能会执行多次
  $(".removeBtn").die().live("click",function(){
     //收集数据，这些数据需要添加到html中的标签属性上，主要是 <a href="javascript:void(0);" class="removeBtn" commentid="388" isreply="0">删除</a>
     commentId = $(this).attr("commentid"); //当前评论的id
     isReply = $(this).attr("isreply");     //标识是直接评论还是回复评论
     //绑定删除确认按钮事件           
     $(".delSub").die().live("click",exeDelete); //绑定删除提示框的确认按钮
   });
}

var bodyWidth,bodyHeight,screenWidth,screenHeight,scrollHeight,thisWidth,thisHeight;
function rule(){
  bodyWidth = $("body").width(),
  bodyHeight = $("body").height()+75,
  screenWidth = $(window).width()/2,
  screenHeight = $(window).height()/2,
  thisWidth = $(".report").width()/2,
  thisHeight = $(".report").height()/2,
  scrollHeight = $("body").scrollTop();
}

function delCom(obj,target,colse){
  rule();
  obj.click(function(){
    $("#layoutBg").remove();
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
//真实执行删除操作
function exeDelete(){
 $.post(pageUrl, { cmtId: commentId, cmd:"deleteCmt"},function(data){
   if(data.code == 0){
    if(isReply == 0){
      $("#r" + commentId).parent().parent().parent().remove();
      //评论数前端减1
      $("#commentCount").html(parseInt($("#commentCount").html())-1);
     }
     else{
      $("#r" + commentId).remove();
     }
   }
   else
   {
     showMessageBox(data.message);
   }   
   $(".deleteTips").hide();
   $("#layoutBg").remove();
   delCom($(".removeBtn"),$(".deleteTips"),$(".close,.reportCancel"));
  });
}

/** 回复评论 */
function replyComment(cmtId, parentId){
 var content = $("#replyAreaCenter" + cmtId).val();
 $.post(pageUrl, { cmtId: cmtId, cmd:"replyCmt", parentId: parentId, content:content},function(data){
   if(data.code == 0){
    $("#replyContent" + parentId).append(data.message);    
    $("#textareaReply" + cmtId).remove();   
    bindDeleteEvent();
    //移动到发布的内容的地方，
    $('html, body').animate({scrollTop: $('#r' + data.data).offset().top-200}, 200);    
   }
   else
   {
     showMessageBox(data.message);
   }
   $("#layoutBg").remove();
   $(".deleteTips").hide();
   delCom($(".removeBtn"),$(".deleteTips"),$(".close,.reportCancel"));   
 });
}

/** 页面的初始化 */
$(function(){  
  bindDeleteEvent();  
  //绑定发布评论事件
  $(".publishBtn").each(function(){
    $(this).bind("click",function(){
      //var cmtType=$(this).attr("cmtType");
      //var objectId=$(this).attr("articleId");
      //if(cmtType == "cmt")
      //{
       var content = $("#textAreaCenter" + objectId).val();
       $.post(pageUrl, {cmd: "comment",content:content},function(data){
       if(data.code == 0){        
        $("#cmt").after(data.message);
        $("#textAreaCenter" + objectId).val("");
        bindDeleteEvent();
        //评论数前端加1
        $("#commentCount").html(parseInt($("#commentCount").html())+1);
       }
       else
       {
         showMessageBox(data.message);
       }
       $(".deleteTips").hide();
       $("#layoutBg").remove();
       delCom($(".removeBtn"),$(".deleteTips"),$(".close,.reportCancel"));
       });
      //}
    });
  });
  
  //绑定更多评论事件
  $("#showMoreComment").bind("click",function(){
    var _self = $(this);
    var lastId = _self.prev().attr("cmtid");
    if(!lastId){
      lastId = 0;
     }
     $.get(operateUrl + "showMoreComment" + "&lastId=" + lastId, function(data){
      _self.before(data);
      bindDeleteEvent();
      $("#layoutBg").remove();
      $(".deleteTips").hide();
      delCom($(".removeBtn"),$(".deleteTips"),$(".close,.reportCancel")); 
      var allCount = parseInt($("#commentCount").html());
      if(isNaN(allCount)){
        allCount = Number.MAX_VALUE - 100;
      }
      if(allCount < _self.parent().children("dl").length){
        _self.remove();
      }      
    });
  });
});
