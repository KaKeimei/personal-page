<#import "../frame_new.ftl" as main>

<#assign html_other_script in main>
	
</#assign>

<@main.page title="激情小说" currentPage='novel' activeTag=3 breadCrumb=breadCrumb>
    <style>
        .col-xs-12{
            padding:0;
        }
    </style>

	<div class="card card-block">
		<h4 class="card-title">${novel.title?trim}</h4>
		<p class="card-text">${novel.content!}</p>
	</div>


	<nav>
		<ul class="pager">
	  		<#if !isFirst?? >
				<li class=""><a href="/web/novelItem?id=#{novel.id + 1}">上一篇</a></li>
			</#if>
            <li class=""><a href="/web/novelItem?id=#{novel.id - 1}">下一篇</a></li>
		</ul>
	</nav>

</@main.page>