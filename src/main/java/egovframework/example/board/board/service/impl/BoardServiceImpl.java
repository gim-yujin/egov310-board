package egovframework.example.board.board.service.impl;

import egovframework.example.board.board.service.BoardService;
import egovframework.example.board.board.service.BoardVO;
import egovframework.example.board.cmm.exception.BoardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

    private final BoardDAO boardDAO;

    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    @Override
    @Transactional
    public Long write(BoardVO board) {
        validate(board);
        if (!StringUtils.hasText(board.getWriter())) {
            throw new BoardException("작성자가 없습니다.");
        }
        boardDAO.insertBoard(board);
        return board.getBoardNo();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardVO read(Long boardNo) {
        if (boardNo == null) return null;
        return boardDAO.selectBoard(boardNo);
    }

    @Override
    @Transactional
    public BoardVO readAndIncreaseViewCount(Long boardNo) {
        if (boardNo == null) return null;
        boardDAO.increaseViewCount(boardNo);
        return boardDAO.selectBoard(boardNo);
    }

    @Override
    @Transactional
    public int update(BoardVO board, String editor) {
        if (board == null || board.getBoardNo() == null) {
            throw new BoardException("수정 대상 글이 없습니다.");
        }
        BoardVO existing = boardDAO.selectBoard(board.getBoardNo());
        if (existing == null) {
            throw new BoardException("존재하지 않는 글입니다.");
        }
        if (!existing.getWriter().equals(editor)) {
            throw new BoardException("작성자만 수정할 수 있습니다.");
        }
        validate(board);
        board.setWriter(existing.getWriter());
        return boardDAO.updateBoard(board);
    }

    @Override
    @Transactional
    public int delete(Long boardNo, String editor) {
        BoardVO existing = boardDAO.selectBoard(boardNo);
        if (existing == null) {
            throw new BoardException("존재하지 않는 글입니다.");
        }
        if (!existing.getWriter().equals(editor)) {
            throw new BoardException("작성자만 삭제할 수 있습니다.");
        }
        return boardDAO.deleteBoard(boardNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardVO> list(BoardVO searchVO) {
        normalizePaging(searchVO);
        return boardDAO.selectBoardList(searchVO);
    }

    @Override
    @Transactional(readOnly = true)
    public int totalCount(BoardVO searchVO) {
        return boardDAO.selectBoardCount(searchVO == null ? new BoardVO() : searchVO);
    }

    private void validate(BoardVO b) {
        if (b == null
                || !StringUtils.hasText(b.getTitle())
                || !StringUtils.hasText(b.getContent())) {
            throw new BoardException("제목/내용은 필수입니다.");
        }
        if (b.getTitle().length() > 200) {
            throw new BoardException("제목은 200자 이내여야 합니다.");
        }
    }

    private void normalizePaging(BoardVO v) {
        if (v.getCurrentPageNo() < 1) v.setCurrentPageNo(1);
        if (v.getRecordCountPerPage() < 1) v.setRecordCountPerPage(10);
        v.setFirstIndex((v.getCurrentPageNo() - 1) * v.getRecordCountPerPage());
    }
}
