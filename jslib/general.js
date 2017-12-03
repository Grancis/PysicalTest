window.onload=function () {
    var cardBox=document.getElementById("cardBox");
    var card=document.getElementsByClassName("card");
    var header=document.getElementsByClassName("header");
    var msg=document.getElementsByClassName("msg");
    var bean=document.getElementsByClassName("bead");
    var content=document.getElementsByClassName("content");
    var nav=document.getElementById("nav");
    setWidth(cardBox,640);
    setWidth(nav,640);
    for(var i=0;i<card.length;++i)
        setHeight(header[i],card[i]);
}
window.onresize=function () {
    setWidth(cardBox,640);
    setWidth(nav,640);
}