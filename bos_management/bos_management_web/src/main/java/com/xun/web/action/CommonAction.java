package com.xun.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xun.bos.domain.base.Area;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:30:23 <br/>       
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    private T model;
    private Class<T> clazz;
    
    public CommonAction(Class<T> clazz) {
        this.clazz = clazz;
        try {
            model = clazz.newInstance();
        } catch (Exception e) {
              
            e.printStackTrace();  
            
        }
    }

    @Override
    public T getModel() {
        return model;
    }

    protected int page;
    protected int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void pageToJson(Page<T> page,JsonConfig jsonConfig) throws IOException{
        List<T> list = page.getContent();
        long total = page.getTotalElements();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        
        String json;
        if (jsonConfig == null) {
            json = JSONObject.fromObject(map).toString();
        }else {
            json = JSONObject.fromObject(map,jsonConfig).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
    
    public void listToJson(List list,JsonConfig jsonConfig) throws IOException{
        String json;
        if (jsonConfig == null) {
            json = JSONArray.fromObject(list).toString();
        }else {
            json = JSONArray.fromObject(list,jsonConfig).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
    
}
  
