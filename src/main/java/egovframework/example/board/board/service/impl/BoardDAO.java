package egovframework.example.board.board.service.impl;

import egovframework.example.board.board.service.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDAO {

    int insertBoard(BoardVO board);

    BoardVO selectBoard(@Param("boardNo") Long boardNo);

    int updateBoard(BoardVO board);

    int deleteBoard(@Param("boardNo") Long boardNo);

    int increaseViewCount(@Param("boardNo") Long boardNo);

    List<BoardVO> selectBoardList(BoardVO searchVO);

    int selectBoardCount(BoardVO searchVO);
}
