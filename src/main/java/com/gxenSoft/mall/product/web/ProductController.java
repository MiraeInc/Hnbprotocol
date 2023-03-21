package com.gxenSoft.mall.product.web;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gxenSoft.mall.cart.service.CartService;
import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.etc.service.EtcService;
import com.gxenSoft.mall.order.service.OrderService;
import com.gxenSoft.mall.order.vo.NpayProductVO;
import com.gxenSoft.mall.order.vo.NpayShippingPolicyVO;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.mall.product.vo.NpayFeeReqVO;
import com.gxenSoft.mall.product.vo.NpayFeeResVO;
import com.gxenSoft.mall.product.vo.NpayFeeVO;
import com.gxenSoft.mall.product.vo.NpayResVO;
import com.gxenSoft.mall.product.vo.ProductVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ProductController
 * PACKAGE NM : com.gxenSoft.mall.product.web
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 29. 
 * HISTORY :
 
 *************************************
 */
@Controller
public class ProductController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private EtcService etcService;
	
	/**
	 * @Method : productList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 리스트
	 */
	@RequestMapping("/product/productList")
	public String productList(ProductVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		if(PathUtil.getDevice().equals("m")){
			schProductVo.setPageBlock(20);
		}
		
		int totalCount = 0;
		List<SqlMap> list = null;
		if (schProductVo.getSchGbn().equals("T")) {  //통합카테고리
			totalCount = productService.getTotalProductListCnt(vo, schProductVo);
			list = productService.getTotalProductList(vo, schProductVo);
		} else {
			totalCount = productService.getProductListCnt(vo, schProductVo);
			list = productService.getProductList(vo, schProductVo);
		}
		
	/*	if(vo.getSetFlag().equals("Y")){
			List<SqlMap> getCate1DepthSetList = getCate1DepthSetList();
			List<SqlMap> getCate2DepthSetList = getCate2DepthSetList();
			model.addAttribute("getCate1DepthSetList", getCate1DepthSetList);
			model.addAttribute("getCate2DepthSetList", getCate2DepthSetList);
		}*/

		SqlMap productNavi = null;
		String folderNm = "";
		if (schProductVo.getSchGbn().equals("T")) {  //통합카테고리
			productNavi = productService.getTotalCategoryNavi(vo, schProductVo);
			folderNm = "totalcate"; //이미지폴더명
		}
		else {
			productNavi = productService.getCategoryNavi(vo, schProductVo);
			folderNm = "brandcate";
		}

		String gnbBrand = "gatsby";
		String mFileImgPath = ""; //모바일이미지
		String pcFileImgPath = ""; // pc이미지
		int brandidx = 1;
		
		if (productNavi != null ) {
			gnbBrand = productNavi.get("layoutType").toString();
			brandidx = Integer.parseInt(productNavi.get("brandIdx").toString());
			
			if (productNavi.get("mFileNm") != null) {
				mFileImgPath = folderNm+"/"+productNavi.get("firstIdx").toString()+ "/"+productNavi.get("mFileNm").toString();	
			}
			if (productNavi.get("fileNm") != null) {
				pcFileImgPath = folderNm+"/"+productNavi.get("firstIdx").toString()+ "/"+productNavi.get("fileNm").toString();	
			}
		}
		
		List<SqlMap> cate1DepthList = null;
		List<SqlMap> cate2DepthList = null;
		List<SqlMap> cate3DepthList = null;
		List<SqlMap> cateMenu3DepthList = null;  //하단 메뉴의 모든 sub메뉴
		if (schProductVo.getSchGbn().equals("T")) {  //통합카테고리
			cate1DepthList = commonService.getTotalCate1DepthList();
			cate2DepthList = commonService.getTotalCate2DepthList(schProductVo);
			cate3DepthList = commonService.getTotalCate3DepthList(schProductVo);
			cateMenu3DepthList =  commonService.getTotalCategory3DepthList();
		} else {
			cate1DepthList = getCate1DepthList(brandidx);
			cate2DepthList = getCate2DepthList(schProductVo);
			cate3DepthList = getCate3DepthList(schProductVo);
			cateMenu3DepthList =  commonService.getCategory3DepthList();
		}
		
		List<SqlMap> productSubImgList = productService.getProductSubImgList();
		
		Page page = new Page(); 
		page.pagingInfo(schProductVo, totalCount);
		
		model.addAttribute("list", list);
		model.addAttribute("VO", vo);
		model.addAttribute("SCHVO", schProductVo);
		model.addAttribute("productNavi", productNavi);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("cate1DepthList", cate1DepthList);
		model.addAttribute("cate2DepthList", cate2DepthList);
		model.addAttribute("cate3DepthList", cate3DepthList);
		model.addAttribute("cateMenu3DepthList", cateMenu3DepthList);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("page", page);
		

		model.addAttribute("gnbBrand",gnbBrand);
		model.addAttribute("mFileImgPath",mFileImgPath);
		model.addAttribute("pcFileImgPath",pcFileImgPath);
		
		
		
		return PathUtil.getCtx()+"/product/productList";
	}
	
	/**
	 * @Method : productView
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 상세
	 */
	@RequestMapping("/product/productView")
	public String productView(ProductVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		SqlMap detail = productService.getProductDetail(vo, schProductVo);														// 상품 상세
		if(detail == null || detail.isEmpty()){
			MethodUtil.alertMsgUrl(request, response, "상품 정보가 없습니다.", "/main.do");
			return null;
		}
		
		SqlMap productNavi = productService.getCategoryNavi(vo, schProductVo);

		//TODO : 아래 내용 재정의할것. 
		String gnbBrand = "gatsby";
		int brandidx = 0;
		if(detail.get("brandIdx") == null) {
		    gnbBrand ="gatsby";
		    detail.put("brandIdx", 0);
		} else {
		    brandidx = Integer.parseInt(detail.get("brandIdx").toString()); //상품의 대표 브랜드
		if (brandidx == 3) {
			gnbBrand ="bifesta";
		} else if (brandidx ==4) {
			gnbBrand ="lucidol";
		}  else if (brandidx ==6) {
			gnbBrand ="mamabutter";
		} else if (brandidx ==7) {
			gnbBrand ="dentalpro";
		} else if (brandidx ==8) {
			gnbBrand ="charley";
		} else if (brandidx == 9) {
			gnbBrand ="barrier";
		} else if (brandidx ==10) {
			gnbBrand ="gpcreate";
		} else if (brandidx == 11) {
			gnbBrand ="locido";
		}
		else {
			gnbBrand ="gatsby";
		}
		}
	   
		
		vo.setGoodsIdx(detail.get("goodsIdx").toString());
		vo.setBrandIdx(detail.get("brandIdx").toString());
		
		schProductVo.setSchCateIdx(detail.get("cateIdx1").toString());
		
		List<SqlMap> productHashList = productService.getProductHashList(vo, schProductVo);						// 해시태그 리스트
		List<SqlMap> optionProductList = productService.getOptionProductList(vo, schProductVo);					// 옵션 상품 리스트
		List<SqlMap> productImgList = productService.getProductImgList(vo, schProductVo);							// 상품 이미지 리스트
		SqlMap productSubInfo = productService.getProductSubInfo(vo);																// 스타일링 무비, 매거진 건수, HOW TO USE, 리뷰 건수 , 리뷰 평균
		
		List<SqlMap> productExhibitionList = productService.getProductExhibitionList(vo, schProductVo);		// 상품 기획전 리스트
		List<SqlMap> hairStyleList = getCodeList("HAIR_STYLE");																			// 머리 길이 리스트
		SqlMap styleInfo = productService.getStyleInfo(vo, schProductVo);															// 스타일링 이미지, 비디오URL
		
		// best, wish, recommend, set 
		List<SqlMap> categoryBestProduct = productService.getCategoryBestProduct(vo, schProductVo);			// best
		SqlMap wishProduct = productService.getWishProduct(vo, schProductVo);												// wish
		
		List<SqlMap> recommendProduct = productService.getRecommendProduct(vo);									// recommend
		SqlMap setProduct = productService.getSetProduct(vo, schProductVo);													// set
		
		SqlMap productTab3 = commonService.getHtmlinfo("HTMLINFO40");													// HTML 정보관리 (배송/교환/반품) 
		SqlMap productTop = commonService.getHtmlinfo("HTMLINFO50");														// HTML 정보관리 (상품정보TOP)
		SqlMap productBottom = commonService.getHtmlinfo("HTMLINFO60");												// HTML 정보관리 (상품정보BOTTOM)
		SqlMap cardPlan = commonService.getHtmlinfo("HTMLINFO10");															// HTML 정보관리 (무이자 할부 안내)
		SqlMap cardBenefit = commonService.getHtmlinfo("HTMLINFO20");														// HTML 정보관리 (카드할인혜택)
		SqlMap pointInfo = commonService.getHtmlinfo("HTMLINFO30");															// HTML 정보관리 (포인트 적립 안내)
		SqlMap addBenefit = commonService.getHtmlinfo("HTMLINFO70");														// HTML 정보관리 (추가혜택)
		SqlMap befestaTop = commonService.getHtmlinfo("HTMLINFO80");														// HTML 정보관리 (비페스타 상품정보TOP)
		SqlMap befestaBottom = commonService.getHtmlinfo("HTMLINFO100");												// HTML 정보관리 (비페스타 상품정보BOTTOM)
		SqlMap lucidolTop = commonService.getHtmlinfo("HTMLINFO90");														// HTML 정보관리 (루시도엘TOP)
		SqlMap lucidolBottom = commonService.getHtmlinfo("HTMLINFO110");												// HTML 정보관리 (루시도엘BOTTOM)
		SqlMap mamaTop = commonService.getHtmlinfo("HTMLINFO120");														// HTML 정보관리 (마마버터TOP)
		SqlMap mamaBottom = commonService.getHtmlinfo("HTMLINFO130");												// HTML 정보관리 (마마버터BOTTOM)
		SqlMap dentalTop = commonService.getHtmlinfo("HTMLINFO140");														// HTML 정보관리 (덴탈프로TOP)
		SqlMap dentalBottom = commonService.getHtmlinfo("HTMLINFO150");												// HTML 정보관리 (덴탈프로BOTTOM)
		SqlMap charleyTop = commonService.getHtmlinfo("HTMLINFO160");													// HTML 정보관리 (찰리TOP)
		SqlMap charleyBottom = commonService.getHtmlinfo("HTMLINFO170");												// HTML 정보관리 (찰리BOTTOM)
		SqlMap barrierTop = commonService.getHtmlinfo("HTMLINFO180");												
		SqlMap barrierBottom = commonService.getHtmlinfo("HTMLINFO190");											
		SqlMap gpTop = commonService.getHtmlinfo("HTMLINFO200");										
		SqlMap gpBottom = commonService.getHtmlinfo("HTMLINFO210");									
		
		List<SqlMap> blogList = productService.getBlogList();																					// 블로그 리스트
		
		SqlMap bannerInfo = commonService.getBannerOne("PRODUCT_BANNER");		// 상품상세 배너
				
		CartVO cartVo = new CartVO();
		cartVo.setGoodsIdx(detail.get("goodsIdx").toString());
		int wishTotalCnt = cartService.getWishTotalCnt(cartVo);			// wish 건수 
		
		String billkeyYn =  "";
		UserInfo userInfo = UserInfo.getUserInfo();
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			SqlMap billkeyInfo = orderService.selectMainBillkey(userInfo.getMemberIdx());			// 대표 빌키 구하기
			if(billkeyInfo == null || billkeyInfo.size() == 0){ 			//등록된 빌키가 없을경우
				billkeyYn =  "N";
			}else {
				billkeyYn = "Y";
			}
		}		
		
		schProductVo.setSchCateFlag("ALL");
		List<SqlMap> cate1DepthList = getCate1DepthList(brandidx);
		List<SqlMap> cate2DepthList = getCate2DepthList(schProductVo);
		schProductVo.setSchCateIdx(detail.get("cateIdx2").toString());
		List<SqlMap> cate3DepthList = getCate3DepthList(schProductVo);
		
	/*	List<SqlMap> getCate1DepthSetList = getCate1DepthSetList();
		List<SqlMap> getCate2DepthSetList = getCate2DepthSetList();
			
		model.addAttribute("getCate1DepthSetList", getCate1DepthSetList);
		model.addAttribute("getCate2DepthSetList", getCate2DepthSetList);
		*/
		model.addAttribute("detail", detail);
		model.addAttribute("VO", vo);
		model.addAttribute("productHashList", productHashList);
		model.addAttribute("optionProductList", optionProductList);
		model.addAttribute("productImgList", productImgList);
		model.addAttribute("productSubInfo", productSubInfo);
		model.addAttribute("productExhibitionList", productExhibitionList);
		model.addAttribute("hairStyleList", hairStyleList);
		model.addAttribute("styleInfo", styleInfo);
		model.addAttribute("wishProduct", wishProduct);
		model.addAttribute("recommendProduct", recommendProduct);
		model.addAttribute("setProduct", setProduct);
		model.addAttribute("categoryBestProduct", categoryBestProduct);
		model.addAttribute("productTab3", productTab3);
		model.addAttribute("wishTotalCnt", wishTotalCnt);
		model.addAttribute("billkeyYn", billkeyYn);
		model.addAttribute("productTop", productTop);
		model.addAttribute("productBottom", productBottom);
		model.addAttribute("befestaTop", befestaTop);
		model.addAttribute("befestaBottom", befestaBottom);
		model.addAttribute("lucidolTop", lucidolTop);
		model.addAttribute("lucidolBottom", lucidolBottom);
		model.addAttribute("mamaTop", mamaTop);
		model.addAttribute("mamaBottom", mamaBottom);
		model.addAttribute("dentalTop", dentalTop);
		model.addAttribute("dentalBottom", dentalBottom);
		model.addAttribute("charleyTop", charleyTop);
		model.addAttribute("charleyBottom", charleyBottom);
		model.addAttribute("barrierTop", barrierTop);
		model.addAttribute("barrierBottom", barrierBottom);
		model.addAttribute("gpTop", gpTop);
		model.addAttribute("gpBottom", gpBottom);
		model.addAttribute("cardPlan", cardPlan);
		model.addAttribute("cardBenefit", cardBenefit);
		model.addAttribute("pointInfo", pointInfo);
		model.addAttribute("addBenefit", addBenefit);
		model.addAttribute("blogList", blogList);
		model.addAttribute("bannerInfo", bannerInfo);
		model.addAttribute("cate1DepthList", cate1DepthList);
		model.addAttribute("cate2DepthList", cate2DepthList);
		model.addAttribute("cate3DepthList", cate3DepthList);
		model.addAttribute("productNavi", productNavi);
		
		model.addAttribute("goodsOptionList", productService.retrieveGoodsOptionList(vo));
		
		/*if(vo.getGnbBrand() != null && vo.getGnbBrand() != ""){
			model.addAttribute("gnbBrand", vo.getGnbBrand());
		}else{
			model.addAttribute("gnbBrand", "default");
			model.addAttribute("gnbBrandFlag", "Y");
		}
		*/
		
		model.addAttribute("gnbBrand",gnbBrand);
		return PathUtil.getCtx()+"/product/productView";
	}
	
	/**
	 * @Method : bestProductList
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	베스트 상품 리스트
	 */
	@RequestMapping("/product/bestProductList")
	public String bestProductList(ProductVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{

		if ((vo.getBrandIdx() == null) || (vo.getBrandIdx().equals("")) )
		{
			vo.setBrandIdx("1");
		}
		
		List<SqlMap> list = productService.getBestProductList(vo);
		List<SqlMap> productSubImgList = productService.getProductSubImgList();
		
		if(vo.getGnbBrand() == null){
			vo.setGnbBrand("default");
		}
		
		
		model.addAttribute("list", list);
		model.addAttribute("VO", vo);
		model.addAttribute("gnbBrand", vo.getGnbBrand());
		model.addAttribute("productSubImgList", productSubImgList);
		
		return PathUtil.getCtx()+"/product/bestProductList";
	}
	
	/**
	 * @Method : productStockCntAjax
	 * @Date		: 2017. 7. 6.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 재고 수량
	 */
	@RequestMapping("/ajax/product/productStockCntAjax") 
	public @ResponseBody SqlMap  productStockCntAjax(ProductVO vo, SchProductVO schProductVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SqlMap map = productService.getProductDetail(vo, schProductVo);														
		return map;
	}
	
	/**
	 * @Method : optionProductAddAjax
	 * @Date		: 2017. 7. 6.
	 * @Author	:  유  준  철 
	 * @Description	:	옵션 상품 수량 및 금액 추가
	 */
	@RequestMapping("/ajax/product/optionProductAddAjax") 
	public ModelAndView optionProductAddAjax(ProductVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		SqlMap detail = productService.getProductDetail(vo, schProductVo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("detail", detail);
		mav.setViewName(PathUtil.getCtx()+"/product/optionProductAdd");		
		
		return mav;
	}
	
	/**
	 * @Method : productLayerAjax
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 레이어 팝업
	 */
	@RequestMapping("/ajax/product/productLayerAjax") 
	public ModelAndView productLayerAjax(ProductVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		SqlMap magazineInfo = productService.getMagazineInfo(vo, schProductVo);											// 매거진 정보
		SqlMap styleInfo = productService.getStyleInfo(vo, schProductVo);															// 스타일링 이미지, 비디오URL
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("magazineInfo", magazineInfo);
		mav.addObject("styleInfo", styleInfo);
		mav.setViewName(PathUtil.getCtx()+"/product/productLayer");		
		
		return mav;
	}
	
	/**
	 * @Method : hairStylingMain
	 * @Date		: 2017. 9. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어 스타일링 메인
	 */
	@RequestMapping("/product/hairStylingMain")
	public String hairStylingMain(ProductVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		List<SqlMap> noWaxlist = productService.getHairStylingNoWaxtList();
		List<SqlMap> waxlist = productService.getHairStylingWaxtList();
		List<SqlMap> hairStyleList = getCodeList("HAIR_STYLE");																			// 머리 길이 리스트
		List<SqlMap> hashList = etcService.getMainHashtagList(); 																			//추천 해시태그 목록
		List<SqlMap> cate1DepthList = getCate1DepthList(1); //갸스비 1depth
		model.addAttribute("noWaxlist", noWaxlist);
		model.addAttribute("waxlist", waxlist);
		model.addAttribute("hairStyleList", hairStyleList);
		model.addAttribute("cate1DepthList", cate1DepthList);
		model.addAttribute("hashList", hashList);
		model.addAttribute("gnbBrand", vo.getGnbBrand());
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/product/hairStylingMain";
	}
	
	/**
	 * @Method : hairStylingAjax
	 * @Date		: 2017. 9. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어 스타일링 메인 상품 JSON 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/ajax/product/hairStylingAjax") 
	public @ResponseBody JSONObject hairStylingAjax(HttpServletRequest request, HttpServletResponse response)throws Exception{
		List<SqlMap> list = productService.getHairStylingProductList();
		
		JSONObject obj = new JSONObject();
		
		// WAX
		JSONArray mrArray = new JSONArray();				// 무빙러버 왁스
		JSONArray swArray = new JSONArray();				// 스타일링 왁스
		JSONArray phArray = new JSONArray();				// 퍼펙트 홀드 왁스
		JSONArray hjArray = new JSONArray();				// 헤어잼
		// POMADE
		JSONArray pmArray = new JSONArray();		
		// SPRAY
		JSONArray spArray = new JSONArray();				
		// GEL
		JSONArray glArray = new JSONArray();				
		// POMADE
		JSONArray foArray = new JSONArray();				
		
		for (int i = 0; i < list.size(); i++){
			
			int pos = ((String) list.get(i).get("mainFile")).lastIndexOf( "." );
			String ext = ((String) list.get(i).get("mainFile")).substring( pos + 1 );
			
			String mainFile = (String) list.get(i).get("mainFile");
			String imgFile = mainFile.substring(0, mainFile.indexOf("."));
			
			
			String img_s = SpringMessage.getMessage("server.imgDomain")+"/goods/"+list.get(i).get("goodsIdx")+"/"+imgFile+"_S."+ext;
			String img_l = SpringMessage.getMessage("server.imgDomain")+"/goods/"+list.get(i).get("goodsIdx")+"/"+imgFile+"_B."+ext;
			
			JSONObject sObject = new JSONObject();
			
			if(list.get(i).get("cateIdx2").equals("8")){
				// 왁스 JSON DATA
				if(list.get(i).get("cateIdx3").equals("12")){
					sObject.put("img_s", img_s);
					sObject.put("img_l", img_l);
					sObject.put("link_cart", list.get(i).get("goodsIdx"));
					sObject.put("item_name", list.get(i).get("goodsTitle"));
					sObject.put("item_pub", list.get(i).get("shortInfo"));
					sObject.put("item_discount", list.get(i).get("discountRate"));
					sObject.put("item_total", list.get(i).get("discountPrice"));
					sObject.put("item_stat_setting", list.get(i).get("setting"));
					sObject.put("item_stat_glossy", list.get(i).get("burnish"));
					sObject.put("item_stat_hair", list.get(i).get("hairstyle"));
					sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
					
					mrArray.add(sObject);
					
				}
				
				if(list.get(i).get("cateIdx3").equals("13")){
					sObject.put("img_s", img_s);
					sObject.put("img_l", img_l);
					sObject.put("link_cart", list.get(i).get("goodsIdx"));
					sObject.put("item_name", list.get(i).get("goodsTitle"));
					sObject.put("item_pub", list.get(i).get("shortInfo"));
					sObject.put("item_discount", list.get(i).get("discountRate"));
					sObject.put("item_total", list.get(i).get("discountPrice"));
					sObject.put("item_stat_setting", list.get(i).get("setting"));
					sObject.put("item_stat_glossy", list.get(i).get("burnish"));
					sObject.put("item_stat_hair", list.get(i).get("hairstyle"));
					sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
					
					swArray.add(sObject);
					
				}
				
				if(list.get(i).get("cateIdx3").equals("14")){
					sObject.put("img_s", img_s);
					sObject.put("img_l", img_l);
					sObject.put("link_cart", list.get(i).get("goodsIdx"));
					sObject.put("item_name", list.get(i).get("goodsTitle"));
					sObject.put("item_pub", list.get(i).get("shortInfo"));
					sObject.put("item_discount", list.get(i).get("discountRate"));
					sObject.put("item_total", list.get(i).get("discountPrice"));
					sObject.put("item_stat_setting", list.get(i).get("setting"));
					sObject.put("item_stat_glossy", list.get(i).get("burnish"));
					sObject.put("item_stat_hair", list.get(i).get("hairstyle"));
					sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
					
					hjArray.add(sObject);
					
				}
				
				if(list.get(i).get("cateIdx3").equals("18")){
					sObject.put("img_s", img_s);
					sObject.put("img_l", img_l);
					sObject.put("link_cart", list.get(i).get("goodsIdx"));
					sObject.put("item_name", list.get(i).get("goodsTitle"));
					sObject.put("item_pub", list.get(i).get("shortInfo"));
					sObject.put("item_discount", list.get(i).get("discountRate"));
					sObject.put("item_total",  list.get(i).get("discountPrice"));
					sObject.put("item_stat_setting", list.get(i).get("setting"));
					sObject.put("item_stat_glossy", list.get(i).get("burnish"));
					sObject.put("item_stat_hair", list.get(i).get("hairstyle"));
					sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
					
					phArray.add(sObject);
					
				}
			}else if(list.get(i).get("cateIdx2").equals("17")){
				sObject.put("img_s", img_s);
				sObject.put("img_l", img_l);
				sObject.put("link_cart", list.get(i).get("goodsIdx"));
				sObject.put("item_name", list.get(i).get("goodsTitle"));
				sObject.put("item_pub", list.get(i).get("shortInfo"));
				sObject.put("item_discount", list.get(i).get("discountRate"));
				sObject.put("item_total",  list.get(i).get("discountPrice"));
				sObject.put("item_stat_setting", "");
				sObject.put("item_stat_glossy", "");
				sObject.put("item_stat_hair", "");
				sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
				
				pmArray.add(sObject);
				
			}else if(list.get(i).get("cateIdx2").equals("9")){
				sObject.put("img_s", img_s);
				sObject.put("img_l", img_l);
				sObject.put("link_cart", list.get(i).get("goodsIdx"));
				sObject.put("item_name", list.get(i).get("goodsTitle"));
				sObject.put("item_pub", list.get(i).get("shortInfo"));
				sObject.put("item_discount", list.get(i).get("discountRate"));
				sObject.put("item_total",  list.get(i).get("discountPrice"));
				sObject.put("item_stat_setting", "");
				sObject.put("item_stat_glossy", "");
				sObject.put("item_stat_hair", "");
				sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
				
				spArray.add(sObject);
				
			}else if(list.get(i).get("cateIdx2").equals("11")){
				sObject.put("img_s", img_s);
				sObject.put("img_l", img_l);
				sObject.put("link_cart", list.get(i).get("goodsIdx"));
				sObject.put("item_name", list.get(i).get("goodsTitle"));
				sObject.put("item_pub", list.get(i).get("shortInfo"));
				sObject.put("item_discount", list.get(i).get("discountRate"));
				sObject.put("item_total",  list.get(i).get("discountPrice"));
				sObject.put("item_stat_setting", "");
				sObject.put("item_stat_glossy", "");
				sObject.put("item_stat_hair", "");
				sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
				
				glArray.add(sObject);
				
			}else if(list.get(i).get("cateIdx2").equals("10")){
				sObject.put("img_s", img_s);
				sObject.put("img_l", img_l);
				sObject.put("link_cart", list.get(i).get("goodsIdx"));
				sObject.put("item_name", list.get(i).get("goodsTitle"));
				sObject.put("item_pub", list.get(i).get("shortInfo"));
				sObject.put("item_discount", list.get(i).get("discountRate"));
				sObject.put("item_total",  list.get(i).get("discountPrice"));
				sObject.put("item_stat_setting", "");
				sObject.put("item_stat_glossy", "");
				sObject.put("item_stat_hair", "");
				sObject.put("link_goodsCd", SpringMessage.getMessage("server.domain")+"/w/product/productView.do?goodsCd="+list.get(i).get("goodsCd"));
				
				foArray.add(sObject);
			}
		}
		
		obj.put("mr", mrArray);					// 무빙러버 왁스
		obj.put("sw", swArray);					// 스타일링 왁스
		obj.put("ph", phArray);						// 퍼펙트 홀드 왁스
		obj.put("hj", hjArray);						// 헤어잼
		obj.put("pm", pmArray);					// 포마드
		obj.put("sp", spArray);						// 스프레이
		obj.put("gl", glArray);						// 젤
		obj.put("fo", foArray);						// 폼
		
		 return obj;
	}
	
	

	 /**
	    * @Method : getProductInfo
	    * @Date: 2018.06.18
	    * @Author :  강병철
	    * @Description	:	 Naver Pay 상품 정보 요청
	    * 
	   */
	@RequestMapping(value = "/product/getProductInfo")    
	public @ResponseBody NpayResVO getProductInfo(@RequestParam Map<String, String> product, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<Integer, String> idArray = new HashMap<Integer, String>();
		Map<Integer, String> optionArray = new HashMap<Integer, String>();
		Map<Integer, String> suppleArray = new HashMap<Integer, String>();
		String supplementSearch = "false";
		String optionSearch = "false";

		for ( Entry<String, String> m : product.entrySet())
		{
			System.out.println(m.getKey() + " ====> " + m.getValue());
			
			 String name = m.getKey().toLowerCase();

			    if (name.startsWith("product[")) {
			    	if (name.substring(name.lastIndexOf('[')+1, name.lastIndexOf(']')).toLowerCase().equals("id") ) {
			    		Integer key = Integer.parseInt( name.substring(name.indexOf('[') + 1, name.indexOf(']'))); 
				        idArray.put(key, m.getValue());
			    	}
			    	else if (name.substring(name.lastIndexOf('[')+1, name.lastIndexOf(']')).toLowerCase().equals("optionmanagecodes") ) {
			    		Integer key = Integer.parseInt( name.substring(name.indexOf('[') + 1, name.indexOf(']')));
			    		 optionArray.put(key, m.getValue());
			    	}
			    	else if (name.substring(name.lastIndexOf('[')+1, name.lastIndexOf(']')).toLowerCase().equals("supplementids") ) {
			    		Integer key = Integer.parseInt( name.substring(name.indexOf('[') + 1, name.indexOf(']')));
			    		 suppleArray.put(key, m.getValue());
			    	}
			    }
			    else if (name.equals("supplementsearch"))
			    {
			    	supplementSearch = name;
			    }
			    else if (name.equals("optionsearch"))
			    {
			    	optionSearch = name;
			    }
			
		}
		
		
		NpayResVO resultMap = new NpayResVO();
		List<NpayProductVO> products =  new ArrayList<>();
		
		String uploadIMGPath = SpringMessage.getMessage("server.imgDomain");
		String domain = SpringMessage.getMessage("server.domain");

		if (PathUtil.getCtx().toUpperCase().equals("/M")) {
			domain = SpringMessage.getMessage("server.mDomain");
		}
		
		for (int j=0;j< idArray.size();j++) {
			String goodsCd = idArray.get(j);
			SqlMap detail = null;
            detail = productService.getProductOriginDetail(goodsCd);
				
			if(detail != null && !detail.isEmpty()){
				NpayProductVO info = new NpayProductVO();
				info.setId(detail.get("goodsCd").toString());
				info.setMerchantProductId(detail.get("goodsCd").toString());
				info.setEcMallProductId(detail.get("goodsCd").toString());
				info.setName(URLEncoder.encode(detail.get("goodsNm").toString(),"UTF-8"));
				info.setBasePrice((int)Double.parseDouble(detail.get("discountPrice").toString()));
				String imageUrl = "";
				String imageUrlStr = detail.get("mainFile").toString();
				if (!imageUrlStr.isEmpty()) {
					String[] str = imageUrlStr.split("\\.");
					if (str.length > 1) {
						imageUrl = URLEncoder.encode(String.format("%s/goods/%s/%s_S.%s", uploadIMGPath,detail.get("goodsIdx").toString(),str[0], str[1]),"UTF-8");
					}
				}
				info.setImageUrl(imageUrl);
				info.setInfoUrl(URLEncoder.encode(String.format("%s%s/product/productView.do?goodsCd=%s", domain, PathUtil.getCtx(),detail.get("goodsCd").toString()),"UTF-8"));
				if (detail.get("stockFlag").toString().equals("N")) {
					//무제한
					info.setStockQuantity(null);
				} else {
					info.setStockQuantity(Integer.parseInt(detail.get("stockCnt").toString()));
				}
				
				 // (ON_SALE : 판매중, SOLD_OUT : 품절, NOT_SALE : 구매불가)
				if (detail.get("displayFlag").toString().equals("Y")) {
					if (detail.get("soldoutYn").toString().equals("Y")) {
						info.setStatus("SOLD_OUT");  //품절
					} else {
						info.setStatus("ON_SALE");  //판매중
					}					
				} else {
					info.setStatus("NOT_SALE");  //구매불가
				}
				
				//배송비 계산
				NpayShippingPolicyVO shipping = new NpayShippingPolicyVO();
				//30000원이상이면 
				if ((int)Double.parseDouble(detail.get("discountPrice").toString()) >= 30000) {
					shipping.setGroupId("000");
					shipping.setMethod("DELIVERY");
					shipping.setFeePayType("FREE"); 			
					shipping.setFeeType("FREE"); //무료
					shipping.setFeePrice(0);	
				} else {
					shipping.setGroupId("100");
					shipping.setMethod("DELIVERY");
					shipping.setFeePayType("PREPAYED"); //선불				
					shipping.setFeeType("CHARGE"); //유료
					shipping.setFeePrice(2500);
				}
				
				/*shipping.setGroupId("000");
				shipping.setMethod("DELIVERY");
				shipping.setFeePayType("FREE"); 			
				shipping.setFeeType("FREE"); //무료
				shipping.setFeePrice(0);*/	
				
				info.setShippingPolicy(shipping);
				
				products.add(info);
			}
		}

		
		resultMap.setProduct(products);
		return resultMap;
	}

	

	 /**
	    * @Method : npayAddShippingFee
	    * @Date: 2018.06.18
	    * @Author :  강병철
	    * @Description	:	 Naver Pay 상품 정보 요청
	    * 
	   */
	@RequestMapping(value = "/product/npayAddFee")    
	public @ResponseBody NpayFeeResVO npayAddFee( NpayFeeReqVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{

		NpayFeeResVO result = new NpayFeeResVO();
		List<NpayFeeVO> additionalFee = new ArrayList<>();
		for ( String item : vo.getProductId())
		{
			System.out.println(item);
			
			NpayFeeVO fee = new NpayFeeVO();
			fee.setId(item);
			fee.setSurprice(0);  //추가배송비는 없음.
			additionalFee.add(fee);
		}
		result.setAdditionalFee(additionalFee);		
		
		return result;
	}

}
