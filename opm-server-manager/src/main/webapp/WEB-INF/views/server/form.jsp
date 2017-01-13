<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Offer-Form</title>
<!-- Header contains main css files -->
<%@include file="../tiles/header.jsp"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">
   <link href="${pageContext.request.contextPath}/assets/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
</head>
<body>
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
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>Group Form</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">
								<form:form method="post" modelAttribute="group" id="myform"
									action="${pageContext.request.contextPath}/Group/Save" enctype="multipart/form-data">
									<div class="row">
										<form:hidden path="id" /><form:hidden path="logo" />
										<div class="col-sm-4 b-r">
											<spring:bind path="name">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="name">Group Name</form:label>
													<form:errors path="name" cssStyle="color:red; font-size:11px;" />
													<form:input path="name" type="text" cssClass="form-control" />
												</div>
											</spring:bind>
										</div>
										<div class="col-sm-4 b-r">
											<spring:bind path="activity">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="activity">Activity</form:label>
													<form:errors path="activity" cssStyle="color:red; font-size:11px;" />
													<form:input path="activity" type="text" cssClass="form-control" />
												</div>
											</spring:bind>
										</div>
										
										<div class="col-sm-4 b-r">
											<div class="form-group">
												<label for="logoFile">Logo</label>
												<input id="logoFile" name="file" type="file" class="file" />
											</div>
										</div>
										
										<div class="col-sm-12 ">
											<div class="hr-line-dashed"></div>
											<div class="row">
												<div class="col-sm-6 h-200">
													<spring:bind path="description">
														<div class="form-group h-200  <%=status.isError() ? "has-error" : ""%>">
															<form:label path="description">Job Description</form:label>
															<form:errors path="description" cssStyle="color:red; font-size:11px;" />
															<form:textarea path="description" type="text" cssClass="summernote form-control"
															 cssStyle="resize:vertical;" rows="4" htmlEscape="true" />
														</div> 
													</spring:bind>
												</div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="hr-line-dashed"></div>
											<div class="pull-right">
												<a href="<c:url value="/Group/All"/>" class="btn btn-white">Cancel</a>
												<button class="btn btn-primary btn-submit" type="submit">Save</button>
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!--  content end -->

			<!--  footer start -->
			<!-- footer contains main scripts  -->
			<%@include file="../tiles/footer.jsp"%>

			 <script src="${pageContext.request.contextPath}/assets/js/plugins/summernote/summernote.min.js"></script>
			<!-- Latest compiled and minified JavaScript -->
			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
			<!--  footer  end -->
			<script>
				$(document).ready(function() {
					
					 $('.summernote').summernote();
					 $('.selectpicker').selectpicker({
							liveSearch : true,
							size : 6
						});
					  $(".note-editable").attr('style','min-height:300px;');
				});		        
			</script>
		</div>
	</div>
</body>
</html>
