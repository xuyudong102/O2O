/**
 * 从后台获取到店铺列表和区域区域信息 填写到前台表单中
 * 从前台获取到表单信息 填充到后台
 */
$(function () {
    var shopId = getQueryString("shopId");
    //判断是修改操作还是添加操作
    var isEdit = shopId?true:false;

    //获取初始化信息
    var initUrl="/o2o/shopadmin/getshopinitinfo";
    //添加店铺信息的提交路径
    var registerShopUrl="/o2o/shopadmin/registershop";
    //根据shopId获取店铺信息
    var shopInfoUrl="/o2o/shopadmin/getshopbyid?shopId="+shopId;
    //修改店铺信息url
    var editShopUrl="/o2o/shopadmin/modifyshop";

    if(!isEdit){
        //如果是添加操作
        getShopInitInfo();
    }else{
        //如果是修改操作
        getShopInfo(shopId);
    }

    //根据shopId进行初始化操作 店铺不能被选择
    function getShopInfo(shopId){
        $.getJSON(shopInfoUrl,function (data) {
           if(data.success) {
               var shop = data.shop;
               $("#shop-name").val(shop.shopName);
               $("#shop-addr").val(shop.shopAddr);
               $("#shop-phone").val(shop.phone);
               $("#shop-desc").val(shop.shopDesc);
               var shopCategory='<option data-id="'
                   +shop.shopCategory.shopCategoryId+'" selected>'
                   +shop.shopCategory.shopCategoryName+'</option>';
               var tempAreaHtml='';
               data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'
                   +item.areaName+'</option>'
               });
               $("#shop-category").html(shopCategory);
               $("#shop-category").attr("disabled","disabled");
               $("#area").html(tempAreaHtml);
               //默认选择现在的区域信息
               $("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
           }
        });
    }

    //后台获取到区域信息 和店铺信息并且填充到前台控件中去
    function getShopInitInfo(){
        $.getJSON(initUrl,function (data) {
            if(data.success){
                //返回信息 成功
                var tempHtml="";
                var tempAreaHtml="";
                data.shopCategoryList.map(function(item,index){
                    tempHtml+='<option data-id="'+item.shopCategoryId+'">'+
                        item.shopCategoryName+'</option>';
                })
                data.areaList.map(function(item,index){
                    tempAreaHtml+='<option data-id="'+item.areaId+'">'+
                        item.areaName+'</option>';
                });
                $("#shop-category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
            //返回信息失败

        });
    }

    //为提交按添加点击事件
    $("#submit").click(function () {
        //TODO  在获取信息之前 使用正则判断输入字符是否合法

        //当点击提交按钮的时候
        var shop={};
        if(isEdit){shop.shopId=shopId};
        shop.shopName=$("#shop-name").val();
        shop.shopAddr=$("#shop-addr").val();
        shop.phone=$("#shop-phone").val();
        shop.shopDesc = $("#shop-desc").val();
        shop.shopCategory={
            //被选中的对象的值 被封装在对象中
            shopCategoryId:$("#shop-category").find("option").not(function(){return !this.selected}).data("id")
        };
        shop.area = {
            areaId:$("#area").find("option").not(function(){return !this.selected}).data("id")
        }
        var shopImg = $("#shop-img")[0].files[0];
        var formData = new FormData();
        formData.append("shopImg",shopImg);
        formData.append("shopStr",JSON.stringify(shop));
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
            url:(isEdit?editShopUrl:registerShopUrl),
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function(data){
                if(data.success){
                    //添加成功
                    $.toast("提交成功!");
                    //提交成功后自动刷新页面
                    $("#shop-name").val("");
                    $("#shop-addr").val("");
                    $("#shop-phone").val("");
                    $("#shop-desc").val("");
                    $("#j-captcha").val("");
                }else{
                    $.toast("提交失败!"+data.errMsg);
                }
                //请求发送后 更换 验证码
                $("#captcha-img").click();
            }
        })
    });

    //正则判断shopID 根据前端传过来的参数key 判断符合的话返回
    function getQueryString(name){
        var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null){
            return decodeURIComponent(r[2]);
        }
        return "";
    }
})
