package com.gxenSoft.mall.style.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : SampleVO
    * PACKAGE NM : com.gxenSoft.mall.style.vo
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 13. 
    * HISTORY :   
    *
    *************************************
    */
public class SampleVO extends CommonVO {

	private Integer sampleIdx;			// 샘플 일련번호
	
	private Integer sampleReplyIdx;	// 댓글 일련번호
	private String replyContents;		// 댓글 내용
	private Integer brandIdx;				// 브랜드 일련번호
	
	
	public Integer getSampleIdx() {
		return sampleIdx;
	}
	public void setSampleIdx(Integer sampleIdx) {
		this.sampleIdx = sampleIdx;
	}
	public Integer getSampleReplyIdx() {
		return sampleReplyIdx;
	}
	public void setSampleReplyIdx(Integer sampleReplyIdx) {
		this.sampleReplyIdx = sampleReplyIdx;
	}
	public String getReplyContents() {
		return StringUtils.defaultString(replyContents);
	}
	public void setReplyContents(String replyContents) {
		this.replyContents = replyContents;
	}
	public Integer getBrandIdx() {
		return brandIdx;
	}
	public void setBrandIdx(Integer brandIdx) {
		this.brandIdx = brandIdx;
	}
	
}
