
var lastTab = null;
function ChangeTab(oLI) {
  if (lastTab ) {
    lastTab.style.backgroundImage = "url(images/left_tab_Selectedno.gif)";
    lastTab.style.color = "#069";
  }
  oLI.style.backgroundImage = "url(images/left_tab_Selected.gif)";
  oLI.style.color = "#000";
  lastTab = oLI;
}
var lastItem = null;
function MouseEvent(oDIV, evt, currentDiv) {
  var eventType = evt.type;
  switch (eventType) {
    case "mouseover":
    if (oDIV.className == "CateItem") {
      oDIV.className = "CateItemMouseover";
    }
    break;
    case "mouseout":
    if (oDIV.className == "CateItemMouseover") {
      oDIV.className = "CateItem";
    }
    break;
    case "click":
    if (lastItem) {
      lastItem.className = "CateItem";
    }
    lastItem = oDIV;
    oDIV.className = "CateItemSelected";
          //修改标签
    if (arguments.length > 2) {
      ShowHiddenDiv(currentDiv);
    }
    break;
    default:
    break;
  }
}
function RefreshWindow(u, t) {
  lastTab.className = "";
  document.getElementById("CurrentOp").innerHTML = t;
  document.getElementById("CurrentOp").className = "Selected";
  lastTab = document.getElementById("CurrentOp");
  window.frames["mainframe"].location = u;
}
function ShowHiddenDiv(obj) {

  var panelRight = document.getElementById("RightPanel");

  var nodes = panelRight.childNodes;
  for (i = 0; i < nodes.length; i++) {
    if (nodes[i].nodeType != 1) {
      continue;
    }
    if (nodes[i].getAttribute("id").substr(0, "RightPanelBlock".length) == "RightPanelBlock") {
      nodes[i].style.display = "none";
    }
  }
  document.getElementById(obj).style.display = "block";
}
function InitUIData() {
  //初始化第一个选择项
  //左边第一个标签选择
  lastItem = document.getElementById("LeftTab").getElementsByTagName("DIV")[0];
  //右边第一个标签选择
  lastTab = document.getElementById("Tab1");
      
  // 初始化消息
  // 先加载一次
  // GetContent("GetMsg.asp");
  // window.setInterval("GetContent('GetMsg.asp?" + Date.parse(new Date()) + "')", 120000);
}
     
     //数据列表样式变化
var lastDataItem = null;
function DataItemStyle(oDIV, evt) {
  var eventType = evt.type;
  switch (eventType) {
    case "mouseover":
    if (oDIV.className == "DataItem") {
      oDIV.className = "DataItemHover";
    }
    break;
    case "mouseout":
    if (oDIV.className == "DataItemHover") {
      oDIV.className = "DataItem";
    }
    break;
    case "click":
    if (lastDataItem) {
      lastDataItem.className = "DataItem";
    }
    lastDataItem = oDIV;
    oDIV.className = "DataItemSelected";
    break;
    default:
    break;
  }
}
var lastTab = null;
function SwitchTab(oDIV, evt) {
  if (lastTab) {
    lastTab.className = "";
  }
  if (oDIV.innerHTML == "\u7ba1\u7406\u9996\u9875") {
    if (window.frames["content4"]) {
      window.frames["content4"].location = "userm_index.asp";
      if (lastTab && (lastTab.innerHTML != "\u7ba1\u7406\u9996\u9875")) {
        lastTab.innerHTML = "";
      }
    }
  }
  lastTab = oDIV;
  oDIV.className = "Selected";
}
function go_cmdurl(o) {
  //if (lastTab) {
  //  lastTab.className = "";
  //}
  window.parent.frames["topframe"].document.getElementById("topnav").innerHTML = o.innerHTML;
  //document.getElementById("CurrentOp").innerHTML = t;
  //document.getElementById("CurrentOp").className = "Selected";
  //lastTab = document.getElementById("CurrentOp");

}
var req = null;
function GetContent(url) {
  req = CreateXMLHttpRequest();
  if (req != null) {
    req.open("GET", url, true);
    req.onreadystatechange = processReqChange;
    req.send(null);
  }
}
function processReqChange() {
  if (req.readyState == 4) {
    if (req.status == 200) {
      document.getElementById("sm").innerHTML = "(" + req.responseText + ")";
    }
  }
}
function CreateXMLHttpRequest() {
  var xhp = null;
  if (window.XMLHttpRequest) {
    xhp = new XMLHttpRequest();
    return xhp;
  } else {
    if (window.ActiveXObject) {
      clsid = ["Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.5.0", "Msxml2.XMLHTTP.4.0", "Msxml2.XMLHTTP.3.0", "Msxml2.XMLHTTP", "Microsoft.XMLHTTP"];
      for (i = 0; i < clsid.length; i++) {
        try {
          xhp = new ActiveXObject(clsid[i]);
          return xhp;
        }
        catch (e) {
          xhp = null;
        }
      }
    }
    return null;
  }
  return null;
}

