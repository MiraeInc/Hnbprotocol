package com.gxenSoft
.mall.order.vo;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : KcpReqVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 07. 16. 
    * HISTORY :   
    *
    *************************************
    */	
public class KcpNotiReqVO {

	//주문요청
	private String tx_cd = "";			 //  Y 	업무처리 구분코드 ,	가상계좌 입금 통보 – TX00,	구매확인 통보 – TX02,배송시작 통보 – TX03,	정산보류 통보 – TX04,	즉시취소 통보 – TX05,	취소 통보 – TX06,	발급계좌해지 통보 – TX07 
	private String tx_tm = "";         //통보된 업무에 대한 업무처리 완료 시간 
	private String site_cd = ""; //   Y 	상점코드 	
	private String tno	= "" ; //NHN KCP 거래 고유번호
	private String order_no = ""; // 	Y 	상점 관리 주문번호 (유니크한 값 설정 권장) 	
    private String result = ""; //0000 성공시, 실패시에는 다른값을 지정해야함.
	private String ipgm_name  = ""; // 거래에대한 주문자명
	private String 	 ipgm_mnyx  		; // 			입금자가 실제 입금한 입금 금액   ※ 주의 – 입금자가 주문 금액과 상이한 금액을 입금 한 경우, NHN KCP에서는 가맹점에 통보 페이지를 통해 실 입금 내역을 통보하고 입금 금액 그대로 가맹점에 정산이 이루어집니다. 해당 페이지에서 DB처리 시 실 입금 금액과 주 문금액을 비교해서 상이한 경우 내부적으로 확 인해주시기 바랍니다
	private String  totl_mnyx; // 	해당 계좌에 입금 된 금액의 합계 일회성 가상계좌의 경우 ipgm_mnyx와 동일한 금액이 통보되나, 고정식 가상계좌의 경우 해당 계좌에 입금된 총 금액이 통보됨 
	private String ipgm_time; //가상계좌에 입금된 시간 
	private String bank_code;  // 가상계좌 은행코드, 또는 환불시에는 환불은행코드
	private String account ; // 입금된 가상계좌 번호
	private String noti_id = ""; // 가상계좌의 각 입금 통보 건에 대한 고유한 값 을 가지는 변수 ※ 주의 – 가상계좌의 경우 결제 1 건에 대해 서 통보가 여러 번 나갈 수 있습니다. 예) 고객이 가상계좌에 입금 후 은행을 통해 취 소 요청을 한 경우 은행에서 NHN KCP로 취소 전문이 전송(일회 계좌은행 출금 불가) – 해당 건은 입금 전문1번과 취소 전문 1번이 가맹점 으로 통보됩니다. 8 
	private String op_cd  = ""; // 가상계좌 입금 처리 결과 구분 코드,  가상계좌는 입금처리 이외에도 은행 공동망을 통한취소가 이루어 질 수 있음.  해당 변수를 통해서 가상계좌 상태 구분을 반 드시 해주시기 바랍니다.
	private String remitter  = ""; //결제 금액을 입금한 입금자명, 주문자명과 다를 수 있음 
	private String cash_a_no = "";   // 가상계좌 발급 시 1원 이상 현금 거래 건에 대 해서 현금영수증 등록 요청 된 경우 고객이 해 당 가상계좌에 입금과 동시에 현금영수증이 등 록되며, 해당 변수를 통해 현금영수증 승인번호 를 전송
	private String cash_a_dt = "";
	private String cash_no = "" ; 	// 가상계좌 발급 시 현금영수증 등록 요청 건에 대해 입금완료 후 리턴 되는 현금영수증 거래 번호 
	
	//가상계좌 환불 통보 
	private String refund_nm =""; //가상계좌의 경우, 환불 시 환불 받을 계좌의 계 좌주명이 리턴 되는 변수 
	private String  refund_mny ; //가상계좌의 경우, 환불 시 환불된 금액이 리턴 되는 변수
	 
