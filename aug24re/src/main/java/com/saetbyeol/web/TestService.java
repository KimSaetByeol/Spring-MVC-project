package com.saetbyeol.web;
/*
 * 2021-08-25 TestService
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	@Autowired
	private TestDAO testDAO;
	
	public List<TestDTO> boardList(Map<String, Object> sendMap){
		return testDAO.boardList(sendMap);
	}

	public void write(TestDTO testDTO) {
		testDAO.write(testDTO);
	}

	public TestDTO detail(int sb_no) {
		return testDAO.detail(sb_no);
	}

	public int delete(TestDTO dto) {
		return testDAO.delete(dto);
	}

	public int update(TestDTO dto) {
		return testDAO.update(dto);
	}

	public String getCategory(int sb_cate) {
		return testDAO.getCategory(sb_cate);
	}

	public List<HashMap<String, Object>> categoryList() {
		return testDAO.categoryList();
	}

	public int totalList(int sb_cate) {
		return testDAO.totalList(sb_cate);
	}
	
	public void viewCount(int sb_no) {
		testDAO.viewCount(sb_no);
	}

	public void like(Map<String, Object> sendMap) {
		int result = testDAO.likeCheck(sendMap);
		System.out.println("result : " + result);
		if(result == 0) {
			testDAO.like(sendMap);
		}
	}
}