function deleteUser(element) {
    const id= Number(element.getAttribute("name"));
    console.log("ID:", id);
    console.log('Delete user with id: ' + id);
    deleteUserHttpRequest(id).then(result => {
        console.log(result);
    });
}

async function deleteUserHttpRequest(id) {
    const response = await fetch(`/api/v1/users/${id}`, {
        method: "DELETE",
    });
    return response.status;
}

function deleteDream(element) {
    const id = element.getAttribute("name");
    console.log("ID:", id);
    console.log("Delete dream with id: " + id);

    deleteDreamHttpRequest(id).then(result => {
        console.log("Response status:", result);
        if (result === 200 || result === 204) {
            element.closest(".dream-card").remove();
        } else {
            alert("Failed to delete dream (status " + result + ")");
        }
    });
}

async function deleteDreamHttpRequest(id) {
    const response = await fetch(`/api/v1/dreams/${id}`, {
        method: "DELETE",
    });
    return response.status;
}
