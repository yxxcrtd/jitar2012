<script src='${ContextPath}js/jitar/md5.js' type='text/javascript'></script>
<script type="text/javascript" src="${SiteUrl}zdsoft/script/sha1.js"></script>

<script>
  function userSSOLogin() {
      if (document.getElementById("loginUserName").value == "") {
          window.alert("请输入用户名！");
          document.getElementById("loginUserName").focus();
          return false;
      }
      if (document.getElementById("loginUserPassword").value == "") {
          window.alert("请输入密码！");
          document.getElementById("loginUserPassword").focus();
          return false;
      }
      if($("#aremember").attr("class")=="check checked"){
          SetCookie("loginName",document.getElementById("loginUserName").value);
      }else{
          delCookie("loginName");
      }        
      username = document.getElementById("loginUserName").value;
      password = document.getElementById("loginUserPassword").value;
      
      var backurl = encodeURIComponent("${FullUrl!}login2/verify.jsp?returnurl=" + encodeURIComponent(window.location.href));
      postData = "username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(hex_md5(password)) +"&clientCode=${ClientCode!}&backurl="+backurl+"&clientLoginUrl=${FullUrl!}login2/homelogin.jsp";
      url = "${SSOServerLoginURL!}";
      window.open("${SSOServerLoginURL!}?"+postData,"loginframe");  //loginframe
      
      /* //如果要使用下面的代码，请使用jquery的 $.get()方法，core.js和jquery.js会冲突。
      new Ajax.Request("md5", { 
      method: 'get',
      parameters: "password="+password,
      onSuccess: function(xhr) {
          password = xhr.responseText;
          var backurl = encodeURIComponent("${FullUrl!}login2/verify.jsp?returnurl=" + encodeURIComponent(window.location.href));
          postData = "username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password) +"&clientCode=${ClientCode!}&backurl="+backurl+"&clientLoginUrl=${FullUrl!}login2/homelogin.jsp";
          url = "${SSOServerLoginURL!}";
          window.open("${SSOServerLoginURL!}?"+postData,"loginframe");  //loginframe
        
        }, 
      onFailure:function(xhr){alert('出现意外！' + xhr.responseText);}
      });
      */

  }
  function ShowLoginMsg(msg){
      document.getElementById("showloginerror").innerHTML = msg;
  }
  
  function checkRemember(){
      if($(arguments[0]).attr("class")=="check checked"){
          $(arguments[0]).attr("class","check");
          SetCookie("aclass","check");
      }else{
          $(arguments[0]).attr("class","check checked");
          SetCookie("aclass","check checked");
      }
  }
  //写cookies
  function SetCookie(name,value)//两个参数，一个是cookie的名子，一个是值
  {
      var Days = 30; //此 cookie 将被保存 30 天
      var exp  = new Date();    //new Date("December 31, 9998");
      exp.setTime(exp.getTime() + Days*24*60*60*1000);
      document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
  }
  function getCookie(name)//取cookies函数        
  {
      var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
       if(arr != null) return unescape(arr[2]); return null;
  
  }
  function delCookie(name)//删除cookie
  {
      var exp = new Date();
      exp.setTime(exp.getTime() - 1);
      var cval=getCookie(name);
      if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
  }
  $(function(){
      if(getCookie("aclass")!=null){
          $("#aremember").attr("class",getCookie("aclass"));
          if($("#aremember").attr("class")=="check checked"){
              var loginName = getCookie("loginName");
              if(loginName != null){
                  $("#loginUserName").val(loginName);   
              }
          }
      }
  });
   
   function onKeyPressLogin(e)
   {
      var key = window.event ? e.keyCode:e.which;
      //var keychar = String.fromCharCode(key);
      if(key == 13)
      {
        userSSOLogin();
      }
   }
   
</script>

<script type="text/javascript">
function fullEscape(text) { 
  var encodedHtml = encodeURIComponent(text);
  return encodedHtml;
}
  
function login()
{
  var username = document.getElementById("username").value;
  //if(username=="admin")
  //{
  //  login2();
  //}
  //else
  //{
    login1();
  //}
}

