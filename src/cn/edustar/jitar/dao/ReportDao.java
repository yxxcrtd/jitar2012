package cn.edustar.jitar.dao;

import cn.edustar.jitar.pojos.Report;

/**
 * 
 * 举报Dao
 * 
 */
public interface ReportDao extends Dao {

    /** 添加一个举报 */
    public void saveReport(Report report);
    
    /** 删除一个举报 */
    public void deleteReport(Report report);
    
    /** 得到一个举报  */
    public Report getReportById(int reportId);
    
    /** 更新状态 */
    public void updateReportStatus(Report report, boolean status);
}
