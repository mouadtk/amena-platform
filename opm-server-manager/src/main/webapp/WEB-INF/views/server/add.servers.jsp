<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<!-- Header contains main css files -->
<%@include file="../tiles/header.jsp"%>

<link href="${pageContext.request.contextPath}/assets/css/plugins/dataTables/datatables.min.css"rel="stylesheet">
<!-- Sweet Alert -->
<link href="${pageContext.request.contextPath}/assets/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/plugins/select2/select2.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap-multiselect/bootstrap-multiselect.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<style>
	body {
		border: #acafb3 !important;
	}
	.bootstrap-tagsinput {
		width: 100% !important;
	}
	.highlighted {
		border-color: green;
	}
</style>
</head>
<body class="pace-done mini-navbar">
	<div id="wrapper">
		<!-- Left menu start -->
		<%@include file="../tiles/leftMenu.jsp"%>
		<!-- Left menu END  -->
		<div id="page-wrapper" class="gray-bg">
			<!--  FIXED BAR START -->
			<%@include file="../tiles/topBar.jsp"%>
			<!-- FIXED BAR END  -->
			<div class="wrapper wrapper-content">
				<!--  content debut -->

				<div class="row">
						<div class="serverSettings ibox float-e-margins col-lg-6">
							<div class="ibox-title">
								<h3>
									<strong>New server</strong><small></small>
								</h3>
							</div>
							<div class="ibox-content">
								<form name="serverForm" id="serverForm" method="post" action="${pageContext.request.contextPath}/Server/Save">
									<div class="row">
										<div class="form-group">
											<label class="col-lg-3 control-label">main ip</label>
											<div class="col-lg-9" style="margin-bottom: 1%;">
												<input name="main_ip" type="text"
													placeholder="e.g. : 1.2.3.4" class="required form-control ">
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label">ssh port</label>
											<div class="col-lg-9" style="margin-bottom: 1%;">
												<input name="sshport" type="text" value="22"
													placeholder="e.g. 22" class="required form-control">
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3 control-label">password</label>
											<div class="col-lg-9" style="margin-bottom: 1%;">
												<input name="password" type="password" value=""
													placeholder="password" class="required form-control">
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3  control-label">extra IPs</label>
											<div class="col-lg-9" style="margin-bottom: 1%;">
												<input name="extraips" type="password" value=""
													placeholder="e.g. 1.2.3.4, 11.22.33.44"
													class="required form-control multivalues">
											</div>
										</div>
										<div class="form-group">
											<label class="col-lg-3  control-label">Provider</label> <select
												id="providers" name="provider" class="required ">
												<option>Select you server provider</option>
												<c:forEach items="${providers }" var="p">
													<option value="${p.id }">${p.name }</option>
												</c:forEach>
												
											</select>
										</div>
										<div class="actions clearfix">
											<div class="text-center">
												<div style="margin-top: 20px">
													<button type="button" id="saveServer"
														class="btn  btn-w-m btn-primary pull-right">
														Save</button>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>Servers File</h5><small> &nbsp; respect the form ( provider Name,mainIP,sshport,password,ips-seperated by pipe | )</small>
								</div>
								<div class="ibox-content">
									<form id="uploadFile" style="min-height: 200px;" method="post" enctype="multipart/form-data" action='<c:url value="/Server/UploadFile"/>'>
										<div class="center">
											<input type="file" style="margin-top: 70px;position: relative;" name="file" class="file "/>
										</div>
										<button type="submit" class="btn btn-primary pull-right" style="margin-bottom: 70px;" >Upload the file!</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!--  content end -->
				<!--  footer start -->
				<!-- footer contains main scripts  -->
				<%@include file="../tiles/footer.jsp"%>

				<!-- Sweet alert -->
				<script src="${pageContext.request.contextPath}/assets/js/plugins/sweetalert/sweetalert.min.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/select2/select2.full.min.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/iCheck/icheck.min.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap-multiselect/bootstrap-multiselect.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/validate/jquery.validate.min.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/dataTables/datatables.min.js"></script>

	    		<script src="${pageContext.request.contextPath}/assets/js/plugins/dropzone/dropzone.js"></script>
				<script type="text/javascript">
	    			 $(document).ready(function(){
	     					$('.multivalues').tagsinput({});
	     					$("#providers").select2();
	     					/** Form validation */
	        				$.validator.setDefaults({
	    				        ignore: []
	    				    });
	        				$("#serverForm").validate({
	        					errorPlacement : function(label, element) {
	        						$('<span class="error"></span>').insertAfter(element).append(label)
	        					}
	        				});
	        				$("#saveServer").click(function(){
	        					if(!$("#serverForm").valid()){return;}
	        					$("#serverForm").submit();
	        				});
	    			 });
    			</script>
				
			</div>
		</div>
</body>
</html>