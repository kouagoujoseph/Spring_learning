package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BankAccount;

import com.example.demo.model.Operation;
import com.example.demo.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/bankAccount")
@CrossOrigin(origins="http://localhost:4200")
public class BankAccountController {

@Autowired
private BankAccountService bankAccountService;

@GetMapping("getAllBanks")
public List<BankAccount> getAllBankAccounts() {
    return bankAccountService.getAllBankAccounts();
}

  @GetMapping("/{id}")
    public BankAccount getCustomerById(@PathVariable String id){
        return bankAccountService.getBankAccountById(id); 
    }

  @GetMapping("/pdf/{id}")
   public ResponseEntity<InputStreamResource> generatePdf(@PathVariable String id){
    BankAccount bankAccount=bankAccountService.getBankAccountById(id);

    if(bankAccount==null){
        return ResponseEntity.notFound().build();
    }

    ByteArrayInputStream bis=bankAccountService.generatePdf(bankAccount);
   
    return ResponseEntity.ok()
                          .contentType(MediaType.APPLICATION_PDF)
                          .body(new InputStreamResource(bis));


   }
  

    @PostMapping("/createBankAccount/{customerId}")
    public BankAccount createBankAccount(@PathVariable String customerId, @RequestBody BankAccount bankAccount){
       return bankAccountService.saveOrUpdateBankAccount(customerId, bankAccount);
    };

    @GetMapping("/{bankAccountId}/getAllOperations")
    public Set<Operation> getAllOperationByBankAccount(@PathVariable String bankAccountId, Set<Operation> operations){
         BankAccount bankAccount= bankAccountService.getBankAccountById(bankAccountId);
         return bankAccount.getOperations();
    }

    @DeleteMapping("{bankAccountId}")
    public void deleteBankAccount(@PathVariable String bankAccountId){
        bankAccountService.deleteBankAccount(bankAccountId);
    }




}
