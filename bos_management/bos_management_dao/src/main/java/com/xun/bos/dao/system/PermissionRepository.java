package com.xun.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xun.bos.domain.system.Permission;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:46:21 <br/>       
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select p from Permission p inner join p.roles r inner join r.users u on u.id=?")
    List<Permission> findbyUser(Long id);

}
  
