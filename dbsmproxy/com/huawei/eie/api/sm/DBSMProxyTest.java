package com.huawei.eie.api.sm;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * <p>Title: mas</p>
 *
 * <p>Description: <br>
 * smproxy 的示例测试类，覆盖了所有的测试接口。<br>
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * <br><br><br>
 *
 * <br><br><br>
 * <p>Company: www.huawei.com</p>
 *
 * @author   www.huawei.com
 * @version 1.0
 */
public class DBSMProxyTest
{
  public DBSMProxyTest()
  {
  }

  /**
   * 演示如何使用dbsmproxy 代码样例。
   * @throws Exception
   */
  public static final void testSendMessage()
      throws Exception
  {
    DBSMProxy smproxy = createProxy();

    testSend(smproxy,200); //发送消息测试接口。
    testGetReceivedSm(smproxy,10); //接收消息测试接口。
    testQuerysmResult(smproxy);//查询消息测试接口。

    smproxy.destroy();
  }

  /**
   * 演示如何产生一个proxy连接的代码样例
   * @return DBSMProxy
   * @throws Exception
   */
  public static final DBSMProxy createProxy()
      throws Exception
  {
    DBSMProxy smproxy = new DBSMProxy();
    Map paras = new HashMap();
    paras.put("url","jdbc:microsoft:sqlserver://10.71.99.70:1433;DatabaseName=db_customsms");
//    paras.put("driver","com.microsoft.jdbc.sqlserver.SQLServerDriver");
    paras.put("user","customsms");
    paras.put("password","123456");
    smproxy.initConn(paras);//通过设定参数初始化。
//    smproxy.initConn();//自动查找配置文件的方式。
//    smproxy.initConn("./apiconf/smapiconf.xml");//手动查找配置文件的方式。
    smproxy.login("admin", "0");
    return smproxy;
  }

  /**
   * 查询上行消息代码样例。
   * @param smproxy DBSMProxy
   * @param count int
   * @throws Exception
   */
  public static final void testGetReceivedSm(DBSMProxy smproxy, int count)
      throws Exception
  {
    SmReceiveBean[] beans =
        smproxy.getReceivedSms(count
        , new java.util.Date(System.currentTimeMillis() - 86400000)
        , new java.util.Date(System.currentTimeMillis()),"1860",null);
    System.out.println("get received sms in a day where sp number likes \'1860\',max count is"+
        count+",sms count:" + beans.length);

    beans = smproxy.getReceivedSms(count
        , new java.util.Date(System.currentTimeMillis() - 86400000)
        , new java.util.Date(System.currentTimeMillis()),null,"13");
    System.out.println("get received sms in a day where telno likes \'13\',max count is"+
        count+",sms count:" + beans.length);

    beans = smproxy.getReceivedSms(-1, null
        , null
        , null,null);
    System.out.println("get all received sms,sms count is:" + beans.length);

  }

  /**
   * 查询消息发送结果，代码样例。
   * @param smproxy DBSMProxy
   * @throws Exception
   */
  public static final void testQuerysmResult(DBSMProxy smproxy)
      throws Exception
  {
    SmSendResultBean[] beans =
        smproxy.querySmsResult(-1
        , new java.util.Date(System.currentTimeMillis() - 86400000*30L)
        , new java.util.Date(System.currentTimeMillis())
        ,null,null);
    for (SmSendResultBean bean:beans)
    {
      System.out.println("bean:"+bean.getSmMsgContent());
//      System.out.println("SmSendResultBean:"+bean);
//      System.out.println("content:"+bean.getSmMsgContent());
    }
  }


  /**
   * 发送测试代码样例。
   * @param smproxy DBSMProxy
   * @param count int
   * @throws Exception
   */
  public static final void testSend(DBSMProxy smproxy, int count)
      throws Exception
  {
    SmSendBean bean = new SmSendBean();
    ArrayList<String> arrs = new ArrayList();
    for (int index = 0; index < count; index++)
    {
      arrs.add("1380000" + index);
    }
    String[] addrs = new String[arrs.size()];
    arrs.toArray(addrs);
    bean.setSmDestAddrs(addrs);
    bean.setSmMsgContent("sms content test info...~!@#$%^&*()_+|..");
    long firstTime = System.currentTimeMillis();
    //int[] ret = smproxy.SendSms(bean);
    int[] ret = smproxy.sendSm(bean);		 
    long lastTime = System.currentTimeMillis();
    System.out.println("======================================");
    System.out.println(" inserted:" + count + " records,consumed " + (lastTime
        - firstTime) + " ms");
    System.out.println(" avg Speed:" + (count) * 1000.0 / (lastTime - firstTime));
    System.out.println("======================================");
    System.out.print("returned sm_id(s):");
    for (int index = 0; index < ret.length; index++)
    {
      System.out.print(" " + ret[index]);
    }
    System.out.println("");
  }

  /**
   * 测试方法主入口。
   *
   * @param args String[]
   * @throws Exception
   */
  public static void main(String[] args)
      throws Exception
  {
    testSendMessage();
  }
}
