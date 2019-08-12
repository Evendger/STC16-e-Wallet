package com.controllers;

import com.entity.BankBill;
import com.entity.Claim;
import com.entity.User;
import com.repository.BankBillRepository;
import com.repository.UserRepository;
import com.serviceImpl.ClaimServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    ClaimServiceImpl service;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BankBillRepository bankBillRepository;


    @GetMapping("/index")
    public String index(){
        return "client";
    }


    @PostMapping()
    public String  createClaim(Principal principal, @RequestParam("bankBillId") long bankBillId){
        User user = userRepository.findUserByName(principal.getName());
	    BankBill bankBill = bankBillRepository.findDistinctById(bankBillId);
	    service.addClaim(user, bankBill);
	    return "claimApply";
    }

    @GetMapping("/all")
    public String addClaimIndex(Model model){
        model.addAttribute("bankBillList", service.getAllBills());
        return "bankBillList";
    }

    @GetMapping
    public List<Claim> getAllUserClaims(Principal principal){
        User user = userRepository.findUserByName(principal.getName());
        return service.getAllUserClaims(user);
    }

    @GetMapping("/{id}")
    public Claim getClaim(@PathVariable long id){
        return service.getClaimById(id);
    }

    @PostMapping("/remove")
    public void removeClaim(@RequestParam("id") int id){
        service.removeClaimById(id);
    }
}
