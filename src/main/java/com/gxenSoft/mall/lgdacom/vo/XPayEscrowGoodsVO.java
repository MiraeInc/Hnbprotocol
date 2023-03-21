package com.gxenSoft.mall.lgdacom.vo;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : XPayEscrowGoodsVO
    * PACKAGE NM : com.gxenSoft.mall.lgdacom.vo
    * AUTHOR	 : 서 정 길
    * CREATED DATE  : 2017. 9. 19. 
    * HISTORY :   
    *
    *************************************
    */	
public class XPayEscrowGoodsVO {

	private String LGD_ESCROW_GOODID = "";		// 에스크로상품번호
	private String LGD_ESCROW_GOODNAME = "";	// 에스크로상품명
	private String LGD_ESCROW_GOODCODE = "";	// 에스크로상품코드
	private String LGD_ESCROW_UNITPRICE = "";	// 에스크로상품금액
	private String LGD_ESCROW_QUANTITY = "";		// 에스크로상품수량
	
	public String getLGD_ESCROW_GOODID() {
		return LGD_ESCROW_GOODID;
	}
	public void setLGD_ESCROW_GOODID(String lGD_ESCROW_GOODID) {
		LGD_ESCROW_GOODID = lGD_ESCROW_GOODID;
	}
	public String getLGD_ESCROW_GOODNAME() {
		return LGD_ESCROW_GOODNAME;
	}
	public void setLGD_ESCROW_GOODNAME(String lGD_ESCROW_GOODNAME) {
		LGD_ESCROW_GOODNAME = lGD_ESCROW_GOODNAME;
	}
	public String getLGD_ESCROW_GOODCODE() {
		return LGD_ESCROW_GOODCODE;
	}
	public void setLGD_ESCROW_GOODCODE(String lGD_ESCROW_GOODCODE) {
		LGD_ESCROW_GOODCODE = lGD_ESCROW_GOODCODE;
	}
	public String getLGD_ESCROW_UNITPRICE() {
		return LGD_ESCROW_UNITPRICE;
	}
	public void setLGD_ESCROW_UNITPRICE(String lGD_ESCROW_UNITPRICE) {
		LGD_ESCROW_UNITPRICE = lGD_ESCROW_UNITPRICE;
	}
	public String getLGD_ESCROW_QUANTITY() {
		return LGD_ESCROW_QUANTITY;
	}
	public void setLGD_ESCROW_QUANTITY(String lGD_ESCROW_QUANTITY) {
		LGD_ESCROW_QUANTITY = lGD_ESCROW_QUANTITY;
	}

}
