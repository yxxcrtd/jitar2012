package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.pojos.Unit;

/**
 * 显示各种结构的机构列表。
 * 
 * 注：本程序只是直接从py转过来的，没有做其他优化。
 * @author mxh
 * 
 */
public class SiteUnitsAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 8082246155622664115L;
    
    private String unit_main_url = "go.action?unitName=";

    @Override
    public String execute(String cmd) throws Exception {
        if (cmd.equals("ajax")) {
            this.getUnitDataList();
            return NONE;
        }

        request.setAttribute("head_nav", "units");
        Unit rootUnit;
        int rootId;
        // 显示单位，至少 ConfigUnitLevel=2
        int ConfigUnitLevel = unitService.getConfigUnitLevel();
        if (ConfigUnitLevel == 1) {
            response.sendRedirect("index.action");
            return NONE;
        } else if (ConfigUnitLevel == 2) {
            int pid;
            rootUnit = unitService.getRootUnit();
            if (null == rootUnit) {
                pid = 0;
            } else {
                pid = rootUnit.getUnitId();
            }
            List<Unit> childUnitList = unitService.getChildUnitListByParenId(pid, false);
            request.setAttribute("rootUnit", rootUnit);
            request.setAttribute("childUnitList", childUnitList);
            return "success1";
        } else if (ConfigUnitLevel == 3) {
            rootUnit = unitService.getRootUnit();
            if (null == rootUnit) {
                rootId = 0;
            } else {
                rootId = rootUnit.getUnitId();
            }
            List<Unit> childUnitList = unitService.getChildUnitListByParenId(rootId, false);
            List<JSONObject> parentUnitArray = new ArrayList<JSONObject>();
            for (Unit cunit : childUnitList) {
                List<Unit> ccunitList = unitService.getChildUnitListByParenId(cunit.getUnitId(), false);
                JSONObject childUnit = new JSONObject();
                childUnit.put("p", cunit);
                childUnit.put("clist", ccunitList);
                // childUnit = {"p":cunit,"clist":ccunitList}
                parentUnitArray.add(childUnit);
            }
            request.setAttribute("parentUnitArray", parentUnitArray);
            return "success2";
        } else {
            get_unit_tree();
            return "success3";
        }
        
    }

    private void get_unit_tree() {
        // cache = __jitar__.cacheProvider.getCache("main");
        // cache_key = "html_unit"
        String html = null;
        Unit rootUnit;
        if (null == html) {
            List<Unit> u_list = unitService.getChildUnitListByParenId(0, false);
            if (u_list.size() < 1) {
                rootUnit = null;
            } else {
                rootUnit = u_list.get(0);
            }
            request.setAttribute("rootUnit", rootUnit);
            if (null == rootUnit) {
                request.setAttribute("html", "");
                return;
            }
            html = "d.add(" + rootUnit.getUnitId() + ",-1,'<b>" + rootUnit.getUnitTitle() + " ("
                    + rootUnit.getSiteTitle() + ")</b>','" + unit_main_url + rootUnit.getUnitName() + "');";
            html += getChildUnit(rootUnit);
            // cache.put(cache_key, html)
        }
        request.setAttribute("html", html);
    }

    private String getChildUnit(Unit parentUnit) {
        String s = "";
        List<Unit> unitList = unitService.getAllUnitOrChildUnitList(parentUnit);
        if (null != unitList) {
            for (Unit unit : unitList) {
                s += "d.add(" + unit.getUnitId() + "," + parentUnit.getUnitId() + ",'" + unit.getUnitTitle() + " ("
                        + unit.getSiteTitle() + ")','" + unit_main_url + unit.getUnitName() + "');\\r\\n";
                s += getChildUnit(unit);
            }
        }
        return s;
    }

    /**
     * 为前端ajax提供数据
     * @throws IOException 
     */
    private void getUnitDataList() throws IOException{
        int pid = params.safeGetIntParam("pid");
        List<Unit> unitlist = unitService.getChildUnitListByParenId(pid, false);
        String json = "[";
        JSONObject jsonData = new JSONObject();
        for (Unit u : unitlist) {
            jsonData.put("NodeId", u.getUnitId());
            jsonData.put("LinkText", u.getUnitTitle() + "（" + u.getSiteTitle() + "）");
            jsonData.put("LinkTitle", u.getSiteTitle());
            jsonData.put("LinkHref", unit_main_url + u.getUnitName());
            if (u.getHasChild()) {
                jsonData.put("ChildCount", "1");
            } else {
                jsonData.put("ChildCount", "0");
            }
            json += jsonData.toString() + ",";
        }
        if (json.endsWith(",")) {
            json = json.substring(0, json.length() - 1);
        }
        json += "]";
        // System.out.println("json=" + json);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
