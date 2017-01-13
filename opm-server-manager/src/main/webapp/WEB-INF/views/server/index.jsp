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
								<h5>Groups Table</h5>
								<div class="ibox-tools"> 
									<a class="btn btn-xs btn-primary "  style="color: white;font-family: open sans, Helvetica Neue, Helvetica, Arial, sans-serif;" href="Add"> 
										<i class="fa fa-plus"> New Group </i>
									</a>
								</div>
							</div>
							<div class="ibox-content">

								<div class="table-responsive">
									<table
										class="table table-striped table-bordered table-hover offer-dataTables">
										<thead>
											<tr>
												<th>Logo</th>
												<th>Name</th>
												<th>Activity</th>
												<th>Description</th>											
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${groups }" var="group">
												<tr class="gradeX" id="tr-${group.id}">
													<td><img src="${pageContext.request.contextPath}/logos/${group.logo }" width="80" /></td>
													<td style="width: 10%;">${group.name }</td>
													<td style="width: 10%;">${group.activity}</td>
													<td>${group.description}</td>
													<td style="width: 8%;">
														<a class="btn btn-success btn-xs" type="button" title="Edit" href='<c:url value="/Group/Edit/${group.id }"/>'> <i class="fa fa-edit"></i></a>
														<a class="btn btn-primary btn-xs deleteClass" style="background-color: black; border-color: black;" type="button" title="delete" data-id="${group.id }" > <i class="fa fa-trash"></i></a>
													</td>
												</tr>
											</c:forEach>								
										</tbody>
										<tfoot>
											<tr>
												<th>Logo</th>
												<th>Name</th>
												<th>Activity</th>
												<th>Description</th>
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
                                <h3>Etes vous sure de supprimer ce Group ? </h3> 
                            </div>
                            <input type="hidden" name="ID2Del" />
                            <div class="removeCandModalBody modal-body">  </div>
                            <div class="modal-footer">
                                <button type="button" id="cancelDel" class="btn btn-white" data-dismiss="modal">Annuler</button>
                                <button type="button" id="confirmDel" class="btn btn-danger" data-dismiss="modal">Confirmer suppression</button>
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
                     {extend: 'csv',title: 'Groups-File'},
                     {extend: 'excel', title: 'Groups-File'},
                     {extend: 'pdf', title: 'Groups-File'},

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
        
        $(".deleteClass").click(function () { 
        	var id=$(this).attr("data-id");
        	swal({
	            title: "Suppression d'offre ",
	            text: "Etes vous sure de supprimer ce Group ?!",
	            type: "warning",
	            showCancelButton: true,
	            confirmButtonColor: "#DD6B55",
	            confirmButtonText: "Ok, Supprimer!",
	            cancelButtonText: "Non, Annuler",
	            closeOnConfirm: false
	        }, function (isConfirm) {
	            if (!isConfirm) return;
	            $.ajax({
	                url: "Delete",
	                type: "POST",
	                data: {
	                    id: id
	                },
	                dataType: "json",
	                success: function (data) {
	                	if(data==0)
	                		swal("Error deleting!", "You can not delete an unavailable offer!", "warning");
	                	else if(data==-1)
	                		swal("Error deleting!", "Please try again", "error");
	                	else if(data==1){
	                		el = $('#tr-'+id);
							el.css("background-color","rgba(198, 38, 38, 0.2)");
							el.fadeOut(1400, function(){
								el.remove();
								var table = $('.offer-dataTables').DataTable();
								table.row('#tr-'+id).remove();
					        });
							swal("Done!", "It was succesfully deleted!", "success");
	                	}
	                },
	                error: function (xhr, ajaxOptions, thrownError) {
	                    swal("Error deleting!", thrownError, "error");
	                }
	            });
	        });
        });
    </script>

</body>
</html>
