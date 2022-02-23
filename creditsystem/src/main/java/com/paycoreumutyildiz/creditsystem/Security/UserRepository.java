package com.paycoreumutyildiz.creditsystem.Security;

import com.paycoreumutyildiz.creditsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    boolean existsBySid(Long sid);
    User findBySid(Long sid);
    void deleteBySid(Long sid);
    boolean existsByUsername(String username);
    User findByUsername(String username);
    void deleteByUsername(String username);
}
