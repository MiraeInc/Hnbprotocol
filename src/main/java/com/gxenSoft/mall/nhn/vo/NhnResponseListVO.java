package com.gxenSoft.mall.nhn.vo;

import org.apache.axis2.databinding.types.xsd.DateTime;


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
public class NhnResponseListVO extends NhnResponseVO  {
	
	private int ReturnedDataCount;					// 이번 응답에 포함된 데이터의 개수 
	private Boolean HasMoreData;  					// 데이터가 더 존재하는지 여부 
	private DateTime  MoreDataTimeFrom;		// 다음 데이터의 조회를 위한 시작 일시(해당 시각 포함) 
	private String InquiryExtraData;					// 다음 데이터의 조회를 위한 추가 데이터(예: 주문 번호). 
	public int getReturnedDataCount() {
		return ReturnedDataCount;
	}
	public void setReturnedDataCount(int returnedDataCount) {
		ReturnedDataCount = returnedDataCount;
	}
	public Boolean getHasMoreData() {
		return HasMoreData;
	}
	public void setHasMoreData(Boolean hasMoreData) {
		HasMoreData = hasMoreData;
	}
	public DateTime getMoreDataTimeFrom() {
		return MoreDataTimeFrom;
	}
	public void setMoreDataTimeFrom(DateTime moreDataTimeFrom) {
		MoreDataTimeFrom = moreDataTimeFrom;
	}
	public String getInquiryExtraData() {
		return InquiryExtraData;
	}
	public void setInquiryExtraData(String inquiryExtraData) {
		InquiryExtraData = inquiryExtraData;
	}


}
