<!DOCTYPE html>
<html>
<head>
    <#import "/common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet" href="/adminlte/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <title>数据表信息管理</title>
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
            <h1>数据表信息</h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-xs-3">

                    <div class="input-group">
                        <span class="input-group-addon">表名称</span>
                        <input type="text" class="form-control" id="name" autocomplete="on">
                    </div>
                </div>
                <div class="col-xs-3">

                    <div class="input-group">
                        <span class="input-group-addon">表ID</span>
                        <input type="text" class="form-control" id="id" autocomplete="on">
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
                            <table id="codeTable_table" class="table table-striped table-hover table-condensed" width="100%" style="white-space: nowrap;">
                                <thead>
                                <tr>
                                    <th name="id">编号</th>
                                    <th name="name">名称</th>
                                    <th name="comments">描述</th>
                                    <th name="className">实体类名称</th>
                                    <th name="updateTimestr">更新时间</th>
                                    <th name="remark">备注信息</th>
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

<!-- 配置表信息 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document" id="modalDialog">
        <div class="modal-content">
            <div class="modal-header">
                <table width="99%">
                    <tr>
                        <td style="width: 15%">
                            <h4 class="modal-title">保存数据表信息</h4>
                        </td>
                        <td style="width: 77%">
                        </td>
                        <td style="width: 8%">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>


<!-- 弹出表列表 -->
<div class="modal fade" id="tableListModal" tabindex="-1" role="dialog" aria-labelledby="mytableModalLabel">
    <div class="modal-dialog modal-lg" role="document" id="modalDialog">
        <div class="modal-content">
            <div class="modal-header">
                <table width="99%">
                    <tr>
                        <td style="width: 15%">
                            <h4 class="modal-title">配置表</h4>
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
                        <label for="name" class="col-sm-2 control-label">选择数据表<font color="red">*</font></label>
                        <div class="col-sm-10"><select id="tableList" name="tableList"
                                                       class="form-control selectpicker"   onchange="getSingleTable(null)">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">名称<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="name" placeholder="请输入..."
                                                      maxlength="200"></div>
                    </div>
                    <div class="form-group">
                        <label for="comments" class="col-sm-2 control-label">描述<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="comments"
                                                      placeholder="请输入..."
                                                      maxlength="500"></div>
                    </div>
                    <div class="form-group">
                        <label for="className" class="col-sm-2 control-label">实体类名称<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="className"
                                                      placeholder="请输入..."
                                                      maxlength="100"></div>
                    </div>
                    <div class="form-group">
                        <label for="remark" class="col-sm-2 control-label">备注信息<font color="red">*</font></label>
                        <div class="col-sm-10"><input type="text" class="form-control" name="remark"
                                                      placeholder="请输入..."
                                                      maxlength="255"></div>
                    </div>
                    <hr>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button type="submit" class="btn btn-primary">${I18n.system_save}</button>
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">${I18n.system_cancel}</button>
                            <input type="hidden" name="id">
                        </div>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<!-- moment -->
<script src="/adminlte/plugins/daterangepicker/moment.min.js"></script>
<script src="/adminlte/plugins/jQuery/jquery-ui-1.9.2.custom.min.js"></script>
//拖拽
<script src="/js/codeTable.index.js"></script>

<script>
    $(document).ready(function () {
        $("#modalDialog").draggable();		        //为模态对话框添加拖拽，拖拽区域只在顶部栏
        $("#modalDialog").draggable({handle: ".modal-header"});//为模态对话框添加拖拽
        $("#addModal").css("overflow", "hidden"); // 禁止模态对话框的半透明背景滚动
    })

    //处理编辑页面的数据
    function getSingleTable(tableName) {
        if(tableName==null)
        {
            tableName=$("#tableList").selectpicker('val');
        }
        $.ajax({
            type: 'post',
            url: base_url + "/codeTable/getOneTable",
            data:{"tableName":tableName},
            dataType: "Json",
            success: function (data) {
                if (data != null) {
                    $("#tableListModal .form input[name='name']").val(data.name);
                    $("#tableListModal .form input[name='comments']").val(data.comments);
                    $("#tableListModal .form input[name='className']").val(data.className);
                    $("#tableListModal .form input[name='remark']").val(data.comments);
                    $("#tableListModal .form input[name='id']").val(data.id);
                }
            }
        })
    }
</script>
</body>
</html>