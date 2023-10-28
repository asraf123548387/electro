 function checkSelectedAddress() {
    const addressCheckboxes = document.querySelectorAll('.address-checkbox');
    let selectedAddressId = null;

    addressCheckboxes.forEach((checkbox) => {
    if (checkbox.checked) {
    selectedAddressId = checkbox.getAttribute('data-id');
    }
    });

    if (selectedAddressId) {
    // An address is selected, and you have the selected address ID.
    // You can use this ID to identify the selected address.
    console.log('Selected Address ID:', selectedAddressId);
    // Now, you can use this ID for further processing, such as submitting it to the server.
    // You can also redirect the user to the order placement page.
    window.location.href = "/user/orderPlaced";
    } else {
    // No address is selected, display an error message.
    const addressError = document.getElementById('address-error');
    addressError.textContent = 'Please select a delivery address.';
    }
    }