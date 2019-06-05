<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>用户视频列表</title>
        <link rel="styleSheet" type="text/css" href="../css/manage.css">        
        <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
        <script type="text/javascript" src="../js/jitar/core.js"></script>
        <script type="text/javascript" src="../js/msgbox.js"></script>
    </head>
    <body style="margin-top: 20px;">
        <h2>用户视频列表</h2>
        <iframe name="hiddenframe" style="display:none"></iframe>
        <table align="center">
            <tr>
                <td colspan="2">
                    <@s.actionerror cssClass="actionError" />
                </td>
            </tr>
        </table>
        
        <form name="videoForm" id="videoForm" action="video.action">
        <div style="text-align: right; width: 100%;">
                关键字：
                <input type="text" size="30" name="k" value="${k!?html}" onMouseOver="this.select();" />
                <#if !(f??)><#assign f = '0'></#if>
                <select name="f">
                    <option value="0" ${(f == '0')?string('selected', '')}>视频标题</option>
                    <option value="1" ${(f == '1')?string('selected', '')}>视频分类</option>
                    <option value="2" ${(f == '2')?string('selected', '')}>上传用户</option>
                </select>
                <#if !(auditState??)><#assign auditState = '-1'></#if>
                <select name="auditState">
                    <option value="-1" ${(auditState == '-1')?string('selected', '')}>审核状态</option>
                    <option value="0" ${(auditState == '0')?string('selected', '')}>已审核</option>
                    <option value="1" ${(auditState == '1')?string('selected', '')}>未审核</option>
                </select>
                <input type="submit" class="button" value="检  索" />
        </div>      
               <input type="hidden" name="cmd" value="uservideo"/>
        </form>
        <form method="post" action="video.action" id="actionVideo">
        <input type="hidden" name="cmd" value=""/>
            <table class="listTable" cellSpacing="1">
                <thead>
                    <tr>
                        <th width="6%">选择</th>
                        <th width="7%">视&nbsp;&nbsp;频</th>
                        <th>视频标题</th>
                        <th width="6%">评论</th>
                        <th width="6%">播放</th>
                        <th width="10%">上传者</th>
                        <th width="10%">上传日期</th>
                        <th width="10%">系统分类</th>
                        <th width="8%">审核状态</th>
                    </tr>
                </thead>
                <tbody>
                    <#if videoList?size == 0>
                    <tr>
                        <td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
                            没有符合条件的视频信息！
                        </td>
                    </tr>
                    </#if>
                    
                    <#list videoList as video>
                    <tr>
                        <td style="text-align: center;">
                            <input type="checkbox" name="vId" value="${video.videoId}" /><br/>
                            ${video.videoId}
                        </td>
                        <td style="text-align: center;">
                           <a href="?cmd=show&amp;videoId=${video.id}" target="_blank"><img width="80px" height="80px" src="${video.flvThumbNailHref!?html}" /></a>
                        </td>
                        <td style="padding-left: 10px;">
                            ${video.title!?html}
                        </td>
                        <td style="padding-left: 10px;">
                            ${video.commentCount}
                        </td>
                        <td style="padding-left: 10px;">
                            ${video.viewCount}
                        </td>
                        <td style="text-align: center;">
                            ${userlist['v'+video.videoId]!}
                        </td>
                        <td style="text-align: center;">
                            ${video.createDate}
                        </td>
                        <td style="text-align: center;">
                         <#if video.categoryId?? && video.sysCate??><a target="_blank" href='${SiteUrl}videos.action?categoryId=${video.categoryId}'>${video.sysCate.name}</a></#if>
                        </td>
                        <td style="text-align: center;">
                           <#if video.auditState==0>
                            已审核
                           <#elseif video.auditState==1>
                            <font color=red>待审核</font>
                           <#elseif video.auditState==2>
                           </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>    
            </form> 
            <div style="width: 98%; text-align: center; margin: 3px auto 3px;">
                <#include "../inc/pager.ftl">
            </div>
            <input type="button" class='button' name='selAll' value='全部选择' onclick="select_all(document.getElementById('actionVideo'))" />
            <input type='button' class='button' value = ' 确  定 ' onclick='selectedVideo();' />
            <input type='button' class='button' value = ' 取  消 ' onclick="closeMe();" />
    <script>
    var blnIsChecked = true;
    function select_all(list_form){
      for (var i = 0; i < list_form.elements.length; i++) {
        if (list_form.elements[i].type == "checkbox" && !list_form.elements[i].disabled) {
        }
        list_form.elements[i].checked = blnIsChecked;
      }
      if(list_form.elements["selAll"]) {
        if(blnIsChecked) {
          list_form.elements["selAll"].value = "取消全选";
        } else {
          list_form.elements["selAll"].value = "全部选择"; 
        }
      }
      blnIsChecked = !blnIsChecked;
    }
    function items_selected()
    {
      var ids = document.getElementsByName("vId");
      var s= "";
      for(i = 0;i<ids.length;i++){
        if(ids[i].checked){
          if(s==""){
            s=ids[i].value;
          }else{
            s=s+","+ ids[i].value;
          }
        }
      }
      return s;
    }
    function hasChecked() {
      // 检查是否有选择.
      var ids = document.getElementsByName("vId");
      var hc = false;
      for(i = 0;i<ids.length;i++){
        if(ids[i].checked){
          hc = true;
          break;
        }
      }
      return hc;
    }
    function selectedVideo() {
      if (hasChecked() == false) {
        alert('没有选择视频');
        return;
      }
      var s=items_selected();
        window.returnValue=s;
        window.close();        
    }
    
function closeMe()
{
    window.returnValue="";
    window.close();
} 
</script>
    <div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
      &nbsp;
    </div>
    
    <#-- 对话框 -->
    <div id='MessageTip' class='message_frame' style='width:260px;'>
      <div class='boxHead'>
       <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
       <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />信息提示</div>
      </div>
      <div style='padding:20px;' id='MsgDiv'>
            
      </div>
    </div>
    </body>
</html>
