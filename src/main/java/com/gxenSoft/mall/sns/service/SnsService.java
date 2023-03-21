package com.gxenSoft.mall.sns.service;

import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : SnsService
 * PACKAGE NM : com.gxenSoft.mall.sns.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 21. 
 * HISTORY :
 
 *************************************
 */
public interface SnsService {
	
	/**
	 * @Method : snsLoginCheck
	 * @Date		: 2017. 6. 21.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 로그인 체크
	 */
	public int snsLoginCheck(MemberVO vo)throws Exception;
	
	/**
	 * @Method : snsConnectLogin
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 연동 로그인 처리
	 */
	public int snsConnectLogin(MemberVO vo)throws Exception;
	
	/**
	 * @Method : snsJoinCheck
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 가입 체크
	 */
	public int snsJoinCheck(MemberVO vo)throws Exception;
	
	/**
	 * @Method : snsConnectSave
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연동
	 */
	public void snsConnectSave(MemberVO vo)throws Exception;
	
	/**
	 * @Method : getSnsInfo
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 정보
	 */
	public SqlMap getSnsInfo(MemberVO vo) throws Exception;
	
	/**
	 * @Method : snsConnectDelete
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연동 해제
	 */
	public int snsConnectDelete(MemberVO vo)throws Exception;
	
	/**
	 * @Method : snsConnectCheck
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연동 체크
	 */
	public int snsConnectCheck(MemberVO vo)throws Exception;
	
	/**
	 * @Method : getSnsMemberInfo
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 연동 회원 정보
	 */
	public SqlMap getSnsMemberInfo(MemberVO vo) throws Exception;
	
	/**
	 * @Method : snsWithdrawCheck
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 재가입 체크
	 */
	public int snsReJoinCheck(MemberVO vo)throws Exception;
}
