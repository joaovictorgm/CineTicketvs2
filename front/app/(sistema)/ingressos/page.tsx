'use client'

import Link from "@/node_modules/next/link";
import { useEffect, useState } from "react";
import { Ingresso } from "../types/ingresso";
import axios from "axios";


export default function Ingressos() {

    const [ingressos , setIngresso] = useState<Ingresso[]>([]);

    useEffect(()=>{
        carregarDados();
    },[])

    const carregarDados = async () => {
        try{
            const dados = axios.get<Ingresso[]>("http://localhost:8080/ingressos");
            setIngresso((await dados).data);

        } catch(error){
            alert("Erro ao carregar dados")}
    }

    return (
        <div className="bg-blue-50 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestao de Ingressos
                </h1>
                <Link href="/ingressos/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"></Link>
            </div>

            <div>
                <div className="bg-white rounded-xl shadow-md overflow-hidden">
                    <table className="w-full text-left">
                        <thead className="bg-blue-600 text-white">
                            <tr>
                                <th className="px-4 py-3 font-semibold">Código</th>
                                <th className="px-4 py-3 font-semibold">Sessao</th>
                                <th className="px-4 py-3 font-semibold">Filme</th>
                                <th className="px-4 py-3 font-semibold">Assento</th>
                                <th className="px-4 py-3 font-semibold">Valor Pago</th>
                                <th className="px-4 py-3 font-semibold">Status</th>
                                <th className="px-4 py-3 font-semibold">Status Ingresso</th>
                                <th className="px-4 py-3 font-semibold">Data Compra</th>
                                <th className="px-4 py-3 font-semibold">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-100">
                            {ingressos.map((ingresso)=>(
                        <tr key={ingresso.id} className="hover:bg-blue-50">
                            <td className="px-4 py-3 text-blue-900">
                               {ingresso.id}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {ingresso.sessao}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {ingresso.filme}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {ingresso.assento}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {ingresso.valorPago}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {ingresso.status}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {ingresso.statusIngresso}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                               {ingresso.dataCompra}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                               <Link href={`/ingressos/${ingresso.id}/editar`}>Editar</Link>
                            </td>
                            </tr>
                            ))}
                            {ingressos.length == 0 && (
                            <tr>
                                <td colSpan={8} className="px-6 py-12 text-center text">
                                    Nenhum ingresso encontrado
                                </td>
                            </tr>
                        ) }
                        </tbody>
                    </table>
                </div>
            </div>
            

           </div> )
}