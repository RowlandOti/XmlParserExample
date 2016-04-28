package com.rowland.xmlparser.data.rest;

public interface IApiEndPoint {

    // The Base url EndPoints
    String API_BASE_URL_ENDPOINT = "glacial-sands-39825.herokuapp.com";
    String API_PATH = "";
    // What protocol to use
    String PROTOCOL_HTTP = "http://";

    void updateInstanceUrl(String instanceUrl);

    String getUrl();

    String getName();
}
