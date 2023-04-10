package com.gxenSoft.util.mailUtil;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.DateUtil;
import com.gxenSoft.sqlMap.SqlMap;

/**
   *************************************
   * PROJECT   : GatsbyMall
   * PROGRAM ID  : MailUtil
   * PACKAGE NM : com.gxenSoft.util.mailUtil
   * AUTHOR	 : 김 민 수
   * CREATED DATE  : 2017. 6. 22. 
   * HISTORY :   
   *
   *************************************
 **/	
public class MailUtil {
	static String smtp = SpringMessage.getMessage("sendMail.smtp");				
	static String sendAddress = SpringMessage.getMessage("sendMail.address");			
	static String sendName = SpringMessage.getMessage("sendMail.name");
	static String siteUrl = SpringMessage.getMessage("server.domain");
	static String imgPath = SpringMessage.getMessage("server.imgDomain");
	
	
	   /**
	    * @Method : nvl
	    * @Date: 2017. 8. 27.
	    * @Author :  서 정 길
	    * @Description	:	null 대신 ""로 반환
	   */
	public static String nvl(Object str){
		if(str == null){
			return "";
		}else{
			return String.valueOf(str);
		}
	}	

	/**
	 * @Method : mailHeader
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	메일 폼 헤더
	 */
	private static StringBuffer mailHeader() throws Exception {
		StringBuffer bf = new StringBuffer();
		
		bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
		bf.append("<tr>");
		bf.append("<td colspan='3' height='34'></td>");
		bf.append("</tr>");
		bf.append("<tr>");
		bf.append("<td width='296'></td>");
		bf.append("<td>");
		bf.append("<img src='"+siteUrl+"/w/images/mail/img_email_logo.jpg' alt='GATSBY' style='border:none;vertical-align: top'>");
		bf.append("</td>");
		bf.append("<td width='297'></td>");
		bf.append("</tr>");
		bf.append("<tr>");
		bf.append("<td colspan='3' height='33'></td>");
		bf.append("</tr>");
		return bf;
	}
	
	/**
	 * @Method : mailFooter
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	메일 폼 푸터 
	 */
	private static StringBuffer mailFooter() throws Exception {
		StringBuffer bf = new StringBuffer();
		
		bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0'bgcolor='#FFFFFF'>");
		bf.append("<tr>");
		bf.append("<td>");
		bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#F7F7F7' style='border-top:1px solid #ddd;'>");
		bf.append("<tr>");
		bf.append("<td colspan='2' height='22'></td>");
		bf.append("</tr>");
		bf.append("<tr>");
		bf.append("<td width='22'></td>");
		bf.append("<td>");
		bf.append("<p style=\"margin:0;font-size:11px;color:#999;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;line-height:20px;\">");
		bf.append("* 본 메일은 발신 전용으로 회신 되지 않습니다. 문의사항은 <a href='"+siteUrl+"/w/mypage/inquiry/inquiryWrite.do' style='font-weight: bold; text-decoration: underline; color: #444;'>1:1문의</a>를 이용해 주세요.");
		bf.append("</p>");
		bf.append("<p style=\"margin:0;font-size:11px;color:#999;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;line-height:20px;\">");
		bf.append("* 주문 관련 정보, 주요 공지사항 및 이벤트 당첨 안내 등은 수신 동의 여부에 관계없이 발송됩니다.");
		bf.append("</p>");
		bf.append("</td>");
		bf.append("</tr>");
		bf.append("<tr>");
		bf.append("<td colspan='2' height='28'></td>");
		bf.append("</tr>");
		bf.append("</table>");
		bf.append("</td>");
		bf.append("</tr>");
		bf.append("<tr>");
		bf.append("<td>");
		bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
		bf.append("<tr>");
		bf.append("<td colspan='2' height='40'></td>");
		bf.append("</tr>");
		bf.append("<tr>");
		bf.append("<td style='vertical-align: top;'>");
		bf.append("<img src='"+siteUrl+"/w/images/mail/img_email_footer.jpg' alt='엠와이지비 로고' style='border:none;vertical-align: top'>");
		bf.append("</td>");
		bf.append("<td>");
		bf.append("<p style=\"height:28px;margin:0;font-size:12px;color:#999;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px\">");
		bf.append("(주)엠와이지비  대표이사 : 문준현&nbsp; &nbsp;&nbsp;사업자등록번호 : 509-88-02366&nbsp; &nbsp;&nbsp;통신판매업신고번호 : 제 2022-서울서초-0451 호");
		bf.append("</p>");
		bf.append("<p style=\"height:28px;margin:0;font-size:12px;color:#999;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px\">");
		bf.append("고객센터 02-544-1191 / 운영시간 10AM - 6AM(토/일/공휴일 휴무)");
		bf.append("</p>");
		bf.append("<p style=\"height:28px;margin:0;font-size:12px;color:#999;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px\">");
		bf.append("주소 : 서울특별시 강남구 학동로 165 (논현동, 마일스디오빌) 206호 대표전화 : 02-544-1191");
		bf.append("</p>");
		bf.append("<p style=\"height:28px;margin:0;font-size:12px;color:#999;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px\">");
		bf.append("EMAIL : mygb_club@naver.com / 이메일 주소 무단수집 거부 / 개인정보보호책임자 : 문준현");
		bf.append("</p>");
		bf.append("</td>");
		bf.append("</tr>");
		bf.append("<tr>");
		bf.append("<td colspan='2' height='30'></td>");
		bf.append("</tr>");
		bf.append("</table>");
		bf.append("</td>");
		bf.append("</tr>");
		bf.append("</table>");

		return bf;
	}
	
