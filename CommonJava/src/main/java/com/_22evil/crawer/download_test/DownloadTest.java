package com._22evil.crawer.download_test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import java.util.*;

public class DownloadTest implements PageProcessor{

    private Site site = Site.me().setRetrySleepTime(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        System.out.println("===" + page.getUrl().get());
        List<String> all = page.getHtml().links().all();
        for (String s : all) {
            System.out.println(s);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("----");
        Spider spider = Spider.create(new DownloadTest());
        spider.addUrl("https://www.22evil.com");
        spider.thread(5);
        spider.addPipeline(new ConsolePipeline());
        spider.run();
    }
}