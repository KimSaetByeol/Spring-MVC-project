package com.saetbyeol.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.saetbyeol.util.Util;
import com.saetbyeol.web.admin.AdminService;
import com.saetbyeol.web.admin.MemberDTO;
import com.saetbyeol.web.log.LogDTO;
import com.saetbyeol.web.log.LogService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private Util util;
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ModelAndView category(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/admin/category");
		
		//scategory 내용 모두 가져와 mv에 붙이기!
		//jsp에 찍어주기
		List<HashMap<String, Object>> cate = adminService.getCategory();
		
		mv.addObject("category", cate);
		
		return mv;
	}
	
	@PostMapping("/category")
	public String categoryInsert(HttpServletRequest request) {
		String categoryName = request.getParameter("categoryName");
		System.out.println(categoryName);
		//로직..
		int result = adminService.categoryInsert(categoryName);
		
		System.out.println("저장 결과 : " + result);
		
		return "redirect:/admin/category"; //get 다시 호출
	}
	
	@GetMapping("/categoryUpdate")
	public ModelAndView categoryUpdate(HttpServletRequest request) {
		int sc_no = Integer.parseInt(request.getParameter("sc_no"));
//		System.out.println("들어온 no : " + sc_no);
		ModelAndView mv = new ModelAndView("/admin/categoryUpdate");
		
		HashMap<String, Object> cate = adminService.getCategory(sc_no);
		
//		System.out.println(cate);
		
		mv.addObject("cate", cate);
		
		return mv;
	}
	
	@PostMapping("/categoryUpdate")
	public String categoryUpdate2(HttpServletRequest request) {
		int sc_no = Integer.parseInt(request.getParameter("sc_no"));
		String category = request.getParameter("category");
		
		//DB로 보내기
		HashMap<String, Object> cate = new HashMap<String, Object>();
		cate.put("sc_no", sc_no);
		cate.put("category", category);
		int result = adminService.categoryUpdate(cate);
		
		return "redirect:/admin/category";
	}
	
	//전체 회원리스트 (단, pw는 노출x)
	// /admin/member 	member.jsp
	@GetMapping("/member")
	public ModelAndView member() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/member"); //페이지 없이 먼저 생성 후 넣어주기
		
		ArrayList<MemberDTO> list = (ArrayList<MemberDTO>)adminService.list();
		mv.addObject("list", list);
		
		return mv;
	}
	
	//회원 등급 조정 down up
	@GetMapping({"/gradeUp", "/gradeDown"})
	public String gradeDown(@RequestParam("sm_no") int sm_no,
							@RequestParam("sm_grade") int sm_grade) {
//		System.out.println("sm_no:" + sm_no);
//		System.out.println("sm_grade:" + sm_grade);
		
		MemberDTO dto = new MemberDTO();
		dto.setSm_no(sm_no);
		dto.setSm_grade(sm_grade);
		int result = adminService.gradeUpDown(dto);
		return "redirect:/admin/member";
	}
	
	@GetMapping("userDelete")
	public String userDelete(@RequestParam("sm_no") int sm_no) {
		int result = adminService.userDelete(sm_no);
		
		return "redirect:/admin/member";
	}
	
	@GetMapping("categoryPrivate")
	public String categoryPrivate(@RequestParam("sc_no") int sc_no) {
		adminService.categoryPrivate(sc_no);
		
		return "redirect:/admin/category";
	}
	
	@GetMapping("/logList")
	public ModelAndView logList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/logList");
		
		String searchName = request.getParameter("searchName");
		String search = request.getParameter("search");
		
		PaginationInfo paginationInfo = new PaginationInfo();
		int pageNo = 1;
		int listScale = 10;
		int pageScale = 10;
		
		if (request.getParameter("pageNo") != null) {
			pageNo = util.str2Int2(request.getParameter("pageNo"));
		}
		
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);

		int startPage = paginationInfo.getFirstRecordIndex();
		int lastPage = paginationInfo.getRecordCountPerPage();

		Map<String, Object> sendMap = new HashMap<String, Object>();
		sendMap.put("startPage", startPage);
		sendMap.put("lastPage", lastPage);
		
		if(searchName != null){			
			sendMap.put("searchName", searchName);
			sendMap.put("search", search);
			mv.addObject("searchName", searchName);
			mv.addObject("search", search);
		}

		List<LogDTO> list = logService.logList(sendMap);
		int totalCount = logService.logTotalList(sendMap);
		paginationInfo.setTotalRecordCount(totalCount);
		mv.addObject("paginationInfo", paginationInfo);
		mv.addObject("pageNo", pageNo);
		mv.addObject("totalCount", totalCount);
		mv.addObject("list", list);	
		return mv;
	}	
}
