package com._22evil.crawer;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by shangguyi on 2017/9/22.
 */
public class MyPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        String author = "author";
        String name = "name";
        String readme = "readme";
        System.out.println("get page: " + url);
        Iterator var3 = resultItems.getAll().entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
            if (entry.getKey().equals(author))
                author = entry.getValue().toString();
            if (entry.getKey().equals(name))
                name = entry.getValue().toString();
            if (entry.getKey().equals(readme))
                readme = entry.getValue().toString();
        }
    }
}