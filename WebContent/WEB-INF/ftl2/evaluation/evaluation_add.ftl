<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
  <style type="text/css">
  #t1 tr td{background:#fff;vertical-align:top;}
  .txt {width:80%;}
  .txt:hover{outline:solid orange 1px}
  .div_title { TEXT-ALIGN: center; MARGIN: 10px 0px; FONT-SIZE: 12pt; FONT-WEIGHT: bold }
  .ListInfo { BORDER-BOTTOM: #000 0px; BORDER-LEFT: #000 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; WIDTH: 100%; PADDING-RIGHT: 0px; BORDER-COLLAPSE: collapse; BORDER-TOP: #000 0px; BORDER-RIGHT: #000 0px; PADDING-TOP: 0px }
  .ListInfo INPUT { BORDER-BOTTOM: #000000 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 2px; PADDING-LEFT: 2px; PADDING-RIGHT: 2px; BACKGROUND: #fff; COLOR: #000; FONT-SIZE: 12px; BORDER-TOP: 0px; BORDER-RIGHT: 0px; PADDING-TOP: 2px; }
  HR { BORDER-BOTTOM: #000000 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; BACKGROUND: #fff; COLOR: #000; BORDER-TOP: 0px; BORDER-RIGHT: 0px;}
  #v0{margin-left:195px;}
 </style>
  
  <title>发起评课 <#include '/WEB-INF/ftl/webtitle.ftl'></title>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function(){
      $('#v1').mouseover(function(){
         $(this).css({'cursor':'pointer','color':'red'});
      }).mouseout(function(){
         $(this).css('color','black');
      })
    });
    function selectUsers()
    {
        //singleuser=0 多选用户  =1单选用户
        //inputUser_Id 和 inputUserName_Id 是表单中的 用户ID和用户Name   必须使用id
        //showgroup=0 不显示群组 =1显示群组
        //单选用户时可以不设置showgroup，肯定不显示群组；多选用户可以设置是否显示群组
        var vReturnValue=""; 
        vReturnValue = window.showModalDialog('selectUser.action?showgroup=0&singleuser=1','','dialogWidth:700px;dialogHeight:450px;scroll:yes;status:no');
        //vReturnValue = window.open("${SiteUrl}manage/common/user_select.action?type=multi&idTag=uid&titleTag=utitle");
        if(vReturnValue==undefined){
            return;
        }
        if(vReturnValue==""){
            return;
        }        
        var arr=vReturnValue.split("|");
        document.getElementById("teacherId").value=arr[0];
        document.getElementById("teacherName").value=arr[1];
        document.getElementById("_personmsg").style.display="block";
        //处理
        getUserEvaluationList(arr[0]);
    }
    
    function getUserEvaluationList(userid)
    {
      var url = 'evaluations.action?cmd=list&userId=' + userid + '&listType=1&tmp=' + Math.random();
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) { 
            var html = xport.responseText;
            document.getElementById("list_left").innerHTML=html;
          }
      });
      
      url = 'evaluations.action?cmd=list&userId=' + userid + '&listType=0&tmp=' + Math.random();
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) { 
            var html = xport.responseText;
            document.getElementById("list_right").innerHTML=html;
          }
      });
      
      document.getElementById("userEvaluationsList").style.display="block";
    }
    
    function userchanged()
    {
        //alert(document.getElementById("teacherName").value);
        //alert(document.getElementById("teacherId").value);
    }
  function checkInput(ff)
  {
   if(ff.teacherId.value=="")
   {
    alert("请输入授课人");
    return false;
   }
     
   if(ff.titleName.value =="")
   {
    alert("请输入评课课题。");
    return false;
   }
   if(ff.gradeId.value =="" || ff.subjectId.value =="")
   {
    alert("请输入学科学段。");
    return false;
   }
   if(ff.teachDate.value =="")
   {
    alert("请选择授课时间。");
    return false;
   }   

   if(ff.startDate.value =="" || ff.endDate.value =="")
   {
    alert("请选择评课开放时间。");
    return false;
   } 
    var btemplate=false;
    var ids = document.getElementsByName("template");
    for (i = 0;i<ids.length;i++) {
      if(ids[i].checked)
      {
        btemplate=true;
        break;
        }
    }
    
   if(btemplate ==false)
   {
    alert("请选择模板。");
    return false;
   }      
   
   return true;
  }
  function showevaluationContent()
  {
    if(document.getElementById("content").style.display=="none")
    {
        var v=document.getElementById("teacherId").value;
        if(v=="")
        {
            alert("请先选择授课人");
            return;
        }
        document.getElementById("content").style.display="block";
        document.getElementById("AHref").innerText=">>>";
    }
    else{
        document.getElementById("content").style.display="none";
        document.getElementById("AHref").innerText="<<<";
    }
  }
  function uploadresource()
  {
    var vReturnValue = window.showModalDialog('selectuploadresource.py','','dialogWidth:780px;dialogHeight:600px;scroll:yes;status:no');
    if(vReturnValue==undefined){
        return;
    }
    if(vReturnValue==""){
        return;
    }      
    //alert("上载的资源Id="+vReturnValue);
     var url = '${SiteUrl}resources.action?cmd=query&id=' + vReturnValue + '&tmp=' + Math.random();
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) {
            var html = xport.responseText;
            //alert(html);
            html=html+"<div id=\"_resourceList\"></div>";
            document.getElementById("_resourceList").outerHTML=html;
          }
      });    
  }
  function selectresource()
  {
    var vReturnValue = window.showModalDialog('${SiteUrl}manage/resource.action?cmd=userresource','','dialogWidth:780px;dialogHeight:600px;scroll:yes;status:no');
    if(vReturnValue==undefined){
        return;
    }
    if(vReturnValue==""){
        return;
    }      
    //alert("选择的资源Id="+vReturnValue);
     var url = '${SiteUrl}resources.action?cmd=query&id=' + vReturnValue + '&tmp=' + Math.random();
     //alert(url);
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) {
            var html = xport.responseText;
            //alert(html);
            html=html+"<div id=\"_resourceList\"></div>";
            document.getElementById("_resourceList").outerHTML=html;
          }
      });      
  }
  function uploadvideo()
  {
      var vReturnValue = window.showModalDialog('selectuploadvideo.py','','dialogWidth:780px;dialogHeight:600px;scroll:yes;status:no');
    if(vReturnValue==undefined){
        return;
    }
    if(vReturnValue==""){
        return;
    }      
    //alert("上载的视频Id="+vReturnValue);
    var url = '${SiteUrl}videos.action?cmd=query&id=' + vReturnValue + '&tmp=' + Math.random();
    //alert("url="+url);
    //window.open(url,"_blank"); 
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) {
            var html = xport.responseText;
            html=html+"<div id=\"_videoList\"></div>";
            document.getElementById("_videoList").outerHTML=html;
          }
      });       
  }
  function selectvideo()
  {
      var vReturnValue = window.showModalDialog('${SiteUrl}manage/video.action?cmd=uservideo','','dialogWidth:780px;dialogHeight:600px;scroll:yes;status:no');
    if(vReturnValue==undefined){
        return;
    }
    if(vReturnValue==""){
        return;
    }      
    //alert("选择的视频Id="+vReturnValue);
     var url = '${SiteUrl}videos.action?cmd=query&id=' + vReturnValue + '&tmp=' + Math.random();
     //alert(url);
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) {
            var html = xport.responseText;
            //html=html+"<div id=\"_videoList\"></div>";
            document.getElementById("_videoList").innerHTML=html;
          }
      });     
  }
  function removeVideo(id)
  {
     if(confirm("确认去除这个视频吗?")==false){return;}
     document.getElementById("tv"+id).outerHTML="";
  }
  function removeResource(id)
  {
     if(confirm("确认去除这个资源吗?")==false){return;}
     document.getElementById("tr"+id).outerHTML="";
  }
  </script>
 </head>
 <body>
