import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintListOfPresidents {

    public static void main(String[] args) {

        printListOf();
    }

    private static void printListOf() {

        String url = "https://en.wikipedia.org/wiki/List_of_Presidents_of_the_United_States";

        try {
            // #mw-content-text > div.mw-parser-output > table:nth-child(13) > tbody > tr:nth-child(3) > td:nth-child(4) > b > big > a
            // #mw-content-text > div.mw-parser-output > table:nth-child(13)
            Document doc = Jsoup.connect(url).get();
//            String html = doc.html();
//            System.out.println(html);
            Elements trEles = doc.select("#mw-content-text > div.mw-parser-output " +
                    "> table:nth-child(13) > tbody > tr:gt(1)");

            Elements aEles = trEles.select("td:eq(3) > b > big > a");

            StringBuilder stringBuilder = new StringBuilder();
            for (Element ele : aEles) {
                String text = ele.text();
                stringBuilder.append(text);
                stringBuilder.append("\n");
            }
            String result = stringBuilder.toString();

            saveToFile(result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void saveToFile(String content) {
        PrintWriter out = null;
        try {
            out = new PrintWriter("listOfPresidents.txt");
            out.println(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
