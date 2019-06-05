package cn.demo.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.User;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemCacheTest {
    public static void main(String[] args) throws IOException {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.2.188:11277"));
        builder.setCommandFactory(new BinaryCommandFactory());// 使用二进制协议
        MemcachedClient memcachedClient = builder.build();
        try {
            
            User user = new User();
            user.setLoginName("下次测试啊下次测试啊下次测试啊下次测试啊");
            
            
            Article article = new Article();
            article.setId(100);
            article.setTitle("测试顶顶顶顶顶顶");
            
            
            memcachedClient.set("user", 0, user);
        
            memcachedClient.set("article", 0, article);

            System.out.println("第一次得到 user=" + (User) memcachedClient.get("user"));
            System.out.println("第一次得到 article=" + (Article) memcachedClient.get("article"));
            
            memcachedClient.delete("article");
            System.out.println("第2次得到 article=" + (Article) memcachedClient.get("article"));
            
            System.out.println("x" + memcachedClient.get("article_E33D1DD84DB8CACF7C4B375324C5A32A"));
          
        } catch (MemcachedException e) {
            System.err.println("MemcachedClient operation fail");
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.err.println("MemcachedClient operation timeout");
            e.printStackTrace();
        } catch (InterruptedException e) {
            // ignore
        }
        try {
            // close memcached client
            memcachedClient.shutdown();
        } catch (IOException e) {
            System.err.println("Shutdown MemcachedClient fail");
            e.printStackTrace();
        }
    }
}
