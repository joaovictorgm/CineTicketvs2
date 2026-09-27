'use client'


import Link from "next/link";
import { Sessao, SessaoFormProps } from "../../types/sessao";
import { useRouter } from "next/navigation";
import { useState } from "react";
import axios from "axios";

export default function SessaoForm({ sessaoExistente }: SessaoFormProps) {

    const router = useRouter();

    const [sessao, setSessao] = useState<Sessao>(sessaoExistente || new Sessao(null, "", "", "", "EXIBIÇÃO_2D", "ATIVO", "", 0))


    const handlerChange = (campo: 'filme' | 'data' | 'sala' | 'status' | 'statusSessao' | 'preco' | 'assentosDisponiveis', valor: string) => {
        setSessao(valorAnterior =>
            new Sessao(
                valorAnterior.id,
                campo === 'filme' ? valor : valorAnterior.filme,
                campo === 'data' ? valor : valorAnterior.data,
                campo === 'sala' ? valor : valorAnterior.sala,
                valorAnterior.status,
                valorAnterior.statusSessao,
                campo === 'preco' ? valor : valorAnterior.preco,
                campo === 'assentosDisponiveis' ? Number(valor) : valorAnterior.assentosDisponiveis,

            )
        )
    }

    const handlerSalvar = async (formData: FormData) => {

        if (sessaoExistente) {
            var dadosRetorno = await axios.put<number>('http://localhost:8080/sessoes/' + sessao.id, sessao)

            if (dadosRetorno.status == 200) {
                alert("Sessão foi salva com sucesso!");
            } else {
                alert(dadosRetorno.data);
                return;
            }


        } else {


            var dadosRetorno = await axios.post<number>('http://localhost:8080/sessoes', sessao)

            if (dadosRetorno.status == 200) {
                alert("Sessao foi salva com sucesso!");
            } else {
                alert(dadosRetorno.data);

                return;
            }

            router.push("/sessoes")

        }

    }

    return (
        <form action={handlerSalvar} className="bg-white rounded-lg border border-blue-100">
            <div className="grid grid-cols-2 gap-x-6 gap-y-4 p-6">
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Filme
                    </label>
                    <input name="Filme" value={sessao.filme} onChange={(e) => handlerChange('filme', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Data
                    </label>
                    <input name="Data" value={sessao.data} onChange={(e) => handlerChange('data', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Sala
                    </label>
                    <input name="Sala" value={sessao.sala} onChange={(e) => handlerChange('sala', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Preço
                    </label>
                    <input name="preco" value={sessao.preco}  onChange={(e)=> handlerChange('preco',e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Assentos disponiveis
                    </label>
                    <input name="assdisponiveis" value={sessao.assentosDisponiveis}  onChange={(e)=> handlerChange('assentosDisponiveis',e.target.value)}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
            </div>
            <div className="flex justify-end gap-3 border-t border-blue-100 px-6 py-4">
                <Link href="/sessoes" className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50 transition-colors">Cancelar</Link>
                <button type="submit" className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700 transition-colors">Salvar</button>
            </div>
        </form>

    );
}