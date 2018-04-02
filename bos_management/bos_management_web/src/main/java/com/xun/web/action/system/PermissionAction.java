package com.xun.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.xun.bos.domain.system.Menu;
import com.xun.bos.domain.system.Permission;
import com.xun.bos.service.system.PermissionService;
import com.xun.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:41:14 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends CommonAction<Permission> {

    public PermissionAction() {
          
        super(Permission.class);  
    }
    
    @Autowired
    private PermissionService permissionService;
    
    @Action(value="permissionAction_save",results={
            @Result(name="success",location="pages/system/permission.html",type="redirect"),
            @Result(name="error",location="pages/system/permission_add.html",type="redirect")
    })
    public String save(){
        try {
            
            permissionService.save(getModel());
            return SUCCESS;
        } catch (Exception e) {
        }
        return ERROR;
    }
    
    @Action("permissionAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Permission> page = permissionService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        pageToJson(page, jsonConfig);
        return NONE;
    }

    @Action("permissionAction_findAll")
    public String findAll() throws IOException{
        Page<Permission> page = permissionService.findAll(null);
        List<Permission> list = page.getContent();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        listToJson(list, jsonConfig);
        return NONE;
    }
}
  
