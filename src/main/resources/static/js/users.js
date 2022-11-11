// function changeCheckbox() {
//     $(".isAdmin, .isUser, .isEditor, .isActive").on("click", function () {
//         if ($(this).is('[checked]')) {
//             $(this).removeAttr('checked')
//         } else {
//             $(this).attr('checked', 'checked')
//         }
//     });
// }

function changeCheckbox(chkbox) {

        if ($(chkbox).is('[checked]')) {
            $(chkbox).removeAttr('checked')
        } else {
            $(chkbox).attr('checked', 'checked')
        }

}

$('.delete-user-btn').on('click', function () {
    let rowId = $(this).parents('tr').attr('id')
    let $row = $('#' + rowId);

    if (confirm('Are you sure?')) {
        $.ajax({
            url: "/api/admin/users/" + rowId.substring(4),
            method: "DELETE",
            success: function () {
                successNotify('user successfully deleted');
                $row.fadeOut(1000, function () {
                    $row.remove();

                })
            },
            error: function (xhr) {
                let message = JSON.parse(xhr.responseText)['message'];
                warningNotify(message)
            }
        });
    }
})

// $('.block-user-btn').on('click', function () {
//     let rowId = $(this).parents('tr').attr('id')
//     let $row = $('#' + rowId);
//
//     $(this).parents('tr').css('pointerEvents', 'none')
//     // alert('не доделано')
//     // if (confirm('Are you sure?')) {
//     //     $.ajax({
//     //         url: "/api/admin/users/" + rowId.substring(4) + '/block',
//     //         method: "POST",
//     //         success: function () {
//     //             successNotify('user successfully deleted');
//     //             $row.fadeOut(1000, function () {
//     //                 $row.remove();
//     //
//     //             })
//     //         },
//     //         error: function (xhr) {
//     //             let message = JSON.parse(xhr.responseText)['message'];
//     //             warningNotify(message)
//     //         }
//     //     });
//     // }
// })


$('.isActive').on('click', function () {
    changeCheckbox($(this))

    let id = $(this).closest('tr').attr('id').substring(4)
    let isActive = this.hasAttribute('checked');

    $.ajax({
        url: "/api/admin/users/" + id + '/block',
        method: "POST",
        data: {
            'locked': !isActive,
        },
        success: function () {
            successNotify(isActive === false ? 'user disabled' : 'user enabled')
        },
        error: function (xhr) {
            let message = JSON.parse(xhr.responseText)['message'];
            warningNotify(message)
            // $(this).fadeOut(1000).fadeIn.attr('checked', 'checked')
        }
    });
            $(this).closest('tr').attr("data-user-enabled", isActive);
})


// function enable(chkbox, id) {
//     let enabled = chkbox.is(":checked");
//     chkbox.closest("tr").attr("data-user-enabled", enabled);
//  https://stackoverflow.com/a/22213543/548473
//     $.ajax({
//         url: userAjaxUrl + id,
//         type: "POST",
//         data: "enabled=" + enabled
//     }).done(function () {
//         chkbox.closest("tr").attr("data-user-enabled", enabled);
//         successNoty(enabled ? "common.enabled" : "common.disabled");
//     }).fail(function () {
//         $(chkbox).prop("checked", !enabled);
//     });
// }