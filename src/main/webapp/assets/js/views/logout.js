
const Logout = {
    onReady: () => {
        Logout.logout();
    },

    logout: () => {
        axios.delete('/api/login')
        .then((response) => {
            window.location.href = "/login";
        })
        .catch((error) => {
            console.log(error.response.data);
        });
    }
};

document.addEventListener("DOMContentLoaded", function(event) { 
    Logout.onReady();
});