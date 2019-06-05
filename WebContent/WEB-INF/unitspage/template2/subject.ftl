<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'></div>
    <div class='gly_head_left'>&nbsp;${webpart.displayName}</div>
  </div>
  <div style='text-align:center;padding:6px'>
  <#if unitsubjectlist??>
  <#list unitsubjectlist as us >
  	<a href='${SiteUrl}subject.py?unitId=${unit.unitId}&id=${us.subjectId!}&subjectId=${us.metaSubjectId!}&gradeId=${us.metaGradeId}'>${us.displayName}</a><#if us_has_next> | </#if>
  </#list>
  </#if>
</div>
</div>