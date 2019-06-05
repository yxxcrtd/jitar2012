<link rel="icon" href="${SiteUrl}images/favicon.ico" />
<link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
<#if UnitUrlPattern??>
<script type='text/javascript'>
var UnitUrl = "${UnitUrlPattern.replace('{unitName}',unit.unitName)}";
</script>
<script type='text/javascript' src='${UnitUrlPattern.replace('{unitName}',unit.unitName)}js/unit/drag.js'></script>
<#else>
<script type='text/javascript'>
var UnitUrl = "${SiteUrl}";
</script>
<script type='text/javascript' src='${SiteUrl}js/unit/drag.js'></script>
</#if>