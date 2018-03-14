package com.xun.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:13:17 <br/>       
 */
public interface CourierService {

    void save(Courier model);

    Page<Courier> findAll(Pageable pageable);

    void updateDelById(String[] arr);

}
  
