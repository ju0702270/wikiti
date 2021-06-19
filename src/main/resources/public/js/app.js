
//const locale = document.documentElement.lang;//récupération de la langue du document


function getListGrade(){
    $.ajax({
        type : 'GET',
        url : '/admin/listGrade',
        dataType : 'json',
        contentType : 'application/json',
        success : function(result) {
            $("#GradeModal").empty();
            result.forEach(grade => {
                $("#GradeModal").append(
                    '<option value="'+grade.id+'">'+grade.min+' ('+grade.administratif+')</option>'
                ); 
            });
        }
    });
}
function getListDegreSecurite(){
    $.ajax({
        type : 'GET',
        url : '/admin/listDegreSecurite',
        dataType : 'json',
        contentType : 'application/json',
        success : function(result) {
            $("#DegreSecuriteModal").empty();
            result.forEach(degreSecurite => {
                $("#DegreSecuriteModal").append(
                    '<option value="'+degreSecurite.id+'">'+degreSecurite.libelle+'</option>'
                ); 
            });
        }
    });
}
function getListFonction(){
    $.ajax({
        type : 'GET',
        url : '/admin/listFonction',
        dataType : 'json',
        contentType : 'application/json',
        success : function(result) {
            $("#FonctionModal").empty();
            result.forEach(fonction => {
                $("#FonctionModal").append(
                    '<option value="'+fonction.id+'">'+fonction.libelle+'</option>'
                ); 
            });
        }
    });
}




/**
 * Fonction de retrait des message de validation d'un formulaire
 * @param {String} formElement : l'id du formulaire à nettoyer
 */
function clearValidation(formElement){
    var validator = $('#'+formElement).validate();
        $('[name]',formElement).each(function(){
        validator.successList.push(this);
        validator.showErrors();
        });
        validator.resetForm();
        validator.reset();
}

/**
 * Chargement de donnée pour les Select de la modal UpdateModal
 */
$('.OpenUpdateModal').click(e=> {
    getListGrade();
    getListDegreSecurite();
    getListFonction();
});

/**
 * validation de saveUtilisateurForm
 */
$('#saveUtilisateurForm').validate({
    rules:{
        matricule:{
            required:true,
            minlength:7,
            digits: true
        },
        nom:{
            required:true,
            minlength:2,
        },
        prenom:{
            required:true,
            minlength:2,
        },
        mail:{
            required: true, 
            email:true
        }
    }
})

/**
 * Sauvegarde de l'utilisateur se trouvant dans le formulaire saveutilisateurForm
 */
$('#saveUtilisateurForm').submit(function(e){
    console.log("saveUser");
    e.preventDefault();
    let dataForm= $(this).serializeArray();
    let data= new Map();
    dataForm.forEach(d => {
        data[d.name]= d.value;
    });
    console.log(data)
    $.ajax({
        type : 'POST',
        url : '/admin/saveUtilisateur',
        data : JSON.stringify(data),
        dataType : 'json',
        contentType : 'application/json',
        success : function(result) {
            console.log(result);
        }
    });
    
});
/**
 * Action prise lors de la fermeture de la modal #updateModal
 * Reset du formulair + clear ddes messages de validation
 */
$('#updateModal').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
    clearValidation('saveUtilisateurForm');
})






 


