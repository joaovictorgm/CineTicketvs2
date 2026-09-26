'use client'

import Link from "next/link";
import { Gerente, GerenteFormProps } from "../../types/gerente";
import { useRouter } from "next/navigation";
import { useState } from "react";
import axios from "axios";

export default function GerenteForm({gerenteExistente}:GerenteFormProps) {
    const router = useRouter();

    const [gerente,setGerente] = useState<Gerente>(gerenteExistente || new Gerente(null, "","","","ATIVO"));

    const handlerChange = (campo: 'nome' | 'email' | 'senha', valor:string) =>{
        setGerente(valorAnterior =>
            new Gerente(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'email' ? valor : valorAnterior.email,
                campo === 'senha' ? valor : valorAnterior.senha,
                valorAnterior.status,
            )

            )
    }

    const handlerSalvar = async (formData: FormData) =>{
        if(gerenteExistente){
            var dadosRetorno = await axios.put<number>('http://localhost:8080/gerentes'+gerente.id,gerente)
        if(dadosRetorno.status == 200){
            alert("Gerente foi salvo com sucesso!")
        } else {
            alert(dadosRetorno.data);
            return;
        }

        }else{

            var dadosRetorno = await axios.post<number>('http://localhost:8080/gerentes' ,gerente)
        
            if(dadosRetorno.status == 200){
                alert("Gerente foi salvo com sucesso!")
            } else {
                alert(dadosRetorno.data);

                return;
            }

            router.push("/gerentes")
        }
    }
    return (
        <form action={handlerSalvar}className="bg-white rounded-lg border border-blue-100">
            <div className="grid grid-cols-2 gap-x-6 gap-y-4 p-6">
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Nome Completo
                    </label>
                    <input name="Nome" value={gerente.nome} placeholder="Digite o seu nome" onChange={(e)=>handlerChange('nome',e.target.value)}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                     Email
                    </label>
                    <input name="Email" value={gerente.email} placeholder="email@email.com" onChange={(e)=>handlerChange('email',e.target.value)}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                      Senha
                    </label>
                    <input name="Senha" value={gerente.senha} placeholder="*******"  onChange={(e)=>handlerChange('senha',e.target.value)}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Status
                    </label>
                    <input name="status" value={gerente.status}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                
            </div>
            <div className="flex justify-end gap-3 border-t border-blue-100 px-6 py-4">
                <Link href="/gerentes" className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50 transition-colors">Cancelar</Link>
                <button type="submit" className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700 transition-colors">Salvar</button>
            </div>
        </form>

    );
}