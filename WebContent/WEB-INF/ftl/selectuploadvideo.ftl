<html>
<head>
<title>上载选择视频</title>
<script type="text/javascript">
  function Ret(id) {
    if (id==-1)
    {
        alert("上载的视频需要审核");
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

<iframe name="_m" src="${SiteUrl}manage/video.action?cmd=upload&needreturn=1" style="width:115%;height:115%;border:0px"></iframe>

</body>
</html>