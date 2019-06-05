var caution = false
var currentDate=null

function fixDate(date) {
        var base = new Date(0)
        var skew = base.getTime()
        if (skew > 0)
                date.setTime(date.getTime() - skew)
}


function getTime() {
        var now = new Date()
        var hour = now.getHours()
        var minute = now.getMinutes()
        now = null
        var ampm = ""
        if (hour >= 12) {
                hour -= 12
                ampm = "PM"
        } else
                ampm = "AM"
        hour = (hour == 0) ? 12 : hour
        if (minute < 10)
                minute = "0" + minute
        return hour + ":" + minute + " " + ampm
}
function leapYear(year) {
        if (year % 4 == 0)
                return true
        return false
}

function getDays(month, year) {
        var ar = new Array(12)
        ar[0] = 31
        ar[1] = (leapYear(year)) ? 29 : 28 // February
        ar[2] = 31
        ar[3] = 30
        ar[4] = 31
        ar[5] = 30
        ar[6] = 31
        ar[7] = 31
        ar[8] = 30
        ar[9] = 31
        ar[10] = 30
        ar[11] = 31
        return ar[month]
}
function getMonthName(month) {
        var ar = new Array(12)
        ar[0] = "一月"
        ar[1] = "二月"
        ar[2] = "三月"
        ar[3] = "四月"
        ar[4] = "五月"
        ar[5] = "六月"
        ar[6] = "七月"
        ar[7] = "八月"
        ar[8] = "九月"
        ar[9] = "十月"
        ar[10] = "十一月"
        ar[11] = "十二月"
        return ar[month]
}
function preMonth()
{
    var year = currentDate.getFullYear()
    var month = currentDate.getMonth()
    var date = currentDate.getDate()
	__GetCalendarMsg(year,month);	
}
function nextMonth()
{
    var year = currentDate.getFullYear()
    var month = currentDate.getMonth()
    var date = currentDate.getDate()
	__GetCalendarMsg(year,month+2);		
}

function setCurrentMonthCal()
{
	showDateMsg();
}

