var ShareUrl = {};
ShareUrl["wangyiweibo"] = "http://t.163.com/article/user/checkLogin.do?link={url}&source={title}&info={title} {url}&images={pic}&togImg=true"; 
ShareUrl["sohuweibo"] = "http://t.sohu.com/third/post.jsp?url={url}&title={title}&content=utf-8&pic={pic}";
ShareUrl["kaixin"] = "http://www.kaixin001.com/rest/records.php?url={url}&content={title}&pic={pic}&style=111";
ShareUrl["sinaweibo"] = "http://v.t.sina.com.cn/share/share.php?title={title}&url={url}";
ShareUrl["qzone"] = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url={url}&title={title}&pics={pic}&summary={content}";
ShareUrl["renren"] = "http://share.renren.com/share/buttonshare.do?link={url}&title={title}";
ShareUrl["qqweibo"] = "http://v.t.qq.com/share/share.php?title={title}&site={site}&pic={pic}&url={url}";
ShareUrl["baidu"] = "http://apps.hi.baidu.com/share/?url={title}&title={title}";
ShareUrl["ifeng"] = "http://t.ifeng.com/interface.php?_c=share&_a=share&sourceUrl={url}&title={title}&pic={pic}&source={url}&type=0";
ShareUrl["xinhuaweibo"] = "http://t.home.news.cn/share.jsp?url={url}&type=3&appkey=&title={title}&pic={pic}&rnd=" + (new Date()).valueOf();
	
var Share = !!window.Share || {};
Share.shareTo = function(sname,p,c)
{
 var tt = window.document.title==""?encodeURIComponent(window.location.href):encodeURIComponent(window.document.title);
 var uu = encodeURIComponent(window.location.href);
 var pp = "",cc = "";
 if(arguments.length > 1) pp = encodeURIComponent(p);
 if(arguments.length > 2) cc = encodeURIComponent(c);
 if(cc == "") cc = tt;
 var url = ShareUrl[sname].replace(/{url}/g,uu).replace(/{title}/g,tt).replace(/{pic}/g,pp).replace(/{content}/g,cc);
 window.open(url,"_blank","width=800,height=600,scrollbars=1,resizable=1");
 return false;
};