package com.gxenSoft.mall.event.event.vo;


import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : EventVO
    * PACKAGE NM : com.gxenSoft.mall.event.event.vo
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 20. 
    * HISTORY :   
    *
    *************************************
    */
public class EventVO extends CommonVO {

	private Integer eventIdx;		// 일련번호
	private Integer brandIdx;		// 브랜드 일련번호 [1:갸스비, 3:비페스타, 4:루시도엘, 6:마마버터, 7:덴탈프로, 8:찰리]
	
	private Integer replyIdx;			// 댓글 일련번호
	private String replyContents;	// 댓글 내용
	private String gubun;				// 구분
	
	
	public Integer getEventIdx() {
		return eventIdx;
	}

	public void setEventIdx(Integer eventIdx) {
		this.eventIdx = eventIdx;
	}

	public Integer getBrandIdx() {
		return brandIdx;
	}

	public void setBrandIdx(Integer brandIdx) {
		this.brandIdx = brandIdx;
	}

	public Integer getReplyIdx() {
		return replyIdx;
	}

	public void setReplyIdx(Integer replyIdx) {
		this.replyIdx = replyIdx;
	}

	public String getReplyContents() {
		return replyContents;
	}

	public void setReplyContents(String replyContents) {
		this.replyContents = replyContents;
	}

	public String getGubun() {
		return StringUtils.defaultString(gubun);
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
}
