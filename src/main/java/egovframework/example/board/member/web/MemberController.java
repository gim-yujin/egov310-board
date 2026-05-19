package egovframework.example.board.member.web;

import egovframework.example.board.member.service.MemberService;
import egovframework.example.board.member.service.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return "member/loginForm";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberVO", new MemberVO());
        return "member/joinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberVO memberVO, Model model) {
        boolean ok = memberService.join(memberVO);
        if (!ok) {
            model.addAttribute("memberVO", memberVO);
            model.addAttribute("joinError", "이미 사용 중인 아이디입니다.");
            return "member/joinForm";
        }
        return "redirect:/member/login";
    }

    @GetMapping("/idcheck")
    @ResponseBody
    public String idCheck(@RequestParam("memberId") String memberId) {
        return memberService.isAvailableId(memberId) ? "OK" : "DUP";
    }
}
