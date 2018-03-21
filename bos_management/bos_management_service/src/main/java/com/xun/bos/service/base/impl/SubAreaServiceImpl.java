package com.xun.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.SubAreaRepository;
import com.xun.bos.domain.base.SubArea;
import com.xun.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:42:32 <br/>       
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Override
    public void save(SubArea model) {
       
        subAreaRepository.save(model);
    }
    @Override
    public Page<SubArea> findAll(Pageable pageable) {
          
        return subAreaRepository.findAll(pageable);
    }
    @Override
    public Page<SubArea> findAll(Specification<SubArea> specification, Pageable pageable) {
          
        return subAreaRepository.findAll(specification, pageable);
    }
    @Override
    public List<SubArea> findUnAssociatedFixedArea() {
        //return subAreaRepository.findUnAssociatedFixedArea();
        return subAreaRepository.findByFixedAreaIsNull();
    }
    @Override
    public List<SubArea> findAssociatedFixedArea(Long id) {
        return subAreaRepository.findAssociatedFixedArea(id);
    }

}
  
