/**
 * 一些常用的js方法
 */




 
/**
 * 自定义http方法
 * @param {} method 
 * @param {*} url 
 * @param {*} send_data 
 * @param {*} callback 
 */
function my_http(method, url, send_data, callback){
  send_data = (function(obj){ // 转成post需要的字符串.
      var str = "";

      for(var prop in obj){
        str += prop + "=" + obj[prop] + "&"
      }
      return str;
    })(send_data);

    var xhr = new XMLHttpRequest();
    if(method != 'POST' || method != 'post' || method != 'GET' || method != 'get' )
      method = 'POST';
    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    //xhr.setRequestHeader("Token","123");
    xhr.onreadystatechange = function(){
      var XMLHttpReq = xhr;
      if (XMLHttpReq.readyState == 4) {
        if (XMLHttpReq.status == 200) {
          var text = XMLHttpReq.responseText;

          //console.log(text);
          callback(text);
        }
      }
    };
    xhr.send(send_data);
}