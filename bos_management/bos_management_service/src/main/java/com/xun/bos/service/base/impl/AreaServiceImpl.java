package com.xun.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.AreaRepository;
import com.xun.bos.domain.base.Area;
import com.xun.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:53:43 <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void save(List<Area> list) {
          
        areaRepository.save(list);
    }

    @Override
    public Page<Area> findAll(Pageable pageable) {
          
        return areaRepository.findAll(pageable);
    }

    @Override
    public void save(Area model) {
          
        areaRepository.save(model);
    }

    @Override
    public List<Area> findByQ(String q) {
          
        return areaRepository.findFromQ("%"+q.toUpperCase()+"%");
    }

}
  
