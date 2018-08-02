
<#import "/admin_frame.ftl" as main>

<#assign html_other_script in main>
	
</#assign>

<@main.page title="会员充值管理" currentPage='vip' activeTag=11>
<style>
	.col-xs-12{
		padding:0;
	}
</style>
		
		<div class="row">
			<div class="col-sm-12 col-md-6 col-lg-4" style="margin-top:20px">
				<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"></i></span>
							<input style="height:44px;" type="text" class="form-control userId" name="userId" placeholder="userId" />
						</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-4" style="margin-top:20px">
				<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-dollar"></i></span>
							<input style="height:44px;" class="form-control payBillAmount" name="payBillAmount" placeholder="充值金额（分）" />
						</div>
				</div>
			</div>
			
			
			<div class="col-sm-12  col-md-6 col-lg-6" style="margin-top:20px">			
				<h5>选择会员类型</h5>
				
				<select class="form-control type">
					<option>0</option>
					<option>1</option>
					<option>30</option>
					<option>90</option>
					<option>365</option>
				</select>
				
			</div>
			<div class="col-sm-12  col-md-12 col-lg-12" style="margin-top:20px">			
				<h5>备注:</h5>
				
				<div class="form-group">
						<div class="input-group">
							<input style="height:44px;width:100%" class="form-control description" name="payBillAmount" placeholder="备注描述" />
						</div>
				</div>
				
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-12" style="margin-top:20px">
				<div id="get-vip-result" role="alert"></div>
			</div>
		
		</div>
		<div class="pull-right" style="margin-top:20px">
			<input id="submitVipImport" type="submit" class="btn btn-primary-outline" value="提交" />
		</div>
		
		<script type='text/javascript'>
		
			$("#submitVipImport").click(function(){
				var userId = $(".userId").val();
				var payBillAmount = $(".payBillAmount").val();
				var type = $(".type").val();
				var description = $(".description").val();
				$.ajax({
						type : "POST",
						url : '/web/admin_projectxx/doImportVip',
						dataType : "json",
						data : {
							"userId" : userId,
							"payBillAmount" : payBillAmount,
							"type" : type,
							"description" : description
						},
						success: function(json){	
							$("#get-vip-result").text(json.message);
							$("#get-vip-result").addClass("alert");
							if(json.result == "SUCCESS"){
								$("#get-vip-result").removeClass("alert-danger");
								$("#get-vip-result").addClass("alert-success");
								
							}else{
								$("#get-vip-result").removeClass("alert-success");
								$("#get-vip-result").addClass("alert-danger");
								
							}
						},
				
					});
			});
		</script>
</@main.page>