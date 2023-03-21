package com.gxenSoft.util.pg.smilepay.vo;

import org.apache.commons.lang.StringUtils;

/**
   *************************************
   * PROJECT   : GatsbyMall
   * PROGRAM ID  : SmilepayReqVO
   * PACKAGE NM : com.gxenSoft.util.pg.smilepay.vo
   * AUTHOR	 : 김 민 수
   * CREATED DATE  : 2017. 11. 26. 
   * HISTORY :   
   *
   *************************************
 **/	
public class SmilepayReqVO {
	private String prType;
	private String MID;
	private String merchantTxnNum;
	private String channelType;
	private String GoodsName;
	private String Amt;
	private String ServiceAmt;
	private String SupplyAmt;
	private String GoodsVat;
	private String currency;
	private String returnUrl;
	private String returnUrl2;
	private String merchantEncKey;
	private String merchantHashKey;
	private String requestDealApproveUrl;
	private String certifiedFlag;
	private String offerPeriod;
	private String offerPeriodFlag;
	private String possiCard;
	private String fixedInt;
	private String maxInt;
	private String noIntYN;
	private String noIntOpt;
	private String pointUseYn;
	private String blockCard;
	private String blockBin;
	private String possiBin;
	private String orderCheckYn;
	private String orderBirthDay;
	private String orderName;
	private String orderTel;
	private String cardSubCoNm;
	private String dummyPageFlag;
	private String taxationAmt;
	private String etc1;
	private String etc2;
	private String etc3;
	private String BuyerName;
	
