'use client'

import Link from "@/node_modules/next/link";
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

    return (
        <div className="min-h-screen bg-blue-50 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestao de Sessões
                </h1>
                <Link href="/sessoes/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"></Link>
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
                        </tr>
                        ))} 
                        {sessoes.length == 0 && (
                            <tr>
                                <td colSpan={8} className="px-6 py-12 text-center text">
                                    Nenhuma sessão encontrada
                                </td>
                            </tr>
                        ) }
                        </tbody>
                    </table>
                </div>
            </div>

            
            

           </div> )
}