import { Filme } from "../(sistema)/types/filmes";
import api from "./api";


export async function buscarListaFilmes() : Promise<Filme[]> {
    const dados = await api.get<Filme[]>("http://localhost:8080/filmes");
    if(dados.status == 200){
        return dados.data;
    }

    return [];
}


export async function deletarFilme(id:number): Promise<number>{
    var dados = await api.delete('http://localhost:8080/filmes/' + id + '/excluir');
    return dados.status;
}


export async function atualizarStatusFilme(filme:Filme): Promise<void>{
    var novoStatus = {};
        if (filme.status === "ATIVO") {
            novoStatus = { status: "BLOQUEADO" }
        } else {
            novoStatus = { status: "ATIVO" }
        }

        var dadosRetorno = await api.patch('http://localhost:8080/filmes/' + filme.id + '/status', novoStatus);
        if (dadosRetorno.status === 200) {
            alert("Atualizado status com sucesso!")
        } else {
            alert(dadosRetorno.data);

            return;
        }
    }

    export async function consultaFilmePorId(id:number):Promise<Filme>{
        
        const valorFilmeBack = await api.get<Filme>('http://localhost:8080/filmes/' + id);
        return valorFilmeBack.data;

    }

    export async function atualizarFilme(filme: Filme): Promise<number>{
        var dadosRetorno = await api.put<number>('http://localhost:8080/filmes/'+filme.id,filme)
        return dadosRetorno.data;
    }

    export async function  salvarFilme (filme:Filme): Promise<number>{
        var dadosRetorno = await api.post<number>('http://localhost:8080/filmes' ,filme)
        return dadosRetorno.data;
    }