package com.xun.web.action.system;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
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

import com.xun.bos.domain.system.Role;
import com.xun.bos.domain.system.User;
import com.xun.bos.service.system.UserService;
import com.xun.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:12:21 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends CommonAction<User> {

    public UserAction() {
          
        super(User.class);  
    }
    @Autowired
    private UserService userService;

    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    @Action(value="userAction_login",results={
            @Result(name="success",location="index.html",type="redirect"),
            @Result(name="login",location="login.html",type="redirect")
    })
    public String login(){
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        if (StringUtils.isNotEmpty(serverCode) && serverCode.equals(checkcode)) {
            //当前用户
            Subject subject = SecurityUtils.getSubject();
            //创建令牌
            AuthenticationToken token = new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
            subject.login(token);
            User user = (User) subject.getPrincipal();
            ServletActionContext.getRequest().getSession().setAttribute("user", user);
            return SUCCESS;
        }
        return LOGIN;
    }
    
    @Action(value="userAction_logout",results={
            @Result(name="success",location="/login.html",type="redirect")
        })
    public String logout(){
        SecurityUtils.getSubject().logout();
        return SUCCESS;
    }
    
    @Action("userAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<User> page = userService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        pageToJson(page, jsonConfig);
        return NONE;
    }
    
    
    private Long[] roleIds;
    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
    @Action(value="userAction_save",results={
            @Result(name="success",location="/pages/system/userlist.html",type="redirect"),
            @Result(name="error",location="/pages/system/userinfo.html",type="redirect")
        })
    public String save(){
        userService.save(roleIds,getModel());
        return SUCCESS;
    }
}
  
