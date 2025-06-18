document.addEventListener("DOMContentLoaded", () => {
    const adicionarUsuarioLink = document.getElementById("adicionarUsuarioLink");
    const inicioLink = document.getElementById("inicioLink");
    const bemVindoMessage = document.getElementById("bemVindoMessage");
    const formularioUsuario = document.getElementById("formularioUsuario");

    // Exibir formulário de adicionar usuário
    adicionarUsuarioLink.addEventListener("click", (e) => {
        e.preventDefault();
        bemVindoMessage.style.display = "none"; // Esconde a mensagem de boas-vindas
        formularioUsuario.style.display = "block"; // Exibe o formulário
    });

    // Fechar o formulário e exibir a mensagem de boas-vindas
    inicioLink.addEventListener("click", (e) => {
        e.preventDefault();
        formularioUsuario.style.display = "none"; // Esconde o formulário
        bemVindoMessage.style.display = "block"; // Exibe a mensagem de boas-vindas
    });

    // Ações do botão "Sair"
    const sairLink = document.querySelector(".nav-link[href='/logout']");
    sairLink.addEventListener("click", (e) => {
        window.location.href = "/login"; // Redireciona para a página de login
    });
});
