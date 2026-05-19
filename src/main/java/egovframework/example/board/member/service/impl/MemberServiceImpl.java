package egovframework.example.board.member.service.impl;

import egovframework.example.board.cmm.exception.BoardException;
import egovframework.example.board.member.service.MemberService;
import egovframework.example.board.member.service.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO, PasswordEncoder passwordEncoder) {
        this.memberDAO = memberDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean join(MemberVO member) {
        if (member == null
                || !StringUtils.hasText(member.getMemberId())
                || !StringUtils.hasText(member.getPassword())
                || !StringUtils.hasText(member.getMemberName())
                || !StringUtils.hasText(member.getEmail())) {
            throw new BoardException("필수 입력값이 비어있습니다.");
        }
        if (!isAvailableId(member.getMemberId())) {
            return false;
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        if (!StringUtils.hasText(member.getRole())) {
            member.setRole("ROLE_USER");
        }
        return memberDAO.insertMember(member) == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberVO findById(String memberId) {
        if (!StringUtils.hasText(memberId)) return null;
        return memberDAO.selectMemberById(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAvailableId(String memberId) {
        if (!StringUtils.hasText(memberId)) return false;
        return memberDAO.countById(memberId) == 0;
    }
}
