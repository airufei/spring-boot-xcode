<!DOCTYPE html>
<html>
<head>
  	<#import "/common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
    <link rel="stylesheet" href="/adminlte/plugins/iCheck/square/blue.css">
	<title>${I18n.admin_name}</title>
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a><b>X</b>CODE</a>
		</div>
		<form id="loginForm" method="post" >
			<div class="login-box-body">
				<p class="login-box-msg">${I18n.admin_name}</p>
				<div class="form-group has-feedback">
	            	<input type="text" name="phone" class="form-control" placeholder="请输入手机号"  maxlength="11" >
	            	<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
	          	<div class="form-group has-feedback">
	            	<input type="password" name="password" class="form-control" placeholder="${I18n.login_password_placeholder}"  maxlength="20" >
	            	<span class="glyphicon glyphicon-lock form-control-feedback"></span>
	          	</div>
				<div class="row">
					<div class="col-xs-8">
		              	<div class="checkbox icheck">
		                	<label>
		                  		<input type="checkbox" name="ifRemember" >${I18n.login_remember_me}
		                	</label>
						</div>
		            </div><!-- /.col -->
		            <div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">${I18n.login_btn}</button>
					</div>
				</div>
			</div>
		</form>
	</div>
<@netCommon.commonScript />
<script src="/plugins/jquery/jquery.validate.min.js"></script>
<script src="/adminlte/plugins/iCheck/icheck.min.js"></script>
<script src="/js/login.1.js"></script>

</body>
</html>
