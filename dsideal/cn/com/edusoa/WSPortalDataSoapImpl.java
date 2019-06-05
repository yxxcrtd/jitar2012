
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package cn.com.edusoa;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.GroupDao;
import cn.edustar.jitar.dao.PrepareCourseDao;
import cn.edustar.jitar.dao.ResourceDao;
import cn.edustar.jitar.dao.UserDao;
import cn.edustar.jitar.dao.hibernate.GroupDaoHibernate;
import cn.edustar.jitar.dao.hibernate.PrepareCourseDaoHibernate;
import cn.edustar.jitar.dao.hibernate.ResourceDaoHibernate;
import cn.edustar.jitar.dao.hibernate.UserDaoHibernate;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;

/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T15:42:12.854+08:00
 * Generated source version: 2.7.5
 * 
                      wsdlLocation = "file:/D:/2013-05--30/a/WS_PortalData.wsdl",
 */

@javax.jws.WebService(
                      serviceName = "WS_PortalData",
                      portName = "WS_PortalDataSoap",
                      targetNamespace = "http://edusoa.com.cn/",
                      endpointInterface = "cn.com.edusoa.WSPortalDataSoap")
public class WSPortalDataSoapImpl implements WSPortalDataSoap {
	@Resource 
	private WebServiceContext context;
	@Resource
	private UserDao userDao;
	@Resource
	private ResourceDao resourceDao;
	@Resource
	private PrepareCourseDao prepareCourseDao;
	@Resource
	private GroupDao groupDao;	 
	
    private static final Logger LOG = Logger.getLogger(WSPortalDataSoapImpl.class.getName());

