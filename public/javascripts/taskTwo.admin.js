var gridButtons = "";
var gridStatus = "";
var table;

$(document).ready(function(){
    prepareButtons()
    initGrid()
});


function bindButtons(){
    $('#tblTask tbody tr td button').unbind('click').on('click',function(event){
        if(event.preventDefault) event.preventDefault();
        if(event.stopImmediatePropagation) event.stopImmediatePropagation();

        var obj = JSON.parse(Base64.decode($(this).parent().attr("data-row")));
        var action = $(this).attr("data-action");

        if(action=='edit'){ showDialog(obj.idTaskTwo); }
        else if(action=='delete'){ deleteRecord(obj.idTaskTwo); }
        //else if(action=='config'){ showDialogConfig(obj.idTaskTwo); }
    })
}

function prepareButtons(){
    var bodyButtons = $("#gridButtons").val();
    var tags = $("<div/>");
    tags.append(bodyButtons);

    $("#btnNew").click(function(){showDialog()});

    gridButtons = "<center>"+tags.html()+"</center>"
}


function initGrid(){
    table = $('#tblTask')
    .on('draw.dt',function(e,settings,json,xhr){
        setTimeout(function(){bindButtons();},500);
        //drawRowNumbers("#example",table);
     })
    .DataTable( {
        ajax: "/tasktwo",
        aoColumns: [
            { data: "idTaskTwo" },
            { data: "titleTask" },
            { data: "descriptionTask" },
            { data: "nombre" },
             {
                 sortable:false, searchable:false,
                 render:function(data,type,row){
                    return gridButtons.replace("{data}", Base64.encode(JSON.stringify(row)));
                 }
             }
        ]
    });
};


function deleteRecord(idTaskTwo){
console.log(idTaskTwo);
$.confirm({
    title: 'CONFIRMAR ACCION',
    content: '¿Desea eliminar el registro?',
    buttons: {
        Si: function(result) {
            if(result){
                $.ajax({
                    url:'/tasktwo/delete/' + idTaskTwo,
                    type:'DELETE',
                    success:function(data){
                        humane.log(data.message)
                        console.log(data)
                        if(data.success){
                            table.ajax.reload();
                        }
                    }
                });
            }
        },
        No: function () {
        }
    }
});
}

function loadData(idTaskTwo){
    var form = $("#frmTask");
    var txtTitle = $("#txtTitulo");
    var txtDescripcion = $("#txtDescripcion");
    var selPersona = $("#selPersona");
    console.log(selPersona);
        $.ajax({
            url: "/tasktwo/get/" + idTaskTwo,
            type:'GET',
            success:function(data){
                console.log("AQUIIIIIIIIIIIIII " + selPersona);
                if(data.success == true){
                    txtTitle.val(data.data.titleTask);
                    txtDescripcion.val(data.data.descriptionTask);
                    selPersona.select2("data",{id:data.data.idPer,text:data.data.nombre});
                }
            }
        });
}

function showDialog(idTaskTwo){
    var isEditing = !(typeof(idTaskTwo) === "undefined" || idTaskTwo === 0);

    dialog = bootbox.dialog({
        title: (isEditing ? "MODIFICAR" : "CREAR NUEVA TAREA"),
        message: $("#taskFormBody").val(),
        className:"modalSmall"
    });

    startValidation();
    initAsyncSelect2("selPersona", "/tasktwo/select");

    if(isEditing){
        //set val to inputHidden
        $("#idHidden").val(idTaskTwo);
        console.log("ID Persona a cargar: " + idTaskTwo);
        loadData(idTaskTwo);
    }
}


function save(){
    var form = $("#frmTask");
    var data = form.serialize();
    console.log(data);
    $.ajax({
        url: '/tasktwo/save',
        type:'POST',
        data: data,
        success:function(data){
            console.log(data);
            humane.log(data.message);
            if(data.success == true){
                table.ajax.reload();
                dialog.modal('hide');
            }
        }
    });
}

function startValidation(){
     $('#frmTask').validate({
         rules: {
            txtTitulo: { required: true, minlength: 2, maxlength:45},
            //txtEdad: { required: true, digits: true }
         },
         submitHandler: function(form) {
            save();
         }
     });
}