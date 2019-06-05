<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>课题组人员信息</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
  <script src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='${SiteUrl}manage/group.py?cmd=home&amp;groupId=${group.groupId}'>课题组管理首页</a>
  &gt;&gt; <span>课题负责人信息</span>
</div>
<br/>

<form name='theForm' action='${SiteUrl}manage/group_ktusers.py' method='post'>
  <input type='hidden' name='cmd' value='save' />  
  <input type='hidden' name='id' value='${ktuser.id}' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <table class='listTable' cellspacing='1'>
        <tr>
            <td style="width:150px;text-align:right">
                课题负责人：
            </td>
            <td>
              ${ktuser.teacherName!?html}
            </td>
        </tr>
        <tr>
        <td style="text-align:right">
        性别：
        </td>    
        <td>
            ${ktuser.teacherGender!?html}
        </td>
        </tr>
        <tr>
        <td style="text-align:right">
        所在单位：
        </td>    
        <td>
            ${ktuser.teacherUnit!?html}
        </td>
        </tr>
        <tr>
        <td style="text-align:right">
        行政职务：
        </td>    
        <td>
             <input type="text" name="teacherXZZW" value="${ktuser.teacherXZZW!?html}"/>
        </td>
        </tr>
        <tr>
        <td style="text-align:right">
        专业职务：
        </td>         
        <td>
             <input type="text" name="teacherZYZW" value="${ktuser.teacherZYZW!?html}"/>
        </td> 
        </tr>
        <tr>
        <td style="text-align:right">
        学历：
        </td> 
        <td>
             <input type="text" name="teacherXL" value="${ktuser.teacherXL!?html}"/>
        </td> 
        </tr>
        <tr>
        <td style="text-align:right">
        学位：
        </td>         
         <td>
             <input type="text" name="teacherXW" value="${ktuser.teacherXW!?html}"/>
        </td>  
      </tr>
        <tr>
        <td style="text-align:right">
        研究专长：
        </td>         
         <td>
             <textarea name="teacherYJZC" style="width:500px;height:150px">${ktuser.teacherYJZC!?html}</textarea>
        </td>  
      </tr>      
    </tbody>
  </table>
  
    <div class='funcButton'>
      <input type='button' class='button' value=' 保 存  ' onclick='javascript:save_m();' />&nbsp;&nbsp;
      <input type='button' class='button' value=' 返 回 ' onclick='javascript:return_m();' />
    </div>
</form>


<script language='javascript'>
 
function save_m() {
    document.theForm.cmd.value="save";
    document.theForm.submit();
}


function return_m(cmd) {
    document.theForm.cmd.value="list";
    document.theForm.submit();
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
