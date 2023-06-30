package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.User;
import com.educative.ecommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

    Optional<WishList> findByProductId(int id);

    Optional<WishList> findByUserId(int id);

//    Optional<WishList> findAllByUserIdByProductId(int userId, int productId);
}
