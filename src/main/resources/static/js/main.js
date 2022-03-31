'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var helloMessage = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    helloMessage = document.querySelector('#helloMessage').value.trim();

    if (username) {
        const options = {
            method: 'POST',
            body: JSON.stringify({username: username}),
            headers: {
                'Content-Type': 'application/json'
            }
        }
        const response = fetch('http://80.87.202.218:8080/validate/username', options)
            .then(res => res.json())
            .then(res => {
                if (res.isValid) {
                    usernamePage.classList.add('hidden');
                    chatPage.classList.remove('hidden');

                    var socket = new SockJS('/ws');
                    stompClient = Stomp.over(socket);

                    stompClient.connect({}, onConnected, onError);
                } else {
                    alert(res.message)
                }
            });
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({username: username, helloMessage: helloMessage.length === 0 ? null : helloMessage})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    switch (message.type) {
        case 'JOIN':
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined!';
            break;
        case 'JOIN_WITH_MESSAGE':
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined with message: ' + message.content;
            break;
        case 'LEAVE':
            messageElement.classList.add('event-message');
            message.content = message.sender + ' left!';
            break;
        case 'CHAT':
            messageElement.classList.add('chat-message');

            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.sender);

            messageElement.appendChild(avatarElement);

            var usernameElement = document.createElement('span');
            var usernameText = document.createTextNode(message.sender);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
            break;

    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)