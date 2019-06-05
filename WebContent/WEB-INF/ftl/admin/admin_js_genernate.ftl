<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理页</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
<script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
<script type='text/javascript'>
var jitar_root = '${SiteUrl}';
function GenArticle(oF)
{
 var js = jitar_root + 'js_article.py?top=' + oF.list_count.options[oF.list_count.selectedIndex].value + '&count=' + oF.item_count.options[oF.item_count.selectedIndex].value+ '&type=' + oF.article_type.options[oF.article_type.selectedIndex].value;
 if(oF.cateId.options[oF.cateId.selectedIndex].value != '') js+='&cateid=' + oF.cateId.options[oF.cateId.selectedIndex].value;
 if(oF.article_extra[0].checked) js+='&author=' + oF.article_extra[0].value;
 if(oF.article_extra[1].checked) js+='&date=' + oF.article_extra[1].value;
 if(oF.groupId.value != '') js+='&groupId=' + oF.groupId.value;
 oF.result.value = '<script type="text/javascript" charset="utf-8" src="' + js + '"></sc' + 'ript>';
}
function GenRes(oF)
{
 var js = jitar_root + 'js_resource.py?top=' + oF.list_count.options[oF.list_count.selectedIndex].value + '&count=' + oF.item_count.options[oF.item_count.selectedIndex].value+ '&type=' + oF.res_type.options[oF.res_type.selectedIndex].value;
 if(oF.cateId.options[oF.cateId.selectedIndex].value != '') js+='&cateid=' + oF.cateId.options[oF.cateId.selectedIndex].value;
 if(oF.res_extra[0].checked) js+='&author=' + oF.res_extra[0].value;
 if(oF.res_extra[1].checked) js+='&date=' + oF.res_extra[1].value;
 if(oF.groupId.value != '') js+='&groupId=' + oF.groupId.value;
 oF.result.value = '<script type="text/javascript" charset="utf-8" src="' + js + '"></sc' + 'ript>';
}

function GenGroup(oF)
{
 var js = jitar_root + 'js_group.py?top=' + oF.list_count.options[oF.list_count.selectedIndex].value + '&type=' + oF.group_type.options[oF.group_type.selectedIndex].value;
 if(oF.cateId.options[oF.cateId.selectedIndex].value != '') js+='&cateid=' + oF.cateId.options[oF.cateId.selectedIndex].value;
 oF.result.value = '<script type="text/javascript" charset="utf-8" src="' + js + '"></sc' + 'ript>';
}

function GenUser(oF)
{
 var js = jitar_root + 'js_user.py?top=' + oF.list_count.options[oF.list_count.selectedIndex].value + '&count=' + oF.item_count.options[oF.item_count.selectedIndex].value+ '&type=' + oF.user_type.options[oF.user_type.selectedIndex].value;
 if(oF.cateId.options[oF.cateId.selectedIndex].value != '') js+='&cateid=' + oF.cateId.options[oF.cateId.selectedIndex].value;

 oF.result.value = '<script type="text/javascript" charset="utf-8" src="' + js + '"></sc' + 'ript>';
}

function GenVideo(oF)
{
 var js = jitar_root + 'js_video.py?top=' + oF.list_count.options[oF.list_count.selectedIndex].value + '&count=' + oF.item_count.options[oF.item_count.selectedIndex].value;
 if(oF.cateId.options[oF.cateId.selectedIndex].value != '') js+='&cateid=' + oF.cateId.options[oF.cateId.selectedIndex].value;

 oF.result.value = '<script type="text/javascript" charset="utf-8" src="' + js + '"></sc' + 'ript>';
}

function GetGroup(gid)
{
 window.open('show_group_list.py?gid=' + gid,'_blank','width=600,height=400,resizable=1');
}
</script>
<style>
div {padding:4px;}
#div_ div{padding:0;font-weight:bold;color:#E66;}
</style>
</head>
<body>
<h2>外站调用代码生成器</h2>
<div id='div_'>
<div style='float:left;'>
<label><input type='radio' name='t' onclick='TabUtil.changeTab("div_",0)' checked='checked' />调用文章</label>
</div>
<div style='float:left'>
<label><input type='radio' name='t' onclick='TabUtil.changeTab("div_",1)' />调用资源</label>
</div>
<div style='float:left'>
<label><input type='radio' name='t' onclick='TabUtil.changeTab("div_",2)' />调用协作组</label>
</div>
<div style='float:left'>
<label><input type='radio' name='t' onclick='TabUtil.changeTab("div_",3)' />调用工作室</label>
</div>
<div style='float:left'>
<label><input type='radio' name='t' onclick='TabUtil.changeTab("div_",4)' />调用视频</label>
</div>
</div>

