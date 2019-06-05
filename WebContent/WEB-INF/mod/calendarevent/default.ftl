<html>
<head>
<script src='${SiteUrl}js/jitar/core.js'></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
<script src='${SiteUrl}js/canlendarEx.js' type='text/javascript'></script>
<script language="javascript">
	var currentdateObj=null;
    //当天的提示信息
	var daymsg = new Array();
		daymsg[0]="";
	 	daymsg[1]="";
	  	daymsg[2]="";
	 	daymsg[3]="";
	 	daymsg[4]="";
	 	daymsg[5]="";
	 	daymsg[6]="";
	 	daymsg[7]="";
	 	daymsg[8]="";
	 	daymsg[9]="";
	 	daymsg[10]="";
	 	daymsg[11]="";
	 	daymsg[12]="";
	 	daymsg[13]="";
	 	daymsg[14]="";
		daymsg[15]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[16]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[17]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[18]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[19]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[20]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[21]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[22]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[23]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[24]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[25]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[26]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[27]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[28]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[29]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[30]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[31]=""; 	
	<#if calendars?? >
		<#list calendars as calendar>
			<#if calendar.eventTimeBegin??>
				<#assign d1 = calendar.eventTimeBegin?string("d")>
				daymsg[${d1}]=daymsg[${d1}]+"<li><a href='${calendar.url}'>${calendar.title}</a>";
			</#if>
			
			<#if calendar.eventTimeEnd??>
					<#assign d2 = calendar.eventTimeEnd?string("d")>
					<#if d1!=d2>
					daymsg[${d2}]=daymsg[${d2}]+"<li><a href='${calendar.url}'>${calendar.title}</a>";
				    </#if>
			</#if>
		</#list>
	</#if>
   function showCalendarCard(e,year,month,day)
   {	//showModelessDialog  showModalDialog
   		//计算窗口显示的位置
		e = window.event || e;
		e.cancelBubble = true;
		var posleftValue = e.screenX; 
		var postopValue = e.screenY;
		
		if(posleftValue>350)
		{
			posleftValue=posleftValue-350;
		}
		else
		{
			posleftValue=posleftValue+50;
		}
		var posleft = posleftValue + "px"; 
		var postop = postopValue + "px";
   		var url = 'showMsg.py?year='+year+'&month='+ month +'&day='+day+'&parentGuid=${parentGuid}&parentType=${parentType}&tmp=' + Math.random();
   		window.showModalDialog(url,"","dialogHeight:200px;dialogWidth:300px;dialogLeft:"+posleft+";dialogTop:"+postop+";resizable:yes;");
   }	
   function __GetCalendarMsg(year,month)
   {
	  var url = 'default.py?year='+year+'&month='+ month +'&parentGuid=${parentGuid}&parentType=${parentType}&tmp=' + Math.random();
	  document.location.href=url;
   }
   
function showDateMsg()
 {
 	//显示当天的提示信息
 	currentDate=new Date(${year},${month}-1,${day});
 	setCal(currentDate);
 }
 function show()
 {
 	currentDate=new Date(${year},${month}-1,${day});
	setCal(currentDate)
 }
 function setMsg(i,s)
 {
 	daymsg[i]=s; 	 	 	 	 	 	 	 	 	 	 	  	
 }

 
function hideMenu(obj){
	obj.style.display="none";
}

function keepMenu(obj){
	obj.style.display="block";
}
</script>
	<link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
	<link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
	<!-- 布局模板 -->
	<link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_${(page.layoutId!'1')}.css" />
	<!-- ToolTip -->
	<link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
</head>
<body style="margin:0px 0px 0px 0px; padding: 0px;FONT-SIZE:9pt;background-color:transparent">
<table border="0" cellpadding="0" cellspacing="0" width="206" height="209" align="center">
	<tr>
		<td  id="showdatecanlendar">
			<script>showDateMsg();</script>
		</td>
	</tr>											
</table>

<script language="javascript">
 if(!window.attachEvent && window.addEventListener)
{
  window.attachEvent = HTMLElement.prototype.attachEvent= document.attachEvent = function(en, func, cancelBubble)
  {
    var cb = cancelBubble ? true : false;
    this.addEventListener(en.toLowerCase().substr(2), func, cb);
  };
  window.detachEvent = HTMLElement.prototype.detachEvent= document.detachEvent = function(en, func, cancelBubble)
  {
    var cb = cancelBubble ? true : false;
    this.removeEventListener(en.toLowerCase().substr(2), func, cb);
  };
}

document.attachEvent("onmouseover", function(){
	try{
		$("CalendarCard_Layer").style.display="none";
	}
	catch(ex){}
	}
);	
</script>
</body>
</html>