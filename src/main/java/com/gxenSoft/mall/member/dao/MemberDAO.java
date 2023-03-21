package com.gxenSoft.mall.member.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : MemberDAO
 * PACKAGE NM : com.gxenSoft.mall.member.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 19. 
 * HISTORY :
     
 *************************************
 */
@Repository("memberDAO")
public class MemberDAO extends CommonDefaultDAO{
	
	/**
	 * @Method : duplicateCheckMemberId
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	아이디 중복 체크
	 */
	public int duplicateCheckMemberId(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("memberDAO.duplicateCheckMemberId", param);
	}
	
	/**
	 * @Method : duplicateCheckEmail
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	이메일 중복 체크
	 */
	public int duplicateCheckEmail(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("memberDAO.duplicateCheckEmail", param);
	}
	
	/**
	 * @Method : duplicateCheckRecommender
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	추천인 중복 체크
	 */
	public int duplicateCheckRecommender(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("memberDAO.duplicateCheckRecommender", param);
	}
	
	/**
	 * @return 
	 * @Method : memberMasterSave
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 저장 (MASTER)
	 */
	public int memberMasterSave(HashMap<String, Object> param)throws Exception{
		return insert("memberDAO.memberMasterSave", param);
	}
	
	/**
	 * @Method : memberDetailSave
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 저장 (DETAIL)
	 */
	public int memberDetailSave(HashMap<String, Object> param)throws Exception{
		return insert("memberDAO.memberDetailSave", param);
	}
	
	/**
	 * @Method : memberPointSave
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 포인트 지급
	 */
	public int memberPointSave(HashMap<String, Object> param)throws Exception{
		return insert("memberDAO.memberPointSave", param);
	}
	
	/**
	 * @Method : memberInfoSave
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 정보 저장
	 */
	public int memberInfoSave(HashMap<String, Object> param)throws Exception{
		return insert("memberDAO.memberInfoSave", param);
	}
	
	/**
	 * @Method : memberInfoUpdate
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 정보 업데이트
	 */
	public int memberInfoUpdate(HashMap<String, Object> param)throws Exception{
		return update("memberDAO.memberInfoUpdate", param);
	}
	
	/**
	 * @Method : getMemberInfoPoint
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 포인트 정보
	 */
	public SqlMap getMemberInfoPoint(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("memberDAO.getMemberInfoPoint", param);
	}
	
	/**
	 * @Method : getMemberDetail
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 상세
	 */
	public SqlMap getMemberDetail(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("memberDAO.getMemberDetail", param);
	}
	
	/**
	 * @Method : memberSnsSave
	 * @Date		: 2017. 6. 21.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS가입 저장
	 */
	public int memberSnsSave(HashMap<String, Object> param)throws Exception{
		return insert("memberDAO.memberSnsSave", param);
	}
	
