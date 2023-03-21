package com.gxenSoft.mall.brand.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxenSoft.mall.brand.dao.BrandDAO;
import com.gxenSoft.mall.brand.vo.BrandVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : BrandServiceImpl
 * PACKAGE NM : com.gxenSoft.mall.brand.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 28. 
 * HISTORY :
 
 *************************************
 */
@Service("brandService")
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandDAO brandDAO;
	
	/**
	 * @Method : getBrandAdList
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	광고(AD) 리스트
	 */
	public List<SqlMap> getBrandAdList(BrandVO vo, SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("DEVICEGUBUN", vo.getDeviceGubun());
		if(vo.getBrandIdx() == null){
			vo.setBrandIdx(1);
		}
		map.put("BRANDIDX", vo.getBrandIdx());
		
		map.put("PAGESTART", ((schProductVo.getPageNo() - 1) * schProductVo.getPageBlock()));					
		map.put("PAGEBLOCK", schProductVo.getPageBlock());																
		map.put("PAGENO", schProductVo.getPageNo());			
		
		try{
			list = brandDAO.getBrandAdList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getBrandAdListCnt
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	광고(AD) 총 건수
	 */
	public int getBrandAdListCnt(BrandVO vo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("DEVICEGUBUN", vo.getDeviceGubun());
		if(vo.getBrandIdx() == null){
			vo.setBrandIdx(1);
		}
		map.put("BRANDIDX", vo.getBrandIdx());
		
		try{
			cnt = brandDAO.getBrandAdListCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	/**
	 * @Method : getBrandMagazineList
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 리스트
	 */
	public List<SqlMap> getBrandMagazineList(BrandVO vo, SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("DEVICEGUBUN", vo.getDeviceGubun());
		if(vo.getBrandIdx() == null){
			vo.setBrandIdx(1);
		}
		map.put("BRANDIDX", vo.getBrandIdx());
		
		map.put("PAGESTART", ((schProductVo.getPageNo() - 1) * schProductVo.getPageBlock()));					
		map.put("PAGEBLOCK", schProductVo.getPageBlock());																
		map.put("PAGENO", schProductVo.getPageNo());			
		
		try{
			list = brandDAO.getBrandMagazineList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getBrandMagazineListCnt
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 총 건수
	 */
	public int getBrandMagazineListCnt(BrandVO vo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("DEVICEGUBUN", vo.getDeviceGubun());
		if(vo.getBrandIdx() == null){
			vo.setBrandIdx(1);
		}
		map.put("BRANDIDX", vo.getBrandIdx());
		
		try{
			cnt = brandDAO.getBrandMagazineListCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	/**
	 * @Method : getBrandMagazineDetail
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세
	 */
	public SqlMap getBrandMagazineDetail(BrandVO vo) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MAGAZINEIDX", vo.getMagazineIdx());
		
		try {
			detail = brandDAO.getBrandMagazineDetail(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : getBrandMagazinePrev
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세 (이전글)
	 */
	public SqlMap getBrandMagazinePrev(String magazineIdx) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MAGAZINEIDX", magazineIdx);
		
		String deviceGubun = "";
		if(PathUtil.getDevice().equals("m")){
			deviceGubun = "M";
		}else{
			deviceGubun = "P";
		}
		map.put("DEVICEGUBUN", deviceGubun);
		
		try {
			detail = brandDAO.getBrandMagazinePrev(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : getBrandMagazineNext
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세 (다음글)
	 */
	public SqlMap getBrandMagazineNext(String magazineIdx) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MAGAZINEIDX", magazineIdx);
		
		String deviceGubun = "";
		if(PathUtil.getDevice().equals("m")){
			deviceGubun = "M";
		}else{
			deviceGubun = "P";
		}
		map.put("DEVICEGUBUN", deviceGubun);
		
		try {
			detail = brandDAO.getBrandMagazineNext(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}

	/**
	 * @Method : getAdBrandCnt
	 * @Date		: 2018. 9. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	광고 브랜드 별 개수	
	 */
	public SqlMap getAdBrandCnt(BrandVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			detail = brandDAO.getAdBrandCnt(map); // 광고 브랜드 별 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	 * @Method : getMagBrandCnt
	 * @Date		: 2018. 9. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	매거진 브랜드 별 개수	
	 */
	public SqlMap getMagBrandCnt(BrandVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			detail = brandDAO.getMagBrandCnt(map); // 매거진 브랜드 별 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
}
