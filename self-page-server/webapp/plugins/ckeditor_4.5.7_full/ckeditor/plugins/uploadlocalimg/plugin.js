CKEDITOR.plugins.add( 'uploadlocalimg', {
		icons: 'uploadlocalimg',
		init: function( editor ) {
				editor.addCommand( 'uploadAndInsert', {
						exec: function( editor ) {
								var inserted = false;
								var now = new Date();
								//editor.insertHtml( 'The current date and time is: <em>' + now.toString() + '</em>' );
								$('.upfile').click();
								$('.upform .upfile').change(function() {
									var filename = $(this).val();
									if (filename == "") {
										return;
									}
									var fileext = filename.substr(filename.lastIndexOf('.') + 1).toLowerCase();
									if (fileext.length < 5 && fileext != 'jpg' && fileext != 'jpeg' && fileext != 'png' && fileext != 'bmp') {
										window.setTimeout('alert("请选择图片文件")', 100);
										return;
									}
									if (inserted == false) {
										//$(this).parents("form").submit();
										submitForm();
									}	
									
								});
								
								function submitForm(){
									$(".uploading").removeClass("hide");
									if (inserted == false) {
										$('.upform').ajaxSubmit({
									//	async: false,
											timeout:   10000,
											success: function(data) {
												if (inserted == false) {
													editor.insertHtml('<p><img alt="" src="' + data.fileUrl +'" /></p>');
													$('.upfile').val("");
													inserted = true;
													$(".uploading-success").removeClass("hide");
													
													$(".uploading").addClass("hide");
													setTimeout('$(".uploading-success").addClass("hide");',2000)
												}
	
											},
											error: function(data) {
													$(".uploading").addClass("hide");
													$(".uploading-failed").removeClass("hide");
													setTimeout('$(".uploading-failed").addClass("hide");',2000);
												}
										});
									}else {
										return;
									}
									
								}
								
						}
				});
				editor.ui.addButton( 'Uploadlocalimg', {
						label: '上传并插入本地图片',
						command: 'uploadAndInsert',
						toolbar: 'insert'
        });
    }
});