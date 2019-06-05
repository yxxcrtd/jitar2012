

/*=============================================================
  注意：在Mozilla中nextSibling/previousSibling包括了\n\r等信息，
    为了得到有效的节点，必须特殊处理

外部变量：
g_childLink 得到children HTML的地址（外部给出）

接口
getRootTreeNode()
getSelectedTreeNode()

// 寻找节点
function findTreeNode(innode, cid)

treeSync(cid)
treeSyncDown(cid)
// 得到 该分类的层次 
// 返回一个 cid-分类title 对的数组
treeNavarr

 TODO: change name tree

// 事件event 接口
OnExpand(id)

Private:
  g_selectedItem  当前选择节点 dom

=============================================================*/

/*
   类 EduTreeView
   
   事件：
   OnExpand(oNode)
   OnChecked(oNode)
*/


var TreeView = new EduTreeView();

function EduTreeView()
{
}

EduTreeView.prototype.OnExpand=function(oNode)
{
	oNode.setCheckedAll(oNode.getChecked());
}

EduTreeView.prototype.OnChecked=function(oNode)
{
	oNode.setCheckedAll(oNode.getChecked());
	if(!oNode.getChecked())
		oNode.setCheckedAllParent(false);
}


// 这个过程由 checkbox or radiobox 调用， 不能删除
function EduOnChecked(eve)
{
	if(TreeView.OnChecked != null)
	{
	  var ele; 
	  
	  if(!eve)
	    ele = window.event.srcElement;
	  else
	    ele = eve.target.parentNode;
	  var o = new treeNode(ele);
		TreeView.OnChecked(o);
	}
}

// 动态的在每个child前面添加竖线！
// 一次添加后，记录一个标志在属性chock中，以后不做添加
function makeChock(childContainer)
{
  if (childContainer.getAttribute('chock') != null) return;
  
  childContainer.setAttribute('chock', 1)

  var chockHtml=getChockHtml(childContainer)
  for( var k=0; k<childContainer.childNodes.length; k++)
  {
    // 耗时8600毫秒
    //if (childContainer.childNodes[k].getAttribute('folderId')!=null)
    //  childContainer.childNodes[k].innerHTML = chockHtml + childContainer.childNodes[k].innerHTML
    var p = childContainer.childNodes[k]
    //if ( p.getAttribute('folderId')!=null ) // 耗时7300毫秒
    if ( p.id != '__childFolder' )  // 耗时7300毫秒
    {
      p.innerHTML = chockHtml + p.innerHTML
    }
  }
  //window.status = '插入html used: ' + (new Date() - tm) + 'ms'
}

// 附注函数，生成cc的孩子的填充图形
// 空白用 <span style="width:19px;height:1px;"></span>
// Bug: IE 5中span会有一个像素的间隙，改用 <img class="line" id="__line" src="/groups/images/tree/b.gif" width="19px" height="1px" align="absbottom">

// 竖线用 <img class="line" id="__line" src="/groups/images/tree/i.gif" align="absbottom">
function getChockHtml(cc)
{
  var strHtml = '';
  var ele = cc;
  while( ele.id == '__childFolder')
  {
    // 对root的children不做处理
    // IE: if (ele.previousSibling.id == '__treeFolder_c0')  break;
    
    // Mozilla 特殊处理
    var preNode = safePrevSibling(ele);
    if (preNode == null)  break;
    
    if (preNode.id =='__treeFolder_c0') break;
    // Mozilla 特殊处理
    
    if (ele.nextSibling == null)
      //strHtml = '<span style="width:19px;height:1px;"></span>' + strHtml
      strHtml = '<img class="line" id="__line" src="/groups/images/tree/b.gif" width="19px" height="1px" align="absbottom">' + strHtml
    else
      strHtml = '<img class="line" id="__line" src="/groups/images/tree/i.gif" align="absbottom">' + strHtml

    ele = ele.parentNode;
  }
  return strHtml;
}

// 找到兄节点. 在mozlla 中 previousSibling 可能是TEXT 节点， 这样的节点没有 id , tagName属性
// todolater mozlla 的 TEXT 节点
function safePrevSibling(node)
{
  var nodeRet = node.previousSibling
  while(nodeRet!=null && (nodeRet.id==null) )
    nodeRet = nodeRet.previousSibling;
  return nodeRet
}

