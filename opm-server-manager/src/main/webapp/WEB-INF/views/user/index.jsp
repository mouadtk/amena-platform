<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<!-- Header contains main css files -->
<%@include file="../tiles/header.jsp"%>
	<link href="${pageContext.request.contextPath}/assets/css/plugins/dataTables/datatables.min.css" rel="stylesheet">
	  <!-- Sweet Alert -->
    <link href="${pageContext.request.contextPath}/assets/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

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
								<h5>Liste Des Utilisateurs</h5>
							</div>
							<div class="ibox-content">

								<div class="table-responsive">
									<table
										class="table table-striped table-bordered table-hover offer-dataTables">
										<thead>
											<tr>
												<th>Nom</th>
												<th>Prenom</th>
												<th>Login</th>
												<th>Role</th>
												<th>Affiliés</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${users}" var="user">
												<tr class="gradeX" id="tr-${user.id}">
													<td>${user.firstName}</td>
													<td>${user.lastName}</td>
													<td>${user.login}</td>
													<td>${user.role}</td>
													<td>
														<c:forEach items="${user.entityGroups }" var="grp" >
														${ grp.name } 
														</c:forEach>
													</td>													
													<td style="width: 8%;">
														<a class="btn btn-primary btn-xs" type="button" title="Details" href='<c:url value="Edit/${user.id }"/>'> <i class="fa fa-edit"></i></a>													
													</td>
												</tr>
											</c:forEach>								
										</tbody>
										<tfoot>
											<tr>
												<th>Nom</th>
												<th>Prenom</th>
												<th>Login</th>
												<th>Role</th>
												<th>Affiliés</th>
												<th></th>
											</tr>
										</tfoot>
									</table>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!--  content end -->
				
				<!-- remove candidature -->
				<div class="modal inmodal" id="deleteOffer" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog">
                    	<div class="modal-content animated fadeIn">
                            <div class="modal-header">
                                <h3>Etes vous sure de supprimer ce candidat</h3> 
                            </div>
                            <input type="hidden" name="ID2Del" />
                            <div class="removeCandModalBody modal-body">  </div>
                            <div class="modal-footer">
                                <button type="button" id="cancelDel" class="btn btn-white" data-dismiss="modal">Annuler</button>
                                <button type="button" id="confirmDel" class="btn btn-danger" data-dismiss="modal">Confirmer la suppression</button>
                            </div>
                        </div>
                    </div>
                </div>
				<!--  footer start -->
				<!-- footer contains main scripts  -->
				<%@include file="../tiles/footer.jsp"%>
				
				<script src="${pageContext.request.contextPath}/assets/js/plugins/dataTables/datatables.min.js"></script>
				<!-- Sweet alert -->
    			<script src="${pageContext.request.contextPath}/assets/js/plugins/sweetalert/sweetalert.min.js"></script>
			</div>
		</div>
	</div>
	<script>
        $(document).ready(function(){
        	  $('.offer-dataTables').DataTable({
                 dom: '<"html5buttons"B>lTfgitp',
                 buttons: [
                     {extend: 'copy'},
                     {extend: 'csv'},
                     {extend: 'excel', title: 'Candidats'},
                     {extend: 'pdf', title: 'Candidats'},

                     {extend: 'print',
                      customize: function (win){
                             $(win.document.body).addClass('white-bg');
                             $(win.document.body).css('font-size', '10px');

                             $(win.document.body).find('table')
                                     .addClass('compact')
                                     .css('font-size', 'inherit');
                     }
                     }
                 ]

             });
        });
        

    </script>

</body>
</html>
