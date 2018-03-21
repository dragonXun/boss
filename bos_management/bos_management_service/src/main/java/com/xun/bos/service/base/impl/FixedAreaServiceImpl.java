package com.xun.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.bos.dao.CourierRepository;
import com.xun.bos.dao.FixedAreaRepository;
import com.xun.bos.dao.SubAreaRepository;
import com.xun.bos.dao.TakeTimeRepository;
import com.xun.bos.domain.base.Courier;
import com.xun.bos.domain.base.FixedArea;
import com.xun.bos.domain.base.TakeTime;
import com.xun.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:16:33 <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
   
    @Autowired
    private CourierRepository courierRepository;
    
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public void save(FixedArea model) {
          
        fixedAreaRepository.save(model);
    }

    @Override
    public void associationCourierToFixedArea(Long id, Long takeTimeId, Long courierId) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        fixedArea.getCouriers().add(courier);
        courier.setTakeTime(takeTime);
    }

    @Override
    public void associationFixedAreaToSubArea(Long id, Long[] subAreaIds) {
        if (id != null) {
            subAreaRepository.unbindSubAreaByFixedArea(id);
        }
        if (subAreaIds != null && subAreaIds.length > 0) {
            for (Long subAreaId : subAreaIds) {
                subAreaRepository.bindSubAreaByFixedArea(subAreaId,id);
            }
        }
    }
}
  
