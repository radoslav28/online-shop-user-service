package com.onlineshop.user.persistence.repositories;

import com.onlineshop.user.persistence.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    Optional<CartItem> findByUserIdAndItemId (UUID userId, UUID itemId);

    List<CartItem> findByUserId (UUID userId);

    void deleteByUserId (UUID userId);

    void deleteByUserIdAndItemId (UUID userId, UUID itemId);
}
