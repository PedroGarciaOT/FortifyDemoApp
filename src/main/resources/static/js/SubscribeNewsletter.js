$.fn.SubscribeNewsletter = function (options) {
    return this.each(function (index, el) {

        var settings = $.extend({
            color: "#556b2f",
            backgroundColor: "white"
        });

        var $this = $(this), $email = $this.find('#email-subscribe-input');
        $this.find('#email-subscribe-button').on('click', function () {
            if (_validateEmail($email.val())) {
                _saveEmail($email.val()).then(response => {
                    _showConfirmationText("Thankyou your email address has been registered.", $email.val(), "text-success");
                }).catch(error => {
                    _showConfirmationText("There was an error registering your email address.", $email.val(), "text-danger");
                });
            } else {
                _showConfirmationText("Please supply a valid email address.", $email.val(), "text-danger");
            }
        });
    });

    function _showConfirmationText(text, email, cssClass) {
        const confirmationH5 = document.createElement("h4");
        confirmationH5.classList.add(cssClass);
        confirmationH5.innerHTML = text + "<br/><h5>" + email + "</h5>";
        const confirmationDiv = document.createElement("div");
        confirmationDiv.classList.add("m-4", "text-center");
        confirmationDiv.appendChild(confirmationH5);
        $('#confirmation-modal').find('#confirmation-modal-body').empty().append(confirmationDiv);
        $('#confirmation-modal').modal('toggle');
        return confirmationDiv;
    }

    async function _saveEmail(email) {
        let data = JSON.stringify(
            {
                firstName: "",
                lastName: "",
                email: email
            }
        )
        return await $.ajax({
            url: '/api/subscribe-user',
            type: 'POST',
            contentType: 'application/json',
            data: data
        }).then();
    }

    function _validateEmail(email) {
        var emailExpression = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return emailExpression.test(email);
    }
};
