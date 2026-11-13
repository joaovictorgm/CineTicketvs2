import { Ingresso } from "../(sistema)/types/ingresso";
import api from "./api";


export async function buscarListaIngressos() : Promise<Ingresso[]> {
    const dados = await api.get<Ingresso[]>("http://localhost:8080/ingressos");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
}

export async function deletarIngresso(id:number): Promise<number>{
    var dadosRetorno = await api.delete('http://localhost:8080/ingressos/' + id + '/excluir')
    return dadosRetorno.status;
}

export async function atualizarStatusIngresso(ingresso:Ingresso): Promise<void>{
    var novoStatus = {};
    if (Ingresso.statusIngresso== "DISPONIVEL") {
        novoStatus = { status: "PAGO" }
    } else {
        novoStatus = { status: "DISPONIVEL" }
    }

    var dadosRetorno = await api.patch('http://localhost:8080/ingressos/' + ingresso.id + '/status', novoStatus)
    if (dadosRetorno.status == 200) {
        alert("Atualizado status com sucesso!")

    } else {
        alert(dadosRetorno.data);
        return;
}
}


