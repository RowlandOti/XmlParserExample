package com.rowland.xmlparser.data.api;

import com.rowland.xmlparser.data.rest.IApiEndPoint;

public class ApiApiEndPoint implements IApiEndPoint {

    private String url;

    @Override
    public void updateInstanceUrl(String instanceUrl) {
        this.url = instanceUrl;
    }

    @Override
    public String getUrl() {
        if (url == null)
            return PROTOCOL_HTTP + API_BASE_URL_ENDPOINT;
        return url;
    }

    @Override
    public String getName() {
        return "xmlparser";
    }
}
