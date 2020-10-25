
const App = {
    onReady: () => {
        //
    },

    setTheme: (theme) => {
        theme = theme ? theme : "light";
        localStorage.setItem("SOEN387-theme", theme);

        document.body.classList.remove("app-light");
        document.body.classList.remove("app-dark");

        document.body.classList.add("app-" + theme);
    },
    getTheme: () => {
        let theme = localStorage.getItem("SOEN387-theme");
        theme = theme ? theme : "light";
        
        return theme;
    },
    toggleTheme: () => {
        if (App.getTheme() != "light")
            App.setTheme("light");
        else
            App.setTheme("dark");
    },

    // device info
    device: {
        // check if you are in a mobile browser
        isMobile: () => {
            return ( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) );
        },

        // check if touchEvent is enabled and working
        isTouchEnabled: () => {
            try { document.createEvent("TouchEvent"); return true; }
            catch(e) { return false; }
        }
    }
};

document.addEventListener("DOMContentLoaded", function(event) { 
    App.onReady();
    App.setTheme(App.getTheme());
});