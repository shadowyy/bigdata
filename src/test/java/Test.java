import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * @author yuyue
 * @version 2017-11-10 0010 17:28
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //TranslateOptions.newBuilder().setApiKey()


        // Instantiates a client
        Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyCALwjj1cPfWX6tMvdMz90fq449DUBMV5g").build().getDefaultInstance().getService();


        // The text to translate
        String text = "Hello, world!";

        // Translates some text into Russian
        Translation translation = translate.translate(text, com.google.cloud.translate.Translate.TranslateOption.sourceLanguage("en"), Translate
                .TranslateOption.targetLanguage("cn"));


        System.out.printf("Text: %s%n", text);
        System.out.printf("Translation: %s%n", translation.getTranslatedText());
    }
}
