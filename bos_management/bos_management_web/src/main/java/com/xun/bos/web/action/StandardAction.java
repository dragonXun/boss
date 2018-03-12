package com.xun.bos.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xun.bos.dao.StandardDao;
import com.xun.bos.domain.base.Standard;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午5:50:02 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
    @Autowired
    private StandardDao dao;
    private Standard model;
    @Override
    public Standard getModel() {
          
        if (model == null) {
            model = new Standard();
        }
        return model;
    }

    @Action(value="standard_save",results={@Result(name="success",location="/pages/base/standard.html")})
    public String save(){
        dao.save(model);
        return SUCCESS;
    }
}
  