	/**
	 * @Method : sendMemberJoin
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 완료 
	 */
	public static void sendMemberJoin(MemberVO vo)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");   

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(vo.getEmail());		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(vo.getMemberNm(), "utf-8","B")+"<"+to+">")));	//받는 사람
			
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] 회원 가입을 환영합니다. 혜택을 확인해 보세요. ","utf-8");
			
			StringBuffer bf = new StringBuffer();
			bf.append("<!DOCTYPE html>");
			bf.append("<html class='no-js' lang='ko'>");
			bf.append("<head>");
			bf.append("<meta charset='UTF-8'>");
			bf.append("<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append("<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append("</head>");
			bf.append("<body style='margin: 0;'>");
			bf.append("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append("<tr>");
			bf.append("<td>");
			bf.append(mailHeader());	 // ========================================== 메일폼 헤더															
			bf.append("<tr>");
			bf.append("<td colspan='3'>");
			bf.append("<img src='"+siteUrl+"/w/images/mail/img_email_membership1.jpg' alt='회원이 되신 것을 진심으로 환영합니다.' style='border:none;vertical-align: top'>");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='45' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;\">");
			bf.append("<img src='"+siteUrl+"/w/images/mail/ico_email_member.jpg' style='border:none;vertical-align:middle'>");
			if(vo.getJoinType().equals("1")){
				bf.append(vo.getMemberNm()+" 고객님께서는 <span style='color:#107dff;'>"+vo.getSnsTypeNm()+"</span>(으)로 가입하셨습니다.</td>");
			}else{
				bf.append(vo.getMemberNm()+" 고객님께서 가입하신 ID는 <span style='color:#107dff;'>"+vo.getMemberId()+"</span>입니다.</td>");
			}
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:15px;\">");
			bf.append("가입일자 : "+DateUtil.getCurrentDate("yyyy-MM-dd"));
			bf.append("</td>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='50'></td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='40'></td>");
			bf.append("</tr>");
