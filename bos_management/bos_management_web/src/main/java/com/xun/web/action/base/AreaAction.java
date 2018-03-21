package com.xun.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xun.bos.domain.base.Area;
import com.xun.bos.domain.base.Standard;
import com.xun.bos.service.base.AreaService;
import com.xun.utils.PinYin4jUtils;
import com.xun.web.action.CommonAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:48:23 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {
    public AreaAction() {
        super(Area.class);  
    }


    @Autowired
    private AreaService areaService;

    @Action(value="areaAction_save",results={
            @Result(name="success",location="/pages/base/area.html",type="redirect")
    })
    public String save(){
        areaService.save(getModel());
        return SUCCESS;
    }
    
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    @Action("areaAction_findAll")
    public String findAll(){
        List<Area> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(q)) {
            list = areaService.findByQ(q);
        }else {
            
            Page<Area> page = areaService.findAll(null);
            list = page.getContent();
        }
        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setExcludes(new String[]{"subareas"});
            listToJson(list, jsonConfig);
        } catch (IOException e) {
              
            e.printStackTrace();  
            
        }
        return NONE;
    }
    
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    @Action(value="areaAction_importXLS",results={
            @Result(name="success",location="../../pages/base/area.html",type="redirect")
    })
    public String importXLS(){
        try {
            HSSFWorkbook workbook  = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Area> list = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                
                province = province.substring(0, province.length()-1);
                city = city.substring(0, city.length()-1);
                district = district.substring(0, district.length()-1);
                String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
                String[] strings = PinYin4jUtils.getHeadByString(province+city+district);
                String shortcode = PinYin4jUtils.stringArrayToString(strings);
                
                Area area = new Area();
                area.setProvince(province);
                area.setCity(city);
                area.setCitycode(citycode);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setShortcode(shortcode);
                list.add(area);
            }
            areaService.save(list);
            workbook.close();
        } catch (IOException e) {
              
            e.printStackTrace();  
            
        }
        return SUCCESS;
    }
    
    
    /*private int page;
    private int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }*/
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Area> page = areaService.findAll(pageable);
       /* List<Area> list = page.getContent();
        long total = page.getTotalElements();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);*/
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
       /* String json = JSONObject.fromObject(map,jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);*/
        pageToJson(page, jsonConfig);
        return NONE;
    }
}
  
