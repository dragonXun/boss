package com.xun.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xun.bos.domain.base.Standard;

/**  
 * ClassName:StantardDao <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午3:44:21 <br/>       
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {
}
  
