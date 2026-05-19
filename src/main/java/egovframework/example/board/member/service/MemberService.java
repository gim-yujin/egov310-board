package egovframework.example.board.member.service;

public interface MemberService {

    /** 회원가입: 비밀번호 해시 후 저장. 이미 존재하는 ID면 false. */
    boolean join(MemberVO member);

    /** 단건 조회. 없으면 null. */
    MemberVO findById(String memberId);

    /** ID 중복 체크. true면 사용 가능. */
    boolean isAvailableId(String memberId);
}
