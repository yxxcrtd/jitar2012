<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title></title>
  <style>
  html,body{ font-size:13px; }
  </style>
</head>
<body>
<h2>频道管理说明：</h2>
<div>
<div>一、开放模板、样式是为了方便定制个性化的页面。 对于模板、样式的修改，需要熟悉 CSS、HTML 的知识才能进行操作，如果不会这些，建议采用默认设置。定制模板可以使用的变量：</div>
<div>
<ul>
<li>SiteUrl：代表网站的根路径，在个性化域名的设置中，是代表总站的根路径，而不是个性域名的根路径。调用方法：${'$'}{SiteUrl} 最终可以生成${SiteUrl}。</li>
<li>channel：代表当前的频道对象，常用的有 title(频道名称)、cssStyle(频道自定义CSS)和logo（频道logo）等属性。调用方法：${'$'}{channel.title}。</li>
</ul>
</div>
</div>
<div>二、不建议对模块显示模板进行修改，除非你对各个模块的数据结构非常熟悉。</div>
</body>
</html>