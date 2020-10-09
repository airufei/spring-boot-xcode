<!DOCTYPE html>
<html>
<head>
    <#import "/common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet" href="/adminlte/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <title>表字段信息管理</title>
    <style>
        tbody {
            display: inline-block;
            height: 450px;
            overflow-y: scroll;
        }

        th {
            display: inline-block;
            width: 150px;
        }

        td {
            display: inline-block;
            width: 150px;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini  <#if cookieMap?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "del" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box-body" style="overflow-x: scroll;">
                        <form class="form-horizontal form" role="form" id="tableColListModal">
                            <table id="codeTableColumn_table" class="table table-striped table-hover table-condensed"
                                   style="white-space: nowrap;">
                                <thead>
                                <tr>
                                    <th name="javaField">JAVA字段名</th>
                                    <th name="isList">是否列表字段</th>
                                    <th name="isQuery">是否查询字段</th>
                                    <th name="comments">字段备注</th>
                                    <th name="isEditpage">页面编辑字段</th>
                                    <th name="isSort">是否排序</th>
                                    <th name="javaType">JAVA类型</th>
                                    <th name="showType">编辑页控件</th>
                                    <th name="tableName">表名称</th>
                                    <th name="name">数据库字段</th>
                                    <th name="jdbcType">类型</th>
                                </tr>
                                </thead>

                                <tbody>
                                <#list colist as  li>
                                    <tr>
                                        <td name="javaField">
                                            <input type="text" name="javaField"
                                                   value="${li.javaField}"/>
                                        </td>
                                        <td name="isList"><select name="isList" class="form-control"
                                                                  style="width:105px">
                                                <option value="1" <#if li.isList?? && li.isList == "1">selected</#if>
                                                        title="是">是
                                                </option>
                                                <option value="0" <#if li.isList?? && li.isList == "0">selected</#if>
                                                        title="否">否
                                                </option>
                                            </select>
                                        </td>
                                        <td name="isQuery"><select name="isQuery" class="form-control"
                                                                   style="width:105px">
                                                <option value="0" <#if li.isQuery?? && li.isQuery == "0">selected</#if>
                                                        title="否">否
                                                </option>
                                                <option value="1" <#if li.isQuery?? && li.isQuery == "1">selected</#if>
                                                        title="是">是
                                                </option>
                                            </select></td>
                                        <td name="comments"><input type="text" name="comments" value="${li.comments}"/>
                                        </td>
                                        <td name="isEditpage"><select name="isEditpage"
                                                                      class="form-control"
                                                                      style="width:105px">
                                                <option value="0"
                                                        <#if li.isEditpage?? && li.isEditpage == "0">selected</#if>
                                                        title="否">否
                                                </option>
                                                <option value="1"
                                                        <#if li.isEditpage?? && li.isEditpage == "1">selected</#if>
                                                        title="是">是
                                                </option>
                                            </select>
                                        </td>
                                        <td name="isSort"><select name="isSort" class="form-control"
                                                                  style="width:105px">
                                                <option value="0" <#if li.isSort?? && li.isSort == "0">selected</#if>
                                                        title="否">否
                                                </option>
                                                <option value="1" <#if li.isSort?? && li.isSort == "1">selected</#if>
                                                        title="是">是
                                                </option>
                                            </select>
                                        </td>
                                        <td name="javaType"><select name="javaType" class="form-control"
                                                                    style="width:105px">

                                                <option value="java.math.BigDecimal"
                                                        <#if li.javaType?? && li.javaType == "java.math.BigDecimal">selected</#if>
                                                        title="java.math.BigDecimal">BigDecimal- ${li.javaType}
                                                </option>
                                                <option value="java.lang.String"
                                                        <#if li.javaType?? && li.javaType == "java.lang.String">selected</#if>
                                                        title="java.lang.String">String
                                                </option>
                                                <option value="java.lang.Long"
                                                        <#if li.javaType?? && li.javaType == "java.lang.Long">selected</#if>
                                                        title="java.lang.Long">Long
                                                </option>
                                                <option value="java.lang.Integer"
                                                        <#if li.javaType?? && li.javaType == "java.lang.Integer">selected</#if>
                                                        title="java.lang.Integer">Integer
                                                </option>
                                                <option value="java.util.Date"
                                                        <#if li.javaType?? && li.javaType == "java.util.Date">selected</#if>
                                                        title="java.util.Date">DateTime
                                                </option>
                                            </select>
                                        </td>
                                        <td name="showType"><select name="showType" class="form-control"
                                                                    style="width:105px">
                                                <option value="input"
                                                        <#if li.showType?? && li.showType == "input">selected</#if>
                                                        title="input">input
                                                </option>
                                                <option value="select"
                                                        <#if li.showType?? && li.showType == "select">selected</#if>
                                                        title="select">select
                                                </option>
                                                <option value="dateBox"
                                                        <#if li.showType?? && li.showType == "dateBox">selected</#if>
                                                        title="dateBox">dateBox
                                                </option>
                                                <option value="redioButton"
                                                        <#if li.showType?? && li.showType == "redioButton">selected</#if>
                                                        title="redioButton">redioButton
                                                </option>
                                            </select>
                                        </td>
                                        <td name="tableName"><input type="text" name="tableName" value="${li.tableName}"
                                                                    readonly="readonly" style="background:#CCCCCC"/>
                                        </td>
                                        <td name="name"><input type="text" name="name" value="${li.name}"
                                                               readonly="readonly" style="background:#CCCCCC"/></td>
                                        <td name="jdbcType"><input type="text" name="jdbcType" value="${li.jdbcType}"
                                                                   readonly="readonly" style="background:#CCCCCC"/></td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                            <div style="text-align: center;margin-top: 30px">
                                <button type="submit" class="btn btn-primary">${I18n.system_save}</button>&nbsp&nbsp&nbsp
                                <button type="button" class="btn btn-default" onclick="goHome()"
                                        data-dismiss="modal">${I18n.system_cancel}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

    </div>
    <!-- footer -->
    <@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/jquery/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<!-- moment -->
<script src="/adminlte/plugins/daterangepicker/moment.min.js"></script>
<script src="/js/codeTableColumn.index.js"></script>
<script>
    function goHome() {
        var url = base_url + "/codeTable";
        window.location.href = url;
    }
</script>
</body>
</html>