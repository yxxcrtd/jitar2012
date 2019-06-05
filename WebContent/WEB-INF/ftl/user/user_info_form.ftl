<#if user??>
<#if !(manageMode??)><#assign manageMode = ''></#if>
<form name="profile_form" id="profile_form" action="user.action" method="post">
  <input type="hidden" name="cmd" value="save" />
  <input type="hidden" name="userId" value="${user.userId}" />
  <input type="hidden" name="loginName" value="${user.loginName!?html}" />
<#if __referer??>
  <input type="hidden" name="__referer" value="${__referer!?html}" />
</#if>
<table align="center">
  <tr>
    <td colspan="2">
    <#if s??>
      <@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
    </#if>
    </td>
  </tr>
</table>
<table class="listTable" cellspacing="1" cellpadding="0">
  <tr>
    <td align="right" width="20%">
      <b>用户登录名：</b>
    </td>
    <td>
      ${user.loginName!?html}
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>个人头像：</b>
    </td>
    <td align="left">
     <table cellspacing="6">
     <tr>
     <td>
    <img id="icon_image" src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif';" 
      width="64" height="64" />
      </td>
      <td>
      <!--
  <#if icon_list?? >
      <select onchange="javascript:icon_selected(this)">
        <option value="">选择一个系统头像</option>
        <option value="origin">(原头像)</option>
      <#list icon_list as icon>
        <option value="${icon}">${Util.fileName(icon)}</option>
      </#list>
      </select>
  </#if>
  -->
      <input type="hidden" name="userIconOld" value="${user.userIcon!''}" />
      <input type="hidden" name="userIcon" id="userIconId" value="${user.userIcon!''}" />
      <!--
        <#if (manageMode != "admin") >
          <iframe id="uploadFrame" name="uploadFrame" frameborder="0" scrolling="no" width="100%" height="30" src="uploadheadimg.jsp?userId=${user.userId}"></iframe>
             头像照片(<font style="color: #FF0000;">png,gif,jpg,jpeg, 图片大小 &lt; 200K</font>)
        </#if>
        -->
        <br/>
        <input type="button" id="btnUpload" name="btnUpload" value="上传头像"  onclick="uploadPhoto();"/>
