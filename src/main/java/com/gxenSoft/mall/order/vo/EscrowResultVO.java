package com.gxenSoft.mall.order.vo;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : EscrowResultVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 서 정 길
    * CREATED DATE  : 2017. 9. 21. 
    * HISTORY :   
    *
    *************************************
    */	
public class EscrowResultVO {

	private String mid = "";					// 상점ID
	private String oid = "";					// 상점주문번호
	private String tid = "";					// LG유플러스에서 부여한 거래번호
	private String transactionid = "";		// LG유플러스 거래번호
	private String txtype = "";				// 결과구분 (C:수령확인결과, R:구매취소요청, D:구매취소결과, S:택배사배송완료,	N:N/C처리완료 (Negative Confirm))
	private String productid = "";			// 상품ID, 기본값=‘1’, Reserved 칼럼 향후 사용 예정
	private String ssn = "";					// 작업자 주민번호, 구매취소결과일때는 공백임 * 개인정보보호법에 의거하여 “.” 리턴함
	private String ip = "";						// 작업자 PC의 IP주소, 구매취소결과일때는 공백임 * 개인정보보호법에 의거하여 “.” 리턴함
	private String mac = "";					// 작업자 PC의 MAC주소, 구매취소결과일때는 공백임 * 개인정보보호법에 의거하여 “.” 리턴함
	private String resdate = "";				// 처리일시, (YYYYMMDDHHMISS 14자리 포맷)
	private String hashdata = "";			// 보안을 위한 인증키 [인증키 생성방법] ① 다음 호출 파라미터의 값을 순서대로 조합한 문자열을 MD5로 Encoding한 문자열값 ② “상점ID + 상점주문번호 + LG유플러스거래번호 + 결과구분 + 상품ID + 주민번호 + IP + MAC주소 + 처리일시 + 상점키”
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getTxtype() {
		return txtype;
	}
	public void setTxtype(String txtype) {
		this.txtype = txtype;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getResdate() {
		return resdate;
	}
	public void setResdate(String resdate) {
		this.resdate = resdate;
	}
	public String getHashdata() {
		return hashdata;
	}
	public void setHashdata(String hashdata) {
		this.hashdata = hashdata;
	}
}
