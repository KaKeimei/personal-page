<#import "../frame_new.ftl" as main>
<#assign html_other_script in main>
<script>
</script>
</#assign>
<#assign ftitle = "SPV钱包-发送"/>
<#assign fBreadCrumb = ""/>
<#assign fcate = ""/>

<#if desc??>
    <#assign ftitle = desc/>
    <#assign fBreadCrumb = breadCrumb/>
    <#assign fcate = cate/>
<#else>
</#if>

<@main.page title=ftitle currentPage='' activeTag=3 breadCrumb=fBreadCrumb>

<div id="vueContainer">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">发送比特币</h3>
        </div>
        <!-- /.box-header -->
        <!-- form start -->
        <div class="form-horizontal">
            <div class="box-body">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">发往地址</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="address" placeholder="接收方的比特币地址...">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label" >金额</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="amount" placeholder="发送的比特币金额如： 0.0001">
                    </div>
                </div>

            </div>
            <!-- /.box-body -->
            <div class="box-footer">
                <a href="/web/bitcoin!home" class="btn btn-default">取消</a>
                <button type="submit" class="btn btn-info pull-right" id="sendTx">发送</button>
            </div>
            <!-- /.box-footer -->
        </div>
    </div>
    <div id="successAlert" class="alert alert-success alert-dismissible hide">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4><i class="icon fa fa-check"></i> 信息</h4>
        交易已经成功发送并广播...
    </div>
    <div id="failAlert" class="alert alert-danger alert-dismissible hide">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4><i class="icon fa fa-check"></i> 信息</h4>
        交易失败...
    </div>
    <div id="waitAlert" class="alert alert-warning alert-dismissible hide">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4><i class="icon fa fa-check"></i> 信息</h4>
        交易正在发送，请耐心等待...
    </div>
</div>

<script>
$("#sendTx").click(function() {
    var address = $("#address").val();
    var amount = $("#amount").val();
    $("#waitAlert").removeClass("hide");
    $.ajax({
        type: "post",
        url: "bitcoin!doSend",
        data: {address:address, amount:amount},
        dataType: "json",

        success: function(data){
            $("#waitAlert").addClass("hide");
            if (data.result == "ok") {
                $("#successAlert").removeClass("hide");
            }else {
                $("#failAlert").removeClass("hide");
            }
            window.setTimeout(function () {
                window.location.href = "bitcoin!home";
            }, 2000);
        }

    });
})
</script>

</@main.page>