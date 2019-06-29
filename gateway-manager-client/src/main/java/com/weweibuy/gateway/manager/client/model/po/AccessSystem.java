package com.weweibuy.gateway.manager.client.model.po;

import java.util.Date;

public class AccessSystem {
    private Long id;

    private String systemId;

    private String systemName;

    private String lbUri;

    private String systemDomain;

    private String systemDesc;

    private Boolean isDelete;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getLbUri() {
        return lbUri;
    }

    public void setLbUri(String lbUri) {
        this.lbUri = lbUri == null ? null : lbUri.trim();
    }

    public String getSystemDomain() {
        return systemDomain;
    }

    public void setSystemDomain(String systemDomain) {
        this.systemDomain = systemDomain == null ? null : systemDomain.trim();
    }

    public String getSystemDesc() {
        return systemDesc;
    }

    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc == null ? null : systemDesc.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}