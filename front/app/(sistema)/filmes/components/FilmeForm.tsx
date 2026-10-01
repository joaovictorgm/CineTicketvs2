'use client'

import Link from "next/link";
import { Filme, FilmeFormProps } from "../../types/filmes";
import { useRouter } from "next/navigation";
import { useState } from "react";
import axios from "axios";

export default function FilmeForm({ filmeExistente }: FilmeFormProps) {

    const router = useRouter();

    const [filme, setFilme] = useState<Filme>(filmeExistente || new Filme(null, "", "", "", "", "ATIVO"));

    const handlerChange = (campo: 'titulo' | 'duracaoMinutos' | 'classificacaoEtaria' | 'dataEstreia', valor: string) => {
        setFilme(valorAnterior =>
            new Filme(
                valorAnterior.id,
                campo === 'titulo' ? valor : valorAnterior.titulo,
                campo === 'duracaoMinutos' ? valor : valorAnterior.duracaoMinutos,
                campo === 'classificacaoEtaria' ? valor : valorAnterior.classificacaoEtaria,
                campo === 'dataEstreia' ? valor : valorAnterior.dataEstreia,
                valorAnterior.status
            )
        )
    }
    const handlerSalvar = async (formData: FormData) => {

        if (filmeExistente) {
            var dadosRetorno = await axios.put<number>('http://localhost:8080/filmes/' + filme.id, filme)

            if (dadosRetorno.status == 200) {
                alert("Filme foi salvo com sucesso!");
                 router.push("/filmes")
            } else {
                alert(dadosRetorno.data);
                return;
            }


        } else {


            var dadosRetorno = await axios.post<number>('http://localhost:8080/filmes', filme)

            if (dadosRetorno.status == 200) {
                alert("filme foi salvo com sucesso!");
                 router.push("/filmes")
            } else {
                alert(dadosRetorno.data);
                

                return;
            }

            router.push("/filmes")

        }

    }

    return (
        <form action={handlerSalvar} className="bg-white rounded-lg border border-blue-100">
            <div className="grid grid-cols-2 gap-x-6 gap-y-4 p-6">
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Titulo
                    </label>
                    <input name="Titulo" value={filme.titulo} placeholder="Informe o filme" onChange={(e) => handlerChange('titulo', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Duração
                    </label>
                    <input name="Duração" value={filme.duracaoMinutos} onChange={(e) => handlerChange('duracaoMinutos', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Classificação Etária
                    </label>
                    <input name="Classificao" value={filme.classificacaoEtaria} onChange={(e) => handlerChange('classificacaoEtaria', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Data de estreia
                    </label>
                    <input name="DataEstreia" value={filme.dataEstreia} onChange={(e) => handlerChange('dataEstreia', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>

            </div>
            <div className="flex justify-end gap-3 border-t border-blue-100 px-6 py-4">
                <Link href="/filmes" className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50 transition-colors">Cancelar</Link>
                <button type="submit" className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700 transition-colors">Salvar</button>
            </div>
        </form>

    );
}