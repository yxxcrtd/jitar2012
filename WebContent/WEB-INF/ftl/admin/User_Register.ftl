<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>新用户注册</title>
    <link rel="icon" href="favicon.ico" />
    <link rel="shortcut icon" href="favicon.ico" />
    <link rel="styleSheet" type="text/css" href="css/manage.css" />
    <script src="js/jquery.js"></script>
    <script src="js/json2.js"></script>
    <script>
    function textCounter(field, countfield, maxlimit) {
        if (field.value.length > maxlimit) {
            field.value = field.value.substring(0, maxlimit);
        }
        else {
            countfield.value = maxlimit - field.value.length;
        }
    }
    function showAll() {
        var obj_grade = document.getElementById("grade");
        obj_grade.style.display = "";
        var obj_subject = document.getElementById("subject");
        obj_subject.style.display = "";
        var obj_all = document.getElementById("grade_subject");
        obj_all.style.display = "";
        <#if subject_need?? && grade_need??>
            <#if subject_need || grade_need>
                document.getElementById("showgradesubject").style.display = "inline";
            </#if>
        </#if>        
        <#if grade_need??>
            <#if grade_need>
                document.getElementById("grade_need").style.display = "inline";
            </#if>
        </#if>        
        <#if subject_need??>
            <#if subject_need>
                document.getElementById("subject_need").style.display = "inline";
            </#if>
        </#if>        
    }
    function isDisplaySubject() {
        var obj_grade = document.getElementById("grade");
        obj_grade.style.display = "";
        var obj_subject = document.getElementById("subject");
        obj_subject.style.display = "none";
        var obj_all = document.getElementById("grade_subject");
        obj_all.style.display = "";
        
        <#if subject_need?? && grade_need??>
            <#if subject_need || grade_need>        
                document.getElementById("showgradesubject").style.display = "none";
            </#if>
        </#if>        
        <#if grade_need??>
            <#if grade_need>
                document.getElementById("grade_need").style.display = "none";
            </#if>
        </#if>        
        <#if subject_need??>
            <#if subject_need>
                document.getElementById("subject_need").style.display = "none";
            </#if>
        </#if>        
        
        return;
    }
    function showAll2() {
        var obj_grade = document.getElementById("grade");
        obj_grade.style.display = "";
        var obj_subject = document.getElementById("subject");
        obj_subject.style.display = "";
        var obj_all = document.getElementById("grade_subject");
        obj_all.style.display = "";
        
        <#if subject_need?? && grade_need??>
            <#if subject_need || grade_need>        
                document.getElementById("showgradesubject").style.display = "none";
            </#if>
        </#if>        
        <#if grade_need??>
            <#if grade_need>
                document.getElementById("grade_need").style.display = "none";
            </#if>
        </#if>        
        <#if subject_need??>
            <#if subject_need>
                document.getElementById("subject_need").style.display = "none";
            </#if>
        </#if>  
        
        return;
    }    
        function uploadPhoto() {
            //window.showModelessDialog("/Personal/UploadPhoto", "", "center:no;dialogLeft:50px;dialogTop:10px;scroll:0;status:0;help:0;resizable:0;dialogWidth:900px;dialogHeight:600px");
            //window.showModalDialog("/Personal/UploadPhoto", window, "center:no;dialogLeft:50px;dialogTop:10px;scroll:0;status:0;help:0;resizable:0;dialogWidth:900px;dialogHeight:600px");
            window.open ("manage/UploadPhoto.jsp", "_blank");
        }     
        
        function setPhotoFile(imgfile){
            regForm.userIcon.value = imgfile; 
            var img = document.getElementById("icon_image");
            img.src = imgfile;
        }  
   </script>
    
    <style>
        ul {display:inline;margin:0;padding:0;border:0; }
        .right{text-align:right;}
    </style>
</head>
<body onLoad="document.regForm.loginName.focus();">
<div style="text-align:center">
    <h2>新用户注册</h2>
    <div id="info" style="display:none;color: #FF0000; font-size: 14px; font-weight: bold;background:#E5ECF4;padding:20px 0;"></div>
    <#if actionErrors?? >
        <table style="color:red;">
        <#list actionErrors as error>
          <tr style="text-align:left"><td><li>${error}</li></td></tr>
        </#list>
        </table>
     </#if>
