console.log("contacts.js")

const viewContactModal = document.getElementById('view_contact_modal');

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        setTimeout(()=>{
            contactModal.classList.add("scale-100");
        }, 50);
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instaceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instaceOptions);

function openContactModal() {
    contactModal.show();
}

function hideContactModal() {
    contactModal.hide();
}

async function loadContactData(id) {
    console.log(id);

    // getting data using api
    try {
        const data = await (await fetch(`http://localhost:8080/api/contacts/${id}`)).json();
        console.log(data);
        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_image").src = data.picture;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
        document.querySelector("#contact_about").innerHTML = data.description;
        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favorite) {
            contactFavorite.innerHTML = "<i class='fas fa-star text-yello-400'></i><i class='fas fa-star text-yello-400'></i><i class='fas fa-star text-yello-400'></i><i class='fas fa-star text-yello-400'></i><i class='fas fa-star text-yello-400'></i>"
        } else{
            contactFavorite.innerHTML = "Not Favorite Contact";
        }
        document.querySelector("#contact_website").href = data.websiteLink;
        document.querySelector("#contact_website").innerHTML = data.websiteLink;
        document.querySelector("#contact.linkedIn").href = data.linkedInLink;
        document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
        openContactModal();
    } catch (error) {
        console.log("Error: ", error);
    }


    // fetch(`http://localhost:8080/api/contacts/${id}`).then(async (response) => {
    //     const data = await response.json();
    //     console.log(data);
    //     return data;
    // }).catch((error) => {
    //     console.log(error);
    // })

}

