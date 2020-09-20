<?xml version="1.0" encoding="UTF-8"?>
<template>


    <comment ID="addFieldComment"><![CDATA[
<#if introspectedColumn??>
/**
    <#if introspectedColumn.remarks?? && introspectedColumn.remarks != ''>
        <#list introspectedColumn.remarks?split("\n") as remark>
 * ${remark}
        </#list>
    </#if>
 */
</#if>
    ]]></comment>


</template>