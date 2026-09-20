'use client'

import Link from "@/node_modules/next/link";
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
            console.log("Resposta da API:", (await dados).data);
            setGerente((await dados).data);
        } catch (error) {
            alert("Erro ao carregar dados")
        }
    }

    return (
        <div className="bg-blue-50 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestao de gerentes
                </h1>
                <Link href="/gerentes/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"></Link>
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
                            <td className="px-4 py-3 text-blue-900">
                               <Link href={`/gerentes/${gerente.id}/editar`}>Editar</Link>
                            </td>
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