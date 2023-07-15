package com.mycompany.datagrapher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class DataGraphController {
    private final ProcessData processData;
    @Autowired
    public DataGraphController(ProcessData processData) {
        this.processData = processData;
    }

    @PostMapping(value = "/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody String userInput) {
        if (!StringUtils.hasText(userInput)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!isValidInput(userInput)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

         String sanitizedInput = sanitizeInput(userInput);
         try {
             byte[] pdfBytes = processData.convertTextToPDF(sanitizedInput);
             HttpHeaders headers = new HttpHeaders();
             headers.setContentType(MediaType.APPLICATION_PDF);
             headers.setContentDispositionFormData("attachment", "data_graph.pdf");
             return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

         } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

         }
    }
     private boolean isValidInput(String userInput) {
            return userInput != null && !userInput.isEmpty();
     }
     private String sanitizeInput(String userInput) {
            return userInput.replaceAll("[^a-zA-Z0-9]", "");
     }
}

