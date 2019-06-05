<html>
<head>
<title>上载选择资源</title>
<script type="text/javascript">
  function Ret(id) {
    if (id==-1)
    {
        alert("上载的资源需要审核");
    }
    else
    {
        window.returnValue=id;
    }
    window.close();
    }
</script>
</head>
<body>
<iframe name="_m" src="${SiteUrl}manage/resource.action?cmd=upload&needreturn=1" style="width:100%;height:100%"></iframe>
</body>
</html>