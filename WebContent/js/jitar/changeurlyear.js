function getHistoryArticle(y)
  {  
  	q = x = [];
  	qs = window.location.search;
  	if(qs == "" || qs == "?" || qs.length < 2 || qs.indexOf("=") == -1)
  	{
  	 window.location.href=window.location.pathname + "?year=" + y;
  	 return;
  	}
  	qs = qs.substring(1);
  	x = qs.split("&");
  	for(i=0;i<x.length;i++)
  	{
  		t = x[i].split("=");
  		
  		if(t[0] != "year" && t[0] != "total" && t[0] != "page")
  		{
  		 if(t.length == 2)
  		 q.push(t[0] + "=" + t[1] + "&");
  		}
  	}
  	if(y!="") q.push("year=" + y);
  	u = q.join("");
  	if(u.substring(u.length-1) == "&") u = u.substring(0,u.length-1);
  	window.location.href=window.location.pathname + "?" + u;
  }