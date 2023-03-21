package com.gxenSoft.mall.sns.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : SnsDAO
 * PACKAGE NM : com.gxenSoft.mall.sns.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 21. 
 * HISTORY :
 
 *************************************
 */
@Repository("snsDAO")
public class SnsDAO extends CommonDefaultDAO{
	
	/**
	 * @Method : snsLoginCheck
	 * @Date		: 2017. 6. 21.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 로그인 체크
	 */
	public int snsLoginCheck(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("snsDAO.snsLoginCheck", param);
	}

	/**
	 * @Method : getSnsInfo
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 정보
	 */
	public SqlMap getSnsInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("snsDAO.getSnsInfo", param);
	}
	
	/**
	 * @Method : getSnsMemberInfo
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 연동 회원 정보
	 */
	public SqlMap getSnsMemberInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("snsDAO.getSnsMemberInfo", param);
	}
	
	/**
	 * @Method : snsConnectDelete
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연동 해제
	 */
	public int snsConnectDelete(HashMap<String, Object> param) throws Exception{
		return (Integer)delete("snsDAO.snsConnectDelete", param);
	}
	
	/**
	 * @Method : snsConnectCheck
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연동 체크
	 */
	public int snsConnectCheck(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("snsDAO.snsConnectCheck", param);
	}
	
	/**
	 * @Method : snsReJoinCheck
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 재가입 체크
	 */
	public int snsReJoinCheck(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("snsDAO.snsReJoinCheck", param);
	}
}
