
<#import "../frame_new.ftl" as main>

<#assign html_other_script in main>
	
</#assign>

<@main.page title="注册新用户" currentPage='photo' activeTag=11 breadCrumb="">
<style>
	.col-xs-12{
		padding:0;
	}
</style>
		
			<form action="signup" method="post" class="form-horizontal" style="margin-bottom: 0px !important;">
				<input type="hidden" name="from" value="${ref!}" />
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                            <input style="height:44px;" type="text" class="form-control" name="passport" placeholder="邮箱，登录及账号找回" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                            <input style="height:44px;" type="text" class="form-control" name="name" placeholder="昵称" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                            <input style="height:44px;" type="password" class="form-control" name="pwd1" placeholder="密码" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                            <input style="height:44px;" type="password" class="form-control" name="pwd2" placeholder="确认密码" />
                        </div>
                    </div>

                    <div class="form-group" >
                        <span style="height:44px;width:50%" class="pull-left "  ><img src="/web/captcha" style="width:100%;height:44px" alt="验证码" height="20" align="bottom" style="cursor:pointer;" title="看不清可单击图片刷新" onclick="this.src='/web/captcha?d='+Math.random();" /></span>
                        <input style="height:44px;width:45%" class="form-control  pull-right"  name="captcha" placeholder="输入验证码" />
                    </div>

					<#if failMsg??>
                        <div class="has-error">
                            <ul class="help-block list-unstyled"><li><i class="fa fa-warning"></i>${failMsg!}</li></ul>
                        </div>
					</#if>
                    <div class="pull-right" style="margin-top:20px">
                        <input type="submit" class="btn btn-primary" value="注册" />
                    </div>
                </div>
                <div class="col-md-4"></div>
			</form>


</@main.page>