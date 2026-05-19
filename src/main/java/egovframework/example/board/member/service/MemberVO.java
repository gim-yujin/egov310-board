package egovframework.example.board.member.service;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String memberId;
    private String password;
    private String memberName;
    private String email;
    private String role = "ROLE_USER";
    private Timestamp regDt;

    public String getMemberId()              { return memberId; }
    public void setMemberId(String v)        { this.memberId = v; }
    public String getPassword()              { return password; }
    public void setPassword(String v)        { this.password = v; }
    public String getMemberName()            { return memberName; }
    public void setMemberName(String v)      { this.memberName = v; }
    public String getEmail()                 { return email; }
    public void setEmail(String v)           { this.email = v; }
    public String getRole()                  { return role; }
    public void setRole(String v)            { this.role = v; }
    public Timestamp getRegDt()              { return regDt; }
    public void setRegDt(Timestamp v)        { this.regDt = v; }
}
