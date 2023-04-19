package com.zefra.pojo;

import com.zefra.util.Toos;

import javax.servlet.ServletContext;

public class ServerMessage {
    private Toos.ServerType serverType;//信息类型
    private String name;//账号名
    private String sessionId;//id名
    private ServletContext servletContext;//全局对象
    public ServerMessage(Toos.ServerType serverType, String name, String sessionId,ServletContext servletContext) {
        this.serverType = serverType;
        this.name = name;
        this.sessionId = sessionId;
        this.servletContext = servletContext;
    }
    public ServletContext getServletContext() {
        return servletContext;
    }
    public String getName() {
        return name;
    }
    public String getSessionId() {
        return sessionId;
    }
    public Toos.ServerType getServerType() {
        return serverType;
    }
    public void setServerType(Toos.ServerType serverType) {
        this.serverType = serverType;
    }
}
