package com.gxenSoft.mall.common.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;

import com.google.gson.Gson;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonMethod
 * PACKAGE NM : com.gatsbyMall.common.web
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 13. 
 * HISTORY :
 
 *************************************
 */
public class CommonMethod {
	
	static final Logger logger = LoggerFactory.getLogger(CommonMethod.class);
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * @Method : isLogin
	 * @Date		: 2017. 6. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	로그인 여부
	 */
	public static boolean isLogin(HttpServletRequest request ) {
		HttpSession session = request.getSession();

		if(session == null) {
			return false;
		}else {
			String memberId = (String)session.getAttribute("SS_MEMBER_ID");		
			
			if(memberId !=null && !memberId.isEmpty()){				
				return true;	
			}else{
				return false;				
			}
		}
	}	 
	
	/**
	 * @Method : isDevice
	 * @Date		: 2017. 8. 15.
	 * @Author	:  유  준  철 
	 * @Description	:	디바이스 체크
	 */
	public static String isDevice(HttpServletRequest request ) {
		Device device = DeviceUtils.getCurrentDevice(request);        
		String returnDevice = "";
		
		if(device == null){
			return "device is null";
		}
		
		if(device.isNormal()){
			returnDevice = "w";
		}else{
			returnDevice = "m";
		}
		return returnDevice;
	}	 
	
	/**
	 * @Method : getCodeList
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	공통 코드 리스트 조회	
	 */
	public List<SqlMap> getCodeList(String commonCd)throws Exception{
		List<SqlMap> list = null;
		try{
			list = commonService.getCodeList(commonCd);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}		
		return list; 
	}

	/**
	 * @Method : getCate1DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	1Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate1DepthList(int brandidx)throws Exception{
		List<SqlMap> list = null;
		 		
		try{
			list = commonService.getCate1DepthList(brandidx);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	/**
	 * @Method : getCate2DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	2Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate2DepthList(SchProductVO schProductVo)throws Exception{
		List<SqlMap> list = null;
		 		
		try{
			list = commonService.getCate2DepthList(schProductVo);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	/**
	 * @Method : getCate3DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	3Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate3DepthList(SchProductVO schProductVo)throws Exception{
		List<SqlMap> list = null;
		 		
		try{
			list = commonService.getCate3DepthList(schProductVo);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	/**
	 * @Method : getBrandList
	 * @Date: 2017. 6. 1.
	 * @Author :  김  민  수
	 * @Description	:	브랜드 리스트	
	 * @HISTORY :
	 *
	 */
	public List<SqlMap> getBrandList()throws Exception{
		List<SqlMap> list = null;
		 		
		try{
			list = commonService.getBrandList();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	/*
	 * @Method : ObjectToHiddenParams
	 * @Date		: 2016. 7. 8.
	 * @Author	:  강병철
	 * @Description	: Object를  parameter 값 hidden 형식 return	
	 */
	public static StringBuffer ObjectToHiddenParams(Object vo)throws Exception{
		Gson gson = new Gson();
		String jsonData = gson.toJson(vo);
	
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
		
		@SuppressWarnings("rawtypes")
		Iterator i = jsonObject.keySet().iterator();
		
		StringBuffer bf = new StringBuffer();
		while(i.hasNext()){
			String key = (String)i.next();
			String value = (jsonObject.get(key) == null)? "":jsonObject.get(key).toString();
			bf.append("<input type='hidden' name='"+key+"' id='"+key+"' value='"+value+"' />\n");
		}
		return bf;
	}
	
	/**
	 * @Method : getCate1DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	1뎁스 카테고리 세트 리스트
	 */
	public List<SqlMap> getCate1DepthSetList()throws Exception{
		List<SqlMap> list = null;
		 		
		try{
			list = commonService.getCate1DepthSetList();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	/**
	 * @Method : getCate2DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	2뎁스 카테고리 세트 리스트
	 */
	public List<SqlMap> getCate2DepthSetList()throws Exception{
		List<SqlMap> list = null;
		 		
		try{
			list = commonService.getCate2DepthSetList();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
}
