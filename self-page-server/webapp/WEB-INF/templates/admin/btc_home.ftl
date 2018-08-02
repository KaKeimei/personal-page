<#import "../frame_new.ftl" as main>
<#assign html_other_script in main>
<script>
</script>
</#assign>
<#assign ftitle = "SPV钱包"/>
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
    <divw class="box">
        <div class="box-header with-border">
            <h3 class="box-title">钱包信息</h3>

            <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <div class="btn-group">
                    <button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-wrench"></i></button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="bitcoin!send">付款</a></li>
                        <li><a href="bitcoin!receive">收款</a></li>
                        <li><a href="bitcoin!backup">备份</a></li>
                        <li class="divider"></li>
                        <li><a href="bitcoin!home">钱包首页</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
            <div class="row">
                <div class="col-md-8">

                    <div class="info-box">
                        <span class="info-box-icon bg-red"><i class="fa fa-bitcoin"></i></span>

                        <div class="info-box-content text-ce">
                            <h4 class="info-box-text">收款地址</h4>
                            <h3 class="info-box-number">${address!}</h3>
                        </div>
                        <!-- /.info-box-content -->
                    </div>
                </div>
                <div class="col-md-2 ">

                        <a href="bitcoin!send" class="btn btn-app pull-right">
                            <i class="fa fa-upload"></i> 付钱
                        </a>
                </div>
                <div class="col-md-2 ">

                    <a href="bitcoin!receive" class="btn btn-app ">
                        <i class="fa fa-download"></i> 收钱
                    </a>
                </div>

            </div>
            <!-- /.row -->
        </div>
        <!-- ./box-body -->
        <div class="box-footer">
            <div class="row">
                <div class="col-sm-3 col-xs-6">
                    <div class="description-block border-right">
                        <h4>  账户余额</h4>
                        <span class="description-text">${balance!}</span>
                    </div>
                    <!-- /.description-block -->
                </div>
                <!-- /.col -->
                <div class="col-sm-3 col-xs-6">
                    <div class="description-block border-right">
                        <h4>  交易笔数</h4>
                        <span class="description-text">#{transactionCount!}</span>
                    </div>
                    <!-- /.description-block -->
                </div>
                <div class="col-sm-3 col-xs-6">
                    <div class="description-block border-right">
                        <h4 >创建时间</h4>
                        <span class="description-text">${createTime?string("yyyy-MM-dd HH:mm:ss")!}</span>
                    </div>
                    <!-- /.description-block -->
                </div>
                <!-- /.col -->
                <div class="col-sm-3 col-xs-6">
                    <div class="description-block border-right">
                        <h4 >最近交易</h4>
                        <span class="description-text">${lastTxTime?string("yyyy-MM-dd HH:mm:ss")!}</span>
                    </div>
                    <!-- /.description-block -->
                </div>
                <!-- /.col -->

            </div>
        </div>
        <!-- /.box-footer -->
    </divw>

    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">交易记录</h3>

            <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
            </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
            <div class="table-responsive">
                <table class="table no-margin">
                    <thead>
                    <tr>
                        <th>txid</th>
                        <th>类型</th>
                        <th>总额</th>
                        <th>手续费</th>
                        <th>交易时间</th>
                        <th>确认数</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list transactions as transaction>

                        <tr>
                            <td><a target="_blank" href="https://live.blockcypher.com/btc-testnet/tx/${transaction.txid!}">${transaction.txid?substring(0,15)}...</a></td>
                            <td>
                                <#if transaction.type.value() = 'income'>
                                    <span class="label label-success">收入</span>
                                <#else >
                                    <span class="label label-danger">支出</span>
                                </#if>
                            </td>
                            <td>${transaction.totalValue!}</td>
                            <td>${transaction.fees!}</td>
                            <td>${transaction.updateDate?string("yyyy-MM-dd HH:mm:ss")!}</td>
                            <td>#{transaction.confirmation!}</td>
                        </tr>
                    </#list>


                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <a href="bitcoin!receive" class="btn btn-sm btn-info btn-flat pull-left">去收钱</a>
            <a href="bitcoin!send" class="btn btn-sm btn-default btn-flat pull-right">去付钱</a>
        </div>
        <!-- /.box-footer -->
    </div>
</div>

</@main.page>