// 点击 +/- 号时候的处理
function cateCollapeClick(e)
{
  if (e==null)  return; 
  var v = new treeNode(e);
  if(v) v.expandCollape();
}

// 展开该分类的字分类，然后在right frame里面显示该页面！
// dbclick 不支持NetScape
function tree_dblclick()
{
  var ele;
  try{ ele= event.srcElement;} catch(e){return;}
  if (ele.className == 'item')
    new treeNode(ele).expandCollape();
}

var g_selectedItem = null;

// 树发生点击
function tree_click(eve)
{

  // IE, eve 为 null
  // Mozzlia eve 为 Event
  var ele; 
  
  if(!eve)
    ele = window.event.srcElement;
  else
    ele = eve.target.parentNode;
    
  if (ele.tagName != 'A')    return;
  new treeNode(ele).selected();
}

// 初始化树的各项数据，可以在页面的onload里调用
function treeInit()
{
  // 设置click 响应例程
  var cc;
  cc = document.getElementById('__treeOuter')
  cc.onclick = tree_click;
  
  // 设置根为当前节点  
  getRootTreeNode().selected();
}

// 提取数据
function treeRetrData(folderId,tagId)
{
    var strLoc = g_childLink +  folderId + "&__tagid__=" + tagId ;
    task_addTask(new taskStepLocation(strLoc));
}

// 子节点的HTML传来后，调用
function loadFrame(id, folderid)
{
    //id 父分类号
    var folder = document.getElementById(folderid);
    if (folder ==null)    return;
    
    var node = new treeNode(folder);
    var childContainer=node.getChildContainer();
    
    var strHtml = window.hiddenframe.document.getElementById('chunk').innerHTML;
    if( strHtml != "" )
    {
      // 数据正确获得
      childContainer.innerHTML = strHtml;
      node.setDataRetrieved("1");
    }
    else      // 数据取了，但是是空的
    { 
      childContainer.innerHTML = '';
      node.setDataRetrieved("2");
    }

    // 展开节点， 但下面这行不能少， 原因不明
    // todolater 将这行去掉
    node.setDisplay(false);
    node.expand();
  
  	// 发出事件通知数据来了
  	if( TreeView.OnExpand != null)
  		TreeView.OnExpand(node);
  	
    // 执行下一个任务
    task_nextTask();
}

// 树同步命令，选中该cid的节点
function treeSync(cid)
{
  var node = getSelectedTreeNode();
  // 一直向上寻找
  while(node.getFolderId() != cid)
  {
    node = node.getParent();
    if(!node) break;
  }
  if(!node)
  {
    // 尝试整个寻找
    node = findTreeNode(getRootTreeNode(), cid);
  }
  if(node) node.selected();
}

// 寻找节点
function findTreeNode(innode, cid)
{
  if(!innode) return null;
  if(innode.getFolderId() == cid) return innode;
  
  var child = innode.getChild();
  for(var i=0; i< child.length; ++i)
  {
    var node = findTreeNode(child[i], cid);
    if(node) return node;
  }
}

// 向下同步节点
function treeSyncDown(cid)
{
  // 展开当前节点
  getSelectedTreeNode().expand();

  // 从当前分类向下寻找分类 cid， 并展开它
  task_addTask(new taskStepTreeSyncDown(cid));
  
}

// 得到 该分类的层次 
// 返回一个 cid-分类title 对的数组
function treeNavarr()
{
  if( g_selectedItem )
  {
    var map = new Array();
    var nd = g_selectedItem;

    while( nd.tagName.toLowerCase() != 'div' )
    {
      nd = nd.parentNode;
    }
    
    var fldid;
    while(nd )
    {
      fldid = nd.getAttribute('folderId');
      if( fldid )
      {
        var arr = new Array(2);
        arr[0] = parseInt(fldid); // 分类ID
        // <nobr><><>...<><a href title></nobr>
        var ndNobr = nd.childNodes.item(0);
        while( ndNobr && 'NOBR' != ndNobr.tagName )
          ndNobr = ndNobr.nextSibling;
        if( ndNobr == null )
          {alert('nobr null');return;}

        // 2 find nobr 孩子， className 为 item 的项
        var vitem = ndNobr.childNodes.item(ndNobr.childNodes.length - 1);
        while( vitem && 'item' != vitem.className )
          vitem = vitem.previousSibling;
        
        if( vitem == null )
          {alert(ndNobr.outerHTML);return;}

        if( 'item' != vitem.className )
          return;
          
        arr[1] = vitem.getAttribute('title');  // 分类名称
        map = map.concat(arr);

        if( nd.id == '__treeFolder_c0' )
          return map;
      }
      // 一直向上寻找
      
      nd = nd.parentNode;
      if(nd.id == '__childFolder')
        nd = safePrevSibling(nd);
      
    }
  }
}

