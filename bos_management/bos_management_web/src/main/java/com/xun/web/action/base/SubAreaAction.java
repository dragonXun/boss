package com.xun.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.xun.bos.domain.base.Area;
import com.xun.bos.domain.base.SubArea;
import com.xun.bos.service.base.SubAreaService;
import com.xun.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:35:41 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends CommonAction<SubArea> {

    @Autowired
    private SubAreaService subAreaService;
    public SubAreaAction() {
        super(SubArea.class);  
    }

    @Action(value="subAreaAction_save",results={
            @Result(name="success",location="/pages/base/sub_area.html",type="redirect")
    })
    public String save(){
        subAreaService.save(getModel());
        return SUCCESS;
    }
    
    @Action("subAreaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<SubArea> page = subAreaService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedArea","subareas"});
        pageToJson(page, jsonConfig);
        return NONE;
    }
    
    @Action("subAreaAction_findUnAssociatedFixedArea")
    public String findUnAssociatedFixedArea() throws IOException{
        List<SubArea> list = subAreaService.findUnAssociatedFixedArea();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        listToJson(list, jsonConfig);
        return NONE;
    }

    @Action("subAreaAction_findAssociatedFixedArea")
    public String findAssociatedFixedArea() throws IOException{
        List<SubArea> list = subAreaService.findAssociatedFixedArea(getModel().getId());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"couriers","subareas"});
        listToJson(list, jsonConfig);
        return NONE;
    }
}
  
