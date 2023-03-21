package com.gxenSoft.mall.nhn.vo;

import java.util.List;


/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : NhnResponseVO
 * PACKAGE NM : com.gxenSoft.mall.nhn.vo
 * AUTHOR	 : 강 병 철
 * CREATED DATE  : 2018. 7. 10. 
 * HISTORY :
 
 *************************************
 */
public class NhnResponseVO  {
	
	private String RequestID;							// 메시지를 식별하기 위한 아이디로, 요청 메시지의 RequestID 필드 값과 동일한 값을 갖는다
	private String ResponseType;  					// 호출한 API 의 성공 여부(Success/SuccessWarning/Error/Error-Warning) 
	private Long  ResponseTime;					// 요청 처리 시간을 표시한다. 요청 메시지를 받은 순 간부터 응답 메시지를 보내는 순간까지의 시간을 밀 리초(millisecond) 단위로 반환한다. 
	private NhnGetProductOrderInfoListVO Error;								    // 오류(error) 정보 
	private List<NhnGetProductOrderInfoListVO> WarningList;				// 경고(warning) 정보 목록 
	private String QuotaStatus;						// 쿼터 잔량과 만료 일시 
	private String DetailLevel;							// 돌려 받는 데이터의 상세 정도(Compact/Full) 
	private String Version;								// API 응답 메시지 버전 
	private String Release;								// 서버 소프트웨어 버전 
	private String TimeStamp;						// 메시지 호출 당시 서버 시각 
	private String MessageID;						// 메시지 아이디 (추후 추적 목적으로 사용) 
	public String getRequestID() {
		return RequestID;
	}
	public void setRequestID(String requestID) {
		RequestID = requestID;
	}
	public String getResponseType() {
		return ResponseType;
	}
	public void setResponseType(String responseType) {
		ResponseType = responseType;
	}
	public Long getResponseTime() {
		return ResponseTime;
	}
	public void setResponseTime(Long responseTime) {
		ResponseTime = responseTime;
	}
	public NhnGetProductOrderInfoListVO getError() {
		return Error;
	}
	public void setError(NhnGetProductOrderInfoListVO error) {
		Error = error;
	}
	public List<NhnGetProductOrderInfoListVO> getWarningList() {
		return WarningList;
	}
	public void setWarningList(List<NhnGetProductOrderInfoListVO> warningList) {
		WarningList = warningList;
	}
	public String getQuotaStatus() {
		return QuotaStatus;
	}
	public void setQuotaStatus(String quotaStatus) {
		QuotaStatus = quotaStatus;
	}
	public String getDetailLevel() {
		return DetailLevel;
	}
	public void setDetailLevel(String detailLevel) {
		DetailLevel = detailLevel;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getRelease() {
		return Release;
	}
	public void setRelease(String release) {
		Release = release;
	}
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	public String getMessageID() {
		return MessageID;
	}
	public void setMessageID(String messageID) {
		MessageID = messageID;
	}
	

}