</div>
<div>
<form name="regForm" action="register.action" method="post" autocomplete="off">
<table border="0" class="listTable" cellspacing="1">
<tr>
    <td style="text-align:right;width:200px">
        <b>用户登录名：</b>
    </td>
    <td>
        <input type="text" name="loginName" value="${user.loginName!?html}" maxlength="16" />
        <img id="v_loginName" style="display:none" />
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
        必须给出登录名, 长度在 4-16 个字符之间, 只能用英文字母或数字, 第一个字符必须是字母，字母必须是小写字符。
    </td>
</tr>
<tr>
    <td class="right">
        <b>登录密码：</b>
    </td>
    <td>
        <input type="password" name="userPassword" value="" />
        <img id="v_userPassword" style="display:none" />
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
        必须给出密码，密码长度6-20位。
    </td>
</tr>
<tr>
    <td class="right">
        <b>再次输入密码：</b>
    </td>
    <td>
        <input type="password" name="rePassword" value=""/>
        <img id="v_rePassword" style="display:none" />
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
        请确定您记住您的密码, 两次输入必须一致.
    </td>
</tr>
<tr>
    <td class="right">
        <b>电子邮件：</b>
    </td>
    <td>
        <input type="text" name="email" maxlength="100" value="${user.email!?html}" />
        <img id="v_email" style="display:none" />
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
         必须输入邮件地址<#if email_need??><#if email_need>, 且不能和别人重复，可能用来找回密码或者登录使用，请填写正确的电子邮件。</#if></#if>
    </td>
</tr>
<tr>
    <td class="right">
        <b>真实姓名：</b>
    </td>
    <td>
        <input type="text" name="trueName" maxLength="25" value="${user.trueName!?html}" />
        <img id="v_trueName" style="display:none" />
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font> 必须输入真实姓名.
    </td>
</tr>
<tr>
    <td class="right">
        <b>身份证号码：</b>
    </td>
    <td>
        <input type="text" name="IDCard" size="20" maxLength="18" value="${user.idCard!?html}" />
        <img id="v_IDCard" style="display:none" />
         ${idcard!}
    </td>
</tr>
<tr>
    <td class="right">
        <b>QQ号码：</b>
    </td>
    <td>
        <input type="text" name="QQ" size="20" maxLength="18" value="${user.qq!?html}" />
    </td>
</tr>           
<tr>
    <td class="right">
        <b>用户身份：</b>
    </td>
    <td>
        <input type="radio" name="role" value="3" onClick="showAll();" checked />教师
        <input type="radio" name="role" value="5" onClick="isDisplaySubject();" />学生
        <input type="radio" name="role" value="4" onClick="showAll2();" />教育局职工
    </td>
</tr>
<tr>
    <td class="right">
        <b>个人头像：</b>
    </td>
    <td>
        <input type="hidden" name="userIcon" value="images/default.gif" />
        <img id="icon_image" src="${Util.url(user.userIcon!'images/default.gif') }" width="64" height="64" border="0" style="float:left;margin-right:4px" />
        <!--
         <#if icon_list??>
            <select onchange="icon_changed(this)" onkeyup="icon_changed(this)">
                <option value="">选择一个头像</option>
                <#list icon_list as icon >
                    <option value="${icon}">${Util.fileName(icon)}</option>
                </#list>
            </select>
            注册完毕后还可以在后台进行修改，或者重新上传一个头像。
        </#if>
        <br/>
        -->
        <br/>
        <input type="button" id="btnUpload" name="btnUpload" value="上传头像"  onclick="uploadPhoto();"/>
        <script>
        function icon_changed(sel) {
            var src = sel.options[sel.selectedIndex].value;
            if (src == null || src == "") return;
            document.regForm.userIcon.value = src;
            var img = document.getElementById("icon_image");
            img.src = src;
        }
        </script>
    </td>
</tr>
<tr>
    <td class="right">
        <b>忘记密码时的问题：</b>
    </td>
    <td>
        <input type="text" name="question" maxlength="50" value="" />
        <img id="v_question" style="display:none" />
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
        用来找回密码。
    </td>
