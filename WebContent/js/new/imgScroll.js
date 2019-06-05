var isround = "";
var scrollmove = "";
var masktime = 10;
var focus_cur = 1;
var p = null;
var h = null;
window.onload=function(){
 if(document.getElementById("pic")){
   p = document.getElementById("pic").getElementsByTagName("li");
 }
 if(document.getElementById("tip")){
   h = document.getElementById("tip").getElementsByTagName("li");
 } 
};
function change(id){
	clearTimeout(isround);
	clearInterval(scrollmove);
	for (var i = 1; i <= h.length; i++) {
		if(i == id){
			document.getElementById("smallimg_"+i).className="active";
		}else{
			document.getElementById("smallimg_"+i).className="";
		}
	}
	if ((next = id + 1) > h.length){
		next = 1;
	}
	isround = setTimeout("change("+next+")",5000);
	scrollmove = setInterval("scrollMove("+id+")",masktime);
	focus_cur = id;
}
function scrollMove(m){
	var srl = document.getElementById("pic").scrollLeft;
	var dsrl = Math.floor((p[0].clientWidth*(m-1)-srl)/5);
	var xsrl = Math.ceil((p[0].clientWidth*(m-1)-srl)/5);
	if(srl > p[0].clientWidth*(m-1)){
		document.getElementById("pic").scrollLeft = srl + dsrl;
	}else if(srl < p[0].clientWidth*(m-1)){
		document.getElementById("pic").scrollLeft = srl + xsrl;
	}else{
		clearInterval(scrollmove);
	}
}
function focus_prev(){
	var prev = (focus_cur+3)%4;
	if(prev == "0"){
		prev = 4;
	}
	change(prev);
}
function focus_next(){
	var next = (focus_cur+1)%4;
	if(next == "0"){
		next = 4;
	}
	change(next);
}
if(p && p.length>1){
	isround = setTimeout("change(2)",5000);
}