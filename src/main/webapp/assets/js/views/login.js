const Login = {
    errorMessages = [],
    errorElement = null,

    onReady: () => {
        errorElement = document.querySelector("div-error");
    },

    login: (username, pass) => {
        username = String(username).trim().length == 0 ? "" : username;
        pass = String(pass).length == 0 ? "" : pass;

        if (username.length == 0)
        {
            Login.errorMessages.push("Username field is required.");
        }

        if (pass.length == 0)
        {
            Login.errorMessages.push("Password field is required.");
        }

        if (Login.errorMessages.length > 0)
            Login.showErrors();
        else
        {
            axios.post('/api/login', {
                username: username,
                password: pass
            })
            .then((response) => {
                window.location.href = "/message_board";
            })
            .catch((error) => {
                console.log(error);
                alert(error);
            });
        }
    },

    showErrors: () => {
        if (errorElement == null) return;

        let errorHTML = "";
        for (let i = 0; i < errorMessages.length; ++i)
            errorHTML += errorHTML + errorMessages[i] + "<br />";

        errorElement.innerHTML = errorHTML;
        Login.errorMessages = [];
    }
};

document.addEventListener("DOMContentLoaded", function(event) { 
    Login.onReady();
});