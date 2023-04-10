package com.gxenSoft.mall.mypage.token.service;

import com.gxenSoft.mall.mypage.token.vo.TokenRequest;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

import java.util.List;

public interface TokenService {

	int getTokenListCnt(SearchVO schVO) throws Exception;

	List<SqlMap> getTokenList(SearchVO schVO) throws Exception;

	void tokenWriteOk(TokenRequest tokenRequest, int totalPoint) throws Exception;

}
