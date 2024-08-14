package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BankAccount;
import com.example.demo.model.Operation;
import com.example.demo.model.opType;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.OperationRepository;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public Operation saveOrUpdateOperation(String bankAccountId, Operation operation){
      Optional<BankAccount> bankAccountOptional=bankAccountRepository.findById(bankAccountId);
      if (bankAccountOptional.isPresent()) {
        BankAccount bankAccount=bankAccountOptional.get();
        operation.setBankAccount(bankAccount);
        if (operation.getType()==opType.DEBIT) {
            if(bankAccount.getBalance()>=operation.getAmount()){
                double balanceDispo=(bankAccount.getBalance()-operation.getAmount());
                bankAccount.setBalance(balanceDispo);
                bankAccountRepository.save(bankAccount);
                return operationRepository.save(operation);
            }else{
              throw new Exception("Le solde disponible est insuffisant pour passer cette opération");
            }
        }
        if (operation.getType()==opType.CREDIT) {
            double balanceDispo=(bankAccount.getBalance()+operation.getAmount());
            bankAccount.setBalance(balanceDispo);
            bankAccountRepository.save(bankAccount);
            return operationRepository.save(operation);
        }
    

      }else{
        throw new RuntimeException("BankAccount not exist");
      }

      return operation;
    }


    public List<Operation> getAllOperations(){
        return operationRepository.findAll();
    }

    public List<Operation> getOperationsByBankAccountId(String bankAccountId){
      return operationRepository.findByBankAccountId(bankAccountId);
    }

    public ByteArrayInputStream  generatePdfForOperations(String bankAccountId) throws MalformedURLException{
      List<Operation> operations=getOperationsByBankAccountId(bankAccountId);
      ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document= new Document(pdfDoc);

        String imagePath="src\\main\\resources\\asset\\imageboa.jpeg";
        ImageData imageData=ImageDataFactory.create(imagePath);
        Image image=new Image(imageData);
        image.setFixedPosition(30, 750);
        image.scaleToFit(100, 100);
        document.add(image);

        document.add(new Paragraph(" ").setMarginTop(30));

        document.add(new Paragraph("Operations effectuées sur le compte N°: " + bankAccountId)
                                 .setFontSize(18)
                                 .setBold()
                                 .setMarginTop(20)
                                 .setFixedPosition(0, 0, null)
                                .setFontColor(ColorConstants.BLUE));



        document.add(new Paragraph(" ").setMarginTop(30));
                                
        float[] columnWidths = {4, 4, 4, 4}; 
        Table table = new Table(columnWidths);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setFontSize(16);
        // table.addCell(new Cell().add(new Paragraph("Id Opération")));
        // table.addCell(new Cell().add(new Paragraph("Date Opération")));
        // table.addCell(new Cell().add(new Paragraph("Solde")));
        // table.addCell(new Cell().add(new Paragraph("Type Opération")));
  table.addCell(new Cell().add(new Paragraph("Id Opération"))
            .setBackgroundColor(ColorConstants.DARK_GRAY)
            .setFontColor(ColorConstants.WHITE)
            .setTextAlignment(TextAlignment.CENTER)
            .setPadding(5));
    table.addCell(new Cell().add(new Paragraph("Date Opération"))
            .setBackgroundColor(ColorConstants.DARK_GRAY)
            .setFontColor(ColorConstants.WHITE)
            .setTextAlignment(TextAlignment.CENTER)
            .setPadding(5));
    table.addCell(new Cell().add(new Paragraph("Montant"))
            .setBackgroundColor(ColorConstants.DARK_GRAY)
            .setFontColor(ColorConstants.WHITE)
            .setTextAlignment(TextAlignment.CENTER)
            .setPadding(5));
    table.addCell(new Cell().add(new Paragraph("Type Opération"))
            .setBackgroundColor(ColorConstants.DARK_GRAY)
            .setFontColor(ColorConstants.WHITE)
            .setTextAlignment(TextAlignment.CENTER)
            .setPadding(5));


        for(Operation operation:operations){
          table.addCell(new Cell().add(new Paragraph(String.valueOf(operation.getId()))));
          table.addCell(new Cell().add(new Paragraph(String.valueOf(operation.getDateOp()))));
          table.addCell(new Cell().add(new Paragraph(String.valueOf(operation.getAmount()))));
          table.addCell(new Cell().add(new Paragraph(String.valueOf(operation.getType()))));

        }

        document.add(table);

        
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }


}
