function GetTemplate(tpname,channelId){
	url = "../manage/channel.action?cmd=gettemplate&name=" + tpname + "&channelId=" + channelId + "&" + (new Date()).getTime();
	h = new window.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject("MSXML2.XMLHTTP");
	h.open("GET", url,true);
	h.setRequestHeader("Connection", "close");
	h.onreadystatechange = function(){
		if(h.readyState == 4){
			if(h.status == 200){
				document.getElementsByName("template")[0].value = h.responseText;
			}
		}
	};
	h.send(null);
}