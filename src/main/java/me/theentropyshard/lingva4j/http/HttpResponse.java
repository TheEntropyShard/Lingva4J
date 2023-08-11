package me.theentropyshard.lingva4j.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class HttpResponse implements Closeable {
    private final int responseCode;
    private final InputStream inputStream;
    private final boolean successful;

    public HttpResponse(int responseCode, InputStream inputStream) {
        this.responseCode = responseCode;
        this.inputStream = inputStream;
        this.successful = responseCode < 400;
    }

    @Override
    public void close() throws IOException {
        if (this.inputStream != null) {
            this.inputStream.close();
        }
    }

    @Override
    public String toString() {
        return "HttpResponse[responseCode = " + this.responseCode + "]";
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public boolean isSuccessful() {
        return this.successful;
    }
}
