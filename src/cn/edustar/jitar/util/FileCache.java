package cn.edustar.jitar.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class FileCache {

    // cacheTime 分钟
    public String getUserFileCacheContent(String userLoginName, String fileName, int cacheTime) throws IOException {
        String filePath = getUserHtmlFolder(userLoginName);
        if (filePath == null) {
            return "无法得到用户路径。";
        }

        long interval = cacheTime * 60 * 1000L;
        filePath += fileName;
        File file = new File(filePath);
        long lastModified = file.lastModified();
        Date d = new Date();
        long current = d.getTime(); // System.currentTimeMillis();
        long diff = current - lastModified;
        if (diff > interval) {
            file = null;
            return "";
        } else {
            String _r = CommonUtil.readFile(file.getCanonicalPath(), "UTF-8");
            file = null;
            return _r;
        }
    }

    public void writeUserFileCacheContent(String userLoginName, String fileName, String content) {
        String filePath = getUserHtmlFolder(userLoginName);
        if (filePath == null) {
            return;
        }
        filePath += fileName;
        CommonUtil.saveFile(filePath, content, "UTF-8");
    }

    public void deleteUserAllCache(String userLoginName) {
        String userFolder = this.getUserHtmlFolder(userLoginName);
        if (userFolder == null)
            return;
        File file = new File(userFolder);
        File[] fs = file.listFiles();
        for (File ff : fs) {
            FileUtils.deleteQuietly(ff);
        }
        fs = null;
    }

    public void deleteUnitCacheFile(String unitName) {
        String unitFolder = this.getUnitHtmlFolder(unitName);
        if (unitFolder == null)
            return;
        File file = new File(unitFolder);
        File[] fs = file.listFiles();
        for (File ff : fs) {
            FileUtils.deleteQuietly(ff);
        }
        fs = null;
    }

    public void deleteUserArticleCate(String userLoginName) {
        String userFolder = this.getUserHtmlFolder(userLoginName);
        if (userFolder == null || userFolder.length() == 0)
            return;
        userFolder += "user_cate.html";
        File file = new File(userFolder);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
        file = null;
    }

    public void deleteUserResourceCate(String userLoginName) {
        String userFolder = this.getUserHtmlFolder(userLoginName);
        if (userFolder == null || userFolder.length() == 0)
            return;
        userFolder += "user_res_cate.html";
        File file = new File(userFolder);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
        file = null;
    }

    public void deleteUserVideoCate(String userLoginName) {
        String userFolder = this.getUserHtmlFolder(userLoginName);
        if (userFolder == null || userFolder.length() == 0)
            return;
        userFolder += "user_video_cate.html";
        File file = new File(userFolder);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
        file = null;
    }

    public void deleteUserLeavewordCache(String userLoginName) {
        String userFolder = this.getUserHtmlFolder(userLoginName);
        if (userFolder == null || userFolder.length() == 0)
            return;
        userFolder += "user_leaveword.html";
        File file = new File(userFolder);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
        file = null;
    }

    private String getUserHtmlFolder(String userLoginName) {
        String path = this.getClass().getResource("").getPath().toString();

        if (path == null) {
            System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
            return null;
        }

        File file = null;
        String root_dir = path.substring(0, path.indexOf("WEB-INF"));
        String user_dir = root_dir + "html/user/" + userLoginName + "/";
        user_dir = user_dir.replace("\\", "/");
        user_dir = user_dir.replace("/", File.separator);
        // if(user_dir.startsWith(File.separator)) user_dir =
        // user_dir.substring(1);
        // System.out.println("DEBUG2 user_dir = " + user_dir);
        try {
            user_dir = URLDecoder.decode(user_dir, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        file = new File(user_dir);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception ex) {
                System.out.println("创建文件夹 " + user_dir + " 失败：" + ex.getLocalizedMessage());
                return null;
            }
        }
        file = null;
        return user_dir;
    }

    public String getUnitHtmlFolder(String unitName) {
        String path = this.getClass().getResource("").getPath().toString();
        if (path == null) {
            System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
            return null;
        }
        File file = null;
        String root_dir = path.substring(0, path.indexOf("WEB-INF"));
        String unit_dir = root_dir + "html/unit/" + unitName + "/";
        unit_dir = unit_dir.replace("\\", "/");
        unit_dir = unit_dir.replace("/", File.separator);
        // if(unit_dir.startsWith(File.separator)) unit_dir =
        // unit_dir.substring(1);
        try {
            unit_dir = URLDecoder.decode(unit_dir, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        file = new File(unit_dir);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception ex) {
                System.out.println("创建文件夹 " + unit_dir + " 失败：" + ex.getLocalizedMessage());
                return null;
            }
        }
        file = null;
        return unit_dir;
    }

    public boolean contentIsExpired(String filePath, int cacheTime) {
        boolean expired = true;
        long interval = cacheTime * 60 * 1000L;
        File file = new File(filePath);
        long lastModified = file.lastModified();
        Date d = new Date();
        long current = d.getTime(); // System.currentTimeMillis();
        long diff = current - lastModified;
        if (diff > interval) {
            file = null;
            expired = true;
        } else {
            file = null;
            expired = false;
        }
        return expired;
    }
}
