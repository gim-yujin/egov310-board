package egovframework.example.board.member.security;

import egovframework.example.board.member.service.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EgovUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final MemberVO member;

    public EgovUserDetails(MemberVO member) {
        this.member = member;
    }

    public MemberVO getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole()));
    }

    @Override public String  getPassword()                { return member.getPassword(); }
    @Override public String  getUsername()                { return member.getMemberId(); }
    @Override public boolean isAccountNonExpired()        { return true; }
    @Override public boolean isAccountNonLocked()         { return true; }
    @Override public boolean isCredentialsNonExpired()    { return true; }
    @Override public boolean isEnabled()                  { return true; }
}
