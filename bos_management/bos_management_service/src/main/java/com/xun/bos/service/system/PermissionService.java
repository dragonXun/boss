package com.xun.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:47:11 <br/>       
 */
public interface PermissionService {

    void save(Permission permission);

    Page<Permission> findAll(Pageable pageable);

}
  
