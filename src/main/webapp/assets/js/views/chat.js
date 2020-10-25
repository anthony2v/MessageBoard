
const Chat = {
    onReady: () => {
        let chatMsgBox = document.querySelector("#chat-form [name='message']");
        chatMsgBox.addEventListener("keydown", Chat.chatMsgBoxKeyPressHandler);
        chatMsgBox.addEventListener("keydown", () => { Chat.resizeTextArea(chatMsgBox) });
        chatMsgBox.addEventListener("keyup", () => { Chat.resizeTextArea(chatMsgBox) });
        Chat.resizeTextArea(chatMsgBox);

        document.querySelector("#btn-download-text").addEventListener("click", () => { Chat.getMessages(null, null, "text") });
        document.querySelector("#btn-download-xml").addEventListener("click", () => { Chat.getMessages(null, null, "xml") });
        document.querySelector("#btn-clear-chat").addEventListener("click", () => { Chat.deleteChat() });

        Chat.getMessages(null, null, "json");
    },

    chatMsgBoxKeyPressHandler: (event) => {
        if (event.keyCode == 13 && !event.shiftKey) {
            Chat.postMessage(document.querySelector("#chat-form [name='name']").value, event.target.value);
            event.target.value = "";
            event.preventDefault();
        }
    },

    resizeTextArea: (el) =>
    {
        let borderTop = parseInt(window.getComputedStyle(el).getPropertyValue('border-top'), 10);
        let borderBottom = parseInt(window.getComputedStyle(el).getPropertyValue('border-bottom'), 10);
        let prevScrollTop = document.documentElement.scrollTop;
        el.style.height = 'auto';
        el.style.height = (el.scrollHeight + borderTop + borderBottom) + 'px';
        document.documentElement.scrollTop = prevScrollTop;
    },

    postMessage: (username, msgText) => {
        username = String(username).trim().length == 0 ? "Anon" : username;

        axios.post('/api/chat', {
            username: username,
            message: msgText
        })
        .then((response) => {
            window.location.reload();
        })
        .catch((error) => {
            console.log(error);
            alert(error);
        });
    },

    displayMessage: (username, date, msgText) => {
        username = String(username).trim().length == 0 ? "Anon" : username;
        
        let placeholderMsg = document.querySelector("#ul-chat .chat-msg-placeholder");
        if (placeholderMsg != null)
            placeholderMsg.remove();

        let chatListItem = document.createElement('li');
        chatListItem.className = "chat-msg chat-left list-group-item border-0";
        chatListItem.innerHTML = "" +
            "<h6>" + 
                "<strong class='chat-msg-username'>" + username + "</strong>" +
                "<small class='chat-msg-time text-muted ml-2'>" + date + "</small>" +
            "</h6>" +
            "<p class='chat-msg-text'>" + msgText + "</p>";
        
        document.querySelector("#ul-chat").appendChild(chatListItem);
    },

    getMessages: (from, to, format) => {
        if (format == "text" || format == "xml")
        {
            let url = "/api/chat?format=" + format;
            url += from != null ? "&from=" + from : "";
            url += from != null ? "&to=" + to : "";
            window.open("/api/chat?format="+format, "_blank");
            return;
        }

        axios.get('/api/chat', {
            params: {
                from: from,
                to: to,
                format: format
            }
        })
        .then((response) => {
            if (format == "json")
            {
                Chat.clearChatDOM(response.data.data.length <= 0);
                //console.log(response);
                for (let i = 0; i < response.data.data.length; ++i)
                    Chat.displayMessage(response.data.data[i].username, response.data.data[i].createdDate, response.data.data[i].message);
            }

            let timer = 100;
            let scrollToBottom = () => { 
                document.documentElement.scrollTop = document.querySelector("#ul-chat").clientHeight; 
                setTimeout(() => { if (timer-- >= 0) scrollToBottom(); }, 1);
            };
            scrollToBottom();
        })
        .catch((error) => {
            console.log(error);
            alert(error);
        });
    },

    clearChatDOM: (showPlaceholder) => {
        document.querySelector("#ul-chat").innerHTML = showPlaceholder ? "<li class='chat-msg chat-msg-placeholder list-group-item text-center'>There are no messages to display...</li>" : "";
    },

    deleteChat: () => {
        axios.delete('/api/chat')
        .then((response) => {
            Chat.clearChatDOM(true);
        })
        .catch((error) => {
            console.log(error);
            alert(error);
        });
    }
};

document.addEventListener("DOMContentLoaded", function(event) { 
    Chat.onReady();
});