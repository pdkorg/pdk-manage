/**
 * Created by hubo on 2015/8/15
 */

var PdkDataTable = function() {

       var tableDefaultSet = function(options) {

            var table = $("#"+options.tableId);

            var paging = options.paging == null ? true: options.paging;

            var serverSide = options.serverSide == null ? true : options.serverSide;

            var searching = options.searching == null ? true : options.searching;

            var ordering =  options.ordering == null ? true : options.ordering;

           var autoWidth =  options.autoWidth == null ? true : options.autoWidth;

           var processing = options.processing == null ? true : options.processing;

            var ajax = null;

            if(serverSide) {
                ajax = {
                    url: options.url,
                    type: 'POST',
                    data: function ( d ) {
                        d.searchText = d.search.value;
                        if ( d.order[0] ) {
                            d.orderIndex = d.order[0].column;
                            d.orderType = d.order[0].dir;
                        }

                    }
                };
            }

            var dataTable = table.DataTable({

                "language": {
                    "sProcessing": "加载中...",
                    "sLengthMenu": "显示 _MENU_ 项结果",
                    "sZeroRecords": "没有匹配结果",
                    "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                    "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                    "sInfoPostFix": "",
                    "sSearch": "搜索:",
                    "sUrl": "",
                    "sEmptyTable": "表中数据为空",
                    "sLoadingRecords": "载入中...",
                    "sInfoThousands": ",",
                    "oPaginate": {
                        "sFirst": "首页",
                        "sPrevious": "上页",
                        "sNext": "下页",
                        "sLast": "末页"
                    },
                    "oAria": {
                        "sSortAscending": ": 以升序排列此列",
                        "sSortDescending": ": 以降序排列此列"
                    }
                },

                "processing": processing,

                "autoWidth":autoWidth,

                paging: paging,

                "ajax": ajax,

                "serverSide": serverSide,
                "searching" : searching,
                "ordering"  : ordering,
                "bStateSave": true,

                "lengthMenu": [
                    [10, 20, 30],
                    [10, 20, 30]
                ],

                "pageLength": 20,

                "pagingType": "bootstrap_full_number",

                "columns": options.columns,

                "columnDefs": options.columnDefs,

                "order":options.order,

                "rowCallback" : function(row, data) {
                    $(row).attr("ts", data.ts);
                },
                "drawCallback": options.drawCallback,
                "initComplete": options.initComplete,
                "stateLoaded":options.stateLoaded
            });

            table.on( 'draw.dt', function () {
                table.find('.group-checkable').attr("checked", false);
                table.find('.group-checkable').uniform();
                $(this).find(".checkboxes").uniform();
            });

           table.on( 'click', 'tr', function () {

            } );

            var tableWrapper = jQuery("#"+options.tableId+'_wrapper');

            tableWrapper.find('.dataTables_length select').select2({
                formatNoMatches: function(term) {
                    return "找不到匹配项目";
                },
                minimumResultsForSearch:-1
            });

            table.find('.group-checkable').change(function() {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function() {
                    if (checked) {
                        $(this).attr("checked", true);
                    } else {
                        $(this).attr("checked", false);
                    }
                });
                jQuery.uniform.update(set);
            });

            tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline");

           return dataTable;
        };

        return {
            init : function(options) {
                return tableDefaultSet(options);
            }
        }
    }();

