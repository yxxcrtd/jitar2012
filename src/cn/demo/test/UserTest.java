package cn.demo.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import cn.edustar.jitar.model.ObjectType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.octopus.sso.model.User;
import com.octopus.sso.service.UserService;
import com.octopus.system.model.Unit;
import com.octopus.system.service.DicManageService;
import com.octopus.system.service.UnitManageService;

public class UserTest {

    // 临时使用，需要从配置文件中读取
    private static String userUrl = "http://192.168.3.133:8080/octopus/hessian/userService";
    private static String unitUrl = "http://192.168.3.233:8080/octopus/hessian/unitManageService";
    private static String dicUrl = "http://192.168.3.42:8080/octopus/hessian/dicManageService";
    
    private static String getLeftCountedString(String inStr, int count){
        if(inStr == null || inStr.length() == 0 || inStr.contains("|") == false) return inStr;
        String[] arr = inStr.split("\\|");
        int arrLength = arr.length;
        String outStr = "";
        if(arrLength > count && count > 0){
            for(int i=0;i<count+1;i++){
                outStr += arr[i] + "|";
            }
        }else
        {
            return inStr;
        }
        return  outStr;
    }
    public static void main(String[] args) throws IOException {
       
      
        
       /* String r = "http://www.jitar.com.cn:8080/jitar2/photos.action?sss=x#33333&t=adbddbaa-497a-4856-bea7-bea40037c84c";
        if (r.contains("#") == false)
            return;
        String part1 = r.substring(0, r.indexOf("#"));
        String s1 = r.substring(r.indexOf("#") + 1);
        System.out.println("s1=" + s1);
        String hash = "";
        for (int i = 0; i < s1.length(); i++) {

            if (s1.charAt(i) == '?' || s1.charAt(i) == '&') {
                break;
            }
            hash += String.valueOf(s1.charAt(i));
        }
        String part2 = s1.substring(hash.length());

        System.out.println("part1=" + part1);
        System.out.println("part2=" + part2);
        System.out.println("hash=" + hash);
        String ret = part1 + part2;
        if (hash.length() > 0) {
            ret += "#" + hash;
        }
        System.out.println("ret=" + ret);
*/
        /*
         * try { HessianProxyFactory factory = new HessianProxyFactory();
         * UnitManageService unitManageService = (UnitManageService)
         * factory.create(UnitManageService.class, unitUrl); Unit u =
         * unitManageService.getByUnitCode("AAAAAA");
         * System.out.println(u.getUnitName()); } catch (Exception e) {
         * //e.printStackTrace();
         * System.out.println("********************用户管理系统不可用**********************"
         * ); }
         */

        /*
         * String isLive = null; try { HessianProxyFactory factory = new
         * HessianProxyFactory(); UserService userService = (UserService)
         * factory.create(UserService.class, userUrl); isLive =
         * userService.checkIsLive(); } catch (Exception e) {
         * //e.printStackTrace();
         * System.out.println("********************用户管理系统不可用**********************"
         * ); }
         * 
         * System.out.println("isLive=" + isLive);
         */

        /*
         * HessianProxyFactory factory = new HessianProxyFactory();
         * factory.setConnectTimeout(5000L); // 毫秒
         * 
         * try { DicManageService dicManageService = (DicManageService)
         * factory.create(DicManageService.class, dicUrl); String ret =
         * dicManageService.showDics("2"); System.out.println("ret=" + ret); }
         * catch (Exception ex) { ex.printStackTrace();
         * System.out.println("传输错误" + ex.getLocalizedMessage()); }
         */

        /*
         * JSONArray reportTypeName =
         * JSON.parseArray("[{\"1\":\"AA\"},{\"2\":\"bb\"}]"); for(Object x :
         * reportTypeName){ System.out.println("x=" +x.getClass()); } if(true)
         * return; String docBase = "\\\\192.168.3.186\\FCS_VIDEO";
         * 
         * File base = new File(docBase); try { base = base.getCanonicalFile();
         * } catch (IOException e) { // Ignore }
         * System.out.println(base.exists() );
         * System.out.println(base.isDirectory() );
         * System.out.println(base.canRead());
         * 
         * // 添加用户 // addUserTest();
         * 
         * // 修改用户 updateUserTest();
         */
        // 用户登录
        // 参见网站的 login.htm

        // 注销登录
        // http://192.168.3.133:8080/octopus/logout.action?clientCode=beijing&backUrl=

        // 修改密码采用接口的updateUserRemote(userJon)方法

        // 添加机构
        // addUnit();

        // 验证票证
        // validateST();

        // getUnit();

    }

