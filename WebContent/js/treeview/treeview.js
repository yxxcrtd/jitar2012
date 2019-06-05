/*****************************************************************************************************************
 * 2013-03-19：通用树结构。
 * 
 * 共用代码，如在使用过程中有何问题或者有什么新需求，请与作者联系。
 * 
 * 对于高版本浏览器，本代码不依赖任何js框架，对于低版本浏览器，需要引用JSON对象定义。
 * json2.js(https://github.com/douglascrockford/JSON-js/blob/master/json2.js)
 * 或者
 * jquery.js(http://jquery.com/download/)
 * 
 * 通用动态加载的树结构，兼容IE6+、Chrome、Firefox、Opera等主流浏览器。
 * 
 * 说明：这个是动态加载的树对象。 
 * 实例化参数说明：
 * rootId：    代表要显示的树的根节点，为了防止Id传冲突和规范的标识，一般采用字母+数字的形式，数字就是数据库中根节点的Id值；
 * dataUrl：   用来动态加载数据的Url，提起数据时，总是以pid=xx的方式把当前NodeId传递到服务器端，作为父节点的id；
 * iconPath：   显示树结构使用的必要的图片虚拟目录地址； 
 * targetName：点击树链接的目标窗口名称； 
 * 
 * 属性说明：
 * AutoExpandPath：自动进行展开的路径，需要按照从根Id到展开Id的顺序依次书写。
 * 
 * 使用方法： 
 * <div id="A10" class="treeview"><img src="base.gif"/><a href="platform_unit_edit.py?unitId=10">根节点</a></div> 
 * 需要的脚本： 
 * var t1 = new TreeView("A10","get_unit_data.py","/jitarbase/js/treeview/", "");
 * t1.AutoExpandPath = "/10/14/15/"; //如果不需要自动展开，可以不写此行 
 * t1.Init(); //展开第一级
 * 
 * 服务器端返回的数据格式必须为JSON，字段信息为： 
 * [
 * {"NodeId":"14", "ChildCount":"1", "LinkHref":"后台返回的链接地址，自己控制", "LinkText":"后台返回的链接的文字信息。"},
 * {"NodeId":"18", "ChildCount":"0", "LinkHref":"platform_unit_edit.py?unitId=18", "LinkText":"<b>root<\/b>"} 
 * ]
 * 
 * 返回数据的JSON的Key名称不能更改。
 * 
 * 返回数据格式说明：
 * NodeId：所返回的节点Id值；
 * ChildCount：是否有子节点的标记，非0代表有子节点；
 * LinkHref：树链接的url地址；
 * LinkText：链接显示的文字，
 * LinkClick：可选，链接的onclick点击事件处理器。
 * 
 * 注意：new TreeView("A10") 和 <div id="A10">中的id要一致，并且是根节点的数据库Id，A前缀可以任意起名字，防止页面中id冲突的作用。
 * 
 ****************************************************************************************************************/
