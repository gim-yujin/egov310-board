package egovframework.example.board.member.service.impl;

import egovframework.example.board.member.service.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {

    int insertMember(MemberVO member);

    MemberVO selectMemberById(@Param("memberId") String memberId);

    int countById(@Param("memberId") String memberId);
}
