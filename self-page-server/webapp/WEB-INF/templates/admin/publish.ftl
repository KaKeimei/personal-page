
<#import "../frame_new.ftl" as main>

<#assign html_other_script in main>
    <script type="text/javascript" src="/plugins/ckeditor_4.5.7_full/ckeditor/ckeditor.js"></script>
<script>

    $(function(){
        $('#myForm').submit(function(){
            var ftitle = $("#title").val()
            var fdata = CKEDITOR.instances["f-detail"].getData()
            $.ajax({
                url:"/web/doPublish",
                data:{
                    title: ftitle,
                    data: fdata
                },
                dataType:"text",
                error:function(data){
                    window.location.href="/web/index";
                },
                success:function(data){
                    window.location.href="/web/index";
                }
            });
        });
    })
</script>
</#assign>

<@main.page title="发布新文章" currentPage='publish' activeTag=11 breadCrumb="">
<style>
    .col-xs-12{
        padding:0;
    }
</style>

<form id="myForm" action="/web/doPublish" method="post" class="form-horizontal" style="">

    <div class="col-md-12">
        <div class="form-group">

            <div class="input-group">
                <select title="cate type" class="form-control" name="cateType" value="">
                    <#if types??>
                        <#list types as cateType>
                            <option value ="${cateType.getValue()}">${cateType.desc}</option>
                        </#list>
                    </#if>

                </select>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon">标题</span>
                <input id="title" style="height:44px;" type="text" class="form-control" name="title" placeholder="文章标题" />
            </div>
        </div>

    <#--<div class="form-group" >-->
    <#--<span style="height:44px;width:50%" class="pull-left "  ><img src="/web/captcha" style="width:100%;height:44px" alt="验证码" height="20" align="bottom" style="cursor:pointer;" title="看不清可单击图片刷新" onclick="this.src='/web/captcha?d='+Math.random();" /></span>-->
    <#--<input style="height:44px;width:45%" class="form-control  pull-right"  name="captcha" placeholder="输入验证码" />-->
    <#--</div>-->

    <#--<#if failMsg??>-->
    <#--<div class="has-error">-->
    <#--<ul class="help-block list-unstyled"><li><i class="fa fa-warning"></i>${failMsg!}</li></ul>-->
    <#--</div>-->
    <#--</#if>-->
    <#--<div class="pull-right" style="margin-top:20px">-->
    <#--<input type="submit" class="btn btn-primary-outline" value="登录" />-->
    <#--</div>-->


    </div>
    <div class="col-md-12">
        <div class="form-group">
            <div class="input-group">
                <textarea rows="30" id="f-detail" class="form-control ckeditor" name="data" placeholder="文章内容" ></textarea>
            </div>
        </div>
    </div>
    <input type="submit" class="btn btn-primary-outline" value="提交" />
</form>



</@main.page>