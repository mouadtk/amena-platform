<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<!-- Header contains main css files -->
<%@include file="../tiles/header.jsp"%>
	<link href="${pageContext.request.contextPath}/assets/css/plugins/dataTables/datatables.min.css" rel="stylesheet">
	  <!-- Sweet Alert -->
    <link href="${pageContext.request.contextPath}/assets/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/plugins/iCheck/custom.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap-multiselect/bootstrap-multiselect.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" rel="stylesheet">
	
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
				 	<form name="testForm">
	                    <div class="msgContent col-lg-8">
	                        <%@include file="./msg.content.jsp"%>
	                    </div>
	                    
	                    <div class="serverSettings col-lg-4">
	                        <%@include file="./serverlist.jsp"%>
	                        <%@include file="./settings.jsp"%>
	                    </div>
                   </form> 
                </div>
                <div class="row">
<!--                 	LOADER EXAMPLES  -->
<!--                 	<i class="fa fa-spinner fa-spin fa-1x fa-fw"></i> -->
<!-- 					<i class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i> -->
<!-- 					<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> -->
<!-- 					<i class="fa fa-cog fa-spin fa-3x fa-fw"></i> -->
<!-- 					<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i> -->
                	
                    <div class="testResults col-lg-12 ">
                    	  <%@include file="./test.results.jsp"%>
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
				<script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap-multiselect/bootstrap-multiselect.js" ></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/validate/jquery.validate.min.js"></script>
				<script src="${pageContext.request.contextPath}/assets/js/plugins/dataTables/datatables.min.js"></script>
				  
    			<script type="text/javascript">
    			
    			 $(document).ready(function() {
    				$(".result-table").DataTable();
    				$('.multivalues').tagsinput({});
    				$('.select_servers').multiselect({
    					enableClickableOptGroups: true,
    		            enableCollapsibleOptGroups: true,
    		            enableFiltering: true,
    		            includeSelectAllOption: true,
    		            maxHeight: 400,
    		            buttonWidth: '100%'
			        });
    				$(".select_encoding").select2();
    				$(".select_header").select2();
    				$('.i-checks').iCheck({
    	                    checkboxClass: 'icheckbox_square-green',
    	                    radioClass: 'iradio_square-green',
    	            });

    				/** Form validation */
    				 $.validator.setDefaults({
				        ignore: []
				    });
    				$("form").validate({
    					errorPlacement : function(label, element) {
    						$('<span class="error"></span>').insertAfter(element).append(label)
    					}
    				});  
    				
    				var TimeOut;
    				var job_id;
    				/***
    				* Send test
    				**/
    				$("#sendTest").click(function(){
    				    if(TimeOut!=null){
    				    	clearTimeout(TimeOut);
    				    	if(job_id!=null){
    				    		$.ajax({
    	    					    type: "POST",
    	    					    url: "/GlobTest/CleanFactory/"+job_id,    					    
    	    					    success: function(data) {
    	    					    	console.log(data);
    	    					    }
    				    		});
    				    	}
    				    }
    					if(!$("form").valid()){
    						return;
    					}
			    		var formdata = $("form").serialize();
    					$.ajax({
    					    type: "POST",
    					    url: "/GlobTest/sendTest",
    					    data: formdata,
    					    success: function(data) {
    					    	/**
    					    	 * read & append results to datatable
    					    	 */
    					    	table = $(".result-table").DataTable();
    					    	table.rows().remove().draw();
   					        	job_id=data["JOB_ID"];
   					        	if(job_id!=null)
   					        		refreshResult();
    					    },
    					    error: function() {    					    	
    					        console.log('server error ');
    					    }
    					});
    				});
    				
    				
    				function refreshResult(){
    					var askStatusAgain=false
    					$.ajax({
    					    type: "POST",
    					    url: "/GlobTest/TestResults/"+job_id,    					    
    					    success: function(data) {
    					    	table = $(".result-table").DataTable()    		
    					    	table.rows().remove().draw();
    					    	console.log(data);
    					    	
    					    	if(data!=null){
    					    		console.log(data);
    					    		load='<i class="fa fa-spinner fa-spin fa-1x fa-fw"></i>';
    					    		data.forEach(function(rsp){
    					    			table.row.add([rsp.server.name ,
    					    			               	rsp.server.mainip,
    					    			               	rsp.ipsrc,
    				    					            rsp.rcptto,
    				    					            '<center>'+(rsp.received?'<button class="btn btn-primary btn-xs btn-circle" type="button"><i class="fa fa-check"></i></button>':
    				    					            	'<button class="btn btn-danger btn-circle" type="button"><i class="fa  fa-times"></i></button>')+'</center>',
    				    					            '<center>'+(rsp.responseType==null?load:btnStyle(rsp.responseType, rsp.dsnStatus))+'</center>',
    				    					            rsp.dsnDiag
    				    					        ]).draw();
    					    			if(rsp.dsnStatus==null)
    					    				askStatusAgain=true;
    					    		})
    					    	}
    					        
    					        $(".testResults").removeClass("hide");
    					        /** scroll down*/
    					        $('html, body').animate({
    					            scrollTop: $(".result-table").offset().top
    					        }, 2000);
    					        
    					        $('.result-table').addClass('highlighted');
    					        setTimeout(function(){
    					          $('.result-table').removeClass('highlighted');}, 2000);
    					        if(askStatusAgain)
    					        	TimeOut=setTimeout(function(){refreshResult()},10000);
    					        else
    					        	console.log("salina ")
    					    },
    					    error: function() {    					    	
    					        console.log('server error ');
    					    }
    					});
    				}
    				
    				
    				function btnStyle(bounceType, dsnStatus){
		    			btnDlv='<a class="btn btn-success btn-xs btn-rounded" href="#">Delivery</a>';
		    			btnBounce='<a class="btn btn-danger btn-xs btn-rounded" href="#">Bounce</a>';
		    			btnDeffered='<a class="btn btn-warning btn-xs btn-rounded" href="#">Deferred</a>';

		    			if(bounceType=='d')
		    				return btnDlv;
		    			else if(bounceType=='b'){
		    				if(dsnStatus!=null && dsnStatus.startsWith('4.'))
		    					return btnDeffered;
		    				else if(dsnStatus!=null && dsnStatus.startsWith('5.'))
		    					return btnBounce;
		    			}
		    			return "NO DESCISION"+dsnStatus;
    				}
    				
    				$("#header_num").change(function (){
    					var header = ["Subject: __Subject\nFrom: __From\nReply-to: __Reply_Name\nTo: __To\nX-Originating-IP: __Ip\nDate: __smtpDate\n",
    								 "MIME-Version: 1.0\nFrom: __From\nReply_Name: __Reply-To\nTo: __To\nSubject: __Subject\nX-Mail-From: __Bounce_Name\nX-RCPT-To: __To\nX-Mailer: __X_Mailer\nDate: __smtpDate",
    								 "",
    								 "Reply-To: __Reply_Name\nBounces_to: __Bounce_Name\nMessage-ID: <__Chr(__Rand(97,122))__Chr(__Rand(98,122)).__X_Mailer@__From_dn>\nX-BFI: __Chr(__Rand(97,122))__Chr(__Rand(98,122))\nFrom: __From\nSubject: __Subject\nTo: __To\nMIME-Version: 1.0\nReturn-Path: __Bounce\nDate: __smtpDate",
    								 "Message-ID: <__Rand(10,100).__Rand(10,100).0.__Rand(1000,9999).__Chr(__Rand(97,122))__Chr(__Rand(98,122))@__X_Mailer>\nTo: __To\nFrom: __From\nSender: __From_nm@__From_dn\nX-Loop: __From_nm@__From_dn\nX-Mailer: __X_Mailer\nX-Unsubscribe: <mailto:leave-__Rand(10000,100000).-.__Rand(10000,100000).__Chr(__Rand(97,122))__Chr(__Rand(98,122))@__From_dn>\nX_Id: __Chr(__Rand(97,122)).__Chr(__Rand(97,122)):__Date:__To/__Chr(__Rand(98,122))__Chr(__Rand(99,122))\nSubject: __Subject\nReturn-Path: __Chr(__Rand(97,122))__Chr(__Rand(98,122))@__Bounce_dn\nDate: __smtpDate",
    								 "Subject: __Subject\nFrom: __From\nReply-To: <__Reply_Name>\nTo: __To\nxprior: Low\nmsprior: Normal\nxdelay: 150000\nxmailer: __X_Mailer\nDate: __smtpDate",
    								 "Message-ID: <29986454.1180728024697.__X_Mailer@__From_dn>\nFrom: __From\nReply-To: __Reply_Name\nTo: __To\nSubject: __Subject\nMime-Version: 1.0\nx-mid: __Rand(100000,999999)\nReturn-Path: __Bounce_Name\nDate: __smtpDate",
    								 "Date: __smtpDate\nSubject: __Subject\nFrom: __From\nReply-to: __Reply_Name\nTo: __To\nX-Mailer: __X_Mailer\nMIME-Version: 1.0",
    								 "X-Mailer: __X_Mailer\nFrom: __From\nPrecedence: bulk\nMIME-Version: 1.0\nList-Unsubscribe: <mailto:listunsub@__Bounce_dn>\nTo: __To\nSubject: __Subject\nIn-Reply-to: __To\nReferences: __To\nX-ListMember: __To\nX-Abuse-Reports-To: abuse@__Bounce_dn"	
    					              ];    					
    					$("textarea[name=formatheader]").val(header[$(this).val()]);
    				});
    				
    			 });
    			</script>
				<style>				
					body {border: #acafb3 !important;}
					.bootstrap-tagsinput {width: 100% !important;}
					.highlighted	{border-color: green;}	
				</style>		
			</div>
		</div>
	</div>
</body>
</html>
