package com.xun.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午2:51:18 <br/>       
 */

public interface StandardService {

    void save(Standard model);

    Page<Standard> findAll(Pageable pageable);

}
  
