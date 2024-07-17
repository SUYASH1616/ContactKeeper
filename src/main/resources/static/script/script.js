console.log("Script Loaded");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded",()=>{
    changeTheme(currentTheme);
})


function changeTheme(currentTheme) {
    changePageTheme(currentTheme);

    const changeButtonTheme = document.querySelector('#theme_change_button');

    changeButtonTheme.querySelector("span").textContent = currentTheme === "light" ? "dark" : "light";

    changeButtonTheme.addEventListener("click", toggleTheme);
}

function toggleTheme() {
    const oldTheme = currentTheme;
    currentTheme = currentTheme === "dark" ? "light" : "dark";

    changePageTheme(currentTheme, oldTheme);
}

function changePageTheme(newTheme, oldTheme = null) {
    if (oldTheme) {
        document.querySelector("html").classList.remove(oldTheme);
    }
    document.querySelector("html").classList.add(newTheme);
    setTheme(newTheme);
    
    // Update the button text after theme change
    document.querySelector("#theme_change_button").querySelector("span").textContent = newTheme === "light" ? "dark" : "light";
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}
