
// 处理重定向[服务端返回401]
$(document).ajaxComplete(function(e, xhr, settings){
    var _location;
    if(xhr.status === 401){
        _location = xhr.getResponseHeader("Location");
        if(_location) {
            location.assign(_location);
        }
    }
});

// ajax例子
//function http() {
//    $.ajax({
//        type: "POST",
//        url: "http://127.0.0.1:8888/hello",
//        data: {},
//        dataType: "json",
//        success: function(data) {
//            console.log(data);
//        },
//        error: function(jqXHR, textStatus, errorThrown) {
//            console.log("status: " + textStatus);
//        }
//    });
//}