package com.gxenSoft.mall.common.service;

import java.util.HashMap;
import java.util.List;

import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.vo.CommonVO;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonService
 * PACKAGE NM : com.gatsbyMall.common.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
public interface CommonService {
	
	/**
	 * @Method : loginCheck
	 * @Date		: 2017. 6. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	로그인 체크 
	 */
	public int loginCheck(MemberVO vo)throws Exception;
	
	/**
	 * @Method : noLoginCheck
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비회원 로그인 체크
	 * @HISTORY :
	 *
	 */
	public int noLoginCheck(OrderVO vo)throws Exception;
	
	/**
	 * @Method : findMemberId
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	아이디 찾기 결과
	 * @HISTORY :
	 *
	 */
	public SqlMap findMemberId(MemberVO vo)throws Exception;
	
	/**
	 * @Method : findMemberPwd
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비밀번호 찾기 결과	
	 * @HISTORY : 
	 *
	 */
	public SqlMap findMemberPwd(MemberVO vo)throws Exception;
	 
	
	/**
	 * @Method : getCodeList
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	공통 코드(하위) 리스트
	 */
	public List<SqlMap> getCodeList(String commonCd)throws Exception;
	
	/**
	 * @Method : getCate1DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	1Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate1DepthList(int brandIdx)throws Exception;
	
	/**
	 * @Method : getCate2DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	2Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate2DepthList(SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getCate3DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	3Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate3DepthList(SchProductVO schProductVo)throws Exception;

	/**
	 * @Method : getTotalCate1DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  강병철 
	 * @Description	:	1Depth 통합 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getTotalCate1DepthList() throws Exception;
	
	/**
	 * @Method : getTotalCate2DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	: 강 병 철
	 * @Description	:	2Depth 통합 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getTotalCate2DepthList(SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getTotalCate3DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  강 병 철
	 * @Description	:	3Depth 통합 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getTotalCate3DepthList(SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getBrandList
	 * @Date: 2017. 5. 24.
	 * @Author :  김  민  수
	 * @Description	:	브랜드 리스트
	 * @HISTORY :
	 *
	 */
	public List<SqlMap> getBrandList()throws Exception;
	
	/**
	 * @Method : getUserInfo
	 * @Date		: 2017. 6. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	로그인 유저 정보
	 */
	public SqlMap getUserInfo(MemberVO vo)throws Exception;
	
	/**
	 * @Method : dormancySave
	 * @Date		: 2017. 6. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	휴면 해제
	 */
	public int dormancySave(MemberVO vo)throws Exception;
	
	/**
	 * @Method : nextChangePwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 다음에 변경
	 */
	public int nextChangePwd(MemberVO vo)throws Exception;
	
	/**
	 * @Method : checkPwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 체크
	 */
	public int checkPwd(MemberVO vo)throws Exception;
	
	/**
	 * @Method : changePwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 변경
	 */
	public int changePwd(MemberVO vo)throws Exception;
	
	/**
	 * @Method : getCategoryList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	GNB 카테고리
	 */
	public List<SqlMap> getCategoryList() throws Exception;
	
	/**
	 * @Method : getCategory3DepthList
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	3DEPTH 카테고리 리스트
	 */
	public List<SqlMap> getCategory3DepthList() throws Exception;

	/**
	 * @Method : getTotalCategory3DepthList
	 * @Date		: 2018. 08. 29.
	 * @Author	:  강 병 철
	 * @Description	: 통합 3DEPTH 카테고리 리스트
	 */
	public List<SqlMap> getTotalCategory3DepthList() throws Exception ;
	/**
	 * @Method : getTotalCateList
	 * @Date		: 2018. 08. 29.
	 * @Author	:  강 병 철
	 * @Description	:	통합 카테고리
	 */
	public List<SqlMap> getTotalCateList() throws Exception;
	
	/**
	 * @Method : eventBanner
	 * @Date		: 2017. 7. 10.
	 * @Author	:  유  준  철 
	 * @Description	:	이벤트 배너
	 */
	public SqlMap eventBanner(String bannerPos)throws Exception;
	
	/**
	 * @Method : getHtmlinfoList
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	HTML 정보관리 리스트
	 */
	public List<SqlMap> getHtmlinfoList() throws Exception;
	
	/**
	 * @Method : getHtmlinfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	HTML 정보관리
	 */
	public SqlMap getHtmlinfo(String gubun)throws Exception;

	/**
	 * @Method : autoCompletProductResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	자동완성 결과
	 */
	public List<SqlMap> autoCompletProductResult(String query)throws Exception;
	
	/**
	 * @Method : autoCompletHashtagResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	자동완성 hashtag 결과
	 */
	public List<SqlMap> autoCompletHashtagResult(String query)throws Exception;
	
	   /**
	    * @Method : getLastestOrderedGoods
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	WING - 최근 구매 상품 1개
	   */
	public SqlMap getLastestOrderedGoods(OrderVO vo) throws Exception;

	/**
	    * @Method : getMainBannerList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	메인 배너 리스트
	   */
	public List<SqlMap> getMainBannerList(String gubun) throws Exception;

	/**
	    * @Method : getPcMidBanner
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	PC 중간 배너
	   */
	public SqlMap getPcMidBanner(String gubun) throws Exception;

	/**
	    * @Method : getMoMidBanner
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	MO 중간 배너
	   */
	public SqlMap getMoMidBanner(String gubun) throws Exception;

