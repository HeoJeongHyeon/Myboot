package my.mysecurity.repository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRepository {
    private final Map<String, UserDetails> users = new HashMap<>();

    public UserDetails findByUsername(String username) {
        return users.get(username);
    }

    public void save(UserDetails user) {
        users.put(user.getUsername(), user);
    }

    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    public List<UserDetails> findAll() {
        return new ArrayList<>(users.values());
    }
}