	/**
	 * @Method : getTermsList
	 * @Date		: 2017. 7. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	약관/개인정보 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTermsList() throws Exception{
		return (List<SqlMap>)selectList("memberDAO.getTermsList", null);
	}
	
	//================================ 마이페이지 ================================
	
	/**
	 * @Method : getMemberInfo
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	보유포인트, 진행중인 주문 건
	 */
	public SqlMap getMemberInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("memberDAO.getMemberInfo", param);
	}
	
	/**
	 * @Method : memberMasterUpdate
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보(master) 변경
	 */
	public int memberMasterUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.memberMasterUpdate", param);
	}
	
	/**
	 * @Method : memberDetailUpdate
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보(detail) 변경
	 */
	public int memberDetailUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.memberDetailUpdate", param);
	}
	
	/**
	 * @Method : memberWithdrawSave
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 저장
	 */
	public int memberWithdrawSave(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("memberDAO.memberWithdrawSave", param);
	}
	
	/**
	 * @Method : memberWithdrawMasterUpdate
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 마스터 업데이트
	 */
	public int memberWithdrawMasterUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("memberDAO.memberWithdrawMasterUpdate", param);
	}
	
	/**
	 * @Method : memberWithdrawDetailUpdate
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 디테일 업데이트
	 */
	public int memberWithdrawDetailUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("memberDAO.memberWithdrawDetailUpdate", param);
	}
	
	/**
	 * @Method : getMemberAccount
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 정보
	 */
	public SqlMap getMemberAccount(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("memberDAO.getMemberAccount", param);
	}
	
	/**
	 * @Method : memberAccountSave
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 저장
	 */
	public int memberAccountSave(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("memberDAO.memberAccountSave", param);
	}
	
	/**
	 * @Method : memberAccountDelete
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 삭제
	 */
	public int memberAccountDelete(HashMap<String, Object> param)throws Exception{
		return (Integer)delete("memberDAO.memberAccountDelete", param);
	}
	
	/**
	 * @Method : getMemberShippingList
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMemberShippingList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("memberDAO.getMemberShippingList", param);
	}
	
	/**
	 * @Method : getShippingDetail
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 상세
	 */
	public SqlMap getShippingDetail(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("memberDAO.getShippingDetail", param);
	}
	
	/**
	 * @Method : memberShippingInsert
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 저장
	 */
	public int memberShippingInsert(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("memberDAO.memberShippingInsert", param);
	}
	
	/**
	 * @Method : memberShippingUpdate
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 수정
	 */
	public int memberShippingUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.memberShippingUpdate", param);
	}
	
	/**
	 * @Method : memberShippingDelete
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 삭제
	 */
	public int memberShippingDelete(HashMap<String, Object> param)throws Exception{
		return (Integer)delete("memberDAO.memberShippingDelete", param);
	}
	
	/**
	 * @Method : shippingDefalutUpdate
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	회원에 해당하는 기본배송지 N으로 전체 업데이트
	 */
	public int shippingAllDefalutUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.shippingAllDefalutUpdate", param);
	}
	
	/**
	 * @Method : shippingDefalutUpdate
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	기본 배송지 설정
	 */
	public int shippingDefalutUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.shippingDefalutUpdate", param);
	}
	
	/**
	 * @Method : memberSnsDetailUpdate
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 회원 정보 수정
	 */
	public int memberSnsDetailUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.memberSnsDetailUpdate", param);
	}
	
	/**
	 * @Method : memberWithdrawSnsDelete
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 회원 탈퇴 삭제
	 */
	public int memberWithdrawSnsDelete(HashMap<String, Object> param)throws Exception{
		return (Integer)delete("memberDAO.memberWithdrawSnsDelete", param);
	}
	
	/**
	 * @Method : callSendSmsProcedure
	 * @Date		: 2017. 8. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	SMS 알림톡 전송
	 */
	public int callSendSmsProcedure(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.callSendSmsProcedure", param);
	}
	
	/**
	 * @Method : callMemberCouponProcedure
	 * @Date		: 2017. 8. 23.
	 * @Author	:  유  준  철 
	 * @Description	:	쿠폰 발급
	 */
	public int callMemberCouponProcedure(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.callMemberCouponProcedure", param);
	}
	
	/**
	 * @Method : memberRecommendSave
	 * @Date		: 2018. 3. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	추천인 대상 내역 저장
	 */
	public int memberRecommendSave(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("memberDAO.memberRecommendSave", param);
	}
	
	/**
	 * @Method : recommendDetail
	 * @Date		: 2018. 3. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	추천인 대상 일련번호
	 */
	public SqlMap recommendDetail(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("memberDAO.recommendDetail", param);
	}
	
	/**
	 * @Method : joinCompleteDetail
	 * @Date		: 2018. 3. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 가입 완료 상세
	 */
	public SqlMap joinCompleteDetail(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("memberDAO.joinCompleteDetail", param);
	}
	
	/**
	 * @Method : getMemberRecommendCnt
	 * @Date		: 2020. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	추천받은 횟수
	 */
	public int getMemberRecommendCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("memberDAO.getMemberRecommendCnt", param);
	}
	
	/**
	 * @Method : getSnsName
	 * @Date		: 2020. 12. 1.
	 * @Author	:  임  재  형 
	 * @Description	:	SNS명 가져오기
	 */
	public SqlMap getSnsName(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("memberDAO.getSnsName", param);
	}
	
	/**
	 * @Method : callSendSmsSmsRejectProcedure
	 * @Date		: 2020. 12. 10.
	 * @Author	:  임  재  형 
	 * @Description	:	SMS 알림톡 전송 [수신동의 철회]
	 */
	public int callSendSmsSmsRejectProcedure(HashMap<String, Object> param)throws Exception{
		return (Integer)update("memberDAO.callSendSmsSmsRejectProcedure", param);
	}
}
