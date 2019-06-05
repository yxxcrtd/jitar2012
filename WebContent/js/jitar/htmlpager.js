var Class =window.Class || {
	create : function() {
		return function() {
			this.initialize.apply(this, arguments);
		};
	}
};
var Pager = Class.create();
Pager.prototype = {
	initialize : function(divId, pageCount, offsetCount, callBack) {
		this.pageCount = pageCount;
		this.offsetCount = offsetCount;
		this.callBack = callBack;
		this.current = 1;
		this.preLabel = "上一页";
		this.nextLabel = "下一页";
		this.divId = divId;
		obj = this;
	},
	transferTo:function(evt)
	{
		var ele = evt?evt.target:window.event.srcElement;
		ele = ele.previousSibling;
		while(ele.nodeType != 1) ele = ele.previousSibling;
		var goPage = parseInt(ele.value,10);
		if(isNaN(goPage)) goPage = 1;
		if(goPage<1) goPage=1;
		if(goPage>this.pageCount) goPage=this.pageCount;
		this.callBack(goPage);
		this.current = goPage;
		this.build();
	},
	build : function() {
		if (this.pageCount < 1)
			return;
		var MaxStart = this.pageCount - this.offsetCount * 2;
		if (MaxStart < 1)
			MaxStart = 1;
		var MinEnd = this.offsetCount * 2 + 1;

		var start = this.current - this.offsetCount;
		var end = this.current + this.offsetCount;
		if (start < 1) {
			start = 1;
			end = MinEnd;
		}
		if (end > this.pageCount) {
			end = this.pageCount;
			start = MaxStart;
		}
		var showGoInput = false;
		var h = "<a href='javascript:void(0)'>" + this.preLabel + "</a> ";
		if(this.current == 1) h = "";
		if (start > 1)
			h += "<a href='javascript:void(0)'>1</a> ";
		if (start > 2){
			showGoInput = true;
			h += "<span class='dot'>...</span>";
		}
			
		var lastNo = 0;
		for (i = start; i < end; i++) {

			if (i == this.current) {
				h += "<span class='current'>" + i + "</span> ";
			} else {
				h += "<a href='javascript:void(0)'>" + i + "</a> ";
			}
			lastNo = i;
		}
		if (start < MaxStart){
			showGoInput = true;
			h += "<span class='dot'>...</span>";
		}
		if (this.current == this.pageCount) {
			h += "<span class='current'>" + this.current + "</span> ";
		} else {
			h += " <a href='javascript:void(0)'>" + this.pageCount + "</a>";
		}
		if(this.current != this.pageCount){
			h += "<a href='javascript:void(0)'>" + this.nextLabel + "</a> ";
		}
		if(showGoInput){
			h += " <input size='4' class='transferinput'/> <input type='button' onclick='obj.transferTo(event)' value='转到' class='transferto' />";
		}
		document.getElementById(this.divId).innerHTML = h;
		var bar = document.getElementById(this.divId).getElementsByTagName("a");
		for (i = 0; i < bar.length; i++) {
			bar[i].onclick = function(evt) {
				var pageNo = parseInt(this.innerHTML, 10);
				if (isNaN(pageNo)) {
					if (this.innerHTML == obj.preLabel)
						pageNo = Math.max(1, obj.current - 1);
					else if (this.innerHTML == obj.nextLabel)
						pageNo = Math.min(obj.pageCount, obj.current + 1);
					else {
						pageNo = 1;
					}
					;
				}

				obj.callBack(pageNo);
				obj.current = pageNo;
				obj.build();
				if (!evt)
					var evt = window.event;
				evt.cancelBubble = true;
				if (evt.stopPropagation)
					evt.stopPropagation();
			};
		}
	}
};