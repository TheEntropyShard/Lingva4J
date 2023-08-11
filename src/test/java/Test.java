import me.theentropyshard.lingva4j.Lingva4J;

import java.io.IOException;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        /*
        Lingva repo: https://github.com/thedaviddelta/lingva-translate
         */

        //String lingvaInstanceUrl = "https://lingva.lunar.icu";
        String lingvaInstanceUrl = "https://lingva.garudalinux.org/";
        Lingva4J lingva = new Lingva4J(lingvaInstanceUrl);

        /*
        Translating
         */
        String translate = lingva.translate("ru", "en", "Привет, как дела?");
        System.out.println(translate);

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
