import { Ingresso } from "../(sistema)/types/ingresso";
import api from "./api";
import { mensagemErro } from "./erroApi";


export async function buscarListaIngressos() : Promise<Ingresso[]> {
try {
    const dados = await api.get<Ingresso[]>("http://localhost:8080/ingressos");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function consultaIngressoPorId(id:number):Promise<Ingresso>{
try {
    const valorSessaoBack = await api.get<Ingresso>('http://localhost:8080/ingressos/' + id);
    return valorSessaoBack.data;
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function deletarIngresso(id:number): Promise<number>{
try {
    var dadosRetorno = await api.delete('http://localhost:8080/ingressos/' + id + '/excluir')
    return dadosRetorno.status;
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function atualizarStatusIngresso(ingresso:Ingresso): Promise<void>{
try {
    var novoStatus = {};
    if (ingresso.statusIngresso == "DISPONIVEL") {          // <- minúsculo: o ingresso recebido
        novoStatus = { statusIngresso: "PAGO" }             // <- nome do campo certo
    } else {
        novoStatus = { statusIngresso: "DISPONIVEL" }
    }

    var dadosRetorno = await api.patch('http://localhost:8080/ingressos/' + ingresso.id + '/status', novoStatus)
    if (dadosRetorno.status == 200) {
        alert("Atualizado status com sucesso!")
    } else {
        alert(dadosRetorno.data);
        return;
    }
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function pagarIngresso(id: number): Promise<boolean> {
try {
    const resposta = await api.patch('http://localhost:8080/ingressos/' + id + '/status', { statusIngresso: "PAGO" });
    return resposta.status === 200;
} catch (erro) {
    return false;
}
}

export async function salvarIngresso(ingresso: Ingresso): Promise<string | null> {
try {
    await api.post('http://localhost:8080/ingressos', ingresso);
    return null;
} catch (erro) {
    return mensagemErro(erro);
}
}

export async function atualizarIngresso(ingresso: Ingresso): Promise<string | null> {
try {
    await api.put('http://localhost:8080/ingressos/' + ingresso.id, ingresso);
    return null;
} catch (erro) {
    return mensagemErro(erro);
}
}