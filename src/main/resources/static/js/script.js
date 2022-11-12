function getFileName(inputName) {
    let filename = $('#' + inputName).val();
    const lastIndex = filename.lastIndexOf("\\");
    if (lastIndex >= 0) {
        filename = filename.substring(lastIndex + 1);
    }
    $('#' + inputName + '-label').html(filename);
}

$("#logout-btn").on('click', function (e) {
    e.preventDefault();
    logout();
});

function logout() {
    $.ajax({
        url: "/logout",
        method: "POST",
        success: function () {
            window.location.href = "/";
        }
    });
}

$("#signIn-btn").on("click", function () {
    let email = $('#username').val();
    let password = $('#password').val();
    if (email === null || email === '') {
        warningNotifyBottomOfTheInputField($('#username'), 'you forgot to enter your email address')
    } else {
        signIn(email, password);
    }
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


$('#signUp-form-btn').on('click', function () {
    let nickname = $('#nicknameSignUpInput').val();
    let password = $('#passwordSignUpInput').val();
    let email = $('#emailSignUpInput').val();

    $('#sign-up-form *input').each(function () {
        if (!$(this).val()) {
            warningNotifyLeftOfTheInputField($(this), 'cannot be empty')
        }
    });

    if (checkInputIsValid(email) && checkInputIsValid(password) && checkInputIsValid(nickname)) {
        $.ajax({
            url: "/api/v1/registration",
            method: "POST",
            data: {
                "nickname": nickname,
                "email": email,
                "password": password
            },
            success: function () {
                let text = 'We have sent an activation email to ' + email + '. ' +
                    'Follow the link provided in it to start the procedure. ' +
                    'If for some reason you haven\'t received the email, try changing the address again or write to us - nb4news@gmail.com.'
                successNotify(parseMessageToUnorderedListByOneDotForNotify(text))
            },
            error: function (xhr) {
                warningNotify(parseMessageToUnorderedListByOneDotForNotify(JSON.parse(xhr.responseText)['message']))
            }
        });
    }
});

function parseMessageToUnorderedListByOneDotForNotify(text) {
    let messageList = '<ul>'
    let message = text.replaceAll('. ', '.. ').split('. ')
    $(message).each(function () {
        messageList += "<li>" + this + "</li>";
    })
    messageList += '</ul>'
    return messageList;
}

function checkInputIsValid(input) {
    return (input != null && input !== '');
}

function successNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position: 'right',
        className: 'success'
    })
}

function infoNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position: 'right',
        className: 'info'
    })
}

function warningNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position: 'right',
        className: 'warn'
    })
}

function warningNotifyLeftOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position: 'left',
        className: 'warn'
    })
}

function warningNotifyBottomOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position: 'bottom center',
        className: 'warn'
    })
}

function errorNotifyRightOfTheInputField(inputField, text) {
    $(inputField).notify(text, {
        position: 'right',
        className: 'error'
    })
}

function warningNotify(text) {
    toastr.options = {
        "progressBar": true,
        "timeOut": "3000",
        "positionClass": 'toast-bottom-right'
    }
    toastr['warning'](text)
}

function successNotify(text) {
    toastr.options = {
        "progressBar": true,
        "timeOut": "3000",
        "positionClass": 'toast-bottom-right'
    }
    toastr['success'](text)
}

$('#temp-mail-redirect').click(function () {
    window.open('https://temp-mail.org/', '_blank')
});

$('a.source-logo').click(function (e) {
    e.preventDefault()
    window.open(this.href, '_blank')
})