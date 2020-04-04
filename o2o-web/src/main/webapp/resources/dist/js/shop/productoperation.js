/**
 * 从后台获取到商品类别  填写到前台表单中
 * 从前台获取到表单信息 填充到后台
 */
$(function () {
    //先从url里面获取productId 如果有就证明是 修改操作没有就证明是添加操作
    var productId = getQueryString("productId");
    //判断是修改操作还是添加操作
    var isEdit = productId?true:false;

    //修改商品中 根据商品号码初始化商品信息
    var infoUrl ="/o2o/shopadmin/getproductbyid?productId="+productId;

    //获取当前店铺设定的商品类别列表url
    var categoryUrl="/o2o/shopadmin/getproductcategory";

    //更新商品信息的URL
    var productPostUrl = "/o2o/shopadmin/modifyproduct";


    if(!isEdit){
        //如果是添加操作
        //获取商品类别信息
        getCategory();
        productPostUrl = "/o2o/shopadmin/addproduct";
    }else{
        //如果是修改操作
        //修改操作不能 修改商品类别
        getInfo(productId);
    }


    //后台获取到店铺下的商品类别信息  填充到 前台页面去
    //productCategoryList
    function getCategory(){
        $.getJSON(categoryUrl,function (data) {
            if(data.success){
                //返回信息 成功
                var tempHtml="";
                data.productCategoryList.map(function(item,index){
                    tempHtml+='<option data-value="'+item.productCategoryId+'">'+
                        item.productCategoryName+'</option>';
                })
                $("#category").html(tempHtml);
            }else{
                //如果查询失败就说明没有shopid  就重定向到shoplist页面
                $.toast("违规操作!");
                window.location.href="/o2o/shopadmin/shoplist";
            }
            //返回信息失败

        });
    }


    /**
     * 根据productid 去查询商品对应的所有商品信息
     */
    function getInfo(){
        $.getJSON(infoUrl,function (data) {
            if(data.success){
                //从返回的json中获取product的对象信息 并赋值给表单
                var product = data.product;
                $("#product-name").val(product.productName);
                $("#priority").val(product.priority);
                $("#normal-price").val(product.normalPrice);
                $("#promotion-price").val(product.promotionPrice);
                $("#product-desc").val(product.productDesc);
                //需要把所有商品类别找到 默认选中 该商品之前的商品类别
                var productCategoryList = data.productCategoryList;

                //修改之前 商品id号码
                var optionSelected = product.productCategory.productCategoryId;
                var optionHtml="";

                productCategoryList.map(function (item,index) {
                    //如果遍历的这条数据是之前的那条的话 默认被选中
                    var isSelect = optionSelected === item.productCategoryId?"selected":"";
                    optionHtml +='<option data-value="'+item.productCategoryId+'"'+isSelect+'>'+item.productCategoryName+'</option>';
                })
                $("#category").html(optionHtml);
            }
        })
    }


    //绑定文件上传控件  最后一个添加图片点完就生成新的添加图片控件
    //如果控件总数未达到6个 则生成新的一个文件上传控件
    $(".detail-img-div").on("change",".detail-img:last-child",function(){
        if($(".detail-img").length<6){
            $("#detail-img").append("<input type='file' class='detail-img'>");
        }
    })

    //为提交按添加点击事件
    $("#submit").click(function () {
        //TODO  在获取信息之前 使用正则判断输入字符是否合法
        var product={};
        if(isEdit){product.productId=productId};
        product.productName=$("#product-name").val();
        product.priority=$("#priority").val();
        product.normalPrice=$("#normal-price").val();
        product.promotionPrice = $("#promotion-price").val();
        product.productDesc = $("#product-desc").val();
        product.productCategory={
            //被选中的对象的值 被封装在对象中
            productCategoryId:$("#category").find("option").not(function(){return !this.selected}).data("value")
        };
        var formData = new FormData();
        formData.append("productStr",JSON.stringify(product));
        //获取缩略图文件流
        var thumbnail = $("#small-img")[0].files[0];

        formData.append("thumbnail",thumbnail);
        //获取详情图文件流
        $(".detail-img").map(function(index,item){
            //判断该空间是否已选择了文件
            if($(".detail-img")[index].files.length>0){
                //将第i个文件流赋值给key为productImgi的表单键值对里
                formData.append("productImg"+index,$(".detail-img")[index].files[0])
            }
        })

        //将验证码传进来  如果不对就不进行ajax提交
        var verifyCodeActual=$("#j-captcha").val();
        if(!verifyCodeActual){
            //如果验证码输入为空 提升用户输入验证码
            $.toast("请输入验证码");
            return ;
        }
        formData.append("verifyCodeActual",verifyCodeActual);
        $.ajax({
            //如果是修改操作 就请求修改页面  如果是添加操作就请求添加页面
            url:productPostUrl,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function(data){
                if(data.success){
                    if(isEdit){
                        //如果是修改操作
                        //修改成功
                        $.toast("修改成功!");
                        //修改成功后自动刷新页面
                        getInfo(productId);
                    }else{
                        //添加成功
                        $.toast("添加成功!");
                        //提交成功后自动刷新页面
                        $("#product-name").val("");
                        $("#priority").val("");
                        $("#normal-price").val("");
                        $("#promotion-price").val("");
                        $("#product-desc").val("");
                        $("#j-captcha").val("");
                    }

                }else{
                    $.toast("提交失败!"+data.errMsg);
                }
                //请求发送后 更换 验证码
                $("#captcha-img").click();
            }
        })
    });

    //正则判断productID 根据前端传过来的参数key 判断符合的话返回
    function getQueryString(name){
        var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null){
            return decodeURIComponent(r[2]);
        }
        return "";
    }
})
