function formDialog(buttonID, formID, title) {
    $('#' + buttonID).click(function() {
        $('#' + formID + 'Div').dialog({
            'modal': true,
            'title': title,
            'buttons': {
                "Cancel": function() {
                    $(this).dialog('close')
                },
                "Add": function() {
                    submitForm($('#' + formID));
                    $(this).dialog('close');
                    setTimeout(function() {
                        location.reload();
                    }, "300")
                }
            }
        });
        return false;
    });
}

function editFormDialog(buttonClass, formID, title) {
    $('.' + buttonClass).click(function() {
        button = $(this);
        $('.' + formID + 'Div-' + $(button).attr('id')).dialog({
            'modal': true,
            'title': title,
            'buttons': {
                "Cancel": function() {
                    $(this).dialog('close')
                },
                "Edit": function() {
                    submitForm($('.' + formID + '-' + $(button).attr('id')));
                    $(this).dialog('close');
                    setTimeout(function() {
                        location.reload();
                    }, "300")
                }
            }
        });
        return false;
    });
}

function confirmDialog(buttonClass, divID, title, question, url) {
    $('.' + buttonClass).click(function() {
        button = $(this);
        $('#' + divID).html(question);
        $('#' + divID).dialog({
            'modal': true,
            'title': title,
            'buttons': {
                "No": function() {
                    $(this).dialog('close')
                },
                "Yes": function() {
                    var dialog = $(this);
                    $.get('/admin?action=' + url + '&id=' + $(button).attr('id'), function() {
                        dialog.dialog('close');
                        setTimeout(function() {
                            location.reload();
                        }, "300")
                    });
                }
            }
        });
        return false;
    });
}

function buyDialog(buttonClass, formID, title) {
    $('.' + buttonClass).click(function() {
        button = $(this);
        $('.' + formID + 'Div-' + $(button).attr('id')).dialog({
            'modal': true,
            'title': title,
            'buttons': {
                "Cancel": function() {
                    $(this).dialog('close')
                },
                "Buy": function() {
                    submitForm($('.' + formID + '-' + $(button).attr('id')));
                    $(this).dialog('close');
                    setTimeout(function() {
                        location.reload();
                    }, "300")
                }
            }
        });
        return false;
    });
}

function askDialog(buttonClass, formID, title) {
    $('.' + buttonClass).click(function() {
        button = $(this);
        $('.' + formID + 'Div-' + $(button).attr('id')).dialog({
            'modal': true,
            'title': title,
            'width': 470,
            'height': 390,
            'buttons': {
                "Cancel": function() {
                    $(this).dialog('close')
                },
                "Send": function() {
                    submitForm($('.' + formID + '-' + $(button).attr('id')));
                    $(this).dialog('close');
                    setTimeout(function() {
                        location.reload();
                    }, "300")
                }
            }
        });
        return false;
    });
}

function submitForm(form) {
    $.post($(form).attr('action'), $(form).serialize());
}