const API_BUSCAR_TODOS = 'http://localhost:8013/contasReceber/listar';
const API_ATUALIZAR = 'http://localhost:8013/contasReceber/atualizar'; 
const API_DELETAR = 'http://localhost:8013/contasReceber/deletar';
const API_SALVAR = "http://localhost:8013/contasReceber/salvar";       
const API_PESQUISAR = "http://localhost:8013/contasReceber/pesquisar";
const API_LISTAR_TIPOS = 'http://localhost:8013/tipodecontas/listartodos'; 

let editandoId = null;
let listaContasGlobal = [];

// 1. CARREGAR OS TIPOS DE CONTA
async function carregarTiposDeConta() {
    const select = document.getElementById("tipoConta");
    if (!select) return;

    try {
        const response = await fetch(API_LISTAR_TIPOS);
        if (!response.ok) throw new Error("Rota não encontrada no backend");
        
        const tipos = await response.json();
        select.innerHTML = '<option value="" disabled selected>Selecione um tipo</option>';
        
        tipos.forEach(tipo => {
            const option = document.createElement("option");
            option.value = tipo.id;
            option.textContent = tipo.nome; 
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar tipos de conta:", error);
    }
}

// 2. LISTAR TODOS OS REGISTROS
async function listarContasReceber() {
    try {
        const response = await fetch(API_BUSCAR_TODOS);
        if (!response.ok) throw new Error("Erro na resposta do servidor");
        const contasReceber = await response.json();
        listaContasGlobal = contasReceber;
        
        renderizarTabela(contasReceber);
        
        // CHAMADA INSERIDA AQUI: Atualiza os cards assim que a página abre
        atualizarCardsIndicadores(contasReceber);
        
    } catch (error) {
        console.error("Erro ao listar contas a receber:", error);
    }
}

// 3. RENDERIZAR TABELA
function renderizarTabela(lista) {
    const tbody = document.getElementById("receber-tbody");
    if (!tbody) return;
    tbody.innerHTML = "";

    const contador = document.getElementById('receber-count');
    if (contador) {
        contador.textContent = lista.length + ' lançamento' + (lista.length !== 1 ? 's' : '');
    }

    if (!lista || lista.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" style="text-align:center;color:var(--muted);padding:32px">Nenhum título encontrado</td></tr>`;
        return;
    }

    const cls = { Pendente: 'badge-amber', Recebido: 'badge-green', Atrasado: 'badge-red' };

    lista.forEach(item => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${item.idClientes || '---'}</td>
            <td>${item.dataEmissao || '---'}</td>
            <td>${item.dataVencimento || '---'}</td>
            <td>${item.dataRecebimento || '---'}</td>
            <td>R$ ${parseFloat(item.valor || 0).toFixed(2)}</td>
            <td><span class="badge ${cls[item.status] || 'badge-amber'}">${item.status || 'Pendente'}</span></td>
            <td>
                <button class="btn-editar" onclick="Editar()">Editar</button>
                <button class="btn-excluir" onclick="deletar(${item.id})">Excluir</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function ejecutarAcaoPrincipal() {
    if (editandoId) {
        Editar();
    } else {
        salvar();
    }
}

// 5. SALVAR REGISTRO (POST)
async function salvar() {
    const valorInput = parseFloat(document.getElementById("valor").value);
    const idClienteInput = parseInt(document.getElementById("cliente").value);
    const idFornecedorInput = parseInt(document.getElementById("fornecedor").value);
    const tipoContaSelect = document.getElementById("tipoConta").value;

    if (isNaN(valorInput) || valorInput < 3.00) {
        alert("O valor mínimo permitido para cadastro é R$ 3,00");
        return;
    }
    if (isNaN(idClienteInput) || isNaN(idFornecedorInput)) {
        alert("Os campos ID Cliente e ID Fornecedor precisam ser números válidos!");
        return;
    }
    if (!tipoContaSelect) {
        alert("Por favor, selecione um Tipo de Conta válido!");
        return;
    }

    const conta = {
        idClientes: idClienteInput,
        idFornecedores: idFornecedorInput,
        descricao: document.getElementById("descricao").value,
        valor: valorInput,
        dataVencimento: document.getElementById("vencimento").value,
        dataEmissao: document.getElementById("emissao").value,
        dataRecebimento: document.getElementById("recebimento").value,
        tipoconta: { id: parseInt(tipoContaSelect) },
        status: document.getElementById("status").value,
        observacao: document.getElementById("observacao").value
    };

    try {
        const response = await fetch(API_SALVAR, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(conta)
        });

        if (response.ok) {
            limparFormulario();
            listarContasReceber();
            alert("Título registrado com sucesso!");
        } else {
            alert("Erro ao salvar no servidor.");
        }
    } catch (error) {
        console.error("Erro na requisição de salvamento:", error);
    }
}

// 6. ATUALIZAR REGISTRO / PREENCHER FORMULÁRIO COMPLETO
async function Editar() {
    if (!editandoId) {
        const evento = window.event;
        if (!evento) return;

        const botaoClicado = evento.target;
        const linha = botaoClicado.closest("tr");
        if (!linha) return;

        const botaoExcluir = linha.querySelector(".btn-excluir");
        if (botaoExcluir) {
            const matchId = botaoExcluir.getAttribute("onclick").match(/\d+/);
            if (matchId) editandoId = parseInt(matchId[0]);
        }

        if (!editandoId) {
            alert("Não foi possível identificar o ID deste registro.");
            return;
        }

        const contaCompleta = listaContasGlobal.find(item => item.id === editandoId);

        if (!contaCompleta) {
            alert("Não foi possível carregar os dados detalhados deste registro.");
            return;
        }

        document.getElementById("cliente").value = contaCompleta.idClientes || "";
        document.getElementById("descricao").value = contaCompleta.descricao || "";
        document.getElementById("valor").value = contaCompleta.valor || "";
        
        document.getElementById("vencimento").value = contaCompleta.dataVencimento ? contaCompleta.dataVencimento.split('T')[0] : "";
        document.getElementById("emissao").value = contaCompleta.dataEmissao ? contaCompleta.dataEmissao.split('T')[0] : "";
        document.getElementById("recebimento").value = contaCompleta.dataRecebimento ? contaCompleta.dataRecebimento.split('T')[0] : "";
        
        document.getElementById("fornecedor").value = contaCompleta.idFornecedores || "";
        document.getElementById("observacao").value = contaCompleta.observacao || "";
        document.getElementById("status").value = contaCompleta.status || "Pendente";

        if (contaCompleta.tipoconta && contaCompleta.tipoconta.id) {
            document.getElementById("tipoConta").value = contaCompleta.tipoconta.id;
        } else {
            document.getElementById("tipoConta").value = "";
        }

        const btnRegistrar = document.querySelector(".btn-primary-full");
        if (btnRegistrar) btnRegistrar.textContent = "Atualizar Lançamento ›";

        document.getElementById("receber-form").scrollIntoView({ behavior: 'smooth' });
        
        return;
    }

    const tipoContaSelect = document.getElementById("tipoConta").value;
    const dadosAtualizados = {
        id: editandoId,
        idClientes: parseInt(document.getElementById("cliente").value),
        idFornecedores: parseInt(document.getElementById("fornecedor").value) || 0,
        descricao: document.getElementById("descricao").value,
        valor: parseFloat(document.getElementById("valor").value),
        dataVencimento: document.getElementById("vencimento").value,
        dataEmissao: document.getElementById("emissao").value,
        dataRecebimento: document.getElementById("recebimento").value,
        tipoconta: tipoContaSelect ? { id: parseInt(tipoContaSelect) } : null,
        status: document.getElementById("status").value,
        observacao: document.getElementById("observacao").value
    };

    try {
        const response = await fetch(`${API_ATUALIZAR}/${editandoId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dadosAtualizados)
        });

        if (response.ok) {
            editandoId = null;
            limparFormulario();
            listarContasReceber();
            alert("Lançamento atualizado com sucesso!");
        } else {
            alert("Erro ao atualizar o registro no servidor.");
        }
    } catch (error) {
        console.error("Erro ao atualizar registro:", error);
    }
}

// 8. DELETAR
async function deletar(id) {
    if (!confirm("Deseja realmente excluir este lançamento?")) return;
    try {
        const response = await fetch(`${API_DELETAR}/${id}`, { method: "DELETE" });
        if (response.ok) {
            listarContasReceber();
            alert("Lançamento removido com sucesso.");
        }
    } catch (error) {
        console.error("Erro ao deletar:", error);
    }
}

// 9. LIMPAR FORMULÁRIO
function limparFormulario() {
    editandoId = null;
    document.getElementById("receber-form").reset();
    document.getElementById("tipoConta").value = "";
    document.getElementById("status").value = "Pendente";
    const btnRegistrar = document.querySelector(".btn-primary-full");
    if(btnRegistrar) btnRegistrar.textContent = "Registrar ›";
}

// 10. ONLOAD
window.onload = () => {
    carregarTiposDeConta();
    listarContasReceber();

    const btnFiltrar = document.getElementById('btn-filtrar');
    if (btnFiltrar) btnFiltrar.onclick = () => pesquisarContas();

    const btnLimparFiltro = document.getElementById('btn-limpar-filtro');
    if (btnLimparFiltro) {
        btnLimparFiltro.onclick = () => {
            document.getElementById('filtro-form').reset();
            listarContasReceber();
        };
    }
};

// 11. PESQUISAR COM FILTROS
async function pesquisarContas() {
    const filtroCliente = document.getElementById('f-cliente').value.trim();
    const filtroDataInicio = document.getElementById('f-data-inicio').value;
    const filtroDataFim = document.getElementById('f-data-fim').value;
    const filtroStatus = document.getElementById('f-status').value;

    const url = new URL(API_PESQUISAR);
    if (filtroCliente) url.searchParams.append('idCliente', filtroCliente); 
    if (filtroDataInicio) url.searchParams.append('dataInicio', filtroDataInicio);
    if (filtroDataFim) url.searchParams.append('dataFim', filtroDataFim);
    if (filtroStatus) url.searchParams.append('status', filtroStatus);

    try {
        const response = await fetch(url);
        if (response.ok) {
            const resultados = await response.json();
            listaContasGlobal = resultados; 
            renderizarTabela(resultados);
            
            // CHAMADA INSERIDA AQUI: Atualiza os cards com base no filtro aplicado!
            atualizarCardsIndicadores(resultados);
        } else {
            console.error("Erro na resposta do servidor:", response.statusText);
        }
    } catch (error) {
        console.error("Erro na pesquisa:", error);
    }
}

// 12. ATUALIZAR INDICADORES DOS CARDS
function atualizarCardsIndicadores(listaContas) {
    let totalGeral = 0;
    let qtdTotal = listaContas.length;

    let totalPendentes = 0;
    let qtdPendentes = 0;

    let totalAtrasados = 0;
    let qtdAtrasados = 0;

    listaContas.forEach(conta => {
        const valorNumerico = parseFloat(conta.valor) || 0;
        
        totalGeral += valorNumerico;

        if (conta.status === 'Pendente') {
            totalPendentes += valorNumerico;
            qtdPendentes++;
        } else if (conta.status === 'Atrasado') {
            totalAtrasados += valorNumerico;
            qtdAtrasados++;
        }
		
		
    });

    // Atualiza os elementos HTML
    if(document.getElementById('card-total-qtd')) document.getElementById('card-total-qtd').innerText = `${qtdTotal} títulos`;
    if(document.getElementById('card-total-valor')) document.getElementById('card-total-valor').innerText = totalGeral.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

    if(document.getElementById('card-pendente-qtd')) document.getElementById('card-pendente-qtd').innerText = `${qtdPendentes} itens`;
    if(document.getElementById('card-pendente-valor')) document.getElementById('card-pendente-valor').innerText = totalPendentes.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

    if(document.getElementById('card-atrasado-qtd')) document.getElementById('card-atrasado-qtd').innerText = `${qtdAtrasados} itens`;
    if(document.getElementById('card-atrasado-valor')) document.getElementById('card-atrasado-valor').innerText = totalAtrasados.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}