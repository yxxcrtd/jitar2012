<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>站点信息配置</title>
	<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	<script type="text/javascript">
		function checkData()
		{
		    score1=document.getElementsByName("score.my.article.add")[0].value;
		    score2=document.getElementsByName("score.other.article.add")[0].value;
		    score3=document.getElementsByName("score.article.rcmd")[0].value;
		    score4=document.getElementsByName("score.resource.add")[0].value;
		    score5=document.getElementsByName("score.resource.rcmd")[0].value;
		    score6=document.getElementsByName("score.photo.upload")[0].value;
		    score7=document.getElementsByName("score.video.upload")[0].value;
		    score8=document.getElementsByName("score.comment.add")[0].value;
		    score9=document.getElementsByName("score.article.adminDel")[0].value;
		    score10=document.getElementsByName("score.resource.adminDel")[0].value;
		    score11=document.getElementsByName("score.comment.adminDel")[0].value;
		    score12=document.getElementsByName("score.photo.adminDel")[0].value;
		    score13=document.getElementsByName("score.video.adminDel")[0].value;
			
			if(score1.indexOf(".")>=0 || score2.indexOf(".")>=0 || score3.indexOf(".")>=0 || score4.indexOf(".")>=0 || score5.indexOf(".")>=0 || score6.indexOf(".")>=0 || score7.indexOf(".")>=0 || score8.indexOf(".")>=0 || score9.indexOf(".")>=0 || score10.indexOf(".")>=0 || score11.indexOf(".")>=0 || score12.indexOf(".")>=0 || score13.indexOf(".")>=0)
			{
				alert("输入的分数不能出现小数");
				return;						
			}
			document.configForm.submit();
		}
	</script>
	<script type='text/javascript'>
  //<![CDATA[  
  function openUpload(t)
  {
    var url = "${SiteUrl}manage/userfm/index.jsp?Type=" + t;
    window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
  }
  
  function SetUrl( url, width, height )
  {
    if(url)
    {
        document.configForm.logo.value = url;
    }
  }
  //]]>
  </script>		
</head>
<body> 
	<#include 'admin_header.ftl' >
	<h2>站点配置</h2>
	<form name='configForm' action='?' method='post'>
		<input type='hidden' name='cmd' value='save' />		
		<table class='listTable'>
		<#if RootUnit??>
			<tr>
				<td align='right' style='width:120px'>
					<b>根单位名称：</b>
				</td>
				<td>
					<input type='text' name='unitTitle' size='50' value='${RootUnit.unitTitle!?html}' />				
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>根单位平台名称：</b>
				</td>
				<td>
					<input type='text' name='siteTitle' size='50' value='${RootUnit.siteTitle!?html}' />
				</td>
			</tr>
			</#if>
			<#--
			<tr>
				<td align='right'>
					<b>网站Logo：</b>
				</td>
				<td>
					<input name='logo' style='width:50%;' value="${logo!?html}" />
					Flash宽度：<input name="logowidth" size="4" value="${logowidth!?html}" title="网站页面的整体宽度为 1000px（像素）。" /> 高度：<input name="logoheight" value="${logoheight!?html}" size="4" />
					<input type='button' value='上传图片…' onclick='openUpload("Image")' />
					<input type='button' value='上传 Flash…' onclick='openUpload("Flash")' />
				</td>
			</tr>
			-->
			<tr>
				<td align='right'>
					<b>站点关键字：</b>
				</td>
				<td>
					<input type='text' name='site.keyword' size='50' value='${config['site.keyword']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>站点版权信息：</b>
				</td>
				<td>
					<textarea name='site.copyright' style='width:90%;height:120px' >${config['site.copyright']!?html}</textarea>
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>网站停用显示信息：</b>
				</td>
				<td>
					<textarea name='site.stop.info' style='width:90%;height:60px' >${config['site.stop.info']!?html}</textarea>
					<br/>${config['site.stop.info'].title!?html}
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>举报类型：</b>
				</td>
				<td>
					<input type='text' name='reportType' size='50' value='${config['reportType']!?html}' /> 以逗号（,）分隔。
				</td>
			</tr>
      <#--
			<tr>
				<td align='right'>
					<b>精品资源网址：</b>
				</td>
				<td>
					<input type='text' name='site.resourceUrl' size='50' value='${config['site.resourceUrl']!?html}' />
				</td>
			</tr>
			-->
			<tr>
				<td align='right'>
					<b>发表<font style="color: #FF0000;">原创</font>文章得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.my.article.add' size='50' value='${config['score.my.article.add']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>发表<font style="color: #FF0000;">转载</font>文章得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.other.article.add' size='50' value='${config['score.other.article.add']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>文章被推荐得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.article.rcmd' size='50' value='${config['score.article.rcmd']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>上载资源得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.resource.add' size='50' value='${config['score.resource.add']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>资源被推荐得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.resource.rcmd' size='50' value='${config['score.resource.rcmd']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>发布照片得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.photo.upload' size='50' value='${config['score.photo.upload']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>上传视频得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.video.upload' size='50' value='${config['score.video.upload']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>发表评论得分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.comment.add' size='50' value='${config['score.comment.add']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>文章被<font style="color: #FF0000;">删除</font>罚分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.article.adminDel' size='50' value='${config['score.article.adminDel']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>资源被<font style="color: #FF0000;">删除</font>罚分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.resource.adminDel' size='50' value='${config['score.resource.adminDel']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>评论被<font style="color: #FF0000;">删除</font>罚分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.comment.adminDel' size='50' value='${config['score.comment.adminDel']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>图片被<font style="color: #FF0000;">删除</font>罚分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.photo.adminDel' size='50' value='${config['score.photo.adminDel']!?html}' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					<b>视频被<font style="color: #FF0000;">删除</font>罚分：</b>
				</td>
				<td>
					<input type='text' size=5 name='score.video.adminDel' size='50' value='${config['score.video.adminDel']!?html}' />
				</td>
			</tr>
			<#--
				<tr>
					<td align='right'>
						<b>用户空间大小限制：</b>
					</td>
					<td>
						<input type="text" size="5" name="resource.uploadMaximumSize" value="${config['resource.uploadMaximumSize']!?html}" />(<font style="color: #FF0000; font-weight: bold;">单位：M</font>)
					</td>
				</tr>
			<tr>
				<td align='right'>
					<b>用户在线时长设定：</b>
				</td>
				<td>
					<input type="text" size="5" name="site.user.online.time" value="${config['site.user.online.time']!?html}" />
					(<font style="color: #FF0000; font-weight: bold;">单位：分钟</font>)
				</td>
			</tr>
      -->
			<tr>
				<td colSpan="2" style="text-align: center;">
					<input type='button' class='button' value='  修  改  ' onclick="checkData()"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type='button' class='button' value=' 返回 ' onclick='window.history.back()' />
				</td>
			</tr>
		</table>
	</form>
	<#include 'admin_footer.ftl' >  
</body>
</html>
