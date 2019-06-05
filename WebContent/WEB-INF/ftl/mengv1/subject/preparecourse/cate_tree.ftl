<#if outHtml?? >
<div class='orange_border'>
  <div class='tree'>
    <script type="text/javascript">
        d = new dTree("d");
        d.add(0,-1,"<b>集体备课</b>","subjectPrepareCourses.py?subjectId=${subject.metaSubject.msubjId}&amp;gradeId=${grade.gradeId}&amp;target=child");
        ${outHtml}
        document.write(d);
        d.openAll();
  </script>
  </div>
</div>
</#if>