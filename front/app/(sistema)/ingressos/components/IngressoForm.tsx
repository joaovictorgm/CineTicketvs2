'use client'

import Link from "next/link";
import { Ingresso, IngressoFormProps } from "../../types/ingresso";
import { useRouter } from "next/navigation";
import { useState } from "react";
import axios from "axios";


export default function IngressoForm({ ingressoExistente }: IngressoFormProps) {

    const router = useRouter();

    const [ingresso, setIngresso] = useState<Ingresso>(ingressoExistente || new Ingresso(null, "", "", "", "", "ATIVO", "ATIVO", ""))

    const handlerChange = (campo: 'sessao' | 'filme' | 'assento' | 'valorPago' | 'status' | 'statusIngresso' | 'dataCompra', valor: string) => {

        setIngresso(valorAnterior =>
            new Ingresso(
                valorAnterior.id,
                campo === 'sessao' ? valor : valorAnterior.sessao,
                campo === 'filme' ? valor : valorAnterior.filme,
                campo === 'assento' ? valor : valorAnterior.assento,
                campo === 'valorPago' ? valor : valorAnterior.valorPago,
                valorAnterior.status,
                valorAnterior.statusIngresso,
                campo=== 'dataCompra' ? valor : valorAnterior.dataCompra,
            )
        )
    }

    const handlerSalvar = async (formData: FormData) =>{

        if(ingressoExistente){
            var dadosRetorno = await axios.put<number>('http://localhost:8080/ingressos'+ingresso.id,ingresso)

            if(dadosRetorno.status ==200){
                alert("Ingresso foi salvo como sucesso!")
            } else {
                alert(dadosRetorno.data);
                return;
            }
        } else{
            var dadosRetorno = await axios.post<number>('http://localhost:8080/ingressos' ,ingresso)
        
        
            if(dadosRetorno.status == 200){
                alert("Ingresso foi salvo com sucesso!")
            } else {
                alert(dadosRetorno.data);

                return;
            }

            router.push("/ingressos")
        
        }
    }

    return (
        <form action={handlerSalvar} className="bg-white rounded-lg border border-blue-100">
            <div className="grid grid-cols-2 gap-x-6 gap-y-4 p-6">
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Sessao
                    </label>
                    <input name="Sessao" value={ingresso.sessao}placeholder="Informe a sessão" onChange={(e)=> handlerChange('sessao',e.target.value)}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Filme
                    </label>
                    <input name="filme" value={ingresso.filme} placeholder="Informe o filme" onChange={(e)=>handlerChange('filme',e.target.value)}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Assento
                    </label>
                    <input name="assento" value={ingresso.assento} placeholder="Informe o assento" onChange={(e)=>handlerChange('assento',e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Valor Pago
                    </label>
                    <input name="valorPago" value={ingresso.valorPago} placeholder="Informe o valor pago"  onChange={(e)=>handlerChange('valorPago',e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
                </div>
                
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                        Data Compra
                    </label>
                    <input name="dtcompra" value={ingresso.dataCompra} placeholder="/**/**/****/"  onChange={(e)=>handlerChange('dataCompra',e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"></input>
                </div>
            </div>
            <div className="flex justify-end gap-3 border-t border-blue-100 px-6 py-4">
                <Link href="/ingressos" className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50 transition-colors">Cancelar</Link>
                <button type="submit" className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700 transition-colors">Salvar</button>
            </div>
        </form>
    );
}