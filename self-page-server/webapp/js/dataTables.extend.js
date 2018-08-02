$.extend( true, $.fn.dataTable.Editor.defaults.i18n, {
	"create": {
        "button": "添加",
        "title":  "添加",
        "submit": "保存"
    },
    "edit": {
        "button": "修改",
        "title":  "修改",
        "submit": "保存"
    },
    "remove": {
        "button": "删除",
        "title":  "删除",
        "submit": "删除",
        "confirm": {
            "_": "确定要删除选中的 %d 行？",
            "1": "确定要删除选中的 1 行？"
        }
    },
    "error": {
        "system": "保存失败，请联系管理员"
    }
});

$.extend( true, $.fn.dataTable.defaults, {
	"oLanguage":{
	    "sProcessing":   "处理中...",
	    "sLengthMenu":   "显示 _MENU_ 项结果",
	    "sZeroRecords":  "没有匹配结果",
	    "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	    "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
	    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	    "sInfoPostFix":  "",
	    "sSearch":       "",
	    "sUrl":          "",
	    "sEmptyTable":     "表中数据为空",
	    "sLoadingRecords": "载入中...",
	    "sInfoThousands":  ",",
	    "oPaginate": {
	        "sFirst":    "首页",
	        "sPrevious": "上页",
	        "sNext":     "下页",
	        "sLast":     "末页"
	    },
	    "oAria": {
	        "sSortAscending":  ": 以升序排列此列",
	        "sSortDescending": ": 以降序排列此列"
	    }
	}
});

//Our custom field type
$.fn.DataTable.Editor.fieldTypes.datepicker = $.extend( true, {}, $.fn.DataTable.Editor.models.fieldType, {
    "create": function ( conf ) {
        var that = this;
 
        conf._enabled = true;
        // Create the elements to use for the input
        conf._input = $('<input type="text" class="form-control">')[0];
        //$(conf._input).attr("id", conf.id);
	    $(conf._input).datepicker({
	    	format: "yyyy-mm",
	        startView: 1,
	        minViewMode: 1,
			language: "zh-CN",
	    	autoclose: true
		});
 
        return conf._input;
    },
    
    "get": function ( conf ) {
    	return $(conf._input).val();
    },
 
    "set": function ( conf, val ) {
        if ( ! conf._enabled) {
            return;
        }
        
 		$(conf._input).datepicker("update", val);
    },
 
    "enable": function ( conf ) {
        conf._enabled = true;
        $(conf._input).removeClass( 'disabled' );
    },
 
    "disable": function ( conf ) {
        conf._enabled = false;
        $(conf._input).addClass( 'disabled' );
    }
 
} );