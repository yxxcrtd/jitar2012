<a name='comment'></a>
<#if SiteConfig?? >
    <#assign enabedComment = SiteConfig.user.site.comment.enabled >
<#else>
    <#assign enabedComment = false >
</#if>
<#if SiteConfig.user.site.article_show?? && SiteConfig.user.site.article_show>
<#assign loginShow="1">			
<#else>
<#assign loginShow="0">
</#if>
<#if loginShow=="0" || (loginUser?? && loginShow=="1") >
	<#if comment_list??>
		<#list comment_list as comment>
			<table border="0" cellspacing="1" cellpadding="3" class="commentTable">
				<tr style='vertical-align:top'>
					<td class="commentLeft" style='padding-top:10px'>
					<#if comment.userId??>
				        <#assign u = Util.userById(comment.userId)>        
				        <#if u?? >       
				        <#if (u.trueName??)><#assign userName = u.trueName >
				        <#elseif (u.nickName??)><#assign userName = u.nickName >
				        <#elseif (u.loginName)??><#assign userName = u.loginName >
				        </#if>
				        <#else>
				        <img src="${SiteUrl}images/default.gif" width='48' height='48' border='0' />
				         匿名用户
				        </#if>
				        <#if userName??>
					        <img src="${SSOServerUrl +'upload/'+u.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'" width='48' height='48' border='0' />
					        <a onmouseover="ToolTip.showUserCard(event,'${u.loginName!}','${userName}', '${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}')" href="${SiteUrl}go.action?loginName=${u.loginName!}" target="_blank">${u.nickName!?html}</a>
					    <#else>
				        	<img src="${SiteUrl}images/default.gif"  width='48' height='48' border='0' />
				         	匿名用户
				        </#if>				        
					<#else>  
				        <img src="${SiteUrl}images/default.gif" width='48' height='48' border='0' />
				         匿名用户
					</#if>
		</td>
		<td class="commentRight">
			<div class="commentHeader">
				<span  style="float: left">&nbsp;&nbsp;用户评论星级：<font color="#FF0000"><#if comment.star &gt; 0><#list 1..comment.star as i>☆</#list></#if></font></span><span style="float: right">发表时间：${comment.createDate}
	<#if article.commentState>				
		<#if enabedComment>
			<#if loginUser??>
				<a href="#cc" onclick="replyComment(${comment.id});">回复</a>
			</#if>
		<#else>
			<a href="#cc" onclick="replyComment(${comment.id});">回复</a>
		</#if>					
	</#if>
				 <a href="#top">回到顶部</a></span>
				<span>&nbsp;</span>
			</div>
			<div class="commentContent">${comment.content}</div>			
		</td>
	</tr>
</table>
  </#list>
</#if>
<#if pager?? >
  <div class="pagingDiv" id="pager_list" currPage="${pager.currentPage}" pageSize="${pager.pageSize}" totalRows="${pager.totalRows}"></div>
</#if>
<#else>
<div style="color:red;font-weight:bold">评论只有登录用户才能看到。</div>
<div class="pagingDiv" id="pager_list" currPage="1" pageSize="100" totalRows="0"></div>
</#if>

<#if !article.commentState>
<div>当前文章已经被设置为不能评论</div>
<#else>
	<#if enabedComment>
		<#if loginUser??>
		<form id="comment_form" name="comment_form"  action="${SiteUrl}manage/article.action?cmd=comment" method="post">
		<input type="hidden" name="title" value="" />
		<input type="hidden" name="id" value="0" />
		<input type="hidden" name="userName" value="${loginUser.trueName!?html}" />
		  评论星级：
		  <input type="radio" name="star" value="1" /><font color="#FF0000">☆</font>
		  <input type="radio" name="star" value="2" /><font color="#FF0000">☆☆</font>
		  <input type="radio" name="star" value="3" /><font color="#FF0000">☆☆☆</font>
		  <input type="radio" name="star" value="4" /><font color="#FF0000">☆☆☆☆</font>
		  <input type="radio" name="star" value="5" /><font color="#FF0000">☆☆☆☆☆</font>&nbsp;&nbsp;(<font color="#FF0000">☆</font>越多，评价越高。)
		  <br />
		  <textarea name="content" value="" style="width:100%;height:100px"></textarea>

		  <input type="hidden" name="articleId" value="${article.id}" />
		  <input type="hidden" name="returnUrl" value="" />
		  <input type="submit" value="发表评论" onclick="if(this.form.content.value==''){alert('请输入评论内容。');return false;}else{this.form.returnUrl.value=window.location.href;}" />
		</form>
		<#else>
		 <div style='padding:4px;font-weight:bold;'>系统已经设置不允许匿名评论。请登录参与评论。</div>
		</#if>
	<#else>	
		<form id="comment_form" name="comment_form" action="${SiteUrl}manage/article.action?cmd=comment" method="post">
			<input type="hidden" name="title" value="" />
			<input type="hidden" name="id" value="0" />
				<#if loginUser??>
					<input type="hidden" name="userName" value="${loginUser.trueName!?html}" />
				<#else>
					<input type="hidden" name="userName" value="" />
				</#if>
			
			评论星级：
			<input type="radio" name="star" value="1" /><font color="#FF0000">☆</font>
			<input type="radio" name="star" value="2" /><font color="#FF0000">☆☆</font>
			<input type="radio" name="star" value="3" /><font color="#FF0000">☆☆☆</font>
			<input type="radio" name="star" value="4" /><font color="#FF0000">☆☆☆☆</font>
			<input type="radio" name="star" value="5" /><font color="#FF0000">☆☆☆☆☆</font>&nbsp;&nbsp;(<font color="#FF0000">☆</font>越多，评价越高。)
			<br />
			<textarea name="content" value="" style="width:100%;height:100px"></textarea>
			<input type="hidden" name="articleId" value="${article.id}" />
			<input id="btn_submit" type="button" value="发表评论" onClick="if(this.form.content.value==''){alert('请输入评论内容。');return false;}else{submit_comment();}"  />
		</form>
	</#if>
	<a name='cc'></a>
</#if>