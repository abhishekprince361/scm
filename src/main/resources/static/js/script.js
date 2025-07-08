console.log("Script loaded");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
})



function changeTheme() {

    changePageTheme(currentTheme, "");

    //set the listener to change the theme
    const changeThemeButton = document.querySelector('#theme_change_button');

    changeThemeButton.addEventListener("click", (event) => {

        let oldTheme = currentTheme;
        console.log("change theme button clicked");
        if (currentTheme === "dark") {
            currentTheme = 'light'
        } else {
            currentTheme = 'dark'
        }
        changePageTheme(currentTheme, oldTheme);
    });

}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

function changePageTheme(theme, oldTheme) {
    setTheme(currentTheme);
    if (oldTheme) {
        document.querySelector('html').classList.remove(oldTheme);
    }

    document.querySelector('html').classList.add(theme);


    document.querySelector('#theme_change_button').querySelector("span").textContent = theme == "light" ? "dark" : "light";
}