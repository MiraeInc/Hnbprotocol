package com.gxenSoft.mall.event.entry.service;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gxenSoft.fileUtil.FileUtil;
import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.event.entry.dao.EntryDAO;
import com.gxenSoft.mall.event.entry.vo.CreatorVO;
import com.gxenSoft.mall.event.entry.vo.EntryPredictVO;
import com.gxenSoft.mall.event.entry.vo.EntryVO;
import com.gxenSoft.mall.event.event.vo.EventVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : EntryServiceImpl
 * PACKAGE NM : com.gxenSoft.gatsbyMall.event.entry.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 8. 22. 
 * HISTORY :
 
 *************************************
 */
@Service("entryService")
public class EntryServiceImpl implements EntryService {
	
	@Autowired
	private EntryDAO entryDAO;
	
	/**
	 * @Method : entryNoCheck
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 번호 체크
	 */
	public int entryNoCheck(EntryVO vo)throws Exception{
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("ENTRYNO", vo.getEntryNo());	
		
		try{
			cnt = entryDAO.entryNoCheck(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return cnt;
	}
	
	/**
	 * @Method : entryEventSave
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 저장
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void entryEventSave(EntryVO vo)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("ENTRYNO", vo.getEntryNo());	
		map.put("ENTRYNM", vo.getEntryNm());	
		map.put("TELNO", vo.getTelNo());
		map.put("BIRTHDATE", vo.getBirthDate());
		map.put("ZIPCD", vo.getZipCd());
		map.put("OLDADDR", vo.getOldAddr());
		map.put("OLDZIPCD", vo.getOldZipCd());	
		map.put("ADDR", vo.getAddr());	
		map.put("ADDRDETAIL", vo.getAddrDetail());	
		map.put("GENDER", vo.getGender());	
		map.put("PURCHASETYPE", vo.getPurchaseType());	
		map.put("REGIDX", "0");
		
		entryDAO.entryEventSave(map);
		
		entryDAO.entryEventUpdate(map);
	}
	
	/**
	 * @Method : predictEventSave
	 * @Date		: 2018. 1. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	토종비결 저장
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int predictEventSave(EntryPredictVO vo)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int flag = 0;
		
		map.put("CP", vo.getCp());
		map.put("PYEAR", vo.getPyear());
		map.put("PMONTH", vo.getPmonth());
		map.put("PDAY", vo.getPday());
		map.put("PHOUR", vo.getPhour());
		map.put("PMINUTE", vo.getPminute());
		map.put("PLEAP", vo.getPleap());
		map.put("PGENDER", vo.getPgender());
		map.put("DEVICE", PathUtil.getDeviceNm());
		map.put("REGIDX", UserInfo.getUserInfo().getMemberIdx());
		
		flag = entryDAO.predictEventSave(map);
		
		return flag;		
	}
	
	/**
	 * @Method : procSpPgCheckNaverEvent
	 * @Date		: 2018. 4. 18.
	 * @Author	:  서  정  길 
	 * @Description	:	네이버 타임보드 이벤트 (2018-04-23 회원 가입자 대상)용 - 구매시 유효성 체크
	 */
	@Override
	public SqlMap procSpPgCheckNaverEvent(String memberIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("MEMBERIDX", memberIdx);			// 회원 번호

			info = entryDAO.procSpPgCheckNaverEvent(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	/**
	 * @Method : day100ringSave
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	커플링 신청
	 */
	public int day100ringSave(EventVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("REGIDX", vo.getRegIdx()); 							// 작성자 일련번호
		
		try{
			int chkRing = entryDAO.day100ringCheck(map);	 // 커플링 신청 중복 체크
			if(chkRing!=0){
				flag = -1;
			}else if(chkRing==0){
				flag = entryDAO.day100ringSave(map); // 커플링 신청
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	/**
	 * @Method : getEventReplyList
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	100일 이벤트 댓글 리스트 --> 2018 리뉴얼 이벤트
	 */
	public List<SqlMap> get100dayReplyList(EventVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		if(PathUtil.getDevice().equals("w")){
			schVO.setPageBlock(30);
		}else{
			schVO.setPageBlock(10);
		}
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("EVENTIDX", vo.getEventIdx()); // 이벤트 일련번호
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = entryDAO.get100dayReplyList(map); // 100일 이벤트 댓글 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : get100dayReplyListCnt
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	100일 이벤트 댓글 리스트 총 개수 --> 2018 리뉴얼 이벤트
	 */
	public int get100dayReplyListCnt(EventVO vo, SearchVO schVO) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("EVENTIDX", vo.getEventIdx()); // 이벤트 일련번호
		
		try{
			cnt = entryDAO.get100dayReplyListCnt(map); // 100일 이벤트 댓글 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	 * @Method : renewReplySave
	 * @Date		: 2018. 9. 6.
	 * @Author	:  임  재  형 
	 * @Description	:	2018 리뉴얼 이벤트 댓글 저장
	 */
	public int renewReplySave(EventVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		// 이모지 입력 시 공백으로 치환
		Pattern emoticons = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
		Matcher emoticonsMatcher = emoticons.matcher(vo.getReplyContents());
		vo.setReplyContents(emoticonsMatcher.replaceAll(""));
		
		map.put("REGIDX", vo.getRegIdx()); 							// 작성자 일련번호
		map.put("REPLYCONTENTS", vo.getReplyContents());	// 내용
		map.put("EVENTIDX", vo.getEventIdx());						// 이벤트 일련번호
		map.put("GUBUN", vo.getGubun());							// 구분
		
		try{
			/*int chkReply = eventDAO.replyCheck(map);	 // 중복 댓글 체크
			if(chkReply!=0){
				flag = -1;
			}else if(chkReply==0){
				flag = entryDAO.renewReplySave(map); // 2018 리뉴얼 이벤트 댓글 저장
			}*/
			flag = entryDAO.renewReplySave(map); // 2018 리뉴얼 이벤트 댓글 저장
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}
	/**
	 * @Method : renewQuizCheck
	 * @Date		: 2018. 9. 6.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 정답체크
	 */
	public int renewQuizCheck(int memberIdx, String quizNo, String answerNo, int eventNo) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("EVENTNO", eventNo);		
		map.put("MEMBERIDX", memberIdx); 
		map.put("QUIZNO", quizNo);	
		map.put("ANSWERNO", answerNo);		
		
		
		try{		
			cnt = entryDAO.renewQuizCheck(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	

	/**
	 * @Method : renewQuizCheck
	 * @Date		: 2018. 9. 6.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 정답체크
	 */
	public int renewQuizSave(int memberIdx, String quizNo, String answerNo, int eventNo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("EVENTNO", eventNo);		
		map.put("MEMBERIDX", memberIdx); 
		map.put("QUIZNO", quizNo);	
		map.put("ANSWERNO", answerNo);		
		
		try{		
			flag = entryDAO.renewQuizSave(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}


	/**
	 * @Method : renewQuizList
	 * @Date		: 2018. 5. 31.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 참가 목록
	 */
	public List<SqlMap> renewQuizList(int memberIdx, int eventNo)  throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("EVENTNO", eventNo);		
		map.put("MEMBERIDX", memberIdx); 
		
		try{
			list = entryDAO.renewQuizList(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : creatorSave
	 * @Date		: 2019. 6. 17.
	 * @Author	:  임  재  형 
	 * @Description	:	크리에이터 제출	
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int creatorSave(CreatorVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("MEMBERNM", vo.getMemberNm());
		map.put("AGE", vo.getAge());
		map.put("PHONENO", vo.getPhoneNo());
		map.put("AREA", vo.getArea());
		map.put("INTRODUCE", vo.getIntroduce());
		map.put("MOMENT", vo.getMoment());
		map.put("SNSURL", vo.getSnsUrl());
		map.put("VIDEOURL", vo.getVideoUrl());
		map.put("MESSAGE", vo.getMessage());
		map.put("PRIVACYYN", vo.getPrivacyYn());
		
		// 동영상
		if(vo.getVideoNm() != null  && !vo.getVideoNm().isEmpty()){
			MultipartFile multipartFile = vo.getVideoNm();
			FileVO fvo = FileUtil.multiPart2File(multipartFile, "entry/"+vo.getEventIdx());	
			
			map.put("VIDEONM", fvo.getUploadFileNm());
		}
		
		flag = entryDAO.creatorSave(map);
		
		return flag;
	}

	/**
	 * @Method : getGoodsDetail
	 * @Date		: 2019. 10. 30.
	 * @Author	:  임  재  형 
	 * @Description	:	상품 정보	
	 */
	public SqlMap getGoodsDetail(String goodsCd) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("GOODSCD", goodsCd);
		
		try{
			detail = entryDAO.getGoodsDetail(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	 * @Method : entryNo2020Check
	 * @Date		: 2020.02.17
	 * @Author	:  강 병 철
	 * @Description	:	벚꽃 이벤트 응모 번호 체크
	 */
	public int entryNo2020Check(EntryVO vo)throws Exception{
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("ENTRYNO", vo.getEntryNo());	
		
		try{
			cnt = entryDAO.entryNo2020Check(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return cnt;
	}
	
	/**
	 * @Method : blossomEventSave
	 * @Date		: 2020.02.17
	 * @Author	:  강 병 철
	 * @Description	:	벚꽃이벤트 응모 저장
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void blossomEventSave(EntryVO vo)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("ENTRYNO", vo.getEntryNo());	
		map.put("ENTRYNM", vo.getEntryNm());	
		map.put("TELNO", vo.getTelNo());
		map.put("ZIPCD", vo.getZipCd());
		map.put("OLDADDR", vo.getOldAddr());
		map.put("OLDZIPCD", vo.getOldZipCd());	
		map.put("ADDR", vo.getAddr());	
		map.put("ADDRDETAIL", vo.getAddrDetail());	
		map.put("REGIDX", "0");
		
		entryDAO.entryEvent2020Save(map);
		
		entryDAO.entryNumber2020Update(map);
	}
}
