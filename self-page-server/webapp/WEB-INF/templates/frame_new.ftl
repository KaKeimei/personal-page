<#macro page title currentPage activeTag breadCrumb>
<!DOCTYPE html>
<html>
<#--不要在子ftl中设置style，否则自动提示无法解析-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${title!'何启明的个人随想'}</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${resUrl!}/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${resUrl!}/fonts/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <!--link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css"-->
    <!-- Theme style -->
    <link rel="stylesheet" href="${resUrl!}/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${resUrl!}/dist/css/skins/skin-red-light.css">
    <!-- extra style-->
    <style>
        body, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6{
            font-family: 'Microsoft Yahei'
        }
    </style>

    <script type="text/javascript" src="${resUrl!}/js/vue.js"></script>
    <script src="${resUrl!}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${resUrl!}/js/bootstrap.min.js"></script>
    <script src="${resUrl!}/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <script src="${resUrl!}/plugins/fastclick/fastclick.js"></script>
    <script src="${resUrl!}/dist/js/app.min.js"></script>
    <script src="${resUrl!}/dist/js/demo.js"></script>
    <script src='${resUrl!}/js/jquery.nicescroll.min.js'></script>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>


</head>
<!-- ADD THE CLASS layout-boxed TO GET A BOXED LAYOUT -->
<body class="hold-transition skin-red-light layout-boxed sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <header class="main-header">
        <!-- Logo -->
        <a href="/" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>Charles</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>何启明</b>个人简历</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>


            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- Messages: style can be found in dropdown.less-->


                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="${resUrl!}/img/thumb.jpg" class="user-image" alt="User Image">
                            <span class="hidden-xs">Charles Ming</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">

                                    <img src="../../img/thumb.jpg" class="img-circle" alt="User Image">

                                    <p>
                                        Charles Ming  - Web Developer
                                        <small>Java web developer since April. 2015</small>
                                    </p>
                            </li>
                            <!-- Menu Body -->
                            <#--<li class="user-body">-->

                            <#--</li>-->
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="/" class="btn btn-default btn-flat">个人资料</a>
                                </div>
                                <div class="pull-right">
                                    <a href="/web/publish" class="btn btn-default btn-flat">写文章</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                </ul>
            </div>
        </nav>
    </header>

    <!-- =============================================== -->

    <!-- Left side column. contains the sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- Sidebar user panel -->
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="${resUrl!}/img/thumb.jpg" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info">
                    <p>java程序员</p>
                    <a href="/"><i class="fa fa-circle text-success"></i> 个人简介</a>
                </div>
            </div>
            <!-- search form -->
            <!--form action="#" method="get" class="sidebar-form">
              <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                    <span class="input-group-btn">
                      <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                      </button>
                    </span>
              </div>
            </form-->
            <!-- /.search form -->
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li class="header">快速导航</li>


                <li>
                    <a href="${request.contextPath}/web/article!list">
                        <i class="fa fa-book"></i> <span>全部文章</span>
                        <span class="pull-right-container">
                        </span>
                    </a>
                </li>

                <#if articleTypes ??>
                    <#list articleTypes as type>
                        <li>
                            <a href="${request.contextPath}/web/article!list?cate=${type.getValue()!}">
                                <i class="fa fa-paperclip"></i> <span>${type.desc!}</span>
                                <span class="pull-right-container">
                        </span>
                            </a>
                        </li>
                    </#list>
                </#if>

                <li>
                    <a href="${request.contextPath}/web/bitcoin!home">
                        <i class="fa fa-bitcoin"></i> <span>比特币钱包例程</span>
                        <span class="pull-right-container">
                        </span>
                    </a>
                </li>




            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- =============================================== -->

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                ${title!}
                <#--small>Blank example to the boxed layout</small-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="/web/index"><i class="fa fa-dashboard"></i> 全部文章</a></li>
                <#if breadCrumb ?? && breadCrumb != "" && breadCrumb.secondName??>
                    <li><a href="${breadCrumb.secondUrl!}">${breadCrumb.secondName!}</a></li>
                </#if>
                <#if breadCrumb ?? && breadCrumb != "" && breadCrumb.thirdName??>
                    <li class="active">${breadCrumb.thirdName!}</li>
                </#if>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <#nested>
        </section>
        <!-- /.content -->
        <button class="btn btn-inverse skin-red-light" id="back-to-top" style="position:fixed;opacity:0.7;margin-top: -1px; text-transform: uppercase;right:24px;bottom:27px"><i class="fa fa-arrow-up"></i></button>
    </div>
    <!-- /.content-wrapper -->

    <!-- back-to-top-->

    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 2.3.8
        </div>
        <strong>Copyright &copy; 2014-2018 </strong> All rights
        reserved.
    </footer>

    <!-- Control Sidebar --> <!-- /.control-sidebar -->

    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->





<script type='text/javascript' src='${resUrl!}/js/enquire.js'></script>
<script type='text/javascript' src='${resUrl!}/js/jquery.cookie.js'></script>
<script type='text/javascript' src='${resUrl!}/js/jquery.touchSwipe.min.js'></script>
<script type='text/javascript' src='${resUrl!}/js/jquery.nicescroll.min.js'></script>
<script type='text/javascript' src='${resUrl!}/js/application.js'></script>
<script type='text/javascript' src='${resUrl!}/plugins/bootbox/bootbox.min.js'></script>
<script type='text/javascript' src='${resUrl!}/plugins/form-toggle/toggle.min.js'></script>


${html_other_script!}

</body>
</html>


</#macro>