package cn.edustar.jitar.manage;

//import cn.edustar.jitar.pojos.UserOnLine;
import cn.edustar.jitar.pojos.UserOnLineStat;

/**
 * 在线管理接口（没有事务）
 * 
 * @author Yang XinXin
 */
public interface OnlineManage {
    
//  public void saveUserOnLine(UserOnLine userOnLine);
//  
//  public UserOnLine findUserOnLineByUserName(String userName);
    
    
    /**
     * 如果不存在记录。则需要添加一条记录到数据库。
     * @param userOnLineStat
     */
    public void saveUserOnLineStat(UserOnLineStat userOnLineStat);
    
    
    /**
     * 得到用户在线统计对象
     * 
     * @return
     */
    public UserOnLineStat getUserOnLineStat();
    
    /**
     * 更新用户在线的统计
     * 
     * @param count - 新的最高记录
     */
    public void updateOnLineStat(int count);

}
