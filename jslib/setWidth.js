function setWidth(ele,max) {
    var realW;
    var w;
    if(window.innerWidth)
        realW=window.innerWidth;
    else if((document.body)&&(document.body.clientWidth))
        realW=document.body.clientWidth;
    else ;
    if(realW>=max)
        w=max+"px";
    else
        w=realW*0.9+"px";
    ele.style.width=w;
    // console.log(w);
}

function setHeight(ele,refEle) {
    var refHeight=refEle.offsetHeight;
    var h=refHeight-10;
    ele.style.height=h+'px';
}