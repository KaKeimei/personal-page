<#import "../frame_new.ftl" as main>
<#import "../vue/novel_list_appender.ftl" as novelAppender>
<#assign html_other_script in main>
	<script>
	</script>
</#assign>

<@main.page title="激情小说" currentPage='novel' activeTag=3 breadCrumb=breadCrumb>

<div id="vueContainer">
	<div class="list-group">
      <#assign lastId= 0/>
	  <#list novelList as novel>
	  <a href="/web/novelItem?id=#{novel.id}" class="list-group-item">
          <div class="row">
              <div class="col-md-8 list-group-item-heading"><h4>${novel.title!}</h4></div>
              <div class="col-md-4 list-group-item-text "><h5>${novel.createTime?string("yyyy-MM-dd")}</h5></div>
          </div>
		  <#if !novel_has_next>
		      <#assign lastId=novel.id/>
		  </#if>
	  </a>
	  </#list>
    <novel-append-item v-for="(index, item) in items" v-bind:nid="item.id" v-bind:ntitle="item.title" v-bind:time-str="item.timeStr"></novel-append-item>
    <novel-load-more></novel-load-more>
	</div>
</div>

    <@novelAppender.page firstId=lastId/>
</@main.page>