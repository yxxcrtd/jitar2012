<html>
<head>
</head>
<body>
<#if reslibCId??>
	<#if reslibCId!="">
		<script language="javascript">
		  
			function selectCate()
			{
				var url="${zyk_url}selectCategory.aspx?subjectCateId=${reslibCId}&site="+document.domain+"&resourceId=${resourceId}";
				window.open(url,"zykSelectCate","dialogHeight:540px;dialogWidth:700px;dialogLeft:50px;dialogTop:50px;resizable:yes;");
			}
		</script>
		<iframe name="reslibFrame1" style="width:100%;height:700px" src="${zyk_url}selectCategory.aspx?subjectCateId=${reslibCId}&site=${SiteUrl}&resourceId=${resourceId}"></iframe>
		<br/>
		<iframe name="reslibFrame2" style="display:none" src="about_blank"></iframe>
	</#if>
</#if>
</body>
</html>