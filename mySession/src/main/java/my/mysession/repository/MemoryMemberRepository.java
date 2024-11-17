package my.mysession.repository;

import lombok.extern.slf4j.Slf4j;
import my.mysession.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class MemoryMemberRepository implements MemberRepository {

    private static final Map<String, Member> store = new ConcurrentHashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getEmail(), member);
        log.info("login info = email:{}, password:{}", member.getEmail(), member.getPassword());

    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @Override
    public void delete(String email) {
        store.remove(email);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 테스트용 데이터 초기화
    public void clearStore() {
        store.clear();
    }
}
