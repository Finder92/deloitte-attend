$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'attend/attenduser/list',
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'attendUserid', index: 'attend_userid', width: 50, key: true, align: 'center'},
            {
                label: '用户类型',
                name: 'attendUsertype',
                index: 'attend_usertype',
                width: 80,
                align: 'center',
                formatter: function (value) {
                    if (value == 0) {
                        return '<span class="fa fa-id-badge">员工</span>';
                    } else if (value == 1) {
                        return '<span class="fa fa-user-circle">访客</span>';
                    } else if (value == 2) {
                        return '<span class="fa fa-id-card-o">VIP访客</span>';
                    } else if (value == 3) {
                        return '<span class="fa fa-address-card">黄名单</span>';
                    } else if(value==4) {
                        return '<span class="fa fa-user-o">陌生人</span>';
                    }
                }
            },
            {label: '与会人员姓名', name: 'attendUssname', index: 'attend_ussname', width: 80, align: 'center'},
            {
                label: '签到状态',
                name: 'attendSignstate',
                index: 'attend_signstate',
                width: 80,
                formatter: function (value) {
                    /*return value == '0' ?
                        '<span class="label label-danger onAttend">未签到</span>' :
                        '<span class="label label-success">已签到</span>';*/
                    if(value=='0'){
                        return '<span class="label label-danger onAttend">未签到</span>';
                    }else if(value=='1'){
                        return '<span class="label label-success">已签到</span>';
                    }else if(value=='2'){
                        return '<span class="label label-danger">不可签到</span>';
                    }
                },
                align: 'center'
            },
            {
                label: '人员识别照片',
                name: 'attendPictureUrl',
                index: 'attend_picture_url',
                width: 80,
                formatter: function (cellvalue) {
                    return '<img src="' + cellvalue + '"  style="width:50px;height:50px;" />';
                },
                align: 'center'
            },
            {label: '签到时间', name: 'attendSigntime', index: 'attend_signtime', width: 80, align: 'center'},
            {label: '人脸识别记录的时间', name: 'attendCreatetime', index: 'attend_createtime', width: 80, align: 'center'}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }

    });

    $(document).on('click', '.onAttend', function () {
        var attendUserid = getSelectedRow();
        var attUserData = $('#jqGrid').jqGrid('getRowData', attendUserid)
        console.info('attUserData', attUserData)
        $.ajax({
            type: "POST",
            url: baseURL + "attend/attenduser/sign",
            contentType: "application/json",
            data: JSON.stringify(attendUserid),
            success: function (r) {
                if (r.code == 0) {
                    layer.msg("操作成功", {icon: 1});
                    $("#jqGrid").trigger("reloadGrid");
                } else {
                    layer.alert(r.msg);
                }
            }
        });
    })
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        attendUser: {}
    },
    methods: {
        find: function () {
            console.info(vm.attendUser.attendUssname)
            var attendUssname = vm.attendUser.attendUssname;
            $.ajax({
                type: "POST",
                url: baseURL + "attend/attenduser/find",
                contentType: "application/json",
                //data: JSON.stringify(attendUssname),
                data: attendUssname,
                success: function (r) {
                    console.info(r);
                    //方式1:ajax将数据返回渲染到页面
                }
            });
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.attendUser = {};
        },
        update: function (event) {
            var attendUserid = getSelectedRow();
            console.info('attendUserid', attendUserid)
            if (attendUserid == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(attendUserid)
        },
        batchsign: function (event) {
            var attendUserids = getSelectedRows();
            console.info('attendUserids', attendUserids)
            var newBatchAttendUserIds = new Array();
            for (var attendUserid in attendUserids) {
                console.info(attendUserids[attendUserid])
                var attUserData = $('#jqGrid').jqGrid('getRowData', attendUserids[attendUserid])
                console.info(attUserData)
                if (attUserData.attendSignstate == '<span class="label label-danger onAttend">未签到</span>') {
                    newBatchAttendUserIds.push(attendUserids[attendUserid])
                }
            }
            console.info(newBatchAttendUserIds)
            $.ajax({
                type: "POST",
                url: baseURL + "attend/attenduser/batchSign",
                contentType: "application/json",
                data: JSON.stringify(newBatchAttendUserIds),
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg("操作成功", {icon: 1});
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.attendUser.attendUserid == null ? "attend/attenduser/save" : "attend/attenduser/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.attendUser),
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        } else {
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
            });
        },
        del: function (event) {
            var attendUserids = getSelectedRows();
            if (attendUserids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "attend/attenduser/delete",
                        contentType: "application/json",
                        data: JSON.stringify(attendUserids),
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            }, function () {
            });
        },
        getInfo: function (attendUserid) {
            $.get(baseURL + "attend/attenduser/info/" + attendUserid, function (r) {
                vm.attendUser = r.attendUser;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'attendUssname': vm.attendUser.attendUssname},
                page: page
            }).trigger("reloadGrid");
        }
    }
});