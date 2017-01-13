<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | Login</title>

    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/assets/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen animated fadeInDown">
         <div>
             <div>
                <h1 class="logo-name">RH+</h1>
            </div>
            <h3>OPM RH+</h3>            
            <p>inscription RH+</p>
			<c:if test="${not empty errorMsg}">
				<div class="alert alert-danger">
				  <strong>Error!</strong> ${errorMsg }.
				</div>
			</c:if>        
			<form:form method="post" modelAttribute="user" id="formUser" class="m-t" role="form" 
									action="${pageContext.request.contextPath}/Offer/Save">    
                <div class="form-group">
                    <spring:bind path="firstName">
						<div class="form-group <%=status.isError() ? "has-error" : ""%>">
							<form:errors path="firstName"
								cssStyle="color:red; font-size:11px;" />
							<form:input path="firstName" placeholder="First name" type="text" cssClass="form-control" />
						</div>
					</spring:bind>
                </div>
                <div class="form-group">
                    <spring:bind path="lastName">
						<div class="form-group <%=status.isError() ? "has-error" : ""%>">
							<form:errors path="lastName"
								cssStyle="color:red; font-size:11px;" />
							<form:input path="lastName" placeholder="Last name" type="text" cssClass="form-control" />
						</div>
					</spring:bind>
                </div>
                <div class="form-group">
                    <spring:bind path="password">
						<div class="form-group <%=status.isError() ? "has-error" : ""%>">
							<form:errors path="password"
								cssStyle="color:red; font-size:11px;" />
							<form:input path="password" placeholder="Password"  type="password" cssClass="form-control" />
						</div>
					</spring:bind>
                </div>
                <div class="form-group">
                    <spring:bind path="confirmPassword">
						<div class="form-group <%=status.isError() ? "has-error" : ""%>">
							<form:errors path="confirmPassword"
								cssStyle="color:red; font-size:11px;" />
							<form:input path="confirmPassword" placeholder="Password  confirmation"  type="password" cssClass="form-control" />
						</div>
					</spring:bind>
                </div>
                <div class="form-group">
<%--                 	<spring:bind path="entityGroup.id"> --%>
<!-- 						<div class="form-group " > -->
<%-- 							<form:label path="entityGroup.id">Entity </form:label> --%>
<%-- 							<form:errors path="entityGroup.id" cssStyle="color:red; font-size:11px;" /> --%>
<%-- 							<form:select multiple="single" path="entitygroup.id" cssClass="form-control selectpicker"> --%>
<%-- 								<form:option value="0" label="" /> --%>
<%-- 								<c:forEach var="entityGroups" items="${eGroup}"> --%>
<%-- 									<optgroup label="${eGroup.name}"> --%>
<%-- 										<form:options items="${eGroup.id}" itemLabel="name" itemValue="id" /> --%>
<!-- 									</optgroup> -->
<%-- 								</c:forEach> --%>
<%-- 							</form:select> --%>
<!-- 						</div> -->
<%-- 					</spring:bind> --%>
				</div>               
                <button type="submit" class="btn btn-primary block full-width m-b">Register</button>
                <p class="text-muted text-center"><small>Already have an account?</small></p>
                <a class="btn btn-sm btn-white btn-block" href='<c:url value="/Auth/login"></c:url>'>Login</a>
            </form:form>
            
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script>
    
	    var password = document.getElementById("password")
	    , confirm_password = document.getElementById("confirm_password");
	
		  function validatePassword(){
		    if(password.value != confirm_password.value) {
		      confirm_password.setCustomValidity("Passwords Don't Match");
		    } else {
		      confirm_password.setCustomValidity('');
		    }
		  }
		
		  password.onchange = validatePassword;
		  confirm_password.onkeyup = validatePassword;
    </script>

</body>
</html>