<script>
function icon_selected(sel) {
  var src = sel.options[sel.selectedIndex].value;
  if (src == null || src == "") return;
  if (src == "origin")
    src = document.profile_form.userIconOld.value;
    
  var image = document.getElementById("icon_image");
  image.src = "../" + src;
  profile_form.userIcon.value = src;
}
</script>
 </td>
  </tr>
  </table>
    </td>
  </tr>
        <tr>
          <td align="right">
            <b>身份：</b>
          </td>
          <td>
          <#if user.positionId == 1 || user.positionId == 2 >
            <span style="display:none">
            <input type="radio" name="role" value="2" <#if user.positionId == 2>checked</#if> />机构管理员
            <input type="radio" name="role" value="1" <#if user.positionId == 1>checked</#if> />系统管理员
            </span>
            <#if user.positionId == 1>系统管理员</#if>
            <#if user.positionId == 2>机构管理员</#if>
          <#else>
            <input type="radio" name="role" value="3" <#if user.positionId == 3>checked</#if> />教师
            <input type="radio" name="role" value="5" <#if user.positionId == 5>checked</#if> />学生
            <input type="radio" name="role" value="4" <#if user.positionId == 4>checked</#if> />教育局职工
          </#if>
            <font style="color: #FF0000;">如果修改了身份，则需要管理员审核通过才能登录！</font>
          </td>
        </tr>  
        <tr>
            <td align="right">
                <strong>真实姓名：</strong>
            </td>
            <td>
                <input type="text" name="trueName" maxLength="25" value="${user.trueName!?html}" />
                <#if trueName == "true">
                    <font style="color: #FF0000;">如果修改了真实姓名，则需要管理员审核通过才能登录！</font>
                </#if>
            </td>
        </tr><#--
        <tr>
            <td align="right">
                <strong>昵称：</strong>
            </td>
            <td>
                <input type="text" name="nickName" maxLength="25" value="${user.nickName!?html}" />
                <#if nickName == "true">
                    <font style="color: #FF0000;">真实姓名和呢称必须一致！ 如果修改，则需要管理员审核通过才能登录！</font>
                </#if>
            </td>
        </tr>-->
        <tr>  
            <td align="right">
                <strong>电子邮件：</strong>
            </td>
            <td>
                <input type="text" name="email" size="50" maxLength="125" value="${user.email!?html}" />
            </td>
        </tr>
        <tr>
            <td align="right">
                <strong>身份证号码：</strong>
            </td>
            <td>
                <input type="text" name="IDCard" size="25" maxLength="18" value="${user.idCard!?html}" />
                <#if IDCard == "true">
                    <font style="color: #FF0000;">如果修改了身份证号码，则需要管理员审核通过才能登录！</font>
                </#if>
            </td>
        </tr>
  <tr>
    <td align="right">
      <b>QQ号码：</b>
    </td>
    <td>
      <input type="text" name="QQ" size="18" maxLength="125" value="${user.qq!?html}" />
      <font style="color: #FF0000;">QQ为公开信息，可能会被人在QQ打扰，请根据情况填写本信息。</font>
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>手机号码：</b>
    </td>
    <td>
      <input type="text" name="MobilePhone" size="18" maxLength="18" value="${user.mobilePhone!?html}" />
    </td>
  </tr>  
  <tr>
    <td align="right">
      <b>工作室名称：</b>
    </td>
    <td>
      <input type="text" name="blogName" size="50" maxLength="125" value="${user.blogName!?html}" />
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>工作室标签：</b>
    </td>
    <td>
      <input type="text" name="userTags" size="50" value="${user.userTags!?html}" /> 以 ',' 逗号分隔.
    </td>
  </tr>
  <tr>
    <td align="right" style="vertical-align:top">
      <b>工作室介绍：</b>
    </td>
    <td>
      <textarea name="blogIntroduce" cols="49" rows="5" >${user.blogIntroduce!?html}</textarea>
      <br />最多填写256个汉字.
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>性别：</b>
    </td>
    <td>
      <input type="radio" name="gender" value="1" ${(user.gender == 1)?string('checked','')} />男
      <input type="radio" name="gender" value="0" ${(user.gender == 0)?string('checked','')} />女
    </td>
  </tr>
  <tr>
  <td align="right" style="vertical-align:top"><b>工作室分类：</b></td><td>
   <select name="categoryId">
        <option value="">工作室分类</option>
        <#if syscate_tree??>
            <#list syscate_tree.all as c>
            <option value="${c.id}" ${(c.categoryId==(user.categoryId!0))?string('selected','')}>
                ${c.treeFlag2} ${c.name!?html}
            </option>
            </#list>
        </#if>
      </select>
      </td>
     </tr>
  <tr>
    <td align="right">
      <b>机构：</b>
    </td>
    <td>
    <#assign unit=Util.unitById(user.unitId)>
    <span id='unitName'><#if unit??>${unit.unitTitle!}</#if></span> 
    <input type='hidden' name='unitId' id="unit_id" value='${user.unitId!}' />
    <input type='button' value='选择机构' onclick='window.open("${SiteUrl}jython/get_unit_list.py","_blank","width=800,height=600,scrollbars=1,resizable=1")' />
    </td>
  </tr>
</table>
      
<table border="0" width="98%">
 <tr>
   <td width="30%">&nbsp;</td>
   <td width="60%">
    <input type="hidden" name="type" value="${type!?html}" />
    <input type="submit" class="button" value="  修  改  " />&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" class="button" value=" 取 消 " onClick="window.history.go(-1);" />
   </td>
 </tr>
</table>
      
</form>


<#else>
用户被删除或者未登录。
</#if>