package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 테스트 실행 시 스프링 컨테이너 함께 로딩
// 설정 정보는 appConfig.xml만 사용 (web 관련 설정은 필요 없음)

@ContextConfiguration(locations = "classpath:appConfig.xml")
// appConfig.xml에서 설정한 빈들을 로딩해서 테스트에 주입

@Transactional
// 테스트마다 트랜잭션 시작 -> 끝나면 자동 롤백
// DB에 실제로 저장되지 않아서 테스트 반복 가능

public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given: 회원 하나 생성
        Member member = new Member();
        member.setName("kim");

        // when: 회원 가입
        Long saveId = memberService.join(member);

        // then: 저장된 회원과 동일한지 확인
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given: 이름이 같은 회원 두 명
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when: 첫 번째 회원 가입 후, 두 번째 회원 가입 시도
        memberService.join(member1);
        memberService.join(member2); // 여기서 예외 발생해야 함

        // then: 예외 발생 안 하면 실패
        fail("예외가 발생해야 한다.");
    }
}