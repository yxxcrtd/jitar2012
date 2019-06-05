package cn.edustar.jitar.service.impl;

import cn.edustar.jitar.dao.ReportDao;
import cn.edustar.jitar.pojos.Report;
import cn.edustar.jitar.service.ReportService;

/**
 * 举报服务实现类。
 * @author mxh
 *
 */
public class ReportServiceImpl implements ReportService {

    private ReportDao reportDao;
    
    @Override
    public void saveReport(Report report) {
        this.reportDao.saveReport(report);
    }

    @Override
    public void deleteReport(Report report) {
        this.reportDao.deleteReport(report);
    }

    /** 得到一个举报  */
    public Report getReportById(int reportId){
        return this.reportDao.getReportById(reportId);
    }
    
    /** 更新状态 */
    public void updateReportStatus(Report report, boolean status){
     this.reportDao.updateReportStatus(report, status);    
    }
    
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

}
