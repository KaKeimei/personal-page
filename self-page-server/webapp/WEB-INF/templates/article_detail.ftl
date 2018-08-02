<#import "frame_new.ftl" as main>

<#assign html_other_script in main>
	
</#assign>

<@main.page title=article.title currentPage='novel' activeTag=3 breadCrumb=breadCrumb>
	<div class="row">
        <div class="col-md-offset-1 col-md-10">
            <p class="card-text">${article.data!}</p>
            <a class="btn btn-danger" href="/web/modify?aid=#{article.id}">编辑此文章</a>
		</div>
	</div>

</@main.page>