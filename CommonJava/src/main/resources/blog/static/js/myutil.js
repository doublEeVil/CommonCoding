$(document).ajaxError(function(e, xhr){
    var _location;
    console.log("=====111 " + xhr.status);
    if(xhr.status === 302){
        _location = xhr.getResponseHeader("Location");
        if(_location) {
            location.assign(_location);
        }
    }
});

$(document).ajaxComplete(function(e, xhr, settings){
    var _location;
    console.log("=====22 " + xhr.status);
    if(xhr.status === 302){
        _location = xhr.getResponseHeader("Location");
        if(_location) {
            location.assign(_location);
        }
    }
});