package com.xun.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WayBillService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午5:22:14 <br/>       
 */
public interface WayBillService {

    void save(WayBill model);

    Page<WayBill> findAll(Pageable pageable);

}
  
