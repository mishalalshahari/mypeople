console.log("Script loaded!");

let currentTheme = getTheme();

//initial-->

document.addEventListener('DOMContentLoaded', () => {
    changeTheme();
});

function changeTheme() {
    //set to webpage
    changePageTheme(currentTheme);

    //set the listener to change theme
    const themeButton = document.querySelector('#theme_change');

    //set the initial icon based on the current theme
    updateButtonIcon(themeButton);

    //set the listener to change theme when the button is clicked
    themeButton.addEventListener("click", (event) => {

        console.log("theme change button clicked");

        //set oldTheme as currentTheme
        const oldTheme = currentTheme;

        if (currentTheme === "dark") {
            //theme to light
            currentTheme="light";
        } else {
            //theme to dark
            currentTheme="dark";
        }

        //update the button icon
        updateButtonIcon(themeButton);

        //call with currentTheme and oldTheme
        changePageTheme(currentTheme,oldTheme);
    });
}

//update the button icon based on the current theme
function updateButtonIcon(button) {
    button.querySelector("span").innerHTML = currentTheme === "light"
        ? '<i class="fa-solid fa-moon"></i>'
        : '<i class="fa-solid fa-sun"></i>';
}

//set theme to localStorage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

//get theme from localStorage
function getTheme() {
    let theme = localStorage.getItem("theme");
    if(theme) return theme;
    return "light";
}

//change current page theme
function changePageTheme(theme, oldTheme) {
    //update in local storage
    setTheme(currentTheme);
    //remove the current theme
    document.querySelector('html').classList.remove(oldTheme);
    //set the current theme
    document.querySelector('html').classList.add(theme);
}