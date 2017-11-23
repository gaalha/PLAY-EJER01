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

        if(action=='edit'){ showDialog(obj.idtask); }
        else if(action=='delete'){ deleteRecord(obj.idtask); }
        else if(action=='config'){ showDialogConfig(obj.idtask); }
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
        ajax: "/task",
        aoColumns: [
            { data: "idtask" },
            { data: "title" },
            { data: "description" },
            { data: "nombre" },
             {
                 sortable:false, searchable:false,
                 render:function(data,type,row){
                    return gridButtons.replace("{data}", Base64.encode(JSON.stringify(row)));
                 }
             }
        ]
    });
    console.log("description");
};


function deleteRecord(idtask){
$.confirm({
    title: 'CONFIRMAR',
    content: 'Desea eliminar?',
    buttons: {
        Confirmar: function(result) {
            if(result){
                $.ajax({
                    url:'/task/delete/'+idtask,
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
        Cancelar: function () {
        }
    }
});
}

function loadData(idtask){
    var form = $("#frmTask");
    var txtTitle = $("#txtTitulo");
    var txtDescripcion = $("#txtDescripcion");
    var selPersona = $("#selPersona");
        $.ajax({
            url: "/task/get/" + idtask,
            type:'GET',
            success:function(data){
                console.log(data);
                if(data.success == true){
                    txtTitle.val(data.data.title);
                    txtDescripcion.val(data.data.description);
                    selPersona.select2("data",{id:data.data.idPersona,text:data.data.nombre});
                }
            }
        });
}

function showDialog(idtask){
    var isEditing = !(typeof(idtask) === "undefined" || idtask === 0);

    dialog = bootbox.dialog({
        title: (isEditing ? "MODIFICAR" : "CREAR NUEVA TAREA"),
        message: $("#taskFormBody").val(),
        className:"modalSmall"
    });

    startValidation();

    initAsyncSelect2("selPersona", "/task/view/selectPeople");

    if(isEditing){
        //set val to inputHidden
        $("#idHidden").val(idtask);
        console.log("ID Persona a cargar: " + idtask);
        loadData(idtask);
    }
}


function save(){
    var form = $("#frmTask");
    var data = form.serialize();
    console.log(data);
    $.ajax({
        url: '/task/insert',
        type:'POST',
        data: data,
        success:function(data){
            console.log(data);
            humane.log(data.message);
            if(data.success==true){
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