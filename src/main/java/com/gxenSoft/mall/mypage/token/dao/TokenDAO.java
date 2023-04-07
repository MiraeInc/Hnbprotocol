package com.gxenSoft.mall.mypage.token.dao;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("tokenDAO")
public class TokenDAO extends CommonDefaultDAO{

	public int getTokenListCnt(HashMap<String, Object> param) throws Exception {
		return selectListTotCnt("tokenDAO.getTokenListCnt", param);
	}

	public List<SqlMap> getTokenList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("tokenDAO.getTokenList", param);
	}

}
