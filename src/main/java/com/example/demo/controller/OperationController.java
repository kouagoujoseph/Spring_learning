package com.example.demo.controller;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Operation;
import com.example.demo.service.OperationService;

@RestController
@RequestMapping("/api/operation")
@CrossOrigin(origins="http://localhost:4200")
public class OperationController {


    @Autowired
    private OperationService operationService;
    
      @GetMapping
    public List<Operation> getAllCustomers(){
        return operationService.getAllOperations();
    }

    @PostMapping("/createOperation/{bankAccountId}")
    public ResponseEntity<?> createOperation(@PathVariable String bankAccountId,@RequestBody Operation operation){
      try {
        Operation savedOperation = operationService.saveOrUpdateOperation(bankAccountId, operation);
        return ResponseEntity.ok(savedOperation);
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    // return operationService.saveOrUpdateOperation(bankAccountId, operation);
    }

    @GetMapping("/getOperations/{bankAccountId}")
    public List<Operation> getOperationsByBankAccountId(@PathVariable String bankAccountId){
      return operationService.getOperationsByBankAccountId(bankAccountId);
    }


    @GetMapping("/operationsByBankAccount/pdf/{bankAccountId}")
        public ResponseEntity<InputStreamResource> generatePdfForOperations(@PathVariable String bankAccountId) throws MalformedURLException {
        ByteArrayInputStream bis = operationService.generatePdfForOperations(bankAccountId);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
    }

}
