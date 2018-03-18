package com.xun.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xun.bos.domain.base.Courier;
import com.xun.bos.domain.base.Standard;
import com.xun.bos.service.base.CourierService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:06:58 <br/>       
 */

@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {
    private Courier model;
    @Autowired
    private CourierService courierService;
    @Override
    public Courier getModel() {
        if (model == null) {
            model = new Courier();
        }
        return model;
    }
    @Action(value="courierAction_save",results={
            @Result(name="success",location="/pages/base/courier.html",type="redirect")
    })
    public String save(){
        courierService.save(model);
        return SUCCESS;
    }
    
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    @Action(value="courierAction_batchDel",results={
            @Result(name="success",location="/pages/base/courier.html",type="redirect")
    })
    public String batchDel(){
        if (StringUtils.isNotEmpty(ids)) {
            String[] arr = ids.split(",");
            courierService.updateDelById(arr);
            return SUCCESS;
        }
        return NONE;
    }
    
    private int page;
    private int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException{
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String courierNum = model.getCourierNum();
                Standard standard = model.getStandard();
                String company = model.getCompany();
                String type = model.getType();
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(courierNum)) {
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                if (StringUtils.isNotEmpty(company)) {
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p2);
                }
                if (StringUtils.isNotEmpty(type)) {
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                if (standard != null && StringUtils.isNotEmpty(standard.getName())) {
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.equal(join.get("name").as(String.class), standard.getName());
                    list.add(p4);
                }
                if (list.size() == 0) {
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                return cb.and(arr);
            }};
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> page = courierService.findAll(specification,pageable);
        long total = page.getTotalElements();
        List<Courier> list = page.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas"});
        String json = JSONObject.fromObject(map,jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);        
        return NONE;
    }

}
  
