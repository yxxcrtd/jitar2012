<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="icon" href="${SiteUrl}images/favicon.ico" />
<link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
<title>${channel.title}</title>
<#if cssStyle??>
    <style>
    ${cssStyle}
    </style>
<#else>
    <#if channel.cssStyle?? >
    <style>
    ${channel.cssStyle}
    </style>
    <#else>
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/channel/template1/common.css" />
    </#if>
</#if>    
<script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
</head>
<body>
#[学科导航]
#[频道Logo]
#[频道导航]