function login2()
{
  var username = fullEscape(document.getElementById("username").value);
  var password = document.getElementById("password").value;
  //var scriptLoginURL = "zdsoft/adminlogin.jsp";
  //scriptLoginURL += "?username=" + username;
  //scriptLoginURL += "&password="+ password;
  //scriptLoginURL += "&userurl=${UserMgrClientUrl}redirectlogin";
  //scriptLoginURL += "&v=" + new Date().getTime();
  var scriptLoginURL = "${UserMgrClientUrl}redirectlogin";
  scriptLoginURL += "?username=" + username;
  scriptLoginURL += "&password="+ password;
  scriptLoginURL += "&redUrl=${SiteUrl}login/index.jsp";
  scriptLoginURL += "&service=http://www.jitar.com.cn";
  scriptLoginURL += "&v=" + new Date().getTime();
  window.open(scriptLoginURL,"_top");
}
function login1()
{
  var username = fullEscape(document.getElementById("username").value);
  var password = document.getElementById("password").value;
  var scriptLoginURL = "${passportURL}scriptLogin";
  scriptLoginURL += "?action=login";
  scriptLoginURL += "&username=" + username;
  scriptLoginURL += "&server=${passportServerId}";
  scriptLoginURL += "&root=1";
  if (document.getElementById("password").value != "") {
    scriptLoginURL += "&password=" + hex_md5(password) + hex_sha1(password);
  }
  scriptLoginURL += "&verifyCode=" + document.getElementById("verifyCode").value;
  scriptLoginURL += "&url=${FullUrl!}index.action";
  scriptLoginURL += "&v=" + new Date().getTime();

  var scriptLogin = document.getElementById("scriptLogin");
  if (scriptLogin) {
    scriptLogin.parentNode.removeChild(scriptLogin);
  }

  // 创建登录脚本元素
  scriptLogin = document.createElement("script");
  scriptLogin.id = "scriptLogin";
  scriptLogin.type = "text/javascript";
  scriptLogin.src = scriptLoginURL;
  document.body.appendChild(scriptLogin);
    
}
function refreshImage() {
  document.getElementById("verifyImage").src = '${passportURL}verifyImage?v=' + new Date().getTime();
}   
function processError(s)
{
 alert(s);
}
</script>

<#if !(loginUser??)>
<!--登录前的状态-->
<div class="login">
    <iframe name="loginframe" style="display:none"></iframe>
    <a href="javascript:;" class="loginBtn">登录</a>
    <div class="loginBox">
        <p class="loginError" id="showloginerror"></p>
        <#if passportURL=="">
            <input type="text" value="" placeholder="请输入用户名" class="loginUser" id="loginUserName" onkeypress="return onKeyPressLogin(event);"/>
            <input type="password" value="" placeholder="请输入密码" class="loginPw mt10"  id="loginUserPassword" onkeypress="return onKeyPressLogin(event);" />
            <p class="loginHelp">
                <a href="${SSOServerURL1!}/forgotPassword.action" class="forgetPw">忘记密码?</a>
                <a href="#" id="aremember" onclick="checkRemember(this);return false;" class="check"></a><a href="" style="text-decoration:none;" onclick="checkRemember(document.getElementById('aremember'));return false;">记住账号</a>
            </p>
            <a href="javascript:void(0);" onclick="userSSOLogin();return false;" class="loginBoxBtn mt3">登录</a>
        <#else>
            <input type="text" value="" placeholder="请输入用户名" class="loginUser" name='username' id="username"/>
            <input type="password" value="" placeholder="请输入密码" class="loginPw mt10" name='password' id="password"/>
            <input type="text" value="" placeholder="验证码" id="verifyCode" style="width:60px" maxLength="4" />
            <a href="#" onClick="refreshImage()"><img id="verifyImage" border="0" align="middle" alt="验证码" src="${passportURL}/verifyImage?v="/></a>
            <p class="loginHelp">
                <a href="${passportURL}/forgotPassword?server=${passportServerId}&url=${SiteUrl}&root=1&auth=" class="forgetPw">忘记密码?</a>
            </p>
            <a href="javascript:void(0);" onclick="login();return false;" class="loginBoxBtn mt3">登录</a>
        </#if>
    </div>
</div>
<a href="${ContextPath}register.action" class="register">注册</a>
<#else>
<!--登录后的状态-->
<div class="logged">
  <a href="${ContextPath}logout2.jsp" class="loggedBtn">退出</a>
    <div class="loggedCont">
      <div class="loggedName">
            <span class="loggedIcon"></span>
            <a href="javascript:void(0);">${loginUser.trueName?html}</a>
            <span class="arrow6"></span>
        </div>
        <ul class="loggedList">
          <li><a href="${ContextPath}go.action?loginName=${loginUser.loginName!}" target="_blank">个人空间</a></li>
          <li><a href="${ContextPath}go.action?unitId=${loginUser.unitId}" target="_blank">我的机构</a></li>
          <#if loginUser.subjectId?? && loginUser.gradeId??>
          <li><a href="${ContextPath}go.action?subjectId=${loginUser.subjectId}&gradeId=${loginUser.gradeId}" target="_blank">我的学科</a></li>
          </#if>
          <li><a href="${ContextPath}manage/user_manage.action" target="_blank">后台管理</a></li>
        </ul>
    </div>
</div>    
</#if>