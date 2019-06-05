<div class="b3">    
<div class="b1_head_c">
<div class="b1_head_right" style="display:none">更多…</div>
<div class="b1_head_left"> 站点统计</div>
</div>
<div class="b1_content">
<div>
<div class="tc1">
<#if site_stat??>
<div class="c1">注册用户数:${site_stat.userCount!0}</div>
 <div class="c2">协作组数:${site_stat.groupCount!0}</div>
 <div class="c5">总文章数:${site_stat.totalArticleCount}</div>
 <div class="c5">总资源数:${site_stat.totalResourceCount!0}</div>
 <div class="c5">今日文章/资源:${site_stat.todayArticleCount!0}/${site_stat.todayResourceCount!0}</div>
 <div class="c5">昨日文章/资源:${site_stat.yesterdayArticleCount!0}/${site_stat.yesterdayResourceCount!0}</div>
 <div class="c5">评论数:${site_stat.commentCount!0}</div>
 <div class="c5">图片数:${site_stat.photoCount!0}</div>
 <div class="c5">视频数:${site_stat.videoCount!0}</div>
 <div class="c5">集备数:${site_stat.prepareCourseCount!0}</div> 
</#if>
</div>
</div>
</div>
</div>
</div>
