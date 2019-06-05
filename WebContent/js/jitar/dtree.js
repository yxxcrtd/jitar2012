// Node object
var JITAR_ROOT = window.JITAR_ROOT || '';
function Node(id, pid, name, url, title, target, icon, iconOpen, open) {
  this.id = id;                   /* 本节点ID */
  this.pid = pid;                 /* 父节点ID */
  this.name = name;               /* 本节点名称 */
  this.url = url;                 /* 本节点的URL */
  this.title = title;             /* 鼠标悬浮于节点上的提示信息 */
  this.target = target;           /* 在哪个框架上打开连接 */
  this.icon = icon;               /* 节点图标 */
  this.iconOpen = iconOpen;       /* 节点展开后的图标 */
  this._io = open || false;       /* 节点是否已打开的标记 */
  this._is = false;               /* is = is selected, 标记该节点是否是被选中的节点 */
  this._ls = false;               /* ls = last slibling, 是否最后一个节点 */
  this._hc = false;               /* hc = has children, 标记是否具有子节点 */
  this._ai = 0;                   /* ai = array index, 节点在数组中的下标 */
  this._p;                        /* 节点的父节点 */
};

function newNode(id, pid, name, url, title, target, icon, iconOpen, open, ai,hc) {
  this.id = id;				/* 本节点ID */
  this.pid = pid;			/* 父节点ID */
  this.name = name;			/* 本节点名称 */
  this.url = url;			/* 本节点的URL */
  this.title = title;		/* 鼠标悬浮于节点上的提示信息 */
  this.target = target;		/* 在哪个框架上打开连接 */
  this.icon = icon;			/* 节点图标 */
  this.iconOpen = iconOpen;	 /* 节点展开后的图标 */
  this._io = open;			 /* 节点是否已打开的标记 */
  this._is = false;			/* is = is selected, 标记该节点是否是被选中的节点 */
  this._ls = false;			/* ls = last slibling, 是否最后一个节点 */
  this._hc = hc;			/* hc = has children, 标记是否具有子节点 */
  this._ai = ai;			/* ai = array index, 节点在数组中的下标 */ 
  this._p;					/* 节点的父节点 */
};


// Tree object

function dTree(objName) {

  this.config = {
    target          : null,
    folderLinks     : true,		/* 文件夹图表上是否具有连接 */
    useSelection    : true,		 /* 选中部分是否高亮显示 */
    useCookies      : true,		/* 是否使用cookie */
    useLines        : true,		/* 显示连接线 */
    useIcons        : true,		/* 显示图标 */
    useStatusText   : false,	/* 在状态栏显示提示信息 */
    closeSameLevel  : false,	/* 关闭同级其他节点 */
    inOrder         : false
  }


  this.icon = {			 /* 指定各个图标 */
    root        : JITAR_ROOT + 'images/dtree/base.gif',		/* 根节点图标 */
    folder      : JITAR_ROOT + 'images/dtree/folder.gif',		/* 未展开节点图标 */
    folderOpen  : JITAR_ROOT + 'images/dtree/folderopen.gif',	 /* 展开后节点图标 */
    node        : JITAR_ROOT + 'images/dtree/page.gif',			/* 叶子节点(无节点的节点)图标 */
    empty       : JITAR_ROOT + 'images/dtree/empty.gif',		 /* 空白图标 */
    line        : JITAR_ROOT + 'images/dtree/line.gif',		  /* 竖向连接线图标 */
    join        : JITAR_ROOT + 'images/dtree/join.gif',			/* 兼具水平、竖向连接线的图标 */
    joinBottom  : JITAR_ROOT + 'images/dtree/joinbottom.gif',		 /* 底端连接线图标 */
    plus        : JITAR_ROOT + 'images/dtree/plus.gif',			 /* 带连接线的+图标 */
    plusBottom  : JITAR_ROOT + 'images/dtree/plusbottom.gif',	/* 带连接线的底端+图标 */
    minus       : JITAR_ROOT + 'images/dtree/minus.gif',			 /* 带连接线的-图标 */
    minusBottom : JITAR_ROOT + 'images/dtree/minusbottom.gif',		/* 带连接线的底端-图标 */
    nlPlus      : JITAR_ROOT + 'images/dtree/nolines_plus.gif',		/* 不带连接线的+图标 */
    nlMinus     : JITAR_ROOT + 'images/dtree/nolines_minus.gif'		/* 不带连接线的-图标 */
  };

  this.obj = objName;		/* 树对象名称 */
  this.aNodes = [];			 /* 节点数组 */
  this.aIndent = [];		/* 缩进数组 */
  this.root = new Node(-1);	 /* 根节点 */
  this.selectedNode = null;	 /* 选定节点 */
  this.selectedFound = false;	 /* 是否已经选定节点 */
  this.completed = false;	 /* 结束标志 */
};



// Adds a new node to the node array
dTree.prototype.add = function(id, pid, name, url, title, target, icon, iconOpen, open) {
  this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, title, target, icon, iconOpen, open);
};

