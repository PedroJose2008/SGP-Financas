const API_BUSCAR_TODOS = 'http://localhost:8013/tipodecontas/listartodos';
const API_ATUALIZAR = 'http://localhost:8013/tipodecontas/atualizar';
const API_DELETAR = 'http://localhost:8013/tipodecontas/deletar';
const API_SALVAR = "http://localhost:8013/tipodecontas/salvar";

async function listarTiposDeContas() {

    const response = await fetch(API_BUSCAR_TODOS);
    const tipos = await response.json();

    const tbody = document.getElementById("tabelaTiposDeContas");
    tbody.innerHTML = "";

    tipos.forEach(tipo => {

        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${tipo.id}</td>
            <td>${tipo.nome}</td>
            <td>${tipo.categoria}</td>
            <td>${tipo.descricao}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editar(${tipo.id})">
                    Editar
                </button>

                <button class="btn btn-danger btn-sm" onclick="deletar(${tipo.id})">
                    Deletar
                </button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

async function deletar(id) {

    if (!confirm("Deseja realmente excluir?")) return;

    await fetch(`${API_DELETAR}/${id}`, {
        method: "DELETE"
    });

    listarTiposDeContas();
}


let editandoId = null;

function editar(id, nome, categoria, descricao) {

    editandoId = id;

    document.getElementById("nome").value = nome;
    document.getElementById("categoria").value = categoria;
    document.getElementById("descricao").value = descricao;

    abrirModal();
}

async function atualizar() {

    const tipo = {
        nome: document.getElementById("nome").value,
        categoria: document.getElementById("categoria").value,
        descricao: document.getElementById("descricao").value
    };

    await fetch(`${API_ATUALIZAR}/${editandoId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(tipo)
    });

    listarTiposDeContas();
    fecharModal();
}

async function salvar() {

    const conta = {
        nome: document.getElementById("nome").value,
        categoria: document.getElementById("categoria").value,
        descricao: document.getElementById("descricao").value
    };

    await fetch(API_SALVAR, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(conta)
    });

    listarTiposDeContas();
    limparFormulario();
}

function limparFormulario() {
    document.getElementById("nome").value = "";
    document.getElementById("categoria").value = "";
    document.getElementById("descricao").value = "";
}


function abrirModal() {

    const modal = new bootstrap.Modal(document.getElementById("modalConta"));
    modal.show();
}

function fecharModal() {

    const modal = bootstrap.Modal.getInstance(document.getElementById("modalConta"));
    modal.hide();
}