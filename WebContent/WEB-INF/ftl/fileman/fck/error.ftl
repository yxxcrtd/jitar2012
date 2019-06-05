<?xml version="1.0" encoding="utf-8"?>
<#-- 按照 FCK 的需求返回一个 XML -->
<Connector command="${Command!?xml}" resourceType="${ResourceType!?xml}">
  <CurrentFolder path="/" url="/" />
  <Error number="${ErrorNumber!0}" text="${ErrorMessage!?xml}" />
</Connector>
