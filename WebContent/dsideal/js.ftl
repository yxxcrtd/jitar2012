<script type="text/javascript">
function fullEscape(text) { 
  var encodedHtml = encodeURIComponent(text);
  return encodedHtml;
}
  
function login()
{
  var username = document.getElementById("username").value;
  if(username == ""){
    alert("请输入用户名");
    return;
  }
  	 document.getElementById("userlogin").submit();  
}

function processError(s)
{
 alert(s);
}
</script>