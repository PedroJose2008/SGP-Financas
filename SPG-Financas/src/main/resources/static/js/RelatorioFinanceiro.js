const API_URL = "http://192.168.56.1:1433:8013/Cadastro";

document.addEventListener("DOMContentLoaded", () => {
    carregarRelatorios();
});

async function carregarRelatorios() {
    try {
        const response = await fetch(`${API_URL}/listartodos`);
        if (!response.ok) {
            throw new Error(`Erro: ${response.status}`);
        }
        const relatorios = await response.json();
        const tbody = document.querySelector(".table-card table tbody");
        tbody.innerHTML = "";
        if (relatorios.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align: center; color: var(--muted);">Nenhum registro encontrado.</td></tr>`;
            return;
        }
        relatorios.forEach(item => {
            const tr = document.createElement("tr");
            const dataFormatada = formatarDataBR(item.dataInicial);
            const isReceita = item.natureza.toLowerCase() === "receita" || item.natureza.toLowerCase() === "entrada";
            const badgeClass = isReceita ? "badge-green" : "badge-red";
            const badgeLabel = isReceita ? "Entrada" : "Saída";
            const valorSinal = isReceita ? "+" : "-";
            const valorCor = isReceita ? "#34d399" : "#fb7185";
            const nomeTipoConta = item.tipoConta && item.tipoConta.descricao ? item.tipoConta.descricao : "Não Informado";
            tr.innerHTML = `
                <td>${dataFormatada}</td>
                <td>${item.cliente} <br><small style="color: var(--muted); font-size: 11px;">Forn: ${item.fornecedor}</small></td>
                <td>${nomeTipoConta}</td>
                <td><span class="badge ${badgeClass}">${badgeLabel}</span></td>
                <td style="color: ${valorCor}">${valorSinal} R$ ${item.id}</td>
                <td>
                    <div style="display: flex; gap: 8px;">
                        <button class="btn-sm" onclick="buscarPorId(${item.id})" style="background: #6366f1; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">Editar</button>
                        <button class="btn-sm" onclick="deletarRelatorio(${item.id})" style="background: #f43f5e; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">Excluir</button>
                    </div>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error(error);
    }
}

async function buscarPorId(id) {
    try {
        const response = await fetch(`${API_URL}/listarPorId/${id}`);
        if (!response.ok) throw new Error("Erro ao buscar");
        const relatorio = await response.json();
        alert(`Editando cliente: ${relatorio.cliente}\nNatureza: ${relatorio.natureza}`);
    } catch (error) {
        console.error(error);
    }
}

async function deletarRelatorio(id) {
    if (!confirm("Tem certeza que deseja excluir?")) return;
    try {
        const response = await fetch(`${API_URL}/deletar/${id}`, {
            method: "DELETE"
        });
        if (response.status === 204 || response.ok) {
            alert("Deletado com sucesso!");
            carregarRelatorios();
        } else {
            alert("Erro ao deletar");
        }
    } catch (error) {
        console.error(error);
    }
}

async function atualizarRelatorio(id, dadosAtualizados) {
    try {
        const response = await fetch(`${API_URL}/atualizar/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dadosAtualizados)
        });
        if (response.ok) {
            alert("Atualizado com sucesso!");
            carregarRelatorios();
        }
    } catch (error) {
        console.error(error);
    }
}

function formatarDataBR(dataString) {
    if (!dataString) return "--/--/----";
    const [ano, mes, dia] = dataString.split("-");
    return `${dia}/${mes}/${ano}`;
}