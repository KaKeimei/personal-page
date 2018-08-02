var articlesStauts = [
    {"index":0,"saveed":false,"isRoot":true}
];

window.onbeforeunload=function (event){
	if(!isAllSaveed()){
	    var evt = event || window.event;
	    evt.returnValue = "您还有素材没有保存，你确定要离开当前页面吗？";
	}
};

function setArticlesStauts(opt,val){
    for(var i=0;i<articlesStauts.length;i++){
        var changeKey = -1;
        for(var key in opt){
            if(articlesStauts[i][key]!=opt[key]){
                changeKey = -1;
                break;
            }else{
                changeKey = i;
            }
        }
        if(changeKey>=0){
            for(var key in val){
                articlesStauts[changeKey][key] = val[key];
            }
        }
    }
}

function isAllSaveed(){
	for(var i=0;i<articlesStauts.length;i++){
		if(!articlesStauts[i]['saveed']){
			return false;
		}
	}
	return true;
}

var autoIndex = 0;

function postResult(data){
    if(data.success == 1){
        setSaveed(data.actionIndex,true);
        $('#form_'+data.index).find('.aid').val(data.aid);
        if(typeof data["parentId"]!="undefined"){
            parentId = data["parentId"];
            definedArticle.parentId = parentId;
            $('.parentId').val(parentId);
        }
        alert("保存成功");
    }else{
        alert("失败,请重试");
    }


}

function setSaveed(index,val){
    setArticlesStauts({index:index},{"saveed":val});
    if(val){
        $('#item_'+index).find('.badge-success,.badge-warning').removeClass('badge-warning').addClass('badge-success').html('已保存');
    }else{
        $('#item_'+index).find('.badge-success,.badge-warning').removeClass('badge-success').addClass('badge-warning').html('未保存');
    }
}

var definedArticle = {
    "aid":"",
    "id":"",
    "title":"",
    "summary":"",
    "content":"",
    "sort":"",
    "parentId":0,
    "sourceUrl":""
};



function addItem(vals,saveed){
    autoIndex++;
    var html = $('#itemTemplate').html();
    if(!vals) vals={};

    vals["index"] = autoIndex;
    if(!vals["imageUrl"]){
        vals["img"]='<p class="default-tip pull-right" style=" color: #AAAAAA;font-size: 14px; margin:8px 0; line-height: 70px; width:70px; background-color: #ECECEC;display: block;text-align: center;text-shadow: 0 1px 0 #FFFFFF;">缩略图</p>';
    }else{
        vals["img"]='<img class="pull-right" src="'+vals["imageUrl"]+'" width="70" height="70" style="margin:8px 0; line-height: 70px; width:70px;" />';
    }
    definedArticle.parentId = parentId;

    if(typeof vals['sort']=="undefined"){
        vals['sort'] = autoIndex;
    }

    $('#subItmes').append(templateParse(html,$.extend({},definedArticle,vals)));
    $('#formBox').append(templateParse($('#formTemplate').html(), $.extend({},definedArticle,vals)));
    $('.forms').css('display','none');
    var p = $('#item_'+autoIndex).position();
    $('#form_'+autoIndex).css({'margin-top':p['top'], 'display':''});
    initEditor('inp_content_'+autoIndex);

    articlesStauts.push({index:autoIndex,"saveed":false,"isRoot":false});
    if(saveed){
        setSaveed(vals["index"],true);
    }

    $('#form_'+autoIndex).find("input,select,textarea").bind("change", function(){
        setSaveed(vals["index"],false);
    });

    $('#form_'+autoIndex).find(".title").bind("change keyup", function(){
        var index = $(this).parents('.forms').find('.actionIndex').val();
        $('#item_'+index).find('.title').text($(this).val());
    });
    $('#form_'+autoIndex).find(".img_url").bind("change", function(){
        var index = $(this).parents('.forms').find('.actionIndex').val();
        var objUrl = getFileInputURL(this.files[0]);
        if (objUrl) {
            if(index==0){
                $('#item_'+index).find(".img_url").html('<div class="reveal news_first col-md-12" style="background-image:url(\''+objUrl+'\')"></div>');
            }else{
                $('#item_'+index).find(".img_url").html('<img class="pull-right" style="margin:8px 0; height: 70px; width:70px;" src="'+objUrl+'" />');
            }
        }
    });

    resetEvent();
}



function editItem(obj){
    var index = $(obj).parents('.item').data('index');
    var id = $(obj).parents('.item').data('id');

    $('.forms').css('display','none');
    var p = $('#item_'+index).position();
    if(index==0){
        p['top'] = 0;
    }
    $('#form_'+index).css({'margin-top':p['top'], 'display':''});
    $('#item_'+index).find('.options i').css('margin-top',($('#item_'+index).height()-$('#item_'+index).find('.options i').height())/2);
}

