package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "https://www.nba.com/search";
        Scanner s = new Scanner(System.in);

        String input = s.nextLine();

        Document doc = searchNBAPlayer3PA(url, "input#search-text-input", "button.Button_button__L2wUb.SearchBar_sbButton__eyDP6", input);

        if (doc == null) {
            System.out.println("Error Occurred");
            return;
        }

        Elements rows = doc.select("div.Block_blockContent__6iJ_n > .Crom_base__f0niE table tbody tr");

        if (rows.size() < 1) {
            System.out.println("Error Occurred");
            return;
        }

        for (Element row : rows) {
            System.out.println("YEAR: " + row.select("td").get(0).text() + " -  3PA: " + row.select("td").get(9).text());
        }

    }

    public static Document searchNBAPlayer3PA(String url, String searchbarCSSSelectorQuery, String searchButtonSelectorQuery, String searchText) {

        try (Playwright playwright = Playwright.create()) {
            System.out.println("Harvesting..");
            final BrowserType firefox = playwright.firefox();
            final Browser browser = firefox.launch();
            final Page page = browser.newPage();
            page.navigate(url);
            page.fill(searchbarCSSSelectorQuery, searchText);
            page.click(searchButtonSelectorQuery);
            // for some searches the nba page dies, so it is possible for some queries error to occure
            page.waitForTimeout(3000);
            page.waitForSelector(".SearchFilters_sfType__9nixi button:first-of-type");
            page.click(".SearchFilters_sfType__9nixi button:first-of-type");
            page.waitForSelector("a.Anchor_anchor__cSc3P.SearchBlock_sb__MK2YF:first-of-type");
            Document doc = Jsoup.parse(page.content());
            Elements results = doc.select("a.Anchor_anchor__cSc3P.SearchBlock_sb__MK2YF");
            if (results.size() != 1) {
                return null;
            }
            Element a = doc.select("a.Anchor_anchor__cSc3P.SearchBlock_sb__MK2YF:first-of-type").first();
            assert a != null;
            String pid = (a.attr("href").split("/")[2]);
            page.navigate("https://www.nba.com/stats/player/" + pid + "?PerMode=Per40");
            page.waitForSelector("table");
            Document doc2 = Jsoup.parse(page.content());
            browser.close();
            return doc2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

/*    public static String getPlayerID(String api_url) {
        try {
            URI uri = URI.create(api_url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).header("Accept", "application/json").timeout(Duration.ofSeconds(10)).build();

            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(res.body());
            JsonNode players = jsonNode.get("results").get("items");
            if (players.size() > 1 || players.size() == 0) {
                return "";
            }
            return players.get(0).get("pid").asText();
        } catch (Exception e) {
            return "";
        }

    }*/
}