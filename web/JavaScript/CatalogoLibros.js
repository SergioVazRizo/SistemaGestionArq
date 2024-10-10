// URL BASE
function setBaseURL() {
    const URL_BASE = 'http://localhost:8080/SistemaGestionArq/';
    return URL_BASE;
}

// Llama a la función y almacena el resultado en una constante global
const BASE_URL = setBaseURL();

document.addEventListener('DOMContentLoaded', function () {
    const catalogos = document.getElementById('catalogos');

    catalogos.addEventListener('click', () => {
        catalogos.parentElement.classList.toggle('active');
    });
});

function verificarToken() {
    const token = localStorage.getItem('token');

    if (!token) {
        window.location.href = BASE_URL + 'index.html';
    }
}

function cerrarSesion() {
    const usuario = localStorage.getItem('usuario');
    const token = localStorage.getItem('token');

    if (!usuario || !token) {
        console.error('No se encontraron usuario o token en el localStorage');
        return;
    }

    fetch(BASE_URL + 'api/login/cerrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'usuario=' + encodeURIComponent(usuario) + '&token=' + encodeURIComponent(token)
    })
            .then(response => {
                if (response.ok) {
                    localStorage.removeItem('usuario');
                    localStorage.removeItem('token');
                    Swal.fire({
                        title: 'Sesión cerrada',
                        text: 'exitosamente',
                        icon: 'success'
                    }).then(() => {
                        window.location.href = BASE_URL + 'SistemaGestion/index.html';
                    });
                } else {
                    throw new Error('Error al cerrar sesión');
                }
            })
            .catch(error => {
                console.error(error);
                Swal.fire({
                    title: 'Error',
                    text: 'Ha ocurrido un error al cerrar sesión',
                    icon: 'error'
                });
            });
}

function previewPDF(event) {
    const file = event.target.files[0];
    if (file && file.type === "application/pdf") {
        const fileURL = URL.createObjectURL(file);
        const pdfPreviewContainer = document.getElementById("pdfPreviewContainer");
        const pdfPreview = document.getElementById("pdfPreview");

        pdfPreview.src = fileURL;
        pdfPreviewContainer.style.display = "block";
    } else {
        alert("Por favor, selecciona un archivo PDF válido.");
    }
}

function removePDF() {
    const pdfPreviewContainer = document.getElementById("pdfPreviewContainer");
    const pdfPreview = document.getElementById("pdfPreview");
    const fileInput = document.getElementById("pfd_libro");

    pdfPreview.src = "";
    pdfPreviewContainer.style.display = "none";
    fileInput.value = ""; // Limpia el valor del input de archivo
}



window.onload = function () {
    verificarToken();
    //cargarLibros();
}



