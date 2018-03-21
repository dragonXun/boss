package com.xun.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xun.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午3:19:57 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    void unbindCustomerByFixedArea(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    void bindCustomerToFixedArea(Long id, String fixedAreaId);

    Customer findByTelephone(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);

}
  
