<script type="text/javascript" src="${SiteUrl}zdsoft/script/md5.js"></script>
<script type="text/javascript" src="${SiteUrl}zdsoft/script/sha1.js"></script>

<script type="text/javascript">
function fullEscape(text) { 
  var encodedHtml = encodeURIComponent(text);
  return encodedHtml;
}
  
function login()
{
  var username = document.getElementById("username").value;
  if(username=="admin")
  {
  	login2();
  }
  else
  {
  	login1();
  }
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
  scriptLoginURL += "&url=${SiteUrl}index.action";
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