package egovframework.example.board;

import egovframework.example.board.cmm.exception.BoardException;
import egovframework.example.board.member.security.EgovUserDetailsService;
import egovframework.example.board.member.service.MemberService;
import egovframework.example.board.member.service.MemberVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EgovUserDetailsService userDetailsService;

    private MemberVO newMember(String id) {
        MemberVO m = new MemberVO();
        m.setMemberId(id);
        m.setPassword("password1");
        m.setMemberName("테스트유저");
        m.setEmail(id + "@example.com");
        return m;
    }

    @Test
    public void join_새로운_회원은_가입에_성공한다() {
        assertTrue(memberService.join(newMember("u1")));

        MemberVO saved = memberService.findById("u1");
        assertNotNull(saved);
        assertEquals("u1",            saved.getMemberId());
        assertEquals("테스트유저",      saved.getMemberName());
        assertEquals("ROLE_USER",     saved.getRole());
    }

    @Test
    public void join_비밀번호는_BCrypt로_저장된다() {
        memberService.join(newMember("u2"));

        MemberVO saved = memberService.findById("u2");
        assertNotEquals("password1", saved.getPassword());
        assertTrue(passwordEncoder.matches("password1", saved.getPassword()));
    }

    @Test
    public void join_중복_아이디는_false를_반환한다() {
        assertTrue(memberService.join(newMember("u3")));
        assertFalse(memberService.join(newMember("u3")));
    }

    @Test(expected = BoardException.class)
    public void join_필수값_누락은_예외() {
        MemberVO m = new MemberVO();
        m.setMemberId("u4");
        memberService.join(m);
    }

    @Test
    public void isAvailableId_미사용_아이디는_true() {
        assertTrue(memberService.isAvailableId("brand-new"));
        memberService.join(newMember("taken"));
        assertFalse(memberService.isAvailableId("taken"));
    }

    @Test
    public void userDetailsService_가입한_회원을_조회한다() {
        memberService.join(newMember("u5"));
        UserDetails ud = userDetailsService.loadUserByUsername("u5");
        assertEquals("u5", ud.getUsername());
        assertTrue(ud.getAuthorities().toString().contains("ROLE_USER"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void userDetailsService_없는_회원은_예외() {
        userDetailsService.loadUserByUsername("not-exist");
    }
}
