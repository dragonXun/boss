package com.xun.bos.service.system.impl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xun.bos.dao.system.PermissionRepository;
import com.xun.bos.domain.system.Permission;
import com.xun.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:47:28 <br/>       
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public void save(Permission permission) {
          
        permissionRepository.save(permission);
    }
    @Override
    public Page<Permission> findAll(Pageable pageable) {
          
        return permissionRepository.findAll(pageable);
    }

}
  
