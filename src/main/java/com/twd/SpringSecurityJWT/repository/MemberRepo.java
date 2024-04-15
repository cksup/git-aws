package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {
//    Optional<Member> findByUsername(String name);
}
