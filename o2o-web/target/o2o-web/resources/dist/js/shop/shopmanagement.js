//如果session或者request存有shopid的话就给商铺管理连接赋值(带shopid)  如果没有直接跳转到商铺列表
$(function () {
    var shopId = getQueryString("shopId");
    var shopInfoUrl='/o2o/shopadmin/getshopmanagementinfo?shopId='+shopId;
    $.getJSON(shopInfoUrl,function (data) {
        if(data.redirect){
            //直接跳转
            window.location.href=data.url;
        }else{
            //如果在session中存了 直接从session中取
            if(data.shopId!=undefined&&data.shopId!=null){
                shopId = data.shopId;
            }
            $("#shopInfo").attr("href","/o2o/shopadmin/shopoperation?shopId="+shopId);
        }
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