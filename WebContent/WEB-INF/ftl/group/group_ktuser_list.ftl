<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理课题组</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='${SiteUrl}manage/group.py?cmd=home&amp;groupId=${group.groupId}'>课题组管理首页</a>
  &gt;&gt; <span>课题负责人管理</span>
</div>
<br/>

<form name='theForm' action='${SiteUrl}manage/group_ktusers.py' method='post'>
  <input type='hidden' name='cmd' value='list' />  
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <table class='listTable' cellspacing='1'>
    <thead>
      <tr>
        <th width='48'>选择</th>
        <th width='260'>课题负责人</th>
        <th width='260'>所在单位</th>
        <th width='160'>行政职务</th>
        <th width='100'>专业职务</th>
        <th width='100'>学位</th>
        <th width='100'>学历</th>
        <th width='50'>操作</th>
      </tr>
    </thead>
    <tbody>
    
    <#list ktUserlist as ktuser>
        <tr>
            <td>
                <input type='checkbox' name='ktuId' value='${ktuser.id}' />${ktuser.id}
            </td>
            <td>
                ${ktuser.teacherName!?html}
            </td>
        <td>
            ${ktuser.teacherUnit!?html}
        </td>
        <td>
             ${ktuser.teacherXZZW!?html}
        </td>
        <td>
             ${ktuser.teacherZYZW!?html}
        </td>  
        <td>
             ${ktuser.teacherXL!?html}
        </td>  
         <td>
             ${ktuser.teacherXW!?html}
        </td>  
        <td>
          <a href='${SiteUrl}manage/group_ktusers.py?cmd=edit_ktuser&amp;groupId=${group.groupId}&amp;id=${ktuser.id}' >修改</a>&nbsp;&nbsp;
          <a href='${SiteUrl}manage/group_ktusers.py?cmd=delete_ktuser&amp;groupId=${group.groupId}&amp;id=${ktuser.id}' onclick='return confirm_delete();'>删除</a>
        </td>
      </tr>
    </#list>
    </tbody>
  </table>
  
  <div class='pager'>
    <#include '../inc/pager.ftl' >
  </div>
    <div class='funcButton'>
      <input id="selAll" class='button' name="Sel_All" onclick="on_AllSelect(theForm)" type="button" value="全部选择" />&nbsp;&nbsp;  
      <input type='button' class='button' value=' 增 加 ' onclick='javascript:add_m();' />&nbsp;&nbsp;
      <input type='button' class='button' value=' 删 除 ' onclick='javascript:delete_m();' />
    </div>
</form>

<form name="savefrm" method="post" action="${SiteUrl}manage/group.action">
    <input type="hidden" name="cmd" value="savektuser">
    <input type='hidden' name='groupId' value='${group.groupId}' />
    <input type="hidden" name="teacherId" id="teacherId" value="">
    <input type="hidden" name="teacherName" id="teacherName" value="">
    <div id="ktUserInfo"></div>
    <div class='funcButton'>
        <input type="button" id="ktuserButton" style="display:none" onclick="saveKtUser();" value=" 保存课题负责人 ">
    </div>
</form> 

<script language='javascript'>
 //全部选择和取消全选.
  var blnIsChecked = true;
  function on_AllSelect(oForm){
        for (var i = 0; i < oForm.elements.length; i++) {
                if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
                    oForm.elements[i].checked = blnIsChecked;
                }
                
        }
        if(oForm.elements["selAll"]) {
            if(blnIsChecked) {
                oForm.elements["selAll"].value = "取消全选";
            } else {
                oForm.elements["selAll"].value = "全部选择"; 
            }
        }
        blnIsChecked = !blnIsChecked;
    }
function confirm_delete() {
  return window.confirm('你是否确定删除课题负责人?');
}
function getTheForm() {
  return document.forms['theForm'];
}

function delete_m() {
  if (has_item_selected()) {
    if (confirm('您是否确定删除课题负责人??') == false)
      return;
  }
  submitCommand('delete');
}


function submitCommand(cmd) {
  if (has_item_selected() == false) {
    alert('没有选择要操作的课题负责人.');
    return;
  }
  var form = getTheForm();
  form.cmd.value = cmd;
  form.submit();
}
function has_one_selected() {
  var ids = document.getElementsByName('ktuId');
  if (ids == null) return false;
  var count = 0;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) ++count;
  }
  return (count == 1);
}
function has_item_selected() {
  var ids = document.getElementsByName('ktuId');
  if (ids == null) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}

