<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.min.css">
    <title>Document</title>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>
                        订单ID
                    </th>
                    <th>
                        姓名
                    </th>
                    <th>
                        手机号
                    </th>
                    <th>
                        地址
                    </th>
                    <th>
                        金额
                    </th>
                    <th>
                        订单状态
                    </th>

                    <th>
                        支付状态
                    </th>
                    <th>
                        创建时间
                    </th>
                    <th colspan="2">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list list.content as item>
                    <tr>
                        <td>${item.orderId}</td>
                        <td>${item.buyerName}</td>
                        <td>${item.buyerAddress}</td>
                        <td>${item.buyerPhone}</td>
                        <td>${item.orderAmount}</td>
                        <td>${item.orderStatusEnum}</td>
                        <td>${item.payStatusEnum}</td>
                        <td>${item.createTime}</td>

                        <td><a class="btn btn-primary btn-xs" href="/seller/order/detail?orderId=${item.orderId}">详情</a></td>
                        <td>
                            <#if item.getOrderStatusEnum().msg =="新订单">
                                <a class="btn btn-danger btn-xs" href="/seller/order/cancel?orderId=${item.orderId}">取消</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <ul class="pagination pull-right">
                <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                <#else>
                    <li><a href="/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                </#if>

                <#list 1..list.getTotalPages() as index>
                    <#if currentPage == index>
                        <li class="disabled"><a href="#">${index}</a></li>
                    <#else>
                        <li><a href="/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                    </#if>
                </#list>

                <#if currentPage gte list.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                <#else>
                    <li><a href="/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                </#if>
            </ul>
        </div>
    </div>
</div>
</body>
</html>