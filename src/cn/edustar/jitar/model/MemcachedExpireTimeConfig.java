package cn.edustar.jitar.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.jitar.util.CommonUtil;

/**
 * Memcached 缓存时间的设置.
 * 
 * @author mxh
 * 
 */
public class MemcachedExpireTimeConfig implements TemplateModelObject {
    
    private Logger log = LoggerFactory.getLogger(MemcachedExpireTimeConfig.class);

    @Override
    public String getVariableName() {
        return "MemcachedExpireTimeConfig";
    }

    public void init() {
        getPropertyFromFile();
    }
    //默认300秒
    private static String siteIndexExpireTime = "300";
    public static int getSiteIndexExpireTime() {
        if (!CommonUtil.isInteger(siteIndexExpireTime)) {
            return 300;
        } else {
            return Integer.valueOf(siteIndexExpireTime).intValue();
        }
    }

    /**
     * 从配置文件中读取配置。
     */
    private void getPropertyFromFile() {
        Properties props = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("memcachedExpireTimeConfig.properties");

        // 如果文件不存在，则
        if (inputStream == null) {
            log.error("不能加载配置文件  memcachedExpireTimeConfig.properties");
            return;
        }
        try {
            props.load(inputStream);
            siteIndexExpireTime = props.getProperty("site_index_expire_time");
        } catch (IOException e) {
            log.error("读取配置文件  memcachedExpireTimeConfig.properties 出现错误。" + e.getLocalizedMessage());
            return;
        } finally {
            inputStream = null;
            props = null;
        }
    }
}
