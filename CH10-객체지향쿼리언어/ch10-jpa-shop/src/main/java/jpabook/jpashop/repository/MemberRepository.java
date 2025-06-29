package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

// @Repository 어노테이션이 붙어 있으면
// 1. 스프링 빈으로 자동 등록: <context:component-scan>에 의해
// 2. 예외 변환(AOP 기반) 기능: 예외를 try-catch로 일일이 잡지 않아도 스프링이 공통적으로 처리 가능한 예외 타입으로 감싸줌
@Repository
public class MemberRepository {

    // 순수 자바 환경에서는 EntityManagerFactory에서 직접 EntityManager를 생성해서 사용함
    // ex. EntityManager em = emf.createEntityManager();
    // @PersistenceContext를 통해 주입받아 사용하면 직접 생성 하지 않아도 됨
    // 만약 EntityManagerFactory 자체가 필요하다면 @PersistenceUnit 어노테이션을 사용해서 주입받을 수 있음
    @PersistenceContext
    EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
