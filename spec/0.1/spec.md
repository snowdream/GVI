# GVI SPEC 0.1

## What is GVI
GVI (the GPS Exchange Format) is a light-weight json data format for the interchange of GPS data between applications and Web services on the Internet.

In Fact, GVI is a json file,and end with the postfix ".gvi".


## GVI SPEC
```json
{
  "version":"0.1",  
  "creator":"Mock GPS Player",
  "provider":"autonavi",
  "name":"Wuyuan",
  "desc":"Located in northeasten Jiangxi province, Wuyuan is a north county of Shangrao city, which is believed as the cradle of Huizhou culture. Known as \"the most beautiful village of China\",\"home to books\" and \"home to tea\", Wuyuan has become an attractive senery spot to to people all over the world. The most attracting sightseeings are the rape flowers and Hui style architecture.",
  "author":"snowdream",
  "copyright":"CC-BY-NC-SA 3.0",
  "link":"http://www.wylyw.cn/",
  "time":"2016-04-19 18:47:46.203 +0800",
  "keywords":"Wuyuan",
  "bounds":{
      "minlongitude":117.886047,
      "minlatitude":29.328013,
      "maxlongitude":117.886047,
      "maxlatitude":29.328013
  },
  "track":[
  {
    "type": 1,
    "start":{
      "longitude":117.886047,
      "latitude":29.328013
    },
    "end":{
      "longitude":117.886047,
      "latitude":29.328013
    },
    "middle":[{
      "longitude":117.886047,
      "latitude":29.328013
    },
    {
      "longitude":117.886047,
      "latitude":29.328013
    },
    {
      "longitude":117.886047,
      "latitude":29.328013
    }],
    "loc":[
      {
        "time":"2016-04-19 18:47:46.203 +0800",
        "elapsedrealtimenanos":0,
        "longitude":117.886047,
        "latitude":29.328013,
        "altitude":50,
        "speed":0,
        "bearing":0,
        "accuracy":50
      },
      ...            
    ]
  },
  ...
  ]
}
```
