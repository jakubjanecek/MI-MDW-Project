$(function() {
    $('.ui-state-default').hover(
        function(){
            $(this).addClass('ui-state-hover');
        },
        function(){
            $(this).removeClass('ui-state-hover');
        }
        );

    formDialog('AddCarButton', 'AddCarForm', 'Add car');
    editFormDialog('EditCarButton', 'EditCarForm', 'Edit car');
    confirmDialog('DeleteCarButton', 'ConfirmationDialog', 'Delete car', 'Do you really want to delete the car?', 'deleteCar');

    formDialog('AddDealerButton', 'AddDealerForm', 'Add dealer');
    editFormDialog('EditDealerButton', 'EditDealerForm', 'Edit dealer');
    confirmDialog('DeleteDealerButton', 'ConfirmationDialog', 'Delete dealer', 'Do you really want to delete the dealer?', 'deleteDealer');
            
    formDialog('AddBrandButton', 'AddBrandForm', 'Add brand');
    editFormDialog('EditBrandButton', 'EditBrandForm', 'Edit brand');
    confirmDialog('DeleteBrandButton', 'ConfirmationDialog', 'Delete brand', 'Do you really want to delete the brand?', 'deleteBrand');

    formDialog('AddModelButton', 'AddModelForm', 'Add model');

    formDialog('AddUserButton', 'AddUserForm', 'Add user');
    editFormDialog('EditUserButton', 'EditUserForm', 'Edit user');
    confirmDialog('DeleteUserButton', 'ConfirmationDialog', 'Delete user', 'Do you really want to delete the user?', 'deleteUser');

    confirmDialog('AdvertiseCarButton', 'ConfirmationDialog', 'Advertise car', 'Do you really want to advertise this car?', 'advertiseCar');

    confirmDialog('DeleteAutosalonOrder', 'ConfirmationDialog', 'Delete Autosalon order', 'Do you really want to delete your order?', 'deleteAutosalonOrder');
});
