
<#import "/admin_frame.ftl" as main>

<#assign html_other_script in main>
	<script type="text/javascript" src="${request.contextPath}/plugins/ckeditor_4.5.7_full/ckeditor/ckeditor.js"></script>
</#assign>

<@main.page title="会员资源导入" currentPage='vip' activeTag=11>
<script type="text/javascript" src="${request.contextPath}/plugins/ckeditor_4.5.7_full/ckeditor/ckeditor.js"></script>
<style>
	.col-xs-12{
		padding:0;
	}
</style>
		<div class="row">
			<div class="col-sm-12" style="margin-top:20px">
				<div id="" class="alert alert-danger">
					<h5>导入之前需要先上传到七牛云，各参数按如下显示传入</h5>
					<h6>1.thumb上传到公开空间，填入时填写全url如：http://o9sbx6hrz.bkt.clouddn.com/a8ec8a13632762d0600802bfa2ec08fa513dc6f8.jpg</h6>
					<h6>2.videoPath上传视频文件到私有空间，此处只需要传入文件名如：XXXXX.mp4</h6>
					<h6>3.summary中的html图片链接全部进行处理并上传到公有空间，加img标签时写全</h6>
					<h6>4.data中的html图片链接全部进行处理并上传到私有有空间，加img标签时如下 src="/web/getResource?filePath=logo_big_new.png"</h6>
				</div>
			</div>
		
		</div>
		<div class="row">
			<div class="col-sm-12 col-md-6 col-lg-6" style="margin-top:20px">
				<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="">title</i></span>
							<input style="height:44px;" type="text" class="form-control title" name="title" placeholder="title" />
						</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-6" style="margin-top:20px">
				<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="">videoPath</i></span>
							<input style="height:44px;" class="form-control videoPath" name="videoPath" placeholder="videoPath" />
						</div>
				</div>
			</div>
			
		</div>
		
		<div class="row">
			<div class="col-lg-12" style="margin-top:20px">
				<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="">thumb</i></span>
							<input style="height:44px;" type="text" class="form-control thumb" name="thumb" placeholder="thumb" />
						</div>
				</div>
			</div>
			
		</div>
		<div class="row">
			<div class="col-sm-12" style="margin-top:20px">
				<div id="" class="alert alert-info">
					下方导入非会员界面
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon1">summary</span>
				  <textarea class="form-control ckeditor" rows="10" aria-describedby="basic-addon1" id="summaryEditor"></textarea>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-12" style="margin-top:20px">
				<div id="" class="alert alert-info">
					下方导入会员界面
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				<div class="input-group">
				  <span class="input-group-addon" id="basic-addon1">data</span>
				  <textarea class="form-control ckeditor" rows="10" aria-describedby="basic-addon1" id="dataEditor"></textarea>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-12" style="margin-top:20px">
				<div id="import-resource-result" role="alert"></div>
			</div>
		
		</div>
		<div class="pull-right" style="margin-top:20px">
			<input id="submitVipImport" type="submit" class="btn btn-primary-outline" value="提交" />
		</div>
		
		<script type='text/javascript'>
			CKEDITOR.replace('summaryEditor');
			CKEDITOR.replace('dataEditor');
			$("#submitVipImport").click(function(){
				var title = $(".title").val();
				var videoPath = $(".videoPath").val();
				var thumb = $(".thumb").val();
				var summary = CKEDITOR.instances.summaryEditor.getData();
				var data = CKEDITOR.instances.dataEditor.getData();
				$.ajax({
						type : "POST",
						url : '/web/admin_projectxx/doImportResource',
						dataType : "json",
						data : {
							"title" : title,
							"videoPath" : videoPath,
							"thumb" : thumb,
							"summary" : summary,
							"data" : data,
						},
						success: function(json){	
							$("#import-resource-result").text(json.message);
							$("#import-resource-result").addClass("alert");
							if(json.result == "SUCCESS"){
								$("#import-resource-result").removeClass("alert-danger");
								$("#import-resource-result").addClass("alert-success");
								
							}else{
								$("#import-resource-result").removeClass("alert-success");
								$("#import-resource-result").addClass("alert-danger");
								
							}
						},
				
					});
			});
		</script>
</@main.page>