// Adds a new node to the node array
dTree.prototype.addNew = function(id, pid, name, url, title, target, icon, iconOpen, open,i,hc) {
  this.aNodes[this.aNodes.length] = new newNode(id, pid, name, url, title, target, icon, iconOpen, open,i,hc);
};


// Open/close all nodes
dTree.prototype.openAll = function() {
  this.oAll(true);
};

dTree.prototype.closeAll = function() {
  this.oAll(false);
};



// Outputs the tree to the page
dTree.prototype.toString = function() {
  var str = '<div class="dtree">\n';
  if (document.getElementById) {
    if (this.config.useCookies) this.selectedNode = this.getSelected();
    str += this.addNode(this.root);
  } else str += '浏览器不支持此种类型的树';
  str += '</div>';

  if (!this.selectedFound) this.selectedNode = null;
  this.completed = true;
  return str;
};



// Creates the tree structure
dTree.prototype.addNode = function(pNode) {
  var str = '';
  var n=0;
  //alert("this.config.inOrder="+this.config.inOrder+" pNode="+pNode);
  if (this.config.inOrder) n = pNode._ai;
  for (n; n<this.aNodes.length; n++) {
    if (this.aNodes[n].pid == pNode.id) {
      var cn = this.aNodes[n];
      cn._p = pNode;
      cn._ai = n;
      this.setCS(cn);
      if (!cn.target && this.config.target) cn.target = this.config.target;
      if (cn._hc && !cn._io && this.config.useCookies)
      { 
      	cn._io = this.isOpen(cn.id);
      }
      if (!this.config.folderLinks && cn._hc) cn.url = null;
      if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
          cn._is = true;
          this.selectedNode = n;
          this.selectedFound = true;
      }
	  //alert("addNode:"+n);	
      str += this.node(cn, n);
      if (cn._ls) break;
    }
  }
  return str;
};



// Creates the node icon, url and text
dTree.prototype.node = function(node, nodeId) {
  var str = '<div class="dTreeNode">' + this.indent(node, nodeId);
  if (this.config.useIcons) {
    if (!node.icon) node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);
    if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
    if (this.root.id == node.pid) {
      node.icon = this.icon.root;
      node.iconOpen = this.icon.root;
    }
    str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
  }

  if (node.url) {
    str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';
    if (node.title) str += ' title="' + node.title + '"';
    if (node.target) str += ' target="' + node.target + '"';
    if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
    if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))
      str += ' onclick="javascript:' + this.obj + '.s(' + nodeId + ');if(window.nodeOnClick){nodeOnClick(event,\'' + node.id + '\',\'' + node.name + '\')};"';
      
    str += '>';
  }
  else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)
  {
    str += '<a id="s' + this.obj + nodeId + '" href="#" onclick="javascript:' + this.obj + '.o(' + nodeId + ');' + this.obj + '.s(' + nodeId + ');if(window.nodeOnClick){ nodeOnClick(event,\'' +  node.id + '\',\'' + node.name + '\'});return false;" class="node">';
  }
  else
  {
    str += '<a id="s' + this.obj + nodeId + '" href="#" onclick="javascript:' + this.obj + '.s(' + nodeId + ');if(window.nodeOnClick){nodeOnClick(event,\'' +  node.id + '\',\'' + node.name + '\')};return false;" class="node">';
  }
  str += node.name;
  //if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';
  str += '</a>';

  str += '</div>';
  if (node._hc) {
    str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
    str += this.addNode(node);
    str += '</div>';
  }

  this.aIndent.pop();
  return str;
};



// Adds the empty and line icons
dTree.prototype.indent = function(node, nodeId) {
  var str = '';
  if (this.root.id != node.pid) {
    for (var n=0; n<this.aIndent.length; n++)
    {
      str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';
    }
    (node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
    if (node._hc) {
      str += '<a href="#" onclick="' + this.obj + '.o(' + nodeId + ');return false;"><img id="j' + this.obj + nodeId + '" src="';

      if (!this.config.useLines)
      {
         str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
      }
      else
      {
         str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
      }
      str += '" alt="" /></a>';
    } 
    else
    {
       str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';
    }
  }
  return str;
};


// Checks if a node has any children and if it is the last sibling
dTree.prototype.setCS = function(node) {
  var lastId;
  node._hc=false;
  for (var n=0; n<this.aNodes.length; n++) {
    //alert("this.aNodes["+n+"].pid=" + this.aNodes[n].pid + " node.id="+node.id); 
    if (""+this.aNodes[n].pid == ""+node.id) node._hc = true;
    if (""+this.aNodes[n].pid == ""+node.pid) lastId = this.aNodes[n].id;
  }
  //alert("node._hc="+node._hc);
  if (lastId==node.id) node._ls = true;
};

// Returns the selected node
dTree.prototype.getSelected = function() {
  var sn = this.getCookie('cs' + this.obj);
  return (sn) ? sn : null;
};


// Highlights the selected node
dTree.prototype.s = function(id) {
  //alert(id);
  if (!this.config.useSelection) return;
  var cn = this.aNodes[id];
  if (cn._hc && !this.config.folderLinks) return;
  if (this.selectedNode != id) {
    if (this.selectedNode || this.selectedNode==0) {
      eOld = document.getElementById("s" + this.obj + this.selectedNode);
      if(eOld) eOld.className = "node";
    }
    eNew = document.getElementById("s" + this.obj + id);
    eNew.className = "nodeSel";
    this.selectedNode = id;
    if (this.config.useCookies) this.setCookie('cs' + this.obj, cn.id);
  }
};



// Toggle Open or close
dTree.prototype.o = function(id) {
  //alert("o="+id);
  var cn = this.aNodes[id];
  this.nodeStatus(!cn._io, id, cn._ls);
  cn._io = !cn._io;
  if (this.config.closeSameLevel) this.closeLevel(cn);
  if (this.config.useCookies) this.updateCookie();
};

// Open or close all nodes
dTree.prototype.oAll = function(status) {
  for (var n=0; n<this.aNodes.length; n++) {
    if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
      this.nodeStatus(status, n, this.aNodes[n]._ls)
      this.aNodes[n]._io = status;
    }
  }
  if (this.config.useCookies) this.updateCookie();
};


