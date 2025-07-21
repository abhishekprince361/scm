console.log("contacts.js")

const baseURL = "http://localhost:8080";
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
        // setTimeout(()=>{
        //     contactModal.classList.add("scale-100");
        // }, 50);
        console.log('modal is show');
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
        const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
        console.log(data);
        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_image").src = data.picture;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
        document.querySelector("#contact_about").innerHTML = data.description;
        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favorite) {
            contactFavorite.innerHTML = "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>"
        } else {
            contactFavorite.innerHTML = "Not Favorite Contact";
        }
        const websiteLink = document.querySelector("#contact_website");
        if (data.websiteLink) {
            websiteLink.href = data.websiteLink;
            websiteLink.innerHTML = data.websiteLink;
        } else {
            websiteLink.innerHTML = "No Link Found"
        }

        const linkedInLink = document.querySelector("#contact_linkedIn");
        if (data.linkedInLink) {
            linkedInLink.href = data.linkedInLink;
            linkedInLink.innerHTML = data.linkedInLink;
        } else {
            linkedInLink.innerHTML = "No Link Found"
        }

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

// delete contact

async function deleteContact(id) {
    console.log(id)
    Swal.fire({
        title: "Do you want to delete the contact?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            const url = `${baseURL}/user/contacts/delete/${id}`;
            window.location.replace(url);
        }
    });
}