//			bf.append("<tr>");
//			bf.append("<td height='' style=\"text-align:center;vertical-align:top;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;\">");
//			bf.append("<span style=\"color:#107dff;font-weight:bold;font-size:38px;vertical-align:middle;\">1</span> &nbsp;2,000P 지급 완료!");
//			bf.append("</td>");
//			bf.append("<td height='' style=\"text-align:center;vertical-align:top;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;\">");
//			bf.append("<span style=\"color:#107dff;font-weight:bold;font-size:38px;vertical-align:middle;\">2</span> &nbsp;무료배송 쿠폰 지급 완료!");
//			bf.append("</td>");
//			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='15'></td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='45' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#107dff;font-size:19px;\">");
			bf.append("로그인 후, 마이페이지에서 확인하세요.");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='45' style='text-align:center;'>");
			bf.append("<a href='"+siteUrl+"/w/login/loginPage.do'style='display:inline-block;vertical-align:middle;'>");
			bf.append("<img src='"+siteUrl+"/w/images/mail/btn_email_login.jpg' alt='로그인' style='border:none;vertical-align: top'>");
			bf.append("</a>");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='40'></td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append(mailFooter());	// ========================================== 메일폼 푸터
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("</body>");
			bf.append("</html>");
			
			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 

			Transport.send(msg); 				//메일 발송
			
		}catch(Exception e){
			e.printStackTrace();
			
		}		
	}
	
	/**
	 * @Method : sendMemberPassword
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 찾기 
	 */
	public static void sendMemberPassword(MemberVO vo)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");   

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(vo.getEmail());		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(vo.getMemberNm(), "utf-8","B")+"<"+to+">")));	//받는 사람
			
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] "+vo.getMemberNm()+"님, 임시비밀번호 입니다. ","utf-8");
			
			StringBuffer bf = new StringBuffer();
			
			bf.append("<!DOCTYPE html>");
			bf.append("<html class='no-js' lang='ko'>");
			bf.append("<head>");
			bf.append("<meta charset='UTF-8'>");
			bf.append("<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append("<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append("</head>");
			bf.append("<body style='margin: 0;'>");
			bf.append("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append("<tr>");
			bf.append("<td>");
			bf.append(mailHeader());	 // ========================================== 메일폼 헤더		
			bf.append("</table>");
			bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='50'></td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:34px;\">");
			bf.append(vo.getMemberNm()+" 회원님의 임시 비밀번호입니다.");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='30'></td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">");
			bf.append(vo.getMemberNm()+" 회원님, 안녕하세요.<br />문의하신 임시 비밀번호를 안내해 드립니다.");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='50'></td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='30'></td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td width='305' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:34px;\">");
			bf.append("<img src='"+siteUrl+"/w/images/mail/ico_email_lock.jpg' style='border:none;vertical-align: top'>");
			bf.append("</td>");
			bf.append("<td width='440' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:34px;\">");
			bf.append("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append("<tr>");
			bf.append("<td height='' style=\"padding-left:20px;text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:15px;\">");
			bf.append(vo.getMemberNm()+" 회원님의 임시 비밀번호");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("<tr><td height='10'></td></tr>");
			bf.append("<tr>");
			bf.append("<td height='' style=\"padding-left:20px;text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#107dff;font-size:34px;font-weight:bold;\">");
			bf.append(vo.getMemberPwd());
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("<tr><td colspan='2' height='30'></td></tr>");
			bf.append("</table>");
			bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append("<tr><td colspan='2' height='40'></td></tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#107dff;font-size:19px;\">");
			bf.append("로그인 후 반드시 새로운 비밀번호로 변경하시기 바랍니다.");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("<tr><td colspan='2' height='30'></td></tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='' style='text-align:center;'>");
			bf.append("<a href='"+siteUrl+"/w/login/loginPage.do'style='display:inline-block;vertical-align:middle;'>");
			bf.append("<img src='"+siteUrl+"/w/images/mail/btn_email_login.jpg' alt='로그인' style='border:none;vertical-align: top'>");
			bf.append("</a>");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='50'></td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append(mailFooter());	// ========================================== 메일폼 푸터
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("</body>");
			bf.append("</html>");
			
			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 

			Transport.send(msg); 				//메일 발송
			
		}catch(Exception e){
			e.printStackTrace();
			
		}		
	}
	
	/**
	 * @Method : sendMemberWithdraw
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 
	 */
	public static void sendMemberWithdraw(MemberVO vo)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");   

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(vo.getEmail());		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(vo.getMemberNm(), "utf-8","B")+"<"+to+">")));	//받는 사람
			
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] 회원 탈퇴가 처리되었습니다. ","utf-8");

			StringBuffer bf = new StringBuffer();
			
			bf.append("<!DOCTYPE html>");
			bf.append("<html class='no-js' lang='ko'>");
			bf.append("<head>");
			bf.append("<meta charset='UTF-8'>");
			bf.append("<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append("<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append("</head>");
			bf.append("<body style='margin: 0;'>");
			bf.append("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append("<tr>");
			bf.append("<td>");
			bf.append(mailHeader());	 // ========================================== 메일폼 헤더		
			bf.append("<tr>");
			bf.append("<td colspan='3'>");
			bf.append("<img src='"+siteUrl+"/w/images/mail/img_email_membership2.jpg' alt='회원탈퇴가 완료되었습니다.' style='border:none;vertical-align: top'>");
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append("<tr>");
			bf.append("<td colspan='2' height='45' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">");
			bf.append("그 동안 GATSBY 서비스를 이용해 주셔서 감사합니다.<br />보다 나은 서비스로 다시 찾아 뵙겠습니다.");
			bf.append("</td>");
			bf.append("<tr><td colspan='2' height='30'></td></tr>");
			bf.append("<tr><td colspan='2' height='' style='text-align:center;'>");
			bf.append("<a href='"+siteUrl+"/w/main.do' style=\"display:inline-block;vertical-align:middle;\">");
			bf.append("<img src='"+siteUrl+"/w/images/mail/btn_email_home.jpg' alt='홈페이지 이동' style=\"border:none;vertical-align: top\">");
			bf.append("</a>");
			bf.append("</td></tr>");
			bf.append("<tr><td colspan='2' height='50'></td></tr>");
			bf.append("</table>");
			bf.append(mailFooter());	// ========================================== 메일폼 푸터
			bf.append("</td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append("</body>");
			bf.append("</html>");
			
			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 

			Transport.send(msg); 				//메일 발송
			
		}catch(Exception e){
			e.printStackTrace();
			
		}		
	}

	/**
	 * @Method : sendOrderCardCompletEmail
	 * @Date		: 2017. 8. 4.
	 * @Author	:  강병철
	 * @Description	:	주문완료 (카드결제) 
	 */
	public static void sendOrderCardCompletEmail(SqlMap orderInfo,List<SqlMap> list)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");    //25

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(nvl(orderInfo.get("senderEmail")));		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(nvl(orderInfo.get("senderNm")), "utf-8","B")+"<"+to+">")));	//받는 사람
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] 주문하신 상품의 결제가 완료되었습니다.","utf-8");
			
			StringBuffer bf = new StringBuffer();
			bf.append(" <!doctype html>");
			bf.append(" <html class='no-js' lang='ko'>");
			bf.append(" <head>");
			bf.append(" 	<meta charset='UTF-8'>");
			bf.append(" 	<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append(" 	<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append(" </head>");
			bf.append(" <body style='margin: 0;'>");
			bf.append(" <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append(" 	<tr>");
			bf.append(" 		<td>");
			bf.append(		mailHeader() );
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#444;font-size:35px;font-weight: bold;\"><span style='color:#107dff;'>주문이 완료</span>되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">"+nvl(orderInfo.get("senderNm"))+"님 주문해 주셔서 감사합니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\">주문 상품</td>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\"><p style='border-left:1px solid #cbcbcb;'>수량</p></td>");
			bf.append(" 				</tr>");
		
		for (SqlMap detail : list) {
			String imgpath ="";
			if(!nvl(detail.get("mainFile")).isEmpty()){
				String img = nvl(detail.get("mainFile"));
				String imgname = img.substring(0, img.indexOf("."));
				String imgext = img.substring(img.indexOf(".")+1, img.length());
				imgpath = siteUrl + imgPath +"/goods/"+nvl(detail.get("goodsIdx"))+"/"+imgname+"_S."+imgext;
				System.out.println(imgpath);
			}

			
			bf.append(" 				<tr>");
			bf.append(" 					<td width='570' height='125' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;\">");
			bf.append(" 						<table width='' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td width='130' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;\"><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='display:inline-block;vertical-align:middle;'><img src='"+imgpath+"' alt='' style='border:none;vertical-align: top'></a></td>");
			if("3".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #a77a29;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else if("4".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #4585ef;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else{
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #000;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 						</table>");
			bf.append(" 					</td>");
			bf.append(" 					<td width='180' height='125' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;border-left:1px solid #cbcbcb;\">" + nvl(detail.get("orderCnt")) + "</td>");
			bf.append(" 				</tr>");
		}	
			
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td width='240' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문일 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderDt"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:right;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='205' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문번호 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderCd"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:left;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='240' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">총 결제금액  &nbsp;<span style='color:#107dff;font-weight:bold;'>"+String.format("%,d", Double.valueOf(nvl(orderInfo.get("totalPayPrice"))).intValue())+"</span></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='40'></td> ");
			bf.append(" 				</tr> ");
			bf.append(" 				<tr> ");
			bf.append(" 					<td colspan='2' height='' style='text-align:center;'>");
			bf.append(" 						<a href='"+siteUrl+"/w/mypage/order/orderDetail.do?orderCd="+nvl(orderInfo.get("orderCd"))+"' style='display:inline-block;vertical-align:middle;'>");
			bf.append(" 							<img src='"+siteUrl+"/w/images/mail/btn_email_search1.jpg' alt='주문 내역 조회' style='border:none;vertical-align: top'>");
			bf.append(" 						</a>");
			bf.append(" 					</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append( 			mailFooter() );
			bf.append(" 		</td>");
			bf.append(" 	</tr>");
			bf.append(" </table>");
			bf.append(" </body>");
			bf.append(" </html>");

			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 
			Transport.send(msg); 				//메일 발송
		
		}catch(Exception e){
			e.printStackTrace();
			
		}		
	}
	
	/**
	 * @Method : sendOrderBankCompletEmail
	 * @Date		: 2017. 8. 4.
	 * @Author	:  강병철
	 * @Description	:	주문완료 (가상계좌) 
	 */
	public static void sendOrderBankCompletEmail(SqlMap orderInfo,List<SqlMap> list)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");    //25

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(nvl(orderInfo.get("senderEmail")));		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(nvl(orderInfo.get("senderNm")), "utf-8","B")+"<"+to+">")));	//받는 사람
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] 주문하신 내역을 안내 드립니다.","utf-8");
			
			StringBuffer bf = new StringBuffer();
			bf.append(" <!doctype html>");
			bf.append(" <html class='no-js' lang='ko'>");
			bf.append(" <head>");
			bf.append(" 	<meta charset='UTF-8'>");
			bf.append(" 	<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append(" 	<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append(" </head>");
			bf.append(" <body style='margin: 0;'>");
			bf.append(" <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append(" 	<tr>");
			bf.append(" 		<td>");
			bf.append(		mailHeader() );
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#444;font-size:35px;font-weight: bold;\"><span style='color:#107dff;'>주문이 완료</span>되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">"+nvl(orderInfo.get("senderNm"))+"님 주문해 주셔서 감사합니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\">주문 상품</td>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\"><p style='border-left:1px solid #cbcbcb;'>수량</p></td>");
			bf.append(" 				</tr>");
		
		for (SqlMap detail : list) {
			String imgpath = "";
			if(!nvl(detail.get("mainFile")).isEmpty()){
				String img = nvl(detail.get("mainFile"));
				String imgname = img.substring(0, img.indexOf("."));
				String imgext = img.substring(img.indexOf(".")+1, img.length());
				imgpath = siteUrl + imgPath +"/goods/"+nvl(detail.get("goodsIdx"))+"/"+imgname+"_S."+imgext;
				System.out.println(imgpath);
			}

			
			bf.append(" 				<tr>");
			bf.append(" 					<td width='570' height='125' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;\">");
			bf.append(" 						<table width='' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td width='130' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;\"><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='display:inline-block;vertical-align:middle;'><img src='"+imgpath+"' alt='' style='border:none;vertical-align: top'></a></td>");
			if("3".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #a77a29;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else if("4".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #4585ef;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else{
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #000;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 						</table>");
			bf.append(" 					</td>");
			bf.append(" 					<td width='180' height='125' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;border-left:1px solid #cbcbcb;\">" + nvl(detail.get("orderCnt")) + "</td>");
			bf.append(" 				</tr>");
		}	
			
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td width='240' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문일 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderDt"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:right;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='205' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문번호 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderCd"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:left;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='240' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">총 결제금액  &nbsp;<span style='color:#107dff;font-weight:bold;'>"+String.format("%,d", Double.valueOf(nvl(orderInfo.get("totalPayPrice"))).intValue())+"</span></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append("			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append("			<tr>");
			bf.append("				<td height='50'></td>");
			bf.append("            </tr>");
			bf.append("			<tr>");
			bf.append("				<td height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#222;font-size:20px;font-weight:bold;padding-left:25px;\">입금 정보</td>");
			bf.append("			</tr>");
			bf.append("			<tr>");
			bf.append("				<td height='20'></td>");
			bf.append("		</tr>");
			bf.append("		</table>");
			bf.append("		<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append("<tr>");
			bf.append("<td colspan='5' height='30'></td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td width='275' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">입금은행 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("bankNm"))+" ("+nvl(orderInfo.get("account"))+")</span></td>");
			bf.append("<td width='30' style='text-align:right;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append("<td width='170' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">입금 예금주 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("depositer"))+"</span></td>");
			bf.append("<td width='30' style='text-align:left;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append("<td width='275' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">입금 기한 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("depositDt"))+"일까지</span></td>");
			bf.append("</tr>");
			bf.append("<tr>");
			bf.append("<td colspan='5' height='30'></td>");
			bf.append("</tr>");
			bf.append("</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='40'></td> ");
			bf.append(" 				</tr> ");
			bf.append(" 				<tr> ");
			bf.append(" 					<td colspan='2' height='' style='text-align:center;'>");
			bf.append(" 						<a href='"+siteUrl+"/w/mypage/order/orderDetail.do?orderCd="+nvl(orderInfo.get("orderCd"))+"' style='display:inline-block;vertical-align:middle;'>");
			bf.append(" 							<img src='"+siteUrl+"/w/images/mail/btn_email_search1.jpg' alt='주문 내역 조회' style='border:none;vertical-align: top'>");
			bf.append(" 						</a>");
			bf.append(" 					</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append( 			mailFooter() );
			bf.append(" 		</td>");
			bf.append(" 	</tr>");
			bf.append(" </table>");
			bf.append(" </body>");
			bf.append(" </html>");

			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 
			Transport.send(msg); 				//메일 발송
		
		}catch(Exception e){
			e.printStackTrace();
			
		}		
	}

	   /**
	    * @Method : sendOrderBankDepositCompletEmail
	    * @Date: 2017. 8. 31.
	    * @Author :  서 정 길
	    * @Description	:	가산계좌 입금 완료
	   */
	public static void sendOrderBankDepositCompletEmail(SqlMap orderInfo,List<SqlMap> list)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");    //25

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(nvl(orderInfo.get("senderEmail")));		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(nvl(orderInfo.get("senderNm")), "utf-8","B")+"<"+to+">")));	//받는 사람
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] 입금 확인이 완료되었습니다.","utf-8");
			
			StringBuffer bf = new StringBuffer();
			bf.append(" <!doctype html>");
			bf.append(" <html class='no-js' lang='ko'>");
			bf.append(" <head>");
			bf.append(" 	<meta charset='UTF-8'>");
			bf.append(" 	<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append(" 	<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append(" </head>");
			bf.append(" <body style='margin: 0;'>");
			bf.append(" <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append(" 	<tr>");
			bf.append(" 		<td>");
			bf.append(		mailHeader() );
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#444;font-size:35px;font-weight: bold;\"><span style='color:#107dff;'>입금 확인이 완료</span>되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">"+nvl(orderInfo.get("senderNm"))+"님 주문해 주셔서 감사합니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\">주문 상품</td>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\"><p style='border-left:1px solid #cbcbcb;'>수량</p></td>");
			bf.append(" 				</tr>");
		
		for (SqlMap detail : list) {
			String imgpath = "";
			if(!nvl(detail.get("mainFile")).isEmpty()){
				String img = nvl(detail.get("mainFile"));
				String imgname = img.substring(0, img.indexOf("."));
				String imgext = img.substring(img.indexOf(".")+1, img.length());
				imgpath = siteUrl + imgPath +"/goods/"+nvl(detail.get("goodsIdx"))+"/"+imgname+"_S."+imgext;
				System.out.println(imgpath);
			}

			
			bf.append(" 				<tr>");
			bf.append(" 					<td width='570' height='125' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;\">");
			bf.append(" 						<table width='' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td width='130' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;\"><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='display:inline-block;vertical-align:middle;'><img src='"+imgpath+"' alt='' style='border:none;vertical-align: top'></a></td>");
			if("3".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #a77a29;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else if("4".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #4585ef;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else{
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #000;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 						</table>");
			bf.append(" 					</td>");
			bf.append(" 					<td width='180' height='125' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;border-left:1px solid #cbcbcb;\">" + nvl(detail.get("orderCnt")) + "</td>");
			bf.append(" 				</tr>");
		}	
			
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td width='240' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문일 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderDt"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:right;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='205' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문번호 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderCd"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:left;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='240' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">총 결제금액  &nbsp;<span style='color:#107dff;font-weight:bold;'>"+String.format("%,d", Double.valueOf(nvl(orderInfo.get("totalPayPrice"))).intValue())+"</span></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='40'></td> ");
			bf.append(" 				</tr> ");
			bf.append(" 				<tr> ");
			bf.append(" 					<td colspan='2' height='' style='text-align:center;'>");
			bf.append(" 						<a href='"+siteUrl+"/w/mypage/order/orderDetail.do?orderCd="+nvl(orderInfo.get("orderCd"))+"' style='display:inline-block;vertical-align:middle;'>");
			bf.append(" 							<img src='"+siteUrl+"/w/images/mail/btn_email_search1.jpg' alt='주문 내역 조회' style='border:none;vertical-align: top'>");
			bf.append(" 						</a>");
			bf.append(" 					</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append( 			mailFooter() );
			bf.append(" 		</td>");
			bf.append(" 	</tr>");
			bf.append(" </table>");
			bf.append(" </body>");
			bf.append(" </html>");

			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 
			Transport.send(msg); 				//메일 발송
		
		}catch(Exception e){
			e.printStackTrace();
			
		}		
	}

	/**
	 * @Method : sendOrderCancelEmail
	 * @Date		: 2017. 8. 4.
	 * @Author	:  강병철
	 * @Description	:	주문취소메일
	 */
	public static void sendOrderCancelEmail(SqlMap orderInfo,List<SqlMap> list)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");    //25

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(nvl(orderInfo.get("senderEmail")));		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(nvl(orderInfo.get("senderNm")), "utf-8","B")+"<"+to+">")));	//받는 사람
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] "+nvl(orderInfo.get("senderNm"))+"님, 주문이 정상적으로 취소되었습니다.","utf-8");
			
			StringBuffer bf = new StringBuffer();
			bf.append(" <!doctype html>");
			bf.append(" <html class='no-js' lang='ko'>");
			bf.append(" <head>");
			bf.append(" 	<meta charset='UTF-8'>");
			bf.append(" 	<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append(" 	<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append(" </head>");
			bf.append(" <body style='margin: 0;'>");
			bf.append(" <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append(" 	<tr>");
			bf.append(" 		<td>");
			bf.append(		mailHeader() );
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#444;font-size:35px;font-weight: bold;\"><span style='color:#107dff;'>주문이 취소</span>되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">"+nvl(orderInfo.get("senderNm"))+"님, 주문하신 상품이 취소되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\">취소 상품</td>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\"><p style='border-left:1px solid #cbcbcb;'>수량</p></td>");
			bf.append(" 				</tr>");
		
		for (SqlMap detail : list) {
			String imgpath ="";
			if(!nvl(detail.get("mainFile")).isEmpty()){
				String img = nvl(detail.get("mainFile"));
				String imgname = img.substring(0, img.indexOf("."));
				String imgext = img.substring(img.indexOf(".")+1, img.length());
				imgpath = siteUrl + imgPath +"/goods/"+nvl(detail.get("goodsIdx"))+"/"+imgname+"_S."+imgext;
				System.out.println(imgpath);
			}

			
			bf.append(" 				<tr>");
			bf.append(" 					<td width='570' height='125' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;\">");
			bf.append(" 						<table width='' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td width='130' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;\"><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='display:inline-block;vertical-align:middle;'><img src='"+imgpath+"' alt='' style='border:none;vertical-align: top'></a></td>");
			if("3".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #a77a29;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else if("4".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #4585ef;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else{
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #000;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 						</table>");
			bf.append(" 					</td>");
			bf.append(" 					<td width='180' height='125' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;border-left:1px solid #cbcbcb;\">" + nvl(detail.get("orderCnt")) + "</td>");
			bf.append(" 				</tr>");
		}	
			
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td width='240' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문일 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderDt"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:right;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='205' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문번호 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderCd"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:left;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='240' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">총 결제금액  &nbsp;<span style='color:#107dff;font-weight:bold;'>"+String.format("%,d", Double.valueOf(nvl(orderInfo.get("totalPayPrice"))).intValue())+"</span></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='40'></td> ");
			bf.append(" 				</tr> ");
			bf.append(" 				<tr> ");
			bf.append(" 					<td colspan='2' height='' style='text-align:center;'>");
			bf.append(" 						<a href='"+siteUrl+"/w/mypage/order/orderDetail.do?orderCd="+nvl(orderInfo.get("orderCd"))+"' style='display:inline-block;vertical-align:middle;'>");
			bf.append(" 							<img src='"+siteUrl+"/w/images/btn_email_search2.jpg' alt='취소 내역 조회' style='border:none;vertical-align: top'>");
			bf.append(" 						</a>");
			bf.append(" 					</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append( 			mailFooter() );
			bf.append(" 		</td>");
			bf.append(" 	</tr>");
			bf.append(" </table>");
			bf.append(" </body>");
			bf.append(" </html>");

			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 
			Transport.send(msg); 				//메일 발송
		
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	/**
	 * @Method : sendOrderChangeEmail
	 * @Date		: 2017. 8. 4.
	 * @Author	:  강병철
	 * @Description	:	주문교환메일
	 */
	public static void sendOrderChangeEmail(SqlMap orderInfo,List<SqlMap> list)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");    //25

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(nvl(orderInfo.get("senderEmail")));		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(nvl(orderInfo.get("senderNm")), "utf-8","B")+"<"+to+">")));	//받는 사람
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] "+nvl(orderInfo.get("senderNm"))+"님, 교환신청이 접수 되었습니다.","utf-8");
			
			StringBuffer bf = new StringBuffer();
			bf.append(" <!doctype html>");
			bf.append(" <html class='no-js' lang='ko'>");
			bf.append(" <head>");
			bf.append(" 	<meta charset='UTF-8'>");
			bf.append(" 	<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append(" 	<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append(" </head>");
			bf.append(" <body style='margin: 0;'>");
			bf.append(" <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append(" 	<tr>");
			bf.append(" 		<td>");
			bf.append(		mailHeader() );
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#444;font-size:35px;font-weight: bold;\"><span style='color:#107dff;'>교환신청이 접수</span>되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">"+nvl(orderInfo.get("senderNm"))+"님, 주문하신 상품이 교환 신청 되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\">교환 상품</td>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\"><p style='border-left:1px solid #cbcbcb;'>수량</p></td>");
			bf.append(" 				</tr>");
		
		for (SqlMap detail : list) {
			String imgpath ="";
			if(!nvl(detail.get("mainFile")).isEmpty()){
				String img = nvl(detail.get("mainFile"));
				String imgname = img.substring(0, img.indexOf("."));
				String imgext = img.substring(img.indexOf(".")+1, img.length());
				imgpath = siteUrl + imgPath +"/goods/"+nvl(detail.get("goodsIdx"))+"/"+imgname+"_S."+imgext;
				System.out.println(imgpath);
			}

			
			bf.append(" 				<tr>");
			bf.append(" 					<td width='570' height='125' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;\">");
			bf.append(" 						<table width='' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td width='130' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;\"><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='display:inline-block;vertical-align:middle;'><img src='"+imgpath+"' alt='' style='border:none;vertical-align: top'></a></td>");
			if("3".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #a77a29;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else if("4".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #4585ef;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else{
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #000;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 						</table>");
			bf.append(" 					</td>");
			bf.append(" 					<td width='180' height='125' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;border-left:1px solid #cbcbcb;\">" + nvl(detail.get("orderCnt")) + "</td>");
			bf.append(" 				</tr>");
		}	
			
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td width='240' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문일 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderDt"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:right;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='205' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문번호 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderCd"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:left;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='240' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">총 결제금액  &nbsp;<span style='color:#107dff;font-weight:bold;'>"+String.format("%,d", Double.valueOf(nvl(orderInfo.get("totalPayPrice"))).intValue())+"</span></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='40'></td> ");
			bf.append(" 				</tr> ");
			bf.append(" 				<tr> ");
			bf.append(" 					<td colspan='2' height='' style='text-align:center;'>");
			bf.append(" 						<a href='"+siteUrl+"/w/mypage/order/orderDetail.do?orderCd="+nvl(orderInfo.get("orderCd"))+"' style='display:inline-block;vertical-align:middle;'>");
			bf.append(" 							<img src='"+siteUrl+"/w/images/btn_email_search3.jpg' alt='교환 내역 조회' style='border:none;vertical-align: top'>");
			bf.append(" 						</a>");
			bf.append(" 					</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append( 			mailFooter() );
			bf.append(" 		</td>");
			bf.append(" 	</tr>");
			bf.append(" </table>");
			bf.append(" </body>");
			bf.append(" </html>");

			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 
			Transport.send(msg); 				//메일 발송
		
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	

	/**
	 * @Method : sendOrderReturnEmail
	 * @Date		: 2017. 8. 4.
	 * @Author	:  강병철
	 * @Description	:	주문교환메일
	 */
	public static void sendOrderReturnEmail(SqlMap orderInfo,List<SqlMap> list)throws Exception{
		try{
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtp); 
			props.put("mail.smtp.port","25");    //25

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);  //디버깅 콘솔창 

			MimeMessage msg = new MimeMessage(session);
			
			InternetAddress from  = new InternetAddress(sendAddress);
			msg.setFrom((new InternetAddress(MimeUtility.encodeText(sendName, "utf-8","B")+"<"+from+">")));  //보내는 사람
			
			InternetAddress to = new InternetAddress(nvl(orderInfo.get("senderEmail")));		//받는사람 메일주소
			msg.setRecipient(Message.RecipientType.TO, (new InternetAddress(MimeUtility.encodeText(nvl(orderInfo.get("senderNm")), "utf-8","B")+"<"+to+">")));	//받는 사람
			msg.setSentDate(new Date()); //보내는 일자
			msg.setSubject("[면역공방] "+nvl(orderInfo.get("senderNm"))+"님, 반품신청이 접수 되었습니다.","utf-8");
			
			StringBuffer bf = new StringBuffer();
			bf.append(" <!doctype html>");
			bf.append(" <html class='no-js' lang='ko'>");
			bf.append(" <head>");
			bf.append(" 	<meta charset='UTF-8'>");
			bf.append(" 	<meta http-equiv='x-ua-compatible' content='ie=edge'>");
			bf.append(" 	<title>"+SpringMessage.getMessage("common.title")+"</title>");
			bf.append(" </head>");
			bf.append(" <body style='margin: 0;'>");
			bf.append(" <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>");
			bf.append(" 	<tr>");
			bf.append(" 		<td>");
			bf.append(		mailHeader() );
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;color:#444;font-size:35px;font-weight: bold;\"><span style='color:#107dff;'>반품신청이 접수</span>되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:19px;line-height:24px;\">"+nvl(orderInfo.get("senderNm"))+"님, 주문하신 상품이 반품 신청 되었습니다.</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\">반품 상품</td>");
			bf.append(" 					<td height='60' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#000;font-size:14px;background:#f7f7f7;\"><p style='border-left:1px solid #cbcbcb;'>수량</p></td>");
			bf.append(" 				</tr>");
		
		for (SqlMap detail : list) {
			String imgpath ="";
			if(!nvl(detail.get("mainFile")).isEmpty()){
				String img = nvl(detail.get("mainFile"));
				String imgname = img.substring(0, img.indexOf("."));
				String imgext = img.substring(img.indexOf(".")+1, img.length());
				imgpath = siteUrl + imgPath +"/goods/"+nvl(detail.get("goodsIdx"))+"/"+imgname+"_S."+imgext;
				System.out.println(imgpath);
			}

			
			bf.append(" 				<tr>");
			bf.append(" 					<td width='570' height='125' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;\">");
			bf.append(" 						<table width='' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td width='130' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;\"><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='display:inline-block;vertical-align:middle;'><img src='"+imgpath+"' alt='' style='border:none;vertical-align: top'></a></td>");
			if("3".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #a77a29;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else if("4".equals(nvl(detail.get("brandIdx")))){
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #4585ef;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}else{
				bf.append(" <td width='440' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:12px;line-height:20px;\"><span style=\"display: inline-block; margin: 0; font-size: 12px; text-decoration: underline; color: #000;\">"+nvl(detail.get("brandNm"))+"</span><br/><a href='" + siteUrl + "/w/product/productView.do?goodsCd=" + nvl(detail.get("goodsCd")) + "' style='color:#444;display:inline-block;vertical-align:middle;text-decoration:none;'>"+nvl(detail.get("goodsNm"))+"</a></td>");
			}
			bf.append(" 							</tr>");
			bf.append(" 							<tr>");
			bf.append(" 								<td colspan='2' height='25'></td>");
			bf.append(" 							</tr>");
			bf.append(" 						</table>");
			bf.append(" 					</td>");
			bf.append(" 					<td width='180' height='125' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;border-top:1px solid #cbcbcb;border-left:1px solid #cbcbcb;\">" + nvl(detail.get("orderCnt")) + "</td>");
			bf.append(" 				</tr>");
		}	
			
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #444;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td width='240' height='' style=\"text-align:right;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문일 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderDt"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:right;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='205' height='' style=\"text-align:center;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">주문번호 &nbsp;<span style='color:#107dff;font-weight:bold;'>"+nvl(orderInfo.get("orderCd"))+"</span></td>");
			bf.append(" 					<td width='30' style='text-align:left;vertical-align:middle;'><img src='"+siteUrl+"/w/images/mail/bl_email_bar.jpg' alt='' style='border:none;vertical-align: middle'></td>");
			bf.append(" 					<td width='240' height='' style=\"text-align:left;vertical-align:middle;font-family:'NanumSquare','나눔고딕',NanumGothic,'맑은고딕',Malgun Gothic,'돋움',Dotum,Helvetica,'Apple SD Gothic Neo',Sans-serif;letter-spacing:-1px;color:#444;font-size:14px;\">총 결제금액  &nbsp;<span style='color:#107dff;font-weight:bold;'>"+String.format("%,d", Double.valueOf(nvl(orderInfo.get("totalPayPrice"))).intValue())+"</span></td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='5' height='30'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append(" 			<table width='750' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF' style='border-top:1px solid #ddd;'>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='40'></td> ");
			bf.append(" 				</tr> ");
			bf.append(" 				<tr> ");
			bf.append(" 					<td colspan='2' height='' style='text-align:center;'>");
			bf.append(" 						<a href='"+siteUrl+"/w/mypage/order/orderDetail.do?orderCd="+nvl(orderInfo.get("orderCd"))+"' style='display:inline-block;vertical-align:middle;'>");
			bf.append(" 							<img src='"+siteUrl+"/w/images/mail/btn_email_search4.jpg' alt='반품 내역 조회' style='border:none;vertical-align: top'>");
			bf.append(" 						</a>");
			bf.append(" 					</td>");
			bf.append(" 				</tr>");
			bf.append(" 				<tr>");
			bf.append(" 					<td colspan='2' height='50'></td>");
			bf.append(" 				</tr>");
			bf.append(" 			</table>");
			bf.append( 			mailFooter() );
			bf.append(" 		</td>");
			bf.append(" 	</tr>");
			bf.append(" </table>");
			bf.append(" </body>");
			bf.append(" </html>");

			msg.setContent(bf.toString(),"text/html;charset=utf-8");   					//컨텐츠영역 
			Transport.send(msg); 				//메일 발송
		
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
