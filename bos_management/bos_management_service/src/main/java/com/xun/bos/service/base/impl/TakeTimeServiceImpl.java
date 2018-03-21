package com.xun.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.TakeTimeRepository;
import com.xun.bos.domain.base.TakeTime;
import com.xun.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午3:45:48 <br/>       
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
          
        return takeTimeRepository.findAll();
    }
}
  
