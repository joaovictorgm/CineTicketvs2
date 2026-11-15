import axios from "axios";
import { Sessao } from "../(sistema)/types/sessao";
import api from "./api";


export async function buscarListaSessao(): Promise<Sessao[]>{
      const dados = await axios.get<Sessao[]>('http://localhost:8080/sessoes/')

        if (dados.status == 200) {
            return dados.data;
        } 
        return [];
        }

export async function consultaSessaoPorId(id:number):Promise<Sessao>{
  const valorSessaoBack = await api.get<Sessao>('http://localhost:8080/sessoes/' + id);
    return valorSessaoBack.data;
}

export async function deletarSessao(id:number): Promise<number>{
     var dados = await axios.delete('http://localhost:8080/sessoes/' + id + '/excluir')
     return dados.status

}

export async function atualizarStatusSessao(sessao: Sessao): Promise<void>{
   var novoStatus = {};
        if (sessao.status === "ATIVO") {
            novoStatus = { status: "BLOQUEADO" }
        } else {
            novoStatus = { status: "ATIVO" }
        }

        var dadosRetorno = await api.patch('http://localhost:8080/filmes/' + sessao.id + '/status', novoStatus);
        if (dadosRetorno.status === 200) {
            alert("Atualizado status com sucesso!")
        } else {
            alert(dadosRetorno.data);

            return;
        }
      }

      export async function atualizarTipoSessao(sessao: Sessao):Promise<void>{
          var novoTipo = {};
        if (sessao.statusSessao=== "EXIBIÇÃO_2D") {
            novoTipo = { statusExibicao: "EXIBICÃO_3D" }
        } else {
            novoTipo = { statusExibicao: "EXIBIÇÃO_2D" }
        }

        var dadosRetorno = await axios.patch('http://localhost:8080/sessoes/' + sessao.id + '/status', novoTipo);
        if (dadosRetorno.status === 200) {
            alert("Atualizado tipo com sucesso!")
        } else {
            alert(dadosRetorno.data);
            return;
        }
    }

        export async function atualizarSessao(sessao: Sessao):Promise<number>{
            var dadosRetorno = await axios.put<number>('http://localhost:8080/sessoes/' + sessao.id, sessao)
            return dadosRetorno.data;
        }

        export async function salvarSessao (sessao:Sessao):Promise<number>{
           var dadosRetorno = await axios.post<number>('http://localhost:8080/sessoes', sessao)
           return dadosRetorno.data;
        }
