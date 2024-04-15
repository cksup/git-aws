package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.Member;
import com.twd.SpringSecurityJWT.exception.MemberNotFoundException;
import com.twd.SpringSecurityJWT.repository.MemberRepo;
import com.twd.SpringSecurityJWT.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepo memberRepo;

    @PostMapping("/join")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/savemember")
    public ResponseEntity<ReqRes> newmember(@RequestBody ReqRes memberRequest){
        return ResponseEntity.ok(authService.newMember(memberRequest));
    }

    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    @GetMapping("/member/{id}")
    public Member getMemberById(@PathVariable Integer id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    @PutMapping("/member/{id}")
    public Member updateUser(@RequestBody Member newUser, @PathVariable Integer id) {

        return memberRepo.findById(id)
                .map(member -> {
                    member.setName(newUser.getName());
                    member.setFather(newUser.getFather());
                    member.setAddress(newUser.getAddress());
                    member.setTelno(newUser.getTelno());
                    member.setNumberth(newUser.getNumberth());
                    member.setBirthday(newUser.getBirthday());
                    return memberRepo.save(member);
                }).orElseThrow(() -> new MemberNotFoundException(id));
    }

    @DeleteMapping("/member/{id}")
    public String deleteMember(@PathVariable Integer id){
        if(!memberRepo.existsById(id)){
            throw new MemberNotFoundException(id);
        }
        memberRepo.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }
}
