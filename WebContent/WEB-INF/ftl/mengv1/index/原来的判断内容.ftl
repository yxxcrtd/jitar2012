 <#if passportURL?? >
  <#if passportURL!="">
  	<div class='login'><#include '../../zdsoft/login.ftl'></div>
  <#else>	
	<div class='login'><#include 'mengv1/index/login.ftl' ></div>
  </#if>
  <#else>
<div class='login'><#include 'mengv1/index/login.ftl' ></div>
  </#if>