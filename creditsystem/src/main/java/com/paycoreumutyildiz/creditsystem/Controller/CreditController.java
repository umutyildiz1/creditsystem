package com.paycoreumutyildiz.creditsystem.Controller;

import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/credit/")
public class CreditController {

    private final CreditService creditService;

    @Autowired
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Credit>> getAllCredits(){
        return ResponseEntity.ok(creditService.getAllCredits());
    }

    @GetMapping("{sid}")
    public ResponseEntity<Credit> getCredit(@PathVariable @Min(1) Long sid){
        return ResponseEntity.ok(creditService.getCredit(sid));
    }

    @PostMapping("create")
    public void createCredit(@RequestBody @Valid Credit credit){
        creditService.addCredit(credit);
    }

    @PutMapping("update")
    public ResponseEntity<Credit> updateCredit(@RequestBody Credit credit){
        return ResponseEntity.ok(creditService.updateCredit(credit));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Boolean> deleteCredit(@RequestParam @Min(1) Long sid){
        return ResponseEntity.ok(creditService.deleteCredit(sid));
    }

    @GetMapping("query")
    public ResponseEntity<Map<String,String>> queryCredit(@RequestParam @Min(1) Long sid){
        return ResponseEntity.ok(creditService.queryCredit(sid));
    }
}
