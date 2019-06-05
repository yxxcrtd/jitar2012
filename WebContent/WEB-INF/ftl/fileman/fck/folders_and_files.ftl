<?xml version="1.0" encoding="utf-8"?>
<#-- 按照 FCK 的需求返回一个 XML -->
<Connector command="${Command!?xml}" resourceType="${ResourceType!?xml}">
  <CurrentFolder path="${CurrentFolder!?xml}" url="${CurrentFolderUrl!?xml}" />
  <Error number="0" />
<#if folder_list?size != 0 >
  <Folders>
  <#list folder_list as folder>
    <Folder name="${folder.name?xml}" />
  </#list>
  </Folders>
</#if>
<#if file_list?size != 0 >
  <Files>
  <#list file_list as file>
    <File name="${file.name?xml}" size="${file.length() / 1024}" />
  </#list>
  </Files>
</#if>
</Connector>
