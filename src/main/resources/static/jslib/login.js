window.onload=function () {
var container=document.getElementById("myBox");
var loginForm=document.getElementById("loginForm")
setWidth(container,360);
}
window.onresize=function () {
    setWidth(container,360);
}
function loginCheck() {
    loginForm.submit();
}