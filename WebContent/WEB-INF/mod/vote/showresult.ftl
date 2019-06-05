<h3>${vote.title} 的调查结果</h3>
<#if vote.description?? >
<div>${vote.description}</div>
</#if>
<#if vote_question_answer_list??>
<div>共有 <span style='color:#F00'>${vote_total}</span> 人参加</div>
<table border='0' style='width:100%;background:#DDD;border:1px solid #FFF' cellspacing='1' cellpadding='2'>
<#list vote_question_answer_list as q>
<tr>
<td style='background:#DDD;' colspan='3'><div style='font-weight:bold' id='q_c_${q.question.voteQuestionId}'>${q.question.title}</div></td>
</tr>
<#if q.answer?? >
<tr style='background:#EEE'>
<td>选项</td><td>比例</td><td>票数</td>
</tr>
<#list q.answer as a>
<tr style='background:#FFF;'>
<td style='padding-left:6px;'>${a_index + 1}，${a.answerContent}</td>
<td style='vertical-align:middle;width:160px;'>
<span style='display:block;background:#D9E4F8;width:100px;float:right;height:10px;margin:2px;'>
<#if vote_total == 0>
<span style="width: 0;background:url('${SiteUrl}images/vote.gif');display:block;height:10px;">&nbsp;</span>
<#else>
<span style="width: ${(a.voteCount/vote_total) * 100 }%;background:url('${SiteUrl}images/vote.gif');display:block;height:10px;">&nbsp;</span>
</#if>
</span>

<span style='float:left;'>
<span style='color:#F00'>
<#if vote_total == 0>
0
<#else>
${((a.voteCount/vote_total) * 100)?string('###.##') }
</#if></span>
%
</span>
</td>
<td><nobr><span style='color:red;'>${a.voteCount}</span> 人</nobr>
</td>
</tr>
</#list>
</#if>
</#list>
</table>
<div style='padding:10px 0'>
<input class="specialSubmit" type='button' value='刷新结果' onclick='window.location.href="${SiteUrl}mod/vote/showresult.action?guid=${parentGuid}&type=${parentType}&voteId=${vote.voteId}"' />
<input class="specialSubmit" type='button' value='返回调查页面' onclick='window.location.href="${SiteUrl}mod/vote/getcontent.action?guid=${parentGuid}&type=${parentType}&voteId=${vote.voteId}"' />
</div>
</#if>