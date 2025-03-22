package main.start;

import jakarta.persistence.*;
import java.util.List;
import start.Member;

// 코드는 크게 3부분으로 나뉜다.
// 엔티티 매니저 설정, 트랜잭션 관리, 비즈니스 로직.
public class JpaMain {

    public static void main(String[] args) {

        // 1. 엔티티 매니저 설정
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook"); // 엔티티 매니저 팩토리 생성
        EntityManager em = emf.createEntityManager(); // 엔티티 매니저 생성

        // 2. 트랜잭션 관리
        EntityTransaction tx = em.getTransaction(); // JPA를 사용하면 항상 트랜잭션 안에서 데이터를 변경해야한다. 그렇지 않으면, 예외가 발생한다.

        try {

            tx.begin(); // 트랜잭션 시작
            logic(em);  // 비즈니스 로직
            tx.commit();// 트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // 예외 발생 시 트랜잭션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }

        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    // 3. 비즈니스 로직
    public static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        // 생성: persist()를 호출하면 JPA가 INSERT SQL을 자동 실행한다.
        em.persist(member);

        // 수정: update()와 같은 별도의 수정 메서드는 없다.
        // JPA는 엔티티의 변경사항을 추적하는 기능이 있으므로, 값이 변경되면 UPDATE SQL이 실행된다.
        member.setAge(20);

        // 1건 조회(PK로 조회): 조회 메서드를 실행하면 JPA가 SELECT SQL을 자동 실행한다.
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        // 목록 조회(JPQL): 조회 메서드를 실행하면 JPA가 SELECT SQL을 자동 실행한다.
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        // 삭제: remove()를 호출하면 JPA가 DELETE SQL을 자동 실행한다.
        em.remove(member);

    }
}
