package egovframework.example.board.board.service;

import java.util.List;

public interface BoardService {

    Long write(BoardVO board);

    BoardVO read(Long boardNo);

    /** 조회수 +1 후 상세 조회 */
    BoardVO readAndIncreaseViewCount(Long boardNo);

    int update(BoardVO board, String editor);

    int delete(Long boardNo, String editor);

    List<BoardVO> list(BoardVO searchVO);

    int totalCount(BoardVO searchVO);
}
