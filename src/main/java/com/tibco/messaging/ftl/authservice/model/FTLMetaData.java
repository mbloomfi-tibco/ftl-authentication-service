package com.tibco.messaging.ftl.authservice.model;

public class FTLMetaData {

    private String realm;
    // FTL Realm Authentication
    private String realm_uuid;

    // eFTL Authentication
    private String host;

    private String channel;

    private String address;

    private String client_id;

    // App name may be sent by a specific client need to check.
    private String appname;

    public String getRealm_uuid() {
        return realm_uuid;
    }

    public void setRealm_uuid(String realm_uuid) {
        this.realm_uuid = realm_uuid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
