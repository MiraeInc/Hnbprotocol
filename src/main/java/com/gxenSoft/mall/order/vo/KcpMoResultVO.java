package com.gxenSoft.mall.order.vo;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : KcpMoResultVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 07. 16. 
    * HISTORY :   
    *
    *************************************
    */	
public class KcpMoResultVO {

	private Boolean result;			
	private String Code = "";  //0000이면성공
	private String Message = "";
	private String approvalKey = ""; 
	private String request_URI = "";
	private String traceNo = ""; 
	private String PayUrl = "";
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getApprovalKey() {
		return approvalKey;
	}
	public void setApprovalKey(String approvalKey) {
		this.approvalKey = approvalKey;
	}
	public String getRequest_URI() {
		return request_URI;
	}
	public void setRequest_URI(String request_URI) {
		this.request_URI = request_URI;
	}
	public String getTraceNo() {
		return traceNo;
	}
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	public String getPayUrl() {
		return PayUrl;
	}
	public void setPayUrl(String payUrl) {
		PayUrl = payUrl;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
											

}
