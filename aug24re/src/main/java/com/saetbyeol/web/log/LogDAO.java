package com.saetbyeol.web.log;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LogDAO {
	@Autowired
	private SqlSession sqlSession;
	
	public void writeLog(LogDTO log) {
		sqlSession.insert("log.writeLog", log);
	}

	public List<LogDTO> logList(Map<String, Object> sendMap) {
		return sqlSession.selectList("log.logList", sendMap);
	}

	public int logTotalList(Map<String, Object> sendMap) {
		return sqlSession.selectOne("log.logTotalList", sendMap);
	}
}
