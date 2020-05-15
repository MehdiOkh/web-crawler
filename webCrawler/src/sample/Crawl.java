package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;


public class Crawl {

    public static void addSubPage(WebPage wPage,ArrayList<String> emails){
        for (WebPage page :wPage.getSubPages()){
            if (page.getSubPages().size()!=0){
                addSubPage(page,emails);
            }else {
                searchMethod(page.getAddress(),page,emails);
            }
        }
    }

    static void searchMethod(String webAddress, WebPage webpage, ArrayList<String> email){
        try {
            String mainAddress = mainAddress(webAddress);
            Document doc = Jsoup.connect(webAddress).get();
            Elements links = doc.body().select("div.details").select("a");
            Elements links2 = doc.body().select("div.articles-list").select("a");

            ArrayList<WebPage> list = new ArrayList<>();
            ArrayList<String> address = new ArrayList<>();
            if (links.size()!=0){

                for (Element link:links){

                    int j=0;
                    char[] chars = link.attr("href").toCharArray();
                    for (char aChar : chars) {
                        if (aChar == '/') {
                            j++;
                        }
                    }
                    if (j == 1 ) {
                        if (!address.contains(mainAddress+link.attr("href"))){
                            address.add(mainAddress+link.attr("href"));
                        }

                    }
                }
                for (String name:address){
                    list.add(new WebPage(name));
                }
            }else {
                Elements lin = doc.body().select("div.user-metadata-details").select("a");
                for (Element element:lin){
                    if (element.attr("href").contains("@") && !email.contains(element.text())){
                        email.add(element.text());
                    }
                }
                for (Element link:links2){
                    if (!address.contains(mainAddress+link.attr("href")) && !link.attr("href").contains("comment") && !link.attr("href").contains("/t/") && !webAddress.equals("https://dev.to"+link.attr("href"))){
                        address.add(mainAddress+link.attr("href"));
                    }

                }
                for (String name:address){
                    list.add(new WebPage(name));
                }

            }
            webpage.setSubPages(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String mainAddress (String webAdd){
        String[] add = webAdd.split("/");
        return add[0]+"//"+add[2];
    }


}
