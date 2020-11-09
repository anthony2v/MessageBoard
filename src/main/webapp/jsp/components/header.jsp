<header>
    <nav class="navbar navbar-expand-lg fixed-top">
        <button class="navbar-toggler p-0 border-0 mr-3" type="button">
            <i class="fas fa-bars"></i>
        </button>
        <a class="navbar-brand text-decoration-none" href="/">SOEN 387</a>

        <div class="sidenav">
            <ul class="navbar-nav navbar-nav-left mr-auto">
                <li class="nav-item">
                    <a class="nav-link text-decoration-none" href="/message_board">Message Board</a>
                </li>
            </ul>
        </div>
        <div class="sidenav-overlay"></div>

        <ul class="navbar-nav navbar-nav-right ml-auto">
<%          if (session == null || session.getAttribute("user") == null)
            { %>
            <li class="nav-item">
                <a class="nav-link text-decoration-none" href="/register"><i class="fas fa-user-plus mr-2"></i>Sign up</a>
            </li>
            <li id="btn-login" class="nav-item">
                <a class="nav-link text-decoration-none" href="/login"><i class="fas fa-sign-in-alt mr-2"></i>Log in</a>
            </li>
<%          } 
            else 
            { %>
            <li id="btn-logout" class="nav-item">
                <a class="nav-link text-decoration-none" href="/logout"><i class="fas fa-sign-out-alt mr-2"></i>Logout</a>
            </li>
<%          } %>
            <li>
                <button class="nav-link ml-3 theme-switch btn btn-link text-decoration-none">
                    <input type="checkbox" id="cbox-theme" />
                    <label class="" for="cbox-theme">
                        <span class="lbl-off">LIGHT</span>
                        <span class="lbl-on">DARK</span>
                    </label>
                </button>
            </li>
        </ul>
    </nav>
</header>

<script type="text/javascript" src="/assets/js/components/header.js"></script>