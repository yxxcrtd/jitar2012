<div>
<form name='blog_search' action='${SiteUrl}u/${user.loginName}/py/user_article_search.py'>
  <input type='hidden' name='userId' value='${user.userId}' />
  <input type='text' name='k' value='${k!?html}' size='30' style='width:100px;' />
  <input type='submit' value='搜索' />
</form>
</div>