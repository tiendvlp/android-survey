package com.devlogs.osg.apolo.fptsurvey.domain.entity;

public class StatusEntity {
    private String id;
    private String type;
    private String content;

    public StatusEntity(String id, String type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
