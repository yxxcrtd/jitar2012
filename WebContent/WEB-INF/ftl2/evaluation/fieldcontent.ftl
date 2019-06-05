<#if field_list??><#list field_list as t >${t.fieldsId},</#list></#if>!!!+!!!*!!!
<#if field_list??>
<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:450px">
<#list field_list as t >
    <tr>
    <td style="width:140px;text-align:right;size:12px;font-weight: bold;">
    <br/><br/>
    ${t.fieldsCaption!?html}
    </td>
    <td>
            <input type="hidden" name="fieldname${t.fieldsId}" value="${t.fieldsCaption!}"/>
            <script id="DHtml${t.fieldsId}" name="fieldcontent${t.fieldsId}" type="text/plain" style="width:800px;height:300px;"></script>
    </td>
    </tr>
</#list>
<tr>
    <td></td>
    <td>
        <input type="submit" name="btnSave" value="保存评课内容" style="cursor:pointer;width:140px;height:30px;background-color:blue;color:white;font-weight:bold;margin-top:5px;margin-bottom:5px;"  onclick="return checkInput(this.form)"/>
    </td>
</tr>
</table>
</#if>