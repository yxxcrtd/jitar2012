package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.service.CacheService;

public class TestAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1830141280403749472L;
    
    private CacheService cacheService;

    @Override
    protected String execute(String cmd) throws Exception {
        
        cacheService.remove("XXXX");
        Page page = new Page();
        page.setTitle("踩踩踩踩");
        request.getSession().invalidate();
        cacheService.put("XXXX", page);
        
        Object x = cacheService.get("XXXX");
        
        if (x == null) {
            System.out.println("写入缓存测试：");
            cacheService.put("XXXX", page);
        } else {
            System.out.println("从缓存读取：" + x.toString());
        }
        
        System.out.println("expiretime：" + cacheService.getExpireTime());
        return null;
    }

}
