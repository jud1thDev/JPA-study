package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    // 저장과 수정(병합)을 다 처리함!!!
    // 이 메소드를 사용하는 클라이언트는 저장과 수정을 구분하지 않아도 되어 클라이언트의 로직이 단순해짐
    // 영속 상태의 엔티티는 변경 감지 기능이 동작해서 트랜잭션을 커밋할 때 자동으로 수정되므로 별도의 수정 메소드를 호출할 필요가 없음
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