    private String moveHashToEnd(String r) {
        if (r.contains("#") == false) {
            return r;
        }
        String part1 = r.substring(0, r.indexOf("#"));
        String s1 = r.substring(r.indexOf("#") + 1);
        System.out.println("s1=" + s1);
        String hash = "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == '?' || s1.charAt(i) == '&') {
                break;
            }
            hash += String.valueOf(s1.charAt(i));
        }
        String part2 = s1.substring(hash.length());
        String ret = part1 + part2;
        if (hash.length() > 0) {
            ret += "#" + hash;
        }
        return ret;
    }

    private static void getUnit() {
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        com.octopus.system.model.Unit ssoUnit = null;
        try {
            UnitManageService unitManageService = (UnitManageService) factory.create(UnitManageService.class, unitUrl);
            ssoUnit = unitManageService.getById(String.valueOf(51));
            System.out.println("ssoUnit=" + ssoUnit);
        } catch (Exception ex) {
            System.out.println("传输错误");
        }
        if (null == ssoUnit) {
            System.out.println("null");
        }
    }

    private static void validateST() {
        System.out.println("***************** 票证验证测试  *****************");

        // 每次票证都是变化的，测试环境下1分钟超时。
        String ticket = "ST-7-m2JqSbochef4MaHQp26HLBia7B7tLv";

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;
        try {
            UserService userService = (UserService) factory.create(UserService.class, userUrl);

            // getUserinfoByST(arg0,arg1)参数说明：
            // arg0是登录时使用的backurl，arg1是票证信息，登录返回的ticket参数。
            result = userService.getUserinfoByST("http://www.jitar.com.cn:8080/index.action", ticket);
            // 返回值是用户对象的json格式
            User user = JSON.parseObject(result, User.class);
            System.out.println("user=" + user.getTrueName());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("传输错误");
        }
        // result={"base64UserIcon":"","email":"xiaoguanglee@163.com","fromSysCode":"0","fromSysName":"","gender":1,"jobNo":"1001","nickName":"admin","nodeCode":"1005","passportId":"402883694005724d014005864c560000","password":"e10adc3949ba59abbe56e057f20f883e","registerTime":null,"trueName":"管理员","unitId":1,"unitName":"中国教育部","userIcon":"upload/s_20130809162047343.jpg","userIcon140":"","userIcon50":"","userName":"admin","userStatus":2,"userType":"2","version":27}

        System.out.println("result=" + result);

    }
    private static void addUnit() {
        System.out.println();
        System.out.println("***************** 添加机构测试  *****************");
        String unitEName = UUID.randomUUID().toString().replaceAll("-", "");
        // unitEName = "AAA";
        Unit unit = new Unit();
        unit.setChildCount(0);
        unit.setParentUnitCode(1);
        unit.setUnitCode("AAAAAA");
        unit.setUnitEName(unitEName);
        unit.setUnitName("测试机构");
        unit.setUnitStatus(1);
        unit.setUnitType(1);
        unit.setVersionNo(1);
        unit.setWebSiteName("皇后区教研平台");

        String unitJson = JSON.toJSONString(unit);

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;

        try {
            UnitManageService unitManageService = (UnitManageService) factory.create(UnitManageService.class, unitUrl);
            result = unitManageService.addUnitRemote(unitJson);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("传输错误");
        }

        System.out.println("unitJson=" + unitJson);
        System.out.println("result=" + result);

    }
    private static void addUserTest() {
        System.out.println("***************** 开始进行添加用户的测试  *****************");
        String base64UserIcon = com.chinaedustar.util.PicProcessUtil.getPicBASE64("d:\\tx1.jpg");
        String userName = UUID.randomUUID().toString().replaceAll("-", "");
        User user = new User();
        user.setUserName(userName);
        user.setTrueName("真实姓名");
        user.setNickName("昵称");
        user.setUnitId(11);
        user.setUnitName("单位名称");
        user.setPassword("password");
        user.setNodeCode("100000");
        user.setUserType("2");
        user.setJobNo("jobNo");
        user.setEmail("aa@aaa.com");
        user.setGender(1);
        user.setVersion(1);
        user.setUserStatus(1);
        user.setFromSysName("教研平台");
        user.setFromSysCode("88888");
        // 目前的做法，如果使用base64UserIcon，则必须传文件名，以后可能不需要。
        user.setBase64UserIcon(base64UserIcon);
        user.setUserIcon("tx1.jpg");
        String userJson = JSON.toJSONString(user);

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;

        try {
            UserService userService = (UserService) factory.create(UserService.class, userUrl);
            result = userService.saveUserRemote(userJson);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("传输错误");
        }

        System.out.println("userJson=" + userJson);
        System.out.println("result=" + result);
    }

    private static void updateUserTest() {
        System.out.println("***************** 修改用户的测试  *****************");
        String passportId = "40288305405bc90001405d135c310008"; // 用户的id
        User user = new User();
        user.setPassportId(passportId);
        user.setUserName("登录名可以修改？");
        user.setTrueName("修改的真实姓名");
        user.setNickName("修改的昵称");
        user.setUnitId(11);
        user.setUnitName("修改的单位名称");
        user.setPassword("password");
        user.setNodeCode("100000");
        user.setUserType("2");
        user.setJobNo("jobNo");
        user.setEmail("newaa@aaa.com");
        user.setGender(1);
        user.setVersion(1);
        // user.setUserIcon(userIcon);
        // user.setBase64UserIcon(userIcon);
        String userJson = JSON.toJSONString(user);

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;
        try {
            UserService userService = (UserService) factory.create(UserService.class, userUrl);
            result = userService.updateUserRemote(userJson);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("传输错误");
        }

        System.out.println("userJson=" + userJson);
        System.out.println("result=" + result);

    }

}
