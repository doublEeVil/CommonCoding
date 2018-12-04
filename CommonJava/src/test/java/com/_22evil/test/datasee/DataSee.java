package com._22evil.test.datasee;
import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataSee {

    public static void main(String ... args) throws Exception {
        new DataSee().see();
    }

    private void see() throws Exception{
        // 1. weibo 热点
        String weiboUrl = "https://s.weibo.com/top/summary";
        Document doc = Jsoup.connect(weiboUrl).get();
        System.out.println(doc);
        Elements elements = doc.getElementsByClass("td-02");
        System.err.println(elements.size());
        for (Element element : elements) {
            System.out.println(element.text() + " ");
        }
    }
}
