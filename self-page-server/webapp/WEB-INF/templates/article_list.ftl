<#import "frame_new.ftl" as main>
<#import "vue/novel_list_appender.ftl" as novelAppender>
<#assign html_other_script in main>
	<script>
	</script>
</#assign>
<#assign ftitle = "何启明的全部文章"/>
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
	<div class="list-group">
      <#assign lastId= 0/>
	  <#list articles as article>
	  <a href="/web/article!detail?aid=#{article.id}" class="list-group-item">
          <div class="row">
              <div class="col-md-8 list-group-item-heading"><h4>${article.title!}</h4></div>
              <div class="col-md-3 list-group-item-text "><h5>${article.createTime?string("yyyy-MM-dd")}</h5></div>
          </div>
		  <#if !article_has_next>
			  <#assign lastId=article.createTime?long/>
		  </#if>
	  </a>
	  </#list>
    <novel-append-item v-for="(index, item) in items" v-bind:nid="item.id" v-bind:ntitle="item.title" v-bind:time-str="item.timeStr"></novel-append-item>
    <novel-load-more></novel-load-more>
	</div>
</div>

    <@novelAppender.page firstId=lastId cate=fcate/>
</@main.page>