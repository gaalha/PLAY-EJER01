$(document).ready(function(){
    data();
});

function data(){
    var element = document.getElementById("pNombre");
    $.ajax({
         url:'/name',
         type:'GET',
         data: data,
         success: function(data){
             console.log(data);
             element.innerHTML = data;
         }
     });
}