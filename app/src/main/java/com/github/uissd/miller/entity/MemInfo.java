package com.github.uissd.miller.entity;

public class MemInfo {
    private String foregroundPkgName;
    private int foregroundUid;
    private String lastPkgName;
    private int lastUid;

    public String getForegroundPkgName() {
        return foregroundPkgName;
    }

    synchronized public void setForegroundPkgName(String foregroundPkgName) {
        this.foregroundPkgName = foregroundPkgName;
    }

    public int getForegroundUid() {
        return foregroundUid;
    }

    synchronized public void setForegroundUid(int foregroundUid) {
        this.foregroundUid = foregroundUid;
    }

    public String getLastPkgName() {
        return lastPkgName;
    }

    synchronized public void setLastPkgName(String lastPkgName) {
        this.lastPkgName = lastPkgName;
    }

    public int getLastUid() {
        return lastUid;
    }

    synchronized public void setLastUid(int lastUid) {
        this.lastUid = lastUid;
    }
}
