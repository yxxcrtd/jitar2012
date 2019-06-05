package cn.edustar.jitar.query.sitefactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.service.TimerCountService;

/**
 * <p>
 * 实现生成静态页面的功能。
 * </p>
 * 
 * @author mxh
 * 
 */
public class SiteFactory {

    private Logger log = LoggerFactory.getLogger(SiteFactory.class);
    private HtmlGeneratorService htmlGeneratorService;
    private GeneratorSevenDaysCommentArticleService generatorSevenDaysCommentArticleService;
    private TimerCountService timerCountService;

    public void Html() {
        log.info("开始进行站点统计………………………………");
        timerCountService.doSiteCount();
        log.info("开始生成静态文件………………………………");
        log.info("执行 DeleteOldViewCount");
        generatorSevenDaysCommentArticleService.DeleteOldViewCount();
        log.info("执行 UpdateSevenDaysCommentArticle");
        generatorSevenDaysCommentArticleService.UpdateSevenDaysCommentArticle();
        log.info("执行 UpdateSevenDaysViewCountArticle");
        generatorSevenDaysCommentArticleService.UpdateSevenDaysViewCountArticle();
        // 新版本不进行静态化
        // log.info("执行 SiteIndex");
        // htmlGeneratorService.SiteIndex();

    }
    public void destroy() {
    }

    public void setHtmlGeneratorService(HtmlGeneratorService htmlGeneratorService) {
        this.htmlGeneratorService = htmlGeneratorService;
    }

    public void setGeneratorSevenDaysCommentArticleService(GeneratorSevenDaysCommentArticleService generatorSevenDaysCommentArticleService) {
        this.generatorSevenDaysCommentArticleService = generatorSevenDaysCommentArticleService;
    }
    public void setTimerCountService(TimerCountService timerCountService) {
        this.timerCountService = timerCountService;
    }

}