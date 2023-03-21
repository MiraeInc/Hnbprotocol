package com.gxenSoft.mall.event.timeSale.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.event.timeSale.dao.TimeSaleDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : TimeSaleServiceImpl
 * PACKAGE NM : com.gxenSoft.mall.event.timeSale.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 20. 
 * HISTORY :
 
 *************************************
 */
@Service("timeSaleService")
public class TimeSaleServiceImpl implements TimeSaleService {
	
	static final Logger logger = LoggerFactory.getLogger(TimeSaleServiceImpl.class);
	
	@Autowired
	private TimeSaleDAO timeSaleDAO;
	
	/**
	 * @Method : getTimeSaleDetail
	 * @Date		: 2017. 7. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	타임 세일 상세
	 */
	public SqlMap getTimeSaleDetail(Integer mainGubun) throws Exception{
		SqlMap detail = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		if(mainGubun != null){
			map.put("MAINGUBUN", mainGubun);
		}
		
		try{
			detail = timeSaleDAO.getTimeSaleDetail(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	 * @Method : getTimeSaleProductList
	 * @Date		: 2017. 7. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	타임 세일 진행 예정 상품 리스트
	 */
	public List<SqlMap> getTimeSaleProductList(String idx) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("TIMESALEIDX", idx);
		
		try{
			list = timeSaleDAO.getTimeSaleProductList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}

}
