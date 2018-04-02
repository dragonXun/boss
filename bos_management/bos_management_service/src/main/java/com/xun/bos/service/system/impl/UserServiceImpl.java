package com.xun.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.system.UserRepository;
import com.xun.bos.domain.system.Role;
import com.xun.bos.domain.system.User;
import com.xun.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:22:33 <br/>       
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
          
        return userRepository.findAll(pageable);
    }

    @Override
    public void save(Long[] roleIds, User user) {
        userRepository.save(user);
        if (roleIds != null && roleIds.length > 0) {
            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                user.getRoles().add(role);
            }
        }
        
    }
}
  
