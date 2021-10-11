package com.saetbyeol.web.admin;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {
	@Autowired
	private SqlSession sqlSession;

	public List<HashMap<String, Object>> getCategory() {
		return sqlSession.selectList("admin.getCategory");
	}

	public HashMap<String, Object> getCategory(int sc_no) {
		return sqlSession.selectOne("admin.getCategory", sc_no);
	}
	
	public int categoryInsert(String categoryName) {
		return sqlSession.insert("admin.categoryInsert", categoryName);
	}

	public int categoryUpdate(HashMap<String, Object> cate) {
		return sqlSession.update("admin.categoryUpdate", cate);
	}

	public List<MemberDTO> list() {
		return sqlSession.selectList("admin.list");
	}

	public int gradeUpDown(MemberDTO dto) {
		return sqlSession.update("admin.gradeUpDown", dto);
	}

	public int userDelete(int sm_no) {
		return sqlSession.update("admin.userDelete", sm_no);
	}

	public void categoryPrivate(int sc_no) {
		sqlSession.update("admin.categoryPrivate", sc_no);
	}

}
