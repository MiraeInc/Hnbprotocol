package com.gxenSoft.mall.member.service;

import java.util.List;

import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : MemberService
 * PACKAGE NM : com.gxenSoft.mall.member.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 19. 
 * HISTORY :
 
 *************************************
 */
public interface MemberService {
	
	/**
	 * @Method : duplicateCheckMemberId
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	아이디 중복 체크
	 */
	public int duplicateCheckMemberId(MemberVO vo)throws Exception;
	
	/**
	 * @Method : duplicateCheckEmail
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	이메일 중복 체크
	 */
	public int duplicateCheckEmail(MemberVO vo)throws Exception;
	
	/**
	 * @Method : duplicateCheckRecommender
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	추천인 중복 체크
	 */
	public int duplicateCheckRecommender(MemberVO vo)throws Exception;
	
	/**
	 * @Method : memberSave
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 저장
	 */
	public void memberSave(MemberVO vo)throws Exception;
	
	/**
	 * @Method : getMemberDetail
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 상세
	 */
	public SqlMap getMemberDetail(MemberVO vo) throws Exception;
	
	/**
	 * @Method : getTermsList
	 * @Date		: 2017. 7. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	약관/개인정보 리스트
	 */
	public List<SqlMap> getTermsList() throws Exception;
	
	//================================ 마이페이지 ================================
	
	/**
	 * @Method : getMemberInfo
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	보유포인트, 진행중인 주문 건
	 */
	public SqlMap getMemberInfo(MemberVO vo) throws Exception;
	
	/**
	 * @Method : memberModify
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보 수정
	 */
	public void memberModify(MemberVO vo)throws Exception;
	
	/**
	 * @Method : memberDelete
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 저장
	 */
	public int memberWithdrawSave(MemberVO vo) throws Exception;
	
	/**
	 * @Method : getMemberAccount
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 정보
	 */
	public SqlMap getMemberAccount() throws Exception;
	
	/**
	 * @Method : memberAccountSave
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 저장
	 */
	public int memberAccountSave(MemberVO vo) throws Exception;
	
	/**
	 * @Method : memberAccountDelete
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 삭제
	 */
	public int memberAccountDelete(MemberVO vo) throws Exception;
	
	/**
	 * @Method : getMemberShippingList
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 리스트
	 */
	public List<SqlMap> getMemberShippingList(MemberVO vo) throws Exception;
	
	/**
	 * @Method : getShippingInfo
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 상세
	 */
	public SqlMap getShippingDetail(MemberVO vo) throws Exception;
	
	/**
	 * @Method : memberShippingSave
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 저장/수정/삭제
	 */
	public int memberShippingSave(MemberVO vo) throws Exception;
	
	/**
	 * @Method : shippingDefalutUpdate
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	기본 배송지 설정
	 */
	public int shippingDefalutUpdate(MemberVO vo) throws Exception;
	
	/**
	 * @Method : joinCompleteDetail
	 * @Date		: 2018. 3. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 가입 완료 상세
	 */
	public SqlMap joinCompleteDetail(MemberVO vo) throws Exception;

}