// Opens the tree to a specific node
dTree.prototype.openTo = function(nId, bSelect, bFirst) {
  if (!bFirst) {
    for (var n=0; n<this.aNodes.length; n++) {
      if (this.aNodes[n].id == nId) {
        nId=n;
        break;
      }
    }
  }
  var cn=this.aNodes[nId];
  if (cn.pid==this.root.id || !cn._p) return;
  cn._io = true;
  cn._is = bSelect;
  if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);
  if (this.completed && bSelect) this.s(cn._ai);
  else if (bSelect) this._sn=cn._ai;
  this.openTo(cn._p._ai, false, true);
};



// Closes all nodes on the same level as certain node
dTree.prototype.closeLevel = function(node) {
  for (var n=0; n<this.aNodes.length; n++) {
    if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {
      this.nodeStatus(false, n, this.aNodes[n]._ls);
      this.aNodes[n]._io = false;
      this.closeAllChildren(this.aNodes[n]);
    }
  }
}


// Closes all children of a node

dTree.prototype.closeAllChildren = function(node) {
  for (var n=0; n<this.aNodes.length; n++) {
    if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {
      if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
      this.aNodes[n]._io = false;
      this.closeAllChildren(this.aNodes[n]);    
    }
  }
}


// Change the status of a node(open or closed)
dTree.prototype.nodeStatus = function(status, id, bottom) {
  eDiv  = document.getElementById('d' + this.obj + id);
  eJoin = document.getElementById('j' + this.obj + id);
  if (this.config.useIcons) {
    eIcon = document.getElementById('i' + this.obj + id);
    eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
  }

  eJoin.src = (this.config.useLines)?
  ((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
  ((status)?this.icon.nlMinus:this.icon.nlPlus);
  
  eDiv.style.display = (status) ? 'block': 'none';

};

// [Cookie] Clears a cookie

dTree.prototype.clearCookie = function() {
  var now = new Date();
  var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
  this.setCookie('co'+this.obj, 'cookieValue', yesterday);
  this.setCookie('cs'+this.obj, 'cookieValue', yesterday);
};



// [Cookie] Sets value in a cookie

dTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
  document.cookie =
    escape(cookieName) + '=' + escape(cookieValue)
    + (expires ? '; expires=' + expires.toGMTString() : '')
    + (path ? '; path=' + path : '')
    + (domain ? '; domain=' + domain : '')
    + (secure ? '; secure' : '');
};



// [Cookie] Gets a value from a cookie

dTree.prototype.getCookie = function(cookieName) {
  var cookieValue = '';
  var posName = document.cookie.indexOf(escape(cookieName) + '=');
  if (posName != -1) {
    var posValue = posName + (escape(cookieName) + '=').length;
    var endPos = document.cookie.indexOf(';', posValue);
    if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));
    else cookieValue = unescape(document.cookie.substring(posValue));
  }
  return (cookieValue);
};



// [Cookie] Returns ids of open nodes as a string
dTree.prototype.updateCookie = function() {
  var str = '';
  for (var n=0; n<this.aNodes.length; n++) {
    if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
      if (str) str += '.';
      str += this.aNodes[n].id;
    }
  }
  this.setCookie('co' + this.obj, str);
};


// [Cookie] Checks if a node id is in a cookie
dTree.prototype.isOpen = function(id) {
  var aOpen = this.getCookie('co' + this.obj).split('.');
  for (var n=0; n<aOpen.length; n++)
    if (aOpen[n] == id) return true;
  return false;
};

// If Push and pop is not implemented by the browser
if (!Array.prototype.push) {
  Array.prototype.push = function array_push() {
    for(var i=0;i<arguments.length;i++)
      this[this.length]=arguments[i];
    return this.length;
  }
};

if (!Array.prototype.pop) {
  Array.prototype.pop = function array_pop() {
    lastElement = this[this.length-1];
    this.length = Math.max(this.length-1,0);
    return lastElement;
  }
};

