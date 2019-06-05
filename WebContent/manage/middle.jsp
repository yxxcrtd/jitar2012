<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title></title>
  <style type="text/css">
   html,body {height:100%;}
   html,body,div,td,img,tr {cursor:pointer;overflow:hidden;padding:0;margin:0;border:0;}
  </style>
  <script type="text/javascript">
  //<![CDATA[  
  function changeFrame()
  {
  	var f = window.parent.document.getElementById("fst")
  	if(f == null) return;
  	if(f.getAttribute("cols") == "180,6,*")
  	{
	 f.setAttribute("cols","0,6,*")
     document.getElementById("_img").src="images/bl_1.gif";
    }
  	else
  	{
	 f.setAttribute("cols","180,6,*")
     document.getElementById("_img").src="images/al_1.gif";
  	}
  }
  //]]>
  </script>
</head>
 <body onclick="changeFrame()">
 <table style="height:100%" cellpadding="0" cellspacing="0" border="0">
 <tr>
 <td><div><img id="_img" src="images/al_1.gif" border="0" alt="" title="切换界面" /></div></td>
 </tr>
 </table>
 </body>
</html>
