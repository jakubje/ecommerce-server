package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.AuthenticationToken;
import com.educative.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