    /* (non-Javadoc)
     * @see cn.com.edusoa.WSPortalDataSoap#getDataList(java.lang.String  userId ,)java.lang.String  dataType ,)int  dataNum ,)java.lang.String  userToken ,)java.lang.String  timeStamp )*
     */
    public cn.com.edusoa.DataListResult getDataList(java.lang.String userId,java.lang.String dataType,int dataNum,java.lang.String userToken,java.lang.String timeStamp) { 
        LOG.info("Executing operation getDataList");
        System.out.println(userId);
        System.out.println(dataType);
        System.out.println(dataNum);
        System.out.println(userToken);
        System.out.println(timeStamp);
        //�������
        if(null == userId || userId.length()==0){
        	return null;
        }
        //dataType   1.���뱸�Σ�2.���𱸿Σ�3.�ҵ���Դ��4.�ҵ�Э��
        if(null == dataType || dataType.length()==0){
        	dataType = "3";	//Ĭ�����ҵ���Դ
        }
        if(dataNum == 0){
        	dataNum = 10;	//Ĭ��10��
        }
        
        if(userToken != null && userToken.length() > 0){
        	//TODO:��֤�û����ʿ���
        	//�����޷�֪�����룬����ͨ������MD5��������֤Ʊ֤
        	
        }
        try{
	        MessageContext ctx = context.getMessageContext();
	        HttpServletRequest request = (HttpServletRequest)ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
	        //HttpSession session = request.getSession();
	        
			String WebRoot = request.getScheme() + "://" + request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":"
					+ request.getServerPort()) + request.getContextPath()
					+ "/";
			
	        cn.com.edusoa.DataListResult _return = null;
	        String hql;
	        User u = userDao.getByAccountId(userId);
	        if(null == u){
	        	System.out.println("û���ҵ����û�[AccountId="+ userId +"]��Ϣ");
	        	LOG.info("û���ҵ����û�[AccountId="+ userId +"]��Ϣ");
	        	return null;
	        }
	        if (dataType.equals("1")){			//���뱸��
	        	_return = GetJoinPrepareCourse(u , WebRoot, dataNum);
	        }else if (dataType.equals("2")){		//���𱸿�
	        	_return = GetCreatePrepareCourse(u , WebRoot, dataNum);
	        	
	        }else if (dataType.equals("3")){		//�ҵ���Դ
	        	_return = GetResource(u , WebRoot, dataNum);
	        }else if (dataType.equals("4")){		//�ҵ�Э��[�Ҽ����Э����]
	        	_return = GetGroup(u , WebRoot, dataNum);
	        }
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    private cn.com.edusoa.DataListResult GetGroup(User u ,String WebRoot,int dataNum)
    {
    	cn.com.edusoa.DataListResult _return = null;
    	List<Object[]> list= groupDao.getMyJoinedGroupList(u.getUserId());
    	if(null == list){
    		return null;
    	}
    	ArrayOfString titles = new ArrayOfString();  
    	ArrayOfString nums = new ArrayOfString();
    	ArrayOfString urls = new ArrayOfString();
    	
    	for(int i = 0;i < dataNum;i++){
    		if(i >= list.size()){
    			break;
    		}
    		Object[] os = list.get(i);
    		Group g = (Group)os[0];
    		
    		titles.getString().add(g.getGroupTitle());
    		nums.getString().add(""+g.getUserCount());
    		urls.getString().add(WebRoot + "go.action?groupId="+g.getGroupId());
    	}
    	_return = new cn.com.edusoa.DataListResult();
    	_return.setResultTitle1("Э�������");
    	_return.setColumnTitle1(titles);
    	_return.setColumnUrl1(urls);
    	
    	_return.setResultTitle2("��Ա");
    	_return.setColumnTitle2(nums);
    	
    	_return.setResultUrl(WebRoot + "u/"+ u.getLoginName() +"/py/user_group_list.py");	 
    	return _return;
    }
    private cn.com.edusoa.DataListResult GetResource(User u ,String WebRoot,int dataNum)
    {
    	cn.com.edusoa.DataListResult _return = null;
    	Pager pager = new Pager();
		pager.setCurrentPage(1);
		pager.setPageSize(dataNum);
		pager.setUrlPattern("");
		pager.setItemNameAndUnit("��Դ", "��");
		ResourceQueryParam param = new ResourceQueryParam();
		param.userId = u.getUserId();
		param.k = null;
		param.userCateId = null;
		param.retrieveUserCategory = true;
		param.retrieveSystemCategory = true;
		param.shareMode = null;
		param.auditState= null;		
		param.delState = null;
		List<cn.edustar.jitar.pojos.Resource> list = resourceDao.getResourceList(param, pager);
    	
    	ArrayOfString titles = new ArrayOfString();  
    	ArrayOfString uploaddates = new ArrayOfString();
    	ArrayOfString urls = new ArrayOfString();
    	
    	String fmt = "";
    	fmt = "MM-dd HH:mm";
    	SimpleDateFormat sdf = new SimpleDateFormat(fmt);
    	
    	for(int i = 0;i < dataNum;i++){
    		if(i >= list.size()){
    			break;
    		}
    		cn.edustar.jitar.pojos.Resource res = list.get(i);
    		titles.getString().add(res.getTitle());
    		uploaddates.getString().add(sdf.format(res.getCreateDate()));
    		urls.getString().add(WebRoot + "showResource.py?resourceId="+res.getResourceId());
    	}
    	_return = new cn.com.edusoa.DataListResult();
    	
    	_return.setResultTitle1("��Դ����");
    	_return.setColumnTitle1(titles);
    	_return.setColumnUrl1(urls);
    	
    	_return.setResultTitle2("����ʱ��");
    	_return.setColumnTitle2(uploaddates);
    	
    	_return.setResultUrl(WebRoot + "u/" + u.getLoginName() + "/rescate/0.html");   
    	return _return;
    }
    private cn.com.edusoa.DataListResult GetCreatePrepareCourse(User u ,String WebRoot,int dataNum)
    {
    	cn.com.edusoa.DataListResult _return = null;
    	List<PrepareCourse> pcs =prepareCourseDao.getPrepareCourseListByCreateUserId(u.getUserId());
    	if(null == pcs){
    		return null;
    	}
    	ArrayOfString titles = new ArrayOfString();  
    	ArrayOfString urls = new ArrayOfString();
    	
    	for(int i = 0;i < dataNum;i++){
    		if(i >= pcs.size()){
    			break;
    		}
    		PrepareCourse pc = pcs.get(i);
    		titles.getString().add(pc.getTitle());
    		urls.getString().add(WebRoot + "p/" + pc.getPrepareCourseId() + "/0/");
    	}
    	_return = new cn.com.edusoa.DataListResult();
    	_return.setResultTitle1("���α���");
    	_return.setColumnTitle1(titles);
    	_return.setColumnUrl1(urls);
    	_return.setResultUrl(WebRoot + "u/"+ u.getLoginName() +"/py/user_preparecourse_list.py");   
    	return _return;
    }
    private cn.com.edusoa.DataListResult GetJoinPrepareCourse(User u ,String WebRoot,int dataNum)
    {
    	cn.com.edusoa.DataListResult _return = null;
    	List<PrepareCourse> pcs = prepareCourseDao.getPrepareCourseListByJoinUserId(u.getUserId());
    	if(null == pcs){
    		return null;
    	}
    	ArrayOfString titles = new ArrayOfString();  
    	ArrayOfString urls = new ArrayOfString();
    	
    	for(int i = 0;i < dataNum;i++){
    		if(i >= pcs.size()){
    			break;
    		}
    		PrepareCourse pc = pcs.get(i);
    		titles.getString().add(pc.getTitle());
    		urls.getString().add(WebRoot + "p/" + pc.getPrepareCourseId() + "/0/");
    	}
    	_return = new cn.com.edusoa.DataListResult();
    	_return.setResultTitle1("���α���");
    	_return.setColumnTitle1(titles);
    	_return.setColumnUrl1(urls);
    	_return.setResultUrl(WebRoot + "manage/user_manage.py");    	
    	return _return;
    }
}
