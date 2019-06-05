function select_all() {
  var ids = document.getElementsByName('articleId');
  if (ids == null || ids.length == 0) return;
  var set_checked = false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked == false) {
      ids[i].checked = true;
      set_checked = true;
    }
  }
  
  if (set_checked == false) {
    for (var i = 0; i < ids.length; ++i) {
      ids[i].checked = false;
    }
  }
}
function has_selected() {
  var ids = document.getElementsByName('articleId');
  if (ids == null || ids.length == 0) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function confirm_delete() {
  return confirm('您是否确定要删除所选择的文章??');
}
function delete_a() {
  if (has_selected() == false) {
    alert('没有选择要操作的文章.');
    return false;
  }
  if (confirm_delete() == false) return;
  submit_command('delete');
}
function recover_a() {
  submit_command('recover');
}
function crash_a() {
  if (!has_selected()) {
    alert('没有选择要操作的文章.');
    return;
  }
	if (!confirm('您是否确定要彻底删除所选择的文章??\n\n警告: 彻底删除之后该文章将不可恢复.'))
	 return;
  submit_command('crash');
}
function audit_a() {
  submit_command('audit');
}
function unaudit_a() {
  submit_command('unaudit');
}
function best_a() {
  submit_command('best');
}

function push() {
  submit_command('push');
}
function unpush() {
  submit_command('unpush');
}

function unbest_a() {
  submit_command('unbest');
}
function unrcmd_a() {
  submit_command('unrcmd');
}
function move_cate_a() {
  submit_command('move');
}
function submit_command(cmd) {
  if (cmd == null || cmd == '') return false;
  if (has_selected() == false) {
    alert('没有选择要操作的文章.');
    return false;
  }
  var the_form = document.forms['articleForm'];
  if (the_form == null) {
    alert('Can\'t find articleForm form.');
    return false;
  }
  the_form.cmd.value = cmd;
  the_form.submit();
  return true;
}
