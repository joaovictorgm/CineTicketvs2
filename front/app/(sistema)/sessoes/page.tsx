'use client'

import Link from "next/link";
import { useEffect, useState } from "react";
import { Sessao } from "../types/sessao";
import axios from "axios";

export default function Sessoes() {

const [sessoes,setSessao] = useState<Sessao[]>([]);

useEffect(()=>{
    carregarDados();
}, [])

const carregarDados = async () =>{
    try{
        const dados = axios.get<Sessao[]>("http://localhost:8080/sessoes")
        setSessao((await dados).data);
    }catch(erros){
        alert("Erro ao carregar Dados")
    }
}

const handlerDeletarSessao = async(sessao:Sessao)=>{
    var dadosRetorno = await axios.delete('http://localhost:8080/sessoes/'+sessao.id+'/excluir')
    
    if(dadosRetorno.status==200){
        alert("Excluido com sucesso!")
    } else {
        alert(dadosRetorno.data);
        return;
    }

    carregarDados();

}

const handlerAlterarStatusSessao = async(sessao:Sessao)=>{
    var novoStatus = {};
    if(sessao.statusSessao==="ATIVO"){
        novoStatus = {statusSessao:"DESATIVADO"}
    } else{
        novoStatus = {statusSessao:"ATIVO"}
    }

    var dadosRetorno = await axios.patch('http://localhost:8080/sessoes/'+sessao.id+'/status-sessao', novoStatus);
    if(dadosRetorno.status===200){
        alert("Atualizado status com sucesso!")
    }else{
        alert(dadosRetorno.data);
        return;
    }

    carregarDados();
}

const handlerAlterarTipoSessao = async(sessao:Sessao)=>{
    var novoTipo = {};
    if(sessao.status==="EXIBIÇÃO_2D"){
        novoTipo = {statusExibicao:"EXIBICÃO_3D"}
    } else{
        novoTipo = {statusExibicao:"EXIBIÇÃO_2D"}
    }

    var dadosRetorno = await axios.patch('http://localhost:8080/sessoes/'+sessao.id+'/status', novoTipo);
    if(dadosRetorno.status===200){
        alert("Atualizado tipo com sucesso!")
    }else{
        alert(dadosRetorno.data);
        return;
    }

    carregarDados();
}

    return (
        <div className="bg-blue-50 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestao de Sessões
                </h1>
                <Link href="/sessoes/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">Cadastrar nova Sessão</Link>
            </div>
            <div>
                <div className="bg-white rounded-xl shadow-md overflow-hidden">
                    <table className="w-full text-left">
                        <thead className="bg-blue-600 text-white">
                          <tr>
                            <th className="px-4 py-3 font-semibold">Codigo</th>
                        
                            <th className="px-4 py-3 font-semibold">Filme</th>

                            <th className="px-4 py-3 font-semibold">Data</th>
                        
                            <th className="px-4 py-3 font-semibold">Sala</th>
                            
                            <th className="px-4 py-3 font-semibold">Status</th>
                        
                            <th className="px-4 py-3 font-semibold">StatusSessao</th>

                            <th className="px-4 py-3 font-semibold">Preço</th>
                        
                            <th className="px-4 py-3 font-semibold">Assentos Disponiveis</th>

                            <th className="px-4 py-3 font-semibold">Ações</th>
                        </tr> 
                        </thead>
                        <tbody className="divide-y divide-blue-100">
                           {sessoes.map((sessao)=>(
                        <tr key={sessao.id} className="hover:bg-blue-50">
                            <td className="px-4 py-3 text-blue-900">
                               {sessao.id}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                               {sessao.filme}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {sessao.data}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {sessao.sala}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {sessao.status}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {sessao.statusSessao}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {sessao.preco}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {sessao.assentosDisponiveis}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                             <div className="flex flex-col gap-1 items-start">
   <Link href={`/sessoes/${sessao.id}/editar`} className="text-blue-600 hover:underline">
            EDITAR
        </Link>
        <button onClick={() => handlerDeletarSessao(sessao)} className="text-red-600 hover:text-red-800 font-medium transition-colors text-left">
            DELETAR
        </button>
        <button
            onClick={() => handlerAlterarStatusSessao(sessao)}
            className={`font-medium transition-colors text-left ${
                sessao.statusSessao === 'BLOQUEADO'
                    ? 'text-orange-600 hover:text-orange-800'
                    : 'text-green-600 hover:text-green-800'
            }`}
        >
            {sessao.status}
        </button>
        <button
    onClick={() => handlerAlterarTipoSessao(sessao)}
    className={`font-medium transition-colors text-left ${
        sessao.status === 'EXIBIÇÃO_2D'
            ? 'text-orange-600 hover:text-blue-800'
            : 'text-green-600 hover:text-green-800'
    }`}
>
    {sessao.statusSessao}
</button>
    </div>
    </td>
                        </tr>
                        ))}
                             { sessoes.length ===0 &&(
                            <tr>
                                <td colSpan={6} className="px-6 py-12 text-center text">
                                    Nenhuma sessao Encontrada
                                </td>
                            </tr>
                        )}
                        
                        </tbody>
                    </table>
                </div>
            </div>

            
            

           </div> )
}