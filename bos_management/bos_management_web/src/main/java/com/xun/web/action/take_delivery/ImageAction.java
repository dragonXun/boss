package com.xun.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 下午3:09:39 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport {

    private File imgFile;
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    private String imgFileFileName;
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    
    @Action("imageAction_upload")
    public String upload() throws IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            
            String dirpath = "/upload";
            String dirRealPath = ServletActionContext.getServletContext().getRealPath(dirpath);
            String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            String filename = UUID.randomUUID().toString().replace("-", "")+suffix;
            File destFile = new File(dirRealPath+"/"+filename);
            FileUtils.copyFile(imgFile, destFile);
            String contextPath = ServletActionContext.getServletContext().getContextPath();
            map.put("error", 0);
            map.put("url", contextPath+"/upload/"+filename);
        } catch (IOException e) {
            map.put("error", 1);
            map.put("message", e.getMessage());
        }
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
    @Action("imageAction_manager")
    public String manager() throws IOException {
        String dirpath = "/upload";
        String dirRealPath = ServletActionContext.getServletContext().getRealPath(dirpath);
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
        File currentPathFile = new File(dirRealPath);
        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if(currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        JSONObject result = new JSONObject();
        String contextPath = ServletActionContext.getServletContext().getContextPath();
        result.put("current_url", contextPath +"/upload/");
        result.put("file_list", fileList);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(result.toString());
        return NONE;
    }
}
  
