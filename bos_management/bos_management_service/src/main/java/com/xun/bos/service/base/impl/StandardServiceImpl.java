package com.xun.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.StandardRepository;
import com.xun.bos.domain.base.Standard;
import com.xun.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午2:49:41 <br/>       
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService{
    @Autowired
    private StandardRepository standardRepository;
    @Override
    public void save(Standard model) {
        standardRepository.save(model);
    }
    @Override
    public Page<Standard> findAll(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

}
  
