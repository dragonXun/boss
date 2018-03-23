package com.xun.bos.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xun.bos.domain.base.Courier;
import com.xun.bos.domain.base.FixedArea;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:16:48 <br/>       
 */
public interface CourierRepository extends JpaRepository<Courier, Long>, JpaSpecificationExecutor<Courier>{
    @Modifying
    @Query("update Courier set  deltag = 1 where id = ?")
    void updateDelById(Long id);

    Set<Courier> findByfixedAreas(FixedArea fixedArea);

}
  
