<!DOCTYPE html>
<html>
<head>
    <#import "/common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet" href="/adminlte/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <title>代码生成方案管理</title>
</head>
<body class="hold-transition skin-blue sidebar-mini  <#if cookieMap?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "${className}" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>代码生成方案</h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-xs-3">

                    <div class="input-group">
                        <span class="input-group-addon">名称</span>
                        <input type="text" class="form-control" id="name" autocomplete="on">
                    </div>
                </div>
                <div class="col-xs-3">

                    <div class="input-group">
                        <span class="input-group-addon">表名</span>
                        <input type="text" class="form-control" id="tableName" autocomplete="on">
                    </div>
                </div>
                <div class="col-xs-3">

                    <div class="input-group">
                        <span class="input-group-addon">备注信息</span>
                        <input type="text" class="form-control" id="remark" autocomplete="on">
                    </div>
                </div>
                <div class="col-xs-1">
                    <button class="btn btn-block btn-info" id="searchBtn">搜索</button>
                </div>

                <div class="col-xs-2">
                    <button class="btn btn-block btn-success add" type="button">新增</button>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <table id="codeScheme_table" class="table table-striped table-hover table-condensed"
                                   width="100%" style="white-space: nowrap;">
                                <thead>
                                <tr>
                                    <th name="id">编号</th>
                                    <th name="name">名称</th>
                                    <th name="category">分类</th>
                                    <th name="packageName">包路径</th>
                                    <th name="moduleName">模块名</th>
                                    <th name="subModuleName">访问路径</th>
                                    <th name="functionName">功能名</th>
                                    <th name="functionNameSimple">功能名（简写）</th>
                                    <th name="functionAuthor">功能作者</th>
                                    <th name="tableName">表名</th>
                                    <th name="tableId">表编号</th>
                                    <th name="updateTimestr">更新时间</th>
                                    <th name="remark">备注信息</th>
                                    <th name="modulePageName">页面模块</th>
                                    <th name="subPageName">子模块</th>
                                    <th name="operate">操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                                <tfoot></tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!-- footer -->
    <@netCommon.commonFooter />
</div>

<!-- job新增.模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document" id="modalDialog">
        <div class="modal-content">
            <div class="modal-header">
                <table width="99%">
                    <tr>
                        <td style="width: 15%">
                            <h4 class="modal-title">代码生成方案</h4>
                        </td>
                        <td style="width: 77%">
                        </td>
                        <td style="width: 8%">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-body" style="overflow-y: scroll;height: 500px">
                <form class="form-horizontal form" role="form">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">名称<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="name" placeholder="用户代码模型"
                                                      maxlength="200"></div>
                    </div>
                    <div class="form-group">
                        <label for="category" class="col-sm-2 control-label">选择生成模型<font color="red">*</font></label>
                        <div class="col-sm-10"><select id="codeModeList" name="category"
                                                       class="form-control selectpicker">
                                <option value="">--选择生成模型--</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="packageName" class="col-sm-2 control-label">包路径<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="packageName"
                                                      placeholder="com.hiscene"
                                                      maxlength="500"></div>
                    </div>
                    <div class="form-group">
                        <label for="subPageName" class="col-sm-2 control-label">子模包<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="subPageName"
                                                      placeholder="api"
                                                      maxlength="50"></div>
                    </div>
                    <div class="form-group">
                        <label for="moduleName" class="col-sm-2 control-label">模块名<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="moduleName"
                                                      placeholder="user"
                                                      maxlength="30"></div>
                    </div>
                    <div class="form-group">
                        <label for="subModuleName" class="col-sm-2 control-label">访问路径<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="subModuleName"
                                                      placeholder="user"
                                                      maxlength="30"></div>
                    </div>

                    <div class="form-group">
                        <label for="functionName" class="col-sm-2 control-label">功能名<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="functionName"
                                                      placeholder="用户信息"
                                                      maxlength="500"></div>
                    </div>
                    <div class="form-group">
                        <label for="functionAuthor" class="col-sm-2 control-label">功能作者<font
                                    color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="functionAuthor"
                                                      placeholder="rufei"
                                                      maxlength="100"></div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">选择配置表<font color="red">*</font></label>
                        <div class="col-sm-10"><select id="codeTableList" name="tableName"
                                                       class="form-control selectpicker">
                                <option value="">--请选择--</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remark" class="col-sm-2 control-label">代码路径<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="path"
                                                      value="/opt/hiscene/code" placeholder="/opt/hiscene/code"
                                                      maxlength="255"></div>
                    </div>
                    <div class="form-group">
                        <label for="modulePageName" class="col-sm-2 control-label">页面模块<font
                                    color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="modulePageName"
                                                      placeholder="user"
                                                      maxlength="100"></div>
                    </div>

                    <hr>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button type="submit" class="btn btn-primary">保存并生成代码</button>
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">${I18n.system_cancel}</button>
                            <input type="hidden" name="id">
                        </div>
                        <br>
                        <a href="http://106.58.211.47:8090/pages/viewpage.action?pageId=12722770" target="_blank">使用说明文档</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/jquery/jquery.validate.min.js"></script>
<!-- moment -->
<script src="/adminlte/plugins/daterangepicker/moment.min.js"></script>
<script src="/adminlte/plugins/jQuery/jquery-ui-1.9.2.custom.min.js"></script>
//拖拽
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="/js/codeScheme.index.js"></script>

<script>
    $(document).ready(function () {
        $("#modalDialog").draggable();		        //为模态对话框添加拖拽，拖拽区域只在顶部栏
        $("#modalDialog").draggable({handle: ".modal-header"});//为模态对话框添加拖拽
        $("#addModal").css("overflow", "hidden"); // 禁止模态对话框的半透明背景滚动
    })
</script>
</body>
</html>