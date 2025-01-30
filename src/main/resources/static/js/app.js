function submitUsername() {
    const username = document.getElementById('username').value;
    if (username.trim() === "") {
        alert("Please enter a username.");
        return;
    }

    localStorage.setItem('username', username);

    // Hide the username input and show the options
    document.querySelector('.username-input').style.display = 'none';
    document.querySelector('.options').style.display = 'block';
}

function hostRoom() {
    // Redirect to host room page (replace URL with your desired path)
    alert("Redirecting to host room...");
    window.location.href = '/host-room.html';
}

function joinRoom() {
    // Redirect to join room page (replace URL with your desired path)
    alert("Redirecting to join room...");
    window.location.href = '/join-room.html';
}