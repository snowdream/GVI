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
  "keywords":"Wuyuan,Jiangxi",
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
      "latitude":29.328013,
      "name":"start"
    },
    "end":{
      "longitude":117.886047,
      "latitude":29.328013,
      "name":"end"
    },
    "middle":[{
      "longitude":117.886047,
      "latitude":29.328013,
      "name":"middle0"
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

## HEAD
1. verion, should be 0.1    required.
1. creator, which tool create the gvi file. if not set,"Mock GPS Player" is the default value.  required.
1. provider, which is the location provider. usually,it should be devices, or location provider. suck as, device, autonavi, baidu, google,openstreetmap etc.if not set,"device" is the default value. required.
1. name,desc,author,copyright,link,time,keywords are optional.
1. bound, is the bound of your tracks.  required.

## BODY
1. track,contains many Navi Segments, which seperated by navi types. The navi types are driving,transit,walking,cycling,flights etc.  required.
1. type, the navi type of the Navi Segment.   required.
```
driving: 0
transit: 1
walking: 2
cycling: 3
flights: 4
```
1. start,the start geopoint of the navi segment. required.
1. end,the end geopoint of the navi segment. required.
1. middle,the middle geopoints of the navi segment. optional.
1. loc, locations of the navi segment.  required.
