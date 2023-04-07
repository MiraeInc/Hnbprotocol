package com.gxenSoft.mall.mypage.token.dao;

import com.gxenSoft.mall.mypage.token.code.StatusCode;
import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("tokenDAO")
public class TokenDAO extends CommonDefaultDAO {

    public int getTokenListCnt(HashMap<String, Object> param) throws Exception {
        return selectListTotCnt("tokenDAO.getTokenListCnt", param);
    }

    public List<SqlMap> getTokenList(HashMap<String, Object> param) throws Exception {
        return (List<SqlMap>) selectList("tokenDAO.getTokenList", param);
    }

    public void tokenWriteOk(Integer requestPoint, Integer changeToken, String walletAddress, Integer memberIdx) {
        Map param = new HashMap();

        param.put("requestPoint", requestPoint);
        param.put("changeToken", changeToken);
        param.put("walletAddress", walletAddress);
        param.put("memberIdx", memberIdx);

        param.put("statusCode", StatusCode.REQUEST);

        insert("tokenDAO.tokenWriteOk", param);
    }
}
