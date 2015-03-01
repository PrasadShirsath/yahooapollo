$(document).ready(function(){
        
    $("#critical-btn").onclick =function(){
        alert('Button clikc');
            $("#critical-table").datatable({
              data:[data:{"severity":5,"count":8,"node":"flickr_db3","property":"Flickr","firstOccurence":1425032631000,"summary":"Down to ping","latestTime":1425033111000}]
            });
    };   
});