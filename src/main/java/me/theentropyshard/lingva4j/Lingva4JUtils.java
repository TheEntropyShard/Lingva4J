package me.theentropyshard.lingva4j;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Internal utils
 */
public final class Lingva4JUtils {
    static String inputStreamToString(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        scanner.close();

        return builder.toString();
    }

    static String urlEncode(String raw) {
        try {
            return new URI(null, null, raw, null).toASCIIString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean strNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    private Lingva4JUtils() {
        throw new UnsupportedOperationException();
    }
}
