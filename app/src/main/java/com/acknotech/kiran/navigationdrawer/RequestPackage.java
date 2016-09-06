package com.acknotech.kiran.navigationdrawer;

/**
 * Created by kiran on 14/8/16.
 */
public class RequestPackage {

    private String uri;
    private String method = "POST";

    private String get_method = "GET";

    public String getGet_method() {
        return get_method;
    }

    public void setGet_method(String get_method) {
        this.get_method = get_method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
