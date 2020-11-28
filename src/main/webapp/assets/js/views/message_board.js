
const MessageBoard = {
    searchCriteria: {},

    onReady: () => {
        document.querySelector("#msgboard-form #btn-download-xml").addEventListener("click", () => { MessageBoard.search("xml") });

        let msgBox = document.querySelector("#msgboard-form [name='messageText']");
        msgBox.addEventListener("keydown", MessageBoard.msgBoxKeyPressHandler);
        msgBox.addEventListener("keydown", () => { MessageBoard.resizeTextArea(msgBox) });
        msgBox.addEventListener("keyup", () => { MessageBoard.resizeTextArea(msgBox) });
        MessageBoard.resizeTextArea(msgBox);

        flatpickr("#msgboard-search-form .flatpickr", {wrap:true, enableTime: true, enableSeconds: true, dateFormat: "Y-m-d H:i:S"});

        document.querySelector("#msgboard-search-form .btn-search").addEventListener('click', () => { MessageBoard.search("html"); });

        MessageBoard.search("html");
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
        window.open("/api/auth/message/attachment?id="+encodeURIComponent(e.currentTarget.id.substring(11))+"&msgId="+encodeURIComponent(msgListItem.id.substring(4)));
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
            if (response.data.message) alert(response.data.message);
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

    loadMessageDOM: (messagesDOM) => {
        //console.log(messagesDOM);
        let placeholderMsg = document.querySelector("#ul-msgboard .msgboard-msg-placeholder");
        if (placeholderMsg != null)
            placeholderMsg.remove();

        let ul_msgboard = document.querySelector("#ul-msgboard");
        ul_msgboard.innerHTML = messagesDOM;

        for (let i = 0; i < ul_msgboard.children.length; ++i)
        {
            let li_msg = ul_msgboard.children[i];
            let editable = li_msg.querySelector(".edit-mode") != undefined;
            
            if (editable) {
                li_msg.querySelector(".btn-edit-message").addEventListener('click', (e) => { MessageBoard.messageEditClick(e); });
                li_msg.querySelector(".btn-delete-message").addEventListener('click', (e) => { MessageBoard.messageDeleteClick(e); });
                li_msg.querySelector(".btn-save-message").addEventListener('click', (e) => { MessageBoard.messageSaveClick(e); });
                li_msg.querySelector(".btn-cancel-message").addEventListener('click', (e) => { MessageBoard.messageCancelClick(e); });
    
                let fileDownloadBtns = li_msg.querySelectorAll(".btn-file_download");
                for (let i = 0; i < fileDownloadBtns.length; ++i)
                        fileDownloadBtns[i].addEventListener('click', (e) => { MessageBoard.messageDownloadFileClick(e); });
    
                let fileDeletionBtns = li_msg.querySelectorAll(".btn-file_to_delete");
                for (let i = 0; i < fileDeletionBtns.length; ++i)
                    fileDeletionBtns[i].addEventListener('click', (e) => { MessageBoard.messageRemoveFileClick(e); });
            }
        }
    },
    
    search: (format) => {
        MessageBoard.searchCriteria = CommonUtil.formToJson(document.querySelector("#msgboard-search-form"), false);

        if (MessageBoard.searchCriteria.fromDate != undefined && MessageBoard.searchCriteria.fromDate.trim() != "")
            MessageBoard.searchCriteria.fromDate = MessageBoard.searchCriteria.fromDate + ".000";
        if (MessageBoard.searchCriteria.toDate != undefined && MessageBoard.searchCriteria.toDate.trim() != "")
            MessageBoard.searchCriteria.toDate = MessageBoard.searchCriteria.toDate + ".000";

        let regexAuthors = /[^ ]+/g;
        MessageBoard.searchCriteria.authors = MessageBoard.searchCriteria.authors.match(regexAuthors);
        
        let hashtag_regex = /\B\#\w\w+\b/g;
        MessageBoard.searchCriteria.hashtags = MessageBoard.searchCriteria.hashtags.match(hashtag_regex);

        if (format == "xml")
        {
            window.open("/api/auth/message?format="+format, "_blank");
        }
        else
        {
            axios.get('/api/auth/message', {
                params: Object.assign({}, MessageBoard.searchCriteria, {"format": format}),
                paramsSerializer: (params) => $.param(params),
            })
            .then(function (response) {
                MessageBoard.clearMessageBoardDOM(response.data == undefined);
                MessageBoard.loadMessageDOM(response.data);
            })
            .catch(function (error) {
                console.error(error.response);
            })
        }
    },

    postMessage: () => {
        let messageJSON = CommonUtil.formToJson(document.querySelector("#msgboard-form"), false);
        let txtMessageAttachments = document.querySelector("#msgboard-form #upload");

        // check for empty message
        if (messageJSON.messageText != undefined && messageJSON.messageText.trim() == "" && txtMessageAttachments.files.length == 0)
            return;

        let hashtag_regex = /\B\#\w\w+\b/g;
        let hashtags = messageJSON.messageText.match(hashtag_regex);
        messageJSON.hashtags = [];
        for (let i = 0; i < messageJSON.hashtags.length; ++i) {
            messageJSON.hashtags.push({tag: hashtags[i]});
        }

        let formData = new FormData();
        formData.append("json", JSON.stringify(messageJSON));

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
            console.log(response);
            if (response.data.message) alert(response.data.message);
            window.location.reload();
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