	// 에스크로 결과 중 가상계좌 구매확인 / 구매취소 통보에 대한 변수 
	private String  st_cd =""; //구매 확인 코드 해당 통보 건에 대해 상태가 구매확인인지 구 매 취소인지 체크하는 코드로 반드시 구분 값 을 확인하셔서 구분 값에 맞는 업무 처리를 하 시기 바랍니다. 
											  //(시스템 구매확인은 배송시작 상태 변경 후 5 일 내에 구매확인이 이루어지지 않았을 경우에, 시스템에서 자동으로 구매확인 처리되는 것을 지칭함) 구매확인 : ‘Y’ 구매취소 : ‘N’ 시스템 구매확인 : ‘S’ 
	private String can_msg  = ""; // 구매 취소 사유 
	
	//배송시작 통보 설정] 에스크로 결과 중 배송시작 통보에 대한 변수 
	private String waybill_no  = "";  //  운송장 번호 
	private String waybill_corp  = ""; //택배 업체의 업체명을 리턴 
	public String getTx_cd() {
		return tx_cd;
	}
	public void setTx_cd(String tx_cd) {
		this.tx_cd = tx_cd;
	}
	public String getTx_tm() {
		return tx_tm;
	}
	public void setTx_tm(String tx_tm) {
		this.tx_tm = tx_tm;
	}
	public String getSite_cd() {
		return site_cd;
	}
	public void setSite_cd(String site_cd) {
		this.site_cd = site_cd;
	}
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getIpgm_name() {
		return ipgm_name;
	}
	public void setIpgm_name(String ipgm_name) {
		this.ipgm_name = ipgm_name;
	}
	public String getIpgm_mnyx() {
		return ipgm_mnyx;
	}
	public void setIpgm_mnyx(String ipgm_mnyx) {
		this.ipgm_mnyx = ipgm_mnyx;
	}
	public String getTotl_mnyx() {
		return totl_mnyx;
	}
	public void setTotl_mnyx(String totl_mnyx) {
		this.totl_mnyx = totl_mnyx;
	}
	public String getIpgm_time() {
		return ipgm_time;
	}
	public void setIpgm_time(String ipgm_time) {
		this.ipgm_time = ipgm_time;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNoti_id() {
		return noti_id;
	}
	public void setNoti_id(String noti_id) {
		this.noti_id = noti_id;
	}
	public String getOp_cd() {
		return op_cd;
	}
	public void setOp_cd(String op_cd) {
		this.op_cd = op_cd;
	}
	public String getRemitter() {
		return remitter;
	}
	public void setRemitter(String remitter) {
		this.remitter = remitter;
	}
	public String getCash_a_no() {
		return cash_a_no;
	}
	public void setCash_a_no(String cash_a_no) {
		this.cash_a_no = cash_a_no;
	}
	public String getCash_no() {
		return cash_no;
	}
	public void setCash_no(String cash_no) {
		this.cash_no = cash_no;
	}
	public String getRefund_nm() {
		return refund_nm;
	}
	public void setRefund_nm(String refund_nm) {
		this.refund_nm = refund_nm;
	}
	public String getRefund_mny() {
		return refund_mny;
	}
	public void setRefund_mny(String refund_mny) {
		this.refund_mny = refund_mny;
	}
	public String getSt_cd() {
		return st_cd;
	}
	public void setSt_cd(String st_cd) {
		this.st_cd = st_cd;
	}
	public String getCan_msg() {
		return can_msg;
	}
	public void setCan_msg(String can_msg) {
		this.can_msg = can_msg;
	}
	public String getWaybill_no() {
		return waybill_no;
	}
	public void setWaybill_no(String waybill_no) {
		this.waybill_no = waybill_no;
	}
	public String getWaybill_corp() {
		return waybill_corp;
	}
	public void setWaybill_corp(String waybill_corp) {
		this.waybill_corp = waybill_corp;
	}
	public String getCash_a_dt() {
		return cash_a_dt;
	}
	public void setCash_a_dt(String cash_a_dt) {
		this.cash_a_dt = cash_a_dt;
	}
	

}
