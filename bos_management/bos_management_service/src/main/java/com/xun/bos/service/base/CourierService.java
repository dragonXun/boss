package com.xun.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xun.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:13:17 <br/>       
 */
public interface CourierService {

    void save(Courier model);

    void updateDelById(String[] arr);

    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);


}
  
