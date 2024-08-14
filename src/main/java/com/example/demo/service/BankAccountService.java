package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BankAccount;
import com.example.demo.model.Customer;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<BankAccount> getAllBankAccounts(){
        return bankAccountRepository.findAll();
    }



    public BankAccount getBankAccountById(String id){
        return bankAccountRepository.findById(id).orElse(null);
       }

public ByteArrayInputStream generatePdf(BankAccount bankAccount){
  
  ByteArrayOutputStream out= new ByteArrayOutputStream();
  PdfWriter pdfWriter= new PdfWriter(out);
  PdfDocument pdfDoc= new PdfDocument(pdfWriter);
  Document document= new Document(pdfDoc);


  document.add(new Paragraph("RELEVEE D'IDENTIFICATION Bancaire RIB du compte: "+ bankAccount.getId()));
  document.add(new Paragraph("ID: " + bankAccount.getId()));
  document.add(new Paragraph("balance: " + bankAccount.getBalance()));
  document.add(new Paragraph("status: " + bankAccount.getStatus()));
  document.add(new Paragraph("currency: " + bankAccount.getCurrency()));

  document.close();

  return new ByteArrayInputStream(out.toByteArray());
}



        public BankAccount saveOrUpdateBankAccount(String customerId, BankAccount bankAccount){
          Optional<Customer> customerOptional= customerRepository.findById(customerId);

          if (customerOptional.isPresent()) {
            Customer customer=customerOptional.get();
            bankAccount.setCustomer(customer);
            return bankAccountRepository.save(bankAccount);
          }else{
            throw new RuntimeException("Customer not found");
          }  
        }

       public void deleteBankAccount(String id){
        bankAccountRepository.deleteById(id);
       }
}
