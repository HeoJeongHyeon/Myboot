package my.myjwt.domain.repository;

import my.myjwt.domain.dao.Member;
import my.myjwt.domain.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);

    boolean existsByRole(Role role);
}
