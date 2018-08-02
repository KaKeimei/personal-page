<#macro pageul pagination url extra...>
	<#if extra?size gt 0>
		<#if extra?keys?seq_contains("noCount")>
			<#assign noCount=extra['noCount'] />
		<#else>
			<#assign noCount="0" />
		</#if>
	<#else>
		<#assign noCount="0" />
	</#if>
	<#if noCount=="0">
	<div class="row">
		
	</#if>
		<#if pagination.totalPageNum &gt; 0>
		<div class="col-xs-12" style="text-align:center">
			<ul class="pagination">
			<#if pagination.currentPage &gt; 1>
			  <li>
			  	<a href="${url}page=1" aria-label="Previous" style="padding-left:0rem;padding-right:0rem">
					<span aria-hidden="true" style="padding-left:10px;padding-right:10px">首页</span>
				</a>
			  </li>
			<#else>
				<li class="disabled">
					<a href="#" aria-label="Previous" style="padding-left:0rem;padding-right:0rem">
						<span aria-hidden="true" style="padding-left:10px;padding-right:10px">首页</span>
					</a>
				</li>
			</#if>
			<#if pagination.startPage &gt; 1>
				<#if pagination.startPage &lt; 5>
					<#if pagination.startPage = 2>
						<li class=""><a href="${url}page=1">...</a></li>
					<#else>
						<li class=""><a href="${url}page=2">...</a></li>
					</#if>	
				<#else>
					<li class=""><a href="${url}page=#{pagination.startPage-3}">...</a></li>
				</#if>
			</#if>
			<#list pagination.startPage..pagination.endPage as page>
			  <li <#if page=pagination.currentPage> class="active"</#if>><a href="${url}page=#{page}">#{page}</a></li>
			</#list>
			<#if pagination.endPage &lt; pagination.totalPageNum>
				<#if (pagination.totalPageNum-pagination.endPage)&lt;5>
					<#if (pagination.totalPageNum-pagination.endPage)=1>
						<li class=""><a href="${url}page=#{pagination.endPage+1}">...</a></li>
					<#else>
						<li class=""><a href="${url}page=#{pagination.endPage+2}">...</a></li>
					</#if>
				<#else>
					<li class=""><a href="${url}page=#{pagination.endPage+3}">...</a></li>
				</#if>
			</#if>
			<#if pagination.currentPage &lt; pagination.totalPageNum>
			  <li>
				<a href="${url}page=#{pagination.totalPageNum}" aria-label="Next" style="padding-left:0rem;padding-right:0rem">
					<span aria-hidden="true" style="padding-left:10px;padding-right:10px">尾页</span>
				</a>
			  </li>

			<#else>
				<li class="disabled">
					<a href="#" aria-label="Next" style="padding-left:0rem;padding-right:0rem">
						<span aria-hidden="true" style="padding-left:10px;padding-right:10px">尾页</span>
					</a>
				</li>
			</#if>
			</ul>
		</div>
		</#if>
	<#if noCount=="0">
	</div>
	</#if>
</#macro>