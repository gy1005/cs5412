var express = require("express");
var app = express();
var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({extended: true}));
app.set("view engine","ejs");
var request = require('request');


//recent games data:
var past = [
    {
    name1: "Jazz",
    name2: "Rockets",
    score1: 102,
    score2: 112,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/SP_dsmXEKFVZH5N1DQpZ4A_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/zhO6MIB1UzZmtXLHkJQBmg_96x96.png"
    },
    
    {
    name1: "Pelicans",
    name2: "Warriors",
    score1: 104,
    score2: 113,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/JCQO978-AWbg00TQUNPUVg_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/XD2v321N_-vk7paF53TkAg_96x96.png"
    },
    
    {
    name1: "76ers",
    name2: "Celtics",
    score1: 112,
    score2: 114,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/US6KILZue2D5766trEf0Mg_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/GDJBo7eEF8EO5-kDHVpdqw_96x96.png"
    }
];

var cur = [
    {
    name1: "Cleveland",
    name2: "Celtics",
    score1: 45,
    score2: 39,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/NAlGkmv45l1L-3NhwVhDPg_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/GDJBo7eEF8EO5-kDHVpdqw_96x96.png"
    },
    
    {
    name1: "Warriors",
    name2: "Rockets",
    score1: 35,
    score2: 50,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/XD2v321N_-vk7paF53TkAg_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/zhO6MIB1UzZmtXLHkJQBmg_96x96.png"
    }
];      

var future = [
    {
    name1: "Mavericks",
    name2: "Thunder",
    score1: 100,
    score2: 120,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/xxxlj9RpmAKJ9P9phstWCQ_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/b4bJ9zKFBDykdSIGUrbWdw_96x96.png"
    },
    
    {
    name1: "Rockets",
    name2: "Warriors",
    score1: 80,
    score2: 90,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/zhO6MIB1UzZmtXLHkJQBmg_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/XD2v321N_-vk7paF53TkAg_96x96.png"
    },
    {
    name1: "Pistons",
    name2: "Knicks",
    score1: 80,
    score2: 90,
    img1: "https://ssl.gstatic.com/onebox/media/sports/logos/qvWE2FgBX0MCqFfciFBDiw_96x96.png",
    img2: "https://ssl.gstatic.com/onebox/media/sports/logos/-rf7eY39l_0V7J4ekakuKA_96x96.png"
    }
];



//main page
app.get("/",function(req,res){    
    res.render("landing",{past:past, cur: cur, future:future});
});


    
//search page
app.post("/search",function(req,res){
    var keyword =  req.body.name;
    
    //need to change this url
    var url = "http://128.253.128.67:20010/tweet/api/search/"+keyword;
    // console.log(url);
    
    request(url,function(err,resquest,body){
      if(err){
          console.log("something is wrong");
          console.log(err);
      } else {
          if(resquest.statusCode == 200){
              var tweets = JSON.parse(body);
              res.render("searchResults",{keyword: keyword, tweets:tweets});
          }
      }
    });   
});


app.get("/search/new", function(req,res){
    res.render("new");
});

//past games page
app.get("/past", function(req, res){
    var now = Math.floor((new Date()).getTime() / 60000)*60000;
    var before = now-1000000;
    var url1 = "http://128.253.128.67:20010/sentiment/api/team/7/between/"+before+"/and/"+now;
    var url2 = "http://128.253.128.67:20010/sentiment/api/team/25/between/"+before+"/and/"+now;
    //var url3 = "http://128.253.128.67:20010/hotspot/api/match/1";
    //need to change this url
    var url4 = "http://128.253.128.67:20010/sentiment/api/team/25/between/1526361480000/and/1526361660000";
    var url5 = "http://128.253.128.67:20010/sentiment/api/team/7/between/1526361480000/and/1526361660000"
    //var urls = [url4, url5];
    console.log(url1);
    console.log(url2);
    var urls =[ url1,url2];
    var tweets=[];
    // console.log(url);
    var completed_reqs = 0;
    for (var i=0; i<2; i++) {
    	request(urls[i],function(err,resquest,body){
      	if(err){
          	console.log("something is wrong");
          	console.log(err);
      } else {
          	if(resquest.statusCode == 200){
              	tweets.push(JSON.parse(body));
              	completed_reqs++;
              	if (completed_reqs == 2){
              		//console.log(tweets[0][0]);
                  //console.log("---------------------");
                  //console.log(tweets[1][0]);
              		res.render("past",{tweets:tweets});
                  //console.log(tweets[2]);
              	}
          }
       }
       }); 
    }
 
});

//ongoing games page


app.get("/data",function(req,res){
   res.render("data"); 
});
app.get("/hotspot",function(req,res){
   var tweets;
   var url3 = "http://128.253.128.67:20010/hotspot/api/match/1";
   request(url3,function(err,resquest,body){
        if(err){
            console.log("something is wrong");
            console.log(err);
      } else {
            if(resquest.statusCode == 200){
                tweets = JSON.parse(body);
                res.render("hotspot",{tweets:tweets});
          }
       }
       });  
});


app.listen("3000",function(){
    console.log("NBA fans server has started");
});
