import { Gerente } from "../(sistema)/types/gerente";
import api from "./api";


export async function buscarListaGerente() : Promise<Gerente[]> {
    const dados = await api.get<Gerente[]>("http://localhost:8080/gerentes"); 
    if(dados.status == 200){
        return dados.data;
    }

    return [];

}

export async function deletarGerente(id:number): Promise<number>{
    var dadosRetorno = await api.delete('http://localhost:8080/gerentes/' +id + '/excluir')
    return dadosRetorno.status;
}


export async function atualizarStatusGerente(gerente:Gerente): Promise<void>{

    var novoStatus = {};
        if (gerente.status === "ATIVO") {
            novoStatus = { status: "BLOQUEADO" }
        } else {
            novoStatus = { status: "ATIVO" }
        }

        var dadosRetorno = await api.patch('http://localhost:8080/gerentes/' + gerente.id + '/status', novoStatus);
        if (dadosRetorno.status === 200) {
            alert("Atualizado status como sucesso!")
        } else {
            alert(dadosRetorno.data);

            return;
}

}

export async function consultaGerentePorId(id:number):Promise<Gerente>{
    const valorGerenteBack = await api.get<Gerente>('http://localhost:8080/gerentes/' + id);
    return valorGerenteBack.data;
}

export async function atualizarGerente (gerente: Gerente): Promise<number>{
    var dadosRetorno = await api.put<number>('http://localhost:8080/gerentes/' + gerente.id, gerente);
    return dadosRetorno.data
}

export async function  salvarGerente (gerente:Gerente): Promise<number>{
    var dadosRetorno = await api.post<number>('http://localhost:8080/gerentes/',gerente);
    return dadosRetorno.data;

}
