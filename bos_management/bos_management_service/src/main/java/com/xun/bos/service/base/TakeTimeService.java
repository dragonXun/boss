package com.xun.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xun.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午3:45:29 <br/>       
 */
public interface TakeTimeService {

    List<TakeTime> findAll();

    Page<TakeTime> findAll(Pageable pageable);

}
  
