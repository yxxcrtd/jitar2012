package cn.edustar.jitar.dao;

import java.util.Date;
import java.util.List;

import cn.edustar.jitar.pojos.ViewCount;

/**
 * DAO的接口类.
 * 
 * @author bai mindong
 */
public interface ViewCountDao extends Dao {

    /**
     * 根据ID获得记录.
     * 
     * @param Id
     * @return
     */
    public ViewCount getViewCount(int id);

    /**
     * 根据日期获得记录.
     * 
     * @param date
     * @return
     */
    public ViewCount getViewCount(int objType, int objId, Date date);

    /**
     * 获得记录
     * 
     * @param objType
     *            = 1文章 2资源.....
     * @param objGUID
     * @return
     */
    public ViewCount getViewCount(int objType, int objId);

    /**
     * 创建对象.
     * 
     * @param viewCount
     */
    public void createViewCount(ViewCount viewCount);

    /**
     * 修改对象.
     * 
     * @param viewCount
     */
    public void updateViewCount(ViewCount viewCount);

    /**
     * 删除,当该删除原始记录的时候,删除对应的记录
     * 
     * @param viewCount
     */
    public void crashViewCount(ViewCount viewCount);

    /**
     * 删除状态
     * 
     * @param objType
     * @param objGUID
     * @param del
     */
    public void changeViewCountdDelStatus(int objType, int objId, int del);

    /**
     * 统计数量(返回前多少天的数量)
     * 
     * @param days
     * @return
     */
    public List<ViewCount> getViewCountList(int objType, int days);

    /**
     * 
     * 
     * @param objType
     * @param days
     * @param topNum
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getViewCountListEx(int objType, int days, int topNum);

    /**
     * 相对增加点击率.
     * 
     * @param viewCount
     *            文章的评论对象.
     * @param count
     *            为正表示增加数量, 为 0 不操作.
     */
    public void incViewCount(ViewCount viewCount, int count);

    /**
     * 按区域获取数据
     * 
     * @param objType
     * @param days
     * @param topNum
     * @param unitPath
     * @param sharedDepth
     * @return
     */
    /*
     * @SuppressWarnings("unchecked") public List getViewCountListShared(int
     * objType, int days, int topNum, String unitPath, int sharedDepth);
     */

}
