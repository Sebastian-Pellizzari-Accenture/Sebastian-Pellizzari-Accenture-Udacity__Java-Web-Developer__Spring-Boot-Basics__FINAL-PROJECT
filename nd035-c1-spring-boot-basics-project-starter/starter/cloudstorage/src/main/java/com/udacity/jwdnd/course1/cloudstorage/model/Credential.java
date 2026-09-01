package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer pwdid;
    private String urlfield;
    private String usernamefield;
    private String passwordcipher;
    private Integer userid;

    public Integer getPwdid() {
        return pwdid;
    }
    public void setPwdid(Integer pwdid) {
        this.pwdid = pwdid;
    }
    public String getUrlfield() {
        return urlfield;
    }
    public void setUrlfield(String urlfield) {
        this.urlfield = urlfield;
    }
    public String getUsernamefield() {
        return usernamefield;
    }
    public void setUsernamefield(String usernamefield) {
        this.usernamefield = usernamefield;
    }
    public String getPasswordcipher() {
        return passwordcipher;
    }
    public void setPasswordcipher(String passwordcipher) {
        this.passwordcipher = passwordcipher;
    }
    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }  
}
