package com.gxenSoft.mall.common.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonDAO
 * PACKAGE NM : com.gatsbyMall.common.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
@Repository("commonDAO")
public class CommonDAO extends CommonDefaultDAO{
	
	/**
	 * @Method : loginCheck
	 * @Date		: 2017. 6. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	로그인 체크 
	 */
	public SqlMap loginCheck(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("commonDAO.loginCheck", param);
	}
	
	/**
	 * @Method : noLoginCheck
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비로그인 체크
	 * @HISTORY :
	 *
	 */
	public SqlMap noLoginCheck(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("commonDAO.noLoginCheck", param);
	}
	
	/**
	 * @Method : getMemberInfo
	 * @Date: 2017. 6. 21.
	 * @Author :  김  민  수
	 * @Description	:	회원 정보 가져오기
	 * @HISTORY :
	 *
	 */
	public SqlMap getMemberInfo(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("commonDAO.getMemberInfo", param);
	}
	
	/**
	 * @Method : findMemberId
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	아이디 찾기 결과
	 * @HISTORY :
	 *
	 */
	public SqlMap findMemberId(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("commonDAO.findMemberId", param);
	}
	
	/**
	 * @Method : findMemberPwd
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비밀번호 찾기 결과
	 * @HISTORY :
	 *
	 */
	public SqlMap findMemberPwd(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("commonDAO.findMemberPwd", param);
	}
	
	/**
	 * @Method : changeMemberPwd
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비밀번호 변경
	 * @HISTORY :
	 *
	 */
	public int changeMemberPwd(HashMap<String, Object> param)throws Exception{
		return (Integer)update("commonDAO.changeMemberPwd", param);
	}
	
