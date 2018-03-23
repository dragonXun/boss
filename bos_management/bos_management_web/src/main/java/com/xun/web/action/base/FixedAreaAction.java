package com.xun.web.action.base;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import com.xun.bos.domain.base.FixedArea;
import com.xun.bos.service.base.FixedAreaService;
import com.xun.crm.domain.Customer;
import com.xun.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:06:25 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    @Autowired
    private FixedAreaService fixedAreaService;
    public FixedAreaAction() {
          
        super(FixedArea.class);  
    }
    
    @Action(value="fixedAreaAction_save",results={
            @Result(name="success",location="/pages/base/fixed_area.html",type="redirect")
    })
    public String save(){
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
 
    @Action("fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<FixedArea> page = fixedAreaService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        pageToJson(page, jsonConfig);
        return NONE;
    }
    
    @Action("fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException{
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findUnAssociatedCustomers")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .getCollection(Customer.class);
        listToJson(list, null);
        return NONE;
    }
    
    @Action("fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException{
        if (getModel() != null && getModel().getId()!=null) {
            List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findAssociatedCustomers")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("fixedAreaId", getModel().getId())
                    .getCollection(Customer.class);
            listToJson(list, null);
        }
        return NONE;
    }
    
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value="fixedAreaAction_assignCustomers2FixedArea",results={
            @Result(name="success",location="/pages/base/fixed_area.html",type="redirect")
    })
    public String assignCustomers2FixedArea() throws IOException{
        if ("hello".equals(getModel().getCompany())) {
            fixedAreaService.associationFixedAreaToSubArea(getModel().getId(), customerIds);
            return SUCCESS;
        }
        if (customerIds == null) {
            customerIds = new Long[]{0L};
        }
        if (getModel() != null && getModel().getId()!=null) {
            WebClient.create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("fixedAreaId", getModel().getId())
                    .query("customerIds", customerIds)
                    .put(null);
        }
        return SUCCESS;
    }
    
    private Long takeTimeId;
    private Long courierId;
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    
    @Action(value="fixedAreaAction_associationCourierToFixedArea",results={
            @Result(name="success",location="/pages/base/fixed_area.html",type="redirect")
    })
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),takeTimeId,courierId);
        return SUCCESS;
    }
}
  
