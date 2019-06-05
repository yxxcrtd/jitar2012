
$(function()
{
  $("#secSelectWrap3 ul li").each(function(){
    $(this).bind("click",function(){
      $("#secInput3").attr("subjectId",$(this).attr("value"));
      $("#secInput3").val($(this).children(":first").text());
    });    
  });
  
  $("#secSelectWrap2 ul li").each(function()
  {
	  
    $(this).bind("click", function()
    {
      var gradeId = $(this).attr("value");
      $("#secInput2").attr("gradeId",gradeId);
      //alert(gradeId);
      // 加载学科
      $.get("manage/admin_subject.py?cmd=subject_options&gradeId=" + gradeId + "&t=" + (new Date()).getTime()).done(function(data)
      {        
        var shtml = "<li value='0'><a href='javascript:void(0);'>全部学科</a></li>";
        var arr = eval(data);
        for ( var i = 0; i < arr.length; i++)
        {
          shtml += "<li value='" + arr[i][0] + "'><a href='javascript:void(0);'>" + arr[i][1] + "</a></li>";
        }
        
        $("#secSelectWrap3 ul li").remove();
        
        $("#secInput3").val("");
        $("#secInput3").attr("subjectId","0");
        $("#secSelectWrap3 ul").html(shtml);
        
        $("#secSelectWrap3 ul li").each(function(){
          $(this).bind("click",function(){
            $("#secInput3").attr("subjectId",$(this).attr("value"));
            $("#secInput3").val($(this).children(":first").text());
            $("#secSelectWrap3").hide();            
          });         
          
        });
        
        //scrollBarShow($("#secSelectWrap2").children("ul"));
        
        
        // 绑定事件。
        //$("#secSelectWrap2 ul li").one("click", function()
       // {
        //  alert($(this).attr("value"));
         
       // });        
      });
    });
  });
});
