package com.xun.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xun.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午12:18:26 <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
  
