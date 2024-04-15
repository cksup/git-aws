package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.entity.Member;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;

import com.twd.SpringSecurityJWT.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();
        try {
            OurUsers ourUsers = new OurUsers();
            ourUsers.setUsername(registrationRequest.getUsername());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            if (registrationRequest.getUsername() == "ADMIN"){
                ourUsers.setRole("ADMIN");}
            else{
                ourUsers.setRole("USER");}
            //           ourUsers.setRole(registrationRequest.getRole());

            var user = ourUserRepo.findByUsername(registrationRequest.getUsername());
 //           System.out.println("user========="+user);
            if (user.isPresent()) {
 //               System.out.println("isPresent() "+user);
                //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
                resp.setStatusCode(400);
                resp.setMessage("이미 등록된 Username입니다.");
            }
            else {
                OurUsers ourUserResult = ourUserRepo.save(ourUsers);

                if (ourUserResult != null && ourUserResult.getId() > 0) {
                    resp.setOurUsers(ourUserResult);
                    resp.setMessage("User Saved Successfully");
                    resp.setStatusCode(200);
                }
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
 //       System.out.println("resp.getstatusCode========="+resp.getStatusCode());
        return resp;
    }

    public ReqRes signIn(ReqRes signinRequest){
        ReqRes response = new ReqRes();

        try {
            response.setUsername(signinRequest.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),signinRequest.getPassword()));
            var user = ourUserRepo.findByUsername(signinRequest.getUsername()).orElseThrow();
 //           System.out.println("USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
 //           response.setUsername(signinRequest.getUsername());
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest) {
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        OurUsers users = ourUserRepo.findByUsername(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }

    public ReqRes newMember(ReqRes memberRequest) {
        ReqRes response = new ReqRes();
        try {
            Member member = new Member();
 //           var user = memberRepo.findByUsername(memberRequest.getUsername());
            member.setName(memberRequest.getName());
            member.setFather(memberRequest.getFather());
            member.setAddress(memberRequest.getAddress());
            member.setTelno(memberRequest.getTelno());
            member.setNumberth(memberRequest.getNumberth());
            member.setBirthday(memberRequest.getBirthday());
            Member memberResult = memberRepo.save(member);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }
}
