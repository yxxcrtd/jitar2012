<script language="javascript">
	function selectFace(facePath)
	{
		window.returnValue=facePath;
		window.close();
	}
</script>
<#if faceList??>
	<#if faceList.size() &gt; 0 >
	    <#-- 定义要显示的列数 columnCount -->
	    <#assign columnCount = 12>
	    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
	    <#if faceList.size() % columnCount == 0>
	    	<#assign rowCount = (faceList.size() / columnCount) - 1>
	    <#else>
	    	<#assign rowCount = (faceList.size() / columnCount)>
	    </#if>
	    <#-- 输出表格 -->
	    <table  cellSpacing="2" align="center">                            
	    <#-- 外层循环输出表格的 tr -->
	    <#list 0..rowCount as row >
	    <tr valign='top'>
	    <#-- 内层循环输出表格的 td  -->
	    <#list 0..columnCount - 1 as cell >
	        <td align="center" width='${100 / columnCount}%'>
	        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
	        <#if faceList[row * columnCount + cell]??>                     
	            <#assign photo = faceList[row * columnCount + cell]> 
	            <img border="0" style="cursor:hand" onclick="selectFace('${SiteUrl}${photo}')" src="${SiteUrl}${photo}"/>                 
	        <#else>
	            &nbsp;
	        </#if>
	        </td>
	    </#list>
	    </tr>
	    </#list>
	    </table>		
	</#if>
</#if>
	
