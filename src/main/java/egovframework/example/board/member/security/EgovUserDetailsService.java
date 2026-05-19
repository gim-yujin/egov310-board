package egovframework.example.board.member.security;

import egovframework.example.board.member.service.MemberService;
import egovframework.example.board.member.service.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("egovUserDetailsService")
public class EgovUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Autowired
    public EgovUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberVO m = memberService.findById(memberId);
        if (m == null) {
            throw new UsernameNotFoundException("회원이 존재하지 않습니다: " + memberId);
        }
        return new EgovUserDetails(m);
    }
}
