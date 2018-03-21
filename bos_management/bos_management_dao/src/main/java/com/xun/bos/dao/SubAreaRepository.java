package com.xun.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xun.bos.domain.base.SubArea;

/**  
 * ClassName:subAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:45:01 <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>, JpaSpecificationExecutor<SubArea> {

    @Query("from SubArea where fixedArea.id is null")
    List<SubArea> findUnAssociatedFixedArea();

    @Query("from SubArea where fixedArea.id = ? ")
    List<SubArea> findAssociatedFixedArea(Long id);

    List<SubArea> findByFixedAreaIsNull();

    @Modifying
    @Query("update SubArea set fixedArea.id = null where fixedArea.id = ?")
    void unbindSubAreaByFixedArea(Long id);

    @Modifying
    @Query("update SubArea set fixedArea.id = ?2 where id = ?1")
    void bindSubAreaByFixedArea(Long subAreaId,Long id);



}
  
