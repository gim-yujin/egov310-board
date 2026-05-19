package egovframework.example.board.cmm;

public class PaginationInfo {

    private int currentPageNo = 1;
    private int recordCountPerPage = 10;
    private int pageSize = 10;
    private int totalRecordCount = 0;

    public int getFirstRecordIndex() {
        return (currentPageNo - 1) * recordCountPerPage;
    }

    public int getTotalPageCount() {
        if (totalRecordCount == 0) return 1;
        return (int) Math.ceil((double) totalRecordCount / recordCountPerPage);
    }

    public int getFirstPageNoOnPageList() {
        return ((currentPageNo - 1) / pageSize) * pageSize + 1;
    }

    public int getLastPageNoOnPageList() {
        int last = getFirstPageNoOnPageList() + pageSize - 1;
        return Math.min(last, getTotalPageCount());
    }

    public boolean isHasPreviousPage() {
        return getFirstPageNoOnPageList() > 1;
    }

    public boolean isHasNextPage() {
        return getLastPageNoOnPageList() < getTotalPageCount();
    }

    public int getCurrentPageNo()        { return currentPageNo; }
    public void setCurrentPageNo(int v)  { this.currentPageNo = Math.max(1, v); }
    public int getRecordCountPerPage()   { return recordCountPerPage; }
    public void setRecordCountPerPage(int v) { this.recordCountPerPage = v; }
    public int getPageSize()             { return pageSize; }
    public void setPageSize(int v)       { this.pageSize = v; }
    public int getTotalRecordCount()     { return totalRecordCount; }
    public void setTotalRecordCount(int v) { this.totalRecordCount = v; }
}
