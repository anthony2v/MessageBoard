
const Message = {
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
    }
};

document.addEventListener("DOMContentLoaded", function(event) {
    let currentURL = new URI();
    let qs = currentURL.query(true);

    axios.get('/api/auth/message', {
        params: qs,
        paramsSerializer: (params) => $.param(params),
    })
    .then(function (response) {
        let placeholderMsg = document.querySelector("#ul-msgboard .msgboard-msg-placeholder");
        if (placeholderMsg != null)
            placeholderMsg.remove();

        let ul_msgboard = document.querySelector("#ul-msgboard");
        ul_msgboard.innerHTML = response.data;

        for (let i = 0; i < ul_msgboard.children.length; ++i)
        {
            let li_msg = ul_msgboard.children[i];
            let editable = li_msg.querySelector(".edit-mode") != undefined;
            
            if (editable) {
                li_msg.querySelector(".btn-edit-message").addEventListener('click', (e) => { Message.messageEditClick(e); });
                li_msg.querySelector(".btn-delete-message").addEventListener('click', (e) => { Message.messageDeleteClick(e); });
                li_msg.querySelector(".btn-save-message").addEventListener('click', (e) => { Message.messageSaveClick(e); });
                li_msg.querySelector(".btn-cancel-message").addEventListener('click', (e) => { Message.messageCancelClick(e); });
    
                let fileDownloadBtns = li_msg.querySelectorAll(".btn-file_download");
                for (let i = 0; i < fileDownloadBtns.length; ++i)
                        fileDownloadBtns[i].addEventListener('click', (e) => { Message.messageDownloadFileClick(e); });
    
                let fileDeletionBtns = li_msg.querySelectorAll(".btn-file_to_delete");
                for (let i = 0; i < fileDeletionBtns.length; ++i)
                    fileDeletionBtns[i].addEventListener('click', (e) => { Message.messageRemoveFileClick(e); });
            }
        }
    })
    .catch(function (error) {
        console.error(error.response);
    })
});