window.$id = function(id) {
    return typeof id == "string" ? document.getElementById(id) : id;
};
window.TreeView = function(rootId, dataUrl, iconPath, targetName) {
    this.Id = rootId;
    this.DataUrl = dataUrl; // 获取数据使的url地址。
    this.IconPath = iconPath; // 树图标所在的文件夹位置。
    this.Target = targetName; // 点击链接的目标窗口名称
    var tempId = rootId; // 需要分解出Id的前缀和后面的数字，后面的数字真正代表数据库中的id值
    var tempNodeId = tempId.match(/\d+/);
    var tempPrefix = tempId.replace(tempNodeId, "");
    this.NodeId = tempNodeId; // 当前处理的节点的ID
    this.AutoExpandPath = null; // 支持自动展开的各个节点路径；
    this.IdPrefix = tempPrefix; // 节点的前缀，是为了防止一个页面多个树导致id冲突。
    this.XmlHttp = null; // 当前实例的XmlHttp对象

    // 初始化加载，也就是用来加载根结构下的第一级数据
    this.Init = function() {
        if (this.DataUrl.indexOf("?") > -1) {
            this.DataUrl += "&";
        }
        else {
            this.DataUrl += "?";
        }
        // 创建一个根节点
        var d = $id(this.id);
        if (!d) {
            d = document.createElement("div");
            d.id = this.Id;
        }

        // 加载根节点数据
        this.XmlHttp = this.CreateXMLHttpRequest();
        if (this.XmlHttp == null) {
            alert("您的浏览器不支持 XMLHttpRequest ,请更改浏览器设置或者更换一款浏览器进行浏览。");
            return;
        }
        // 根节点数据需要存在，才能加载下一级节点。
        this.FetchData(this.NodeId);
    };

    this.toString = function() {
        alert("这是一棵树：" + this.Id);
    };

    // 参数应该可以使用 this.NodeId替换
    this.FetchData = function(nid) {
        var selfInstance = this;
        this.XmlHttp.open("GET", this.DataUrl + "pid=" + nid + "&tmp=" + (new Date()).getTime(), false);
        this.XmlHttp.onreadystatechange = function() {
            selfInstance.ReadyStateChangeCallback();
        };
        this.XmlHttp.send(null);
    };

    this.ReadyStateChangeCallback = function() {
        if (this.XmlHttp.readyState == 4) {
            if (this.XmlHttp.status == 200) {
                var responseData = this.XmlHttp.responseText;
                if (responseData == "") {
                    alert("没有加载到数据。");
                    return;
                }
                if (!(typeof console === "undefined" || typeof console.log === "undefined")) {
                    console.info(responseData);
                }

                var nodeLevel = $id(this.IdPrefix + this.NodeId).getAttribute("nodeLevel");
                if (nodeLevel == null) nodeLevel = "";
                var nodeLeftHtml = this.GetLeftHtml(nodeLevel);

                var TreeNodes;
                try {
                    if (window.JSON) {
                        TreeNodes = JSON.parse(responseData);
                    }
                    else {
                        if (window.jQuery) {
                            TreeNodes = jQuery.parseJSON(responseData);
                        }
                        else {
                            if ($id("_ProcessTip")){
                              $id("_ProcessTip").innerHTML = nodeLeftHtml + " <span style='color:red;background:#00ff00'>没有JSON解析器，请使用高版本浏览器或者通知程序开发商。</font>";
                            }
                            alert("没有JSON解析器，请使用高版本浏览器或者通知程序开发商。");
                            return;
                        }
                    }
                }
                catch (ex) {
                    if ($id("_ProcessTip")){
                      $id("_ProcessTip").innerHTML = nodeLeftHtml + " <span style='color:red;background:#00ff00'>加载数据失败，返回的数据格式不正确。</font>";
                    }
                    alert("无法加载返回的数据，可能格式不正确。");
                    return;
                }

                var NodeHtml = [];
                var containerDiv = document.createElement("div");
                containerDiv.id = this.IdPrefix + "container_" + this.NodeId;
                for (var i = 0; i < TreeNodes.length; i++) {
                    var TreeNode = TreeNodes[i];
                    var img1 = document.createElement("img");
                    var img2 = document.createElement("img");
                    var link = document.createElement("a");
                    link.href = TreeNode.LinkHref;
                    link.innerHTML = TreeNode.LinkText;
                    if (this.Target && this.Target != "") {
                        link.target = this.Target;
                    }
                    if (TreeNode.hasOwnProperty("LinkTitle")) {
                        link.setAttribute("title", TreeNode.LinkTitle);
                    }
                    if (TreeNode.hasOwnProperty("LinkClick")) {
                        link.onclick = new Function(TreeNode.LinkClick);
                    }
                    var nodeDiv = document.createElement("div");
                    nodeDiv.id = this.IdPrefix + TreeNode.NodeId;
                    nodeDiv.setAttribute("pid", this.NodeId);
                    nodeDiv.setAttribute("loaded", "0");
                    nodeDiv.setAttribute("expended", "0");
                    nodeDiv.setAttribute("display", "");
                    if (i == TreeNodes.length - 1) /* 最后一个节点 */
                    {
                        A = nodeLevel + "1";
                        if (TreeNode.ChildCount != "0") {
                            img1.src = this.IconPath + 'ftv2plastnode.gif';
                            img1.onclick = this.Collapse; // 绑定事件。这里如果使用addEnentListener方法可能有问题；
                            img1.onload = this.AutoExpand; // 绑定事件。这里如果使用addEnentListener方法可能有问题；
                            img1.tv = this; // 将当前的TreeView实例绑定到点击的对象上，以便得到该对象的实例
                        }
                        else {
                            img1.src = this.IconPath + 'ftv2lastnode.gif';
                        }
                    }
                    else {
                        A = nodeLevel + "0";
                        if (TreeNode.ChildCount != "0") {
                            img1.src = this.IconPath + 'ftv2pnode.gif';
                            img1.onclick = this.Collapse;
                            img1.onload = this.AutoExpand;
                            img1.tv = this;
                        }
                        else {
                            img1.src = this.IconPath + 'ftv2node.gif';
                        }
                    }
                    nodeDiv.innerHTML = nodeLeftHtml;
                    img2.src = this.IconPath + 'folder.gif';
                    nodeDiv.appendChild(img1);
                    nodeDiv.appendChild(img2);
                    nodeDiv.appendChild(link);
                    nodeDiv.setAttribute("nodeLevel", A);
                    nodeDiv.setAttribute("style", "white-space:nowrap;word-break:keep-all;");
                    nodeDiv.setAttribute("nowrap", "nowrap");
                    nobr = document.createElement("nobr");
                    nobr.appendChild(nodeDiv);
                    containerDiv.appendChild(nobr);
                }

                $id(this.IdPrefix + this.NodeId).appendChild(containerDiv);
                $id(this.IdPrefix + this.NodeId).setAttribute("loaded", "1");
                $id(this.IdPrefix + this.NodeId).setAttribute("expended", "1");
                if ($id("_ProcessTip") && $id("_ProcessTip").parentNode) {
                    $id("_ProcessTip").parentNode.removeChild($id("_ProcessTip"));
                }
            }
            else{
              alert("服务器返回错误：\r\n" + this.XmlHttp.statusText);
              }
        }
    };

    this.AutoExpand = function(evt) {
        var eleImg = window.event ? window.event.srcElement : evt.target;
        var _tv = this.tv;
        if (_tv.AutoExpandPath == null) return;
        var _id = eleImg.parentNode.getAttribute("id");
        if (_id == null) return;
        _id = _id.replace(_tv.IdPrefix, "");
        if (_tv.AutoExpandPath.indexOf("/" + _id + "/") > -1) {
            eleImg.click();
            // 去掉事件，防止刷新图标时再次执行这个过程；
            eleImg.onload = null;
        }
    };

    this.Collapse = function(evt) {
        var eleImg = window.event ? window.event.srcElement : evt.target;
        if ($id("_ProcessTip")) return; // 另外的节点还没有处理完成，不能再进行处理别的
        var _tv = this.tv;
        var ParentDiv = eleImg.parentNode; // 得到容器的DIV对象
        NodeId = ParentDiv.getAttribute("id").substr(_tv.IdPrefix.length); // 分解成id值
        var expended = ParentDiv.getAttribute("expended"); // 得到改节点是否展开，
        var loaded = ParentDiv.getAttribute("loaded"); // 检查数据是否是第一次加载，数据加载过的就不进行加载了，
        var clickedImageSrc = eleImg.src; // 点击图片的地址
        var clickedImageNextSiblingSrc = eleImg.nextSibling.src; // 点击图片右边图片的地址
        // 加载子节点

        _tv.NodeId = NodeId;
        if (loaded == "0") {
            _tv.ShowProcess(ParentDiv);
            window.setTimeout(function() {
                _tv.FetchData(NodeId);
            }, 1);
            clickedImageSrc = clickedImageSrc.replace("pnode.gif", "mnode.gif");
            clickedImageSrc = clickedImageSrc.replace("ftv2plastnode.gif", "ftv2mlastnode.gif");
            eleImg.src = clickedImageSrc;
            clickedImageNextSiblingSrc = clickedImageNextSiblingSrc.replace("folder.gif", "base.gif");
            eleImg.nextSibling.src = clickedImageNextSiblingSrc;
            return;
        }
        else {
            if (expended == "1") {
                ParentDiv.setAttribute("expended", "0");
                clickedImageSrc = clickedImageSrc.replace("ftv2mnode.gif", "ftv2pnode.gif");
                clickedImageSrc = clickedImageSrc.replace("ftv2mlastnode.gif", "ftv2plastnode.gif");
                eleImg.src = clickedImageSrc;
                clickedImageNextSiblingSrc = clickedImageNextSiblingSrc.replace("base.gif", "folder.gif");
                eleImg.nextSibling.src = clickedImageNextSiblingSrc;
                // 隐藏
                $id(_tv.IdPrefix + "container_" + ParentDiv.getAttribute("id").substr(_tv.IdPrefix.length)).style.display = "none";
            }
            else {
                ParentDiv.setAttribute("expended", "1");
                clickedImageSrc = clickedImageSrc.replace("ftv2pnode.gif", "ftv2mnode.gif");
                clickedImageSrc = clickedImageSrc.replace("ftv2plastnode.gif", "ftv2mlastnode.gif");
                eleImg.src = clickedImageSrc;
                clickedImageNextSiblingSrc = clickedImageNextSiblingSrc.replace("folder.gif", "base.gif");
                eleImg.nextSibling.src = clickedImageNextSiblingSrc;
                // 显示
                $id(_tv.IdPrefix + "container_" + ParentDiv.getAttribute("id").substr(_tv.IdPrefix.length)).style.display = "";
            }
        }
    };

    this.ShowProcess = function() {
        if ($id("_ProcessTip")) {
            $id("_ProcessTip").parentNode.removeChild($id("_ProcessTip"));
        }
        var nodeLevel = $id(this.IdPrefix + this.NodeId).getAttribute("nodeLevel");
        if (nodeLevel == null) nodeLevel = "";
        var tip = document.createElement("div");
        tip.innerHTML = this.GetLeftHtml(nodeLevel) + " <span style='color:red;background:#00ff00'>正在加载数据，请稍候……</font>";
        tip.setAttribute("id", "_ProcessTip");
        var previousSiblingDiv = $id(this.IdPrefix + this.NodeId);
        previousSiblingDiv.parentNode.insertBefore(tip, previousSiblingDiv.nextSibling);
    };

    this.GetLeftHtml = function(nodeLevel) {
        var nodeLeftHtml = "";
        for (var j = 0; j < nodeLevel.length; j++) {
            if (nodeLevel.substr(j, 1) == "1") {
                nodeLeftHtml += '<img alt="" src="' + this.IconPath + 'ftv2blank.gif" />';
            }
            else {
                nodeLeftHtml += '<img alt="" src="' + this.IconPath + 'ftv2vertline.gif" />';
            }
        }
        return nodeLeftHtml;
    };

    this.CreateXMLHttpRequest = function() {
        if (typeof XMLHttpRequest != "undefined") {
            return new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            var aVersions = ["MSXML2.XMLHttp.6.0", "MSXML2.XMLHttp.5.0", "MSXML2.XMLHttp.4.0", "MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp", "Microsoft.XMLHttp"];
            for (var i = 0; i < aVersions.length; i++) {
                try {
                    var oXmlHttp = new ActiveXObject(aVersions[i]);
                    return oXmlHttp;
                }
                catch (oError) {}
            }
        }
        return null;
    };
};