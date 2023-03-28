package com.gxenSoft.mall.mypage.review.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxenSoft.fileUtil.FileUtil;
import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.mypage.review.service.ReviewService;
import com.gxenSoft.mall.mypage.review.vo.ReviewVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.method.DateUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;

@Controller
public class ReviewController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CommonService commonService;

	@RequestMapping("/ajax/mypage/review/reviewListAjax") 
	public ModelAndView reviewListAjax(ReviewVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		schProductVo.setPageBlock(5);
		int totalCount = reviewService.getReviewCnt(vo);							// 리뷰 총 건수
		int photoCnt = reviewService.getPhotoCnt(vo);								// 포토 리뷰 총 건수
		int reviewAvg = reviewService.getReviewAvg(vo);							// 구매 만족도 평균 별표시
		
		List<SqlMap> list = reviewService.getReviewList(vo, schProductVo);
		/*for(int i=0; i<list.size(); i++){
			list.get(i).put("reviewDesc", MethodUtil.repl(list.get(i).get("reviewDesc").toString()));
		}*/
		
		Page page = new Page(); 
		if(vo.getReviewType().equals("ALL")){
			page.pagingInfo(schProductVo, totalCount);
		}else{
			page.pagingInfo(schProductVo, photoCnt);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("VO", vo);
		mav.addObject("list", list);
		mav.addObject("totalCount", totalCount);
		mav.addObject("photoCnt", photoCnt);
		mav.addObject("reviewAvg", reviewAvg);
		mav.addObject("page", page);
		mav.addObject("SCHVO", schProductVo);
		mav.setViewName(PathUtil.getCtx()+"/mypage/review/reviewList");		
		
		return mav;
	}

	@RequestMapping("/ajax/mypage/review/reviewDetailAjax") 
	public ModelAndView reviewDetailAjax(ReviewVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		ModelAndView mav = new ModelAndView();
		
		SqlMap detail = reviewService.getReviewDetail(vo);
		detail.put("reviewDesc", MethodUtil.repl(detail.get("reviewDesc").toString()));
		
		mav.addObject("IS_LOGIN", CommonMethod.isLogin(request));
		mav.addObject("memberIdx", UserInfo.getUserInfo().getMemberIdx());
		mav.addObject("VO", vo);
		mav.addObject("detail", detail);
		mav.setViewName(PathUtil.getCtx()+"/mypage/review/reviewDetail");
		
		return mav;
	}

	@RequestMapping("/mypage/review/reviewDetail") 
	public String reviewDetail(ReviewVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		SqlMap detail = reviewService.getReviewDetail(vo);
		detail.put("reviewDesc", MethodUtil.repl(detail.get("reviewDesc").toString()));
		
		model.addAttribute("detail", detail);
		model.addAttribute("VO", vo);
		model.addAttribute("IS_LOGIN", CommonMethod.isLogin(request));
		model.addAttribute("memberIdx", UserInfo.getUserInfo().getMemberIdx());
		
		return PathUtil.getCtx()+"/mypage/review/reviewDetail";
	}

	@RequestMapping(value = "/ajax/mypage/review/reviewFileupload", produces="application/json")
	@ResponseBody
	public String reviewFileupload(MultipartFile[] fileData, HttpServletRequest request )throws  Exception{
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<String> arrFile = new ArrayList<String>();		
		List<String> oarrFile = new ArrayList<String>();		
		
		for (MultipartFile file : fileData) { 
			String path = "";
			String opath = ""; 
			FileVO fvo = FileUtil.multiPart2Img(file, "reviewTemp"+File.separator+DateUtil.dateFormat("yyyyMMdd") );	
			FileUtil.makeThumnail (fvo.getFileFolder() , fvo.getUploadFileNm() ,90 ,90);
			FileUtil.makeThumnail (fvo.getFileFolder() , fvo.getUploadFileNm() ,420 ,420);
			FileUtil.makeThumnail (fvo.getFileFolder() , fvo.getUploadFileNm() ,200 ,200);
			path = File.separator + fvo.getFileFolder() + File.separator + FileUtil.getFileName(fvo.getUploadFileNm())+"_T90"+"."+FileUtil.getFileExt(fvo.getUploadFileNm());
			opath = File.separator + fvo.getFileFolder() + File.separator + fvo.getUploadFileNm();
			arrFile.add(path);
			oarrFile.add(opath);
		} 
		//Object to JSON in String
		result.put("code", "1");
		result.put("msg", "file upload success");
		result.put("files",arrFile);
		result.put("ofiles",oarrFile);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	@RequestMapping("/ajax/mypage/review/reviewSave")
	public @ResponseBody int reviewSave(ReviewVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int flag = 0;
		
		flag = reviewService.reviewSave(vo); // 리뷰 등록
		
		return flag;
	}

	@RequestMapping("/mypage/review/reviewSave")
	public void reviewSaveM(ReviewVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int flag = reviewService.reviewSave(vo); // 리뷰 등록
		
		String url = "";
		if(vo.getLayerType()!=null && !vo.getLayerType().isEmpty()){
			if("order".equals(vo.getLayerType())){
				url = "/mypage/order/myOrderList.do";
			}else if("goods".equals(vo.getLayerType())){
				String goodsCd = "";
				if(vo.getGoodsCd()!=null && !vo.getGoodsCd().isEmpty()){
					goodsCd = vo.getGoodsCd();
				}
				url = "/product/productView.do?goodsCd="+goodsCd;
			}else if("review".equals(vo.getLayerType())){
				url = "/mypage/review/writeReviewList.do";
			}else if("sample".equals(vo.getLayerType())){
				url = "/mypage/sample/sampleList.do";
			}
		}
		
		String msg = "";
		if("U".equals(vo.getStatusFlag())){
			msg = "수정";
		}else{
			msg = "등록";
		}
		
		if (flag > 0) {
			MethodUtil.alertMsgUrl(request, response, "리뷰가 "+msg+" 되었습니다.", url);
		}else if(flag == -1){
			MethodUtil.alertMsgUrl(request, response, "이미 등록 하셨습니다.", url);
		}else{
			MethodUtil.alertMsgUrl(request, response, msg+" 중 오류가 발생 하였습니다.", url);
		}
	}

	@RequestMapping("/mypage/review/noWriteReviewList") 
	public String reviewNoWriteList(ReviewVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int noWriteCount = reviewService.getNoWriteReviewCnt();	// 작성 가능한 리뷰 총 개수
		int writeCount = reviewService.getWriteListCnt();	// 작성 한 리뷰 총 개수
		List<SqlMap> noWriteList = reviewService.getNoWriteList(schVO); // 작성 가능한 리뷰 리스트
		
		Page page = new Page(); 
		page.pagingInfo(schVO, noWriteCount);
		
		model.addAttribute("noWriteCount", noWriteCount);
		model.addAttribute("writeCount", writeCount);
		model.addAttribute("noWriteList", noWriteList);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
	
		return PathUtil.getCtx()+"/mypage/review/noWriteReviewList";
	}

	@RequestMapping("/mypage/review/writeReviewList") 
	public String reviewWriteList(ReviewVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int noWriteCount = reviewService.getNoWriteReviewCnt();	// 작성 가능한 리뷰 총 개수
		int writeCount = reviewService.getWriteListCnt();	// 작성 한 리뷰 총 개수
		List<SqlMap> writeList = reviewService.getWriteList(schVO);	// 작성 한 리뷰 리스트
		for(int i=0; i<writeList.size(); i++){
			writeList.get(i).put("reviewDesc", MethodUtil.repl(writeList.get(i).get("reviewDesc").toString()));
		}
		
		Page page = new Page(); 
		page.pagingInfo(schVO, writeCount);
		
		model.addAttribute("noWriteCount", noWriteCount);
		model.addAttribute("writeCount", writeCount);
		model.addAttribute("writeList", writeList);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/mypage/review/writeReviewList";
	}

	@RequestMapping("/ajax/mypage/review/reviewDelete")
	public @ResponseBody int reviewDeleteAjax(ReviewVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int flag = 0;
		
		flag = reviewService.reviewDelete(vo); // 리뷰 삭제
		
		return flag;
	}

	@RequestMapping("/mypage/review/reviewDelete")
	public void reviewDelete(ReviewVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int flag = reviewService.reviewDelete(vo); // 리뷰 삭제
		
		String url = "";
		if(vo.getLayerType()!=null && !vo.getLayerType().isEmpty()){
			if("order".equals(vo.getLayerType())){
				url = "/mypage/order/myOrderList.do";
			}else if("goods".equals(vo.getLayerType())){
				url = "/mypage/review/writeReviewList.do";
			}else if("review".equals(vo.getLayerType())){
				url = "/mypage/review/writeReviewList.do";
			}else if("sample".equals(vo.getLayerType())){
				url = "/mypage/sample/sampleList.do";
			}
		}
		
		if (flag > 0) {
			MethodUtil.alertMsgUrl(request, response, "삭제가 완료 되었습니다.", url);
		}else{
			MethodUtil.alertMsgUrl(request, response, "삭제 중 오류가 발생 하였습니다.", url);
		}
	}

	@RequestMapping("/ajax/mypage/review/reviewWriteAjax") 
	public ModelAndView reviewWriteAjax(ReviewVO vo, HttpServletRequest request , HttpServletResponse response, HttpSession session)throws Exception{
		ModelAndView mav = new ModelAndView();
		
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
			return null;
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
			return null;
		}
		
		if(vo.getOrderDetailIdx()==null && vo.getWinnerIdx()==null){
			MethodUtil.alertMsgBack(request, response, "유효하지 않은 정보 입니다.");
			return null;
		}
		
		String orderDetailIdx = "";
		String winnerIdx = "";
		
		if(vo.getOrderDetailIdx()==null && vo.getWinnerIdx()==null ) {
			MethodUtil.alertMsgBack(request, response, "파라메터가 유효하지 않습니다.");
			return null;
		}
		if(vo.getOrderDetailIdx()!=null ) {
			orderDetailIdx = vo.getOrderDetailIdx().toString();
		}

		if(vo.getWinnerIdx()!=null ) {
			winnerIdx = vo.getWinnerIdx().toString();
		}

		if(orderDetailIdx.isEmpty() && winnerIdx.isEmpty()) {
			MethodUtil.alertMsgBack(request, response, "파라메터가 유효하지 않습니다.");
			return null;
		}
		
		if(!orderDetailIdx.isEmpty()){
			SqlMap orderDetail = reviewService.getOrderDetail(vo.getOrderDetailIdx(), UserInfo.getUserInfo().getMemberIdx());	// 주문 디테일 정보
			
			if(orderDetail==null){
				MethodUtil.alertMsgBack(request, response, "주문정보가 존재하지 않습니다.");
				return null;
			}else{
				vo.setGoodsIdx(orderDetail.get("goodsIdx").toString());
				vo.setOrderDetailIdx(Integer.parseInt(orderDetail.get("orderDetailIdx").toString()));
			}
		}else {
			SqlMap winnerDetail = reviewService.getWinnerDetail(vo.getWinnerIdx(), UserInfo.getUserInfo().getMemberIdx());	// 당첨자 정보
			
			if(winnerDetail==null){
				MethodUtil.alertMsgBack(request, response, "당첨자정보가 존재하지 않습니다.");
				return null;
			}else{
				vo.setGoodsIdx(winnerDetail.get("goodsIdx").toString());
				vo.setWinnerIdx(Integer.parseInt(winnerDetail.get("winnerIdx").toString()));
			}
		}

		if("U".equals(vo.getStatusFlag())){
			SqlMap detail = reviewService.getReviewDetail(vo); // 리뷰 상세
			vo.setGoodsIdx(detail.get("goodsIdx").toString());
			
			if (!StringUtils.isEmpty(detail.get("img1"))) {
				String img1 = detail.get("img1").toString();
				detail.put("imgPath1", File.separator + "review" + File.separator +detail.get("reviewIdx").toString()+ File.separator+ FileUtil.getFileName(img1)+"_T90"+"."+FileUtil.getFileExt(img1));
			}
			if (!StringUtils.isEmpty(detail.get("img2"))) {
				String img2 = detail.get("img2").toString();
				detail.put("imgPath2", File.separator + "review" + File.separator +detail.get("reviewIdx").toString()+ File.separator+ FileUtil.getFileName(img2)+"_T90"+"."+FileUtil.getFileExt(img2));
			}
			if (!StringUtils.isEmpty(detail.get("img3"))) {
				String img3 = detail.get("img3").toString();
				detail.put("imgPath3", File.separator + "review" + File.separator +detail.get("reviewIdx").toString()+ File.separator+ FileUtil.getFileName(img3)+"_T90"+"."+FileUtil.getFileExt(img3));
			}
			
			mav.addObject("detail", detail);
		}
		SqlMap goodsDetail = reviewService.getGoodsDetail(vo);	// 상품 정보
		
		List<SqlMap> hairStyleList = commonService.getCodeList("HAIR_STYLE"); // 모발 길이 리스트
		List<SqlMap> hairTypeList = commonService.getCodeList("HAIR_TYPE"); // 모발 타입 리스트
		
		mav.addObject("hairStyleList", hairStyleList);
		mav.addObject("hairTypeList", hairTypeList);
		mav.addObject("goodsDetail", goodsDetail);
		mav.addObject("VO", vo);
		mav.setViewName(PathUtil.getCtx()+"/mypage/review/reviewWrite");
		
		return mav;
	}

	@RequestMapping("/mypage/review/reviewWrite") 
	public String reviewWrite(ReviewVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
			return null;
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
			return null;
		}
		
		if(vo.getOrderDetailIdx()==null && vo.getWinnerIdx()==null){
			MethodUtil.alertMsgBack(request, response, "유효하지 않은 정보 입니다.");
			return null;
		}
		String orderDetailIdx = "";
		String winnerIdx = "";
		
		if(vo.getOrderDetailIdx()==null && vo.getWinnerIdx()==null ) {
			MethodUtil.alertMsgBack(request, response, "파라메터가 유효하지 않습니다.");
			return null;
		}
		if(vo.getOrderDetailIdx()!=null ) {
			orderDetailIdx = vo.getOrderDetailIdx().toString();
		}

		if(vo.getWinnerIdx()!=null ) {
			winnerIdx = vo.getWinnerIdx().toString();
		}

		if(orderDetailIdx.isEmpty() && winnerIdx.isEmpty()) {
			MethodUtil.alertMsgBack(request, response, "파라메터가 유효하지 않습니다.");
			return null;
		}
		
		
		if(!orderDetailIdx.isEmpty()){
			SqlMap orderDetail = reviewService.getOrderDetail(vo.getOrderDetailIdx(), UserInfo.getUserInfo().getMemberIdx());	// 주문 디테일 정보
			
			if(orderDetail==null){
				MethodUtil.alertMsgBack(request, response, "주문정보가 존재하지 않습니다.");
				return null;
			}else{
				vo.setGoodsIdx(orderDetail.get("goodsIdx").toString());
				vo.setOrderDetailIdx(Integer.parseInt(orderDetail.get("orderDetailIdx").toString()));
			}
		}else {
			SqlMap winnerDetail = reviewService.getWinnerDetail(vo.getWinnerIdx(), UserInfo.getUserInfo().getMemberIdx());	// 당첨자 정보
			
			if(winnerDetail==null){
				MethodUtil.alertMsgBack(request, response, "당첨자정보가 존재하지 않습니다.");
				return null;
			}else{
				vo.setGoodsIdx(winnerDetail.get("goodsIdx").toString());
				vo.setWinnerIdx(Integer.parseInt(winnerDetail.get("winnerIdx").toString()));
			}
		}
		
		
		if("U".equals(vo.getStatusFlag())){
			SqlMap detail = reviewService.getReviewDetail(vo); // 리뷰 상세
			vo.setGoodsIdx(detail.get("goodsIdx").toString());
			
			if (!StringUtils.isEmpty(detail.get("img1"))) {
				String img1 = detail.get("img1").toString();
				detail.put("imgPath1", File.separator + "review" + File.separator +detail.get("reviewIdx").toString()+ File.separator+ FileUtil.getFileName(img1)+"_T90"+"."+FileUtil.getFileExt(img1));
			}
			if (!StringUtils.isEmpty(detail.get("img2"))) {
				String img2 = detail.get("img2").toString();
				detail.put("imgPath2", File.separator + "review" + File.separator +detail.get("reviewIdx").toString()+ File.separator+ FileUtil.getFileName(img2)+"_T90"+"."+FileUtil.getFileExt(img2));
			}
			if (!StringUtils.isEmpty(detail.get("img3"))) {
				String img3 = detail.get("img3").toString();
				detail.put("imgPath3", File.separator + "review" + File.separator +detail.get("reviewIdx").toString()+ File.separator+ FileUtil.getFileName(img3)+"_T90"+"."+FileUtil.getFileExt(img3));
			}
			
			model.addAttribute("detail", detail);
		}
		SqlMap goodsDetail = reviewService.getGoodsDetail(vo);	// 상품 정보
		List<SqlMap> hairStyleList = commonService.getCodeList("HAIR_STYLE"); // 모발 길이 리스트
		List<SqlMap> hairTypeList = commonService.getCodeList("HAIR_TYPE"); // 모발 타입 리스트
		
		model.addAttribute("hairStyleList", hairStyleList);
		model.addAttribute("hairTypeList", hairTypeList);
		model.addAttribute("goodsDetail", goodsDetail);
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/mypage/review/reviewWrite";
	}
	
}
