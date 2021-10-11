package com.saetbyeol.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.saetbyeol.util.FileSave;
import com.saetbyeol.util.Util;
import com.saetbyeol.web.log.LogDTO;
import com.saetbyeol.web.log.LogService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/* 2021-08-25 testController 
 * 
 * user -> RD -> controller -> Service -> DAO -> mybatis -> DB
 * 					DTO(VO)
 * 					View(jsp)
 * 
 * 데이터 통신 user => Controller -> Service -> DAO -> DB
1. pom.xml에 관련 maven을 다 입력한다.
2. src/main/resources에 spring, mybatis(mappers, config)을 생성한다
3. spring에 data-context.xml을 생성하고 sqlSession 설정 및 dataSource 객체를 생성한다.
4. mybatisConfig에 내가 만든 클래스를 mybatis가 사용할 수 있도록 등록해준다.(TestDTO)
5. testMapper에 sql문을 생성한다. id는 boardList로 resultType은 TestDTO로 만든다. 그리고 해당 sql문을 갖고 있는 mapper이름을 test라고 지정한다.
6. TestDTO를 생성한다. getter와 setter를 설정한다.
7. TestDAO를 생성한다. 실제 데이터를 불러오도록 한다. sqlSession을 이용하여 test속 boardList라고 생성된 sql문을 통해 데이터베이스와 통신한다.
8. TestService.java를 생성하고 데이터베이스와 통신한 후 받아온 값을 담아준다.
9. TestController.java에서  맵핑작업(어느 페이지에 대한 컨트롤러인지)을 해주고, TestService.java에 있는 boardList를 잡아 ModelAndView에 데이터를 리스트로 담아서 반환값으로 지정해줍니다.
10. board.jsp에서 컨트롤러에서 받은 값을 표현해줍니다.

 */

@Controller
public class TestController {
	@Autowired
	private TestService testService;
	@Autowired
	private Util util;
	@Autowired
	private LogService logService;
	@Autowired
	private FileSave fileSave;

	@RequestMapping("/menu")
	public ModelAndView menu() {
		ModelAndView mv = new ModelAndView("menu");
		List<HashMap<String, Object>> categoryList = testService.categoryList();
		mv.addObject("categoryList", categoryList);
		return mv;
	}

	// 맵핑작업
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public ModelAndView board(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("board");// jsp

		int sb_cate = 1;
		if (request.getParameter("sb_cate") != null && util.str2Int(request.getParameter("sb_cate"))) {
			sb_cate = util.str2Int2(request.getParameter("sb_cate"));
		}
		Map<String, Object> sendMap = new HashMap<String, Object>();
		sendMap.put("sb_cate", sb_cate);

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

		sendMap.put("startPage", startPage);
		sendMap.put("lastPage", lastPage);

		List<TestDTO> boardList = testService.boardList(sendMap);
		int totalList = testService.totalList(sb_cate);
		paginationInfo.setTotalRecordCount(totalList);
		mv.addObject("paginationInfo", paginationInfo);
		mv.addObject("pageNo", pageNo);
		mv.addObject("totalList", totalList);

		mv.addObject("list", boardList);
		if (boardList.size() > 0) {
			mv.addObject("category", boardList.get(0).getSc_category());
		}
		mv.addObject("sb_cate", sb_cate);

		// ip불러오기
		String ip = util.getUserIp(request);

		String target = "board?sb_cate=" + sb_cate;
		String data = "게시판 열람";
		LogDTO log = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {// 로그인 했다면
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id가 있음.
		} else {
			log = new LogDTO(ip, target, data);// sl_id가 없음.
		}
		logService.writeLog(log);

		return mv;
	}

	// @RequestMapping(value="/write", method={RequestMethod.GET,
	// RequestMethod.POST})
	// 또는 method를 안쓰면 두 방식 모두 들어온다.
	// ㄴ @RequestMapping(value="/write")
	@GetMapping("/write") // GET만 들어오게
	public String write(HttpServletRequest request) {
		// 위에서 잡은 sb_cate를 mv에 붙여서 write.jsp로 보내기
		// 그럼 form태그 안에서 붙여서 글쓰기 버튼을 눌렀을 때
		// sb_cate값 까지 가져갈 수 있다

		// 로그인 한 사람 확인
		HttpSession session = request.getSession();

		if (session.getAttribute("sm_name") != null && session.getAttribute("sm_id") != null) {
			return "write"; // 정상 로그인일 경우 글쓰기 화면
		} else {
			return "redirect:/"; // 로그인 없을 경우 인덱스화면으로
		}

	}

