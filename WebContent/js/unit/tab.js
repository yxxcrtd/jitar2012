var $ = function(id) {return document.getElementById(id);};
var TabUtil = !!window.TabUtil || {};
TabUtil.changeTab = function(commonID,curIndex)
{
	var o = $(commonID).getElementsByTagName("div");
	for(var i = 0;i<o.length;i++)
	{
		o[i].className = i == curIndex?"cur":"";
		$(commonID + i).style.display = i == curIndex?"":"none";
	};
};

var CommonUtil = !!window.CommonUtil || {};

CommonUtil.reFixImg = function(oImg,ConstWidth,ConstHeight)
{	
	//如果宽度和高度都不超，就不进行缩放了。
	w = oImg.width;
	h = oImg.height;
	if( w > ConstWidth && h > ConstHeight)
	{		
		//alert('step1' + oImg.src)
		if(w / h > ConstWidth / ConstHeight )
		{
			oImg.width = ConstWidth;
		}
		else
		{
			oImg.height = ConstHeight;
		}
	}
	else if( w > ConstWidth )
	{	
		oImg.width = ConstWidth;
	}
	else if(h > ConstHeight)
	{
		oImg.height = ConstHeight;
	}
	else
	{
		//一切ok		
	}
	oImg.style.visibility = 'visible';
	return;
};