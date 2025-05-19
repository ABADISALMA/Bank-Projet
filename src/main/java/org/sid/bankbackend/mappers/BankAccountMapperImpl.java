package org.sid.bankbackend.mappers;

import org.sid.bankbackend.dtos.CustomerDTO;
import org.sid.bankbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
//Mapstruct on declare juste la signature de lamethodes et la suit sera generer automatiquement de customerDTO.setId(customer.getId());
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);//spring on peux utiliser un objet BeanUtils pour copier les proprites d'un objet a un autre
        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
