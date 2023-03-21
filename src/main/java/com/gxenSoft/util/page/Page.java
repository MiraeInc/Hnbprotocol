package com.gxenSoft.util.page;

import com.gxenSoft.method.ConvertUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : Page
 * PACKAGE NM : com.gxenSoft.util.page
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 10. 
 * HISTORY :
 
 *************************************
 */
public class Page {
	
	private int pageSize; 											// 페이지 수
    private int pageBlock;										// 게시글 수
    private int prevPageNo; 									// 이전 페이지 번호
    private int pageNo; 											// 페이지 번호		
    private int nextPageNo; 									// 다음 페이지 번호
    private int finalPageNo; 									// 마지막 페이지 번호
    private int totalCount; 										// 게시글 전체 수
    private String pageStr;										// 페이징 	

    /**
     * @return the pageStr
     */
    public String getPageStr() {
        return pageStr;
    }

    /**
     * @param pageStr the pageStr to set
     */
    public void setPageStr(String pageStr) {
        this.pageStr = pageStr;
    }
    
    
    public int getPageBlock() {
		return pageBlock;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	/**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the prevPageNo
     */
    public int getPrevPageNo() {
        return prevPageNo;
    }

    /**
     * @param prevPageNo the prevPageNo to set
     */
    public void setPrevPageNo(int prevPageNo) {
        this.prevPageNo = prevPageNo;
    }

    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return the nextPageNo
     */
    public int getNextPageNo() {
        return nextPageNo;
    }

    /**
     * @param nextPageNo the nextPageNo to set
     */
    public void setNextPageNo(int nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    /**
     * @return the finalPageNo
     */
    public int getFinalPageNo() {
        return finalPageNo;
    }

    /**
     * @param finalPageNo the finalPageNo to set
     */
    public void setFinalPageNo(int finalPageNo) {
        this.finalPageNo = finalPageNo;
    }

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
	/**
     * @Method : pagingInfo
     * @Date		: 2017. 7. 11.
     * @Author	:  유  준  철 
     * @Description	:	페이징 값
     */
    public void pagingInfo(SearchVO searchVO , int totalCount)throws Exception{
    	this.pageNo = searchVO.getPageNo();
    	this.pageSize = searchVO.getPageSize();
    	this.pageBlock = searchVO.getPageBlock();
    	this.totalCount = totalCount;
    	this.paging();
    }
   
    /**
     * @Method : paging
     * @Date		: 2017. 7. 11.
     * @Author	:  유  준  철 
     * @Description	:	페이징 처리
     */
    private void paging() {
    	 if (this.totalCount == 0) return; 												// 게시 글 전체 수가 없는 경우
         if (this.pageNo == 0) this.setPageNo(1); 								// 페이지 번호 (기본 값)
         if (this.pageSize == 0) this.setPageSize(5); 							// 페이지 수 (기본 값)
         if (this.pageBlock == 0) this.setPageBlock(12); 					// 게시글 수 (기본 값)
         
         int totalPage = totalCount / pageBlock;			
         
         if (totalCount % pageBlock > 0) {
             totalPage++;
         }

         if (totalPage < pageNo) {
        	 pageNo = totalPage;
         }

         int startPage = ((pageNo - 1) / pageSize) * pageSize + 1;			
         int endPage = startPage + pageSize - 1;
         
         if (endPage > totalPage) {
    	    endPage = totalPage;
         }
         
         this.setFinalPageNo(endPage);
         
         int finalPage = (totalCount + (pageBlock - 1)) / pageBlock; 
         if (this.pageNo > finalPage) this.setPageNo(finalPage); 

         if (this.pageNo < 0 || this.pageNo > finalPage) this.pageNo = 1; 

         boolean isNowFirst = pageNo == 1 ? true : false; 
         boolean isNowFinal = pageNo == finalPage ? true : false; 

         if (isNowFirst) {
             this.setPrevPageNo(1); 
         } else {
         	this.setPrevPageNo((   (((pageNo-1)/pageSize)*pageSize) <= 1 ? 1 : (((pageNo-1)/pageSize)*pageSize))   ); 
         }

         if (isNowFinal) {
             this.setNextPageNo(finalPage); 
         } else {
             this.setNextPageNo((  (((pageNo-1)/pageSize+1)*pageSize)+1 >= finalPage ? finalPage : (((pageNo-1)/pageSize+1)*pageSize)+1)   ); 
         }

         this.setFinalPageNo(finalPage); 
         
         StringBuffer bf = new StringBuffer();
         
         bf.append("<ul>");
         
         if(pageNo > pageSize){
         	bf.append("<li class='pagin-left'><a href='javascript:' onclick='goPage("+prevPageNo+");'><span>이전(블럭단위)</span></a></li> ");			// 이전 페이지 블럭 단위
         	bf.append("<li class='pagin-prev'><a href='javascript:' onclick='goPage("+(pageNo-1)+");'><span>이전</span></a></li> ");							// 이전 페이지
         }else{
        	 if(pageNo != 1){
        		 bf.append("<li class='pagin-left'></li> ");			// 이전 페이지 블럭 단위
        		 bf.append("<li class='pagin-prev'><a href='javascript:' onclick='goPage("+(pageNo-1)+");'><span>이전</span></a></li> ");						// 이전 페이지
        	 }else{
        		 bf.append("<li class='pagin-left'></li> ");
        	 }
         }
         
         for (int count = startPage; count <= endPage; count++) {
    	    if (count == pageNo) {
    	    	bf.append("<li class='active'><a href='javascript:'><span>"+ConvertUtil.formatNumber(count)+"</span></a></li> ");	
    	    } else {
    	    	bf.append("<li><a href='javascript:' onclick='goPage("+count+");'><span>"+ConvertUtil.formatNumber(count)+"</span></a></li> ");
    	    }
         }
         
         if(nextPageNo < finalPageNo){
        	 bf.append("<li class='pagin-next'><a href='javascript:' onclick='goPage("+(pageNo+1)+");'><span>다음</span></a></li> ");							// 다음 페이지
         	bf.append("<li class='pagin-right'><a href='javascript:' onclick='goPage("+nextPageNo+");'><span>다음(블럭단위)</span></a></li> ");			// 다음 페이지 블럭 단위
         }else{
        	 if(pageNo != totalPage){
        		 bf.append("<li class='pagin-next'><a href='javascript:' onclick='goPage("+(pageNo+1)+");'><span>다음</span></a></li> ");						// 다음 페이지
        		 bf.append("<li class='pagin-right'></li> ");			// 다음 페이지 블럭 단위
        	 }
         }
        
         bf.append("</ul>");
         this.setPageStr(bf.toString());
    }
}
