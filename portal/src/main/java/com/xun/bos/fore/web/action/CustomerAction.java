package com.xun.bos.fore.web.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xun.crm.domain.Customer;
import com.xun.utils.MailUtils;
import com.xun.utils.SmsUtils;

import net.sf.json.JSONObject;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午9:35:47 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

    private Customer model = new Customer();
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Override
    public Customer getModel() {
          
        return model;
    }

    @Action("customerAction_sendSMS")
    public String sendSMS(){
        final String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);
        try {
            
            jmsTemplate.send("sms_message", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone", model.getTelephone());
                    mapMessage.setString("code", code);
                    return mapMessage;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();  
            
        }
        return NONE;
    }
    
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value="customerAction_regist",results={
            @Result(name="success",location="signup-success.html",type="redirect"),
            @Result(name="error",location="signup-fail.html",type="redirect")
    })
    public String regist(){
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
        if (StringUtils.isNotEmpty(checkcode) && checkcode.equals(serverCode)) {
            Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/isActived")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .get(Customer.class);
                    
            if (customer != null) {
                return NONE;
            }
            WebClient.create("http://localhost:8180/crm/webService/customerService/save")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .post(model);
            String email = model.getEmail();
            String telephone = model.getTelephone();
            String activeCode = RandomStringUtils.randomNumeric(16);
            redisTemplate.opsForValue().set(telephone, activeCode,1,TimeUnit.DAYS);
            MailUtils.sendMail(email, "用户激活", "<a href='http://localhost:8280/portal/customerAction_active.action"
                    + "?checkcode="+activeCode+"&telephone="+telephone+"'>"+"欢迎注册速运会员,请在24小时内点击激活</a>");
            return SUCCESS;
        }
        return ERROR;
    }
    
    @Action(value="customerAction_active",results={
            @Result(name="success",location="login.html",type="redirect"),
            @Result(name="error",location="signup-fail.html",type="redirect")
    })
    public String active(){
        String activeCode = redisTemplate.opsForValue().get(model.getTelephone());
        if (StringUtils.isNotEmpty(checkcode) && checkcode.equals(activeCode)) {
            Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/isActived")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .query("telephone", model.getTelephone())
            .get(Customer.class);
            
            if (customer == null) {
                return ERROR;
            }
            if (customer.getType() == null) {
                WebClient.create("http://localhost:8180/crm/webService/customerService/active")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .query("telephone", model.getTelephone())
                .put(null);
            }
            return SUCCESS;
        }
        return ERROR;
    }
    
    @Action(value="customerAction_login",results={
            @Result(name="success",location="index.html",type="redirect"),
            @Result(name="error",location="login.html",type="redirect")
    })
    public String login(){
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");

        if (StringUtils.isNotEmpty(checkcode) && checkcode.equals(validateCode)) {
            Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/login")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .query("password", model.getPassword())
                    .get(Customer.class);
            if (customer != null && customer.getType() != null && customer.getType() == 1) {
                ServletActionContext.getRequest().getSession().setAttribute("user", customer);
                return SUCCESS;
            }
        }
        return ERROR;
    }
    
    @Action("customerAction_findByTelephone")
    public String findByTelephone(){
        
        Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/isActived")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .query("telephone", model.getTelephone())
                .get(Customer.class);
        try {
            if (customer != null) {
                
                ServletActionContext.getResponse().getWriter().write("true");
            }
        } catch (IOException e) {
              
            e.printStackTrace();  
            
        }
        return NONE;
    }
    
}
  
