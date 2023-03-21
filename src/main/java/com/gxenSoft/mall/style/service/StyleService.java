package com.gxenSoft.mall.style.service;

import java.util.List;

import com.gxenSoft.mall.style.vo.CounselVO;
import com.gxenSoft.mall.style.vo.HowtouseVO;
import com.gxenSoft.mall.style.vo.SampleVO;
import com.gxenSoft.mall.style.vo.TipVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : StyleService
    * PACKAGE NM : com.gxenSoft.mall.style.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 13. 
    * HISTORY :   
    *
    *************************************
    */
public interface StyleService {

	/**
	    * @Method : getCounselListCnt
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 리스트 총 개수
	   */
	int getCounselListCnt(CounselVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getCounselList
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 리스트
	   */
	List<SqlMap> getCounselList(CounselVO vo, SearchVO schVO) throws Exception;
	
	/**
	    * @Method : counselSave
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 신청
	   */
	int counselSave(CounselVO vo, int memberIdx) throws Exception;

	/**
	    * @Method : counselView
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 상세
	   */
	SqlMap counselView(CounselVO vo) throws Exception;
	
	/**
	    * @Method : counselDelete
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 삭제
	   */
	int counselDelete(CounselVO vo, int memberidx) throws Exception; 

	/**
	    * @Method : getSampleInfo
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 정보
	   */
	SqlMap getSampleInfo(SampleVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getSampleReplyListCnt
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 리스트 총 개수
	   */
	int getSampleReplyListCnt(SampleVO vo) throws Exception;

	/**
	    * @Method : getSampleReplyList
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 리스트
	   */
	List<SqlMap> getSampleReplyList(SampleVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : sampleReplySave
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 등록
	   */
	int sampleReplySave(SampleVO vo) throws Exception;
	
	/**
	    * @Method : sampleReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 삭제
	   */
	int sampleReplyDeleteAjax(SampleVO vo, int memberIdx) throws Exception;
	
	/**
	 * @Method : addSampleReadCnt
	 * @Date		: 2018. 4. 10.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 조회 수 증가
	 */
	void addSampleReadCnt(SampleVO vo) throws Exception;

	/**
	    * @Method : getTipListCnt
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 리스트 총 개수
	   */
	int getTipListCnt(SearchVO schVO, TipVO vo) throws Exception;

	/**
	    * @Method : getTipList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 리스트
	   */
	List<SqlMap> getTipList(SearchVO schVO, TipVO vo) throws Exception;

	/**
	    * @Method : getTipView
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 상세
	   */
	SqlMap getTipView(TipVO vo) throws Exception;

	/**
	    * @Method : getHowtouseListCnt
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 리스트 총 개수
	   */
	int getHowtouseListCnt(HowtouseVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getHowtouseList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 리스트
	   */
	List<SqlMap> getHowtouseList(HowtouseVO vo, SearchVO schVO) throws Exception;
	
	/**
	    * @Method : getHowtouseDetailMaster
	    * @Date: 2017. 7. 17.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 마스터
	   */
	SqlMap getHowtouseDetailMaster(HowtouseVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getHowtouseDetailList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 상세
	   */
	List<SqlMap> getHowtouseDetailList(HowtouseVO vo, SearchVO schVO) throws Exception;

	/**
	 * @Method : getTipBrandCnt
	 * @Date		: 2018. 9. 11.
	 * @Author	:  임  재  형 
	 * @Description	:	스타일 팁 브랜드 별 개수
	 */
	SqlMap getTipBrandCnt(SearchVO schVO, TipVO vo) throws Exception;

	/**
	 * @Method : getHowtoBrandCnt
	 * @Date		: 2018. 9. 12.
	 * @Author	:  임  재  형 
	 * @Description	:	상품 사용법 브랜드 별 개수
	 */
	SqlMap getHowtoBrandCnt(SearchVO schVO, HowtouseVO vo) throws Exception;

	/**
	 * @Method : getSampleListCnt
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 리스트 총 개수
	 */
	int getSampleListCnt(SampleVO vo) throws Exception;

	/**
	 * @Method : getSampleList
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 리스트
	 */
	List<SqlMap> getSampleList(SampleVO vo, SearchVO schVO) throws Exception;

	/**
	 * @Method : sampleApplAjax
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청
	 */
	int sampleApplAjax(SampleVO vo) throws Exception;

	/**
	 * @Method : winnerCheckAjax
	 * @Date		: 2019. 4. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	당첨여부 체크
	 */
	int winnerCheckAjax(SampleVO vo) throws Exception;
	
}
