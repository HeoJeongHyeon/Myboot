package my.mysession.repository;

import my.mysession.domain.Member;

import java.util.List;
import java.util.Optional;


public interface MemberRepository{
    void save(Member member);
    Optional<Member> findByEmail(String email);
    void delete(String email);
    List<Member> findAll();
}
