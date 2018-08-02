
<#import "/admin_frame.ftl" as main>

<#assign html_other_script in main>
	
</#assign>

<@main.page title="金币不足" currentPage='photo' activeTag=11>
<style>
	.col-xs-12{
		padding:0;
	}
</style>

			<div class="row">
				<div class="alert alert-danger">您的金币余额不足</div>
			</div>

</@main.page>