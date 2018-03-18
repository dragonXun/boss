package com.xun.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xun.bos.domain.base.SubArea;

/**  
 * ClassName:subAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:45:01 <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>, JpaSpecificationExecutor<SubArea> {

}
  
