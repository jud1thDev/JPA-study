package jpabook.jpashop.service;

import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by holyeye on 2014. 3. 12..
 */

@Service
@Transactional
public class OrderService {

    @Autowired MemberRepository memberRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemService itemService;

    /** 주문 */
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));
        Item item = itemService.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery(member.getAddress());
        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }


    /** 주문 취소 */
    public void cancelOrder(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다. id=" + orderId));

        //주문 취소
        order.cancel();
    }

                    /** 주문 검색 */
                public List<Order> findOrders(OrderSearch orderSearch) {
                    // Spring Data JPA의 기본 findAll() 메소드 사용
                    // 실제 검색 로직은 필요에 따라 구현
                    return orderRepository.findAll();
                }

}
