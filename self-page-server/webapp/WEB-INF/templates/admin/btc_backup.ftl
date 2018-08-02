<#import "../frame_new.ftl" as main>
<#assign html_other_script in main>
<script>
</script>
</#assign>
<#assign ftitle = "SPV钱包-备份"/>
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
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title">钱包种子</h3>

            <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <div class="btn-group">
                    <button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-wrench"></i></button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="bitcoin!send">付款</a></li>
                        <li><a href="bitcoin!receive">收款</a></li>
                        <li class="divider"></li>
                        <li><a href="bitcoin!home">钱包首页</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
            <div class="row">
                <div class="col-md-10">

                    <div class="info-box">
                        <span class="info-box-icon bg-red"><i class="fa fa-lightbulb-o"></i></span>

                        <div class="info-box-content text-ce">
                            <h4 class="info-box-text">钱包种子</h4>
                            <h3 class="info-box-number">${seed!}</h3>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                </div>

                <div class="col-md-2 ">

                    <a href="bitcoin!home" class="btn btn-app">
                        <i class="fa fa-step-backward"></i> 返回钱包
                    </a>
                </div>


            </div>
            <!-- /.row -->
        </div>
    </div>


</div>

</@main.page>