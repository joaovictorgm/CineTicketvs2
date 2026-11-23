'use client'

import Link from "next/link";
import { useEffect, useState } from "react";
import { Ingresso } from "../types/ingresso";
import { useDispatch } from "react-redux";
import { adicionarItem } from "@/app/redux/slices/carrinhoSlice";
import { atualizarStatusIngresso, buscarListaIngressos, deletarIngresso } from "@/app/services/ingressoService";
import { mensagemErro } from "@/app/services/erroApi";


export default function Ingressos() {

const [ingressos, setIngresso] = useState<Ingresso[]>([]);
const dispatch = useDispatch();

useEffect(() => {
    carregarDados();
}, [])

const carregarDados = async () => {
    try {

        const dados = await buscarListaIngressos();

        setIngresso(dados);

    } catch (error) {
        alert(mensagemErro(error))
    }
}

const handlerDeletarIngresso = async (ingresso: Ingresso) => {
    try {
        var dadosRetorno = await deletarIngresso(ingresso.id || 0);

        if (dadosRetorno == 200) {
            alert("A venda foi realizda com sucesso!");
        } else {
            alert("Erro ao excluir");
            return;
        }

        carregarDados();
    } catch (error) {
        alert(mensagemErro(error));
    }


}

const handlerAlterarStatusIngresso = async (ingresso: Ingresso) => {
    try {
        await atualizarStatusIngresso(ingresso);

        carregarDados();
    } catch (error) {
        alert(mensagemErro(error));
    }

    }

const handlerAdicionarCarrinho = (ingresso: Ingresso) => {
    dispatch(adicionarItem({
        ingressoId: ingresso.id!,
        sessao: Number(ingresso.sessao),
        filme: Number(ingresso.filme),
        assento: ingresso.assento,
        valorPago: Number(ingresso.valorPago),
        statusTipo: ingresso.statusTipo,
    }));
}



return (
    <div className="bg-blue-50 p-8">
        <div className="flex items-center justify-between mb-6">
            <h1 className="text-2xl font-bold text-blue-900">
                Gestão de Ingressos
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
                                        {ingresso.statusIngresso === 'DISPONIVEL' && (
                                            <button onClick={() => handlerAdicionarCarrinho(ingresso)} className="text-blue-600 hover:text-blue-800 font-medium transition-colors text-left">
                                                ADICIONAR AO CARRINHO
                                            </button>
                                        )}
                                        
                                        <button
                                            onClick={() => handlerAlterarStatusIngresso(ingresso)}
                                            className={`font-medium transition-colors text-left ${ingresso.statusIngresso === 'PAGO'
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


    </div >)
}