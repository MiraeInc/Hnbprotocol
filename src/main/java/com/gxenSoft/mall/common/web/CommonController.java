package com.gxenSoft.mall.common.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gxenSoft.fileUtil.FileUtil;
import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.CommonVO;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.etc.service.EtcService;
import com.gxenSoft.mall.event.timeSale.service.TimeSaleService;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonController
 * PACKAGE NM : com.gatsbyMall.common.web
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
@Controller
public class CommonController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private EtcService etcService;
	@Autowired
	private TimeSaleService timeSaleService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/main", "/gatsby/main", "/gatsby" })
	public String gatsbyMain(CommonVO vo,
							 SearchVO schVO,
							 Model model,
							 @RequestParam(value = "goodsFlag", required = false) String goodsFlag
	) throws Exception{
		List<SqlMap> mainBannerList = commonService.getMainBannerList("GB_BANNER");	// 갸스비 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("GB", "sale");					// 메인 상품 리스트 (SALE)
		List<SqlMap> newproductsList = commonService.getMainProductList("GB", "newproducts"); // 메인 상품 리스트 (NEW PRODUCTS)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("GB", "html"); 									// 메인 HTML
		List<SqlMap> reviewList = commonService.getMainReviewList("GB", "review"); 			// 메인 후기 리스트
		List<SqlMap> snsList = commonService.getMainSnsList("GB");									// 메인 SNS 리스트
		List<SqlMap> setList = commonService.getMainProductList("GB", "set");						// 메인 상품 리스트 (SET)
		List<SqlMap> tipList = commonService.getMainTipList("GB", "tip"); 								// 메인 TIP 리스트
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> eventList = commonService.getMainEventList("GB"); 								// 메인 이벤트 리스트
		List<SqlMap> noticeList = commonService.getNoticeList();											// 공지사항 리스트
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트

		if (goodsFlag != null) {
			saleList = saleList.stream()
					.filter(x -> goodsFlag.equals(x.get("goodsFlag")))
					.collect(Collectors.toList());
		}

		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("newproductsList", newproductsList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("snsList", snsList);
		model.addAttribute("setList", setList);
		model.addAttribute("tipList", tipList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("eventList", eventList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		model.addAttribute("goodsFlag", goodsFlag);
		
		return PathUtil.getCtx()+"/gatsby/gatsbyMain";
	}
	
	/**
	 * @Method : main
	 * @Date		: 2017. 6. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	갸스비 메인 페이지
	 */
	/*@RequestMapping(value = { "/gatsby/main", "/gatsby" })
	public String gatsbyMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{	
		List<SqlMap> mainBannerList = commonService.getMainBannerList("BANNER_GUBUN12"); // 갸스비몰 메인배너 리스트
		SqlMap pcMidBanner = commonService.getPcMidBanner("BANNER_GUBUN52"); // PC 갸스비몰 중간 배너
		SqlMap moMidBanner = commonService.getMoMidBanner("BANNER_GUBUN52"); // MO 갸스비몰 중간 배너
		SqlMap sourceBanner = commonService.getSourceBanner(1); // 갸스비몰 제품소개
		List<SqlMap> hashList = commonService.getMainHashtagList(1); // 메인 해시태그 리스트
		List<SqlMap> reviewList = commonService.getReviewList(1); // 갸스비몰 후기 리스트
		List<SqlMap> eventList = commonService.getEventList(); // 이벤트 리스트
		for(SqlMap item : reviewList) {
			ProductVO pVo = new ProductVO();
			pVo.setGoodsIdx(item.get("goodsIdx").toString());
			List<SqlMap> productHashList = productService.getProductHashList(pVo, null);	
			item.put("hashList", productHashList);
		}
		
		if(PathUtil.getDevice().equals("m")){
			SqlMap timeSale = timeSaleService.getTimeSaleDetail(1);	// 갸스비몰 타임세일 상세
			model.addAttribute("timeSale", timeSale);
		}
		
		List<SqlMap> bestList = commonService.getMainGoodsList("best", 1, schVO); // 갸스비몰 베스트 리스트
		List<SqlMap> tipsList = commonService.getTipsList(1); // 갸스비몰 TIPS 리스트
		List<SqlMap> productSubImgList = productService.getProductSubImgList();
		List<SqlMap> noticeList = commonService.getNoticeList();	// 공지사항 리스트
		List<SqlMap> popupList = commonService.getPopupList(); // 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("pcMidBanner", pcMidBanner);
		model.addAttribute("moMidBanner", moMidBanner);
		model.addAttribute("sourceBanner", sourceBanner);
		model.addAttribute("hashList", hashList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("tipsList", tipsList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("eventList", eventList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/gatsby/main";		
	}*/
	
	/**
	 * @Method : bifestaMain
	 * @Date		: 2018. 2. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	비페스타 메인 페이지
	 */
	@RequestMapping(value = { "/bifesta/main", "/bifesta" })
	public String bifestaMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{
		List<SqlMap> mainBannerList = commonService.getMainBannerList("BF_BANNER");		// 비페스타 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("BF", "sale");					// 메인 상품 리스트 (SALE)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("BF", "html"); 									// 메인 HTML
		List<SqlMap> reviewList = commonService.getMainReviewList("BF", "review"); 			// 메인 후기 리스트
		List<SqlMap> snsList = commonService.getMainSnsList("BF");										// 메인 SNS 리스트
		List<SqlMap> setList = commonService.getMainProductList("BF", "set");						// 메인 상품 리스트 (SET)
		List<SqlMap> tipList = commonService.getMainTipList("BF", "tip"); 								// 메인 TIP 리스트
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> noticeList = commonService.getNoticeList();											// 공지사항 리스트
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("snsList", snsList);
		model.addAttribute("setList", setList);
		model.addAttribute("tipList", tipList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/bifesta/bifestaMain";
	}
	
	/**
	 * @Method : lucidoLMain
	 * @Date		: 2018. 2. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	루시도엘 메인 페이지
	 */
	@RequestMapping(value = { "/lucidol/main", "/lucidol" })
	public String lucidolMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{
		List<SqlMap> mainBannerList = commonService.getMainBannerList("LD_BANNER");		// 루시도엘 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("LD", "sale");					// 메인 상품 리스트 (SALE)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("LD", "html"); 									// 메인 HTML
		List<SqlMap> reviewList = commonService.getMainReviewList("LD", "review"); 			// 메인 후기 리스트
		List<SqlMap> setList = commonService.getMainProductList("LD", "set");						// 메인 상품 리스트 (SET)
		List<SqlMap> tipList = commonService.getMainTipList("LD", "tip"); 								// 메인 TIP 리스트
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> noticeList = commonService.getNoticeList();											// 공지사항 리스트
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		List<SqlMap> snsList = commonService.getMainSnsList("LD");									// 메인 SNS 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("snsList", snsList);
		model.addAttribute("setList", setList);
		model.addAttribute("tipList", tipList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/lucidol/lucidolMain";
	}
	
	/**
	 * @Method : mamabutterMain
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형
	 * @Description	:	마마버터 메인 페이지
	 */
/*
	@RequestMapping(value = { "/mamabutter/main", "/mamabutter" })
	public String mamabutterMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{
		List<SqlMap> mainBannerList = commonService.getMainBannerList("MM_BANNER");	// 마마버터 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("MM", "sale");					// 메인 상품 리스트 (SALE)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("MM", "html"); 								// 메인 HTML
		List<SqlMap> reviewList = commonService.getMainReviewList("MM", "review"); 			// 메인 후기 리스트
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> noticeList = commonService.getNoticeList();											// 공지사항 리스트
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/mama/mamabutterMain";
	}
*/
	/**
	 * @Method : dentalproMain
	 * @Date		: 2018. 8. 24.
	 * @Author	:  임  재  형
	 * @Description	:	덴탈프로 메인 페이지
	 */
	@RequestMapping(value = { "/dentalpro/main", "/dentalpro" })
	public String dentalproMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{
		List<SqlMap> mainBannerList = commonService.getMainBannerList("DP_BANNER");	// 덴탈프로 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("DP", "sale");					// 메인 상품 리스트 (VIEW ALL)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("DP", "html"); 									// 메인 HTML
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> noticeList = commonService.getNoticeList();											// 공지사항 리스트
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("bestList", bestList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/dental/dentalproMain";
	}
	
	/**
	 * @Method : charleyMain
	 * @Date		: 2018. 8. 24.
	 * @Author	:  임  재  형
	 * @Description	:	찰리 메인 페이지
	 */
/*
	@RequestMapping(value = { "/charley/main", "/charley" })
	public String charleyMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{
		List<SqlMap> mainBannerList = commonService.getMainBannerList("CL_BANNER");	// 덴탈프로 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("CL", "sale");					// 메인 상품 리스트 (VIEW ALL)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("CL", "html"); 									// 메인 HTML
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> noticeList = commonService.getNoticeList();											// 공지사항 리스트
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("bestList", bestList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/charley/charleyMain";
	}
*/
	/**
	    * @Method : bestListMoreAjax
	    * @Date: 2017. 8. 23.
	    * @Author : 임  재  형
	    * @Description	:	베스트 리스트 더보기
	   */
	@RequestMapping("/ajax/main/bestListMoreAjax") 
	public ModelAndView bestListMoreAjax(SearchVO schVO, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		schVO.setPageBlock(3);
		
		List<SqlMap> bestList = commonService.getMainGoodsList("best", Integer.parseInt(schVO.getSchType()), schVO);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("bestList", bestList);
		mav.setViewName(PathUtil.getCtx()+"/bestListMore");		
		return mav;
	}
	
	   /**
	    * @Method : topSearchAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강병철
	    * @Description	:	top검색
	   */
	@RequestMapping(value = "/ajax/common/topSearchAjax") 
	public  ModelAndView topSearchAjax(String query, HttpServletRequest request, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<SqlMap> productResultList =  commonService.autoCompletProductResult(query);
		List<SqlMap> hashtagResultList =  commonService.autoCompletHashtagResult(query);
		mav.addObject("productResultList", productResultList);
		mav.addObject("hashtagResultList", hashtagResultList);
		
		mav.setViewName(PathUtil.getCtx()+"/topsearchResult");		
		
		return mav;
	}
	
	   /**
	    * @Method : lastestOrderedGoodsAjax
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	WING - 최근 구매 상품 1개 (비회원용)
	   */
	@RequestMapping(value = "/ajax/common/lastestOrderedGoodsAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO lastestOrderedGoodsAjax(OrderVO vo) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		if(vo.getSessionId() == null || vo.getSessionId().isEmpty()){	// 비회원이고 세션 ID 없을때
			vo.setMemberIdx(0);
			
			resultMap.setResult(false);
			resultMap.setErrorCode("99");	// 로그인 페이지 이동
			resultMap.setMsg("세션 정보가 없습니다!");
			return resultMap;
		}else{	// 비회원이고 세션 ID 있을때
			vo.setMemberIdx(0);
	
			try{
				SqlMap goodsInfo = commonService.getLastestOrderedGoods(vo);	// 장바구니 리스트

				resultMap.setResult(true);
				resultMap.setResultMap(goodsInfo);				
			}catch(Exception e){
				e.printStackTrace();
				
				resultMap.setResult(false);
				resultMap.setMsg("최근 구매 상품 정보를 가져오던 중 에러가 발생했습니다!");
				return resultMap;
			}
		}
		
		return resultMap;
	}
	
	/**
	    * @Method : downloadFile
	    * @Date: 2017. 8. 29.
	    * @Author : 임  재  형
	    * @Description	:	파일 다운로드
	   */
	@RequestMapping(value="/common/downloadFile", method = RequestMethod.GET)
	public void  downloadFile(FileVO vo , HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		File downFile = null;
		
		SqlMap fileInfo = new SqlMap();
		
		try{
			fileInfo = commonService.getFileInfo(vo); // 파일정보 가지고 오기 	
		}catch (Exception e) {
			 e.printStackTrace();
		}
		
		String FileFolder = fileInfo.get("tableIdx").toString();
		String UploadFileNm = fileInfo.get("cscenterFile").toString();
		
		//downFile = FileUtil.downloadFile(vo.getFileFolder(), vo.getUploadFileNm());
		downFile = FileUtil.downloadFile("notice/"+FileFolder, UploadFileNm);
		
		if(!downFile.exists() || !downFile.isFile() || downFile.length() < 1){
			MethodUtil.alertMsgBack(request, response, "파일이존재하지않습니다.");
			return;
		}
		
		// fileServer의 파일들만 다운로드 할수 있게 수정 (절대경로로체크함) 
		/* D:/project/Sksiltron/workspace/SangsangManager/src/main/webapp/upload/fileServer/../../../../../pom.xml */		
		String uploadFILEPath = SpringMessage.getMessage("server.filePath");
		String downFileAbs =  downFile.getCanonicalPath().toString();
		downFileAbs = downFileAbs.replaceAll("\\\\", "/"); // 윈도경로 절대경로로 변경 
		uploadFILEPath = uploadFILEPath.replaceAll("\\\\", "/"); // 윈도경로 절대경로로 변경

		//System.out.println("uploadFILEPath : "+uploadFILEPath);
		//System.out.println("downFileAbs : "+downFileAbs);
			
		 if (!downFileAbs.startsWith(uploadFILEPath)) {
				MethodUtil.alertMsgBack(request, response, "정상적인 다운로드 파일이 아닙니다.");
				return;			
		}

		
		//String fileNm = java.net.URLEncoder.encode((String)vo.getOrgFileNm(), "UTF-8");
		String fileNm = vo.getUploadFileNm();
		
		response.setContentType("application/octet-stream"); 
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileNm+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        ServletOutputStream out = response.getOutputStream();
        
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(downFile);
            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) { try { fis.close(); } catch (Exception e2) {}}
        }
        out.flush();
	}
	
	/**
	    * @Method : getFacebookToken
	    * @Date: 2017. 8. 15.
	    * @Author : 임  재  형
	    * @Description	:	페이스북 토큰 받기
	   */
	@RequestMapping(value = "/ajax/getFacebookToken", method = RequestMethod.GET) 
	public @ResponseBody String getFacebookToken() throws Exception{
		URL url;
		InputStream is = null;
		try{
			url = new URL("https://graph.facebook.com/oauth/access_token"
					+"?client_id=225018704694032"
					+"&client_secret=498bdc924faa89ae94c4ea077326c76c"
					+"&grant_type=client_credentials");
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			is = request.getInputStream();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		StringBuffer sb = new StringBuffer();
		Scanner scan = new Scanner(is, "UTF-8");
		
		while(scan.hasNextLine()){
			sb.append(scan.nextLine());
		}
		// {"access_token":"225018704694032|gTnrHvuiGL6YXB2-X9ti5uSAmZw","token_type":"bearer"}
		//225018704694032|gTnrHvuiGL6YXB2-X9ti5uSAmZw
		String token = sb.toString();
		int st = token.indexOf(":\"");
		int ed = token.indexOf("\",\"token_type");
		token = token.substring((st+2), ed);
		
		return token;
	}
	
	/**
	 * @Method : srcPathAjax
	 * @Date		: 2018. 4. 11.
	 * @Author	:  유  준  철 
	 * @Description	:	유입 경로 저장
	 */
	@RequestMapping("/ajax/srcPathAjax") 
	public @ResponseBody void  srcPathAjax(CommonVO vo, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		vo.setSrcPathSessionId(session.getId());
		commonService.getSrcPathSave(vo);			// 유입 경로 저장
	}
	
	/**
	 * @Method : getBonoYn
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 노출 여부
	 */
	@RequestMapping(value = "/ajax/getBonoYn", method = RequestMethod.POST) 
	public @ResponseBody SqlMap getBonoYn(HttpServletRequest request) throws Exception{
		SqlMap bono = commonService.getBonoYn();

		return bono;
	}
	
	/**
	 * @Method : bonoClick
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 클릭
	 */
	@RequestMapping(value = "/ajax/bonoClick", method = RequestMethod.POST) 
	public @ResponseBody SqlMap bonoClick(HttpServletRequest request, HttpServletResponse response) throws Exception{
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
			return null;
		}
		
		// 보노보노 클릭
		SqlMap bono = commonService.bonoClick();
		
		int bonoPoint = 0;
		String reason = "";
		int result = Integer.parseInt(bono.get("result").toString());
		/*
		if(bono.get("bonoPoint") != null && result == 1){
			bonoPoint = Integer.parseInt(bono.get("bonoPoint").toString());
			
			if(bonoPoint == 500){
				reason = "POINT_REASON140";
			}else if(bonoPoint == 10000){
				reason = "POINT_REASON150";
			}
			
			commonService.bonoPointSave(reason, bonoPoint); // 보노보노 포인트 지급
		}
		*/
		return bono;
	}
	
	/**
	 * @Method : barrierMain
	 * @Date		: 2018. 2. 5.
	 * @Author	:  강 병 철
	 * @Description	:	Barrier Repair 메인 페이지
	 */
	@RequestMapping(value = { "/barrier/main", "/barrier" })
	public String barrierMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{	
		List<SqlMap> mainBannerList = commonService.getMainBannerList("BR_BANNER");	// 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("BR", "sale");					// 메인 상품 리스트 (SALE)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("BR", "html"); 									// 메인 HTML
		List<SqlMap> reviewList = commonService.getMainReviewList("BR", "review"); 			// 메인 후기 리스트
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/barrier/barrierMain";
	}
	

	/**
	 * @Method : lucidoMain
	 * @Date		: 2018. 2. 5.
	 * @Author	:  강 병 철
	 * @Description	:	Barrier Repair 메인 페이지
	 */
	@RequestMapping(value = { "/lucido/main", "/lucido" })
	public String lucidoMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{	
		List<SqlMap> mainBannerList = commonService.getMainBannerList("LU_BANNER");	// 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("LU", "sale");					// 메인 상품 리스트 (SALE)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("LU", "html"); 									// 메인 HTML
		List<SqlMap> reviewList = commonService.getMainReviewList("LU", "review"); 			// 메인 후기 리스트
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/lucido/lucidoMain";
	}
	/**
	 * @Method : gpcreateMain
	 * @Date		: 2018. 2. 5.
	 * @Author	:  강 병 철
	 * @Description	:	GP Create 메인 페이지
	 */
/*
	@RequestMapping(value = { "/gpcreate/main", "/gpcreate" })
	public String gpcreateMain(CommonVO vo, SearchVO schVO, Model model) throws Exception{	
		List<SqlMap> mainBannerList = commonService.getMainBannerList("GP_BANNER");	// 메인배너 리스트
		List<SqlMap> saleList = commonService.getMainProductList("GP", "sale");					// 메인 상품 리스트 (SALE)
		List<SqlMap> productSubImgList = productService.getProductSubImgList();					// 상품 서브이미지 리스트
		SqlMap mainHtml = commonService.getMainHtml("GP", "html"); 									// 메인 HTML
		List<SqlMap> reviewList = commonService.getMainReviewList("GP", "review"); 			// 메인 후기 리스트
		List<SqlMap> bestList = commonService.getMainProductList("GB", "best");					// 메인 상품 리스트 (BEST)
		List<SqlMap> popupList = commonService.getPopupList(); 											// 팝업 리스트
		
		model.addAttribute("mainBannerList", mainBannerList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("mainHtml", mainHtml);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("bestList", bestList);
		model.addAttribute("popupList", popupList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/gpcreate/gpcreateMain";
	}
*/
	
	/**
	 * @Method : porecare
	 * @Date		: 2020. 6. 11.
	 * @Author	:  임  재  형
	 * @Description	:	porecare 스페셜 페이지
	 */
	@RequestMapping("/porecare")
	public String porecare(Model model) throws Exception{	
		return PathUtil.getCtx()+"/porecare";
	}
}
