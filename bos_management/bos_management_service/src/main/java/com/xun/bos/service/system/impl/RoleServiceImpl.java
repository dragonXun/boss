package com.xun.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.system.RoleRepository;
import com.xun.bos.domain.system.Menu;
import com.xun.bos.domain.system.Permission;
import com.xun.bos.domain.system.Role;
import com.xun.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:27:12 <br/>       
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> findAll(Pageable pageable) {
          
        return roleRepository.findAll(pageable);
    }

    @Override
    public void save(Role role, Long[] permissionIds, String menuIds) {
          
        roleRepository.save(role);
        if (StringUtils.isNotEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String menuId : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(menuId));
                role.getMenus().add(menu);
            }
        }
        if (permissionIds != null && permissionIds.length > 0) {
            for (Long id : permissionIds) {
                Permission permission = new Permission();
                permission.setId(id);
                role.getPermissions().add(permission);
            }
        }
    }
}
  
