/**
 *
 * */
$(function () {
    var listUrl = "/o2o/shopadmin/getproductlistbyshop?pageIndex=1&&pageSize=999";
    var statusUrl = "/o2o/shopadmin/modifyproduct";
    getlist();

    //var shopId = getQueryString("shopId");

    // //如果没有shopId 直接跳转到列表页面
    // if(shopId){
    //     //有
    //     getlist();
    // }else{
    //     //没有
    //     location.href="/o2o/shopadmin/shoplist";
    // }
    //初始化的一下方法 获取此店铺下的商品列表
    function getlist() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                //如果成功请求后台 带回来productList
                //渲染productList
                handleList(data.productList);
            }
        })
    }

    //渲染列表信息
    function handleList(data) {
        var tempHtml = "";
        data.map(function (item, index) {
            //遍历每条商品信息 拼接成一行显示 列信息包括
            //商品名称 优先级 上架/下架(含productId) 编辑按钮(含productId)
            var textOp = "下架";
            var contaryStatus = 0;
            if (item.enableStatus == 0) {
                //若状态值为0 表明是已下架的商品 操作变为上架 (即点击上架按钮上架相关商品)
                textOp = "上架";
                contaryStatus = 1;
            } else {
                contaryStatus = 0;
            }

            tempHtml += '<div class="row row-product ">' +
                '<div class="col-33">' + item.productName + '</div>' +
                '<div class="col-20">' + item.priority + '</div>' +
                '<div class="col-40">' +
                '<a herf="#" class="edit" data-id="' + item.productId + '" data-status="' + item.enableStatus + '">编辑</a>' +
                '<a herf="#" class="status" data-id="' + item.productId + '" data-status="' + contaryStatus + '">' + textOp + '</a>' +
                '<a herf="#" class="preview" data-id="' + item.productId + '" data-status="' + item.enableStatus + '">预览</a>' +
                '</div></div>';
        })
        $(".product-wrap").empty();
        $(".product-wrap").append(tempHtml);
    }

    //为class为product-wrap里面的a标签绑定上点击事件
    $(".product-wrap").on("click","a",function (e) {
        var target = $(e.currentTarget);
        if(target.hasClass("edit")){
            //如果是修改操作的a链接
            window.location.href="/o2o/shopadmin/productoperation?productId="+e.currentTarget.dataset.id;
        }else if(target.hasClass("status")){
            //改变状态的a链接 调用后台功能上/下 架商品功能 带有productId参数
            changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status);
        }else if(target.hasClass("preview")){
            //预览的a链接 preview 则去前台展示系统 该商品详情页预览商品情况
            window.location.href="/o2o/frontend/productdetail?productId="+e.currentTarget.dataset.id;
        }
    });

    function changeItemStatus(id,enableStatus){
        //定义product json对象并添加productId以及状态（上架/下架）
        var product={};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm("确定吗？",function () {
            //上下架商品相关
            $.ajax({
                url:statusUrl,
                type:'POST',
                data:{
                    productStr :JSON.stringify(product),
                    statusChange:true
                },
                dataType:'json',
                success:function (data) {
                    if(data.success){
                        //如果修改成功
                        $.toast("操作成功!");
                    }else{
                        $.toast("操作失败!");
                    }
                    getlist();
                }
            })
        })
    }

    /*//点击一次新增一行
    $("#new").click(function () {
        var tempHtml = '<div class="row row-product-category temp">' +
            '<div class="col-33"><input class="category-input category" placeholder="分类名" type="text">' + '</div>' +
            '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级">' + '</div>' +
            '<div class="col-33"><a class="button delete">删除</a></div></div>';
        $(".category-wrap").append(tempHtml);
    });*/
    //点击提交按钮获取新增表单信息 并提交
   /* $("#submit").click(function () {
        var tempArr = $(".temp");
        var productCategoryList = [];
        tempArr.map(function (index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find(".category").val();
            tempObj.priority = $(item).find(".priority").val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        })
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(productCategoryList),
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功!");
                    getlist();
                } else {
                    $.toast("提交失败!");
                    getlist();
                }
            }
        });
    });*/

   /* //删除新元素
    $(".category-wrap").on('click', '.row-product-category.temp .delete',
        function (e) {
            $(this).parent().parent().remove();
        }
    );
    //删除历史元素
    $(".category-wrap").on('click', '.row-product-category.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm("确定吗?", function () {
                $.ajax({
                    type: "POST",
                    url: deleteUrl,
                    data: {
                        productCategoryId: target.dataset.id
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.toast("删除成功!");
                            getlist();
                        } else {
                            $.toast("删除失败!" + data.errMsg);
                            getlist();
                        }
                    }
                })
            })
        }
    );
*/

    /*//正则判断shopID 根据前端传过来的参数key 判断符合的话返回
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURIComponent(r[2]);
        }
        return "";
    }

    //拼接店铺管理连接
    function deleteProductCategory(productCategoryId) {
        if (productCategoryId) {
            //如果是可用状态
            return '<a href="/o2o/shopadmin/deleteproductcategory?productCategoryId=' + productCategoryId + '">删除</a>';
        } else {
            return '';
        }
    }*/

})