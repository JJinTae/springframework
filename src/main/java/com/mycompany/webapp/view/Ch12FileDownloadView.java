package com.mycompany.webapp.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class Ch12FileDownloadView extends AbstractView{
	private static final Logger logger = LoggerFactory.getLogger(Ch12FileDownloadView.class);

	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("실행");
		
		String fileName = (String)model.get("fileName");
		String userAgent = (String)model.get("userAgent");
		
		
		String contenttype = request.getServletContext().getMimeType(fileName); // 파일의 확장명을 보고 타입을 결정해준다.
		String originalfilename = fileName;
		String savedName = fileName;
		logger.info(fileName);
		
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
