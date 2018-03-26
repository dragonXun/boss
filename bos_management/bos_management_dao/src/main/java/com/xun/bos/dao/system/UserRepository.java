package com.xun.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xun.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:59:50 <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
  
