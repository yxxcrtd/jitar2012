<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" /> 
 <script type="text/javascript" src="js/jitar/dtree.js"></script>
<script type="text/javascript">
function doOK(id,name)
{	  
	window.returnValue =name+'|'+id ;
	window.close();
}

/* 函数传递3个参数，event 对象、节点的id 、节点名称 */
function nodeOnClick(evt)
{
    if (/Firefox[\/\s](\d+\.\d+)/.test(navigator.userAgent)){
     var ffversion=new Number(RegExp.$1)
     if(ffversion < 3)
     {
      window.parent.opener.document.theForm.restype.value = arguments[2];
      window.parent.opener.document.theForm.resTypeID.value = arguments[1];
      window.parent.close();
      return;
     }
    }
    
    
    window.parent.returnValue =  arguments[2] +'|'+ arguments[1] ;
    window.parent.close();
    //document.getElementById("debug").innerHTML = arguments[0] + " " + arguments[1] + " " + arguments[2]
}
</script>
</head>
<body>
<div style="width:400px">
 
 <script type="text/javascript">
  <!-- 
  
   d = new dTree('d');
   ${resType_js}
   document.write(d);
  //-->
 </script>
</div>
</body>
</html> 