package com.xun.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xun.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午3:21:20 <br/>       
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerService {
    @GET
    @Path("/findAll")
    public List<Customer> findAll();
   
    @POST
    @Path("/save")
    public void save(Customer customer);
    
    @GET
    @Path("/findUnAssociatedCustomers")
    public List<Customer> findUnAssociatedCustomers();
    
    @GET
    @Path("/findAssociatedCustomers")
    public List<Customer> findAssociatedCustomers(@QueryParam("fixedAreaId") String fixedAreaId);
   
    @PUT
    @Path("/assignCustomers2FixedArea")
    public void assignCustomers2FixedArea(@QueryParam("customerIds") Long[] customerIds,
            @QueryParam("fixedAreaId") String fixedAreaId);
    @GET
    @Path("/isActived")
    public Customer isActived(@QueryParam("telephone") String telephone);

    @GET
    @Path("/login")
    public Customer login(@QueryParam("telephone") String telephone,
            @QueryParam("password") String password);
    
    @PUT
    @Path("/active")
    public void active(@QueryParam("telephone") String telephone);
}
  
