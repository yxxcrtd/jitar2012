<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <title>发表/修改文章</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
 <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
 <script type="text/javascript" src="js/common.js"></script>
 <script type="text/javascript" src="js/user.js"></script>
 <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 <script type="text/javascript">
	<!--
	// 提交文章
	function submit_article() {
	    
		if (checkValid() == false)
			return; 
		$("HtmlEditor").value = editor.getContent();
		if($("HtmlEditor").value == "")
		{
		  alert("请输入文章内容");
		  return false;
		}
		$("draftState").value = 'false';
		document.forms.blogForm.submit();
		window.document.getElementById("btn_submit").disabled = "disabled"
		window.document.getElementById("btn_submit").value = "正在提交数据...";
	}
	// 存为草稿
	function save_draft() {
		// 保存草稿不进行数据的验证，也不允许进行验证
		$("HtmlEditor").value = editor.getContent();
		$("draftState").value = 'true';
		document.forms.blogForm.submit();
	}	  
	// 进行必要的验证检查
	function checkValid() {
		$("HtmlEditor").value = (editor.getContent())
		var count = 0;
		for (i = 0; i < document.forms.blogForm.articleType.length; i++) {
			if (document.forms.blogForm.articleType[i].checked) {
				count++;
				//alert(document.forms.blogForm.articleType[i].value);
			}
		}
		if (count == 0) {
			alert("请选择一个文章类型！");
			return false;
		}
		var Title = document.forms.blogForm.articleTitle.value;
		if (Title.length < 1) {
			window.alert("  请输入文章标题！");
			document.forms.blogForm.articleTitle.focus();
			return false;
		}
		
		var gid = document.forms.blogForm.gradeId.options[document.forms.blogForm.gradeId.selectedIndex].value;
		if(gid == "")
		{
		 alert("请选择学段。")
		 return false;
		}
		
		var sid = document.forms.blogForm.subjectId.options[document.forms.blogForm.subjectId.selectedIndex].value;
		if(sid == "")
		{
		 alert("请选择学科。")
		 return false;
		}
		
		var cateid = document.forms.blogForm.sysCate.options[document.forms.blogForm.sysCate.selectedIndex].value;
		if(cateid == "")
		{
		 alert("请选择系统分类。")
		 return false;
		}
		
		return true;
	}
	function getKeyword(articleTitle) {
		//为了防止导致性能下降，不再使用
		return;
		if (articleTitle.length == 0) {
			return;
		}
		articleTitle = document.getElementById("articleTitle").value;
        postData = "articleTitle=" + articleTitle + "&cmd=split";
        url = "article.action";
        new Ajax.Request (url, {
        	method: 'post',
        	parameters: postData,
        	onSuccess: function(xhr) { document.getElementById("articleTags").value = unescape(xhr.responseText); }, 
        	onFailure: function(xhr) { document.getElementById("articleTags").value = ""; }
        });
	}
	function qtC(str) {
		return encodeURIComponent(str);
	}
	function qtDec(str) {
		return decodeURIComponent(str);
	}
	// -->
 </script>
</head>
<body>
<#if article??>
<h2>${(article.articleId == 0)?string('发表文章', '修改文章')}</h2>
<#if num??>[您每天允许发表${num}篇文章,今天已经发表${todaynum}篇文章]</#if>		
<div class="funcButton">
 您现在的位置：<a href="${SiteUrl}manage/" target="_top">个人控制面板</a>
 &gt;&gt; <a href="article.action?cmd=list">文章管理</a>
 &gt;&gt; ${(article.articleId == 0)?string("发表文章", "修改文章")}
</div>
<#include "article_edit_form.ftl">
<#else>
文章对象不存在，可能是输入的地址不正确。
</#if>
</body>
</html>