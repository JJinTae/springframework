package com.mycompany.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ch09")
public class Ch09Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch09Controller.class);

	@RequestMapping("/content")
	public String content() {
		return "ch09/content";
	}
	
	@PostMapping("/fileupload")
	public String fileupload(String title, String desc, MultipartFile attach) throws Exception{
		logger.info("실행");
		
		// 문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);
		
		// 파일 파트 내용 읽기
		logger.info("attach : " + attach.getOriginalFilename());
		logger.info("file type : " + attach.getContentType());
		logger.info("file size : " + attach.getSize());
		
		// 파일 파트 데이터를 서버의 파일로 저장
		String savedName = new Date().getTime() + "-" + attach.getOriginalFilename();
		File file = new File("C:/hyundai_it&e/upload_files/" + savedName);
		attach.transferTo(file);
		
		return "redirect:/ch09/content";
	}
	
	@PostMapping(value="/fileuploadAjax", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String fileuploadAjax(String title, String desc, MultipartFile attach) throws Exception{
		logger.info("실행");
		
		// 문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);
		
		// 파일 파트 내용 읽기
		logger.info("attach : " + attach.getOriginalFilename());
		logger.info("file type : " + attach.getContentType());
		logger.info("file size : " + attach.getSize());
		
		// 파일 파트 데이터를 서버의 파일로 저장
		String savedName = new Date().getTime() + "-" + attach.getOriginalFilename();
		File file = new File("C:/hyundai_it&e/upload_files/" + savedName);
		attach.transferTo(file);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		jsonObject.put("savedName", savedName);
		String json = jsonObject.toString();
		
		return json;
	}
	
	// 좋은 방법이 아니다.
	// 응답 바디의 데이터 형식이 고정이다.
	// toByteArray()에 리턴하는 배열의 길이 문제 파일의 크기만큼 배열을 생성함
	//	@GetMapping(value="/filedownload", produces="image/jpeg")
	//	@ResponseBody
	//	public byte[] filedownload(String savedname, HttpServletResponse response) throws Exception{
	//		String filePath = "C:/hyundai_it&e/upload_files/" + savedname;
	//		InputStream is = new FileInputStream(filePath);
	//		byte[] data = IOUtils.toByteArray(is);
	//		
	//		return data;
	//	}
	
	@GetMapping(value="/filedownload")
	@ResponseBody
	public void filedownload(
			int fileNo, 
			HttpServletResponse response,
			@RequestHeader("User-Agent") String userAgent
			) throws Exception{
		String contenttype = "image/jpeg";
		String originalfilename = "눈내리는 마을.jpg";
		String savedName = "1630656728626-눈내리는 마을.jpg";
		
		// 응답 바디의 데이터의 형식
		response.setContentType(contenttype);
		
		// 브라우저 별로 한글 파일명을 변환
		if(userAgent.contains("Trident") || userAgent.contains("MSIE")) {
			// IE일 경우
			originalfilename = URLEncoder.encode(originalfilename, "UTF-8");
		} else {
			// 크롬, 엣지, 사파리 브라우저에서 한글 파이명을 반환
			originalfilename = new String(originalfilename.getBytes("UTF-8"), "ISO-8859-1");
		}
		
		// 파일을 첨부로 다운로드 하도록 설정
		response.setHeader("Content-Disposition", "attachment; filename=\"" + originalfilename + "\"");
		
		// 파일로부터 데이터를 읽는 입력스트림 생성
		String filePath = "C:/hyundai_it&e/upload_files/" + savedName;
		InputStream is = new FileInputStream(filePath);
		
		// 응답 바디에 출력하는 출력스트림 얻기
		OutputStream os = response.getOutputStream();
		
		// 입력스트림 -> 출력스트림
		// 파일사이즈가 커져도 무리가 없다. 일정량씩 읽고 출력하는 형태이다.
		FileCopyUtils.copy(is, os);
		is.close();
		os.flush();
		os.close();
	}
}