// Mozilla 替代 IE 的 all.namedItem 的函数
// all.namedItem: Retrieves an object or a collection from the specified collection
function all_namedItem(node, name)
{
  var arr = new Array();
  for(var k=0; k<node.childNodes.length; k++)
  {
    var cc=node.childNodes[k];
    if (cc.id == name)
      arr = arr.concat(cc)
    if (cc.hasChildNodes)
    {
      var v = all_namedItem(cc, name);
      if (v)
        arr = arr.concat( v );
    }
  }
  if (arr.length>1)
    return arr;
  else if(arr.length==1)
    return arr[0];
  else
    return null;
}


// Mozilla 替代 IE 的 all.tags 的函数
// all.tags: Retrieves  a collection from the specified collection
function all_tags(node, tagName)
{
  var arr = new Array();
  for(var k=0; k<node.childNodes.length; k++)
  {
    var cc=node.childNodes[k];
    if (cc.tagName == tagName)
      arr = arr.concat(cc);
    if (cc.tagName && cc.hasChildNodes)
    {
      var v = all_tags(cc, tagName);
      if (v.length>0)
        arr = arr.concat( v );
    }
  }
  return arr;
}

function __dumpAttributes(obj)
{
  var str = obj.id + ' attributes:\n'
  for(var k=0; k<obj.attributes.length; k++)
    str += obj.attributes[k].name + ' = ' + obj.attributes[k].value + '\n';

  alert(str)
}

// 任务，为了支持Mozilla
/* ========================================= */

// 任务步骤， 都有 run 方法, 任务结束后， 都有义务调用 task_nextTask()

  function taskStep()
  {
  }

  
  // 调入子分类的步骤对象
  function taskStepLocation(url)
  {
    this.url = url;
    this.run = taskStepLocationRun;
  }
  
  // 调入子分类的操作
  function taskStepLocationRun()
  {
    window.frames["hiddenframe"].location = this.url;
  }

  // 向下同步对象
  function taskStepTreeSyncDown(cid)
  {
    this.cid = cid;
    this.run = taskStepTreeSyncDownRun;
  }
  
  // 向下同步操作
  function taskStepTreeSyncDownRun()
  {
    // 是否是当前节点
    var node = getSelectedTreeNode();
    if(node.getFolderId() == this.cid){ node.selected(); return; }

    // 从当前节点向下寻找， 并选中它
    var child = node.getChild();
    for(var i=0; i< child.length; ++i)
    {
      node = child[i];
      if(node.getFolderId() == this.cid){ node.selected(); break; }
    }
    
    // 没有找到， 那么向上同步尝试寻找
    if(i == child.length)
      treeSync(this.cid);
    
    task_nextTask();  // 继续下步操作
  }

// 任务队列
var m_tasks = new Array();
function task_addTask(taskStep)
{
  m_tasks = m_tasks.concat(taskStep)

  if (m_tasks.length == 1)
    taskStep.run();
}

// 执行下一个任务
function task_nextTask()
{
  m_tasks=m_tasks.slice(1);
  if( m_tasks.length > 0)
  {
    var taskobject = m_tasks[0];
    taskobject.run();
  }
}


