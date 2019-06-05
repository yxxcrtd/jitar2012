// @Yang Xinxin

function tips(id, str) {
	var l = document.getElementById(id).offsetLeft;
	var t = document.getElementById(id).offsetTop - 23;
	document.getElementById("tips").innerHTML = str;
	document.getElementById("tips").style.left = l + "px";
	document.getElementById("tips").style.top = t + "px";
	document.getElementById("tips").style.display = "";
}

function showTips() {
	document.getElementById("tips").style.display = "none";
}

function changeBgColor(obj, colors) {
	obj.style.backgroundColor = colors;
}
