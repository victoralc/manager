function completeTask(checkbox) {
    var taskId = checkbox.getAttribute("data-task-id");
    var taskNameElement = document.getElementById("taskName_" + taskId);

    if(checkbox.checked) {
        taskNameElement.style.textDecoration = "line-through";
    } else {
        taskNameElement.style.textDecoration = "none";
    }

    // Send PUT request to update task status
    fetch('/tasks/' + taskId + '/status', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            completed: checkbox.checked,
        }),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log('Task status updated:', data);
    })
    .catch(error => {
        console.error('There was a problem updating the task status:', error);
    });
}