	@PostMapping("/write")
//	public String write2(HttpServletRequest request) {
	public String write2(HttpServletRequest request, MultipartFile file) { // btitle, bcontent 이름만 같으면 자동으로 DTO 저장
//		testDTO.setNo(1);
		// session에 있는 id값 넣어주기
		HttpSession session = request.getSession();

		if (session.getAttribute("sm_name") != null && session.getAttribute("sm_id") != null) {
			//데이터베이스로 보낼 dto 만들기
			TestDTO testDTO = new TestDTO();
			testDTO.setSb_title(request.getParameter("sb_title"));
			testDTO.setSb_content(request.getParameter("sb_content"));
			testDTO.setSb_cate(util.str2Int2(request.getParameter("sb_cate")));
			System.out.println("title: " + testDTO.getSb_title());
			System.out.println("content: " + testDTO.getSb_content());
			System.out.println("category: " + testDTO.getSb_cate());
			
			//file
			testDTO.setSb_orifile(file.getOriginalFilename());

			//실제로 파일을 저장시키기 -> 실제 저장된 파일 이름 -> 아래에
			testDTO.setSb_file("실제 저장된 파일명");
//			String realPath = servletContext.getRealPath("resource/upfile");
			
			System.out.println("file : " + file.getOriginalFilename());
			System.out.println("file size : " + file.getSize());
			
			// 정상 로그인
			testDTO.setSm_id((String) session.getAttribute("sm_id")); // id는 세션으로 올릴거기 때문에
//			testDTO.setSb_cate(testDTO.getSb_cate());

			testService.write(testDTO);
			// ip불러오기

			String ip = util.getUserIp(request);

			String target = "write";
			String data = "글쓰기 : " + testDTO.getSb_content();
			LogDTO log = null;

			// HttpSession session = request.getSession();
			if (session.getAttribute("sm_id") != null) {// 로그인 했다면
				String id = (String) session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);// sl_id가 있음.

			} else {
				log = new LogDTO(ip, target, data);// sl_id가 없음.
			}
			logService.writeLog(log);

			return "redirect:/board?sb_cate=" + testDTO.getSb_cate();
		
		} else {
			// 로그인 안 한 사람
			return "redirect:/";
		}
	}

	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		testService.viewCount(sb_no); //조회수
		TestDTO dto = testService.detail(sb_no);

		ModelAndView mv = new ModelAndView("detail");

		mv.addObject("dto", dto);
		String ip = util.getUserIp(request);
		String target = "detail?sb_no=" + sb_no;
		String data = "글 읽기 : " + dto.getSb_content();
		LogDTO log = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {// 로그인 했다면
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id가 있음.
		} else {
			log = new LogDTO(ip, target, data);// sl_id가 없음.
		}
		logService.writeLog(log);
		
		return mv;
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TestDTO dto = new TestDTO();
		dto.setSb_no(sb_no);
		dto.setSm_id((String) session.getAttribute("sm_id"));
		if (session.getAttribute("sm_id") != null) {
			int result = testService.delete(dto);

			String ip = util.getUserIp(request);

			String target = "delete?sb_no=" + sb_no;
			String data = "글삭제 : " + result;
			LogDTO log = null;

			// HttpSession session = request.getSession();
			if (session.getAttribute("sm_id") != null) {// 로그인 했다면
				String id = (String) session.getAttribute("sm_id");
				log = new LogDTO(ip, target, id, data);// sl_id가 있음.
			} else {
				log = new LogDTO(ip, target, data);// sl_id가 없음.
			}
			logService.writeLog(log);
			return "redirect:/board";
		} else {
			return "redirect:/";
		}

	}

	@GetMapping("/update")
	public ModelAndView update(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("update");

		// 글번호 가져오기
		int sb_no = Integer.parseInt(request.getParameter("sb_no"));
//		System.out.println("sb_no : " + sb_no);

		TestDTO dto = testService.detail(sb_no);

		mv.addObject("dto", dto);

		return mv;
	}

	@PostMapping("/update")
	public String update(TestDTO dto, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("sm_id") != null) {
			dto.setSm_id((String) session.getAttribute("sm_id"));
			int result = testService.update(dto);
			// ip불러오기
			String ip = util.getUserIp(request);

			String target = "update?sb_no=" + dto.getSb_no();
			String data = "글수정 : " + result + " : " + dto.getSb_content();
			LogDTO log = null;
			String id = (String) session.getAttribute("sm_id");
			log = new LogDTO(ip, target, id, data);// sl_id가 있음.
			logService.writeLog(log);

			return "redirect:/detail?sb_no=" + dto.getSb_no();
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping("/like")
	public String like(@RequestParam("sb_no") int sb_no, HttpServletRequest request) {
//		System.out.println("들어온 sb_no : " + sb_no);
		HttpSession session = request.getSession();
		
		if(session.getAttribute("sm_id") != null) {
			Map<String, Object> sendMap = new HashMap<String, Object>();
			
			sendMap.put("sm_id", session.getAttribute("sm_id"));
			sendMap.put("sb_no", sb_no);
			
			testService.like(sendMap);
			return "redirect:/detail?sb_no="+sb_no;			
		} else {
			return "redirect:/";
		}
	}
}