</tr>
<tr>
    <td class="right">
        <b>问题答案：</b>
    </td>
    <td>
        <input type="text" name="answer" maxlength="50" value="" />
        <img id="v_answer" style="display:none" />              
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font> 用来找回密码。
    </td>
</tr>
<tr>
    <td class="right">
        <b>工作室名称：</b>
    </td>
    <td>
        <input type="text" name="blogName" size="40" maxlength="40" value="${user.blogName!?html}" /> 默认为：'真实姓名'的工作室
    </td>
</tr>
<tr>
    <td align="right">
        <b>工作室标签：</b>
    </td>
    <td>
        <input type="text" name="userTags" size="50" value="${user.userTags!?html}" /> 以 ',' 逗号分隔.
    </td>
</tr>
<tr>
    <td class="right">
        <b>工作室简介：</b>
    </td>
    <td>
     <input name="blogIntroduce" style="width:90%" value="${user.blogIntroduce!?html}" maxlength="200" /><br/>最多填写200个汉字
    </td>
</tr>
<tr>
    <td class="right">
        <b>性别：</b>
    </td>
    <td>
        <input type="radio" name="gender" value="1" ${(user.gender == 1)?string('checked', '')} />男
        <input type="radio" name="gender" value="0" ${(user.gender == 0)?string('checked', '')} />女
    </td>
</tr>
<tr>
    <td class="right">
    <b>学段/学科：</b>
    </td>
    <td id="grade_subject">
    <select name="gradeId"  onchange="grade_changed(this)" id="grade">
      <option value="">选择学段年级</option>
        <#if grade_list??>
            <#list grade_list as grade>
                <option value="${grade.gradeId}">
                    <#if grade.isGrade>${grade.gradeName!?html}<#else>&nbsp;&nbsp;${grade.gradeName!?html}</#if>
                </option>
            </#list>
        </#if>
        </select>
        <select name="subjectId" id="subject">
        <option value="">选择所属学科</option>
            <#if subject_list??>
                <#list subject_list as msubj>
                    <option value="${msubj.msubjId}">
                        ${msubj.msubjName!?html}
                    </option>
                </#list>
            </#if>
        </select>               
        <img id="v_subjectGradeId" style="display:none" />
        <#if subject_need?? && grade_need??>
            <#if subject_need || grade_need>
            <font id="showgradesubject" style="color: #FF0000; font-size: 14px; font-weight: bold;display:inline">*</font>&nbsp;
            </#if>
            <#if grade_need??> 
            <#if grade_need>
                <span id="grade_need" style="display:inline">学段必填</span>
            </#if>
            </#if>                    
            <#if subject_need??> 
            <#if subject_need>
            <span id="subject_need" style="display:inline">
             &nbsp;&nbsp;学科必填
             </span>
            </#if>
            </#if> 
        </#if>
        <span id="subject_loading" style="display:none">
            <img src="manage/images/loading.gif" align="absmiddle" hsapce="3" />正在加载学科信息...
        </span>
    </td>
</tr>
<tr>
    <td class="right">
        <b>工作室分类：</b>
    </td>
    <td>                        
        <select name="categoryId">
            <option value="">选择工作室分类</option>
                <#if syscate_tree??>
                    <#list syscate_tree.all as category>
                        <option value="${category.id}">
                            ${category.treeFlag2} ${category.name}
                        </option>
                    </#list>
                </#if>
        </select>
    </td>
</tr>
<tr>
    <td class="right">
        <b>所在机构：</b>
    </td>
    <td>
        <span id='unitName'></span>
        <input type='hidden' id='unit_id' name='unitId' value='' />
        <input type='button' value='请选择…' onclick='selectUnit()' />
        <img id="v_unitId" style="display:none" />
        <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
    </td>
</tr>
<!--
<tr>
    <td class="right">
        <b>输入验证码：</b>
    </td>
    <td style="vertical-align:top">
     <input type="text" name="verifyCode" style="height:26px;" />
     <img src="manage/verifyCodeServlet" style="vertical-align:top" onclick='this.src="manage/verifyCodeServlet?" + (new Date()).getTime();'/>
     <img id="v_verifyCode" style="display:none" />
    </td>
