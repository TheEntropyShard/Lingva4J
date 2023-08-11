# Lingva4J
A simple Java API client for [Lingva](https://github.com/thedaviddelta/lingva-translate).

This library does not use any external dependencies, only <br>
bundled [JSON](https://github.com/stleary/JSON-java) parser which is distributed as Public Domain.

# Docs
Code is documented

## Example
```java
import java.util.Map;

public class Example {
    public static void main(String[] args) throws Exception {
        //String lingvaInstanceUrl = "https://lingva.lunar.icu";
        String lingvaInstanceUrl = "https://lingva.garudalinux.org/";
        Lingva4J lingva = new Lingva4J(lingvaInstanceUrl);

        /*
        Translating
         */
        String translate = lingva.translate("ru", "en", "Привет, как дела?");
        System.out.println(translate); // Prints "Hi, how are you?"

        /*
        Get languages
         */
        Map<String, String> languages = lingva.getLanguages();
        System.out.println(languages);

        /*
         Don't really know what to do with this
         */
        byte[] bytes = lingva.textToSpeech("ru", "Привет");
    }
}
```

## License
This library is licensed with [MIT](https://choosealicense.com/licenses/mit/) license.