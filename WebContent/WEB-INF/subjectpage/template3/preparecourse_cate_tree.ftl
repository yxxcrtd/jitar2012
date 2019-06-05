<#if outHtml?? >
<div class='orange_border'>
  <div class='tree'>
    <script type="text/javascript">
        d = new dTree("d");
        d.add(0,-1,"<b>集体备课</b>","preparecourse.py?subjectId=${subject.metaSubject.msubjId}&amp;gradeId=${grade.gradeId}&unitId=${unitId!}");
        ${outHtml}
        document.write(d);
        d.openAll();
  </script>
  </div>
</div>
</#if >