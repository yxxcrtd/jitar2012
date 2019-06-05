<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_left'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
	<#if subject??>
        <div class="c1">学科用户数：${subject.userCount!0}</div>
        <div class="c2">协作组数：${subject.groupCount!0}</div>
        <div class="c3">学科文章数：${subject.articleCount!0+subject.historyArticleCount!0}</div>
        <div class="c3">学科资源数：${subject.resourceCount!0}</div>
        <div class="c3">学科视频数：${subject.videoCount!0}</div>
        <div class="c3">学科集备数：${subject.prepareCourseCount!0}</div>
        <div class="c3">学科活动数：${subject.actionCount!0}</div>
    </#if>
  </div>
</div>