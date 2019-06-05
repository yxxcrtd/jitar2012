<link rel="icon" href="${SiteUrl}images/favicon.ico" />
<link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
<script type='text/javascript'>
var JITAR_ROOT = '${SiteUrl}';
</script>
<#if SubjectUrlPattern??>
<script type='text/javascript'>
var SubjectUrl = "${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode)}";
</script>
<script type='text/javascript' src='${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode)}js/subject/drag.js'></script>
<#else>
<script type='text/javascript'>
var SubjectUrl = "${SiteUrl}";
</script>  
<script type='text/javascript' src='${SiteUrl}js/subject/drag.js'></script>
</#if>