<div style='clear:both;padding:10px 0;'>
<div id='div_0' style='display:block'>
<form>
<strong>文章调用：</strong><br/>
<div>
显示条数：<select name='list_count'>
<#list 4..20 as i>
<option value='${i}'<#if i == 10> selected='selected'</#if>>${i}</option>
</#list>
</select>
</div>
<div>
每条显示字数：<select name='item_count'>
<#list 4..30 as i>
<option value='${i}'<#if i == 10> selected='selected'</#if>>${i}</option>
</#list>
</select>
</div>
<div>
显示文章类别：<select name='article_type'>
<option value='0'>普通文章</option>
<option value='1'>推荐文章</option>
<option value='2'>名师文章</option>
<option value='3'>精华文章</option>
</select>
</div>
<div>
文章分类：
<select name='cateId'>
<option value=''>全部分类</option>
<#if article_cates??>
<#list article_cates.all as c>
<option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
</#list>
</#if>
</select>
</div>
<div>
显示特定群组的文章：
群组id：<input name='groupId' id='ArticleGroupId' value='' /> <input type='button' value='选择群组…' onclick='GetGroup("ArticleGroupId")' />
</div>
<div>
<label><input type='checkbox' name='article_extra' value='1' />显示作者</label>
<label><input type='checkbox' name='article_extra' value='1' />显示日期</label>
</div>
<input type='button' value='生成代码' onclick='GenArticle(this.form)' />
<textarea name='result' style='width:100%;height:60px'></textarea>
说明：调用程序默认还支持这些参数的筛选：subjectId（元学科）、gradeId（元学段）、userCateId、userId，大小写必须一致。此配置程序不再一一列出。
</form>
</div>
<div id='div_1' style='display:none'>
<form>
<strong>资源调用：</strong><br/>
<div>
显示条数：<select name='list_count'>
<#list 4..20 as i>
<option value='${i}'<#if i == 10> selected='selected'</#if>>${i}</option>
</#list>
</select>
</div>
<div>
每条显示字数：<select name='item_count'>
<#list 4..30 as i>
<option value='${i}'<#if i == 10> selected='selected'</#if>>${i}</option>
</#list>
</select>
</div>
<div>
显示资源类别：<select name='res_type'>
<option value='0'>最新资源</option>
<option value='1'>推荐资源（小组精华）</option>
</select>
</div>
<div>
资源分类：
<select name='cateId'>
<option value=''>全部分类</option>
<#if res_cate??>
<#list res_cate.all as r>
<option value='${r.categoryId}'>${r.treeFlag2 + r.name}</option>
</#list>
</#if>
</select>
</div>
<div>
显示特定群组的资源：
群组id：<input name='groupId' id='ResGroupId' value='' /> <input type='button' value='选择群组…' onclick='GetGroup("ResGroupId")' />
</div>
<div>
<label><input type='checkbox' name='res_extra' value='1' />显示作者</label>
<label><input type='checkbox' name='res_extra' value='1' />显示日期</label>
</div>
<input type='button' value='生成代码' onclick='GenRes(this.form)' />
<textarea name='result' style='width:100%;height:60px'></textarea>
说明：调用程序默认还支持这些参数的筛选：subjectId（元学科）、gradeId（元学段）、userId、userCateId（资源个人分类），大小写必须一致。此配置程序不再一一列出。
</form>
</div>

<div id='div_2' style='display:none'>
<form>
<strong>协作组调用：</strong><br/>
<div>
显示条数：<select name='list_count'>
<#list 4..20 as i>
<option value='${i}'<#if i == 10> selected='selected'</#if>>${i}</option>
</#list>
</select>
</div>
<div>
显示协作组类别：<select name='group_type'>
<option value='0'>最新协作组</option>
<option value='1'>推荐协作组</option>
<option value='2'>优秀团队</option>
</select>
</div>
<div>
协作组分类：
<select name='cateId'>
<option value=''>全部分类</option>
<#if group_cate??>
<#list group_cate.all as g>
<option value='${g.categoryId}'>${g.treeFlag2 + g.name}</option>
</#list>
</#if>
</select>
</div>
<input type='button' value='生成代码' onclick='GenGroup(this.form)' />
<textarea name='result' style='width:100%;height:60px'></textarea>
</form>
</div>

<div id='div_3' style='display:none'>
<form>
<strong>工作室调用：</strong><br/>
<div>
显示条数：<select name='list_count'>
<#list 4..20 as i>
<option value='${i}'<#if i == 10> selected='selected'</#if>>${i}</option>
</#list>
</select>
</div>
<div>
每条显示字数：<select name='item_count'>
<#list 4..30 as i>
<option value='${i}'>${i}</option>
</#list>
</select>
</div>
<div>
显示工作室类别：<select name='user_type'>
<option value='0'>最新工作室</option>
<#if user_type_list??>
<#list user_type_list as ut>
<option value='${ut.typeId}'>${ut.typeName}</option>
</#list>
</#if>
</select>
</div>
<div>
工作室分类：
<select name='cateId'>
<option value=''>全部分类</option>
<#if user_cate??>
<#list user_cate.all as c>
<option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
</#list>
</#if>
</select>
</div>
<input type='button' value='生成代码' onclick='GenUser(this.form)' />
<textarea name='result' style='width:100%;height:60px'></textarea>
说明：调用程序默认还支持这些参数的筛选：subjectId（元学科）、gradeId（元学段）、unitId，大小写必须一致。此配置程序不再一一列出。
</form>
</div>

<div id='div_4' style='display:none'>
<form>
<strong>视频调用：</strong><br/>
<div>
显示条数：<select name='list_count'>
<#list 4..20 as i>
<option value='${i}'<#if i == 10> selected='selected'</#if>>${i}</option>
</#list>
</select>
</div>
<div>
每条显示字数：<select name='item_count'>
<#list 10..30 as i>
<option value='${i}'>${i}</option>
</#list>
</select>
</div>
<div>
视频分类：
<select name='cateId'>
<option value=''>全部分类</option>
<#if video_cate??>
<#list video_cate.all as c>
<option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
</#list>
</#if>
</select>
</div>
<input type='button' value='生成代码' onclick='GenVideo(this.form)' />
<textarea name='result' style='width:100%;height:60px'></textarea>
说明：调用程序默认还支持这些参数的筛选：subjectId（元学科）、gradeId（元学段）、unitId、userId、userCateId（视频个人分类），大小写必须一致。此配置程序不再一一列出。
</form>
</div>

</body>
</html>