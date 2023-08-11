package me.theentropyshard.lingva4j.http;

public class HttpHeader {
    private final String key;
    private final String value;

    public HttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * Converts this HttpHeader to its String representation
     * @param revealValue Must be true, if caller wants value to be shown 
     */
    public String toString(boolean revealValue) {
        return "HttpHeader[key = " + this.key + ", value = " + (revealValue ? this.value : "<CENSORED>");
    }

    @Override
    public String toString() {
        return this.toString(false);
    }
}
