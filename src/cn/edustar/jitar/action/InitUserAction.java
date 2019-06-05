package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.ProductConfigService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;

public class InitUserAction  extends AbstractServletAction{

    private static final long serialVersionUID = 3441240417768093738L;
    /** 分类服务 */
    private CategoryService categoryService;

    /** 配置服务 */
    private ConfigService configService;
    
    /** 用户服务 */
    private UserService userService;

    /** 学科服务 */
    private SubjectService subjectService;

    /** 区县、机构服务 */
    private UnitService unitService;
    
    
    /** 权限服务 */
    private AccessControlService accessControlService;

    private ProductConfigService productConfigService;


    /** 记录错误或者提示信息 */
    private List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();
  
    private String cmd = null;
 
    public String execute() {
        if (cmd == null) {
            cmd = "";
        }
        //System.out.println("cmd=" + cmd);
        if (cmd.equals("save")) {
            return save();
        } else {
            User user = this.getLoginUser();
            if (user == null) {
                this.addActionError("请重新登录。");
                return ERROR;
            }

            // 得到学科列表；年级列表；区县列表；机构列表
            //super.putMetaSubjectList();
            //super.putGradeList();

            // 将'系统分类树'放到'request'中
            putSysCategoryToRequest();

            request.setAttribute("user", user);
            return INPUT;
        }
    }

    private String save() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 将'系统分类树：syscate_tree'放到'request'中
     */
    private void putSysCategoryToRequest() {
        Object syscate_tree = categoryService.getCategoryTree(CategoryService.BLOG_CATEGORY_TYPE);
        setRequestAttribute("syscate_tree", syscate_tree);
    }

    public UserService getUserService() {
        return userService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    public UnitService getUnitService() {
        return unitService;
    }
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    public void setProductConfigService(ProductConfigService productConfigService) {
        this.productConfigService = productConfigService;
    }

}
