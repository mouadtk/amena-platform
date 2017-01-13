<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- Header contains main css files -->
<%@include file="../tiles/header.jsp"%>
<link
	href="${pageContext.request.contextPath}/assets/css/plugins/dataTables/datatables.min.css"
	rel="stylesheet">
<!-- Sweet Alert -->
<link
	href="${pageContext.request.contextPath}/assets/css/plugins/sweetalert/sweetalert.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/assets/css/plugins/select2/select2.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/assets/css/plugins/iCheck/custom.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap-multiselect/bootstrap-multiselect.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css"
	rel="stylesheet">

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
					<form name="serverForm" >
						<div class="serverSettings col-lg-12">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h3>
										<strong>list servers</strong><small></small>
									</h3>
								</div>
								<div class="ibox-content">
									<div class="row">
										<div class="ibox float-e-margins">
												<div class="ibox-title">
													
												<div class="ibox-tools">
													<a class="btn btn-primary" 
															title="LaunchInstallation For all new servers and retry failed installation"
															href='<c:url value="/Server/GlobalInstallation"/>' 
													>Launch Installation</a>
												</div>
											</div>
											<div class="ibox-content ">
												<div class="row">
													<table
														class="table table-striped table-bordered table-hover result-table">
														<thead>
															<tr>
<!-- 																<th><input name="selectALL" type="checkbox" value="0"></th> -->
																<th>IP</th>
																<th>Provider</th>
																<th>State</th>
																<th>Installation</th>
																<th>Action</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${servers}" var="serv">
																<tr id="tr_${serv.id}">
<%-- 																	<td><input name="selectedServer" type="checkbox" value="${serv.id}"></td> --%>
																	<td>${serv.mainip}</td>
																	<td>${serv.provider.name}</td>
																	<td>${serv.state}</td>
																	<td>${serv.installState}</td>
																	<td>
																		<a class="btn btn-primary btn-xs "  href='<c:url value="/Server/Details/${serv.id}" />' title="details"><i class="fa fa-info"></i></a>
																		<c:choose>
																			<c:when test="${serv.installState=='INPROCESS'}">
																				<a class="btn btn-success btn-xs LaunchInstall" title="Launch installtion" disabled="disabled" data-id="${serv.id}"><i class="fa fa-info"></i></a>
																			</c:when>
																			<c:otherwise>
																				<a class="btn btn-success btn-xs LaunchInstall" title="Launch installtion" data-id="${serv.id}"><i class="fa fa-info"></i></a>
																			</c:otherwise>
																		</c:choose>																		
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="testResults col-lg-12 "></div>
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

				<script type="text/javascript">
    			 $(document).ready(function(){
						$(".table").DataTable();
						setTimeout(function(){routing();},1000*30);
    			 });
    			 
    			 $("input[name='selectALL']").click(function(){
    				 var x= $("input[name='selectALL']").is(":checked");
   					 $.each($("input[name='selectedServer']"),function(key, e){
   						 $(e).prop("checked",x);
   					 });
    			 });
    			 $("input[name='selectedServer']").click(function(){
    				 if(!$(this).is(":checked"))
    					 $("input[name='selectALL']").prop("checked",false);
    			 });

				$("body").on("click",".LaunchInstall", function(){
    				var id=$(this).attr("data-id");
    				var ebtn= $(this);
    				if(ebtn.attr("disabled")=="disabled"){
    					toastr['warning']("server already in process!");
    					return;
    				}
    					
    				ebtn.attr("disabled","disabled");
					$.ajax({
							url : 'Launch',
							type : 'POST',
							data:{'idServer':id},
							success : function(response, statut) {
								if(response.startsWith("Error")){
									toastr['error'](response);
								}else{
									$("#tr_"+id+" :nth-child(4)").html("INPROCESS");
									$(".table").dataTable().fnDestroy();
									$(".table").dataTable();
									
								}
							},
							error : function(response, statut, error){								
								toastr['error'](response);
								ebtn.attr("disabled",false);
							}
						});
				 });
				
				//check installation process 
				
				function routing(){
					var listId=[];
					$.each($(".LaunchInstall"),function(key,elm){
						console.log($(elm).html() +" "+$(elm).attr("disabled"));
						if($(elm).attr("disabled")=="disabled")
							listId.push($(elm).attr("data-id"));
					})
					if(listId.length>0){
						$.ajax({
							url : 'CheckInstallation',
							type : 'POST',
							data:{'ids':listId},
							
							success : function(response, statut) {
									for(var j=0; j<Object.keys(response).length;j++){
										var key= Object.keys(response)[j];
										$("#tr_"+key+" :nth-child(4)").html(response[key]);
										if(response[key]!="INPROCESS")
											$(".LaunchInstall[data-id="+key+"]").attr("disabled",false);
									}
									$(".table").dataTable().fnDestroy();
									$(".table").dataTable();
							},
							error : function(response, statut, error){								
								toastr['error'](response);
							}
						});
					}
					setTimeout(function(){routing();},1000*30);
				}				
				
				</script>
			</div>
		</div>
	</div>
</body>
</html>