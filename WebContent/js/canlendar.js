var currentDate2=null;

function Year_Month(){ 
	var now; 
	if(currentDate2==null)
		now = new Date();
	else
		now = currentDate2;
		
	var yy = now.getYear(); 
	var mm = now.getMonth()+1; 
	var cl = '<font color="#0000df">'; 
	if (now.getDay() == 0) cl = '<font color="#c00000">'; 
	if (now.getDay() == 6) cl = '<font color="#00c000">'; 
	return(cl + yy + '年' + mm + '月</font>'); 
} 
function Date_of_Today(){ 
	var now; 
	if(currentDate2==null)
		now = new Date();
	else
		now = currentDate2;
	var cl = '<font color="#ff0000">'; 
	if (now.getDay() == 0) cl = '<font color="#c00000">'; 
	if (now.getDay() == 6) cl = '<font color="#00c000">'; 
	return(cl + now.getDate() + '</font>'); 
} 

function Day_of_Today(){ 
	var day = new Array(); 
	day[0] = "星期日"; 
	day[1] = "星期一"; 
	day[2] = "星期二"; 
	day[3] = "星期三"; 
	day[4] = "星期四"; 
	day[5] = "星期五"; 
	day[6] = "星期六"; 
	var now; 
	if(currentDate2==null)
		now = new Date();
	else
		now = currentDate2;
	var cl = '<font color="#0000df">'; 
	if (now.getDay() == 0) cl = '<font color="#c00000">'; 
	if (now.getDay() == 6) cl = '<font color="#00c000">'; 
	return(cl + day[now.getDay()] + '</font>'); 
} 
function CurentTime(){ 
	var now = new Date(); 
	var hh = now.getHours(); 
	var mm = now.getMinutes(); 
	var ss = now.getTime() % 60000; 
	ss = (ss - (ss % 1000)) / 1000; 
	var clock = hh+':'; 
	if (mm < 10) clock += '0'; 
	clock += mm+':'; 
	if (ss < 10) clock += '0'; 
	clock += ss; 
	return(clock); 
} 
function refreshCalendarClock(){ 
	document.all.calendarClock1.innerHTML = Year_Month(); 
	document.all.calendarClock2.innerHTML = Date_of_Today(); 
	document.all.calendarClock3.innerHTML = Day_of_Today(); 
	document.all.calendarClock4.innerHTML = CurentTime(); 
} 

function showCanlendar2(d)
{
	if(currentDate2==null)
		currentDate2=currentDate
	
	if(currentDate2==null)
		currentDate2=new Date()
	else
		{
		    var year = currentDate.getYear()
		    var month = currentDate.getMonth()
			currentDate2=new Date(year,month,d)			
		}	
			
}
function showCanlendar()
{
var webUrl = webUrl; 
var sHtml;
sHtml='<table border="0" cellpadding="0" cellspacing="0"><tr><td>'; 
sHtml=sHtml+'<table id="CalendarClockFreeCode" border="0" cellpadding="0" cellspacing="0" width="60" height="70" '
sHtml=sHtml+'style="position:absolute;visibility:hidden">'
sHtml=sHtml+'<tr><td align="center"><font '
sHtml=sHtml+'style="cursor:hand;color:#ff0000;font-family:宋体;font-size:14pt;line-height:120%" '
if (webUrl != 'netflower'){ 
	sHtml=sHtml+'</td></tr><tr><td align="center"><font '
	sHtml=sHtml+'style="cursor:hand;color:#2000ff;font-family:宋体;font-size:9pt;line-height:110%" '
} 
sHtml=sHtml+'</td></tr></table>'
sHtml=sHtml+'<table border="0" cellpadding="0" cellspacing="0" width="61" height="70">'
sHtml=sHtml+'<tr><td valign="top" width="100%" height="100%">'
sHtml=sHtml+'<table border="1" cellpadding="0" cellspacing="0" width="58"  height="67">'
sHtml=sHtml+'<tr><td align="center" width="100%" height="100%" >'
sHtml=sHtml+'<font id="calendarClock1" style="font-family:宋体;font-size:7pt;line-height:120%"> </font><br>'
sHtml=sHtml+'<font id="calendarClock2" style="color:#ff0000;font-family:Arial;font-size:14pt;line-height:120%"> </font><br>'
sHtml=sHtml+'<font id="calendarClock3" style="font-family:宋体;font-size:9pt;line-height:120%"> </font><br>'
sHtml=sHtml+'<font id="calendarClock4" style="color:#100080;font-family:宋体;font-size:8pt;line-height:120%"><b> </b></font>'
sHtml=sHtml+'</td></tr></table>'
sHtml=sHtml+'</td></tr></table>'
sHtml=sHtml+'</td></tr></table>'
document.all("dateTag").innerHTML=sHtml
setInterval('refreshCalendarClock()',1000); 
}
