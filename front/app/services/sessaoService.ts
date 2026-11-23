import axios from "axios";
import { Sessao } from "../(sistema)/types/sessao";
import api from "./api";
import { mensagemErro } from "./erroApi";


export async function buscarListaSessao(): Promise<Sessao[]>{
try {
    const dados = await api.get<Sessao[]>('http://localhost:8080/sessoes')

    if (dados.status == 200) {
        return dados.data;
    }
    return [];
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function consultaSessaoPorId(id:number):Promise<Sessao>{
try {
    const valorSessaoBack = await api.get<Sessao>('http://localhost:8080/sessoes/' + id);
    return valorSessaoBack.data;
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function deletarSessao(id:number): Promise<number>{
try {
    var dados = await api.delete('http://localhost:8080/sessoes/' + id + '/excluir')
    return dados.status
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function atualizarStatusSessao(sessao: Sessao): Promise<void>{
try {
    var novoStatus = {};
    if (sessao.statusSessao === "ATIVO") {
        novoStatus = { statusSessao: "DESATIVADO" }
    } else {
        novoStatus = { statusSessao: "ATIVO" }
    }

    var dadosRetorno = await api.patch('http://localhost:8080/sessoes/' + sessao.id + '/status-sessao', novoStatus);
    if (dadosRetorno.status === 200) {
        alert("Atualizado status com sucesso!")
    } else {
        alert(dadosRetorno.data);
        return;
    }
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function atualizarTipoSessao(sessao: Sessao):Promise<void>{
try {
    var novoTipo = {};
    if (sessao.status === "EXIBICAO_2D") {
        novoTipo = { statusExibicao: "EXIBICAO_3D" }
    } else {
        novoTipo = { statusExibicao: "EXIBICAO_2D" }
    }

    var dadosRetorno = await api.patch('http://localhost:8080/sessoes/' + sessao.id + '/status', novoTipo);
    if (dadosRetorno.status === 200) {
        alert("Atualizado tipo com sucesso!")
    } else {
        alert(dadosRetorno.data);
        return;
    }
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function atualizarSessao(sessao: Sessao):Promise<number>{
try {
    var dadosRetorno = await api.put<number>('http://localhost:8080/sessoes/' + sessao.id, sessao)
    return dadosRetorno.data;
} catch (error) {
    throw new Error(mensagemErro(error));
}
}

export async function salvarSessao (sessao:Sessao):Promise<number>{
try {
    var dadosRetorno = await api.post<number>('http://localhost:8080/sessoes', sessao)
    return dadosRetorno.data;
} catch (error) {
    throw new Error(mensagemErro(error));
}
}