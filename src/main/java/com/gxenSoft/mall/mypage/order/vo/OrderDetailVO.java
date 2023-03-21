package com.gxenSoft.mall.mypage.order.vo;

public class OrderDetailVO {

	private Integer orderDetailIdx;			// 주문 디테일 일련번호
	private Integer orderIdx;					// 주문 마스터 일련번호
	private String orderCd;						// 주문코드
	private String orderStatusCd;				// 주문상태코드
	private Integer goodsIdx;					// 상품 일련번호
	private String goodsCd;						// 상품코드
	private String goodsNm;						// 상품명
	private Integer orderCnt = 0;				// 상품개수
	private Double price = 0.0;					// 정상가
	private Double discountRate = 0.0;		// 할인율
	private Double discountPrice = 0.0;		// 판매가
	private Integer couponIdx;					// 적용된 상품 쿠폰 일련번호
	private Double couponPrice = 0.0;		// 상품 쿠폰 할인 금액
	private Double pointPrice = 0.0;			// 포인트 결제 금액 (비율로 나눠서 저장)
	private Double prePointPrice = 0.0;		// 선포인트 결제 금액 (비율로 나눠서 저장)
	private Double payPrice;						// 결제금액 (실제 PG사 결제금액)
	private Double savePoint = 0.0;			// 적립 예정 포인트 
	private String displayDate;					// 상품노출일자 (상품 FRONT 전시되는 일자 YYYYMMDD 형태)
	private String saleFlag = "N";				// 판매기간 옵션구분 (Y: 시작일/종료일, N: 기간없음)
	private String saleStartDate;				// 판매시작일자 (YYYYMMDD 형태)
	private String saleEndDate;					// 판매종료일자 (YYYYMMDD 형태)
	private String saleStatus = "P";			// 판매상태 (P:판매전, Y:판매중, R:품절, S:판매중단)
	private String useYn = "N";					// 사용여부(노출여부) (상품 FRONT 실제 노출여부 Y: 노출  N: 비노출)
	private String stockFlag = "N";				// 재고 설정구분 (Y: 재고수량에 따름, N: 무제한)
	private Integer stockCnt = 0;				// 재고 수량 (재고 설정이 무제한인 경우 수량 0)
	private String buyLimitFlag = "N";		// 구매제한 설정구분 (Y: 수량별 구매제한, N: 제한 없음)
	private Integer buyMaxCnt = 0;			// 최대 구매 가능 수량 (수량별 구매제한인 경우만 유효)
	private Integer buyMinCnt = 0;			// 최소 구매 가능 수량 (수량별 구매제한인 경우만 유효)
	private String pointFlag = "N";				// 포인트 적립 설정구분 (Y: 적립 사용, N: 미적립)
	private Integer pointRate = 0;				// 포인트적립율
	private String setFlag = "N";				// 세트상품 구분 (Y : 세트 상품, N : 일반상품)
	private String goodsImg = "";				// 상품 대표 이미지
	private String goodsRealImg = "";		// 상품 대표 실제 이미지
	private String deliveryCompCd;			// 배송업체 코드 (공통코드)
	private String invoiceNo;						// 송장번호
	private String shippingDt;					// 발송일
	private String deliveredDt;					// 배송완료일
	private String purchaseDt;					// 구매확정일
	private Integer regIdx;						// 작성자 일련번호
	private String regHttpUserAgent;			// 작성자 USER_AGENT
	private String regIp;							// 작성자 IP
	private String regDt;							// 작성일자
	private Integer editIdx;						// 수정자 일련번호
	private String editHttpUserAgent;			// 수정자 USER_AGENT
	private String editIp;							// 수정자 IP
	private String editDt;							// 수정일자
	
	private String reason;							// 주문 상태 변경 사유

