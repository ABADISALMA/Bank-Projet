package org.sid.bankbackend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.bankbackend.Exeption.CustomerNotFoundException;
import org.sid.bankbackend.dtos.CustomerDTO;
import org.sid.bankbackend.entities.Customer;
import org.sid.bankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j

//@RequestMapping("/customers")//c'est pour acceder au controller

public class CustomerRestController {

    private BankAccountService bankAccountService;
    @GetMapping ("/customers")
    public List<CustomerDTO> Customers() {
       return  bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")//toute fct de consultation on utilise getmap
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long costumerId) throws CustomerNotFoundException {
            return  bankAccountService.getCustomer(costumerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {//requestbody pour indiquer au spring que les donner seront recupperer format json dans la requette
            return  bankAccountService.saveCostomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId ,@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerDTO.setId(customerId);
        return  bankAccountService.updateCostomer(customerDTO);
    }

    @DeleteMapping("/cutomers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }
}

