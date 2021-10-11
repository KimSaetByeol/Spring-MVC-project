package com.saetbyeol.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saetbyeol.util.Util;
import com.saetbyeol.web.log.LogDTO;
import com.saetbyeol.web.log.LogService;
import com.saetbyeol.web.login.LoginDTO;
import com.saetbyeol.web.login.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private Util util;
	@Autowired
	private LogService logService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginAction(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
//		@RequestParam("id") String id;
		
//		System.out.println("들어온 id : " + id);
//		System.out.println("들어온 pw : " + pw);
		
		//LoginDTO 로 만들어주세요.
		LoginDTO dto = new LoginDTO();
		dto.setSm_id(id);
		dto.setSm_pw(pw);
		
		//service -> DAO -> mapper.xml
		LoginDTO result = loginService.login(dto);
		System.out.println(result);
		
		String ip = util.getUserIp(request);
		
		String target = "login";
		String data = "로그인 id : " + id + ", pw : " + pw;
		if (result != null) {
			data += " 로그인 성공";
		} else {
			data += " 로그인 문제발생";
		}
		
		System.out.println(data);
		
		LogDTO log = null;
		log = new LogDTO(ip, target, id, data);// sl_id가 있음.
		logService.writeLog(log);

		if (result != null) {
			HttpSession session = request.getSession();
			session.setAttribute("sm_name", result.getSm_name());
			session.setAttribute("sm_id", result.getSm_id());
			return "redirect:/board?sb_cate=4";
		} else {
			return "redirect:/?error=loginError";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// ip불러오기
		String ip = util.getUserIp(request);
		
		String target = "로그아웃";
		String data = "로그아웃 : ";
		String id = (String) session.getAttribute("sm_id");
		LogDTO log = new LogDTO(ip, target, id, data);// sl_id가 있음.

		logService.writeLog(log);

		if (session.getAttribute("sm_id") != null) {
			session.removeAttribute("sm_id");
		}
		if (session.getAttribute("sm_name") != null) {
			session.removeAttribute("sm_name");
		}
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@PostMapping("/join")
	public String joinAction(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		Map<String, Object> login = new HashMap<String, Object>();
		login.put("id", id);
		login.put("pw", pw);
		login.put("name", name);
		login.put("email", email);
		int result = loginService.join(login);
		
		String ip = util.getUserIp(request);
		
		String target = "가입하기";
		String data = "가입하기 결과 : " + result + " id: " + id + " /name : " + name +  " /email : " + email;
		LogDTO log = new LogDTO(ip, target, data);
		logService.writeLog(log);

		return "redirect:/";
	}
	
	//ajax id check
	@PostMapping("/checkID.do")
	public @ResponseBody String checkID(HttpServletRequest request) {
		String id = request.getParameter("id");
		String check = "1";
		check = loginService.checkID(id);

		String ip = util.getUserIp(request);
		
		String target = "checkID";
		String data = "id 물어봄 : " + id;
		LogDTO log = new LogDTO(ip, target, data);
		logService.writeLog(log);

		return check;
	}
}