	/**
	    * @Method : getMainGoodsList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	세트, 베스트 리스트
	   */
	public List<SqlMap> getMainGoodsList(String gubun, int mainGubun, SearchVO schVO) throws Exception;

	/**
	    * @Method : getTipsList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	TIPS 리스트
	   */
	public List<SqlMap> getTipsList(int mainGubun) throws Exception;

	/**
	    * @Method : getReviewList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	후기 리스트
	   */
	public List<SqlMap> getReviewList(int mainGubun) throws Exception;

	/**
	    * @Method : getEventList
	    * @Date: 2017. 8. 11.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 리스트
	   */
	public List<SqlMap> getEventList() throws Exception;

	/**
	    * @Method : getSourceBanner
	    * @Date: 2017. 8. 12.
	    * @Author : 임  재  형
	    * @Description	:	제품 소개
	   */
	public SqlMap getSourceBanner(int mainGubun) throws Exception;
	
	/**
	    * @Method : getWingCoupon
	    * @Date: 2017. 8. 12.
	    * @Author : 강 병 철
	    * @Description	:	윙의 쿠폰 정보
	   */
	public List<SqlMap> getWingCouponList(HashMap<String, Object> map) throws Exception;

	/**
	    * @Method : getMainGoodsListCnt
	    * @Date: 2017. 8. 23.
	    * @Author : 임  재  형
	    * @Description	:	스타일 셋 / 핫픽 리스트 총 개수
	   */
	public int getMainGoodsListCnt(String gubun, int mainGubun) throws Exception;
	
	/**
	    * @Method : getBannerWing
	    * @Date: 2017. 8. 28.
	    * @Author : 임  재  형
	    * @Description	:	윙 배너 리스트
	   */
	public List<SqlMap> getBannerWing(String gubun) throws Exception;

	/**
	    * @Method : getPopupList
	    * @Date: 2017. 9. 1.
	    * @Author : 임  재  형
	    * @Description	:	팝업 리스트
	   */
	public List<SqlMap> getPopupList() throws Exception;

	/**
	    * @Method : getBannerHeader
	    * @Date: 2017. 9. 11.
	    * @Author : 임  재  형
	    * @Description	:	띠배너
	   */
	public SqlMap getBannerHeader(String bannerPos) throws Exception;
	
	/**
	 * @Method : getCate1DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	1뎁스 카테고리 세트 리스트
	 */
	public List<SqlMap> getCate1DepthSetList()throws Exception;
	
	/**
	 * @Method : getCate2DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	2뎁스 카테고리 세트 리스트
	 */
	public List<SqlMap> getCate2DepthSetList()throws Exception;

	/**
	 * @Method : getMainHashtagList
	 * @Date		: 2018. 2. 12.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 해시태그 리스트
	 */
	public List<SqlMap> getMainHashtagList(int mainGubun) throws Exception;

	/**
	 * @Method : getNoticeList
	 * @Date		: 2018. 2. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	공지사항 리스트
	 */
	public List<SqlMap> getNoticeList() throws Exception;
	
	/**
	 * @Method : getSrcPathSave
	 * @Date		: 2018. 4. 11.
	 * @Author	:  유  준  철 
	 * @Description	:	유입 경로 저장
	 */
	public void getSrcPathSave(CommonVO vo)throws Exception;

	/**
	 * @Method : getBonoYn
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 노출 여부
	 */
	public SqlMap getBonoYn() throws Exception;

	/**
	 * @Method : bonoClick
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 클릭
	 */
	public SqlMap bonoClick() throws Exception;

	/**
	 * @Method : bonoPointSave
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 포인트 지급
	 */
	public int bonoPointSave(String reason, int bonoPoint) throws Exception;
	
	/**
	    * @Method : getBannerOne
	    * @Date: 2018. 8. 22.
	    * @Author : 임  재  형
	    * @Description	:	배너 1개만 가져오기
	   */
	public SqlMap getBannerOne(String bannerPos) throws Exception;

	
	/***************************** 2018 리뉴얼 메인 *****************************/
	
	
	/**
	 * @Method : getMainProductList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 상품 리스트
	 */
	public List<SqlMap> getMainProductList(String mainGubun, String gubun) throws Exception;

	/**
	 * @Method : getMainHtml
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 HTML
	 */
	public SqlMap getMainHtml(String mainGubun, String gubun) throws Exception;

	/**
	 * @Method : getMainReviewList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 후기 리스트
	 */
	public List<SqlMap> getMainReviewList(String mainGubun, String gubun) throws Exception;

	/**
	 * @Method : getMainSnsList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 SNS 리스트
	 */
	public List<SqlMap> getMainSnsList(String mainGubun) throws Exception;

	/**
	 * @Method : getMainTipList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 Tip 리스트
	 */
	public List<SqlMap> getMainTipList(String mainGubun, String gubun) throws Exception;

	/**
	 * @Method : getMainEventList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 이벤트 리스트
	 */
	public List<SqlMap> getMainEventList(String mainGubun) throws Exception;

	/**
	 * @Method : getFloatPopup
	 * @Date		: 2018. 9. 17.
	 * @Author	:  임  재  형 
	 * @Description	:	PC 플로팅 팝업
	 */
	public SqlMap getFloatPopup(String popGubun) throws Exception;
	
	/**
	 * @Method : getFileInfo
	 * @Date		: 2020. 01. 14.
	 * @Description	: 파일다운로드시 파일정보
	 */
	public SqlMap getFileInfo(FileVO vo)throws Exception;
	
	
}
