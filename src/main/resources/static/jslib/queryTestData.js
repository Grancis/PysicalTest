// var studentData={
//     term:"",
//     name:'',
//     assessment:"",
//     data:[height,weight,lung,jump,shortRun,step,longRunFemale,longRunMale,sitUp,pullUp,sitAndReach,grip,visionLeft,visionRight],
//     score:[height,weight,lung,jump,shortRun,step,longRunFemale,longRunMale,sitUp,pullUp,sitAndReach,grip,visionLeft,visionRight],
//     suggestion:""
// }

var data=[{"sex":"male","term":"20162","name":"韦振朝","assessment":"52分，未完成","dataList":["160","51.8","2970","2.13","","","","255","","11","2.6","","",""],"scores":["100","50","62","","","","66","","64","40","","",""],"suggestion":"肺活量|坐位体前屈|项目未完成"},{"sex":"male","term":"20171","name":"韦振朝","assessment":"65分，及格","dataList":["160","50.3","2453","2.21","7.7","","","222","","12","3.1","","",""],"scores":["100","10","66","74","","","80","","68","50","","",""],"suggestion":"肺活量|坐位体前屈|"}]
//data 为离线测试数据
function wipeDataSpace(data,sex) {
    var arr=new Array();
    var dataLen=data.length;
    if (sex=="male"){
        for (var i=0;i<5;++i){
            arr[i]=data[i];
        }
        arr[5]=data[7];
        arr[6]=data[9];
        arr[7]=data[10];
    }
    else if(sex=="female"){
        for (var i=0;i<5;++i){
            arr[i]=data[i];
        }
        arr[5]=data[6];
        arr[6]=data[8];
        arr[7]=data[10];
    }
    return arr;
}
function wipeScoresSpace(score,sex) {
    var arr=new Array();
    var dataLen=score.length;
    if (sex=="male"){
        for (var i=0;i<4;++i){
            arr[i]=score[i];
        }
        arr[4]=score[6];
        arr[5]=score[8];
        arr[6]=score[9];
    }
    else if(sex="female"){
        for (var i=0;i<4;++i){
            arr[i]=score[i];
        }
        arr[4]=score[5];
        arr[5]=score[7];
        arr[6]=score[9];
    }
    return arr;
}
function formatData(data) {
    var terms=new Array();
    var len=data.length;
    for (var i=0;i<len;++i){
        terms[i]=new Object();
        terms[i].sex=data[i].sex;
        terms[i].term=data[i].term;
        terms[i].name=data[i].name;
        terms[i].assessment=data[i].assessment;
        terms[i].dataList=wipeDataSpace(data[i].dataList,data[i].sex);
        terms[i].scores=wipeScoresSpace(data[i].scores,data[i].sex);
        terms[i].suggestion=data[i].suggestion;
    }
    return terms;
}
//更新测试数据信息
function updateDate(dataList,n) {
    $("#HaW-"+n).text(dataList[0]+"cm "+dataList[1]+"kg");
    for(var i=0;i<document.getElementsByClassName("data-"+n).length;i++){
        document.getElementsByClassName("data-"+n)[i].innerHTML=dataList[i+2];
    }
}
//更新分数信息
function  updateScore(scores,n) {
    for (var i=0;i<scores.length;i++){
        document.getElementsByClassName("score-"+n)[i].innerHTML=scores[i];
    }
}
//显示已经填充数据的盒子
function showBox(n) {
        $("#cardBox-"+n).removeClass("hide");
}
//更新网页数据
function updateHTML(terms) {
    for(var i=0;i<terms.length;i++){
        var j=i+1;
        $("#year-"+j).text(terms[i].term);
        $("#assessment-"+j).text(terms[i].assessment);
        document.getElementsByClassName("suggestion")[i].innerHTML=terms[i].suggestion;
        updateDate(terms[i].dataList,j);
        updateScore(terms[i].scores,j);
        changeDif(terms[i].sex);
        showBox(j);
    }
}
//男女项目区别更正
function changeDif(sex) {
  if(sex=="male"){
      $(".sex_dif").each(function () {
          $(this).text("引体向上");
      })
  }
  else if(sex=="female"){
      $(".sex_dif").each(function () {
          $(this).text("仰卧起坐");
      })
  }
}

//触发函数
$(document).ready(function () {
    // $.getJSON("/data",function (json) {
    //     var data=json;
    //     var terms=formatData(data);
    //     updateHTML(terms);
    // })
    //以上注释为在线请求数据的处理
    var terms=formatData(data);
    updateHTML(terms);
})