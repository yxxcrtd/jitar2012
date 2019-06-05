<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
 <title></title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<form action='?' method='post'>
<h2>发布网站更新通知</h2>
<textarea name="updateInfo" style="width:100%;height:200px">${updateInfo!?html}</textarea>
<input type="submit" value="保存更新通知" class='button'/>
</form>
<#if deleteCache=="1">
修改成功！<br/>
<iframe src="clearall_cache.py?cachetype=unit" style="display:none"></iframe>
<iframe src="clearall_cache.py?cachetype=index" style="display:none"></iframe>
</#if>
公告示例：<br/><textarea style="width:100%;height:200px">
<div style="height:80px;text-align:center;border:1px solid red;background:#fff;padding-top:20px; font-size: 18px; color:#f00;">
为了提供更好的服务，2012年6月29号下午14:00至15:00之间将进行一次短暂的升级，<br/>
届时平台将不能浏览和文章的发布，也不能上传资源、视频、图片，敬请谅解。
</div>
</textarea>
</body>
</html>
