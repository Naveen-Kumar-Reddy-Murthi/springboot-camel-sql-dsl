package com.mnr.camel;

import com.mnr.camel.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepoistory extends JpaRepository<Order, Integer> {
}
