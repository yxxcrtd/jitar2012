if (!!!window.MarqueeScroll) {
	var MarqueeScroll = function(containerId, contentId, step, speed) {
		var Container = document.getElementById(containerId);
		var Content = document.getElementById(contentId);
		var Guid = ("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx".replace(/[x]/g,
				function(c) {
					var r = Math.random() * 16 | 0, v = c == 'x' ? r: r & 0x3 | 0x8;
					return v.toString(16);
				}).toUpperCase());
		if (typeof (window.Marquees) == "undefined") {
			window.Marquees = {};
			window.Marquees.Get = function(i) {
				return window.Marquees[i];
			};
		}
		window.Marquees[Guid] = this;
		var contentWidth = Content.offsetWidth;
		// 对于内容少的场合，需要补齐所有内容才能进行滚动。
		var addNumber = Math.floor(Container.offsetWidth / contentWidth) + 1;

		if (Content.tagName.toLowerCase() == "table") {
			var cellCount = Content.rows[0].cells.length;
			if (cellCount == 0) {
				// 防止支持出现<table><tr></tr></table>这样的单元格
				return;
			}
			for (i = 0; i < addNumber; i++) {
				for (k = 0; k < cellCount; k++) {
					td = Content.rows[0].insertCell(Content.rows[0].cells.length);
					td.innerHTML = Content.rows[0].cells[k].innerHTML;
				}
			}
		} else {
			for (i = 0; i < addNumber; i++) {
				o = Content.cloneNode(true);
				Content.appendChild(o);
			}
		}

		this.MarTimer = null;
		this.Init = function() {
			if (Container.scrollLeft >= contentWidth) {
				Container.scrollLeft = 0;
			} else {
				Container.scrollLeft += step;
			}
		};
		this.Stop = function() {
			if (window.Marquees[Guid].MarTimer) {
				window.clearInterval(window.Marquees[Guid].MarTimer);
			}
		};
		this.Start = function() {
			window.Marquees[Guid].MarTimer = window.setInterval(
					window.Marquees[Guid].Init, speed);
		};
		this.Start();
		Container.onmouseover = window.Marquees[Guid].Stop;
		Container.onmouseout = window.Marquees[Guid].Start;
	};
}