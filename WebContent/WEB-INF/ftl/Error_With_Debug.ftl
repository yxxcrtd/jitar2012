<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>错误页面</title>
    <link rel="stylesheet" type="text/css" href="../css/manage.css" />   
  </head>

  <body>
  
<center>
  <font style="color: #FF0000; font-size: 26px; font-weight: bold;">操作发生错误</font>
  <div style='border:1px solid red; width:75%; text-align:left; margin-top:12px;'>
  <ul>
    <#list actionErrors as error>
      <li>${error}</li>
    </#list>
  </ul>
  
  <div style='margin:4px;'>
  <center>
  <#if actionLinks??>
    <#list actionLinks as link>
      [${link.html}] 
    </#list>
  <#else>
    [<a href='javascript:window.history.back();'>返 回</a>]
  </#if>
  </center>
  </div>
  
  </div>
</center>
	
	<hr/>
	<h1>以下为调试信息：</h1>
	        
    <h2>Request Parameters - 页面请求参数</h2>
    <#list request.parameterMap?keys as key>
      <#assign value = request.parameterMap[key]>
      <li>request.parameter[${key}] (${value?size})=
      <#if value?size == 0>(empty)
      <#elseif value?size == 1>${value[0]}
      <#else>
       <#list value as v>${v}<#if v_has_next>, </#if></#list>
      </#if>
    </#list>
  
    <hr/>
    <h2>Action 对象</h2>
    <li>action = ${action}
    
    <hr/>
    <h2>Request 对象</h2>
    <li>request = ${request}
    <li>keys = 
    <#list request?keys as key>${key}, </#list>
    
    <hr/>
    <h2>Session</h2>
    <li>session = ${session}
    <li>keys = <#list session?keys as key>${key}, </#list>
     
    <hr/>
    <h2>Application</h2>
    <li>keys = <#list application?keys as key>${key}, </#list>
  </body>
</html>