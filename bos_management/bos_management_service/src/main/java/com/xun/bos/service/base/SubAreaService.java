package com.xun.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xun.bos.domain.base.SubArea;

/**  
 * ClassName:subAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:40:10 <br/>       
 */
public interface SubAreaService {

    void save(SubArea model);

    Page<SubArea> findAll(Pageable pageable);

    Page<SubArea> findAll(Specification<SubArea> specification, Pageable pageable);

    List<SubArea> findUnAssociatedFixedArea();

    List<SubArea> findAssociatedFixedArea(Long id);

}
  
