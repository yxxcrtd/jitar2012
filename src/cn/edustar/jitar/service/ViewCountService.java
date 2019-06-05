package cn.edustar.jitar.service;

import java.util.Date;
import java.util.List;
import cn.edustar.jitar.pojos.ViewCount;

/**
 * @author Yang Xinxin
 */
public interface ViewCountService {

    /**
     * 根据ID获得记录
     * 
     * @param Id
     * @return
     */
    public ViewCount getViewCount(int id);

    /**
     * 根据日期获得记录
     * 
     * @param date
     * @return
     */
    public ViewCount getViewCount(int objType, int objId, Date date);

    /**
     * 创建对象
     * 
     * @param viewCount
     */
    public void createViewCount(ViewCount viewCount);

    /**
     * 修改对象
     * 
     * @param viewCount
     */
    public void updateViewCount(ViewCount viewCount);

    /**
     * 更改删除状态
     * 
     * @param objType
     * @param objGUID
     * @param del
     */
    public void changeViewCountdDelStatus(int objType, int objId, int del);

    /**
     * 删除,当该删除原始记录的时候,删除对应的记录
     * 
     * 
     * @param viewCount
     */
    public void deleteViewCount(ViewCount viewCount);

    /**
     * @param objType
     * @param objGUID
     */
    public void deleteViewCount(int objType, int objId);

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
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List getViewCountListEx(int objType, int days, int topNum);

    /**
     * 相对增加点击率
     * 
     * @param viewCount
     *            文章的评论对象.
     * @param count
     *            为正表示增加数量, 为 0 不操作.
     */
    public void incViewCount(ViewCount viewCount, int count);

    /**
     * 
     * @param objType
     * @param objGUID
     * @param count
     */
    public void incViewCount(int objType, int objId, int count);

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
