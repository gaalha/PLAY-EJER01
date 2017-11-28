var gridButtonsPerson = "";
var gridStatus = "";
var table;

$(document).ready(function(){
    prepareButtonsPerson()
    initGridPerson()
});

// /////////////////////////////////////// PERSONA
function bindButtons(){
    $('#tblPerson tbody tr td button').unbind('click').on('click',function(event){
        if(event.preventDefault) event.preventDefault();
        if(event.stopImmediatePropagation) event.stopImmediatePropagation();

        var obj = JSON.parse(Base64.decode($(this).parent().attr("data-row")));
        var action = $(this).attr("data-action");

        if(action=='edit'){ showDialogPerson(obj.idPersona); }
        else if(action=='delete'){ deleteRecord(obj.idPersona); }
        else if(action=='task'){ showGridTask(obj.idPersona, obj.nombre); }
    })
}

function prepareButtonsPerson(){
    var bodyButtons = $("#gridButtonsPerson").val();
    var tags = $("<div/>");
    tags.append(bodyButtons);
    $("#btnNewPerson").click(function(){showDialogPerson()});
    $("#btnOpenTaskGrid").click(function(){showGridTask()});
    gridButtonsPerson = "<center>"+tags.html()+"</center>"
}


function initGridPerson(){
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
                    return gridButtonsPerson.replace("{data}", Base64.encode(JSON.stringify(row)));
                 }
             }
        ]
    });
    //$('#example').removeClass('display').addClass('table table-bordered table-hover dataTable');
};


function loadDataPerson(idPersona){
    var form = $("#frmPerson");
    var txtNombre = $("#txtNombre");
    var txtEdad = $("#txtEdad");
    //var selCountryState = $("#selCountryState");
    //var status = $("#checkStatus");
        $.ajax({
            url: "/person/get/" + idPersona,
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

function showDialogPerson(idPersona){
    var isEditing = !(typeof(idPersona) === "undefined" || idPersona === 0);

    dialog = bootbox.dialog({
        title: (isEditing ? "MODIFICAR" : "CREAR NUEVO"),
        message: $("#peopleFormBody").val(),
        className:"modalSmall"
    });

   startValidation();

    if(isEditing){
        $("#idHidden").val(idPersona);
        console.log("ID Persona a cargar: " + idPersona);
        loadDataPerson(idPersona);
    }
}


function deleteRecord(idPersona){
    bootbox.confirm("Desea Eliminar?", function(result) {
        if(result){
            $.ajax({
                url:'/person/delete/'+idPersona,
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


function save(){
    var form = $("#frmPerson");
    var data = form.serialize();
    $.ajax({
        url: '/person/save',
        type: 'POST',
        data: data,
        success:function(data){
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

/*******************************************************************
********************************************************************
                      MOSTRAR EL GRID PARA TAREAS
********************************************************************
********************************************************************/

var TaskGridButtons = "";
var TaskGridStatus = "";
var taskTable;

function showGridTask(idPersona, nombre){
    dialog = bootbox.dialog({
        title: ("GESTION DE TAREAS POR PERSONA"),
        message: $("#taskGridBody").val(),
        size: "large"
    });

    $("#personIdHidden").val(idPersona);
    $("#personNameHidden").val(nombre);

    prepareButtonsTask();
    initGridTask(idPersona);
}

function prepareButtonsTask(){
    var bodyButtons = $("#gridButtonsTask").val();
    var tags = $("<div/>");
    tags.append(bodyButtons);

    $("#btnNewTask").click(function() {showTaskDialog() });

    TaskGridButtons = "<center>"+tags.html()+"</center>";
}

function bindTaskButtons() {
    $('#tblTask tbody tr td button').unbind('click').on('click', function(event){
        if(event.preventDefault) event.preventDefault();
        if(event.stopImmediatePropagation) event.stopImmediatePropagation();
        var obj =  JSON.parse(Base64.decode($(this).parent().attr("data-row")));
        var action = $(this).attr("data-action");

        if(action == 'editTask'){showTaskDialog(obj.idTaskTwo);}
        else if(action == "deleteTask"){deleteTaskRecord(obj.idTaskTwo);}
    })
}

function initGridTask(idPersona){
    taskTable = $('#tblTask')
        .on('draw.dt', function(e, settings, json, xhr){
            setTimeout(function(){bindTaskButtons();}, 500);
        })
        .DataTable({
            ajax: "/tasktwo/person/" + idPersona,
            aoColumns: [
                { data: "idTaskTwo", sortable:false, searchable:false },
                { data: "titleTask" },
                { data: "descriptionTask" },
                {
                    sortable: false, searchable:false,
                    render: function(data, type, row, meta){
                        return TaskGridButtons.replace("{data}", Base64.encode(JSON.stringify(row)));
                    }
                }
            ]
        });
}

function loadTaskData(idTaskTwo){
    var form = $("#frmTask");
    var title = $("#txtTitulo");
    var description = $("#txtDescripcion");
    var person = $("#selPersona");

    $.ajax({
        url: "/tasktwo/get/" + idTaskTwo,
        type: 'GET',
        success: function(data){
            if(data.success == true){
                title.val(data.data.titleTask);
                description.val(data.data.descriptionTask);
                person.select2("data",{id:data.data.idPersona,text:data.data.nombre});
            }
        }
    });
}

function loadPersonSelect2(idPersona){
    var person = $("#selPersona");
    var personId = $("#personIdHidden").val();

    $.ajax({
        url: "/person/get/" + personId,
        type: 'GET',
        success: function(data){
            if(data.success == true){
                person.select2("data",{id:data.data.idPersona,text:data.data.nombre}).select2('disable');
            }
        }
    });
}


function showTaskDialog(idTaskTwo){
    var isEditing = !(typeof(idTaskTwo) === "undefined" || idTaskTwo === 0);
    var personId = $("#personIdHidden");

    dialog = bootbox.dialog({
        title: (isEditing ? "EDITAR" : "NUEVO"),
        message: $("#taskFormBody").val(),
        className: "modalSmall"
    });
    initAsyncSelect2("selPersona", "/tasktwo/select");
    startTaskValidation();

    if(isEditing){
        $("#idHidden").val(idTaskTwo);
        loadTaskData(idTaskTwo);
    }else{
        loadPersonSelect2(personId);
    }
}

function startTaskValidation(){
    $("#frmTask").validate({
        rules: {
            txtTitulo: { required: true, minlength: 5, maxlength: 150 },
            txtDescripcion: {required: true, minlength: 10, maxlength: 200 }
        },
        submitHandler: function(form){
            saveTask();
        }
    })
}

function saveTask(){
    var person = $("#selPersona");
    person.select2('enable');

    var form = $("#frmTask");
    var data = form.serialize();

    $.ajax({
        url: '/tasktwo/save',
        type: 'POST',
        data: data,
        success: function(data){
            humane.log(data.message);
            if(data.success == true){
                taskTable.ajax.reload();
                dialog.modal('hide');
            }
        }
    });
}

function deleteTaskRecord(idTaskTwo){
    bootbox.confirm("Desea Eliminar?", function(result) {
        if(result){
            $.ajax({
                url:'/tasktwo/delete/' + idTaskTwo,
                type:'DELETE',
                success:function(data){
                    humane.log(data.message)
                    if(data.success){
                        taskTable.ajax.reload();
                    }
                }
            });
        }
    });
}

