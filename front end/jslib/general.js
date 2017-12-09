window.onload=function () {
    var cardBoxes=document.getElementsByClassName("cardBox");
    var card=document.getElementsByClassName("card");
    var header=document.getElementsByClassName("header");
    var msg=document.getElementsByClassName("msg");
    var bean=document.getElementsByClassName("bead");
    var content=document.getElementsByClassName("content");
    var nav=document.getElementById("nav");
    for (var i=0;i<cardBoxes.length;++i)
        setWidth(cardBoxes[i],640);
    for(var i=0;i<card.length;++i)
        setHeight(header[i],card[i]);
}
window.onresize=function () {
    for (var i=0;i<cardBoxes.length;++i)
        setWidth(cardBoxes[i],640);
}