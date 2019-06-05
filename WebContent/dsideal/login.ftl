<#include '/dsideal/js.ftl' >
<div id="loginstatus">
<#if loginUser?? >
    <div class='login_head login_head_a'>欢迎：${loginUser.nickName}</div>
    <div class='logincontent'>
    <div>工作室访问量：${loginUser.visitCount!}</div>
    <div>发表的文章数:${loginUser.articleCount!}</div>
    <div>上传的资源数:${loginUser.resourceCount!}</div>
    <div>发表的图片数:${loginUser.photoCount!}</div>
    <div>我发表的评论数:${loginUser.commentCount!}</div>
    </div>              
<#else>
    <!--
    <iframe xxxsrc="${dsssoserver}/iframelogin?targetService=${siteserverurl}&loginFailURL=${siteserverurl}" ></iframe>
    -->
 <div class='login_head login_head_a'>用户登录</div>
  <iframe name="_hidden"  style="display:none"></iframe>
  <form id='userlogin' method='post' style='padding-top:8px;' action="${dsssoserver}/remotelogin">
    <input type="hidden" name="targetService" value="${siteserverurl}/index.py" />
    <input type="hidden" name="loginFailURL" value="${siteserverurl}/dsideal/UnLogin.jsp" />
    <div>用户名称：<input class='loginInput' name='username' id="username"/></div>
    <div>用户密码：<input class='loginInput' name='password' id='password' type='password' /></div>
    <div><input type='button'  value='登录' class='subbtn' onclick='login()'/> 
    <input type='reset' class='subbtn' value='重置' /> </div>                                
  </form>
</#if>
</div>
<script>
window.onload=function()
{
    window.parent.document.getElementById("loginstatus").innerHTML = document.getElementById("loginstatus").innerHTML
}
</script>