package com.xun.web.action.take_delivery;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.xun.bos.domain.take_delivery.WayBill;
import com.xun.bos.service.take_deliverys.WayBillService;
import com.xun.web.action.CommonAction;

/**  
 * ClassName:WayBillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午5:13:39 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WayBillAction extends CommonAction<WayBill> {

    public WayBillAction() {
          
        super(WayBill.class);  
        
    }

    @Autowired
    private WayBillService wayBillService;
    
    @Action("wayBillAction_save")
    public String save() throws IOException{
        String msg = "0";
        try {
            wayBillService.save(getModel());
        } catch (Exception e) {
            msg = "1";
        }
        ServletActionContext.getResponse().getWriter().write(msg);
        return NONE;
    }
    
    @Action("wayBillAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<WayBill> page = wayBillService.findAll(pageable);
        pageToJson(page, null);
        return NONE;
    }
}
  
