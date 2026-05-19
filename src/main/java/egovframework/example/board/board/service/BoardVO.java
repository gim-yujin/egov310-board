package egovframework.example.board.board.service;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long boardNo;
    private String title;
    private String content;
    private String writer;
    private int viewCnt;
    private Timestamp regDt;
    private Timestamp updDt;

    /* 페이징/검색 */
    private int currentPageNo = 1;
    private int recordCountPerPage = 10;
    private int firstIndex = 0;
    private String searchKeyword;

    public Long getBoardNo()                  { return boardNo; }
    public void setBoardNo(Long v)            { this.boardNo = v; }
    public String getTitle()                  { return title; }
    public void setTitle(String v)            { this.title = v; }
    public String getContent()                { return content; }
    public void setContent(String v)          { this.content = v; }
    public String getWriter()                 { return writer; }
    public void setWriter(String v)           { this.writer = v; }
    public int getViewCnt()                   { return viewCnt; }
    public void setViewCnt(int v)             { this.viewCnt = v; }
    public Timestamp getRegDt()               { return regDt; }
    public void setRegDt(Timestamp v)         { this.regDt = v; }
    public Timestamp getUpdDt()               { return updDt; }
    public void setUpdDt(Timestamp v)         { this.updDt = v; }

    public int getCurrentPageNo()             { return currentPageNo; }
    public void setCurrentPageNo(int v)       { this.currentPageNo = v; }
    public int getRecordCountPerPage()        { return recordCountPerPage; }
    public void setRecordCountPerPage(int v)  { this.recordCountPerPage = v; }
    public int getFirstIndex()                { return firstIndex; }
    public void setFirstIndex(int v)          { this.firstIndex = v; }
    public String getSearchKeyword()          { return searchKeyword; }
    public void setSearchKeyword(String v)    { this.searchKeyword = v; }
}
