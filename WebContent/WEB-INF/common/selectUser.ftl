<html>
  <head>
    <title>选择用户</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/index/index.css" />
    <style>
  html,body {width:100%;text-align:left;color:#000;}
  </style>
<script src='${SiteUrl}js/jitar/core.js'></script>
<script language="javascript">
	function selectTab(i)
	{
      if(i=="u0")
		{  
	  		document.getElementById["u0"].className="cur";
	  		document.getElementById["u1"].className="";
		}
      if(i=="u1")
		{  
	  		document.getElementById["u1"].className="cur";
	  		document.getElementById["u0"].className="";
		}
	return true;
	}

	function selectUsers(ids,names,loginnames,genders,unittitles)
	{
		<#if userUrl=="">
		    <#if inputUser_Id!="" || inputUserName_Id!="" || inputLoginName_Id!="">  
    			<#if inputUser_Id!="">
    			self.opener.document.getElementById("${inputUser_Id}").value=ids;
    			</#if>
    			<#if inputUserName_Id!="">
    			self.opener.document.getElementById("${inputUserName_Id}").value=names;
    			</#if>	
    			<#if inputLoginName_Id!="">
    			self.opener.document.getElementById("${inputLoginName_Id}").value=loginnames;
    			</#if>
    			self.close();
    		<#else>
    		    window.returnValue=ids+"|"+names+"|"+loginnames+"|"+genders+"|"+unittitles;
    		    window.close();
    		</#if>	
		<#else>
			document.getElementById("inputUser_Id").value=ids;
			document.getElementById("inputUserName_Id").value=names;
			document.getElementById("inputLoginName_Id").value=loginnames;
			document.getElementById("uuuForm").submit();
		</#if>	
	}
</script>   
  </head>
  <body>
  <#if userUrl!="">
  	<form name="uuuForm" id="uuuForm" method="POST" action="${userUrl}" target="_self">
  		<input type="hidden" id="roomid" name="roomid" value="${roomid}">
  		<input type="hidden" id="inputUser_Id" name="inputUser_Id" value="">
  		<input type="hidden" id="inputUserName_Id" name="inputUserName_Id" value="">
  		<input type="hidden" id="inputLoginName_Id" name="inputLoginName_Id" value="">
  	</form> 
  </#if>  
      <div id="user_" class='tab2'>
      <div id='u0' class="cur"><a href='selectUser.action?cmd=list&singleuser=${singleuser!}&inputUser_Id=${inputUser_Id!}&inputUserName_Id=${inputUserName_Id}&inputLoginName_Id=${inputLoginName_Id}' onclick='selectTab("u0")' target='frumain_user'>用户</a></div>
	  <#if singleuser==0>	
      	<#if showgroup==1>
			<div id='u1' class=""><a href='selectUser.action?cmd=glist&singleuser=${singleuser!}&inputUser_Id=${inputUser_Id!}&inputUserName_Id=${inputUserName_Id}&inputLoginName_Id=${inputLoginName_Id}' onclick='selectTab("u1")' target='frumain_user'>群组</a></div>		   
      	</#if>
      <#else>
      
      </#if>
      </div>
  	<iframe name="frumain_user" style="width:100%;height:90%" frameborder=0 src="selectUser.action?cmd=list&singleuser=${singleuser!}&inputUser_Id=${inputUser_Id!}&inputUserName_Id=${inputUserName_Id}&inputLoginName_Id=${inputLoginName_Id}"></iframe>
  </body>
</html>
