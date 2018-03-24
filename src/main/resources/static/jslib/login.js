window.onload=function () {
var container=document.getElementById("myBox");
var accountInput=document.getElementById("accountInput");
var pwdInput=document.getElementById("pwdInput");
var errorBox=document.getElementById("errorBox");
var loginForm=document.getElementById("loginForm")
setWidth(container,360);
}
window.onresize=function () {
    setWidth(container,360);
}
function loginCheck() {
    //正则检查。。
    //加密
    loginForm.submit();
}