package com.gxenSoft.mall.style.service;


import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.style.dao.StyleDAO;
import com.gxenSoft.mall.style.vo.CounselVO;
import com.gxenSoft.mall.style.vo.HowtouseVO;
import com.gxenSoft.mall.style.vo.SampleVO;
import com.gxenSoft.mall.style.vo.TipVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;



/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : StyleServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.style.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 13. 
    * HISTORY :   
    *
    *************************************
    */
@Service("styleService")
public class StyleServiceImpl implements StyleService {
	
	static final Logger logger = LoggerFactory.getLogger(StyleServiceImpl.class);
	
	@Autowired
	private StyleDAO styleDAO;
	

	/**
	    * @Method : getCounselListCnt
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	스타일 상담 리스트 총 개수
	   */
	public int getCounselListCnt(CounselVO vo, SearchVO schVO) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			cnt = styleDAO.getCounselListCnt(map); // 스타일 상담 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getCounselList
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	스타일 상담 리스트
	   */
	public List<SqlMap> getCounselList(CounselVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(6);
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = styleDAO.getCounselList(map); // 스타일 상담 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	    * @Method : counselSave
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	스타일 상담 신청
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int counselSave(CounselVO vo, int memberIdx) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		String uploadIMGPath = SpringMessage.getMessage("server.imgPath");
		map.put("QREGIDX", vo.getRegIdx()); 			// 작성자 일련번호
		map.put("QTITLE", vo.getqTitle());					// 제목
		map.put("QCONTENTS", vo.getqContents());	// 내용
		map.put("HAIRSTYLE", vo.getHairStyle());		// 모발 길이
		map.put("HAIRTYPE", vo.getHairType());			// 모발 타입
		map.put("ALARMYN", vo.getAlarmYn());			// 답변 알람 받기 여부
		map.put("MEMBERIDX", memberIdx);				// 회원 일련번호
		map.put("QEMAIL", vo.getqEmail());				// 회원 이메일
		if("I".equals(vo.getStatusFlag())){
			flag = styleDAO.counselSave(map); // 스타일 상담 신청
			
			if (vo.getqImg() != null) 
			{
				int i = 1;
				for (String img : vo.getqImg()) {
					 File tmpFile = new File(img);
					 String fileFolder = img.substring(0,img.lastIndexOf(File.separator));
					
					String Filename = tmpFile.getName();
				    String sourceFile = uploadIMGPath+File.separator+img;
				    String targetFile = uploadIMGPath+File.separator+"counsel"+File.separator+map.get("COUNSELIDX")+File.separator+Filename;				    
				    String thumSourceFile = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
				    String thumTargetFile = uploadIMGPath+File.separator+"counsel"+File.separator+map.get("COUNSELIDX")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);				    

				    //qImg+Integer.toString(i)
				    File source = new File(sourceFile);
				    File target = new File(targetFile);
				    File th_source = new File(thumSourceFile);
				    File th_target = new File(thumTargetFile);
				    FileUtil.copyFile(source, target); //원본복사
				    FileUtil.copyFile(th_source, th_target); //썸네일복사
				    map.put("QIMG"+Integer.toString(i), Filename);
				    i++;
				}
				//이미지정보 수정
				if (i > 1) {
					map.put("COUNSELIDX", map.get("COUNSELIDX"));
					styleDAO.counselUpdate(map); 
				}
			}
			
		}else if("U".equals(vo.getStatusFlag())){
			map.put("COUNSELIDX", vo.getCounselIdx());	// 일련번호
			if (!vo.getqImg1().isEmpty()) {
		    	map.put("QIMG1", vo.getqImg1().toString());
		    } 
			if (!vo.getqImg2().isEmpty()) {
		    	map.put("QIMG2", vo.getqImg2().toString());
		    } 
			if (!vo.getqImg3().isEmpty()) {
		    	map.put("QIMG3", vo.getqImg3().toString());
		    } 
			if (!vo.getqImg4().isEmpty()) {
		    	map.put("QIMG4", vo.getqImg4().toString());
		    } 
			
			if (vo.getqImg() != null) 
			{
				int i = 1;
				for (String img : vo.getqImg()) {
					File tmpFile = new File(img);
					String fileFolder = img.substring(0,img.lastIndexOf(File.separator));
					
					String Filename = tmpFile.getName();
				    String sourceFile = uploadIMGPath+File.separator+img;
				    String targetFile = uploadIMGPath+File.separator+"counsel"+File.separator+map.get("COUNSELIDX")+File.separator+Filename;				    
				    String thumSourceFile = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
				    String thumTargetFile = uploadIMGPath+File.separator+"counsel"+File.separator+map.get("COUNSELIDX")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);				    

				    //qImg+Integer.toString(i)
				    File source = new File(sourceFile);
				    File target = new File(targetFile);
				    File th_source = new File(thumSourceFile);
				    File th_target = new File(thumTargetFile);
				    FileUtil.copyFile(source, target); //원본복사
				    FileUtil.copyFile(th_source, th_target); //썸네일복사
				    if (vo.getqImg1().isEmpty() && StringUtils.isEmpty(map.get("QIMG1"))) {
				    	map.put("QIMG1", Filename);
				    } else {
					    if (vo.getqImg2().isEmpty() && StringUtils.isEmpty(map.get("QIMG2"))) {
					    	map.put("QIMG2", Filename);
					    } else {
					    	if (vo.getqImg3().isEmpty() && StringUtils.isEmpty(map.get("QIMG3"))) {
						    	map.put("QIMG3", Filename);
						    } else {
						    	if (vo.getqImg4().isEmpty() && StringUtils.isEmpty(map.get("QIMG4"))) {
							    	map.put("QIMG4", Filename);
							    } 
						    }		
					    }				    	
				    }
				    i++;
				}
			}
			flag = styleDAO.counselUpdate(map); // 스타일 상담 수정
		}
		
		return flag;
	}

	/**
	    * @Method : counselView
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	스타일 상담 상세	
	   */
	public SqlMap counselView(CounselVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("COUNSELIDX", vo.getCounselIdx()); // 일련번호
		
		try{
			detail = styleDAO.counselView(map); // 스타일 상담 상세
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	    * @Method : counselDelete
	    * @Date: 2017. 7. 14.
	    * @Author :  임  재  형
	    * @Description	:	스타일 상담 삭제
	   */
	public int counselDelete(CounselVO vo, int memberidx) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("COUNSELIDX", vo.getCounselIdx()); // 일련번호
		map.put("MEMBERIDX", memberidx);
		try{
			flag = styleDAO.counselDelete(map); // 스타일 상담 삭제
			if(flag > 0){
				// 이미지 폴더 삭제 [FileUtil 충돌]
				String uploadIMGPath = SpringMessage.getMessage("server.imgPath");
				String folder = "counsel/"+vo.getCounselIdx();
				String uploadPath = uploadIMGPath + File.separator + folder + File.separator;
				
		    	try{
		    		File file = new File(uploadPath);
		    		File[] files = file.listFiles(); 
		    		
		    		if(files !=null){
		    			for (int i=0; i< files.length; i++) { 
		        			files[i].delete();								//파일 삭제
		        		}
		    		}
		    		if(file.exists()){
		    			file.delete();										//폴더 삭제	
		    		}
		    	}catch(Exception e){
		    		e.printStackTrace();
		    		throw e;
		    	}
		    	// 이미지 폴더 삭제 [FileUtil 충돌]
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	/**
	    * @Method : getSampleInfo
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	정품신청 정보	
	   */
	public SqlMap getSampleInfo(SampleVO vo, SearchVO schVO) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SAMPLEIDX", vo.getSampleIdx());
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		
		try{
			detail = styleDAO.getSampleInfo(map); // 정품신청 정보
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	    * @Method : getSampleReplyListCnt
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	정품신청 댓글 리스트 총 개수
	   */
	public int getSampleReplyListCnt(SampleVO vo) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SAMPLEIDX", vo.getSampleIdx()); // 정품신청 일련번호
		
		try{
			cnt = styleDAO.getSampleReplyListCnt(map); // 정품신청 댓글 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getSampleReplyList
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	정품신청 댓글 리스트
	   */
	public List<SqlMap> getSampleReplyList(SampleVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(20);
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("SAMPLEIDX", vo.getSampleIdx()); // 정품신청 일련번호
		map.put("SCHCHECK", schVO.getSchCheck());
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = styleDAO.getSampleReplyList(map); // 정품신청 댓글 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	    * @Method : sampleReplySave
	    * @Date: 2017. 7. 13.
	    * @Author :  임  재  형
	    * @Description	:	정품신청 댓글 등록
	   */
	public int sampleReplySave(SampleVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("REGIDX", UserInfo.getUserInfo().getMemberIdx());		// 작성자 일련번호
		map.put("REPLYCONTENTS", vo.getReplyContents());	// 내용
		map.put("SAMPLEIDX", vo.getSampleIdx());
		
		try{
			flag = styleDAO.sampleReplySave(map); // 정품신청 댓글 등록
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}
	
	/**
	    * @Method : sampleReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	정품신청 댓글 삭제
	   */
	public int sampleReplyDeleteAjax(SampleVO vo, int memberIdx) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SAMPLEREPLYIDX", vo.getSampleReplyIdx()); // 댓글 일련번호
		map.put("MEMBERIDX", memberIdx);	// 회원 일련번호
		
		try{
			flag = styleDAO.sampleReplyDeleteAjax(map); // 정품신청 댓글 삭제
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}
	
	/**
	 * @Method : addSampleReadCnt
	 * @Date		: 2018. 4. 10.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 조회 수 증가
	 */
	public void addSampleReadCnt(SampleVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SAMPLEIDX", vo.getSampleIdx());
		
		styleDAO.addSampleReadCnt(map); // 정품신청 조회 수 증가
	}

	/**
	    * @Method : getTipListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  임  재  형
	    * @Description	:	스타일 팁 리스트 총 개수
	   */
	public int getTipListCnt(SearchVO schVO, TipVO vo) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BRANDIDX", vo.getBrandIdx());	// 브랜드 일련번호
		map.put("BRANDYN", "N");
		
		try{
			if(vo.getBrandIdx() != null){ // 브랜드 일 경우
				map.put("BRANDYN", "Y");
			}
			cnt = styleDAO.getTipBrandListCnt(map); // 스타일 팁 브랜드 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getTipList
	    * @Date: 2017. 7. 14.
	    * @Author :  임  재  형
	    * @Description	:	스타일 팁 리스트
	   */
	public List<SqlMap> getTipList(SearchVO schVO, TipVO vo) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(6);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("BRANDIDX", vo.getBrandIdx());	// 브랜드 일련번호
		map.put("BRANDYN", "N");
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			if(vo.getBrandIdx() != null){ // 브랜드 일 경우
				map.put("BRANDYN", "Y");
			}
			list = styleDAO.getTipBrandList(map); // 스타일 팁 브랜드 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	    * @Method : getTipView
	    * @Date: 2017. 7. 14.
	    * @Author :  임  재  형
	    * @Description	:	스타일 팁 상세
	   */
	public SqlMap getTipView(TipVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("TIPIDX", vo.getTipIdx()); // 일련번호
		
		try{
			detail = styleDAO.getTipView(map); // 스타일 팁 상세
			styleDAO.addTipReadCnt(map);	// 스타일 팁 조회 수 증가
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	    * @Method : getHowtouseListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  임  재  형
	    * @Description	:	상품 사용법 리스트 총 개수
	   */
	public int getHowtouseListCnt(HowtouseVO vo, SearchVO schVO) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BRANDIDX", vo.getBrandIdx());	// 브랜드 일련번호
		map.put("BRANDYN", "N");
		
		try{
			if(vo.getBrandIdx() != null){
				map.put("BRANDYN", "Y");
			}
			cnt = styleDAO.getHowtouseBrandListCnt(map); // 상품 사용법 브랜드 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getHowtouseList
	    * @Date: 2017. 7. 14.
	    * @Author :  임  재  형
	    * @Description	:	상품 사용법 리스트
	   */
	public List<SqlMap> getHowtouseList(HowtouseVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(9);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("BRANDIDX", vo.getBrandIdx());	// 브랜드 일련번호
		map.put("BRANDYN", "N");
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			if(vo.getBrandIdx() != null){
				map.put("BRANDYN", "Y");
			}
			list = styleDAO.getHowtouseBrandList(map); // 상품 사용법 브랜드 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	    * @Method : getHowtouseDetailMaster
	    * @Date: 2017. 7. 17.
	    * @Author :  임  재  형
	    * @Description	:	상품 사용법 마스터
	   */
	public SqlMap getHowtouseDetailMaster(HowtouseVO vo, SearchVO schVO) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("HOWTOUSEIDX", vo.getHowtouseIdx()); // 일련번호
		
		try{
			detail = styleDAO.getHowtouseDetailMaster(map); // 상품 사용법 마스터
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	    * @Method : getHowtouseDetailList
	    * @Date: 2017. 7. 14.
	    * @Author :  임  재  형
	    * @Description	:	상품 사용법 상세
	   */
	public List<SqlMap> getHowtouseDetailList(HowtouseVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("HOWTOUSEIDX", vo.getHowtouseIdx()); // 일련번호
		
		try{
			detail = styleDAO.getHowtouseDetailList(map); // 상품 사용법 상세
			styleDAO.addHowReadCnt(map);	// 상품 사용법 조회 수 증가
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	 * @Method : getTipBrandCnt
	 * @Date		: 2018. 9. 11.
	 * @Author	:  임  재  형 
	 * @Description	:	스타일 팁 브랜드 별 개수	
	 */
	public SqlMap getTipBrandCnt(SearchVO schVO, TipVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			detail = styleDAO.getTipBrandCnt(map); // 스타일 팁 브랜드 별 개수	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	 * @Method : getHowtoBrandCnt
	 * @Date		: 2018. 9. 12.
	 * @Author	:  임  재  형 
	 * @Description	:	상품 사용법 브랜드 별 개수	
	 */
	public SqlMap getHowtoBrandCnt(SearchVO schVO, HowtouseVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			detail = styleDAO.getHowtoBrandCnt(map); // 상품 사용법 브랜드 별 개수	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	 * @Method : getSampleListCnt
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 리스트 총 개수
	 */
	public int getSampleListCnt(SampleVO vo) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			cnt = styleDAO.getSampleListCnt(map); // 정품신청 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	 * @Method : getSampleList
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 리스트
	 */
	public List<SqlMap> getSampleList(SampleVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(10);
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = styleDAO.getSampleList(map); // 정품신청 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : sampleApplAjax
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int sampleApplAjax(SampleVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 작성자 일련번호
		map.put("SAMPLEIDX", vo.getSampleIdx());
		
		try{
			int dubCheck = styleDAO.sampleDupCheck(map); // 정품신청 중복체크
			
			if(dubCheck != 0){
				flag = -1;
			}else{
				flag = styleDAO.sampleAppl(map); // 정품신청
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	/**
	 * @Method : winnerCheckAjax
	 * @Date		: 2019. 4. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	당첨여부 체크
	 */
	public int winnerCheckAjax(SampleVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 작성자 일련번호
		map.put("SAMPLEIDX", vo.getSampleIdx());
		
		try{
			flag = styleDAO.sampleDupCheck(map); // 당첨여부 체크
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

}
