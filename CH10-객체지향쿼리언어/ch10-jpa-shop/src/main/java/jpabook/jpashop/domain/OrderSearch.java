package jpabook.jpashop.domain;

import org.springframework.data.jpa.domain.Specification;
import static jpabook.jpashop.domain.OrderSpec.memberNameLike;
import static jpabook.jpashop.domain.OrderSpec.orderStatusEq;

public class OrderSearch {

    private String memberName;      //회원 이름
    private OrderStatus orderStatus;//주문 상태

    //Getter, Setter
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    // 검색 조건으로 Specification을 생성하는 메소드
    public Specification<Order> toSpecification() {
        Specification<Order> spec = null;
        
        if (memberName != null && !memberName.isEmpty()) {
            spec = memberNameLike(memberName);
        }
        
        if (orderStatus != null) {
            if (spec == null) {
                spec = orderStatusEq(orderStatus);
            } else {
                spec = spec.and(orderStatusEq(orderStatus));
            }
        }
        
        return spec;
    }
}
