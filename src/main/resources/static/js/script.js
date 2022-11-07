function getFileName(inputName) {
    let filename = $('#' + inputName).val();
    const lastIndex = filename.lastIndexOf("\\");
    if (lastIndex >= 0) {
        filename = filename.substring(lastIndex + 1);
    }
    $('#' + inputName + '-label').html(filename);
};

$("#logout-btn").on('click', function (e) {
    e.preventDefault();
    $.ajax({
        url: "/logout",
        method: "POST",
        success: function () {
            window.location.href = "/";
        }
    });
});

$("#signIn-btn").on("click", function () {
    let email = $('#username').val();
    let password = $('#password').val();
    signIn(email, password);
})

function signIn(email, password) {
    $.ajax({
        url: "/login",
        method: "POST",
        data: {
            "password": password,
            "username": email
        },
        success: function () {
            window.location.href = "/news";
        },
        error: function (xhr) {
            let message = JSON.parse(xhr.responseText)['message'];
            warningNotify(message)
        }
    });
}

function warningNotify(text) {
    toastr.options = {
        positionClass: 'toast-bottom-right'
    }
    toastr['warning'](text)
}

function successNotify(text) {
    toastr.options = {
        positionClass: 'toast-bottom-right'
    }
    toastr['success'](text)
}

$('#signUp-form-btn').on('click', function () {
    let test = $(this)
    // successNotifyRightOfTheInputField($(this), 'successTest')
    // infoNotifyRightOfTheInputField($(this), 'infoTest')
    // warningNotifyRightOfTheInputField($(this), 'warningTest')
    errorNotifyRightOfTheInputField($(this), 'errorTest')
})

function successNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position : 'right',
        className : 'success'
    })
}

function infoNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position : 'right',
        className : 'info'
    })
}

function warningNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position : 'right',
        className : 'warn'
    })
}

function errorNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position : 'right',
        className : 'error'
    })
}

$('#temp-mail-redirect').click(function() {
    window.open('https://temp-mail.org/', '_blank')
});