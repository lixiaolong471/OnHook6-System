package com.mars.param.user;

/**
 * Created by lixl on 2018/7/2 0002.
 */
public class UserSaveNoticeRequest {

    private Long id;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
