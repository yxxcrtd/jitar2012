<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />
  <style>
  html,body{padding:0;margin:0;border:0;width:auto;}
  </style>
  <script src='js/jitar/core.js'></script>
  <script src='js/jitar/dtree.js'></script>
  <script>
  function resizeIframe()
  {
  	h = Math.max(document.body.offsetHeight,document.documentElement.offsetHeight);
  	window.parent.document.getElementById("_left").style.height = h + 20 + "px";
  }
  </script>
 </head>
 <body onload="resizeIframe()" onclick="resizeIframe()">

    <div class='res1'>
      <div class='res1_c'>
<script type="text/javascript">
  d = new dTree("d");
  //function Node(id, pid, name, url, title, target, icon, iconOpen, open) 
  d.add(0,-1,"<b>全部评课</b>","evaluations_middle.py?show=all","","_middle");
  ${outHtml}
  document.write(d);
  //d.openAll();
</script>
</div>
</div>
</body>
</html>