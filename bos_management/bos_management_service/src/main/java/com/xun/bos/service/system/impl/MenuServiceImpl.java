package com.xun.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.system.MenuRepository;
import com.xun.bos.domain.system.Menu;
import com.xun.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:12:25 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void save(Menu menu) {
        if (menu.getParentMenu() != null && menu.getParentMenu().getId() == null) {
            menu.setParentMenu(null);
        }
        menuRepository.save(menu);
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
          
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findLevelOne() {
          
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public List<Menu> findbyUser(Long uid) {
          
        return menuRepository.findbyUser(uid);
    }
}
  
