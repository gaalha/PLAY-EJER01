var gridButtons = "";
var gridStatus = "";
var table;

$(document).ready(function(){
    prepareButtons()
    initGrid()
});

function bindButtons(){
    $('#tblPerson tbody tr td button').unbind('click').on('click',function(event){
        if(event.preventDefault) event.preventDefault();
        if(event.stopImmediatePropagation) event.stopImmediatePropagation();

        var obj = JSON.parse(Base64.decode($(this).parent().attr("data-row")));
        var action = $(this).attr("data-action");

        if(action=='edit'){ showDialog(obj.idPersona); }
        else if(action=='delete'){ deleteRecord(obj.idPersona); }
        //else if(action=='config'){ showDialogConfig(obj.idPersona); }
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
    table = $('#tblPerson')
    .on('draw.dt',function(e,settings,json,xhr){
        setTimeout(function(){bindButtons();},500);
        //drawRowNumbers("#example",table);
     })
    .DataTable( {
        ajax: "/person",
        aoColumns: [
            { data: "idPersona" },
            { data: "nombre"},
            {
                data: "edad",
                render:function(data,type,row){
                    return data+" años";
                }
            },
             {
                 sortable:false, searchable:false,
                 render:function(data,type,row){
                    //console.log(data);
                    return gridButtons.replace("{data}", Base64.encode(JSON.stringify(row)));
                 }
             }
        ]
    });
};

function deleteRecord(idPersona){
    bootbox.confirm("Desea Eliminar?", function(result) {
        if(result){
            $.ajax({
                url:'/eliminar/'+idPersona,
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
    });
}

function loadData(idPersona){
    var form = $("#frmPerson");
    var txtNombre = $("#txtNombre");
    var txtEdad = $("#txtEdad");
    //var selCountryState = $("#selCountryState");
    //var status = $("#checkStatus");
        $.ajax({
            url: "/getPeople/" + idPersona,
            type:'GET',
            success:function(data){
                console.log(data);
                if(data.success == true){
                    txtNombre.val(data.data.nombre);
                    txtEdad.val(data.data.edad);
                }
            }
        });
}

function showDialog(idPersona){
    var isEditing = !(typeof(idPersona) === "undefined" || idPersona === 0);

    dialog = bootbox.dialog({
        title: (isEditing ? "MODIFICAR" : "CREAR NUEVO"),
        message: $("#peopleFormBody").val(),
        className:"modalSmall"
    });

   startValidation();

    if(isEditing){
        //set val to inputHidden
        $("#idHidden").val(idPersona);
        console.log("ID Persona a cargar: " + idPersona);
        loadData(idPersona);
    }
}


function save(){
    var form = $("#frmPerson");
    var data = form.serialize();
    $.ajax({
        url: '/person/save',
        type: 'POST',
        data: data,
        success:function(data){
            //console.log(data);
            humane.log(data.message);
            if(data.success==true){
                table.ajax.reload();
                dialog.modal('hide');
            }
        }
    });
}

function startValidation(){
     $('#frmPerson').validate({
         rules: {
            txtNombre: { required: true, minlength: 2, maxlength:45},
            txtEdad: { required: true, digits: true }
         },
         submitHandler: function(form) {
            save();
         }
     });
}