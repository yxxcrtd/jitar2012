<div class='orange_border' style='padding:4px;'>
<form method='get' style='text-align:center;'>
  <input type='hidden' name='subjectId' value='${subject.metaSubject.msubjId}' />
  <input type='hidden' name='gradeId' value='${subject.metaGrade.gradeId}' />
  <input type='hidden' name='levelGradeId' value='${levelGradeId!}' />
  关键字：<input class='s_input2' name='k' value="${k!?html}" style='width:300px' />
  <input type='image' src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/b_s.gif' align='absmiddle' />
</form>
</div>
