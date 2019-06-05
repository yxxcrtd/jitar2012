//Support Non-IE's selectNodes() selectSingleNode()
function parseXML(xmlhttpResponse) {
  var result;
  if (Prototype.Browser.IE) {
    result = xmlhttpResponse.responseXML;
  } else {
    var parser = new DOMParser();
    result = parser.parseFromString(xmlhttpResponse.responseText, "text/xml");
  }
  return result;
}

function getNodeText(s)
{
  if(!Prototype.Browser.IE)
  {
    return s.textContent;
  }
  else
  {
    return s.text;
  }
}


if (!Prototype.Browser.IE) {
  XMLDocument.prototype.selectSingleNode = Element.prototype.selectSingleNode = function (s) {
    return this.selectNodes(s)[0];
  };
  XMLDocument.prototype.selectNodes = Element.prototype.selectNodes = function (s) {
    var rt = [];
    for (var i = 0, rs = this.evaluate(s, this, this.createNSResolver(this.ownerDocument == null ? this.documentElement : this.ownerDocument.documentElement), XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null), sl = rs.snapshotLength; i < sl; i++) {
      rt.push(rs.snapshotItem(i));
    }
    return rt;
  };
  //Code Bellow doesn't work,maybe prototype block it!!!!
  XMLDocument.prototype.__proto__.__defineGetter__("xml", function () {
    try {
      return new XMLSerializer().serializeToString(this);
    }
    catch (e) {
      //return document.createElement("div").appendChild(this.cloneNode(true)).innerHTML;
    }
  });
  Element.prototype.__proto__.__defineGetter__("xml", function () {
    try {
      return new XMLSerializer().serializeToString(this);
    }
    catch (e) {
      //return document.createElement("div").appendChild(this.cloneNode(true)).innerHTML;
    }
  });
  XMLDocument.prototype.__proto__.__defineGetter__("text", function () {
    return this.firstChild.textContent;
  });
  Element.prototype.__proto__.__defineGetter__("text", function () {
    return this.textContent;
  });
  Element.prototype.__proto__.__defineSetter__("text", function (s) {
    return this.textContent = s;
  });
}

