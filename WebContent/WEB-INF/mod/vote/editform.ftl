<form method='post'>
<table style='width:600px'>
<tr>
<th style='width:66px;'>调查名称：</th><td><input name='vote_title' style='width:500px' /></td>
</tr>
<tr>
<th style='vertical-align:top'>调查描述：</th><td><textarea name='vote_desc' style='width:500px;height:40px'></textarea></td>
</tr>
<tr>
<th>截止日期：</th><td><input name='vote_enddate' id='v_enddate' value='' /></td>
</tr>
<tr>
<th colspan='2'>调查选项：</th>
</tr>
</table>
<div id='q'>
<table style='width:600px'>
<tr>
<th style='width:66px;'>问题1：</th><td><input name='vote_q_1' style='width:500px' /></td>
</tr>
<tr>
<td>问题类型：</td><td>
<input type='radio' name='vote_t_1' value='1' checked='checked' />单选 
<input type='radio' name='vote_t_1' value='0' />多选</td>
</tr>
<tr>
<td>必选个数：</td><td><input  name='vote_max_1' value='0' />（多选的时候使用，0表示不限制。）</td>
</tr>
<tr>
<td style='vertical-align:top'>选项：</td><td id='td_1'>
	<div><textarea name='vote_a_1' style='width:500px;height:40px' onclick='addQuestionItem(event)' onkeydown='addQuestionItem(event)'></textarea></div>
	<div><textarea name='vote_a_1' style='width:500px;height:40px' onclick='addQuestionItem(event)' onkeydown='addQuestionItem(event)'></textarea></div>
</td>
</tr>
</table>
</div>
<div style=''><input type='button' value='增加调查问题' onclick='addQuestion()' /> <input type='submit' value=' 保  存 ' /></div>
<div style='color:red;font-weight:bold;padding-top:6px'>【说明：】问题名称和选项如果为空，则将删除本条目。保存前请仔细检查各项输入。</div>
<input type='hidden' id='qnum' name='question_num' value='1' />
</form>
<script type='text/javascript'>
var isIE10 = false;
/*@cc_on
    if (/^10/.test(@_jscript_version)) {
        isIE10 = true;
    }
@*/

function addQuestionItem(evt)
{
 var ele = Platform.isIE ? window.event.srcElement:evt.target;
 if(!ele) return;
 var elename=ele.name;
 var eleindex = elename.split('_')[2]
 var eles = document.getElementsByName(elename)
 var hasBlank = false;

 for(var i = 0;i<eles.length;i++)
 {
 	if(eles[i].value == '')
 	{
 		hasBlank = true;
 		return;
 	} 	
 }

if(!hasBlank)
{
    var d;
    var t;
	d = document.createElement('DIV');
	if(Platform.isIE && !isIE10)
	{
		t = document.createElement('<TEXTAREA name="' + elename + '"></TEXTAREA>');
		//t.className = '';
		//t.setAttribute('name',elename);	
		//t.name = elename;
	}
	else
	{
	 	t = document.createElement('textarea');
		//t.className = '';
		t.setAttribute('name',elename);	
		t.name = elename;
	}
	t.style.width = '500px';
	t.style.height = '40px';
	t.onclick = t.onkeydown = addQuestionItem;
	d.appendChild(t)
	document.getElementById('td_' + eleindex).appendChild(d) 		
}
}

function addQuestion()
{
  var q_count = parseInt(document.getElementById('qnum').value, 10);
  q_count += 1;
  var strHtml = "<table style='width:600px'>";
  	  strHtml += "<tr><th style='width:66px;'>问题" + q_count + "：</th><td><input name='vote_q_" + q_count + "' style='width:500px' /></td></tr>";
  	  strHtml += "<tr><td>问题类型：</td><td><input type='radio' name='vote_t_" + q_count + "' value='1' checked='checked' />单选 <input type='radio' name='vote_t_" + q_count + "' value='0' />多选</td></tr>";
  	  strHtml += "<tr><td>必选个数：</td><td><input  name='vote_max_" + q_count + "' value='0' />（多选的时候使用，0表示不限制。）</td></tr>";
  	  strHtml += "<tr><td style='vertical-align:top'>选项：</td><td id='td_" + q_count + "'>";
  	  strHtml += "<div><textarea name='vote_a_" + q_count + "' style='width:500px;height:40px' onclick='addQuestionItem(event)' onkeydown='addQuestionItem(event)'></textarea></div>";
  	  strHtml += "</td></tr></table>";
  	  var d = document.createElement('div');
  	  d.innerHTML = strHtml;
  document.getElementById("q").appendChild(d)
  document.getElementsByName('vote_a_' + q_count)[0].onclick = document.getElementsByName('vote_a_' + q_count)[0].onkeydown = addQuestionItem;
  document.getElementById('qnum').value = q_count;
}
window.setTimeout('calendar.set("v_enddate")',2000);
</script>