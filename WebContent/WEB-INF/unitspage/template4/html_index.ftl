<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />
  <style type='text/css'>  
	#header_zone { width:100% }
	#left_zone { }
	#middle_zone {width:100%;}
	#right_zone {}
	#bottom_zone {width:100%;} 
  </style>
<#include "/WEB-INF/unitspage/unit_header_js.ftl">
 </head>
<body>

<#include "/WEB-INF/unitspage/unit_total_header.ftl">
<#function showdata t>
  <#assign data2>
${("webpart" + t.unitWebpartId?string)?eval}
<div style='height:8px;font-size:0;clear:both;'></div>
</#assign>
  <#return data2>
</#function>

<div id='container'>

<#if hasTopWebpart?? >
	<table border='0' style='width:100%' cellpadding='0' cellspacing='0'>
	<tr>
	<td id='header_zone'>
	 <#list webpartList as t>
	    <#if t.webpartZone == 1>	    
	    ${showdata(t)}
	    </#if>
	  </#list>
	</td>
	</tr>
	</table>
</#if>


<table border='0' style='width:100%' cellpadding='0' cellspacing='0'>

	<tr style='vertical-align:top;'>		
    <td class='col' id='middle_zone'>
    	<#if hasLeftWebpart?? >
		  <#list webpartList as t>
		    <#if t.webpartZone == 3>
		    ${showdata(t)}
		    </#if>
		  </#list>
	  	</#if>
    	<#if hasMiddleWebpart?? >
		  <#list webpartList as t>
		    <#if t.webpartZone == 4>
		    ${showdata(t)}
		    </#if>
		  </#list>
	  	</#if>
	  
	 	<#if hasRightWebpart?? >
		  <#list webpartList as t>
		    <#if t.webpartZone == 5>
		    ${showdata(t)}
		    </#if>
		  </#list>
	  	</#if>
    </td>
	</tr>
</table>

<#if hasBottomWebpart?? >
<div style='clear:both;height:8px;'></div>
<table border='0' style='width:100%' cellpadding='0' cellspacing='0'>
	<tr>
	<td class='col' id='bottom_zone'>
	 <#list webpartList as t>
	    <#if t.webpartZone == 2>
	    ${showdata(t)}
	    </#if>
	  </#list>
	</td>
	</tr>
	</table>
</#if>

</div>
<#include "/WEB-INF/unitspage/unit_footer.ftl">
</body>
</html>

