package com.tibco.messaging.ftl.authservice.controller;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Base64;

public class FtlAuthenticationRequest {

    /*
     * We're expecting input with the form
     * {
     *      "username" : "guest",
     *      "password" : "Z3Vlc3QtcHc=",
     *      "meta":{
     *          "appname":"myapp"
     *          ...
     *      }
     * }
     */


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String password;
    private FTLMetaData meta;  // this needs to be next object

    public FtlAuthenticationRequest(String userName, String password, FTLMetaData meta) {
        this.username = userName;
        this.password = password;
        this.meta = meta;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordDec() {

            String password = new String(Base64.getDecoder().decode((String) this.password), StandardCharsets.UTF_8);
            return password = Normalizer.normalize(password,Normalizer.Form.NFC);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FTLMetaData getMeta() {
        return meta;
    }

    public void setMeta(FTLMetaData meta) {
        this.meta = meta;
    }
}
