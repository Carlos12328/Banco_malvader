document.addEventListener("DOMContentLoaded", () => {
    // Seletores dos elementos
    const adicionarUsuarioLink = document.getElementById("adicionarUsuarioLink");
    const encerramentoContaLink = document.querySelectorAll(".nav-link")[3]; // Posição 3 = 4º botão da sidebar
    const inicioLink = document.getElementById("inicioLink");

    const bemVindoMessage = document.getElementById("bemVindoMessage");
    const formularioUsuario = document.getElementById("formularioUsuario");
    const formularioEncerramento = document.getElementById("formularioEncerramento");

    // Função para esconder todos os formulários
    function esconderTodosOsFormularios() {
        formularioUsuario.style.display = "none";
        formularioEncerramento.style.display = "none";
    }

    // Botão "Adicionar Usuário"
    adicionarUsuarioLink.addEventListener("click", (e) => {
        e.preventDefault();
        bemVindoMessage.style.display = "none";
        esconderTodosOsFormularios();
        formularioUsuario.style.display = "block";
    });

    // Botão "Encerramento de Conta"
    encerramentoContaLink.addEventListener("click", (e) => {
        e.preventDefault();
        bemVindoMessage.style.display = "none";
        esconderTodosOsFormularios();
        formularioEncerramento.style.display = "block";
    });

    // Botão "Início"
    inicioLink.addEventListener("click", (e) => {
        e.preventDefault();
        esconderTodosOsFormularios();
        bemVindoMessage.style.display = "block";
    });

    // Botão "Sair"
    const sairLink = document.querySelector(".nav-link[href='/logout']");
    sairLink.addEventListener("click", (e) => {
        window.location.href = "/login";
    });
});
