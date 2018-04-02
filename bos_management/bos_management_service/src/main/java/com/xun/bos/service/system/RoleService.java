package com.xun.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:26:49 <br/>       
 */
public interface RoleService {

    void save(Role role, Long[] permissionIds, String menuIds);

    Page<Role> findAll(Pageable pageable);

}
  
