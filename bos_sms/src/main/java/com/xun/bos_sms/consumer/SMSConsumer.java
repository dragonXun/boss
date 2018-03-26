package com.xun.bos_sms.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.xun.utils.SmsUtils;

/**  
 * ClassName:SmsConsumer <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 上午11:32:02 <br/>       
 */
@Component
public class SMSConsumer implements MessageListener{
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
           try {
                String telephone = mapMessage.getString("telephone");
                String code = mapMessage.getString("code");
                System.out.println(telephone + "====" + code);
                SmsUtils.sendSms(telephone, code);
            } catch (JMSException e) {
                  
                e.printStackTrace();  
                
            } catch (ClientException e) {
                  
                e.printStackTrace();  
                
            }
    }
}
  
