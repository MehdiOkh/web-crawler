package sample;

import java.util.ArrayList;
import java.util.List;

public class WebPage {
    private String address;
    private List<WebPage> subPages = new ArrayList<>();

    public WebPage(String address) {
        this.address = address;
    }

    public void setSubPages(List<WebPage> subPages) {
        this.subPages = subPages;
    }

    public String getAddress() {
        return address;
    }

    public List<WebPage> getSubPages() {
        return subPages;
    }
}
