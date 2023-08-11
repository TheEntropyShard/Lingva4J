package me.theentropyshard.lingva4j;

import me.theentropyshard.lingva4j.http.HttpClient;
import me.theentropyshard.lingva4j.http.HttpMethod;
import me.theentropyshard.lingva4j.http.HttpRequest;
import me.theentropyshard.lingva4j.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple Java API client for Lingva
 */
public final class Lingva4J {
    private static final String API_V1 = "/api/v1/";

    private final String baseUrl;
    private final HttpClient httpClient;

    public Lingva4J(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }

        this.baseUrl = baseUrl;
        this.httpClient = new HttpClient();
        this.httpClient.setUserAgent("Lingva4J/1.0.0");
    }

    /**
     * Translates given text with given source and destination languages
     *
     * @param sourceLang Two-letter language code to translate from
     * @param destLang   Two-letter language code to translate into
     * @param text       Text to translate
     * @return Translated text
     * @throws IOException Thrown when an IO exception occurs
     */
    public String translate(String sourceLang, String destLang, String text) throws IOException {
        if (Lingva4JUtils.strNullOrEmpty(sourceLang)) {
            throw new IllegalArgumentException("Source language is null or empty");
        }

        if (Lingva4JUtils.strNullOrEmpty(destLang)) {
            throw new IllegalArgumentException("Destination language is null or empty");
        }

        if (Lingva4JUtils.strNullOrEmpty(text)) {
            throw new IllegalArgumentException("Text is null or empty");
        }

        HttpRequest request = new HttpRequest();
        text = Lingva4JUtils.urlEncode(text);
        request.setUrl(this.baseUrl + Lingva4J.API_V1 + sourceLang + "/" + destLang + "/" + text);
        request.setMethod(HttpMethod.GET);

        try (HttpResponse response = this.httpClient.send(request)) {
            String source = Lingva4JUtils.inputStreamToString(response.getInputStream());
            JSONObject jsonObject = new JSONObject(source);
            if (response.isSuccessful()) {
                return jsonObject.getString("translation");
            } else {
                return jsonObject.getString("error");
            }
        }
    }

    /**
     * Pronounces text using with the specified language
     *
     * @param lang Two-letter language code
     * @param text Text to pronounce
     * @return Array of bytes containing audio data
     * @throws IOException Thrown when an IO exception occurs
     */
    public byte[] textToSpeech(String lang, String text) throws IOException {
        if (Lingva4JUtils.strNullOrEmpty(lang)) {
            throw new IllegalArgumentException("Language is null or empty");
        }

        if (Lingva4JUtils.strNullOrEmpty(text)) {
            throw new IllegalArgumentException("Text is null or empty");
        }

        HttpRequest request = new HttpRequest();
        text = Lingva4JUtils.urlEncode(text);
        request.setUrl(this.baseUrl + Lingva4J.API_V1 + "audio" + "/" + lang + "/" + text);
        request.setMethod(HttpMethod.GET);

        try (HttpResponse response = this.httpClient.send(request)) {
            if (response.isSuccessful()) {
                String source = Lingva4JUtils.inputStreamToString(response.getInputStream());
                JSONObject jsonObject = new JSONObject(source);
                JSONArray audioArray = jsonObject.getJSONArray("audio");
                byte[] data = new byte[audioArray.length()];
                for (int i = 0; i < data.length; i++) {
                    data[i] = ((Integer) audioArray.get(i)).byteValue();
                }

                return data;
            } else {
                return new byte[0];
            }
        }
    }

    /**
     * Returns languages supported by this translator
     *
     * @return Map&lt;String, String&gt;, where keys are two-letter language codes and values are language names
     * @throws IOException Thrown when an IO exception occurs
     */
    public Map<String, String> getLanguages() throws IOException {
        Map<String, String> languages = new HashMap<>();

        HttpRequest request = new HttpRequest();
        request.setUrl(this.baseUrl + Lingva4J.API_V1 + "languages");
        request.setMethod(HttpMethod.GET);

        try (HttpResponse response = this.httpClient.send(request)) {
            String source = Lingva4JUtils.inputStreamToString(response.getInputStream());
            JSONObject jsonObject = new JSONObject(source);
            if (response.isSuccessful()) {
                JSONArray languagesArray = jsonObject.getJSONArray("languages");
                for (Object o : languagesArray) {
                    JSONObject languageObject = (JSONObject) o;
                    languages.put(languageObject.getString("code"), languageObject.getString("name"));
                }
            } else {
                return Collections.singletonMap("error", jsonObject.getString("error"));
            }
        }

        return languages;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }
}
