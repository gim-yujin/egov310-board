package egovframework.example.board.board.web;

import egovframework.example.board.board.service.BoardService;
import egovframework.example.board.board.service.BoardVO;
import egovframework.example.board.cmm.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public String list(@ModelAttribute("searchVO") BoardVO searchVO, Model model) {
        if (searchVO.getCurrentPageNo() < 1) searchVO.setCurrentPageNo(1);
        if (searchVO.getRecordCountPerPage() < 1) searchVO.setRecordCountPerPage(10);

        List<BoardVO> list = boardService.list(searchVO);
        int total = boardService.totalCount(searchVO);

        PaginationInfo p = new PaginationInfo();
        p.setCurrentPageNo(searchVO.getCurrentPageNo());
        p.setRecordCountPerPage(searchVO.getRecordCountPerPage());
        p.setPageSize(10);
        p.setTotalRecordCount(total);

        model.addAttribute("boardList", list);
        model.addAttribute("paginationInfo", p);
        return "board/list";
    }

    @GetMapping("/detail/{boardNo}")
    public String detail(@PathVariable("boardNo") Long boardNo,
                         Authentication auth, Model model) {
        BoardVO b = boardService.readAndIncreaseViewCount(boardNo);
        if (b == null) {
            model.addAttribute("errorMessage", "존재하지 않는 글입니다.");
            return "redirect:/board/list";
        }
        model.addAttribute("board", b);
        model.addAttribute("currentUser", auth != null ? auth.getName() : null);
        return "board/detail";
    }

    @GetMapping("/form")
    public String writeForm(Model model) {
        model.addAttribute("board", new BoardVO());
        model.addAttribute("mode", "write");
        return "board/form";
    }

    @PostMapping("/form")
    public String write(@ModelAttribute BoardVO board, Authentication auth) {
        board.setWriter(auth.getName());
        Long id = boardService.write(board);
        return "redirect:/board/detail/" + id;
    }

    @GetMapping("/form/{boardNo}")
    public String editForm(@PathVariable("boardNo") Long boardNo,
                           Authentication auth, Model model) {
        BoardVO b = boardService.read(boardNo);
        if (b == null || !b.getWriter().equals(auth.getName())) {
            return "redirect:/board/list";
        }
        model.addAttribute("board", b);
        model.addAttribute("mode", "edit");
        return "board/form";
    }

    @PostMapping("/form/{boardNo}")
    public String edit(@PathVariable("boardNo") Long boardNo,
                       @ModelAttribute BoardVO board,
                       Authentication auth) {
        board.setBoardNo(boardNo);
        boardService.update(board, auth.getName());
        return "redirect:/board/detail/" + boardNo;
    }

    @PostMapping("/delete/{boardNo}")
    public String delete(@PathVariable("boardNo") Long boardNo, Authentication auth) {
        boardService.delete(boardNo, auth.getName());
        return "redirect:/board/list";
    }
}
