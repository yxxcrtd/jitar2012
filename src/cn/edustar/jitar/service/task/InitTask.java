package cn.edustar.jitar.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import cn.edustar.jitar.pojos.TimerCount;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.SiteThemeService;
import cn.edustar.jitar.service.TimerCountService;
//import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.ViewCountService;

/**
 * 初始化系统数据，如站点主题、导航数据。
 * 
 * @author mxh
 * 
 */
public class InitTask {
    private Logger log = LoggerFactory.getLogger(InitTask.class);

    private SiteThemeService siteThemeService;
    private SiteNavService siteNavService;
    private TimerCountService timerCountService;
    private ViewCountService viewCountService;

    public void setSiteThemeService(SiteThemeService siteThemeService) {
        this.siteThemeService = siteThemeService;
    }
    public void setSiteNavService(SiteNavService siteNavService) {
        this.siteNavService = siteNavService;
    }

    public void setTimerCountService(TimerCountService timerCountService) {
        this.timerCountService = timerCountService;
    }
    public void setViewCountService(ViewCountService viewCountService) {
        this.viewCountService = viewCountService;
    }
    // public void setStatService(StatService statService) {
    // this.statService = statService;
    // }
    public void init() {
        log.info("系统初始化工作开始进行……………………");
        siteThemeService.init();
        siteNavService.init();
        // 对于空库，第一次执行的时候进行一次初始化吧
        TimerCount tc = timerCountService.getTimerCountById(TimerCount.COUNT_TYPE_SITE);
        if (null == tc) {
            timerCountService.doSiteCount();
        }

        // statService.init();
       
    }

    
}
