package sample;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;

import static sample.Crawl.addSubPage;
import static sample.Crawl.searchMethod;

public class CrawlService extends Service<ArrayList<String>> {
    private final String add;
    private final int times;
    public static ArrayList<String> totalEmails = new ArrayList<>();

    public CrawlService(String add, int times) {
        this.add = add;
        this.times = times;
    }

    @Override
    protected Task<ArrayList<String>> createTask() {
        return new Task<>() {
            @Override
            protected ArrayList<String> call() {

                WebPage webPage = new WebPage(add);
                ArrayList<String> emails = new ArrayList<>();
                searchMethod(add,webPage,emails);
                for (int i=0; i<times; i++){
                    addSubPage(webPage,emails);
                    updateProgress(i+1,times);
                }
                for (String email:emails){
                    if (!totalEmails.contains(email)){
                        totalEmails.add(email);
                    }
                }

                return emails;
            }
        };
    }
}
