package com.xun.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:09:13 <br/>       
 */
public interface FixedAreaService {

    Page<FixedArea> findAll(Pageable pageable);

    void save(FixedArea model);

    void associationCourierToFixedArea(Long id, Long takeTimeId, Long courierId);

    void associationFixedAreaToSubArea(Long id, Long[] customerIds);

}
  
