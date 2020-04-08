/**
 *
 * */
$(function () {
    var listUrl="/o2o/shopadmin/getproductcategory?shopId=";
    var addUrl="/o2o/shopadmin/addproductcategorys";
    var deleteUrl="/o2o/shopadmin/removeproductcategory"
    var shopId = getQueryString("shopId");

    //如果没有shopId 直接跳转到列表页面
    if(shopId){
        //有
        getlist();
    }else{
        //没有
        location.href="/o2o/shopadmin/shoplist";
    }
    //初始化的一下方法
    function getlist(e) {
        $.ajax({
            url:listUrl+shopId,
            type:"get",
            dataType:"json",
            success:function (data) {
                if(data.success){
                    //如果成功请求后台 带回来productCategoryList
                    //渲染productCategoryList
                    handleList(data.productCategoryList);
                }
            }
        });
    }
    //渲染列表信息
    function handleList(data) {
        var tempHtml = "";
        data.map(function (item,index) {

            tempHtml+='<div class="row row-product-category now"><div class="col-33 product-category-name">'+item.productCategoryName+'</div>' +
                '<div class="col-33">'+item.priority+'</div>'+
                '<div class="col-33"><a class="button delete" data-id="'+item.productCategoryId+'">删除</a></div></div>';
        })
        $(".category-wrap").empty();
        $(".category-wrap").append(tempHtml);
    }

    //点击一次新增一行
    $("#new").click(function () {
        var tempHtml ='<div class="row row-product-category temp">' +
            '<div class="col-33"><input class="category-input category" placeholder="分类名" type="text">' +'</div>' +
            '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级">' +'</div>' +
        '<div class="col-33"><a class="button delete">删除</a></div></div>';
        $(".category-wrap").append(tempHtml);
    });
    //点击提交按钮获取新增表单信息 并提交
    $("#submit").click(function(){
        var tempArr = $(".temp");
        var productCategoryList = [];
        tempArr.map(function (index,item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find(".category").val();
            tempObj.priority = $(item).find(".priority").val();
            if(tempObj.productCategoryName&&tempObj.priority){
                productCategoryList.push(tempObj);
            }
        })
        $.ajax({
           url:addUrl,
           type:'POST',
           data:JSON.stringify(productCategoryList),
           contentType:'application/json',
           success:function(data){
                if(data.success){
                    $.toast("提交成功!");
                    getlist();
                }else{
                    $.toast("提交失败!");
                    getlist();
                }
           }
        });
    });

    //删除新元素
    $(".category-wrap").on('click','.row-product-category.temp .delete',
        function (e) {
            $(this).parent().parent().remove();
        }
        );
    //删除历史元素
    $(".category-wrap").on('click','.row-product-category.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm("确定吗?",function () {
                $.ajax({
                    type:"POST",
                    url:deleteUrl,
                    data:{
                        productCategoryId:target.dataset.id
                    },
                    dataType: 'json',
                    success:function (data) {
                        if(data.success){
                            $.toast("删除成功!");
                            getlist();
                        }else{
                            $.toast("删除失败!"+data.errMsg);
                            getlist();
                        }
                    }
                })
            })
        }
    );





    //正则判断shopID 根据前端传过来的参数key 判断符合的话返回
    function getQueryString(name){
        var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null){
            return decodeURIComponent(r[2]);
        }
        return "";
    }

    //拼接店铺管理连接
    function deleteProductCategory(productCategoryId) {
        if(productCategoryId){
            //如果是可用状态
            return '<a href="/o2o/shopadmin/deleteproductcategory?productCategoryId='+productCategoryId+'">删除</a>';
        }else{
            return '';
        }
    }

})