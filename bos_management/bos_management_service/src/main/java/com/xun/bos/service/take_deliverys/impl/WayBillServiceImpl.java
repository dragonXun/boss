package com.xun.bos.service.take_deliverys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.take_delivery.WayBillRepository;
import com.xun.bos.domain.take_delivery.WayBill;
import com.xun.bos.service.take_deliverys.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午5:23:39 <br/>       
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;
    
    @Override
    public void save(WayBill model) {
        wayBillRepository.save(model);
        
    }

    @Override
    public Page<WayBill> findAll(Pageable pageable) {
          
        return wayBillRepository.findAll(pageable);
    }

}
  
