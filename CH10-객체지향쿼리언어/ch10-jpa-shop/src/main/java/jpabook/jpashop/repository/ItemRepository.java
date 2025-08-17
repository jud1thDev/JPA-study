package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameContaining(String name);
}
