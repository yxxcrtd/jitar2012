//错误提示定义，通过 com.chinaedustar.groups.commons.CommonUtil类生成
TITLE_IS_BLANK = "标题不能为空字符串";
TITLE_IS_TOO_LONG = "标题太长";
TITLE_IS_LONGER_THAN_255 = "标题长度大于255";
CONTENT_IS_BLANK = "内容不能为空";
ABSTRACT_IS_BLANK = "摘要不能为空";


if (window.Node) { // 修正Node的DOM
  Node.prototype.replaceNode = function (Node) { // 替换指定节点
    this.parentNode.replaceChild(Node, this);
  };
  Node.prototype.removeNode = function (removeChildren) { // 删除指定节点
    if (removeChildren) {
      return this.parentNode.removeChild(this);
    } else {
      var range = document.createRange();
      range.selectNodeContents(this);
      return this.parentNode.replaceChild(range.extractContents(), this);
    }
  };
  Node.prototype.swapNode = function (Node) { // 交换节点
    var nextSibling = this.nextSibling;
    var parentNode = this.parentNode;
    node = Node;
    node.parentNode.replaceChild(this, Node);
    parentNode.insertBefore(node, nextSibling);
  };
}

function $(id) {
  return document.getElementById(id);
}

function on_mouse_over(evt)
{  
  var sourceElement = window.event?window.event.srcElement:evt.target
  if(sourceElement == null) return;
  var trELement = GetElementFromChild(sourceElement,"TR")
  if(trELement)
  {    
    trELement.setAttribute("oldClassName",trELement.className)
    trELement.className = "mouseHightlight";
  }  
}
function on_mouse_out(evt)
{  
  var sourceElement = window.event?window.event.srcElement:evt.target
  if(sourceElement == null) return;
  var trELement = GetElementFromChild(sourceElement,"TR")
  if(trELement)
  {    
    if(trELement.getAttribute("oldClassName"))
    {
      trELement.className = trELement.getAttribute("oldClassName")
    }
  }
}

function checkAll(evt, elementName) {
  //var srcObject = window.event?window.event.srcElement:evt.target
  var srcObject = evt;
  var checkBoxs = document.getElementsByName(elementName);
  for (i = 0; i < checkBoxs.length; i++) {
    checkBoxs[i].checked = srcObject.checked;
    var rowObject = checkBoxs[i];
    rowObject = GetElementFromChild(checkBoxs[i], "TR");
    if (rowObject) {
      rowObject.className = srcObject.checked ? "backHightColor" : "backNormalColor";
    }
  }
}
function checkSelf(evt) {
  var srcObject = evt;
  var rowObject = srcObject;
  rowObject = GetElementFromChild(evt, "TR");
  if (rowObject) {
    rowObject.className = srcObject.checked ? "backHightColor" : "backNormalColor";    
    rowObject.setAttribute("oldClassName",(srcObject.checked ? "backHightColor" : "backNormalColor"))
  }
}
function GetElementFromChild(childObject, targetElementTagName) {
  var _obj = childObject;
  while (!(_obj.nodeType == 1 && _obj.tagName == (targetElementTagName.toUpperCase()))) {
    _obj = _obj.parentNode;
    if(_obj == null) 
    {
      break;
      return null;
    }
  }
  if(_obj == null) 
  {
      return null;
   }
  if (_obj.nodeType != 1) {
    return null;
  }
  if (_obj.tagName != targetElementTagName) {
    return null;
  }
  return _obj;
}

//交换节点
var lastTr = null;
function SwapTrNode(oTr) {
  oTr = oTr.parentNode;
  while (oTr.tagName != "TR") {
    oTr = oTr.parentNode;
  }
  if (lastTr == null) {
    lastTr = oTr;
    oTr.cells[1].className = "mouseClickColor";
  } else {
    oTr.cells[1].className = null
    lastTr.cells[1].className = null
    if(oTr.getAttribute("oldClassName"))
    {
      oTr.className = oTr.getAttribute("oldClassName")
    }
    if(lastTr.getAttribute("oldClassName"))
    {
      lastTr.className = lastTr.getAttribute("oldClassName")
    }
    oTr.swapNode(lastTr);
    lastTr = null;
  }
}

