'use client'

import Link from "next/link";
import { Ingresso, IngressoFormProps } from "../../types/ingresso";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { atualizarIngresso, salvarIngresso } from "@/app/services/ingressoService";
import { buscarListaSessao } from "@/app/services/sessaoService";
import { mensagemErro } from "@/app/services/erroApi";


export default function IngressoForm({ ingressoExistente }: IngressoFormProps) {

const router = useRouter();

const [ingresso, setIngresso] = useState<Ingresso>(ingressoExistente || new Ingresso(null, "", "", "", "", "INTEIRA", "DISPONIVEL", "", null, null))

const [sessoes, setSessoes] = useState<Awaited<ReturnType<typeof buscarListaSessao>>>([]);

useEffect(() => {
    buscarListaSessao().then(setSessoes).catch((erro) => alert(mensagemErro(erro)));
}, []);

const handlerChange = (campo: 'sessaoId' | 'usuarioId' | 'assento' | 'valorPago' | 'statusTipo' | 'dataCompra', valor: string) => {

    setIngresso(valorAnterior =>
        new Ingresso(
            valorAnterior.id,
            valorAnterior.sessao,
            valorAnterior.filme,
            campo === 'assento' ? valor : valorAnterior.assento,
            campo === 'valorPago' ? valor : valorAnterior.valorPago,
            campo === 'statusTipo' ? valor : valorAnterior.statusTipo,
            valorAnterior.statusIngresso,
            campo === 'dataCompra' ? valor : valorAnterior.dataCompra,
            campo === 'sessaoId' ? Number(valor) : valorAnterior.sessaoId,
            campo === 'usuarioId' ? Number(valor) : valorAnterior.usuarioId,
        )
    )
}

const handlerSalvar = async (formData: FormData) => {
    try {
        const erro = ingressoExistente
            ? await atualizarIngresso(ingresso)
            : await salvarIngresso(ingresso);

        if (erro) {
            alert(erro);
            return;
        }

        alert("Ingresso foi salvo com sucesso!")
        router.push("/ingressos")
    } catch (erro) {
        alert(mensagemErro(erro));
    }
}

return (
    <form action={handlerSalvar} className="bg-white rounded-lg border border-blue-100">
        <div className="grid grid-cols-2 gap-x-6 gap-y-4 p-6">
            <div className="flex flex-col gap-1">
                <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Sessao
                </label>
                <select
                    name="Sessao"
                    value={ingresso.sessaoId ?? (ingresso.sessao ? Number(ingresso.sessao) : "")}
                    disabled={!!ingressoExistente}
                    onChange={(e) => handlerChange('sessaoId', e.target.value)}
                    className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"
                >
                    <option value="">Selecione a sessão</option>
                    {sessoes.map((s) => (
                        <option key={s.id} value={s.id ?? ""}>
                            {s.id} - {s.filme} - {s.data} - Sala {s.sala}
                        </option>
                    ))}
                </select>
            </div>
            <div className="flex flex-col gap-1">
                <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Código do cliente
                </label>
                <input name="usuarioId" value={ingresso.usuarioId ?? ""} placeholder="Informe o código do cliente" disabled={!!ingressoExistente} onChange={(e) => handlerChange('usuarioId', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
            </div>
            <div className="flex flex-col gap-1">
                <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Assento
                </label>
                <input name="assento" value={ingresso.assento} placeholder="Informe o assento" onChange={(e) => handlerChange('assento', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
            </div>
            <div className="flex flex-col gap-1">
                <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Valor Pago
                </label>
                <input name="valorPago" value={ingresso.valorPago} placeholder="Informe o valor pago" onChange={(e) => handlerChange('valorPago', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
            </div>
            <div className="flex flex-col gap-1">
                <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Tipo de Ingresso
                </label>
                <select
                    name="statusTipo"
                    value={ingresso.statusTipo}
                    onChange={(e) => handlerChange('statusTipo', e.target.value)}
                    className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"
                >
                    <option value="INTEIRA">Inteira</option>
                    <option value="MEIA">Meia</option>
                </select>
            </div>

            <div className="flex flex-col gap-1">
                <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Data Compra
                </label>
                <input name="dtcompra" value={ingresso.dataCompra} placeholder="/**/**/****/" onChange={(e) => handlerChange('dataCompra', e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
            </div>
        </div>
        <div className="flex justify-end gap-3 border-t border-blue-100 px-6 py-4">
            <Link href="/ingressos" className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50 transition-colors">Cancelar</Link>
            <button type="submit" className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700 transition-colors">Salvar</button>
        </div>
    </form>
);
}