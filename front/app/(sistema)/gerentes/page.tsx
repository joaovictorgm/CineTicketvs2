'use client'

import Link from "next/link";
import { useEffect, useState } from "react";
import axios from "axios";
import { Gerente } from "../types/gerente";


export default function Gerentes() {

    const [gerentes, setGerente] = useState<Gerente[]>([]);

    useEffect(()=>{
        carregarDados();
    }, [])

    const carregarDados = async () => {
        try{
            const dados = axios.get<Gerente[]>("http://localhost:8080/gerentes");
            setGerente((await dados).data);
        } catch (error) {
            alert("Erro ao carregar dados")
        }
    }

    const handlerDeletarGerente = async(gerente:Gerente) =>{
        var dadosRetorno = await axios.delete('http://localhost:8080/gerentes/'+gerente.id+'/excluir')

        if(dadosRetorno.status ==200){
            alert("Excluido como sucesso!");
        } else {
            alert(dadosRetorno.data);
            return;
        }

        carregarDados();
    }

    const handlerAlterarStatusGerente = async(gerente:Gerente)=>{
        var novoStatus = {};
        if(gerente.status==="ATIVO"){
            novoStatus = {status:"EXCLUIDO"}
        }else{
            novoStatus = {status:"ATIVO"}
        }

        var dadosRetorno = await axios.patch('http://localhost:8080/usuarios/'+gerente.id+'/status',novoStatus);
        if(dadosRetorno.status===200){
            alert("Atualizado status como sucesso!")
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
                    Gestao de gerentes
                </h1>
                <Link href="/gerentes/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">Cadastrar novo gerente</Link>
            </div>

            <div>
                <div className="bg-white rounded-xl shadow-md overflow-hidden">
                    <table className="w-full text-left">
                        <thead className="bg-blue-600 text-white">
                            <tr>
                                <th className="px-4 py-3 font-semibold">Codigo</th>
                                <th className="px-4 py-3 font-semibold">Nome</th>
                                <th className="px-4 py-3 font-semibold">Email</th>
                                <th className="px-4 py-3 font-semibold">Senha</th>
                                <th className="px-4 py-3 font-semibold">Status</th>
                                <th className="px-4 py-3 font-semibold">Ações</th>
                                

                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-100">
                        {gerentes.map((gerente)=>(
                        <tr key={gerente.id} className="hover:bg-blue-50">
                            <td className="px-4 py-3 text-blue-900">
                               {gerente.id}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                               {gerente.nome}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {gerente.email}
                            </td>
                           
                            <td className="px-4 py-3 text-blue-900">
                                {gerente.status}
                            </td>
                            <div className="flex flex-col gap-1 items-start">
                            <Link href={`/gerentes/${gerente.id}/editar`}>EDITAR</Link>
                            <button onClick={() => handlerDeletarGerente(gerente)} className="text-red-600 hover:text-red-800 font-medium transition-colors text-left">
                                DELETAR
                            </button>
                            <button onClick={()=>handlerAlterarStatusGerente(gerente)} className={`font-medium transition-colors text-left ${
                gerente.status === 'EXCLUIDO'
                    ? 'text-orange-600 hover:text-orange-800'
                    : 'text-green-600 hover:text-green-800'
            }`}
        >
            {gerente.status}</button>
                            </div>
                            
                        </tr>
                        ))}
                          { gerentes.length ===0 &&(
                            <tr>
                                <td colSpan={5} className="px-6 py-12 text-center text">
                                    Nenhum gerente encontrado
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
            

           </div> )
}