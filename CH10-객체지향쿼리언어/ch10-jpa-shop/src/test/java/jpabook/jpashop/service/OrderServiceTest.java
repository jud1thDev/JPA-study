package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
// 테스트 실행 시 스프링 컨테이너 로딩

@ContextConfiguration(locations = "classpath:appConfig.xml")
// 설정 정보 로딩 (appConfig.xml)

@Transactional
// 각 테스트 후 트랜잭션 롤백 -> 반복 테스트 가능

public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given: 회원과 상품 생성
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        // when: 상품 주문
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then: 주문 정보 검증
        Order getOrder = orderRepository.findById(orderId).orElse(null);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * 2, getOrder.getTotalPrice());
        assertEquals(8, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given: 재고 10인 상품에 11개 주문
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        // when: 주문 실행 → 예외 발생 예상
        orderService.order(member.getId(), item.getId(), 11);

        // then: 예외 안 나면 실패
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() {
        // given: 주문 먼저 수행
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when: 주문 취소
        orderService.cancelOrder(orderId);

        // then: 상태와 재고 복구 확인
        Order getOrder = orderRepository.findById(orderId).orElse(null);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(10, item.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}

