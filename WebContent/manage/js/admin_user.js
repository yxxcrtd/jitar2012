var blnIsChecked = true;
function on_checkAll(oForm) {
  for (var i = 0; i < oForm.elements.length; i++) {
    if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
    }
    oForm.elements[i].checked = blnIsChecked;
  }
	if (oForm.elements["selAll"]) {
	  if (blnIsChecked) {
	    oForm.elements["selAll"].value = " 全 选 ";
	  } else {
	    oForm.elements["selAll"].value = " 全 选 ";
	  }
	}
	blnIsChecked = !blnIsChecked;
}
   
function delSel(list_form) {
  if (hasChecked(list_form) == false) {
    alert("没有选择任何要操作的用户.");
	  return false;
	} else {
	  if (confirm("您是否确定要删除选择的用户??") == false) {
	    return false;
	  }
  }
	list_form.cmd.value = "delete";
	list_form.submit();
}

function auditSel(list_form) {
  if (hasChecked(list_form) == false) {
    alert("没有选择任何要操作的用户.");
    return false;
  } else {
    if (confirm("您是否确定要审核选择的用户??") == false) {
      return false;
    }
  }
  list_form.cmd.value = "audit";
  list_form.submit();
}

function lockSel(list_form) {
  if (hasChecked(list_form) == false) {
    alert("没有选择任何要操作的用户.");
    return false;
  } else {
    if (confirm("您是否确定要锁住选择的用户??") == false) {
      return false;
    }
  }
  list_form.cmd.value = "lock";
  list_form.submit();
}

function renewSel(list_form) {
  if (hasChecked(list_form) == false) {
    alert("没有选择任何要操作的用户.");
    return false;
  } else {
    if (confirm("您是否确定要恢复选择的用户??") == false) {
      return false;
    }
  }
  list_form.cmd.value = "renew";
  list_form.submit();
}

function openSel(list_form) {
	if (hasChecked(list_form) == false) {
		alert("没有选择用户！");
		return false;
	} else {
		if (confirm("您确定要开启选择用户的个人会议室？") == false) {
			return false;
		}
	}
	list_form.cmd.value = "open";
	list_form.submit();
}

function closeSel(list_form) {
	if (hasChecked(list_form) == false) {
		alert("没有选择用户！");
		return false;
	} else {
		if (confirm("您确定要关闭选择用户的个人会议室？") == false) {
			return false;
		}
	}
	list_form.cmd.value = "close";
	list_form.submit();
}

function hasChecked(vform) {
  for (var i = 0; i < vform.elements.length; i++) {
    var e = vform.elements[i];
    if (e.checked) {
      return true;
    }
  }
  return false;
}

function setToSelect(list_form, sel) {
	var set_to = sel.options[sel.selectedIndex].value;
	if (set_to == null || set_to == '') return;
	
  if (hasChecked(list_form) == false) {
    alert("没有选择任何要操作的用户.");
    return false;
  } else {
    list_form.cmd.value = "set";
    list_form.set_to.value = set_to;
    list_form.submit();
  }
}

function unsetTo(list_form, setName) {
  if (hasChecked(list_form) == false) {
    alert("没有选择任何要操作的用户.");
    return false;
  } else {
    list_form.cmd.value = "set";
    list_form.set_to.value = setName;
    list_form.submit();
  }
}

/*按 Esc 退出 */
function close_dialog(event) {
    if (event.keyCode == 27) {
        MessageBox.Close();
    }
}
