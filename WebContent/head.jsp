<%@page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%><%
  //如果传递的时候带返回地址，则直接取，这是最好的方法
  String url = request.getRequestURL().toString();
  url = url.substring(0,url.lastIndexOf("/")) + "/";
  String contextPath = request.getContextPath() + "/";
%><!doctype html><html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta itemprop="image" content="<%=contextPath %>images/favicon.png">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">  
<meta name="publishtime" content="2013-09-10 11:21:21" />
<title>教研平台首页</title>
<link rel="icon" href="<%=contextPath %>favicon.ico" />
<link rel="shortcut icon" href="<%=contextPath %>favicon.ico" />
<link rel="stylesheet" href="<%=contextPath %>skin/default/css/common.css" type="text/css" />
<link rel="stylesheet" href="<%=contextPath %>skin/default/css/index.css" type="text/css" />
<script src="<%=contextPath %>js/new/jquery.js" type="text/javascript"></script>
<script src="<%=contextPath %>js/new/jscroll.js" type="text/javascript"></script>
<script src="<%=contextPath %>js/new/index.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=contextPath %>js/new/show_photo.js" type="text/javascript" charset="utf-8"></script>
<!--[if IE 6]>
<script src="<%=contextPath %>js/new/ie6.js" type="text/javascript"></script>
<script src="<%=contextPath %>js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.login a,.videoPlay,.videoPlayBg,.tx');
</script>
<![endif]-->
</head>
<body>
<div class="banner">
  <div class="main">
      <div class="bannerImg"><img src="<%=contextPath %>skin/default/images/banner.jpg" /></div>
        <div class="bannerMask"></div>
    </div>
</div>
<div class="main mt25 clearfix" style="background:#fff">