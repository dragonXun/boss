package com.xun.bos.service.base.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.CourierRepository;
import com.xun.bos.domain.base.Courier;
import com.xun.bos.service.base.CourierService;

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
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }
    @Override
    public void updateDelById(String[] arr) {
        for (String string : arr) {
            courierRepository.updateDelById(Long.parseLong(string));
        }
    }
    @Override
    public void updateRecById(String[] arr) {
        for (String id : arr) {
            Courier courier = courierRepository.findOne(Long.parseLong(id));
            if (courier != null) {
                courier.setDeltag(null);
            }
        }
        
    }

}
  
