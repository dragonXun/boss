package com.xun.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:22:10 <br/>       
 */
public interface UserService {

    Page<User> findAll(Pageable pageable);

    void save(Long[] roleIds, User user);

}
  
