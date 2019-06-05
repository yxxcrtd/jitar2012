
var blnIsChecked = true;

// 选择所有的check box
function on_checkAll(oForm) {
	for (var i = 0; i < oForm.elements.length; i++) {
		if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {// && event.srcElement != oForm.elements[i])
		}
		oForm.elements[i].checked = blnIsChecked;
	}
	if (oForm.elements["selAll"]) {
		if (blnIsChecked) {
			oForm.elements["selAll"].value = "全部不选";
		} else {
			oForm.elements["selAll"].value = "全部选择";
		}
	}
	blnIsChecked = !blnIsChecked;
}

// 是否有check box被选中
function hasItemSelected(form) {
	var idlist = form.elements["id"];
	if (idlist == null) {
		return false;
	}
	if (idlist.length != null) {
		for (var i = 0; i < idlist.length; i++) {
			if (idlist[i].disabled != true) {
				if (idlist[i].checked) {
					return true;
				}
			}
		}
	} else {
		if (idlist != null) {
			if (idlist.disabled != true) {
				return idlist.checked;
			}
		}
	}
	return false;
}
// =============================
//   全选操作
// =============================

// 全选函数， 将指定 form 的 checkbox 全部选中， 如果全部选中， 则全部取消
function checkAll(vform) {
  // check if checkall
	var isCheckall = true;
	for (var i = 0; i < vform.elements.length; i++) {
		var e = vform.elements[i];
		if (e.type == "checkbox" && e.checked == false) {
			isCheckall = false;
			break;
		}
	}
	for (var i = 0; i < vform.elements.length; i++) {
		var e = vform.elements[i];
		if (e.type == "checkbox") {
			e.checked = !isCheckall;
		}
	}
}

// 判断是否存在选中的元素
function hasChecked(vform) {
	for (var i = 0; i < vform.elements.length; i++) {
		var e = vform.elements[i];
		if (e.checked) {
			return true;
		}
	}
	return false;
}

