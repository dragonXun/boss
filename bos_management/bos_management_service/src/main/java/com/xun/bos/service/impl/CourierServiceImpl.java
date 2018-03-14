package com.xun.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.CourierRepository;
import com.xun.bos.domain.base.Courier;
import com.xun.bos.service.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:14:37 <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    @Override
    public void save(Courier model) {
        courierRepository.save(model);
    }
    @Override
    public Page<Courier> findAll(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
    @Override
    public void updateDelById(String[] arr) {
        for (String string : arr) {
            courierRepository.updateDelById(Long.parseLong(string));
        }
    }

}
  