</tr>
-->
<tr>
    <td></td>
    <td>
        <input class="button" style="cursor: pointer;" type="button" onclick="validateInputData()" value="  注    册  " />&nbsp;&nbsp;&nbsp;&nbsp;                        
        <input class="button" style="cursor: pointer;" type="reset" value=" 重 置 " />
    </td>
</tr>
</table>
</form>
</div>
<script>
var okImage = "images/ok.gif", errorImage = "images/dele.gif";
function validateInputData() {
    $("#info").css("display", "none");
    var json = {
        "cmd": "save",
        "loginName": $("input[name='loginName']").val(),
        "userPassword": $("input[name='userPassword']").val(),
        "rePassword": $("input[name='rePassword']").val(),
        "email": $("input[name='email']").val(),
        "trueName": $("input[name='trueName']").val(),
        "IDCard": $("input[name='IDCard']").val(),
        "QQ": $("input[name='QQ']").val(),
        "role": $("input[name='role'][checked]").val(),
        "userIcon": $("input[name='userIcon']").val(),
        "question": $("input[name='question']").val(),
        "answer": $("input[name='answer']").val(),
        "blogName": $("input[name='blogName']").val(),
        "userTags": $("input[name='userTags']").val(),
        "blogIntroduce": $("input[name='blogIntroduce']").val(),
        "gender": $("input[name='gender'][checked]").val(),
        "gradeId": $("select[name='gradeId']").val(),
        "subjectId": $("select[name='subjectId']").val(),
        "categoryId": $("select[name='categoryId']").val(),
        "unitId": $("input[name='unitId']").val(),
        "verifyCode": $("input[name='verifyCode']").val()
    }
    /*
    str = ""
    for(v in json)
    str += v + " : " + json[v] + "\r\n"
    alert(str)
    */
    var request = $.ajax({
        type: "post",
        url: "register.action?t" + (new Date()).getTime(),
        cache: false,
        data: json,
        dataType: "json"
    });
    request.done(function (data) {
        $("#info").css("display", "");
        switch (data.StatusCode) {
            case "0":
                $("#info").css("display", "none");
                alert("注册成功。");
                window.location = "index.action";
                break;
            case "1": //只是提示简单信息
                $("#info").html(data.StatusText);
                break;
            case "2": //具有多行的提示信息，是一个数组
                var al = data.StatusText;
                var errorInfo = "";
                $.each(al, function (index, v) {
                    $("#v_" + v.key).css("display", "");
                    if (v.status == "0") {
                        $("#v_" + v.key).attr("src", okImage);
                    }
                    else {
                        $("#v_" + v.key).attr("src", errorImage);
                        errorInfo += "<li>" + v.text + "</li>";
                    }
                })
                if (errorInfo.length > 0) {
                    $("#info").html(errorInfo);
                }
                else {
                    $("#info").css("display", "none");
                }
                break;
        }
    });

    request.fail(function (jqXHR, textStatus) {
        $("#info").html(jqXHR.responseText);
    });

}

function selectUnit() {
    window.open('jython/get_unit_list.py', 'unit', 'width=800,height=600,resizable=1,scrollbars=1');
}
function grade_changed(sel) {
    // 得到所选学科.
    var gradeId = sel.options[sel.selectedIndex].value;
    var subject_sel = document.regForm.subjectId;

    if (gradeId == null || gradeId == "" || gradeId == 0) {
        clear_options(subject_sel);
        add_option(subject_sel, '', '选择学科');
        return;
    }
    subject_sel.disabled = true;
    $('subject_loading').css("display", "");

    // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
    url = 'manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
    $.get(url, function (data) {
        var options = eval(data);
        clear_options(subject_sel);
        add_option(subject_sel, '', '选择学科');
        for (var i = 0; i < options.length; ++i) {
            add_option(subject_sel, options[i][0], options[i][1]);
        }
        $('subject_loading').css("display", "none");
        subject_sel.disabled = false;
    });
}
function clear_options(sel) {
    sel.options.length = 0;
}
function add_option(sel, val, text) {
    if (val == '') {
        sel.options[sel.options.length] = new Option(text, val, true, true);
    }
    else {
        sel.options[sel.options.length] = new Option(text, val);
    }
}
</script>
</body>
</html>