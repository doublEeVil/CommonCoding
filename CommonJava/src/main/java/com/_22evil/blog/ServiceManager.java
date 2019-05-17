package com._22evil.blog;
import com._22evil.blog.service.IAdminService;
import com._22evil.blog.service.IArticleService;
import com._22evil.blog.service.IPicInfoService;
import com._22evil.blog.service.impl.AdminServiceImpl;
import com._22evil.blog.service.impl.ArticleServiceImpl;
import com._22evil.blog.service.impl.PicInfoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class ServiceManager {
    private static ServiceManager                     instance;
    private static AnnotationConfigApplicationContext context;

    private ServiceManager() {
    }

    public static ServiceManager getInstance() {
        if (null == instance) {
            synchronized (ServiceManager.class) {
                if (null == instance) {
                    instance = new ServiceManager();
                    context = new AnnotationConfigApplicationContext();
                    context.register(BlogConfig.class);
                    context.refresh();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化一些数据
     */
    public void initData() {
        System.out.println("loading data ...");
        ServiceManager.getInstance().getPicInfoService().initPicData();
        ServiceManager.getInstance().getArticleService().onStart();
    }

    /**
     * 获得文章服务
     * @return
     */
    public IArticleService getArticleService() {
        return context.getBean(ArticleServiceImpl.class);
    }

    /**
     * 文章管理服务
     * @return
     */
    public IAdminService getAdminService() {
        return context.getBean(AdminServiceImpl.class);
    }

    /**
     * 图片管理
     * @return
     */
    public IPicInfoService getPicInfoService() {
        return context.getBean(PicInfoService.class);
    }
}
