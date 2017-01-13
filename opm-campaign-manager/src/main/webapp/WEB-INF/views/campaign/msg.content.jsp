<div class="ibox float-e-margins">
    <div class="ibox-title">
        <h3><strong>e-mail structure </strong><small>header & body</small></h3>	        
    </div>
    <div class="ibox-content">
        <div class="row">
            <div class="col-sm-7 b-r" style="width:58% !important"><h3 class="m-t-none m-b">Headers <hr></h3> 
                   
                   <div class="form-group" >
                   		<label class="col-sm-11" title="Send a text for each mailbox"> Send a test to each email <input name="all_once"  type="checkbox" class="i-checks"> </label>
                   </div>
                   	<div class="form-group" >
                    	<label class="col-lg-3 control-label">Send to</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="sendto" type="text" placeholder="e.g. yourmail@gmail.com" class="required form-control multivalues"></div>                    	
                    </div>
                    	
                  	<div class="form-group" >
                    	<label class="col-lg-3 control-label">From name</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="fromname" type="text" value="admin" placeholder="e.g. mohamed karim" class="required form-control"></div>
                    </div>
                    
					<div class="form-group" >
                    	<label class="col-lg-3 control-label">From email</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="fromemail" type="text" value="admin" placeholder="e.g. admin" class="required form-control multivalues"></div>
                    </div>
                    
                    <div class="form-group" >
                    	<label class="col-lg-3 control-label">Bounce name</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="namebounce" type="text" value="return" placeholder="e.g. return" class="form-control"></div>
                    </div>
                    
                    <div class="form-group" >
                    	<label class="col-lg-3 control-label">Reply name</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="replyname" type="text" value="reply" placeholder="e.g. reply " class="form-control"></div>
                    </div>
                    
					<div class="form-group" >
                    	<label class="col-lg-3 control-label">Return Path</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="returnpath" type="text" value="return" placeholder="e.g. return@opm.com" class="form-control multivalues"></div>
                    </div>
                    
                    <div class="form-group" >
                    	<label class="col-lg-3 control-label">Recieved</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="received" type="text" placeholder=" " class="form-control multivalues"></div>
                    </div>
                   
                   	<div class="form-group" >
                    	<label class="col-lg-3 control-label">X-Mailer</label> 
                    	<div class="col-lg-9" style="margin-bottom: 1%;"><input name="xmailer" value="phpmailer" type="text" placeholder="e.g. phpmailer" class="form-control"></div>
                    </div>
                    
            </div>           
           
            <div class="col-sm-5"><h3>Header Foramt<hr/></h3>
	                <div class="form-group" >
	                   	<label class="col-lg-3 control-label" >Encoding</label> 
			            <div class="col-lg-9" >    
			                <select id="encode_type" name="encode_type" class="select_encoding form-control">
									<option value="0">codage_type0</option>
									<option value="1">codage_type1</option>
									<option value="2">codage_type2</option>
									<option value="3">codage_type3</option>
									<option value="4">codage_type4</option>
									<option value="5">codage_type5</option>
									<option value="6">codage_type6</option>
									<option value="7">codage_type7</option>
									<option value="8">codage_type8</option>
									<option value="9">codage_type9</option>
									<option value="10">codage_type10</option>
									<option value="11">codage_type11</option>
							</select>
						</div>
	            	</div>
	            	<p>
	            	<div class="form-group" >
	                   	<label class="col-lg-3 control-label" style="margin-top: 1%;" >Header </label> 
			            <div class="col-lg-9" style="margin-top: 1%;">    
			               <select id="header_num" name="header_num"  class="select_header form-control">
								<option value="0">Header 0</option>
								<option value="1">Header 1</option>
								<option value="2">Header 2</option>
								<option value="3">Header 3</option>
								<option value="4">Header 4</option>
								<option value="5">Header 5</option>
								<option value="6">Header 6</option>
								<option value="7">Header 7</option>
								<option value="8">Header 8</option>
							</select>
						</div>
	            	</div>
	            	<div class="form-group" >
	                    
			            <div class="col-lg-12" style="resize:vertical !important;margin:1% 0px 0px 0px !important;padding:0px !important" >    			            	
							<textarea name="formatheader" class="required form-control" rows="15">
Subject: __Subject
From: __From
Reply-to: <__Reply_Name>
To: __To
X-Originating-IP: __Ip
Date: __smtpDate
							</textarea>							 
						</div>
	            	</div>
	  
	        </div>
        </div>
    </div>
    
    <div class="ibox-content">
       <div class="row">
	       <div class="form-group" >
	       		<label class="col-lg-2 control-label">Subject </label>
	       		<div class="col-lg-6" style="margin-bottom: 1%;"><input type="text" name="subject" placeholder=" "  class="required form-control"></div>
                <label class="col-sm-2"> <input name="addservname" type="checkbox" class="i-checks"> add server name </label>
                <label class="col-sm-2"> <input name="addip"  type="checkbox" class="i-checks"> add IP </label>
	       </div>
	       <div class="form-group col-lg-12" >
	            <label class="control-label">Message </label> 
	           	<div class="" style="margin-top:1%;" >    	            	
					<textarea name="message" class="required form-control" rows="10"></textarea>					
				</div>
          </div>
                    
       </div>
    </div>
    
</div>