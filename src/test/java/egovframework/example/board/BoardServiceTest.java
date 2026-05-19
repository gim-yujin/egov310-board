package egovframework.example.board;

import egovframework.example.board.board.service.BoardService;
import egovframework.example.board.board.service.BoardVO;
import egovframework.example.board.cmm.exception.BoardException;
import egovframework.example.board.member.service.MemberService;
import egovframework.example.board.member.service.MemberVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
public class BoardServiceTest {

    @Autowired private BoardService boardService;
    @Autowired private MemberService memberService;

    @Before
    public void setup() {
        MemberVO m = new MemberVO();
        m.setMemberId("writer1");
        m.setPassword("password1");
        m.setMemberName("작성자1");
        m.setEmail("w1@example.com");
        memberService.join(m);

        MemberVO m2 = new MemberVO();
        m2.setMemberId("writer2");
        m2.setPassword("password1");
        m2.setMemberName("작성자2");
        m2.setEmail("w2@example.com");
        memberService.join(m2);
    }

    private BoardVO sample(String title, String writer) {
        BoardVO b = new BoardVO();
        b.setTitle(title);
        b.setContent("내용 " + title);
        b.setWriter(writer);
        return b;
    }

    @Test
    public void write_그리고_read() {
        Long id = boardService.write(sample("첫 글", "writer1"));
        assertNotNull(id);

        BoardVO saved = boardService.read(id);
        assertEquals("첫 글", saved.getTitle());
        assertEquals("writer1", saved.getWriter());
        assertEquals(0, saved.getViewCnt());
    }

    @Test
    public void readAndIncreaseViewCount는_조회수를_1증가시킨다() {
        Long id = boardService.write(sample("조회수 테스트", "writer1"));
        BoardVO b1 = boardService.readAndIncreaseViewCount(id);
        BoardVO b2 = boardService.readAndIncreaseViewCount(id);
        assertEquals(1, b1.getViewCnt());
        assertEquals(2, b2.getViewCnt());
    }

    @Test
    public void update_제목과_내용을_갱신한다() {
        Long id = boardService.write(sample("원본", "writer1"));
        BoardVO upd = new BoardVO();
        upd.setBoardNo(id);
        upd.setTitle("수정됨");
        upd.setContent("수정 내용");

        int n = boardService.update(upd, "writer1");
        assertEquals(1, n);

        BoardVO after = boardService.read(id);
        assertEquals("수정됨", after.getTitle());
        assertEquals("수정 내용", after.getContent());
        assertNotNull(after.getUpdDt());
    }

    @Test(expected = BoardException.class)
    public void update_다른_사용자는_수정할수없다() {
        Long id = boardService.write(sample("권한 테스트", "writer1"));
        BoardVO upd = new BoardVO();
        upd.setBoardNo(id);
        upd.setTitle("탈취 시도");
        upd.setContent("탈취");
        boardService.update(upd, "writer2");
    }

    @Test
    public void delete_작성자만_삭제할수있다() {
        Long id = boardService.write(sample("삭제 테스트", "writer1"));
        int n = boardService.delete(id, "writer1");
        assertEquals(1, n);
        assertNull(boardService.read(id));
    }

    @Test(expected = BoardException.class)
    public void delete_타인은_삭제할수없다() {
        Long id = boardService.write(sample("타인 삭제 시도", "writer1"));
        boardService.delete(id, "writer2");
    }

    @Test(expected = BoardException.class)
    public void write_제목_누락은_예외() {
        BoardVO b = new BoardVO();
        b.setContent("내용만 있음");
        b.setWriter("writer1");
        boardService.write(b);
    }

    @Test
    public void list와_totalCount_페이징_검색() {
        for (int i = 1; i <= 15; i++) {
            boardService.write(sample("글" + i, i % 2 == 0 ? "writer1" : "writer2"));
        }

        BoardVO search = new BoardVO();
        search.setCurrentPageNo(1);
        search.setRecordCountPerPage(10);

        List<BoardVO> page1 = boardService.list(search);
        assertEquals(10, page1.size());
        assertEquals(15, boardService.totalCount(search));

        search.setCurrentPageNo(2);
        List<BoardVO> page2 = boardService.list(search);
        assertEquals(5, page2.size());

        // 검색
        BoardVO s = new BoardVO();
        s.setSearchKeyword("글1");
        s.setCurrentPageNo(1);
        s.setRecordCountPerPage(10);
        int hits = boardService.totalCount(s);
        // 글1, 글10, 글11, 글12, 글13, 글14, 글15
        assertEquals(7, hits);
    }
}