/* ======================================================
 树的节点类
 ====== 已实现  =========
       getDataRetrieved 数据是否装载标志 0,1,2,3
       setDataRetrieved 数据是否装载标志 0,1,2,3
       
       getId
       
       isExpanded       bool 是否展开
       expand           展开
       expandCollape    展开合起
       getFolderId      分类编号
       getParent        得到父节点
       Selected         
       isRootNode       bool
       getChild      得到子节点数组
       
       getChecked		是否选中
       setChecked		设置选中
       setCheckedAll	设置 children 全部
       setCheckedAllParent  设置 父 
       findFolder   (cid)
 Private:
       getChildContainer  得到子孙的容器
       setDisplay       bool 显示状态
       getDisplay       bool 
       setSelected      bool 选中
       
 ====== 未实现, 规划中  =========
 属性 id, folderid , title
 私有属性:  Dom (节点对应的 HTML Dom)
 方法： 展开， 折合， 交换
       getPresibling 得到兄节点
       getNextsibling 得到弟节点
 Private:
==========================================================
*/
// 构造函数
function treeNode(dom)
{
  this.dom = null;
  this.getDataRetrieved = getDataRetrieved;
  this.setDataRetrieved = setDataRetrieved;
  this.getId = fngetId;
  this.getChildContainer = getChildContainer;
  this.isExpanded = isExpanded;
  this.isRootNode = fnisRootNode;
  this.expand = expand;
  this.expandCollape = expandCollape;
  this.getFolderId = getFolderId;
  this.getParent = getParent;
  
  this.selected = fnSelected;
  this.setSelected = fnsetSelected;
  this.setDisplay = setDisplay;
  this.getDisplay = getDisplay;
  this.getChild = fngetChild;
  
  this.findFolder=findFolder;
  // 规范化参数:寻找 DIV
  var folder = dom;
  while(folder && folder.tagName != 'DIV' )
    folder = folder.parentNode;
  
  if(folder == null || folder.tagName != 'DIV') return;
  this.dom = folder;
 
}

// 寻找节点
function findFolder(cid)
{
  if(this.getFolderId() == cid) return this;
  
  var child = this.getChild();
  for(var i=0; i< child.length; ++i)
  {
    var node = child[i].findFolder(cid);
    if(node) return node;
  }
  return null;
}

// 是否选中
treeNode.prototype.getChecked=function()
{
  if(this.dom == null)   return null;

  var arrItems = all_tags(this.dom,'INPUT');
  if(arrItems.length ==0) 
  	return;
  return arrItems[0].checked;
	}

// 是否选中
treeNode.prototype.setChecked=function(checked)
{
  if(this.dom == null)   return null;

  var arrItems = all_tags(this.dom,'INPUT');
  if(arrItems.length ==0) 
  	return;
  arrItems[0].checked = checked;
	}

treeNode.prototype.setCheckedAll=function(checked)
{
	  this.setChecked(checked);
	  var child = this.getChild();
	  for(var i=0; i< child.length; ++i)
	    child[i].setCheckedAll(checked);
}

treeNode.prototype.setCheckedAllParent=function(checked)
{
  // 一直向上寻找
  var oParent = this.getParent();
  while(oParent)
  {
    oParent.setChecked(checked);
    oParent = oParent.getParent();
  }
}

//
function fngetId()
{
  if(this.dom ==null) return null;
  return this.dom.id;  
}

// 得到父节点
function getParent()
{
  if(this.dom ==null) return null;

  var nd = this.dom;
  nd = nd.parentNode;
  while(nd)
  {
    if(nd.id == '__treeOuter')
      return null;
      
    if(nd.id == '__childFolder')
    {
      nd = safePrevSibling(nd);
    }
    else
      nd = nd.parentNode;

    try
    {
      if(nd.getAttribute('folderId')) break;
    }
    catch(e)
    {
      alert(e);
    }
  }

  if(nd) return new treeNode(nd);
}

// 数据是否装载标志 0,1,2,3
function getDataRetrieved()
{
  if(this.dom ==null) return 0;
  return parseInt(this.getChildContainer().getAttribute('dataRetrieved'))
}

// 数据装载标志
function setDataRetrieved(v)
{
  if(this.dom ==null) return 0;
  this.getChildContainer().setAttribute('dataRetrieved',v);
}

// 获分类编号      
function getFolderId()
{
  if(this.dom ==null) return -1;
  return this.dom.getAttribute('folderId');
}

// 显示状态
function setDisplay(v)
{
  if(this.dom ==null) return 0;
  if(v)
    this.getChildContainer().style.display='';
  else
    this.getChildContainer().style.display='none';
}
function getDisplay()
{
  if(this.dom ==null) return 0;
  return this.getChildContainer().style.display=='';
}

