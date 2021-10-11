package com.saetbyeol.util;
//파일 저장하는 클래스
//객체화 하기 위해 추가해야 할 것은?

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSave {
	//Spring에서 제공하는 fileCopyUtil을 이용해 파일 저장하기
	public String save(String realPath, MultipartFile files) throws IOException {
		File file = new File(realPath);
		if(!file.exists()) { //경로가 있는지 질의
//			file.mkdir(); //자기 파일만 만들기
			file.mkdirs(); //자기 파일까지 오는 경로의 모든 파일 만들기
		}
		//UUID-> 유니크?
		String fileName = UUID.randomUUID().toString();
		fileName = fileName + "_" + files.getOriginalFilename();
		System.out.println("만들어진 fileName : " + fileName);
		
		//파일 올리기
		file = new File(file, fileName);
		FileCopyUtils.copy(files.getBytes(), file);
		
		return fileName;
	}
	
	//multiPart에서 제공하는 업로드로 저장하기
	
}
