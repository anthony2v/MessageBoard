
const MessageBoard = {
    searchCriteria: {},

    onReady: () => {
        let msgBox = document.querySelector("#msgboard-form [name='message']");
        msgBox.addEventListener("keydown", MessageBoard.msgBoxKeyPressHandler);
        msgBox.addEventListener("keydown", () => { MessageBoard.resizeTextArea(msgBox) });
        msgBox.addEventListener("keyup", () => { MessageBoard.resizeTextArea(msgBox) });
        MessageBoard.resizeTextArea(msgBox);

        flatpickr("#msgboard-search-form [name='fromDate']", {enableTime: true, dateFormat: "Y-m-d H:i:S"});
        flatpickr("#msgboard-search-form [name='toDate']", {enableTime: true, dateFormat: "Y-m-d H:i:S"});

        //MessageBoard.getMessages(null, null, null, null);
    },

    msgBoxKeyPressHandler: (event) => {
        if (event.keyCode == 13 && !event.shiftKey) {
            MessageBoard.postMessage(event.target.value);
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

    postMessage: (msgText) => {
        username = String(username).trim().length == 0 ? "Anon" : username;

        axios.post('/api/auth/message', {
            message: msgText
        })
        .then((response) => {
            MessageBoard.getMessages(searchCriteria.authors, searchCriteria.hashtags, searchCriteria.fromDate, searchCriteria.toDate);
        })
        .catch((error) => {
            console.log(error);
            alert(error);
        });
    }
};

// const MessageBoard = {
//     onReady: () => {
//         let chatMsgBox = document.querySelector("#chat-form [name='message']");
//         chatMsgBox.addEventListener("keydown", MessageBoard.chatMsgBoxKeyPressHandler);
//         chatMsgBox.addEventListener("keydown", () => { MessageBoard.resizeTextArea(chatMsgBox) });
//         chatMsgBox.addEventListener("keyup", () => { MessageBoard.resizeTextArea(chatMsgBox) });
//         MessageBoard.resizeTextArea(chatMsgBox);

//         document.querySelector("#btn-download-text").addEventListener("click", () => { MessageBoard.getMessages(null, null, "text") });
//         document.querySelector("#btn-download-xml").addEventListener("click", () => { MessageBoard.getMessages(null, null, "xml") });
//         document.querySelector("#btn-clear-chat").addEventListener("click", () => { MessageBoard.deleteMessageBoard() });

//         MessageBoard.getMessages(null, null, "json");
//     },

//     chatMsgBoxKeyPressHandler: (event) => {
//         if (event.keyCode == 13 && !event.shiftKey) {
//             MessageBoard.postMessage(document.querySelector("#chat-form [name='name']").value, event.target.value);
//             event.target.value = "";
//             event.preventDefault();
//         }
//     },

//     resizeTextArea: (el) =>
//     {
//         let borderTop = parseInt(window.getComputedStyle(el).getPropertyValue('border-top'), 10);
//         let borderBottom = parseInt(window.getComputedStyle(el).getPropertyValue('border-bottom'), 10);
//         let prevScrollTop = document.documentElement.scrollTop;
//         el.style.height = 'auto';
//         el.style.height = (el.scrollHeight + borderTop + borderBottom) + 'px';
//         document.documentElement.scrollTop = prevScrollTop;
//     },

//     postMessage: (username, msgText) => {
//         username = String(username).trim().length == 0 ? "Anon" : username;

//         axios.post('/api/chat', {
//             username: username,
//             message: msgText
//         })
//         .then((response) => {
//             window.location.reload();
//         })
//         .catch((error) => {
//             console.log(error);
//             alert(error);
//         });
//     },

//     displayMessage: (username, date, msgText) => {
//         username = String(username).trim().length == 0 ? "Anon" : username;
        
//         let placeholderMsg = document.querySelector("#ul-chat .chat-msg-placeholder");
//         if (placeholderMsg != null)
//             placeholderMsg.remove();

//         let chatListItem = document.createElement('li');
//         chatListItem.className = "chat-msg chat-left list-group-item border-0 mb-3";
//         chatListItem.innerHTML = "" +
//             "<h6>" + 
//                 "<strong class='chat-msg-username'>" + username + "</strong>" +
//                 "<small class='chat-msg-time text-muted ml-2'>" + date + "</small>" +
//             "</h6>" +
//             "<p class='chat-msg-text'>" + msgText + "</p>" +
//             "<div>";

//         // loop over attachments
//         // chatListItem.innerHTML += "<button class="btn btn-sm btn-secondary">attachment 1</button>";

//         chatListItem.innerHTML += "</div>";
        
//         document.querySelector("#ul-chat").appendChild(chatListItem);
//     },

//     getMessages: (from, to, format) => {
//         if (format == "text" || format == "xml")
//         {
//             let url = "/api/chat?format=" + format;
//             url += from != null ? "&from=" + from : "";
//             url += from != null ? "&to=" + to : "";
//             window.open("/api/chat?format="+format, "_blank");
//             return;
//         }

//         axios.get('/api/chat', {
//             params: {
//                 from: from,
//                 to: to,
//                 format: format
//             }
//         })
//         .then((response) => {
//             if (format == "json")
//             {
//                 MessageBoard.clearMessageBoardDOM(response.data.data.length <= 0);
//                 //console.log(response);
//                 for (let i = 0; i < response.data.data.length; ++i)
//                     MessageBoard.displayMessage(response.data.data[i].username, response.data.data[i].createdDate, response.data.data[i].message);
//             }

//             let timer = 100;
//             let scrollToBottom = () => { 
//                 document.documentElement.scrollTop = document.querySelector("#ul-chat").clientHeight; 
//                 setTimeout(() => { if (timer-- >= 0) scrollToBottom(); }, 1);
//             };
//             scrollToBottom();
//         })
//         .catch((error) => {
//             console.log(error);
//             alert(error);
//         });
//     },

//     clearMessageBoardDOM: (showPlaceholder) => {
//         document.querySelector("#ul-chat").innerHTML = showPlaceholder ? "<li class='chat-msg chat-msg-placeholder list-group-item text-center'>There are no messages to display...</li>" : "";
//     },

//     deleteMessageBoard: () => {
//         axios.delete('/api/chat')
//         .then((response) => {
//             MessageBoard.clearMessageBoardDOM(true);
//         })
//         .catch((error) => {
//             console.log(error);
//             alert(error);
//         });
//     }
// };

document.addEventListener("DOMContentLoaded", function(event) { 
    MessageBoard.onReady();
});