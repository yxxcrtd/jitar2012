<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>资源类型选择</title>
  <style>
html,body {
	margin: 0;
	padding: 0;
	text-align: left;
	font-family:宋体,verdana,sans-serif;
	font-size:12px;
}

a {
	COLOR: #425b6f;
}

a:hover {
	COLOR: #ff6633;
}
</style>
<script type="text/javascript">
		
		function doOK(id,name)
		{
		  
			window.returnValue =name+'|'+id ;
			window.close();
		}
	
    	</script>
</head>
<body style="margin-top: 20px;">
<ul>
	  <#list resType_list as resType>
          <li><a href="javascript:" name="${resType.tcId!?html}"  onclick="doOK('${resType.tcId!?html}','${resType.tcTitle!?html}')">${resType.tcTitle!?html}</a></li>
       </#list>
	</ul>
</body>
</html>