// 是否展开
function isExpanded()
{
  return this.getDisplay();
}

//展开 Expand
function expand()
{
  if(this.dom ==null) return ;
  if(!this.isExpanded())
   this.expandCollape();
}

function expandCollape()
{
  if(this.dom ==null) return ;

  var folderId = this.getFolderId();    // 获得节点号
  var childContainer = this.getChildContainer();  // 得到子女节点容器
  var dr = this.getDataRetrieved();   // 数据是否装载标志
  
  if(this.isExpanded()==false)
  {
    // 检测是否数据已经提取了。
    if( '0' == dr )
    {
      // 没有提取，发送请求
	    treeRetrData(folderId, this.getId());
	  
	    // 显示正在调入信息，dataRetrieved = "3" 表示正在调入
      this.setDataRetrieved("3");
	    var strHtml='<div nowrap style="z-index:2;position:absolute;background-color:#d4facd;">' + getChockHtml(childContainer) + "<font color='darkblue'>正在调入信息.....</font>" + '</div>';
	    childContainer.innerHTML = strHtml;
      this.setDisplay(true);
    }
    else if( '1' == dr )  // 已经提取
    {
      makeChock(childContainer);
      this.setDisplay(true);
    }
  }
  else
    this.setDisplay(false);  // 该树可见，现在让它不可见

  // 设置图片
  if( dr == '1' )
  {
    var imgElem = all_namedItem(this.dom, '__cateCollape');
    if( imgElem == null ) return;
  
    var str;
    if( this.isExpanded())
      str = 'minus.gif'
    else
      str = 'plus.gif'
      
    if ( this.dom.nextSibling.nextSibling == null )
      str = '/groups/images/tree/l' + str
    else
      str = '/groups/images/tree/t' + str
    imgElem.src = str
  }
  else if(dr == '2')
  {
    var imgElem = all_namedItem(this.dom, '__cateCollape');
    if( imgElem == null ) return;
    
    if ( this.dom.nextSibling.nextSibling == null )
      imgElem.src = '/groups/images/tree/l.gif'
    else
      imgElem.src = '/groups/images/tree/t.gif'
  }
}

// 得到子孙的容器
function getChildContainer()
{
  if(this.dom ==null) return null;
  
  var childContainer =  this.dom.nextSibling;
  while (childContainer.id==null)
    childContainer = childContainer.nextSibling
  return childContainer;
}

function fnisRootNode()
{
  if(this.dom ==null) return false;
  return this.getId == '__treeFolder_c0';
}

// 将当前节点选中
function fnSelected()
{
  if(this.dom ==null) return ;
  getSelectedTreeNode().setSelected(false);
  this.setSelected(true);
}

//
function fnsetSelected(v)
{
  if(this.dom ==null) return ;

  // 每个节点标题 是由<A> 或 <SPAN>包起来的
  // <DIV><NOBR>....<A>...</NOBR></DIV>
  var arrItems = all_tags(this.dom,'A');
  if(arrItems.length ==0)
    var arrItems = all_tags(this.dom,'SPAN');
  if(arrItems.length ==0) return;

  var selItem = arrItems[0];
  
  if(v)
  {
    // 反白显示当前节点
    g_selectedItem = selItem;
    selItem.style.background = 'royalblue';
    selItem.style.color = 'white';
  }
  else
  {
    // 正常显示
    selItem.style.background = 'white';
    selItem.style.color = '';
  }
}

// 获得子节点
function fngetChild()
{
  var arr = new Array();
  if(this.dom == null) return arr;

  var childContainer = this.getChildContainer();
  for(var i=0; i<childContainer.childNodes.length; ++i)
  {
    var node = childContainer.childNodes.item(i);
    if(node.id && node.getAttribute('folderId'))
      arr = arr.concat(new treeNode(node));
  }
  return arr;  
}

//==============================
//  end treeNode 类结束
//==============================================================//

// 得到当前节点
function getSelectedTreeNode()
{
  return new treeNode(g_selectedItem);
}

// 获得根节点
function getRootTreeNode()
{
  var item = document.getElementById('__treeFolder_c0');
  return new treeNode(item);
}


/* ========================================= */

