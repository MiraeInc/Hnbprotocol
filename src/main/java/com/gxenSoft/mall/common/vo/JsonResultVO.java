package com.gxenSoft.mall.common.vo;

import java.util.List;

import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : JsonResultVO
    * PACKAGE NM : com.gxenSoft.mall.common.vo
    * AUTHOR	 : 서 정 길
    * CREATED DATE  : 2017. 6. 27. 
    * HISTORY :   
    *
    *************************************
    */	
public class JsonResultVO {

	private Boolean result;			
	private String msg;
	private String errorCode;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	
	private Integer resultCnt;						// 반환 결과 수
	private Boolean wishFlag;					// 찜 여부 
	
	private List<SqlMap> resultList;		// 엑셀 파일 업로드 후 반환 결과 리스트
	private SqlMap resultMap;				// 반환 결과 SqlMap
	
	private Page page;
	
	
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getData5() {
		return data5;
	}
	public void setData5(String data5) {
		this.data5 = data5;
	}
	public String getData6() {
		return data6;
	}
	public void setData6(String data6) {
		this.data6 = data6;
	}
	public Integer getResultCnt() {
		return resultCnt;
	}
	public void setResultCnt(Integer resultCnt) {
		this.resultCnt = resultCnt;
	}
	public Boolean getWishFlag() {
		return wishFlag;
	}
	public void setWishFlag(Boolean wishFlag) {
		this.wishFlag = wishFlag;
	}
	public List<SqlMap> getResultList() {
		return resultList;
	}
	public void setResultList(List<SqlMap> resultList) {
		this.resultList = resultList;
	}
	public SqlMap getResultMap() {
		return resultMap;
	}
	public void setResultMap(SqlMap resultMap) {
		this.resultMap = resultMap;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}
