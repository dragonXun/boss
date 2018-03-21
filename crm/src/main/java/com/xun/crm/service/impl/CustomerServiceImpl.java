package com.xun.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xun.crm.dao.CustomerRepository;
import com.xun.crm.domain.Customer;
import com.xun.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午3:22:15 <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
          
        return customerRepository.findAll();
    }
    @Override
    public List<Customer> findUnAssociatedCustomers() {
          
        return customerRepository.findByFixedAreaIdIsNull();
    }
    @Override
    public List<Customer> findAssociatedCustomers(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }
    @Override
    public void assignCustomers2FixedArea(Long[] customerIds, String fixedAreaId) {
        if (StringUtils.isNotEmpty(fixedAreaId)) {
            customerRepository.unbindCustomerByFixedArea(fixedAreaId);
        }
        if (customerIds != null && customerIds.length > 0) {
            for (Long id : customerIds) {
                customerRepository.bindCustomerToFixedArea(id,fixedAreaId);
            }
        }
    }
    @Override
    public void save(Customer customer) {
          
        customerRepository.save(customer);
    }
    @Override
    public Customer isActived(String telephone) {
          
        return customerRepository.findByTelephone(telephone);
    }
    @Override
    public void active(String telephone) {
          
        Customer customer = customerRepository.findByTelephone(telephone);
        if (customer != null) {
            customer.setType(1);
        }
    }
    @Override
    public Customer login(String telephone, String password) {
          
        return customerRepository.findByTelephoneAndPassword(telephone,password);
    }

}
  