function setCal(now) {
        var year = now.getFullYear()
        var month = now.getMonth()
        var monthName = getMonthName(month)
        var date = now.getDate()
        now = null
        var firstDayInstance = new Date(year, month, 1)
        var firstDay = firstDayInstance.getDay()
        firstDayInstance = null
        var days = getDays(month, year)
        drawCal(firstDay + 1, days, date, monthName, year,month+1)
}
function drawCal(firstDay, lastDate, date, monthName, year,month) {
        var headerHeight = 40
        var border = 0
        var cellspacing = 0
        var headerColor = "#000000"
        var headerSize = "+1"
        var Weeksize="-1"
        var fondsize="-2"
        var colWidth = 22
        var dayCellHeight = 20
        var dayColor = "darkblue"
        var cellHeight = 22
        var todayColor = "red"
        var timeColor = "purple"
        var text = ""
        
        text += '<CENTER>'
        text += '<TABLE width="206" BORDER=' + border + ' cellpadding=\"0\" CELLSPACING=' + cellspacing + '>'
        text += '<TD align=\"center\"  class=\"calendarTitleFont\" HEIGHT=' + headerHeight + '>'
	        text += '<TABLE width="206" BORDER="0"  HEIGHT=' + headerHeight + ' cellpadding=\"0\" CELLSPACING=' + cellspacing + '>'
	        text += '<TR>'
	        text += '<TD width=30></TD>'
	        text += '<TD align="Left" width="30"><a href="#" onclick="preMonth();return false;"><img border=0 src=\"riliright.gif\" title=\"上月\"></a></TD>'
	        text += '<TD align="center">' 
	        text += '<FONT style=\"FONT-SIZE:10pt\">'
	        text += monthName + ' ' + year
	        text += '</FONT>'
	        text += '</TD>'
	        text += '<TD align="right" width="30"><a href="#" onclick="nextMonth();return false;"><img border=0 src=\"rilileft.gif\" title=\"下月\"></a></TD>'
	        text += '<TD width=30></TD>'
	        text += '</TR>'
	        text += '</TABLE>'
        text +='</TD>'
        text +='</TR>'
        
        text +='<TR>'
        text +='<TD align=\"center\" valign=\"middle\">'
        text += '<TABLE width="154" border=0 cellpadding=\"0\" CELLSPACING=0  height=132 >'
         
        var digit = 1
        var curCell = 1
        var msgColor=""
        var backg=""
        var lrow=0

        text +='<TR>'
        text +='<TD><Font Size='+Weeksize+'>日</Font></TD><TD><Font Size='+Weeksize+'>一</Font></TD><TD><Font Size='+Weeksize+'>二</Font></TD><TD><Font Size='+Weeksize+'>三</Font></TD><TD><Font Size='+Weeksize+'>四</Font></TD><TD><Font Size='+Weeksize+'>五</Font></TD><TD><Font Size='+Weeksize+'>六</Font></TD>'
        text +='</TR>'
        
        for (var row = 1; row <= Math.ceil((lastDate + firstDay - 1) / 7); ++row) {
        	lrow=lrow+1
                text += '<TR>'
                for (var col = 1; col <= 7; ++col) {
                        if (digit > lastDate)
                        	{
                        		for(var icol=0;icol<=7-col;icol++)
                        		text += '<TD HEIGHT=22 WIDTH=22></TD>'
                                break
                            }  
                        if (curCell < firstDay) {
                                text += '<TD HEIGHT=22 WIDTH=22></TD>';
                                curCell++
                        } else {
                                if (digit == date) {
                                	if(daymsg[digit]=="")
                                		{
                                		backg="style=\"text-align:center;\""
                                		text += '<TD '+backg+' HEIGHT=22 WIDTH=22>'
                                		}
                                	else
                                		{
                                		//onmouseover
                                		backg="style=\"background:url(rilicell1.gif);text-align:center;cursor:hand;\""
                                        text += '<TD '+backg+' HEIGHT=22 WIDTH=22 onclick="showCalendarCard(event,'+year+','+month+','+digit+');">'
                                        }
									    if(col == 1)
									          text += '<FONT COLOR="red" Size='+fondsize+'>'
									    else if(col == 7)
									    	  text += '<FONT  COLOR="red" Size='+fondsize+'>'
									    else	  	
									          text += '<FONT  Size='+fondsize+'>'
                                        
                                        text += digit + '</FONT>'
                                        text += '</TD>'
                                } else{
                                	if(daymsg[digit]=="")
                                		{
                                		backg="style=\"text-align:center;\""
                                		text += '<TD '+backg+' HEIGHT=22 WIDTH=22 HEIGHT=' + cellHeight + '>'
                                		}
                                	else
                                		{
                                		//onmouseover
                                		backg="style=\"background:url(rilicell1.gif);text-align:center;cursor:hand;\""
                                		text += '<TD '+backg+' HEIGHT=22 WIDTH=22  onclick="showCalendarCard(event,'+year+','+month+','+digit+');"  HEIGHT=' + cellHeight + '>'
                                     	}
								    if(col == 1)
								          text += '<FONT  COLOR="red" Size='+fondsize+'>'
								    else if(col == 7)
								    	  text += '<FONT  COLOR="red" Size='+fondsize+'>'
								    else	  	
								          text += '<FONT  Size='+fondsize+'>'
                                       text += digit 
                                       text += '</FONT>'
                                       text += '</TD>'
                                       }
                                digit++
                        }
                }
                text += '</TR>'
        }
        for(var row=0;row<(6-lrow);row++)
        {
        	text += '<TR>'
        	 for (var col = 1; col <= 7; ++col) {
        	 	text += '<TD HEIGHT=22 WIDTH=22 ></TD>'
        	 }
        	text += '</TR>'
        }
        text += '</TABLE>'
        text +='</TD>'
        text +='</TR>'
        text += '</TABLE>'
        text += '</CENTER>'
        //document.write(text)
        document.all("showdatecanlendar").innerHTML=text
}
