package cn.demo.test;

import java.net.MalformedURLException;

import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Message;

import cn.edustar.remoting.hessian.client.DataSender;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;

public class HTest {

    @SuppressWarnings({"rawtypes", "unused"})
    public static void main(String[] args){
       /* GroupMember gm = new GroupMember();
        gm.setTeacherXL("@23@#$%^&*()<>/』『【】{}[];'嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻");
        
        
        String url = "http://localhost:8080/jitar2012/manage/remoting/unitChannelService?aaa=xxxx";
        String data = "{\"methodName\":\"Add\",\"dataBody\":\"测试啊\"}";
        DataSender ds = new DataSender();
        ds.setServiceUrl(url);
        ds.setMethodName("Message");
        ds.setDataBody(JSON.toJSONString(gm));
        String result = ds.send();
        System.out.println("result=" + result);
        System.out.println("code=" + ds.getResultCode());
        System.out.println("message=" + ds.getResultMessage());*/
        
        /*HessianProxyFactory factory = new HessianProxyFactory();
        try {
            IBaseChannelService dataChannelService = (IBaseChannelService)factory.create(IBaseChannelService.class, url);
            String result = dataChannelService.sendData(AESEncryptUtil.encrypt(data,"ABCD_?<:1234efgh","1234567812345678"));
            System.out.println("Back:" + result);
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        
     /*
         gm = new GroupMember();
        gm.setTeacherXL("@23@#$%^&*()<>/』『【】{}[];'嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻");
        GroupChannel gc = new GroupChannel();
        gc.setUrl(url);
        gc.setEncrypted(true);
        gc.setPassword("chinaedustar");
        gc.addGroupMember(gm);*/
       
      
        
  /*String url = "http://servername/manage/remoting/dataChannelService";
  //实例化数据转发器
  DataSender dataSender = new DataSender();

  //设置接收端的Url，如果是发送到统一的数据中心，可以不设置此参数
  dataSender.setUrl(url);

  //设置发送的对象类型，接收端根据这个设置来决定处理的分支
  dataSender.setObjectType("Message");

  //设置对象要调用的方法，
  dataSender.setMethodName("Add");

  //设置是否加密传输数据
  dataSender.setEncrypted(true);

  //设置要加密的密钥，注意，密钥的存放将采取一种机制，一般不用设置，要设置的话，必须发送端与接收端一致，由于是多平台系统，可能要采用一种默认的算法实现。
  dataSender.setPassword("chinaedustar");

  //创建一个实例对象，这里以站内消息为例
  Message m = new Message();
  m.setId(100);
  m.setContent("测试的信息");        

  //发送数据，如果需要返回数据(如查询)，则返回要返回数据，格式为json格式，
  @SuppressWarnings("unchecked")
  String result = dataSender.send(m);

  //根据返回的状态码判断是否成功。成功的状态码是0
  if(dataSender.getResultCode() == 0){
   System.out.println("操作成功，进行其他处理。");
  }
  else{
   //发送失败，失败原因
   System.out.println("发送失败，失败原因:" + dataSender.getResultMessage());
  }
  dataSender = null;
*/
          
        
        /*DataSender ds = new DataSender(url);
        ds.setObjectType("AAA");
        ds.setMethodName("dekete");
        Message m = new Message();
        m.setId(100);
        m.setContent("测试的信息");        
        String d = ds.send(m);
        if(getResultCode() == 0){
         System.out.println(ds.getResultMessage());
        }
        else{
         System.out.println(ds.getResultMessage());
        }
        
        System.out.println(ds.getResultCode());
        System.out.println(ds.getResultMessage());
        
        ds = new DataSender(url);
        ds.setObjectType("BBBB");
        ds.setMethodName("Add");
        ds.setEncrypted(true);
        d = ds.send(m);
        System.out.println(ds.getResultCode());
        System.out.println(ds.getResultMessage());*/
        
        
       /* String data = "{\"data\":\"hello\",\"methodName\":\"dekete\",\"objectType\":\"AAA\"}";
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            DataChannelService dataChannelService = (DataChannelService)factory.create(DataChannelService.class, url);
            String result = dataChannelService.sendData(data);
            System.out.println("Back:" + result);
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }
}
