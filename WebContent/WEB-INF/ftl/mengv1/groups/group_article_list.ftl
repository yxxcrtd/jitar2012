<div class='b1'>
  <div class='b1_head2'>
    <div class='b1_head_right'><a href='ktgroups.action?act=all&type=article'>更多…</a></div>
    <div class='b1_head_left'>课题成果-文章</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
      <#if article_list?? >
      <table border='0' cellpadding='1' cellpadding='1' width='246'>
        <#list article_list as article>
            <td><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
            </td>
          </tr>
          </#list>
       </table>
      <#else>
       暂无文章
      </#if>
    </div>
  </div>
</div>

