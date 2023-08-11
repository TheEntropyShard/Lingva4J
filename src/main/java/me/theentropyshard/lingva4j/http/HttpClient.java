package me.theentropyshard.lingva4j.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {
    private final List<HttpHeader> commonHeaders;

    private String userAgent;

    public HttpClient() {
        this.commonHeaders = new ArrayList<>();
    }

    public HttpResponse send(HttpRequest request) throws IOException {
        HttpURLConnection c = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        c.setRequestMethod(request.getMethod().toString());
        c.setDoInput(true);
        c.setDoOutput(true);

        HttpClient.setHeaders(c, this.commonHeaders);
        HttpClient.setHeaders(c, request.getAdditionalHeaders());

        if (this.userAgent != null) {
            c.setRequestProperty("User-Agent", this.userAgent);
        }

        if (request.getMethod() == HttpMethod.POST) {
            byte[] payload = request.getPayload();

            if (payload == null) {
                throw new IOException("Null payload in POST request");
            }

            c.setRequestProperty("Content-Length", String.valueOf(payload.length));
            c.setRequestProperty("Content-Type", request.getContentType());

            OutputStream outputStream = c.getOutputStream();
            outputStream.write(payload);
            outputStream.flush();
        }

        int responseCode = c.getResponseCode();
        InputStream inputStream = c.getErrorStream() == null ? c.getInputStream() : c.getErrorStream();

        return new HttpResponse(responseCode, inputStream);
    }

    private static void setHeaders(HttpURLConnection c, List<HttpHeader> headers) {
        headers.forEach(header -> c.setRequestProperty(header.getKey(), header.getValue()));
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
