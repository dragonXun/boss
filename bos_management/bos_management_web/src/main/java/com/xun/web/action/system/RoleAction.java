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
import com.xun.bos.domain.system.Role;
import com.xun.bos.service.system.RoleService;
import com.xun.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:24:52 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {

    public RoleAction() {
          
        super(Role.class);  
    }
    @Autowired
    private RoleService roleService;
    
    @Action(value="roleAction_save",results={
            @Result(name="success",location="pages/system/role.html",type="redirect"),
            @Result(name="error",location="pages/system/role_add.html",type="redirect")
    })
    public String save(){
        try {
            
            roleService.save(getModel(),permissionIds,menuIds);
            return SUCCESS;
        } catch (Exception e) {
        }
        return ERROR;
    }
    
    @Action("roleAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Role> page = roleService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"menus","permissions","users"});
        pageToJson(page, jsonConfig);
        return NONE;
    }
    @Action("roleAction_findAll")
    public String findAll() throws IOException{
        Page<Role> page = roleService.findAll(null);
        List<Role> list = page.getContent();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"menus","permissions","users"});
        listToJson(list, jsonConfig);
        return NONE;
    }
    
    private String menuIds;
    private Long[] permissionIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    

}
  
