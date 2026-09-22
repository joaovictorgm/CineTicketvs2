'use client'

import { useEffect, useState } from "react";
import {Filme} from "../types/filmes"
import axios from "axios";
import Link from "next/link";



export default function Filmes(){
    const [filmes,setFilme] = useState<Filme[]>([]);

    useEffect(() =>{
        carregarDados();
    },[])


const carregarDados = async()=>{
    try{
        const dados = axios.get<Filme[]>("http://localhost:8080/filmes");
        console.log("Resposta da API:", (await dados).data);
        setFilme((await dados).data);
    }catch (error) {
        alert("Erro ao carregar dados")
    }
    }

    return(
           <div className="min-h-screen bg-blue-100 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de Filmes
                </h1>
                <Link href="/filmes/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"></Link>
            </div>
            <div>
                <div className="bg-white rounded-xl shadow-md overflow-hidden">
                    <table className="w-full text-left">
                        <thead className="bg-blue-600 text-white">
                            <tr>
                               <th className="px-4 py-3 font-semibold">Código</th> 
                               <th className="px-4 py-3 font-semibold">titulo</th>
                               <th className="px-4 py-3 font-semibold">Duração </th>
                               <th className="px-4 py-3 font-semibold">Classificação Indicativa</th>
                               <th className="px-4 py-3 font-semibold">Data de Estreia</th>
                               <th className="px-4 py-3 font-semibold">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-100">
                            {filmes.map((filme)=>(
                                <tr key={filme.id} className="hover:bg-blue-50">
                                     <td className="px-4 py-3 text-blue-900">{filme.id}</td>
                                    <td className="px-4 py-3 text-blue-900">{filme.titulo}</td>
                                    <td className="px-4 py-3 text-blue-900">{filme.duracaoMinutos}</td>
                                    <td className="px-4 py-3 text-blue-900">{filme.classificacaoEtaria}</td>
                                    <td className="px-4 py-3 text-blue-900">{filme.dataEstreia}</td>
                                    <td className="px-4 py-3"><Link href={`/filmes/${filme.id}/editar`} className="text-blue-600 hover:underline">Editar</Link></td>
                                </tr>

                            ))}
                            {filmes.length ===0 &&(
                                <tr>
                                    <td colSpan={5} className="px-4 py-6 text-center text-blue-500">
                                        Nenhum filme encontrado
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
           </div>
    )
}