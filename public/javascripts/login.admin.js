
$(document).ready(function(){
    startValidation();
});

function startValidation(){
     $('#frmLogin').validate({
         rules: {
            txtName: { required: true, minlength: 4, maxlength:12},
            txtPass: { required: true, minlength: 3, maxlength:12 }
         },
         submitHandler: function(form) {
            login();
         }
     });
}

function login(){
    var form = $("#frmLogin");
    var data = form.serialize();
    $.ajax({
        url:'/login/validate',
        type:'POST',
        data: data,
        success: function(data){
            if(data.success){
                window.location = "/dashboard";
            }else{
                humane.log(data.message);
            }
        }
    });
}

/*function logOut(){
    $.ajax({
        url:'/logout',
        type:'GET',
        data: data,
        success:function(data){
            humane.log(data.message)
            console.log(data)
            if(data.success==true){
                window.location = "/login";
            }
        }
    });
}*/

