<!-- 配置上载路径 -->
    <script type="text/javascript">
        window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
        window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
    </script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>  
    
<#if article??>
<table class="listTable" cellSpacing="1" cellPadding="0" style="width:1024px">
<tbody>
<tr>
	<td align="right" style="width:100px">
		<strong>文章标题：</strong>
	</td>
	<td>
		<input name="articleTitle" value="${article.title!?html}" style="width: 90%;" maxLength="128" /> <font style="color: #FF0000; font-weight: bold;">*</font>
	</td>
</tr>
<tr>
	<td align="right">
		<strong>学段/学科：</strong>
	</td>
	<td>
		<select name="gradeId" onchange='grade_changed(this)'>
			<option value=''>选择所属学段</option>
				<#if grade_list??>
					<#list grade_list as grade>
						<option value="${grade.gradeId}"${(grade.gradeId == (article.gradeId!0))?string('selected="selected" ', '')}>${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
					</#list>
				</#if>
		</select>         
		<select name="subjectId">
			<option value=''>选择所属学科</option>
				<#if subject_list??>
					<#list subject_list as msubj>
						<option value="${msubj.msubjId}"${(msubj.msubjId == (article.subjectId!0))?string('selected="selected" ', '')}>${msubj.msubjName!?html}</option>
					</#list>
				</#if>
		</select>
		<span id='subject_loading' style='display:none'>
			<img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
		</span> * 选择您的文章所属的学科和年级, 如果不属于或没有可以不选择.
	</td>
</tr>
<tr>
  <td align="right"><b>所属分类：</b></td>
  <td>
<select name="sysCate" id="sysCate">
  <option value="">请选择系统分类</option>
<#if article_categories??>
  <#list article_categories.all as c>
    <option value="${c.categoryId}"${(c.categoryId == (article.sysCateId!0))?string('selected="selected" ', '') }>
      ${c.treeFlag2} ${c.name!?html}
    </option>
  </#list>
</#if>
</select>
  <#if usercate_tree??>
<select name="userCate" id="userCate">
  <option value="">请选择个人分类</option>
  <#list usercate_tree.all as category>
  <option value="${category.id}" ${(category.categoryId == (article.userCateId!0))?string('selected', '') }>
    ${category.treeFlag2} ${category.name!?html}
  </option>
  </#list>
</select>
  </#if>

<#if channel_article_categories??>
<select name="channelCate" id="channelCate">
  <option value="">请选择自定义频道文章分类</option>
  <#list channel_article_categories.all as category>
  <#assign cp = Util.convertIntFrom36To10(category.parentPath) + category.id?string + "/" >
  <option value="${cp}"${(cp == (channelCate!""))?string(' selected="selected"', '')}>${category.treeFlag2} ${category.name!?html}</option>  
  </#list>
</select>
</#if>
<#if formUrl??>
<!-- 从 url中获取 -->
<#else>
<input type=hidden name="channelId" value="${channelId!}" />
</#if>
  </td>
</tr>
<tr>
  <td align="right"><b>专题分类：</b></td>
  <td>
  <select name="specialSubjectId">
  <option value="">选择专题分类</option>
  <#if specialsubject_list??>
  <#list specialsubject_list as sl>
  <#if specialSubjectId??>
    <#if specialSubjectId == sl.specialSubjectId >
    <option value="${sl.specialSubjectId}" selected="selected">${sl.title}</option>
    <#else>
    <option value="${sl.specialSubjectId}">${sl.title}</option>
    </#if>
  <#else>
     <option value="${sl.specialSubjectId}">${sl.title}</option>
  </#if>
  </#list>
  </#if>
	</select>
  </td>
</tr>
<tr>
  <td align="right"><b>关键字(标签)：</b></td>
  <td>
	<input name="articleTags" id="articleTags" value="${article.tags!?html}" size='40' /> (请以 ',' 逗号分隔标签，关键字有利于你的文章被更多的人阅读。)
  </td>
</tr>
<tr>
  <td align="right"><b>文章摘要：</b></td>
  <td>
<textarea name="articleAbstract" id="articleAbstract" rows="4" style="width:98%">${article.articleAbstract!?html}</textarea>
  </td>
</tr>
</tbody>
</table>
  <div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;position:relative;height:560px;">
    <div style="padding:8px;height:560px;width:94px;float:left">文章内容(<font color='red'>*</font>):</div>
    <div style="position:absolute;top:0;left:110px">
      <script id="articleContent" name="articleContent" type="text/plain" style="width:900px;height:480px;">
      ${article.articleContent!}
    </script>                          
    <script type="text/javascript">
       var editor = UE.getEditor('articleContent');
    </script>
    </div>
</div>
<table class="listTable" cellSpacing="1" cellPadding="0" style="width:1024px">
<tbody>
<tr>
  <td align="right" style="width:100px"><b>其他属性：</b></td>
  <td>
<input type="checkbox" name="commentState" value="1" ${article.commentState?string('checked="checked"','')}/>允许评论
<input type="checkbox" name="hideState" value="1" ${(article.hideState==1)?string('checked="checked"','')}/>设为隐藏
  </td>
</tr>
</tbody>
<tfoot>
<tr>
  <td colspan="2" align="center" height="30">
    <input type="submit" class='button' value="修改文章" />
    <input type="button" class='button' value=" 返  回 " onclick="window.history.back();" />
  </td>
</tr>
</tfoot>
</table>

<script>
function GetGroupCate(sid)
{
	var gradeId = sid.options[sid.selectedIndex].value;
	var groupCateForm = document.getElementById("EditForm").groupCateId;
	clear_options(groupCateForm);
	add_option(groupCateForm, '', '选择一个协作组文章分类');
	var url = "${SiteUrl}manage/article.action?cmd=dest_cate&groupId=" + gradeId + '&tmp=' + Math.random();
	var myAjax = new Ajax.Request(
	url, {
		method: 'get',
		onComplete: fill_gres_cate,    // 指定回调函数.
		asynchronous: true             // 是否异步发送请求.
	});
}

function fill_gres_cate(request)
{
  var gres_categories = eval(request.responseText);
  if (gres_categories == null || gres_categories.length == null || gres_categories.length == 0) {
    alert('该协作组尚未建立文章分类');
    return;
  }
  var groupCateForm = document.getElementById("EditForm").groupCateId;
  for (var i = 0; i < gres_categories.length; ++i) {
    c = gres_categories[i];
    add_option(groupCateForm, c.id, c.treeFlag2 + ' ' + c.name);
  }
}

function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.getElementById("EditForm").subjectId;

  if (gradeId == null || gradeId == '' || gradeId == 0) {
    clear_options(subject_sel);
    add_option(subject_sel, '', '选择学科');
    return;
  } 
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = '${SiteUrl}manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '选择学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}
function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  sel.options[sel.options.length] = new Option(text.replace(/&nbsp;/g," "),val)    
}
<#if joined_groups??>
<#if groupId?? && groupId &gt; 0>
GetGroupCate(document.getElementById("EditForm").groupId);
</#if>
</#if>
</script>
<#else>
您所编辑的文章不存在。
</#if>