<#include '../site_head.ftl'>
<div style='height:8px;font-size:0;margin-top:15px;'></div>
<div style="border:1px solid gray;width:1000px;margin:0 auto;background-color:white;padding:10px 10px;">
	    <form method="post" action="evaluations.action">
	    <input type="hidden" name="act" value="save"/> 
	    <input type="hidden" name="cmd" value="save"/>
	    <div>
	            请选择授课人:<input  name="teacherName" id="teacherName"  style="width:140px;" readonly="readonly" class="txt" onChange="userchanged()"/><font style="color:red">*</font>&nbsp;&nbsp;<input id='v1' type="button" name="selectuser" value="点此选择授课人..." onClick="selectUsers();"/>
	            <input type="hidden" name="teacherId" id="teacherId" value=""/>
	    </div> 
	    <div id="_personmsg" style="height:26px;display:none;">
	        <font color="red">请先检查该授课人的评课记录，确定准备新增加的评课是否已经存在</font>
	    </div>
	    <div id="userEvaluationsList" class="ListInfo" style="width:100%;display:none;">
	        <div id="userEvaluationsList_left"  style="display:block;float:left;width:500px;">
	           <FIELDSET>
	                <LEGEND><font style="font-size:9pt;color:#95a9b4">该授课人正在进行的评课课题</font></LEGEND>
	                <div id="list_left"></div>
	            </FIELDSET>
	        </div>
	        <div id="userEvaluationsList_right" style="display:block;float:left;width:500px">
	           <FIELDSET>
	                <LEGEND><font style="font-size:9pt;color:#95a9b4">该授课人已经完成的评课课题</font></LEGEND>
	                <div id="list_right"></div>
	            </FIELDSET>        
	        </div>
	        <div style='clear:both;'></div>
	    </div> 
       <hr width='85%'/>
       <div>点击这里新增评课 <a href="#"  id="AHref" onClick="showevaluationContent();"><<<</a></div>
       <div style='height:8px;font-size:0;'></div>
    <div id="content" style="display:none;">   
    <table id="t1" border="0" cellspacing="1" cellpadding="2" style="background:#B0BEC7;margin:auto;width:800px;">
    <tr>
    <td>课题名称<font style="color:red">*</font>：</td><td><input name="titleName" class="txt"/></td>
    </tr>
    <tr>
    <td>学段/学科<font style="color:red">*</font>：</td><td>
    <select name="gradeId" onchange='grade_changed(this)'>
      <option value=''>选择所属学段</option>
      <#if grade_list??>
        <#list grade_list as grade>
            <option value="${grade.gradeId}" >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
        </#list>
      </#if>                
      </select>
      <select name="subjectId">
      <option value=''>选择所属学科</option>
      <#if subject_list??>
        <#list subject_list as msubj>
            <option value="${msubj.msubjId}" >${msubj.msubjName!?html}</option>
        </#list>
      </#if>
      </select>
      <span id='subject_loading' style='display:none'><img src='${SiteUrl}manage/images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...</span> 
    
    </td>
    </tr>
    <tr>
    <td>辅导老师：</td><td><input type="text" class="txt" name="fdTeacher" id="fdTeacher" style="width:120px"/></td>
    </tr>     
    <tr>
    <td>授课时间<font style="color:red">*</font>：</td><td><input name="teachDate" id="teachDate"  class="Wdate" onClick="WdatePicker()" style="width:120px"/></td>
    </tr>    
    <tr>
    <td>评课开放时间<font style="color:red">*</font>：</td>
    <td>
        <input name="startDate" id="startDate" class="Wdate" onClick="WdatePicker()" style="width:120px"/>&nbsp;&nbsp;到&nbsp;&nbsp;
        <input name="endDate" id="endDate"  class="Wdate" onClick="WdatePicker()" style="width:120px"/>
    </td>
    </tr>   
    <tr>
    <td>相关视频：</td>
    <td>
        <table border="0" cellspacing="1" cellpadding="1" style="width:100%">
        <tr>
            <th>标题</th>
            <th style="width:100px">状态</th>
            <th style="width:60px">删除</th>
        </tr>   
        </table> 
        <div id="_videoList"></div>
        <div>
            <input type="button" name="upload1" value="上传视频" onClick="uploadvideo();"/>
            <input type="button" name="upload2" value="选择视频" onClick="selectvideo();"/>
        </div>
    </td>
    </tr> 
      
    <tr>
    <td>相关资源：</td>
    <td>
        <table border="0" cellspacing="1" cellpadding="1" style="width:100%">
        <tr>
            <th>标题</th>
            <th style="width:100px">状态</th>
            <th style="width:60px">删除</th>
        </tr>    
        </table> 
        <div id="_resourceList"></div>
        <div>
            <input type="button" name="upload3" value="上传资源" onClick="uploadresource();"/>
            <input type="button" name="upload4" value="选择资源" onClick="selectresource();"/>
        </div>
    </td>
    </tr>  
    <tr>
    <td>选择评课模板：</td>
        <td>
          <#if template_list??>
              <#list template_list as t>            
                <input type="checkbox" name="template" value="${t.evaluationTemplateId}"/><span  style="font-size:small;">${t.evaluationTemplateName!?html}</span></br>
              </#list>
           </#if>     
        </td>
    </tr>    
    </table>
    <div style="text-align:Center"><input type="submit" value="保存" style="cursor:pointer;margin-top:10px;width:80px;height:30px;background-color:blue;color:white;font-weight:bold;"  onclick="return checkInput(this.form)" /></div>
    </div>
    </form>
</div>

<#include '../footer.ftl'>
<script type="text/javascript">
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.forms[0].subjectId;

  if (gradeId == null || gradeId == '' || gradeId == 0) {
    clear_options(subject_sel);
    add_option(subject_sel, '', '选择学科');
    return;
  } 
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = '${SiteUrl}manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '选择学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}
function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  sel.options[sel.options.length] = new Option(text.replace(/&nbsp;/g," "),val)    
}
</script>
</body>
</html>