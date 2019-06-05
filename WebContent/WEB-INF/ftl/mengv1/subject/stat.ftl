<div class="c">
    <div class="c_head">
        <div class="c_head_right"></div>
        <div class="c_head_left titlecolor"> 学科统计</div>
    </div>
    
    <div class="c_content">
        <#if subject??>
            <div class="c1">注册用户：${subject.userCount!0}</div>
            <div class="c2">协作组：${subject.groupCount!0}</div>
            <div class="c3">文章/资源：${subject.articleCount!0} / ${subject.resourceCount!0}</div>
            <div class="c3">今日文章/资源：${subject.todayArticleCount!0} / ${subject.todayResourceCount!0}</div>
            <div class="c3">昨日文章/资源：${subject.yesterdayArticleCount!0} / ${subject.yesterdayResourceCount!0}</div>
        </#if>
    </div>
</div>
