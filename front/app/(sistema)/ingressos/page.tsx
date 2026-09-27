'use client'

import Link from "@/node_modules/next/link";
import { useEffect, useState } from "react";
import { Ingresso } from "../types/ingresso";
import axios from "axios";


export default function Ingressos() {

    const [ingressos, setIngresso] = useState<Ingresso[]>([]);

    useEffect(() => {
        carregarDados();
    }, [])

    const carregarDados = async () => {
        try {
            const dados = axios.get<Ingresso[]>("http://localhost:8080/ingressos");
            setIngresso((await dados).data);

        } catch (error) {
            alert("Erro ao carregar dados")
        }
    }

    const handlerDeletarIngresso = async (ingresso: Ingresso) => {
        var dadosRetorno = await axios.delete('http://localhost:8080/ingressos/' + ingresso.id + '/excluir')

        if (dadosRetorno.status == 200) {
            alert("A venda foi realizda com sucesso!");
        } else {
            alert(dadosRetorno.data);
            return;
        }

        carregarDados();


    }

    const handlerAlterarStatusIngresso = async (ingresso: Ingresso) => {
        var novoStatus = {};
        if (ingresso.statusIngresso == "DISPONIVEL") {
            novoStatus = { status: "PAGO" }
        } else {
            novoStatus = { status: "DISPONIVEL" }
        }

        var dadosRetorno = await axios.patch('http://localhost:8080/ingressos/' + ingresso.id + '/status', novoStatus)
        if (dadosRetorno.status == 200) {
            alert("Atualizado status com sucesso!")

        } else {
            alert(dadosRetorno.data);
            return;
        }

        carregarDados();

    }





    return (
        <div className="bg-blue-50 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestao de Ingressos
                </h1>
                <Link href="/ingressos/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">Cadastrar novo ingresso</Link>
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
                            {ingressos.map((ingresso) => (
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
                                        {ingresso.statusTipo}
                                    </td>
                                    <td className="px-4 py-3 text-blue-900">
                                        {ingresso.statusIngresso}
                                    </td>
                                    <td className="px-4 py-3 text-blue-900">
                                        {ingresso.dataCompra}
                                    </td>
                                     <td className="px-4 py-3 text-blue-900">
                                    <div className="flex flex-col gap-1 items-start">
                                        <Link href={`/ingressos/${ingresso.id}/editar`} className="text-blue-600 hover:underline">
                                            EDITAR
                                        </Link>
                                        <button onClick={() => handlerDeletarIngresso(ingresso)} className="text-red-600 hover:text-red-800 font-medium transition-colors text-left">
                                            VENDER
                                        </button>
                                        <button
                                            onClick={() => handlerAlterarStatusIngresso(ingresso)}
                                            className={`font-medium transition-colors text-left ${
                                                ingresso.statusIngresso === 'PAGO'
                                                    ? 'text-orange-600 hover:text-blue-800'
                                                    : 'text-green-600 hover:text-green-800'
                                                }`}
                                        >
                                            {ingresso.statusIngresso}
                                        </button>
                                    </div>
                                    </td>
                                </tr>

                        
                        ))}

                        {ingressos.length === 0 && (
                            <tr>
                                <td colSpan={9} className="px-6 py-12 text-center text">
                                    Nenhum ingresso Encontrado
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
            

           </div > )
}