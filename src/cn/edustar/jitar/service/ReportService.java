package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.Report;

/**
 * 举报服务接口。
 * @author mxh
 *
 */
public interface ReportService {
    /** 添加一个举报 */
    public void saveReport(Report report);
    
    /** 删除一个举报 */
    public void deleteReport(Report report);
    
    /** 得到一个举报  */
    public Report getReportById(int reportId);
    
    /** 更新状态 */
    public void updateReportStatus(Report report, boolean status);
}
