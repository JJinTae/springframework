<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">FileUpload & FileDownload</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				Form 태그를 위한 FileUpload
			</div>
			<div class="card-body">
			
				<form method="post" enctype="multipart/form-data" action="fileupload">
					<div class="form-group">
						<label for="title">File Title</label>
						<input type="text" class="form-control" id="title" name="title" placeholder="제목">
					</div>
					
					<div class="form-group">
						<label for="desc">File Description</label>
						<input type="text" class="form-control" id="desc" name="desc" placeholder="파일 설명">
					</div>
					
					<div class="form-group">
						<label for="attach">Example file input</label>
						<input type="file" class="form-control-file" id="attach" name="attach">
					</div>
					<button class="btn btn-info btn-sm">파일 업로드</button>
					<a href="javascript:fileupload()" class="btn btn-info btn-sm">AJAX파일 업로드</a>
				</form>
				
				
				 
				
			</div>
			
			<script>
				function fileupload() {
					const title = $("#title").val();
					const desc = $("#desc").val();
					const attach = document.querySelector("#attach").files[0];
					
					// Multipart/form-data
					const formData = new FormData(); // Multipart를 만드는 객체
					formData.append("title", title);
					formData.append("desc", desc);
					formData.append("attach", attach);
					
					// Ajax로 서버로 전송
					$.ajax({
						url: "fileuploadAjax",
						method: "post",
						data: formData,
						cache: false, // file을 큰 것을 보내기 때문에 캐싱해서 저장할 필요가 없다.
						processData: false, // file 데이터는 있는 그대로 서버로 전송해라
						contentType: false // 전체가 동일한 contentType이 아니기 때문에
					}).done((data) => {
						console.log(data);
						if(data.result === "success"){
							window.alert("파일 전송이 성공됨")	
						}
					});
				}
			</script>
			
		</div>
		
		<div class="card">
			<div class="card-header">
				FileUpload & FileDownload
			</div>
			<div class="card-body">
				<a href="filedownload?fileNo=1" class="btn btn-info btn-sm">파일 다운로드</a>
				<hr>
				
			</div>
		</div>
		
	</div>
</div>


<%@ include file="/WEB-INF/views/common/footer.jsp"%>