function removeItem(obj){

    if(!confirm("您确定要删除该文章吗？")){
        return ;
    }

    var index = $(obj).parents('.item').data('index');
    var id = $("#form_"+index).find('.aid').val();
    var parentId = $("#form_"+index).find('.parentId').val();
    if (!parentId) {
    	parentId=0;
    }

    if(id){
        $.getJSON('articles_modify?action=delete&parentId='+parentId+'&id='+id+'&actionIndex='+index,function(data){
            if(data.success){
                var index = data.actionIndex;
                $('#item_'+index).remove();
                $('#form_'+index).remove();
            }else{
                alert('删除失败,请重试!');
            }
        });
    }else{
        $('#item_'+index).remove();
        $('#form_'+index).remove();
    }

}

function templateParse(html,vals){
    for(var t in vals){
        html = html.replace(new RegExp("{"+t+"}", "gi"), vals[t]);
    }
    return html;
}

function resetEvent(){

    $(".item").unbind('mouseenter').mouseenter(function(){
        $(this).find('.options').css({"display":"inline","height":$(this).height()});
    }).unbind('mouseleave').mouseleave(function(){
        $(this).find('.options').css({"display":"none"});
    });

    $(".item .edit").unbind('click').click(function(){
        editItem(this);
    });
    $(".item .remove").unbind('click').click(function(){
        removeItem(this);
    });
}

function initEditor(id){
    CKEDITOR.replace(id);
    CKEDITOR.on( 'instanceReady', function(event) {
        event.editor.removeMenuItem('image');
    });
}

//function getRealContent() {
//    return CKEDITOR.instances.inp_content.getData();
//}

setInterval(fillAndCheck,100);
function fillAndCheck() {
    try{
        for(var key in articlesStauts){
            if(CKEDITOR.instances && CKEDITOR.instances["inp_content_"+articlesStauts[key].index]){
                $("#cke_inp_content_"+articlesStauts[key].index+" img").removeAttr("style");
                $("#cke_inp_content_"+articlesStauts[key].index+" img").attr("width", "100%");
                $("#cke_inp_content_"+articlesStauts[key].index+" img").removeAttr("height");
                if($("#inp_content_"+articlesStauts[key].index).text()!=CKEDITOR.instances["inp_content_"+articlesStauts[key].index].getData()){
                    $("#inp_content_"+articlesStauts[key].index).text(CKEDITOR.instances["inp_content_"+articlesStauts[key].index].getData());
                    $("#inp_content_"+articlesStauts[key].index).text();
                    $("#inp_content_"+articlesStauts[key].index).change();
                }
            }
        }
    }catch(e){}
    return true;
}

function checkPost(index){
    if($('#form_'+index).find('.parentId').val()==0){
        alert("请先保存第一条!");
        return false;
    }
    return true;
}

//建立一個可存取到該file的url
function getFileInputURL(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}

$(document).ready(function(){
    initEditor("inp_content_0");
    $('#form_0').find("input,select,textarea").bind("change", function(){
        setSaveed(0,false);
    });

    if(articleInfo==null){
        addItem();
    }else{
    	setSaveed(0,true);
        for(var key in articleInfo.subArticles){
            if(articleInfo.subArticles[key].status!=4){
                addItem(articleInfo.subArticles[key],true);
            }
        }
    }
    $('#form_0').find("input,select,textarea").bind("change", function(){
        setSaveed(0,false);
    });
    $('.item').eq(0).find('.edit').click();


    $('#form_0').find(".title").bind("change keyup", function(){
        var index = $(this).parents('.forms').find('.actionIndex').val();
        $('#item_'+index).find('.title').text($(this).val());
    });
    $('#form_0').find(".img_url").bind("change", function(){
        var index = $(this).parents('.forms').find('.actionIndex').val();
        var objUrl = getFileInputURL(this.files[0]);
        if(objUrl) {
            $('#item_'+index).find(".img_url").html('<div class="reveal news_first col-md-12" style="background-image:url(\''+objUrl+'\')"></div>');
        }
    });
});
function prevArticleMore(obj){
    var title=$(obj).parents('.forms').find('.title').val();
    var content=$(obj).parents('.forms').find('.content').val();
    var index=$(obj).parents('.forms').find('.actionIndex').val();

    var img = $('#item_'+index).find('.img_url img').attr('src');
    prevArticle($('#inp_title').val(), content, img);
}