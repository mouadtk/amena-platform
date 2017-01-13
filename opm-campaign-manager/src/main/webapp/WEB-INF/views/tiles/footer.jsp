<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="footer">
	<div class="pull-right">
<!-- 		10GB of <strong>250GB</strong> Free. -->
	</div>
	<div>
		<strong>Copyright</strong> Operating Media Company &copy; 2016-2017
	</div>
</div>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.1.js"></script>
<!-- jQuery UI -->
<script src="${pageContext.request.contextPath}/assets/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>


<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}/assets/js/inspinia.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/plugins/pace/pace.min.js"></script>

<!-- Toast UI -->
<script src="${pageContext.request.contextPath}/assets/js/plugins/toastr/toastr.min.js"></script>

<script  type="text/javascript">
	toastr.options = {
	  "closeButton": true,
	  "debug": false,
	  "progressBar": true,
	  "preventDuplicates": false,
	  "positionClass": "toast-top-right",
	  "onclick": null,
	  "showDuration": "400",
	  "hideDuration": "1000",
	  "timeOut": "7000",
	  "extendedTimeOut": "1000",
	  "showEasing": "swing",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}
</script>

<c:if test="${not empty errorMsg}">
	<script>
		toastr['error']("${errorMsg}")
	</script>
</c:if>
<c:if test="${not empty successMsg}">
	<script>
		toastr['success']("${successMsg}")
	</script>
</c:if>
<c:if test="${not empty warningMsg}">
	<script>
		toastr['warning']("${warningMsg}")
	</script>
</c:if>