	public Integer getOrderDetailIdx() {
		return orderDetailIdx;
	}
	public void setOrderDetailIdx(Integer orderDetailIdx) {
		this.orderDetailIdx = orderDetailIdx;
	}
	public Integer getOrderIdx() {
		return orderIdx;
	}
	public void setOrderIdx(Integer orderIdx) {
		this.orderIdx = orderIdx;
	}
	public String getOrderCd() {
		return orderCd;
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}
	public String getOrderStatusCd() {
		return orderStatusCd;
	}
	public void setOrderStatusCd(String orderStatusCd) {
		this.orderStatusCd = orderStatusCd;
	}
	public Integer getGoodsIdx() {
		return goodsIdx;
	}
	public void setGoodsIdx(Integer goodsIdx) {
		this.goodsIdx = goodsIdx;
	}
	public String getGoodsCd() {
		return goodsCd;
	}
	public void setGoodsCd(String goodsCd) {
		this.goodsCd = goodsCd;
	}
	public String getGoodsNm() {
		return goodsNm;
	}
	public void setGoodsNm(String goodsNm) {
		this.goodsNm = goodsNm;
	}
	public Integer getOrderCnt() {
		return orderCnt;
	}
	public void setOrderCnt(Integer orderCnt) {
		this.orderCnt = orderCnt;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Integer getCouponIdx() {
		return couponIdx;
	}
	public void setCouponIdx(Integer couponIdx) {
		this.couponIdx = couponIdx;
	}
	public Double getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(Double couponPrice) {
		this.couponPrice = couponPrice;
	}
	public Double getPointPrice() {
		return pointPrice;
	}
	public void setPointPrice(Double pointPrice) {
		this.pointPrice = pointPrice;
	}
	public Double getPrePointPrice() {
		return prePointPrice;
	}
	public void setPrePointPrice(Double prePointPrice) {
		this.prePointPrice = prePointPrice;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public Double getSavePoint() {
		return savePoint;
	}
	public void setSavePoint(Double savePoint) {
		this.savePoint = savePoint;
	}
	public String getDisplayDate() {
		return displayDate;
	}
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	public String getSaleFlag() {
		return saleFlag;
	}
	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}
	public String getSaleStartDate() {
		return saleStartDate;
	}
	public void setSaleStartDate(String saleStartDate) {
		this.saleStartDate = saleStartDate;
	}
	public String getSaleEndDate() {
		return saleEndDate;
	}
	public void setSaleEndDate(String saleEndDate) {
		this.saleEndDate = saleEndDate;
	}
	public String getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getStockFlag() {
		return stockFlag;
	}
	public void setStockFlag(String stockFlag) {
		this.stockFlag = stockFlag;
	}
	public Integer getStockCnt() {
		return stockCnt;
	}
	public void setStockCnt(Integer stockCnt) {
		this.stockCnt = stockCnt;
	}
	public String getBuyLimitFlag() {
		return buyLimitFlag;
	}
	public void setBuyLimitFlag(String buyLimitFlag) {
		this.buyLimitFlag = buyLimitFlag;
	}
	public Integer getBuyMaxCnt() {
		return buyMaxCnt;
	}
	public void setBuyMaxCnt(Integer buyMaxCnt) {
		this.buyMaxCnt = buyMaxCnt;
	}
	public Integer getBuyMinCnt() {
		return buyMinCnt;
	}
	public void setBuyMinCnt(Integer buyMinCnt) {
		this.buyMinCnt = buyMinCnt;
	}
	public String getPointFlag() {
		return pointFlag;
	}
	public void setPointFlag(String pointFlag) {
		this.pointFlag = pointFlag;
	}
	public Integer getPointRate() {
		return pointRate;
	}
	public void setPointRate(Integer pointRate) {
		this.pointRate = pointRate;
	}
	public String getSetFlag() {
		return setFlag;
	}
	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public String getGoodsRealImg() {
		return goodsRealImg;
	}
	public void setGoodsRealImg(String goodsRealImg) {
		this.goodsRealImg = goodsRealImg;
	}
	public String getDeliveryCompCd() {
		return deliveryCompCd;
	}
	public void setDeliveryCompCd(String deliveryCompCd) {
		this.deliveryCompCd = deliveryCompCd;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getShippingDt() {
		return shippingDt;
	}
	public void setShippingDt(String shippingDt) {
		this.shippingDt = shippingDt;
	}
	public String getDeliveredDt() {
		return deliveredDt;
	}
	public void setDeliveredDt(String deliveredDt) {
		this.deliveredDt = deliveredDt;
	}
	public String getPurchaseDt() {
		return purchaseDt;
	}
	public void setPurchaseDt(String purchaseDt) {
		this.purchaseDt = purchaseDt;
	}
	public Integer getRegIdx() {
		return regIdx;
	}
	public void setRegIdx(Integer regIdx) {
		this.regIdx = regIdx;
	}
	public String getRegHttpUserAgent() {
		return regHttpUserAgent;
	}
	public void setRegHttpUserAgent(String regHttpUserAgent) {
		this.regHttpUserAgent = regHttpUserAgent;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public Integer getEditIdx() {
		return editIdx;
	}
	public void setEditIdx(Integer editIdx) {
		this.editIdx = editIdx;
	}
	public String getEditHttpUserAgent() {
		return editHttpUserAgent;
	}
	public void setEditHttpUserAgent(String editHttpUserAgent) {
		this.editHttpUserAgent = editHttpUserAgent;
	}
	public String getEditIp() {
		return editIp;
	}
	public void setEditIp(String editIp) {
		this.editIp = editIp;
	}
	public String getEditDt() {
		return editDt;
	}
	public void setEditDt(String editDt) {
		this.editDt = editDt;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}