//将表单序列化成json格式的数据(但不适用于含有控件的表单，例如复选框、多选的select)
(function ($) {
    $.fn.serializeJson = function () {
        var jsonData1 = {};
        var serializeArray = this.serializeArray();
        // 先转换成{"id": ["12","14"], "name": ["aaa","bbb"], "pwd":["pwd1","pwd2"]}这种形式
        $(serializeArray).each(function () {
            if (jsonData1[this.name]) {
                if ($.isArray(jsonData1[this.name])) {
                    jsonData1[this.name].push(this.value);
                } else {
                    jsonData1[this.name] = [jsonData1[this.name], this.value];
                }
            } else {
                jsonData1[this.name] = this.value;
            }
        });
        // 再转成[{"id": "12", "name": "aaa", "pwd":"pwd1"},{"id": "14", "name": "bb", "pwd":"pwd2"}]的形式
        var vCount = 0;
        // 计算json内部的数组最大长度
        for (var item in jsonData1) {
            var tmp = $.isArray(jsonData1[item]) ? jsonData1[item].length : 1;
            vCount = (tmp > vCount) ? tmp : vCount;
        }

        if (vCount > 1) {
            var jsonData2 = new Array();
            for (var i = 0; i < vCount; i++) {
                var jsonObj = {};
                for (var item in jsonData1) {
                    jsonObj[item] = jsonData1[item][i];
                }
                jsonData2.push(jsonObj);
            }
            return JSON.stringify(jsonData2);
        } else {
            return "[" + JSON.stringify(jsonData1) + "]";
        }
    };
})(jQuery);

$(function () {

    //验证保存数据并且提交
    var addModalValidate = $("#tableColListModal").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
            element.parent('div').append(error);
        },
        submitHandler: function (form) {
            var parms = $("#tableColListModal").serializeJson();
            var parms1=$("#tableColListModal").serialize();
            //parms=JSON.stringify(parms);
            var typeName="保存成功";
            $.ajax({
                type: 'POST',
                url: base_url + "/codeTableColumn/saveList",
                data: parms,
                dataType: "json",
                contentType: 'application/json;charset=utf-8', //设置请求头信息
                success: function (data) {
                    console.log(data)
                    if (data.code === 200) {
                        layer.open({
                            title: I18n.system_tips,
                            btn: [I18n.system_ok],
                            content: typeName + I18n.system_success,
                            icon: '1',
                        });
                        setInterval(goHome, 2000);
                    } else {
                        layer.open({
                            title: I18n.system_tips,
                            btn: [I18n.system_ok],
                            content: (data.msg || typeName + I18n.system_fail),
                            icon: '2'
                        });
                    }
                }
            });
        }
    });

    function goHome() {
        var url = base_url + "/codeTable";
        window.location.href = url;
    }
});