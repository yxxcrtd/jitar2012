package cn.edustar.jitar.dao.hibernate;

import cn.edustar.jitar.dao.ReportDao;
import cn.edustar.jitar.pojos.Report;

public class ReportDaoHibernate extends BaseDaoHibernate implements ReportDao {

    @Override
    public void saveReport(Report report) {
        this.getSession().save(report);
    }

    @Override
    public void deleteReport(Report report) {
        this.getSession().delete(report);
    }

    /** 得到一个举报 */
    public Report getReportById(int reportId) {
        return (Report) this.getSession().get(Report.class, reportId);
    }
    
    /** 更新状态 */
    public void updateReportStatus(Report report, boolean status){
        if(null == report) return;
        String hql = "UPDATE Report SET status = :status WHERE reportId=:reportId";
        this.getSession().createQuery(hql).setBoolean("status",status).setInteger("reportId", report.getReportId()).executeUpdate();
    }

    @Override
    public void evict(Object object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

}
