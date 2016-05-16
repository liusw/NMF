package com.boyaa.mf.entity.data;

import com.boyaa.mf.support.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by liusw
 * 创建时间：16-4-21.
 */
public class ClientError {
    private String plat;
    private String sid;
    private String title;
    private String desc;
    private String descMd5;
    private String version;
    private String versionLua;
    private String mobileType;
    private String os;
    private String device;
    private Date firstTime;
    private Date latestTime;
    private String occurCount;
    private String status;
    private String tableType;

    public ClientError() {
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescMd5() {
        return descMd5;
    }

    public void setDescMd5(String descMd5) {
        this.descMd5 = descMd5;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionLua() {
        return versionLua;
    }

    public void setVersionLua(String versionLua) {
        this.versionLua = versionLua;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }
    @JsonSerialize(using = DateJsonSerializer.class)
    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    public String getOccurCount() {
        return occurCount;
    }

    public void setOccurCount(String occurCount) {
        this.occurCount = occurCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTableType() {
        return tableType;
    }
    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    @Override
    public String toString() {
        return "ClientError{" +
                "plat='" + plat + '\'' +
                ", sid='" + sid + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", descMd5='" + descMd5 + '\'' +
                ", version='" + version + '\'' +
                ", versionLua='" + versionLua + '\'' +
                ", mobileType='" + mobileType + '\'' +
                ", os='" + os + '\'' +
                ", device='" + device + '\'' +
                ", firstTime='" + firstTime + '\'' +
                ", latestTime='" + latestTime + '\'' +
                ", occurCount='" + occurCount + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
