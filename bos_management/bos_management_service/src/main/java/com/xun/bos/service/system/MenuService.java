package com.xun.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.system.Menu;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:11:42 <br/>       
 */
public interface MenuService {

    void save(Menu menu);

    Page<Menu> findAll(Pageable pageable);

    List<Menu> findLevelOne();

    List<Menu> findbyUser(Long id);

}
  
