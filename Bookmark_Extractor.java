import java.io.BufferedReader;
import java.io.FileReader;
import java.util.SortedSet;
import java.util.TreeSet;

public class Bookmark_Extractor {
    private static SortedSet<Integer> integerSortedSet;

    public static void main(String[] args) {
        String fileToParse =args[0];
        integerSortedSet = new TreeSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./" + fileToParse +
                ".sdr/metadata.pdf.lua"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("[\"page\"]")) {
                    int equalInd = line.indexOf("=");
                    int commaInd = line.indexOf(",");
                    String toParse = line.substring(equalInd + 2, commaInd );
                    integerSortedSet.add(Integer.valueOf(toParse));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pagesToParse = "";

        for (Integer integer : integerSortedSet) {
           pagesToParse += integer + " ";
        }
        System.out.println(pagesToParse);
        new BashWorker("pdftk " + fileToParse + ".pdf cat " +pagesToParse + " output " + fileToParse + "bookmarks.pdf");

    }
}
