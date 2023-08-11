package me.theentropyshard.lingva4j.http;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    /**
     * Request url
     */
    private String url;

    /**
     * Request method
     * @see HttpMethod
     */
    private HttpMethod method;

    /**
     * Additional headers that may override common headers in HttpClient
     */
    private List<HttpHeader> additionalHeaders;

    /**
     * Request payload. Must be non-null if the request method is POST
     */
    private byte[] payload;

    /**
     * Content type in POST request
     */
    private String contentType;

    public HttpRequest() {
        this.additionalHeaders = new ArrayList<>();
    }

    public void addHeader(HttpHeader header) {
        this.additionalHeaders.add(header);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public List<HttpHeader> getAdditionalHeaders() {
        return this.additionalHeaders;
    }

    public void setAdditionalHeaders(List<HttpHeader> additionalHeaders) {
        this.additionalHeaders = additionalHeaders;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
