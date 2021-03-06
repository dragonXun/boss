package com.xun.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
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
import com.xun.bos.domain.system.User;
import com.xun.bos.service.system.MenuService;
import com.xun.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:05:50 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends CommonAction<Menu> {

    public MenuAction() {
          
        super(Menu.class);  
    }
    @Autowired
    private MenuService menuService;

    @Action(value="menuAction_save",results={
            @Result(name="success",location="pages/system/menu.html",type="redirect"),
            @Result(name="error",location="pages/system/menu_add.html",type="redirect")
    })
    public String save(){
        try {
            
            menuService.save(getModel());
            return SUCCESS;
        } catch (Exception e) {
        }
        return ERROR;
    }
    

    @Action("menuAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage())-1, rows);
        Page<Menu> page = menuService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","childrenMenus","parentMenu"});
        pageToJson(page, jsonConfig);
        return NONE;
    }

    @Action("menuAction_findLevelOne")
    public String findLevelOne() throws IOException{
        List<Menu> list = menuService.findLevelOne();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","parentMenu"});
        listToJson(list, jsonConfig);
        return NONE;
    }

    @Action("menuAction_findbyUser")
    public String findbyUser() throws IOException{
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Menu> list;
        if ("admin".equals(user.getUsername())) {
            Page<Menu> page = menuService.findAll(null);
            list = page.getContent();
        }else {
            
            list = menuService.findbyUser(user.getId());
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","parentMenu","children"});
        listToJson(list, jsonConfig);
        return NONE;
    }
}
  
