<%@ page import="java.io.File" %>
<%@ page import="com.fruitforloops.model.User" %>

<script type="text/javascript">
const Server = {
    session: {
        user: {
<%          
			User user = session != null ? (User) session.getAttribute("user") : null;
			if (user != null)
			{
			    out.println("username: '" + user.getUsername() + "'");
			}
%>
        }
    }
};
</script>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,shrink-to-fit=no" />

<link rel="icon" type="image/png" href="/assets/media/favicon.ico" />

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,300i,400,400i,700,700i" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.6/flatpickr.min.css" integrity="sha512-OtwMKauYE8gmoXusoKzA/wzQoh7WThXJcJVkA29fHP58hBF7osfY0WLCIZbwkeL9OgRCxtAfy17Pn3mndQ4PZQ==" crossorigin="anonymous" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.6/themes/dark.min.css" integrity="sha512-9ILigk7+SdhDDU3AxNcp/jz8KYo8hj6l9v5iIPQj127U/zy7kqE518HUKXjWgfZsRyZc7glx/Lw4V10ESjkOKQ==" crossorigin="anonymous" />
<link rel="stylesheet" href="/assets/css/lib/fontawesome-free-5.14.0-web/css/all.min.css" />
<link rel="stylesheet" href="/assets/css/core/styles.css?r=<% out.print(new File(application.getRealPath("assets/css/core/styles.css")).lastModified()); %>" />
<link rel="stylesheet" href="/assets/css/core/app.css?r=<% out.print(new File(application.getRealPath("assets/css/core/app.css")).lastModified()); %>" />
<link rel="stylesheet" href="/assets/css/core/app-dark.css?r=<% out.print(new File(application.getRealPath("assets/css/core/app-dark.css")).lastModified()); %>" />

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.20.0/axios.min.js" integrity="sha512-quHCp3WbBNkwLfYUMd+KwBAgpVukJu5MncuQaWXgCrfgcxCJAq/fo+oqrRKOj+UKEmyMCG3tb8RB63W+EmrOBg==" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/classlist/1.2.20180112/classList.min.js" integrity="sha512-ISyiBXwG69Utm2wNP5ex+4P5+JmPaXmBMV82Uq/Yuck54FnOC4ZIJ9vDIT9TzHzZZC/xdYgivc2wmKuJkAZHmQ==" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/flatpickr/4.6.6/flatpickr.min.js" integrity="sha512-Nc36QpQAS2BOjt0g/CqfIi54O6+UWTI3fmqJsnXoU6rNYRq8vIQQkZmkrRnnk4xKgMC3ESWp69ilLpDm6Zu8wQ==" crossorigin="anonymous"></script>
<script type="text/javascript" src="/assets/js/core/shim.js?r=<% out.print(new File(application.getRealPath("assets/js/core/shim.js")).lastModified()); %>"></script>
<script type="text/javascript" src="/assets/js/core/app.js?r=<% out.print(new File(application.getRealPath("assets/js/core/app.js")).lastModified()); %>"></script>
<script type="text/javascript" src="/assets/js/core/utils/common_util.js?r=<% out.print(new File(application.getRealPath("assets/js/core/utils/common_util.js")).lastModified()); %></script>"></script>