	/**
	 * @Method : getCodeList
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	공통 코드(하위코드) 리스트	
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCodeList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCodeList", param);
	}

	/**
	 * @Method : getCate1DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	1depth 카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCate1DepthList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCate1DepthList", param);
	}
	
	/**
	 * @Method : getCate2DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	2depth 카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCate2DepthList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCate2DepthList", param);
	}
	
	/**
	 * @Method : getCate3DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	3depth 카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCate3DepthList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCate3DepthList", param);
	}

	/**
	 * @Method : getTotalCate1DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  강병철
	 * @Description	:	1depth 통합카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTotalCate1DepthList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getTotalCate1DepthList", param);
	}
	
	/**
	 * @Method : getTotalCate2DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:   강병철
	 * @Description	:	2depth 통합카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTotalCate2DepthList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getTotalCate2DepthList", param);
	}
	
	/**
	 * @Method : getTotalCate3DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  강 병 철
	 * @Description	:	3depth 통합 카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTotalCate3DepthList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getTotalCate3DepthList", param);
	}
	
	/**
	 * @Method : getBrandList
	 * @Date: 2017. 5. 24.
	 * @Author :  김  민  수
	 * @Description	:	브랜드 리스트
	 * @HISTORY :
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBrandList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getBrandList", param);
	}
	
	/**
	 * @Method : dormancyUpdate
	 * @Date		: 2017. 6. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	휴면 해제
	 */
	public int dormancyUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("commonDAO.dormancyUpdate", param);
	}
	
	/**
	 * @Method : memberLogInsert
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	로그인 이력 저장
	 */
	public int memberLogInsert(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("commonDAO.memberLogInsert", param);
	}
	
	/**
	 * @Method : memberInfoUpdate
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보 업데이트 (방문수, 방문IP, 방문일자)
	 */
	public int memberInfoUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("commonDAO.memberInfoUpdate", param);
	}
	
	/**
	 * @Method : nextChangePwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 다음에 변경
	 */
	public int nextChangePwd(HashMap<String, Object> param)throws Exception{
		return (Integer)update("commonDAO.nextChangePwd", param);
	}
	
	/**
	 * @Method : checkPwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 체크
	 */
	public int checkPwd(HashMap<String, Object> param)throws Exception{
		return (Integer)selectOne("commonDAO.checkPwd", param);
	}
	
	/**
	 * @Method : getCategoryList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	GNB 카테고리
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCategoryList()throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCategoryList", null);
	}
	
	/**
	 * @Method : getCategory3DepthList
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	3DEPTH 카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCategory3DepthList() throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCategory3DepthList", null);
	}
	
	/**
	 * @Method : getTotalCateList
	 * @Date		: 2018.08.29
	 * @Author	: 강 병 철
	 * @Description	:	통합 카테고리
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTotalCateList()throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getTotalCateList", null);
	}

	/**
	 * @Method : getTotalCategory3DepthList
	 * @Date		: 2018.08.29
	 * @Author	: 강 병 철
	 * @Description	:	통합 	3DEPTH 카테고리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTotalCategory3DepthList() throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getTotalCategory3DepthList", null);
	}
	
	/**
	 * @Method : eventBanner
	 * @Date		: 2017. 7. 10.
	 * @Author	:  유  준  철 
	 * @Description	:	이벤트 배너
	 */
	public SqlMap eventBanner(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("commonDAO.eventBanner", param);
	}
	
	/**
	 * @Method : getHtmlinfoList
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	HTML 정보관리 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHtmlinfoList() throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getHtmlinfoList", null);
	}
	
	/**
	 * @Method : getHtmlinfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	HTML 정보관리 정보
	 */
	public SqlMap getHtmlinfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("commonDAO.getHtmlinfo", param);
	}
	
	/**
	 * @Method : autoCompletResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	자동완성 상품 검색 결과
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> autoCompletProductResult(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("commonDAO.autoCompletProductResult", param);
	}

	/**
	 * @Method : autoCompletHashtagResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	자동완성 hashtag 검색 결과
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> autoCompletHashtagResult(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("commonDAO.autoCompletHashtagResult", param);
	}
	
	   /**
	    * @Method : getLastestOrderedGoods
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	WING - 최근 구매 상품 1개
	   */
	public SqlMap getLastestOrderedGoods(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("commonDAO.getLastestOrderedGoods", param);
	}
	
	/**
	 * @Method : getCartUpdateList
	 * @Date		: 2017. 8. 12.
	 * @Author	:  유  준  철 
	 * @Description	:	장바구니 업데이트 목록
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCartUpdateList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCartUpdateList", param);
	}
	
	/**
	 * @Method : getCartDeleteList
	 * @Date		: 2017. 8. 12.
	 * @Author	:  유  준  철 
	 * @Description	:	장바구니 삭제 목록
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCartDeleteList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCartDeleteList", param);
	}
	
	/**
	 * @Method : cartUpdate
	 * @Date		: 2017. 8. 12.
	 * @Author	:  유  준  철 
	 * @Description	:	장바구니 업데이트
	 */
	public int cartUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("commonDAO.cartUpdate", param);
	}
	
	/**
	 * @Method : cartDelete
	 * @Date		: 2017. 8. 12.
	 * @Author	:  유  준  철 
	 * @Description	:	장바구니 삭제
	 */
	public int cartDelete(HashMap<String, Object> param)throws Exception{
		return (Integer)delete("commonDAO.cartDelete", param);
	}

	/**
	    * @Method : getMainBannerList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	메인 배너 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainBannerList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainBannerList", param);
	}

	/**
	    * @Method : getPcMidBanner
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	PC 중간 배너
	   */
	public SqlMap getPcMidBanner(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getPcMidBanner", param);
	}

	/**
	    * @Method : getMoMidBanner
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	MO 중간 배너
	   */
	public SqlMap getMoMidBanner(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getMoMidBanner", param);
	}

	/**
	    * @Method : getMainGoodsList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	세트, 베스트 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainGoodsList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainGoodsList", param);
	}

	/**
	    * @Method : getTipsList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	TIPS 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTipsList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getTipsList", param);
	}

	/**
	    * @Method : getReviewList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	후기 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getReviewList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getReviewList", param);
	}

	/**
	    * @Method : getEventList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getEventList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getEventList", param);
	}

	/**
	    * @Method : getSourceBanner
	    * @Date: 2017. 8. 12.
	    * @Author : 임  재  형
	    * @Description	:	제품 소개
	   */
	public SqlMap getSourceBanner(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getSourceBanner", param);
	}
	
	/**
	 * @Method : getWingCouponList
	 * @Date		: 2017. 8. 15.
	 * @Author	:  유  준  철 
	 * @Description	:	윙의 쿠폰 정보
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getWingCouponList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getWingCouponList", param);
	}

	/**
	    * @Method : getMainGoodsListCnt
	    * @Date: 2017. 8. 23.
	    * @Author : 임  재  형
	    * @Description	:	스타일 셋 / 핫픽 리스트 총 개수
	   */
	public int getMainGoodsListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("commonDAO.getMainGoodsListCnt", param);
	}

	/**
	    * @Method : getBannerWing
	    * @Date: 2017. 8. 28.
	    * @Author : 임  재  형
	    * @Description	:	윙 배너 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBannerWing(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getBannerWing", param);
	}

	/**
	    * @Method : getPopupList
	    * @Date: 2017. 9. 1.
	    * @Author : 임  재  형
	    * @Description	:	팝업 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getPopupList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getPopupList", param);
	}

	/**
	    * @Method : getBannerHeader
	    * @Date: 2017. 9. 11.
	    * @Author : 임  재  형
	    * @Description	:	띠배너
	   */
	public SqlMap getBannerHeader(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getBannerHeader", param);
	}
	
	/**
	 * @Method : getCate1DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	1뎁스 카테고리 세트 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCate1DepthSetList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCate1DepthSetList", param);
	}
	
	/**
	 * @Method : getCate2DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	2뎁스 카테고리 세트 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCate2DepthSetList(HashMap<String, Object> param)throws Exception{
		return (List<SqlMap>)selectList("commonDAO.getCate2DepthSetList", param);
	}

	/**
	 * @Method : getMainHashtagList
	 * @Date		: 2018. 2. 12.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 해시태그 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainHashtagList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainHashtagList", param);
	}

	/**
	 * @Method : getNoticeList
	 * @Date		: 2018. 2. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	공지사항 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getNoticeList() throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getNoticeList", null);
	}
	
	/**
	 * @Method : callSrcPathProcedure
	 * @Date		: 2018. 4. 11.
	 * @Author	:  유  준  철 
	 * @Description	:	유입경로 프로시저 호출
	 */
	public int callSrcPathProcedure(HashMap<String, Object> param)throws Exception{
		return (Integer)update("commonDAO.callSrcPathProcedure", param);
	}
	
	/**
	 * @Method : goodsViewUpdate
	 * @Date		: 2018. 4. 12.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 뷰 테이블 업데이트 (유입경로)
	 */
	public int goodsViewUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("commonDAO.goodsViewUpdate", param);
	}

	/**
	 * @Method : callSpBonoLoading
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 노출 여부
	 */
	public SqlMap callSpBonoLoading(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.callSpBonoLoading", param);
	}

	/**
	 * @Method : callSpBonoClick
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 클릭
	 */
	public SqlMap callSpBonoClick(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.callSpBonoClick", param);
	}

	/**
	 * @Method : getMemberPoint
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	회원 포인트 정보
	 */
	public SqlMap getMemberPoint(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getMemberPoint", param);
	}

	/**
	 * @Method : memberPointUpdate
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	회원 포인트 업데이트
	 */
	public int memberPointUpdate(HashMap<String, Object> param) throws Exception {
		return (Integer)update("commonDAO.memberPointUpdate", param);
	}
	
	/**
	    * @Method : getBannerOne
	    * @Date: 2018. 8. 22.
	    * @Author : 임  재  형
	    * @Description	:	배너 1개만 가져오기
	   */
	public SqlMap getBannerOne(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getBannerOne", param);
	}
	
	
	/***************************** 2018 리뉴얼 메인 *****************************/
	
	
	/**
	 * @Method : getMainProductList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainProductList", param);
	}

	/**
	 * @Method : getMainHtml
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 HTML
	 */
	public SqlMap getMainHtml(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getMainHtml", param);
	}
	
	/**
	    * @Method : getMainReviewList
	    * @Date: 2018. 8. 23.
	    * @Author : 임  재  형
	    * @Description	:	메인 후기 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainReviewList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainReviewList", param);
	}

	/**
	 * @Method : getMainSnsList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 SNS 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainSnsList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainSnsList", param);
	}

	/**
	 * @Method : getMainTipList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 Tip 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainTipList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainTipList", param);
	}

	/**
	 * @Method : getMainEventList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 이벤트 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainEventList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("commonDAO.getMainEventList", param);
	}
	
	/**
	 * @Method : callSpSmsSend
	 * @Date		: 2018. 8. 29.
	 * @Author	:  임  재  형 
	 * @Description	:	휴대폰 임시비밀번호 발송
	 */
	public int callSpSmsSend(HashMap<String, Object> param) throws Exception {
		return (Integer)update("commonDAO.callSpSmsSend", param);
	}

	/**
	 * @Method : getFloatPopup
	 * @Date		: 2018. 9. 17.
	 * @Author	:  임  재  형 
	 * @Description	:	PC 플로팅 팝업
	 */
	public SqlMap getFloatPopup(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("commonDAO.getFloatPopup", param);
	}
	
	/**
	 * @Method : dormancyDelete
	 * @Date		: 2019. 11. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	휴면 삭제
	 */
	public int dormancyDelete(HashMap<String, Object> param)throws Exception{
		return (Integer)delete("commonDAO.dormancyDelete", param);
	}
	
	
	/**
	 * @Method : getFileInfo
	 * @Date		: 2020. 01. 14.
	 * @Description	:	파일 다운로드시 파일정보 가지고 오기 
	 */
	public SqlMap getFileInfo(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("commonDAO.getFileInfo", param);
	}
	
}
