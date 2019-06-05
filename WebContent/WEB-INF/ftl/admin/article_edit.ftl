<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>修改文章</title>
	<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/user.js"></script>
	<script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
	<script type="text/javascript">
	<!--
 		var oFCKeditor;		
	// 提交文章.
		function submit_article() {
  		if (checkValid() == false)
  			return;
  		$("HtmlEditor").value = editor.getContent();
  		$("draftState").value = 'false';
  		document.forms.blogForm.submit();
		}  		
	// 进行必要的验证检查.
	function checkValid() {
  		$("HtmlEditor").value = (editor.getContent())
  		var Title = document.forms.blogForm.articleTitle.value;
  		if (Title.length < 1) {
			alert("请输入文章标题。");
			document.forms.blogForm.articleTitle.focus();
			return false;
  		}
  		return true;
		}
	function doHelp() {
		alert("TODO")
	}		

	//-->
	</script>
</head>
<body>
<h2>修改文章</h2>
<#include '../article/article_edit_form.ftl' >
</body>
</html>
