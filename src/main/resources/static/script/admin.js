// admin.js

console.log("admin user");

// Ensure the DOM is fully loaded before running the script
document.addEventListener("DOMContentLoaded", function () {
    // Select the file input element
    document
        .querySelector("#image_file_input")
        .addEventListener("change", function (event) {
            let file = event.target.files[0];
            if (file) {
                let reader = new FileReader();
                reader.onload = function () {
                    // Update the src attribute of the image preview
                    document
                        .querySelector("#upload_image_preview")
                        .setAttribute("src", reader.result);
                };
                reader.readAsDataURL(file);
            }
        });
});
