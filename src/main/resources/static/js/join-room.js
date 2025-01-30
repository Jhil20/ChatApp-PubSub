// Populate username from localStorage on page load
document.addEventListener('DOMContentLoaded', () => {
    const username = localStorage.getItem('username');
    console.log(username)
    if (username) {
        document.getElementById('username').value = username;
    } else {
        // Redirect back to index if no username
        window.location.href = '/index.html';
    }
});

document.getElementById('joinRoomForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const userData = {
        username: document.getElementById('username').value,
        roomName: document.getElementById('roomName').value,
        memberType: document.getElementById('memberType').value
    };

    try {
        const response = await fetch('/invitee/joinRoom', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            const result = await response.text();
            // alert(result);

            // Save roomName and redirect to chat page
            localStorage.setItem('roomName', userData.roomName);
            localStorage.setItem('username', userData.username+'-1234');
            window.location.href = 'chat-page.html';
        } else {
            alert('Failed to create room');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to create room');
    }
});