function saveKtUser()
{
    var v=document.getElementById("teacherId").value;
    if(v=="")
    {
        alert("请选择课题负责人");
        return;
    }
  document.savefrm.submit();    
}
    function add_m()
    {
        var vReturnValue=""; 
        vReturnValue = window.showModalDialog('../selectUser.action?showgroup=0&singleuser=0&more=1','','dialogWidth:700px;dialogHeight:450px;scroll:yes;status:no');
        //vReturnValue = window.open("${SiteUrl}manage/common/user_select.action?type=multi&idTag=uid&titleTag=utitle");
        if(vReturnValue==undefined){
            return;
        }
        if(vReturnValue==""){
            return;
        }        
        getUserList(vReturnValue);
        document.getElementById("ktuserButton").style.display="block";
    }
    function getUserList(userids)
    {
        var arr=userids.split("|");
        document.getElementById("teacherId").value=arr[0];
        document.getElementById("teacherName").value=arr[1];
        var names=arr[1].split(",");
        var genders=arr[3].split(",");
        var units=arr[4].split(",");
        var _html="";
         var us=arr[0].split(",");
        for(i=0;i<us.length;i++)
        {     
            _html=_html+"<table border='0' cellspacing='0' style='width:100%' id='ktTable"+us[i]+"'>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>姓名:</td>";
            _html=_html+"        <td>"+names[i]+"</td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>性别:</td>";
            _html=_html+"        <td>"+ genders[i] +"</td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>所在单位:</td>";
            _html=_html+"        <td>"+ units[i] +"</td>";    
            _html=_html+"    </tr>";            
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>行政职务:</td>";
            _html=_html+"        <td><input type='text' name='gktXZZW"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>专业职务:</td>";
            _html=_html+"        <td><input type='text' name='gktZYZW"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>学历:</td>";
            _html=_html+"        <td><input type='text' name='gktXL"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>学位:</td>";
            _html=_html+"        <td><input type='text' name='gktXW"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>研究专长:</td>";
            _html=_html+"        <td><textarea name='gktYJZC"+us[i]+"' style='width:500px;height:80px'></textarea></td>";    
            _html=_html+"    </tr>";
            _html=_html+"</table>";
            _html=_html+"<input type='hidden' name='xkxdNum"+us[i]+"' id='xkxdNum"+us[i]+"' value=1>";
            _html=_html+"<input type='hidden' name='KTteacherId' value="+us[i]+">";
            _html=_html+"<input type='hidden' name='KTteacherName"+us[i]+"' value='"+names[i] +"'>";
            _html=_html+"<input type='hidden' name='KTteacherGender"+us[i]+"' value='"+genders[i] +"'>";
            _html=_html+"<input type='hidden' name='KTteacherUnit"+us[i]+"' value='"+units[i] +"'>";
            _html=_html+"<br/><hr/><br/>";
        }     
        document.getElementById("ktUserInfo").innerHTML=_html;
    }
    
function appendXKXD(btn,userid,xdIndex)
{
        var sHtml;
        var oRow1;
        var oTable;
        var oCell1;
        oTable = document.getElementById("ktTable"+userid);
        //alert("rows.length=" + oTable.rows.length);
        oRow1 = oTable.insertRow(oTable.rows.length);
        oCell2 = oRow1.insertCell(0);
        oCell1 = oRow1.insertCell(1);
        //oCell1.height = "25";
        sHtml= "<select name='gkt_gradeId_"+ userid +"_"+ xdIndex +"'  id='gkt_gradeId_"+ userid +"_"+ xdIndex +"'  onchange='grade_changed(this,\"gkt_subjectId_"+userid+"_"+xdIndex+"\",\"loading"+userid+"_"+xdIndex+"\")'>";
        sHtml=sHtml+gradeoption;
        sHtml=sHtml+"</select>";
        sHtml=sHtml+"  <select name='gkt_subjectId_"+userid+"_"+xdIndex+"' id='gkt_subjectId_"+userid+"_"+xdIndex+"'>";
        sHtml=sHtml+subjectoption;
        sHtml=sHtml+"   </select>";
        sHtml=sHtml+"   <span id='loading"+userid+"_"+xdIndex+"' style='display:none'>";
        sHtml=sHtml+"   <img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...";
        sHtml=sHtml+"   </span>";
        sHtml=sHtml+"   <input type='button' name='addxkxd' onclick='appendXKXD(this,"+userid+","+ (xdIndex+1) +")' value='增加学科学段'>";
        oCell1.innerHTML = sHtml;
        btn.style.display="none";
        document.getElementById("xkxdNum"+userid).value=xdIndex;
}    

function grade_changed(sel,subId,loadingId)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.getElementById(subId);

  if (gradeId == null || gradeId == '' || gradeId == 0) {
    clear_options(subject_sel);
    add_option(subject_sel, '', '选择学科');
    return;
  } 
  subject_sel.disabled = true;
  var img = document.getElementById(loadingId);
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
    sel.options[sel.options.length] = new Option(text,val)
    
}
</script>

</body>
</html>
