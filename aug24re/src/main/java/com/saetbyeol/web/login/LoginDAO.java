package com.saetbyeol.web.login;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAO {
	@Autowired
	private SqlSession sqlSession;

	public LoginDTO login(LoginDTO dto) {
		return sqlSession.selectOne("login.login", dto);
	}

	public String checkID(String id) {
		return sqlSession.selectOne("login.checkID", id);
	}
	
	public int join(Map<String, Object> login) {
		return sqlSession.insert("login.join", login);
	}
}
