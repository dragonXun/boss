package com.xun.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xun.bos.domain.base.TakeTime;
import com.xun.bos.service.base.TakeTimeService;
import com.xun.web.action.CommonAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午3:41:52 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {

    public TakeTimeAction() {
          
        super(TakeTime.class);  
    }

    @Autowired
    private TakeTimeService takeTimeService;
    
    @Action("takeTimeAction_findAll")
    public String findAll() throws IOException{
        List<TakeTime> list = takeTimeService.findAll();
        listToJson(list, null);
        return NONE;
    }
    
}
  
