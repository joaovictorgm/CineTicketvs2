'use client'

import Link from "next/link";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "@/app/redux/store";
import { Sessao } from "../types/sessao";
import { atualizarStatusSessao, atualizarTipoSessao, buscarListaSessao, deletarSessao } from "@/app/services/sessaoService";
import { mensagemErro } from "@/app/services/erroApi";

export default function Sessoes() {
    const usuario = useSelector((state: RootState) => state.auth.usuario);
    const Gerente = usuario?.role === "ROLE_GERENTE";
    const Admin = usuario?.role === "ROLE_ADMIN";

    const [sessoes, setSessao] = useState<Sessao[]>([]);

    useEffect(() => {
        carregarDados();
    }, [])

    const carregarDados = async () => {
        try {
            const dados = await buscarListaSessao();
            setSessao(dados);
        } catch (erros) {
            alert(mensagemErro(erros))
        }
    }

    const handlerDeletarSessao = async (sessao: Sessao) => {
        try {
            var dadosRetorno = await deletarSessao(sessao.id || 0);

            if (dadosRetorno == 200) {
                alert("Excluido com sucesso!")
            } else {
                alert("Erro ao excluir");
                return;
            }

            carregarDados();
        } catch (erros) {
            alert(mensagemErro(erros));
        }
    }

    const handlerAlterarStatusSessao = async (sessao: Sessao) => {
        try {
            await atualizarStatusSessao(sessao);

            carregarDados();
        } catch (erros) {
            alert(mensagemErro(erros));
        }
    }

    const handlerAlterarTipoSessao = async (sessao: Sessao) => {
        try {
            await atualizarTipoSessao(sessao);

            carregarDados();
        } catch (erros) {
            alert(mensagemErro(erros));
        }
    }

    return (
        <div className="bg-blue-50 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestão de Sessões
                </h1>
                {(Gerente || Admin) && (
                    <Link href="/sessoes/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
                        Cadastrar nova Sessão
                    </Link>
                )}
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
                                {(Gerente || Admin) && (
                                    <th className="px-4 py-3 font-semibold">Ações</th>
                                )}
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-100">
                            {sessoes.map((sessao) => (
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
                                    {(Gerente || Admin) && (
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
                                                    className={`font-medium transition-colors text-left ${sessao.statusSessao === 'DESATIVADO'
                                                        ? 'text-orange-600 hover:text-orange-800'
                                                        : 'text-green-600 hover:text-green-800'
                                                        }`}
                                                >
                                                    {sessao.statusSessao}
                                                </button>
                                                <button
                                                    onClick={() => handlerAlterarTipoSessao(sessao)}
                                                    className={`font-medium transition-colors text-left ${sessao.status === 'EXIBICAO_2D'
                                                        ? 'text-orange-600 hover:text-blue-800'
                                                        : 'text-green-600 hover:text-green-800'
                                                        }`}
                                                >
                                                    {sessao.status}
                                                </button>
                                            </div>
                                        </td>
                                    )}
                                </tr>
                            ))}
                            {sessoes.length === 0 && (
                                <tr>
                                    <td colSpan={(Gerente || Admin) ? 9 : 8} className="px-6 py-12 text-center text">
                                        Nenhuma sessao Encontrada
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