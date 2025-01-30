const username = localStorage.getItem("username");
const roomName = localStorage.getItem("roomName");

console.log(username)
console.log(roomName)
// Redirect to the home page if username or roomName is missing
if (!username || !roomName) {
    alert("You must host or join a room before accessing the chat.");
    window.location.href = "/";
}

const chatBox = document.getElementById("chatBox");
const messageInput = document.getElementById("messageInput");
const sendButton = document.getElementById("sendButton");

// Function to append messages to the chat box
function appendMessage(sender, message) {
    const messageDiv = document.createElement("div");
    messageDiv.classList.add("message");
    messageDiv.innerHTML = `<span>${sender}:</span> ${message}`;
    chatBox.appendChild(messageDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
}

// Send message to the server
async function sendMessage() {
    const message = messageInput.value.trim();
    if (!message) return;

    const data = {
        username: username,
        roomName: roomName,
        message: message,
    };

    try {
        const response = await fetch("/message/sendMessage", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            appendMessage("You", message);
            messageInput.value = "";
        } else {
            alert("Failed to send message.");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

// Fetch messages from the server periodically
// Fetch messages from the server periodically
async function receiveMessages() {
    const data = {
        username: username, // Include username in the request body
        roomName: roomName, // Include roomName in the request body
    };

    try {
        const response = await fetch("/message/receiveMessage", {
            method: "POST", // Use POST to send a request body
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data), // Send the user object in the body
        });

        if (response.ok) {
            const messages = await response.json();
            console.log(messages)// Parse the response as JSON
            Object.entries(messages).forEach(([sender, message]) => {
                console.log(sender," : ",message)
                appendMessage(sender, message);
            });
        } else {
            console.error("Failed to fetch messages.");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}


// Set up event listeners
sendButton.addEventListener("click", sendMessage);
messageInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter") sendMessage();
});

// Poll for new messages every 2 seconds
setInterval(receiveMessages, 2000);