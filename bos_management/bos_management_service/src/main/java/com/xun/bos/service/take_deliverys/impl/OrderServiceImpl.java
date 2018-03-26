package com.xun.bos.service.take_deliverys.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.AreaRepository;
import com.xun.bos.dao.CourierRepository;
import com.xun.bos.dao.FixedAreaRepository;
import com.xun.bos.dao.take_delivery.OrderRepository;
import com.xun.bos.dao.take_delivery.WorkBillRepository;
import com.xun.bos.domain.base.Area;
import com.xun.bos.domain.base.Courier;
import com.xun.bos.domain.base.FixedArea;
import com.xun.bos.domain.base.SubArea;
import com.xun.bos.domain.take_delivery.Order;
import com.xun.bos.domain.take_delivery.WorkBill;
import com.xun.bos.service.take_deliverys.OrderService;
import com.xun.crm.domain.Customer;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午12:13:50 <br/>       
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
  
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private WorkBillRepository workBillRepository;
    
    @Autowired
    private AreaRepository areaRepository;
    
    @Override
    public void saveOrder(Order order) {
        Area sendArea = order.getSendArea();
        if (sendArea != null) {
            Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(),sendArea.getCity(),sendArea.getDistrict());
            order.setSendArea(sendAreaDB);
        }
        Area recArea = order.getRecArea();
        if (recArea != null) {
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(),recArea.getCity(),recArea.getDistrict());
            order.setRecArea(recAreaDB);
        }
        order.setStatus("待取件");
        orderRepository.save(order);
        if (StringUtils.isNotEmpty(order.getSendAddress())) {
            String fixedAreaId = WebClient.create("http://localhost:8180/crm/webService/customerService/findFixedAreaId")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("address", order.getSendAddress())
                    .get(String.class);
            if (StringUtils.isNotEmpty(fixedAreaId)) {
                FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
                autoBill(order, fixedArea);
                return;
            }
        }
        
        if (order.getSendArea() != null) {
            String sendAddress = order.getSendAddress();
            Set<SubArea> subareas = order.getSendArea().getSubareas();
            if (StringUtils.isNotEmpty(sendAddress)&&!subareas.isEmpty()) {
                for (SubArea subArea : subareas) {
                    if (sendAddress.contains(subArea.getKeyWords())
                            ||sendAddress.contains(subArea.getAssistKeyWords())) {
                        FixedArea fixedArea = subArea.getFixedArea();
                        autoBill(order, fixedArea);
                        return;
                    }
                }
            }
            order.setOrderType("人工分单");
            System.out.println("自动分单失败,请人工分单!");
        }

    }


    private void autoBill(Order order, FixedArea fixedArea) {
        if (fixedArea != null) {
            Set<Courier> couriers = fixedArea.getCouriers();
            if (!couriers.isEmpty()) {
                Iterator<Courier> iterator = couriers.iterator();
                Courier courier = iterator.next();
                order.setOrderTime(new Date());
                order.setOrderType("自动分单");
                order.setOrderNum(UUID.randomUUID().toString().replace("-", ""));
                order.setCourier(courier);
                WorkBill workBill = new WorkBill();
                workBill.setAttachbilltimes(null);
                workBill.setBuildtime(new Date());
                workBill.setCourier(courier);
                workBill.setOrder(order);
                workBill.setPickstate("新单");
                workBill.setRemark(order.getRemark());
                workBill.setSmsNumber("123456");
                workBill.setType("新");
                workBillRepository.save(workBill);
                System.out.println(courier.getName()+":工单:"+workBill);
            }
        }
    }
   
   

}
  
