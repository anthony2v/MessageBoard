
const Header = {
    init: () => {
        CommonUtil.includeStylesheet("/assets/css/components/header.css");

        document.querySelector("header nav .navbar-toggler").addEventListener("click", (event) => {
            document.querySelector("header nav .sidenav").classList.toggle('open');
        });
        window.addEventListener('click', (event) => {   
            let sidenavEl = document.querySelector("header nav .sidenav");
            let btnNavbarToggler = document.querySelector("header nav .navbar-toggler");
            if (sidenavEl.classList.contains("open") && !btnNavbarToggler.contains(event.target) && !sidenavEl.contains(event.target))
                sidenavEl.classList.toggle("open");
        });

        Header.updateThemeSwitch();
        document.querySelector("header .theme-switch input").addEventListener("change", (event) => {
            App.toggleTheme();
        });
    },

    updateThemeSwitch: () => {
        let cboxThemeSwitch = document.querySelector("header .theme-switch input");
        if (document.body.classList.contains("app-dark"))
            cboxThemeSwitch.checked = true;
    }
};

document.addEventListener("DOMContentLoaded", function(event) { 
    Header.init();
});