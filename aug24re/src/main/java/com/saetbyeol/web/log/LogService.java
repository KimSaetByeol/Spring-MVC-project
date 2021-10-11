package com.saetbyeol.web.log;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
	@Autowired
	private LogDAO logDAO;
	
	public void writeLog(LogDTO log) {
		logDAO.writeLog(log);
	}

	public List<LogDTO> logList(Map<String, Object> sendMap) {
		return logDAO.logList(sendMap);
	}

	public int logTotalList(Map<String, Object> sendMap) {
		return logDAO.logTotalList(sendMap);
	}
}
