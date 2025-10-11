function uploadAndPreview(input) {
    const file = input.files[0];
    const imgEl = document.getElementById('profile-pic');

    if (!file) return;
    const reader = new FileReader();
    reader.onload = e => {
        imgEl.src = e.target.result;
        imgEl.style.transform = 'scale(1.1)';
        setTimeout(() => imgEl.style.transform = 'scale(1)', 200);
    };
    reader.readAsDataURL(file);
    const formData = new FormData();
    formData.append("image", file);

    fetch("/profile-picture", {
        method: "POST",
        body: formData
    }).then(res => {
        if (res.ok) {
            console.log("Profile picture updated successfully! Reloading page...");
            window.location.reload();
        } else {
            console.error("Failed to upload profile picture. Status:", res.status);
            alert("Грешка при качването на снимката.");
        }
    }).catch(err => {
        console.error("Upload error:", err);
        alert("Грешка при изпращане на заявката.");
    });
}
document.addEventListener('DOMContentLoaded', () => {
    const profilePic = document.getElementById('profile-pic');
    const fileInput = document.getElementById('file-input');

    if (profilePic && fileInput) {
        profilePic.style.cursor = 'pointer';
        profilePic.addEventListener('click', () => {
            fileInput.click();
        });
    } else {
        console.error("Profile picture or file input element not found. Check IDs in HTML.");
    }
});