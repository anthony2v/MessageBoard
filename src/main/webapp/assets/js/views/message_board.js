
const MessageBoard = {
    searchCriteria: {},

    onReady: () => {
        let msgBox = document.querySelector("#msgboard-form [name='messageText']");
        msgBox.addEventListener("keydown", MessageBoard.msgBoxKeyPressHandler);
        msgBox.addEventListener("keydown", () => { MessageBoard.resizeTextArea(msgBox) });
        msgBox.addEventListener("keyup", () => { MessageBoard.resizeTextArea(msgBox) });
        MessageBoard.resizeTextArea(msgBox);

        flatpickr("#msgboard-search-form [name='fromDate']", {enableTime: true, dateFormat: "Y-m-d H:i:S"});
        flatpickr("#msgboard-search-form [name='toDate']", {enableTime: true, dateFormat: "Y-m-d H:i:S"});

        MessageBoard.search();
    },

    msgBoxKeyPressHandler: (event) => {
        if (event.keyCode == 13 && !event.shiftKey) {
            MessageBoard.postMessage();
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

    clearMessageBoardDOM: (showPlaceholder) => {
        document.querySelector("#ul-msgboard").innerHTML = showPlaceholder ? "<li class='msgboard-msg msgboard-msg-placeholder list-group-item text-center border-0 mb-3'>There are no messages to display...</li>" : "";
    },

    createMessageDOM: (message, username, date, msgText, attachments) => {
        username = String(username).trim();
        
        let placeholderMsg = document.querySelector("#ul-msgboard .msgboard-msg-placeholder");
        if (placeholderMsg != null)
            placeholderMsg.remove();

        let editable = (Server.session.user.username == message.username);
        
        let msgboardListItem = document.createElement('li');
        msgboardListItem.className = "msgboard-msg list-group-item border-0 mb-3";
        msgboardListItem.id = ""+message.id;
        msgboardListItem.innerHTML = "" +
        "<h6>" +
            "<strong class='msgboard-msg-username'>" + message.username + "</strong>" +
            "<small class='msgboard-msg-time text-muted ml-2'>" + message.createdDate + "</small>";
        if (editable)
        {
            msgboardListItem.innerHTML += "" +
            "<div class='msg-actions float-right read-mode'>" +
                "<button type='button' class='btn btn-sm btn-secondary rounded-right shadow-none' data-toggle='dropdown'>" +
                    "<i class='fas fa-ellipsis-v'></i>" +
                "</button>" +
                "<div class='dropdown-menu'>" +
                    "<button type='button' class='btn-edit-message btn rounded-0 dropdown-item'><i class='fas fa-pencil-alt mr-2 text-uppercase'></i>Edit</button>" +
                    "<button type='button' class='btn-delete-message btn btn-danger bg-danger rounded-0 dropdown-item'><i class='fas fa-trash-alt mr-2 text-uppercase'></i>Delete</button>" +
                "</div>" +
            "</div>";
        }
        msgboardListItem.innerHTML += "</h6>";
        if (editable)
        {
        msgboardListItem.innerHTML += "" +
        "<form class='edit-mode display-none' onsubmit='return false;'>" +
            "<textarea name='messageText' class='form-control shadow-none mb-2' rows='1' placeholder='Edit your message here...'>" + message.messageText + "</textarea>" +
            "<div class='msg-attachments mb-2'>" +
                "<label for='upload' class='btn btn-primary btn-sm shadow-none mb-0 mr-2'>" +
                    "<i class='fas fa-paperclip'></i>" +
                    "<input type='file' id='upload' multiple class='display-none' />" +
                "</label>";

                if (message.attachments) {
                    for (let i = 0; i < message.attachments.length; ++i)
                        msgboardListItem.innerHTML += "<button id='"+ message.attachments[i].id +"' class='btn btn-sm btn-danger shadow-none'>" + message.attachments[i].filename + "</button>";
                }
            
            msgboardListItem.innerHTML += "" + 
            "</div>" +
            "<div>" +
                "<button class='btn btn-success shadow-none'>Save</button>" +
                "<button class='btn btn-danger shadow-none'>Cancel</button>" +
            "</div>" +
        "</form>";
        }
        msgboardListItem.innerHTML += "" +
        "<div class='read-mode'>" +
            "<p class='msgboard-msg-text'>Test</p>" +
            "<div class='msg-attachments'>";
                if (message.attachments) {
                    for (let i = 0; i < message.attachments.length; ++i)
                        msgboardListItem.innerHTML += "<button id='"+ message.attachments[i].id +"' class='btn btn-sm btn-secondary shadow-none'>" + message.attachments[i].filename + "</button>";
                }
            
            msgboardListItem.innerHTML += "" +
            "</div>" +
        "</div>";
        
        document.querySelector("#ul-msgboard").appendChild(msgboardListItem);

        // bind functions for edit mode
    },

    search: () => {
        MessageBoard.searchCriteria = CommonUtil.formToJson(document.querySelector("#msgboard-search-form"));

        axios.get('/api/auth/message', {
            params: MessageBoard.searchCriteria
        })
        .then(function (response) {
            MessageBoard.clearMessageBoardDOM(response.data.data.length <= 0);
            //console.log(response.data);
            for (let i = 0; i < response.data.data.length; ++i)
                MessageBoard.createMessageDOM(response.data.data[i]);
        })
        .catch(function (error) {
            console.log(error);
            alert(error);
        })
    },

    postMessage: () => {
        let formData = new FormData();

        formData.append("json", CommonUtil.formToJson(document.querySelector("#msgboard-form")));

        let txtMessageAttachments = document.querySelector("#msgboard-form #upload");
        if (txtMessageAttachments.files) {
            for(var i = 0; i < txtMessageAttachments.files.length; i++) {
                if(txtMessageAttachments.files[i])
                    formData.append("files[]", txtMessageAttachments.files[i]);
            }
        }

        axios.post('/api/auth/message', 
            formData,
            { 
                headers: { 
                    'Content-Type': 'multipart/form-data' 
                }
            }
        )
        .then((response) => {
            //MessageBoard.search();
        })
        .catch((error) => {
            console.log(error);
            alert(error);
        });
    },

    editMessage: (msgId) => {

    },

    deleteMessage: (msgId) => {

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
//         // chatListItem.innerHTML += "<button class='btn btn-sm btn-secondary'>attachment 1</button>";

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