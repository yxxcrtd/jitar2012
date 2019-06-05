/* 教研通用 js 脚本。编码：UTF-8. */
// 检查 html document 中是否存在 divId 名字的元素(一般是 div)
function chkdiv(divId) {
  var elem = document.getElementById(divId);
  return (elem != null);
}

function getpara() {
  var str,parastr
  str = window.location.search;
  alert('getpara str=' + str);
  parastr = str.substring(1);
  return parastr;
}
