
const MessageBoard = {
    searchCriteria: {},

    onReady: () => {
        let msgBox = document.querySelector("#msgboard-form [name='messageText']");
        msgBox.addEventListener("keydown", MessageBoard.msgBoxKeyPressHandler);
        msgBox.addEventListener("keydown", () => { MessageBoard.resizeTextArea(msgBox) });
        msgBox.addEventListener("keyup", () => { MessageBoard.resizeTextArea(msgBox) });
        MessageBoard.resizeTextArea(msgBox);

        flatpickr("#msgboard-search-form [name='fromDate']", {enableTime: true, enableSeconds: true, dateFormat: "Y-m-d H:i:S"});
        flatpickr("#msgboard-search-form [name='toDate']", {enableTime: true, enableSeconds: true, dateFormat: "Y-m-d H:i:S"});

        document.querySelector("#msgboard-search-form .btn-search").addEventListener('click', MessageBoard.search);

        MessageBoard.search();
    },

    msgBoxKeyPressHandler: (event) => {
        if (event.keyCode == 13 && !event.shiftKey) {
            MessageBoard.postMessage();
            event.currentTarget.value = "";
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

    messageEditClick: (e) => {
        msgListItem = $(e.currentTarget).closest("li.msgboard-msg")[0];
        msgListItem.classList.remove('read-mode');
        msgListItem.classList.add('edit-mode');
    },

    messageDeleteClick: (e) => {
        msgListItem = $(e.currentTarget).closest("li.msgboard-msg")[0];

        axios.delete('/api/auth/message', {
            params: { id: msgListItem.id.substring(4) }
        })
        .then((response) => {
            msgListItem.remove();
        })
        .catch((error) => {
            console.error(error.response.data);
            alert(error.response.data.message);
        });
    },

    messageRemoveFileClick: (e) => {
        msgListItem = $(e.currentTarget).closest("li.msgboard-msg")[0];
        msgListItem.querySelector("form input[name='filesToDelete']").value += e.currentTarget.id.substring(15) + " ";
        e.currentTarget.remove();
    },

    messageDownloadFileClick: (e) => {
        msgListItem = $(e.currentTarget).closest("li.msgboard-msg")[0];
        window.open("/api/auth/messsage/attachment?id="+encodeURIComponent(e.currentTarget.id.substring(11))+"&msgId="+encodeURIComponent(msgListItem.id.substring(4)));
    },

    messageSaveClick: (e) => {
        msgListItem = $(e.currentTarget).closest("li.msgboard-msg")[0];

        let filesToDelete = msgListItem.querySelector("form input[name='filesToDelete']").value.split(" ");
        let messageJSON = CommonUtil.formToJson(msgListItem.querySelector("form"), false);
        messageJSON["filesToDelete"] = filesToDelete;
        messageJSON["id"] = msgListItem.id.substring(4);

        // this should be ignored on the backend anyways
        messageJSON["author"] = msgListItem.querySelector(".msgboard-msg-username").getAttribute("data-val");
        messageJSON["createdDate"] = msgListItem.querySelector(".msgboard-msg-time").getAttribute("data-val");
        //

        messageJSON = JSON.stringify(messageJSON);
        let formData = new FormData();
        formData.append("json", messageJSON);

        let txtMessageAttachments = msgListItem.querySelector("form #msg-li-upload-"+msgListItem.id.substring(4));
        if (txtMessageAttachments.files) {
            for(var i = 0; i < txtMessageAttachments.files.length; i++) {
                if(txtMessageAttachments.files[i])
                    formData.append("files[]", txtMessageAttachments.files[i]);
            }
        }

        axios.put('/api/auth/message', 
            formData,
            { 
                headers: { 
                    'Content-Type': 'multipart/form-data' 
                }
            }
        )
        .then((response) => {
            window.location.reload();
        })
        .catch((error) => {
            console.error(error.response.data);
            alert(error.response.data.message);
        });
    },

    messageCancelClick: (e) => {
        msgListItem = $(e.currentTarget).closest("li.msgboard-msg")[0];
        msgListItem.classList.remove('edit-mode');
        msgListItem.classList.add('read-mode');
    },

    createMessageDOM: (message) => {
        //console.log(message);
        let placeholderMsg = document.querySelector("#ul-msgboard .msgboard-msg-placeholder");
        if (placeholderMsg != null)
            placeholderMsg.remove();

        let editable = (Server.session.user.username == message.author);
        
        let msgboardListItem = document.createElement('li');
        msgboardListItem.className = "msgboard-msg list-group-item border-0 mb-3";
        msgboardListItem.id = "msg-"+message.id;
        let innerHTML = "" +
        "<h6>" +
            "<strong class='msgboard-msg-username' data-val='" + message.author + "'>" + message.author + "</strong>" +
            "<small class='msgboard-msg-time text-muted ml-2' data-val='" + message.createdDate + "'>" + message.createdDate + "</small>";
        if (editable)
        {
            innerHTML += "" +
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
        innerHTML += "</h6>";
        if (editable)
        {
        innerHTML += "" +
        "<form class='edit-mode' onsubmit='return false;'>" +
            "<textarea name='messageText' class='form-control shadow-none mb-2' rows='1' placeholder='Edit your message here...'>" + message.messageText + "</textarea>" +
            "<div class='msg-attachments mb-2'>" +
                "<label for='msg-li-upload-" + message.id + "' class='btn btn-primary btn-sm shadow-none mb-0 mr-2'>" +
                    "<i class='fas fa-paperclip'></i>" +
                    "<input type='file' id='msg-li-upload-" + message.id + "' multiple class='display-none' />" +
                "</label>" + 
                "<input name='filesToDelete' type='hidden' />";
                
                if (message.attachments != undefined) {
                    for (let i = 0; i < message.attachments.length; ++i)
                        innerHTML += "<button id='del-attachment-"+ message.attachments[i].id +"' class='btn-file_to_delete btn btn-sm btn-outline-danger shadow-none mr-2'><i class='fas fa-times-circle mr-2'></i>" + message.attachments[i].filename + "</button>";
                }

            innerHTML += "" + 
            "</div>" +
            "<div>" +
                "<button class='btn-save-message btn btn-success shadow-none mr-2'>Save</button>" +
                "<button class='btn-cancel-message btn btn-danger shadow-none mr-2'>Cancel</button>" +
            "</div>" +
        "</form>";
        }
        innerHTML += "" +
        "<div class='read-mode'>" +
            "<p class='msgboard-msg-text'>" + message.messageText + "</p>" +
            "<div class='msg-attachments'>";
            
                if (message.attachments != undefined) {
                    for (let i = 0; i < message.attachments.length; ++i)
                        innerHTML += "<button id='attachment-"+ message.attachments[i].id +"' class='btn-file_download btn btn-sm btn-secondary shadow-none mr-2'><i class='fas fa-file mr-2'></i>" + message.attachments[i].filename + "</button>";
                }
            
            innerHTML += "" +
            "</div>" +
        "</div>";

        msgboardListItem.innerHTML = innerHTML;
        document.querySelector("#ul-msgboard").appendChild(msgboardListItem);

        if (editable) {
            document.querySelector("#msg-"+message.id+" .btn-edit-message").addEventListener('click', (e) => { MessageBoard.messageEditClick(e); });
            document.querySelector("#msg-"+message.id+" .btn-delete-message").addEventListener('click', (e) => { MessageBoard.messageDeleteClick(e); });
            document.querySelector("#msg-"+message.id+" .btn-save-message").addEventListener('click', (e) => { MessageBoard.messageSaveClick(e); });
            document.querySelector("#msg-"+message.id+" .btn-cancel-message").addEventListener('click', (e) => { MessageBoard.messageCancelClick(e); });

            let fileDownloadBtns = document.querySelectorAll("#msg-"+message.id+" .btn-file_download");
            for (let i = 0; i < fileDownloadBtns.length; ++i)
            fileDownloadBtns[i].addEventListener('click', (e) => { MessageBoard.messageDownloadFileClick(e); });

            let fileDeletionBtns = document.querySelectorAll("#msg-"+message.id+" .btn-file_to_delete");
            for (let i = 0; i < fileDeletionBtns.length; ++i)
                fileDeletionBtns[i].addEventListener('click', (e) => { MessageBoard.messageRemoveFileClick(e); });
        }
    },
    
    search: () => {
        MessageBoard.searchCriteria = CommonUtil.formToJson(document.querySelector("#msgboard-search-form"), false);

        if (MessageBoard.searchCriteria.fromDate != undefined && MessageBoard.searchCriteria.fromDate.trim() != "")
            MessageBoard.searchCriteria.fromDate = MessageBoard.searchCriteria.fromDate + ".000";
        if (MessageBoard.searchCriteria.toDate != undefined && MessageBoard.searchCriteria.toDate.trim() != "")
            MessageBoard.searchCriteria.toDate = MessageBoard.searchCriteria.toDate + ".000";

        let regexAuthors = /[^ ]+/g;
        MessageBoard.searchCriteria.authors = MessageBoard.searchCriteria.authors.match(regexAuthors);
        
        let hashtag_regex = /\B\#\w\w+\b/g;
        MessageBoard.searchCriteria.hashtags = MessageBoard.searchCriteria.hashtags.match(hashtag_regex);

        axios.get('/api/auth/message', {
            params: MessageBoard.searchCriteria,
            paramsSerializer: (params) => $.param(params),
        })
        .then(function (response) {
            //console.log(response.data.data);
            MessageBoard.clearMessageBoardDOM(response.data.data == undefined || response.data.data.length <= 0);

            if (response.data.data != undefined) {
            for (let i = 0; i < response.data.data.length; ++i)
                MessageBoard.createMessageDOM(response.data.data[i]);
            }
        })
        .catch(function (error) {
            console.error(error.response.data);
            alert(error.response.data.message);
        })
    },

    postMessage: () => {
        let messageJSON = CommonUtil.formToJson(document.querySelector("#msgboard-form"), false);

        let hashtag_regex = /\B\#\w\w+\b/g;
        let hashtags = messageJSON.messageText.match(hashtag_regex);
        messageJSON.hashtags = [];
        for (let i = 0; i <hashtags.length; ++i) {
            messageJSON.hashtags.push({tag: hashtags[i]});
        }

        let formData = new FormData();
        formData.append("json", JSON.stringify(messageJSON));

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
            MessageBoard.search();
        })
        .catch((error) => {
            console.error(error.response.data);
            alert(error.response.data.message);
        });
    }
};

document.addEventListener("DOMContentLoaded", function(event) { 
    MessageBoard.onReady();
});