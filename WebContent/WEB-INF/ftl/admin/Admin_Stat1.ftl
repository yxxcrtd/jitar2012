<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#content img").ajaxStart(function() {
				$(this).show();
			}).ajaxStop(function() {
				$(this).hide();
			});
			$("#realContent").load("admin_stat.action");
		});
		</script>
	</head>
	
	<body>
		<div id="content" style="text-align: center;">
			<img src="../images/load.gif" />
		</div>
		<div id="realContent"></div>
	</body>
</html>
