package com.xun.bos.service.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xun.bos.dao.take_delivery.WorkBillRepository;
import com.xun.bos.domain.take_delivery.WorkBill;
import com.xun.utils.MailUtils;
import com.xun.utils.SmsUtils;

/**  
 * ClassName:MailJob <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午8:41:25 <br/>       
 */
@Component
public class WorkBillJob {

    @Autowired
    private WorkBillRepository workBillRepository;
    
    public void sendMail(){
        List<WorkBill> list = workBillRepository.findAll();
        String emailBody = "编号\t快递员\t取件状态\t时间<br/>";
        for (WorkBill workBill : list) {
            emailBody += workBill.getId()+"\t"+workBill.getCourier().getName()+"\t"+
                    workBill.getPickstate()+"\t"+workBill.getBuildtime()+"<br/>";
        }
        MailUtils.sendMail("648973562@qq.com", "运单详情", emailBody );
        System.out.println("邮件已发送~!~");
    }
}
  