	public String getPrType() {
		return StringUtils.defaultString(prType);
	}
	public void setPrType(String prType) {
		this.prType = prType;
	}
	public String getMID() {
		return StringUtils.defaultString(MID);
	}
	public void setMID(String mID) {
		MID = mID;
	}
	public String getMerchantTxnNum() {
		return StringUtils.defaultString(merchantTxnNum);
	}
	public void setMerchantTxnNum(String merchantTxnNum) {
		this.merchantTxnNum = merchantTxnNum;
	}
	public String getChannelType() {
		return StringUtils.defaultString(channelType);
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getGoodsName() {
		return StringUtils.defaultString(GoodsName);
	}
	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}
	public String getAmt() {
		return StringUtils.defaultString(Amt);
	}
	public void setAmt(String amt) {
		Amt = amt;
	}
	public String getServiceAmt() {
		return StringUtils.defaultString(ServiceAmt);
	}
	public void setServiceAmt(String serviceAmt) {
		ServiceAmt = serviceAmt;
	}
	public String getSupplyAmt() {
		return StringUtils.defaultString(SupplyAmt);
	}
	public void setSupplyAmt(String supplyAmt) {
		SupplyAmt = supplyAmt;
	}
	public String getGoodsVat() {
		return StringUtils.defaultString(GoodsVat);
	}
	public void setGoodsVat(String goodsVat) {
		GoodsVat = goodsVat;
	}
	public String getCurrency() {
		return StringUtils.defaultString(currency);
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReturnUrl() {
		return StringUtils.defaultString(returnUrl);
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getReturnUrl2() {
		return StringUtils.defaultString(returnUrl2);
	}
	public void setReturnUrl2(String returnUrl2) {
		this.returnUrl2 = returnUrl2;
	}
	public String getMerchantEncKey() {
		return StringUtils.defaultString(merchantEncKey);
	}
	public void setMerchantEncKey(String merchantEncKey) {
		this.merchantEncKey = merchantEncKey;
	}
	public String getMerchantHashKey() {
		return StringUtils.defaultString(merchantHashKey);
	}
	public void setMerchantHashKey(String merchantHashKey) {
		this.merchantHashKey = merchantHashKey;
	}
	public String getRequestDealApproveUrl() {
		return StringUtils.defaultString(requestDealApproveUrl);
	}
	public void setRequestDealApproveUrl(String requestDealApproveUrl) {
		this.requestDealApproveUrl = requestDealApproveUrl;
	}
	public String getCertifiedFlag() {
		return StringUtils.defaultString(certifiedFlag);
	}
	public void setCertifiedFlag(String certifiedFlag) {
		this.certifiedFlag = certifiedFlag;
	}
	public String getOfferPeriod() {
		return StringUtils.defaultString(offerPeriod);
	}
	public void setOfferPeriod(String offerPeriod) {
		this.offerPeriod = offerPeriod;
	}
	public String getOfferPeriodFlag() {
		return StringUtils.defaultString(offerPeriodFlag);
	}
	public void setOfferPeriodFlag(String offerPeriodFlag) {
		this.offerPeriodFlag = offerPeriodFlag;
	}
	public String getPossiCard() {
		return StringUtils.defaultString(possiCard);
	}
	public void setPossiCard(String possiCard) {
		this.possiCard = possiCard;
	}
	public String getFixedInt() {
		return StringUtils.defaultString(fixedInt);
	}
	public void setFixedInt(String fixedInt) {
		this.fixedInt = fixedInt;
	}
	public String getMaxInt() {
		return StringUtils.defaultString(maxInt);
	}
	public void setMaxInt(String maxInt) {
		this.maxInt = maxInt;
	}
	public String getNoIntYN() {
		return StringUtils.defaultString(noIntYN);
	}
	public void setNoIntYN(String noIntYN) {
		this.noIntYN = noIntYN;
	}
	public String getNoIntOpt() {
		return StringUtils.defaultString(noIntOpt);
	}
	public void setNoIntOpt(String noIntOpt) {
		this.noIntOpt = noIntOpt;
	}
	public String getPointUseYn() {
		return StringUtils.defaultString(pointUseYn);
	}
	public void setPointUseYn(String pointUseYn) {
		this.pointUseYn = pointUseYn;
	}
	public String getBlockCard() {
		return StringUtils.defaultString(blockCard);
	}
	public void setBlockCard(String blockCard) {
		this.blockCard = blockCard;
	}
	public String getBlockBin() {
		return StringUtils.defaultString(blockBin);
	}
	public void setBlockBin(String blockBin) {
		this.blockBin = blockBin;
	}
	public String getPossiBin() {
		return StringUtils.defaultString(possiBin);
	}
	public void setPossiBin(String possiBin) {
		this.possiBin = possiBin;
	}
	public String getOrderCheckYn() {
		return StringUtils.defaultString(orderCheckYn);
	}
	public void setOrderCheckYn(String orderCheckYn) {
		this.orderCheckYn = orderCheckYn;
	}
	public String getOrderBirthDay() {
		return StringUtils.defaultString(orderBirthDay);
	}
	public void setOrderBirthDay(String orderBirthDay) {
		this.orderBirthDay = orderBirthDay;
	}
	public String getOrderName() {
		return StringUtils.defaultString(orderName);
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderTel() {
		return StringUtils.defaultString(orderTel);
	}
	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}
	public String getCardSubCoNm() {
		return StringUtils.defaultString(cardSubCoNm);
	}
	public void setCardSubCoNm(String cardSubCoNm) {
		this.cardSubCoNm = cardSubCoNm;
	}
	public String getDummyPageFlag() {
		return StringUtils.defaultString(dummyPageFlag);
	}
	public void setDummyPageFlag(String dummyPageFlag) {
		this.dummyPageFlag = dummyPageFlag;
	}
	public String getTaxationAmt() {
		return StringUtils.defaultString(taxationAmt);
	}
	public void setTaxationAmt(String taxationAmt) {
		this.taxationAmt = taxationAmt;
	}
	public String getEtc1() {
		return StringUtils.defaultString(etc1);
	}
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}
	public String getEtc2() {
		return StringUtils.defaultString(etc2);
	}
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	public String getEtc3() {
		return StringUtils.defaultString(etc3);
	}
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}
	public String getBuyerName() {
		return StringUtils.defaultString(BuyerName);
	}
	public void setBuyerName(String buyerName) {
		BuyerName = buyerName;
	}
}
