package com.xun.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xun.bos.domain.base.Area;
import com.xun.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 上午11:46:19 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
    private Order model;
    @Override
    public Order getModel() {
        if (model == null) {
            model = new Order();
        }
        return model;
    }

    private String sendAreaInfo;
    private String recAreaInfo;
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    @Action(value="orderAction_add",results={
            @Result(name="success",location="index.html",type="redirect"),
            @Result(name="error",location="signup-fail.html",type="redirect")
    })
    public String add(){
        if (StringUtils.isNotEmpty(sendAreaInfo)) {
            String[] split = sendAreaInfo.split("/");
            String province = split[0];
            province = province.substring(0, province.length()-1);
            String city = split[1];
            city = city.substring(0, city.length()-1);
            String district = split[2];
            district = district.substring(0, district.length()-1);
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            model.setSendArea(area);
        }
        if (StringUtils.isNotEmpty(recAreaInfo)) {
            String[] split = recAreaInfo.split("/");
            String province = split[0];
            province = province.substring(0, province.length()-1);
            String city = split[1];
            city = city.substring(0, city.length()-1);
            String district = split[2];
            district = district.substring(0, district.length()-1);
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            model.setRecArea(area);
        }
        if (model != null) {
            System.out.println(model);
            WebClient.create("http://localhost:8080/bos_management_web/webService/orderService/saveOrder")
            .accept(MediaType.APPLICATION_JSON)
            .type(MediaType.APPLICATION_JSON)
            .post(model);
            return SUCCESS;
        }
        return ERROR;
    }
}
  
