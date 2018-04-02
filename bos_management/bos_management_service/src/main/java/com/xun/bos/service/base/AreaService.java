package com.xun.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:53:08 <br/>       
 */
public interface AreaService {

    void save(List<Area> list);

    Page<Area> findAll(Pageable pageable);

    void save(Area model);

    List<Area> findByQ(String q);

    List<Object[]> exportChart();

}
  
