package com.gxenSoft.mall.common.vo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gxenSoft.mall.member.vo.MemberVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : UserInfo
 * PACKAGE NM : com.gatsbyMall.common.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
public class UserInfo extends MemberVO {
	
	public UserInfo(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		String memberId = (String)session.getAttribute("SS_MEMBER_ID");
		
		if(memberId !=null && !memberId.isEmpty()){
			int memberIdx = ((Integer)(session.getAttribute("SS_MEMBER_IDX"))).intValue();
			
			this.setMemberIdx(memberIdx);
			this.setMemberId((String)session.getAttribute("SS_MEMBER_ID"));
			this.setMemberNm((String)session.getAttribute("SS_MEMBER_NM"));
			this.setLevelIdx(String.valueOf(session.getAttribute("SS_LEVEL_IDX")));
			this.setMemberLoginType((String)session.getAttribute("SS_MEMBER_LOGIN_TYPE"));
			this.setRegIdx(memberIdx);									
			this.setEditIdx(memberIdx);
		}
	}
	
	public static UserInfo getUserInfo()throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		
		return new UserInfo();
	}
}
