package com.gxenSoft.mall.mypage.review.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.TextUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.mypage.order.dao.MypageOrderDAO;
import com.gxenSoft.mall.mypage.order.service.MypageOrderService;
import com.gxenSoft.mall.mypage.review.dao.ReviewDAO;
import com.gxenSoft.mall.mypage.review.vo.ReviewVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private MypageOrderDAO mypageOrderDAO;
	
	@Autowired
	private MypageOrderService mypageOrderService;

	public List<SqlMap> getReviewList(ReviewVO vo, SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("PAGESTART", ((schProductVo.getPageNo() - 1) * schProductVo.getPageBlock()));					
		map.put("PAGEBLOCK", schProductVo.getPageBlock());																
		map.put("PAGENO", schProductVo.getPageNo());		
		map.put("GOODSIDX", vo.getGoodsIdx());
		map.put("REVIEWTYPE", vo.getReviewType());
		
		try{
			list = reviewDAO.getReviewList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}

	public int getReviewCnt(ReviewVO vo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			cnt = reviewDAO.getReviewCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	public int getPhotoCnt(ReviewVO vo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			cnt = reviewDAO.getPhotoCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	public int getReviewAvg(ReviewVO vo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			cnt = reviewDAO.getReviewAvg(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	public SqlMap getReviewDetail(ReviewVO vo)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("REVIEWIDX", vo.getReviewIdx());
		
		try{
			detail = reviewDAO.getReviewDetail(map); // 리뷰 상세
			if(TextUtils.isEmpty(vo.getStatusFlag())){
				reviewDAO.addViewCnt(map); // 조회 수 증가
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int reviewSave(ReviewVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		String uploadIMGPath = SpringMessage.getMessage("server.imgPath");
		
		map.put("GOODSIDX", vo.getGoodsIdx());			// 상품 일련번호
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 작성자 일련번호
		map.put("RATING", vo.getRating());					// 별점
		map.put("HAIRSTYLE", vo.getHairStyle());			// 헤어 길이
		map.put("HAIRTYPE", vo.getHairType());				// 헤어 타입
		map.put("REVIEWDESC", vo.getReviewDesc());	// 리뷰 내용
		map.put("LAYERTYPE", vo.getLayerType());			// 레이어 타입
		
		if (vo.getqImg() != null){ // 포토 후기
			map.put("REVIEWPOINT", "500");
			map.put("PAYDEDREASON", "POINT_REASON70");
		}else{ // 일반 후기
			map.put("REVIEWPOINT", "100");
			map.put("PAYDEDREASON", "POINT_REASON70");
		}
		
		if("I".equals(vo.getStatusFlag())) {
			if("review".equals(vo.getLayerType()) || "sample".equals(vo.getLayerType()) || ("order".equals(vo.getLayerType()) && vo.getChkReview().equals("Y"))  ){
				if(vo.getOrderDetailIdx()==null){
					map.put("WINNERIDX", vo.getWinnerIdx());		// 당첨자 일련번호
				}else if(vo.getWinnerIdx()==null){
					map.put("ORDERDETAILIDX", vo.getOrderDetailIdx()); // 주문 디테일 일련번호
				}
				
				int chkReview = reviewDAO.reviewCheck(map);	 // 당첨자 중복 리뷰 체크
				if(chkReview!=0){
					flag = -1;
				}else if(chkReview==0){
					flag = reviewDAO.reviewSave(map); // 리뷰 등록
					reviewDAO.memberPointAdd(map); // 회원 보유 포인트 증가
					reviewDAO.memberPointAddHistory(map); // 회원 포인트 증가 히스토리 추가
				}
				
				if (vo.getqImg() != null){
					int i = 1;
					for (String img : vo.getqImg()) {
						 File tmpFile = new File(img);
						 String fileFolder = img.substring(0,img.lastIndexOf(File.separator));
						
						String Filename = tmpFile.getName();
					    String sourceFile = uploadIMGPath+File.separator+img;
					    String targetFile = uploadIMGPath+File.separator+"review"+File.separator+(Integer)map.get("reviewIdx")+File.separator+Filename;				    
					    String thumSourceFile = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T90"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumTargetFile = uploadIMGPath+File.separator+"review"+File.separator+(Integer)map.get("reviewIdx")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T90"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);	
					    String thumSourceFile420 = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T420"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumTargetFile420 = uploadIMGPath+File.separator+"review"+File.separator+(Integer)map.get("reviewIdx")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T420"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumSourceFile200 = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T200"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumTargetFile200 = uploadIMGPath+File.separator+"review"+File.separator+(Integer)map.get("reviewIdx")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T200"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    
					    //qImg+Integer.toString(i)
					    File source = new File(sourceFile);
					    File target = new File(targetFile);
					    File th_source = new File(thumSourceFile);
					    File th_target = new File(thumTargetFile);
					    File th_source420 = new File(thumSourceFile420);
					    File th_target420 = new File(thumTargetFile420);
					    File th_source200 = new File(thumSourceFile200);
					    File th_target200 = new File(thumTargetFile200);
					    FileUtil.copyFile(source, target); //원본복사
					    FileUtil.copyFile(th_source, th_target); //썸네일복사 90
					    FileUtil.copyFile(th_source420, th_target420); //썸네일복사 420
					    FileUtil.copyFile(th_source200, th_target200); //썸네일복사 200
					    map.put("IMG"+Integer.toString(i), Filename);
					    i++;
					}
					com.gxenSoft.fileUtil.FileUtil.createPermission(uploadIMGPath+File.separator+"review"+File.separator+(Integer)map.get("reviewIdx"), "755"); // 폴더, 파일 권한
					if (i > 1) {
						map.put("REVIEWIDX", (Integer)map.get("reviewIdx"));
						reviewDAO.reviewImgInsert(map); // 리뷰 이미지 등록
					}
				}
			}
			
			//구매확정
			if ("order".equals(vo.getLayerType()) ) {
				map.clear();
				map.put("ORDERDETAILIDX",vo.getOrderDetailIdx());
				SqlMap detail = mypageOrderDAO.getOrderDetailInfo(map);
				int orderIdx = Integer.parseInt(detail.get("orderIdx").toString());
				
				String reason = "사용자 구매확정";
				map.clear();
				map.put("ORDERIDX", orderIdx);
				map.put("ORDERDETAILIDX",vo.getOrderDetailIdx());
				mypageOrderDAO.updateOrderDetailStatusTo900(map);
				
				OrderVO orderVo = new OrderVO();
				orderVo.setOrderIdx(orderIdx);
				orderVo.setMemberIdx(UserInfo.getUserInfo().getMemberIdx());
				orderVo.setRegHttpUserAgent("");
				orderVo.setRegIp("");
				mypageOrderService.insertOrderStatusLog(orderVo, vo.getOrderDetailIdx() , "900" ,reason);
				
				//master 상태 변경
				map.clear();
				map.put("ORDERIDX", orderIdx);
				mypageOrderDAO.updateOrderStatusCdAsDetailMinOrderStatusCd(map);
				
				//포인트 적립
				if (orderVo.getMemberIdx() > 0) {
					map.clear();
					map.put("ORDERDETAILIDX", vo.getOrderDetailIdx());
					int Point = mypageOrderDAO.getOrderSavePoint(map);
					
					if (Point > 0) {
						map.clear();
						map.put("MEMBER_IDX", orderVo.getMemberIdx());
						map.put("ORDER_IDX", orderVo.getOrderIdx());
						map.put("EVENT_IDX", vo.getOrderDetailIdx());
						map.put("PAY_DED_TYPE", "P"); //지급/차감구분 ( P : 지급, M : 차감 )
						map.put("PAY_DED_REASON", "POINT_REASON50"); //POINT_REASON50 : 구매확정에의한 구매포인트 지급
						map.put("PAYMENT_PRICE", Point);
						map.put("DEDUCTION_PRICE", ""); //차감포인트
						mypageOrderDAO.orderInsertPointHistory(map);
						
						map.clear();
						map.put("MEMBERIDX", orderVo.getMemberIdx());
						map.put("POINT", Point);
						mypageOrderDAO.memberPointUpdate(map);

/*	// 추천인 일단 주석 처리
						// 첫 주문이고 추천인이 있으면 추천인에게 포인트 지급
						map.clear();
						map.put("MEMBERIDX", orderVo.getMemberIdx());
						Integer recommendIdx = mypageOrderDAO.getRecommendIdx(map);	// 추천인 일련번호 (일련번호가 있다는건 현재가 최초 주문이고 추천인을 입력했다라는 뜻)
						if(recommendIdx != null && recommendIdx.intValue() > 0){
							int recommendPoint = 1000;		// 1000 포인트 지급
							
							map.clear();
							map.put("MEMBER_IDX", recommendIdx);	// 추천인 일련번호
							map.put("ORDER_IDX", null);
							map.put("EVENT_IDX", null);
							map.put("PAY_DED_TYPE", "P"); //지급/차감구분 ( P : 지급, M : 차감 )
							map.put("PAY_DED_REASON", "POINT_REASON20"); //POINT_REASON20 : 추천인 등록으로 인한 포인트지급
							map.put("PAYMENT_PRICE", recommendPoint);
							map.put("DEDUCTION_PRICE", ""); //차감포인트
							mypageOrderDAO.orderInsertPointHistory(map);
							
							map.clear();
							map.put("MEMBERIDX", recommendIdx);		// 추천인 일련번호
							map.put("POINT", recommendPoint);
							mypageOrderDAO.memberPointUpdate(map);

							// 추천인 일련번호 삭제
							map.clear();
							map.put("MEMBERIDX", orderVo.getMemberIdx());	// 추천한 사람 일련번호(주문자/로그인 한 회원)
							mypageOrderDAO.updateRecommendIdxNull(map);					
						}
*/
					}
				}
			}
					
		}else if("U".equals(vo.getStatusFlag())){
			if("review".equals(vo.getLayerType()) || "goods".equals(vo.getLayerType()) || "sample".equals(vo.getLayerType())){
				map.put("REVIEWIDX", vo.getReviewIdx()); // 리뷰 일련번호
				
				if (!TextUtils.isEmpty(vo.getImg1())) {
			    	map.put("IMG1", vo.getImg1().toString());
			    } 
				if (!TextUtils.isEmpty(vo.getImg2())) {
			    	map.put("IMG2", vo.getImg2().toString());
			    } 
				if (!TextUtils.isEmpty(vo.getImg3())) {
			    	map.put("IMG3", vo.getImg3().toString());
			    }
				
				if (vo.getqImg() != null) 
				{
					int i = 1;
					for (String img : vo.getqImg()) {
						File tmpFile = new File(img);
						String fileFolder = img.substring(0,img.lastIndexOf(File.separator));
						
						String Filename = tmpFile.getName();
					    String sourceFile = uploadIMGPath+File.separator+img;
					    String targetFile = uploadIMGPath+File.separator+"review"+File.separator+map.get("REVIEWIDX")+File.separator+Filename;				    
					    String thumSourceFile = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T90"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumTargetFile = uploadIMGPath+File.separator+"review"+File.separator+map.get("REVIEWIDX")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T90"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);				    
					    String thumSourceFile420 = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T420"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumTargetFile420 = uploadIMGPath+File.separator+"review"+File.separator+map.get("REVIEWIDX")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T420"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumSourceFile200 = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T200"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    String thumTargetFile200 = uploadIMGPath+File.separator+"review"+File.separator+map.get("REVIEWIDX")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T200"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
					    
					    //qImg+Integer.toString(i)
					    File source = new File(sourceFile);
					    File target = new File(targetFile);
					    File th_source = new File(thumSourceFile);
					    File th_target = new File(thumTargetFile);
					    File th_source420 = new File(thumSourceFile420);
					    File th_target420 = new File(thumTargetFile420);
					    File th_source200 = new File(thumSourceFile200);
					    File th_target200 = new File(thumTargetFile200);
					    
					    FileUtil.copyFile(source, target); //원본복사
					    FileUtil.copyFile(th_source, th_target); //썸네일복사 90
					    FileUtil.copyFile(th_source420, th_target420); //썸네일복사 420
					    FileUtil.copyFile(th_source200, th_target200); //썸네일복사 200
					    
					    if (TextUtils.isEmpty(vo.getImg1()) && StringUtils.isEmpty(map.get("IMG1"))) {
					    	map.put("IMG1", Filename);
					    } else {
						    if (TextUtils.isEmpty(vo.getImg2()) && StringUtils.isEmpty(map.get("IMG2"))) {
						    	map.put("IMG2", Filename);
						    } else {
						    	if (TextUtils.isEmpty(vo.getImg3()) && StringUtils.isEmpty(map.get("IMG3"))) {
							    	map.put("IMG3", Filename);
							    }	
						    }				    	
					    }
					    
					    i++;
					}
				}
				com.gxenSoft.fileUtil.FileUtil.createPermission(uploadIMGPath+File.separator+"review"+File.separator+map.get("REVIEWIDX"), "755"); // 폴더, 파일 권한
				flag = reviewDAO.reviewUpdate(map); // 리뷰 수정
			}
		}
		
		return flag;
	}

	public int getNoWriteReviewCnt() throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		try{
			cnt = reviewDAO.getNoWriteReviewCnt(map); // 작성 가능 한 리뷰 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	public int getWriteListCnt() throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		try{
			cnt = reviewDAO.getWriteListCnt(map); // 작성 한 리뷰 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	public List<SqlMap> getNoWriteList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(5);
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		// 페이징
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = reviewDAO.getNoWriteList(map); // 작성 가능한 리뷰 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	public List<SqlMap> getWriteList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(5);
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		// 페이징
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = reviewDAO.getWriteList(map); // 작성 한 리뷰 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int reviewDelete(ReviewVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("REVIEWIDX", vo.getReviewIdx()); // 리뷰 일련번호
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); // 회원 일련번호
		
		flag = reviewDAO.reviewDelete(map); // 리뷰 삭제
		if(flag > 0){
			reviewDAO.reviewDisplayDelete(map); // 메인 전시 관리 리뷰 삭제
		}
		
		if(flag > 0 && !TextUtils.isEmpty(vo.getReviewPoint())){
			// 이미지 폴더 삭제 [FileUtil 충돌]
			String uploadIMGPath = SpringMessage.getMessage("server.imgPath");
			String folder = "review/"+vo.getReviewIdx();
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
			
			map.put("REVIEWPOINT", vo.getReviewPoint());
			map.put("PAYDEDREASON", "POINT_REASON80");
			
			reviewDAO.memberPointMinus(map); // 회원 보유 포인트 차감
			reviewDAO.memberPointMinusHistory(map); // 회원 포인트 차감 히스토리 추가
		}
		
		return flag;
	}

	public SqlMap getGoodsDetail(ReviewVO vo) throws Exception {
		SqlMap goodsDetail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		goodsDetail = reviewDAO.getGoodsDetail(map); // 상품 정보
		
		return goodsDetail;
	}

	public SqlMap getOrderDetail(int orderDetailIdx, int memberIdx) throws Exception {
		SqlMap orderDetail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("ORDERDETAILIDX", orderDetailIdx);
		map.put("MEMBERIDX", memberIdx);
		
		orderDetail = reviewDAO.getOrderDetail(map); // 주문 디테일 정보
		
		return orderDetail;
	}

	public SqlMap getWinnerDetail(int winnerIdx, int memberIdx) throws Exception {
		SqlMap winnerDetail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("WINNERIDX", winnerIdx);
		map.put("MEMBERIDX", memberIdx);
		
		winnerDetail = reviewDAO.getWinnerDetail(map); // 당첨자 정보
		
		return winnerDetail;
	}

}
