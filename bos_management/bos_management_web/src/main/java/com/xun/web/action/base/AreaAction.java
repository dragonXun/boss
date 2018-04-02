package com.xun.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
import com.xun.utils.FileDownloadUtils;
import com.xun.utils.PinYin4jUtils;
import com.xun.web.action.CommonAction;

import freemarker.core.ReturnInstruction.Return;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
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
    
    
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Area> page = areaService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        pageToJson(page, jsonConfig);
        return NONE;
    }
    
    @Action("areaAction_exportAreaExcel")
    public String exportAreaExcel() throws IOException{
        Page<Area> page = areaService.findAll(null);
        List<Area> list = page.getContent();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.createFreezePane(1, 1);
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 7000);
        
        // Sheet样式   
        HSSFCellStyle sheetStyle = workbook.createCellStyle();   
        sheetStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
        sheetStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．   
        sheetStyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．  
        // 背景色的设定   
        sheetStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);   
        // 前景色的设定   
        sheetStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);   
        // 填充模式   
        sheetStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);   
        // 设置列的样式   
        for (int i = 0; i <= 14; i++) {   
          sheet.setDefaultColumnStyle((short) i, sheetStyle);   
        }   
        // 设置字体   
        HSSFFont headfont = workbook.createFont();   
        headfont.setFontName("黑体");   
        headfont.setFontHeightInPoints((short) 22);// 字体大小   
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗  
     // 另一个样式   
        HSSFCellStyle headstyle = workbook.createCellStyle();   
        headstyle.setFont(headfont);   
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中   
        headstyle.setLocked(true);   
        headstyle.setWrapText(true);// 自动换行 
        
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("省");
        titleRow.createCell(1).setCellValue("市");
        titleRow.createCell(2).setCellValue("区");
        titleRow.createCell(3).setCellValue("邮编");
        titleRow.createCell(4).setCellValue("简码");
        titleRow.createCell(5).setCellValue("城市编码");
        for (Area area : list) {
            HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);
            row.createCell(0).setCellValue(area.getProvince());
            row.createCell(1).setCellValue(area.getCity());
            row.createCell(2).setCellValue(area.getDistrict());
            row.createCell(3).setCellValue(area.getPostcode());
            row.createCell(4).setCellValue(area.getShortcode());
            row.createCell(5).setCellValue(area.getCitycode());
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        String fileName = "区域数据.xls";
        String header = ServletActionContext.getRequest().getHeader("User-Agent");
        String mimeType = ServletActionContext.getServletContext().getMimeType(fileName);
        fileName = FileDownloadUtils.encodeDownloadFilename(fileName, header);
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
        return NONE;
    }
    
    @Action("areaAction_exportChart")
    public String exportChart() throws IOException{
        List<Object[]> list = areaService.exportChart();
        listToJson(list, null);
        return NONE;
    }
    
    @Autowired
    private DataSource dataSource;
    @Action("areaAction_ExportPDF")
    public String ExportPDF() throws Exception{
        // 读取 jrxml 文件
        String jrxml = ServletActionContext.getServletContext().getRealPath("/jasper/area.jrxml");
        // 准备需要数据
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company", "浮云浅浅");
        // 准备需要数据
        JasperReport report = JasperCompileManager.compileReport(jrxml);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());

        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream ouputStream = response.getOutputStream();
        // 设置相应参数，以附件形式保存PDF
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + FileDownloadUtils.encodeDownloadFilename("工作单.pdf",
                ServletActionContext.getRequest().getHeader("user-agent")));
        // 使用JRPdfExproter导出器导出pdf
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
        exporter.exportReport();// 导出
        ouputStream.close();// 关闭流
        return NONE;
    }
}
  
