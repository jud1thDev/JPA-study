package main.start;
import start.Member;

import jakarta.persistence.*;

public class JpaMain {
    static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        Member member = createMember("memberA", "회원1"); // 1. 영속성 컨텍스트1에서 저장 후 종료
        member.setUsername("회원명변경"); // 2. 준영속 상태에서 이름 변경
        mergeMember(member); // 3. 영속성 컨텍스트2에서 병합
    }

    // 영속성 컨텍스트1: 회원 생성 및 저장 후 준영속 상태로 반환
    static Member createMember(String id, String username) {
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();

        tx1.begin();
        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setAge(25);
        em1.persist(member);
        tx1.commit();

        em1.close(); // 영속성 컨텍스트1 종료 → member는 준영속 상태가 됨

        return member;
    }

    // 영속성 컨텍스트2: 준영속 상태의 member 병합
    static void mergeMember(Member member) {
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        Member mergeMember = em2.merge(member); // 병합 실행
        tx2.commit(); // 병합된 mergeMember가 DB에 반영됨

        // 준영속 상태 출력
        System.out.println("member = " + member.getUsername());

        // 병합된 영속 상태 출력
        System.out.println("mergeMember = " + mergeMember.getUsername());

        // 영속성 컨텍스트 포함 여부 확인
        System.out.println("em2 contains member = " + em2.contains(member));         // false
        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember)); // true

        em2.close(); // 영속성 컨텍스트2 종료
    }
}

