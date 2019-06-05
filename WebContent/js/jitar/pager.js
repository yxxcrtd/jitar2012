var Pager = !!window.Pager || {};
Pager.goPage = function(pn, callbackurl){
 if(isNaN(parseInt(pn,10))){
  pn = 1;
 }
 else{
  pn = parseInt(pn,10);
 }
 callbackurl = encodeURIComponent(callbackurl);
 if(pn < 1) pn = 1;
 if(callbackurl.indexOf("mod") == -1 ) return;
 if(callbackurl.substring(0,3) != "mod"){
  callbackurl = callbackurl.substr(callbackurl.indexOf("mod"));
 }
 window.location.href = BasePageUrl + "?url=" + callbackurl + "%26page=" + pn;
};
/*
%3F -> ?
%26 -> &
%2F -> /
%20 -> 空格

*/