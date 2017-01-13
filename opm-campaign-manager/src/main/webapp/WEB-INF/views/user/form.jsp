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
								<h5>User Form</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">
								<form:form method="post" modelAttribute="user" id="myform"
									action="${pageContext.request.contextPath}/User/Save">
									<div class="row">
										<div  class="col-sm-6 ">
											<spring:bind path="firstName">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="firstName">First Name</form:label>
													<form:errors path="firstName" cssStyle="color:red; font-size:11px;" />
													<form:input path="firstName" type="text" cssClass="form-control" />
												</div>
											</spring:bind>
										</div>
										<div  class="col-sm-6">
											<spring:bind path="lastName">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="lastName">Last Name</form:label>
													<form:errors path="lastName" cssStyle="color:red; font-size:11px;" />
													<form:input path="lastName" type="text" cssClass="form-control" />
												</div>
											</spring:bind>
										</div>
										<div  class="col-sm-6">
											<spring:bind path="login">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="login">Login</form:label>
													<form:errors path="login" cssStyle="color:red; font-size:11px;" />
													<form:input path="login" type="text" cssClass="form-control" />
												</div>
											</spring:bind>
										</div>
										<div  class="col-sm-6">
											<spring:bind path="actif">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="actif" cssClass="form-label ">Compte</form:label>
													<form:errors path="actif" cssStyle="color:red; font-size:11px;" />
													<form:radiobutton path="actif"    label ="Actif" value="true"/>
													<form:radiobutton path="actif"   label="Inactif" value="false"/>
												</div>
											</spring:bind>
										</div>
										<div class="col-sm-6 ">
											<form:hidden path="id" />
											<spring:bind path="entityGroups">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="entityGroups">Entity </form:label>
													<form:errors path="entityGroups" cssStyle="color:red; font-size:11px;" />
													<form:select id="entities" multiple="true" path="entityGroups" cssClass="form-control selectpicker">
														<form:option value="0" label="" />
														<c:forEach var="itemGroup" items="${groups }">
															<optgroup label="${itemGroup.name}">
																<form:options items="${itemGroup.entities}" itemLabel="name" itemValue="id" />
															</optgroup>
														</c:forEach>
													</form:select>
												</div>
											</spring:bind>
										</div>
										<div class="col-sm-6 ">
											<form:hidden path="id" />
											<spring:bind path="role">
												<div class="form-group <%=status.isError() ? "has-error" : ""%>">
													<form:label path="role">Role </form:label>
													<form:errors path="role" cssStyle="color:red; font-size:11px;" />
													<form:select id="roles" multiple="single" path="role" cssClass="form-control selectpicker">
														<form:option value="" label="" />
														<form:options items="${roles}"  />
													</form:select>
												</div>
											</spring:bind>
										</div>
										<div class="col-sm-12">
											<div class="hr-line-dashed"></div>
											<div class="pull-right">
												<a href="<c:url value="/User/All"/>" class="btn btn-white">Cancel</a>
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

			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
			<!--  footer  end -->
			<script type="text/javascript">
				var item=[];
			</script>
			<c:forEach items="${user.entityGroups}" var="x">
				<script type="text/javascript">
					item.push("${x.id}");
				</script>
			</c:forEach>
			<script>
				$(document).ready(function() {
					 $("#entities").val(item);
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
