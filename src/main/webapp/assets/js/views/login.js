const Login = {
    errorMessages: [],
    errorElement: null,

    onReady: () => {
        Login.errorElement = document.querySelector("#div-error");

        document.querySelector("form.login-form #btn-login").addEventListener("click", () => {
            Login.login(document.querySelector("form.login-form [name='username']").value, document.querySelector("form.login-form [name='password']").value); 
        });
    },

    login: (username, pass) => {
        let loginButton = document.querySelector("form.login-form #btn-login");
        loginButton.disabled = true;

        username = String(username).trim().length == 0 ? "" : String(username);
        pass = String(pass).length == 0 ? "" : String(pass);

        if (username.length == 0)
        {
            Login.errorMessages.push("Username field is required.");
        }

        if (pass.length == 0)
        {
            Login.errorMessages.push("Password field is required.");
        }

        if (Login.errorMessages.length > 0)
        {
            Login.showErrors();
            loginButton.disabled = false;
        }
        else
        {
            axios.post('/api/login', {
                username: username,
                password: pass
            })
            .then((response) => {
                window.location.href = "/message_board";
                loginButton.disabled = false;
            })
            .catch((error) => {
                console.log(error.response);
                Login.errorMessages.push(error.response.data.message);
                Login.showErrors();
                loginButton.disabled = false;
            });
        }
    },

    showErrors: () => {
        if (Login.errorElement == null) return;

        if (Login.errorMessages.length == 0)
        {
            Login.errorElement.classList.remove("display-none");
            Login.errorElement.classList.add("display-none");
        }
        else
        {
            if (Login.errorElement.classList.contains("display-none"))
                Login.errorElement.classList.remove("display-none");
        }

        let errorHTML = "";
        for (let i = 0; i < Login.errorMessages.length; ++i)
            errorHTML += Login.errorMessages[i] + "<br />";

        Login.errorElement.innerHTML = errorHTML;
        Login.errorMessages = [];
    }
};

document.addEventListener("DOMContentLoaded", function(event) { 
    Login.onReady();
});