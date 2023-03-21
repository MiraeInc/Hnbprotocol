package com.gxenSoft.mall.brand.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gxenSoft.mall.brand.service.BrandService;
import com.gxenSoft.mall.brand.vo.BrandVO;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : BrandController
    * PACKAGE NM : com.gxenSoft.mall.brand.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class BrandController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	@Autowired
	private BrandService brandService;


	/**
	 * @Method : brandAd
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	브랜드 광고(AD) 리스트
	 */
	@RequestMapping("/brand/brandAdList")
	public String brandAd(BrandVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		if(PathUtil.getDeviceNm().equals("P")){
			vo.setDeviceGubun("P");
		}else{
			vo.setDeviceGubun("M");
		}
		
		schProductVo.setPageBlock(5);
		
		List<SqlMap> list = brandService.getBrandAdList(vo, schProductVo);						
		int totalCount = brandService.getBrandAdListCnt(vo); 
		
		SqlMap adBrandCnt = brandService.getAdBrandCnt(vo);	// 광고 브랜드 별 개수
		
		Page page = new Page(); 
		page.pagingInfo(schProductVo, totalCount);
		
		model.addAttribute("list", list);
		model.addAttribute("adBrandCnt", adBrandCnt);
		model.addAttribute("VO", vo);
		model.addAttribute("page", page);
		model.addAttribute("tabType", "2");
		return PathUtil.getCtx()+"/brand/brandAdList";
	}
	
	/**
	 * @Method : brandAdListMoreAjax
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	브랜드 광고(AD) 리스트 더보기
	 */
	@RequestMapping("/ajax/brand/brandAdListMoreAjax") 
	public ModelAndView brandAdListMoreAjax(BrandVO vo, SchProductVO schProductVo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if(PathUtil.getDeviceNm().equals("P")){
			vo.setDeviceGubun("P");
		}else{
			vo.setDeviceGubun("M");
		}
		
		schProductVo.setPageBlock(5);
		
		List<SqlMap> list = brandService.getBrandAdList(vo, schProductVo);			
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName(PathUtil.getCtx()+"/brand/brandAdListMore");		
		return mav;
	}
	
	/**
	 * @Method : brandMagazine
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	브랜드 매거진 리스트
	 */
	@RequestMapping("/brand/brandMagazineList")
	public String brandMagazine(BrandVO vo, SchProductVO schProductVo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		if(PathUtil.getDeviceNm().equals("P")){
			vo.setDeviceGubun("P");
			schProductVo.setPageBlock(6);
		}else{
			vo.setDeviceGubun("M");
			schProductVo.setPageBlock(5);
		}
		
		List<SqlMap> list = brandService.getBrandMagazineList(vo, schProductVo);						
		int totalCount = brandService.getBrandMagazineListCnt(vo); 
		
		SqlMap magBrandCnt = brandService.getMagBrandCnt(vo);	// 매거진 브랜드 별 개수
		
		Page page = new Page(); 
		page.pagingInfo(schProductVo, totalCount);
		
		model.addAttribute("list", list);
		model.addAttribute("magBrandCnt", magBrandCnt);
		model.addAttribute("VO", vo);
		model.addAttribute("page", page);
		model.addAttribute("tabType", "3");
		
		return PathUtil.getCtx()+"/brand/brandMagazineList";
	}
	
	/**
	 * @Method : brandMagazineListMoreAjax
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	브랜드 매거진 리스트 더보기
	 */
	@RequestMapping("/ajax/brand/brandMagazineListMoreAjax") 
	public ModelAndView brandMagazineListMoreAjax(BrandVO vo, SchProductVO schProductVo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if(PathUtil.getDeviceNm().equals("P")){
			vo.setDeviceGubun("P");
		}else{
			vo.setDeviceGubun("M");
		}
		
		schProductVo.setPageBlock(6);
		List<SqlMap> list = brandService.getBrandMagazineList(vo, schProductVo);			
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName(PathUtil.getCtx()+"/brand/brandMagazineListMore");		
		return mav;
	}
	
	/**
	 * @Method : brandMagazineView
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	브랜드 매거진 상세
	 */
	@RequestMapping("/brand/brandMagazineView")
	public String brandMagazineView(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		SqlMap detail = brandService.getBrandMagazineDetail(vo);
		
		String magazineIdx = detail.get("magazineIdx").toString();	
		
		SqlMap detailPrev = brandService.getBrandMagazinePrev(magazineIdx);
		SqlMap detailNext = brandService.getBrandMagazineNext(magazineIdx);

		model.addAttribute("detail" , detail);
		model.addAttribute("detailPrev" , detailPrev);
		model.addAttribute("detailNext" , detailNext);
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "3");
		return PathUtil.getCtx()+"/brand/brandMagazineView";
	}

	@RequestMapping("/brand/brandAboutMandom")
	public String brandAboutMandom(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "1");
		
		return PathUtil.getCtx()+"/brand/brandAboutMandom";
	}
	
	/**
	    * @Method : brandGatsby
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	브랜드 갸스비
	   */
	@RequestMapping("/brand/brandGatsby")
	public String brandGatsby(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandGatsby";
	}
	
	/**
	    * @Method : brandBifesta
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	BIFESTA
	   */
	@RequestMapping("/brand/brandBifesta")
	public String brandBifesta(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandBifesta";
	}
	
	/**
	    * @Method : brandLucidoL
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	LUCIDO-L
	   */
	@RequestMapping("/brand/brandLucidoL")
	public String brandLucidoL(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandLucidoL";
	}
	
	/**
	    * @Method : brandMama
	    * @Date: 2018. 8. 24.
	    * @Author : 임  재  형
	    * @Description	:	MAMA BUTTER
	   */
	@RequestMapping("/brand/brandMama")
	public String brandMama(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandMama";
	}
	
	/**
	    * @Method : brandDental
	    * @Date: 2018. 8. 24.
	    * @Author : 임  재  형
	    * @Description	:	DentalPro
	   */
	@RequestMapping("/brand/brandDental")
	public String brandDental(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandDental";
	}
	
	/**
	    * @Method : brandCharley
	    * @Date: 2018. 8. 24.
	    * @Author : 임  재  형
	    * @Description	:	Charley
	   */
	@RequestMapping("/brand/brandCharley")
	public String brandCharley(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandCharley";
	}
	
	/**
	    * @Method : brandBarrier
	    * @Date: 2018. 11. 15
	    * @Author : 강 병 철
	    * @Description	:	
	   */
	@RequestMapping("/brand/brandBarrier")
	public String brandBarrier(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandBarrier";
	}
	
	/**
	    * @Method : brandGpcreate
	    * @Date: 2018. 11. 15
	    * @Author : 강 병 철
	    * @Description	:	
	   */
	@RequestMapping("/brand/brandGpcreate")
	public String brandGpcreate(BrandVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/brand/brandGpcreate";
	}
}
