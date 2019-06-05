package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.PlacardService;

/**
 * 群组公告模块.
 * 
 */
public class GroupPlacardModule extends AbstractModuleWithTP {
    /** 公告服务 */
    private PlacardService pla_svc;

    /**
     * 构造.
     */
    public GroupPlacardModule() {
        super("group_placard", "协作组公告");
    }

    /** 公告服务 */
    public void setPlacardService(PlacardService pla_svc) {
        this.pla_svc = pla_svc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.
     * ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
     */
    public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
        // 得到组.
        Group group = (Group) request.getAttribute(ModuleRequest.GROUP_MODEL_KEY);
        // 得到组公告.
        // TODO: 3 -- CONFIG
        List<Placard> placard_list = pla_svc.getRecentPlacard(ObjectType.OBJECT_TYPE_GROUP, group.getGroupId(), 3);
        // 组装为XHTML.
        outputHtmlResult(group, placard_list, response);
    }

    // 产生 XHTML 结果，这要求 title, content 都是良好 html 格式.
    private void outputHtmlResult(Group group, List<Placard> placard_list, ModuleResponse response) throws IOException {
        HashMap<String, Object> root_map = new HashMap<String, Object>();
        root_map.put("group", group);
        root_map.put("placard_list", placard_list);

        String template_name = "/WEB-INF/group/default/group_placard.ftl";

        response.setContentType(Module.TEXT_HTML_UTF_8);
        processTemplate(root_map, response.getOut(), template_name);
    }
}
