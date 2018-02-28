// var studentData={
//     term:"",
//     name:'',
//     assessment:"",
//     data:[height,weight,lung,jump,shortRun,step,longRunFemale,longRunMale,sitUp,pullUp,sitAndReach,grip,visionLeft,visionRight],
//     score:[height,weight,lung,jump,shortRun,step,longRunFemale,longRunMale,sitUp,pullUp,sitAndReach,grip,visionLeft,visionRight],
//     suggestion:""
// }
$(document).ready(function () {
    var term=new Array();//len
    var assess=new Array();//len
    var data=new Array();//len*14 9(+1)valid
    var score=new Array();//len*13 8valid
    var suggest=new Array();//len
    $.getJSON("/query",function (json) {
        var len=json.length;
        var k=0,n=0;
        //格式化json数据
        for(var i=0;i<len;++i){
            term[i]=json[i].term;
            assess[i]=json[i].assessment;
            for(var j=0;j<14;++j){
                if(json[i].datas[j]==""||j==1)
                    ;
                else if(j==0)
                    data[k++]=json[i].datas[j]+" "+json[i].datas[j+1];
                else
                    data[k++]=json[i].datas[j];
                if(json[i].scores[j]=="")
                    ;
                else
                    score[n++]=json[i].scores[j];

            }
            suggest[i]=json[i],suggestion;

        }
        //更新网页数据
        for(var i=0;i<len;++i){
            var j=i+1;
            $("#year-"+j).text(term[i]);
            $(".assessment")[i].text(assess[i]);
            $(".suggestion")[i].text(suggest[i]);
            for(var m=0;m<8;++m){
                $(".data-"+j)[m].text(data[m+i*8]);
                $(".score-"+j)[m].text(score[m+i*8]);
            }

        }
        //对已经填充的cardBox进行css修改让其显示
        for(var i=1;i<=len;++i){
            $("#cardBox-"+i).css("display","block");
        }
    })
})