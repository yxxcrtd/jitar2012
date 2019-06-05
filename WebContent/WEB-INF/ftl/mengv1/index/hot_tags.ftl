<div class='r1' style='height:140px; overflow: hidden;'>
  <div class='r1_head'>
    <div class='r1_head_right'><a href='tags.action'>更多...</a></div>
    <div class='r1_head_left'>热点标签</div>
  </div>
  <div class='r1_content'>
  <#if hot_tags?? ><#list hot_tags as t ><a href='show_tag.action?tagId=${t.tagId}'>${t.tagName}</a> </#list></#if>
  </div>
</div>