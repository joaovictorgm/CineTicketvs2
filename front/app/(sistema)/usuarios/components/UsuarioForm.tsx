'use client'

import Link from "next/link";
import { useState } from "react";
import { Usuario, UsuarioFormProps } from "../../types/usuario";
import axios from "axios";
import { useRouter } from "next/navigation";

export default function UsuarioForm({usuarioExistente}:UsuarioFormProps) {

    const router = useRouter();
 
    // 1 usuario, 2 como chama ele, como se fosse get e set
    const [usuario, setUsuario] = useState<Usuario>(usuarioExistente || new Usuario(null, "","","","","ATIVO"));

    const handlerChange = ( campo: 'nome'| 'telefone' | 'email'| 'cpf'| 'senha', valor:string) =>{
        setUsuario(valorAnterior =>
            new Usuario(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'telefone' ? valor : valorAnterior.telefone,
                campo === 'email' ? valor : valorAnterior.email,
                campo === 'senha' ? valor : valorAnterior.senha,
                valorAnterior.status,
               

            )

        )
    }

    const handlerSalvar = async (formData: FormData) =>{

        if(usuarioExistente){
            var dadosRetorno = await axios.put<number>('http://localhost:8080/usuarios'+usuario.id,usuario)

        if(dadosRetorno.status == 200){
            alert("Usuário foi salvo com sucesso!");
        } else {
            alert(dadosRetorno.data);
            return;
        }


        }else{

      
        var dadosRetorno = await axios.post<number>('http://localhost:8080/usuarios' ,usuario)

        if(dadosRetorno.status == 200){
            alert("Usuário foi salvo com sucesso!");
        } else {
            alert(dadosRetorno.data);

            return;
        }

        router.push("/usuarios")

    }

    }
    return (
        <form action={handlerSalvar} className="bg-white rounded-lg border border-blue-100">
            <div className="grid grid-cols-2 gap-x-6 gap-y-4 p-6">
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                    Nome Completo
                    </label>
                    <input name="Nome"  value={usuario.nome}placeholder="Digite o seu nome" onChange={(e)=> handlerChange('nome',e.target.value)}className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                     Telefone
                    </label>
                    <input name="Telefone"  value={usuario.telefone} placeholder="(00)0000-0000" onChange={(e)=> handlerChange('telefone',e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                      Email
                    </label>
                    <input name="Email" value={usuario.email} placeholder="email@email.com" onChange={(e)=> handlerChange('email',e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400"> 
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                      Senha
                    </label>
                    <input  name="Senha" value={usuario.senha} placeholder="******" onChange={(e)=> handlerChange('senha',e.target.value)} className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                
                </div>
            </div>
            <div className="flex justify-end gap-3 border-t border-blue-100 px-6 py-4">
                <Link href="/usuarios" className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50 transition-colors">Cancelar</Link>
                <button type="submit" className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700 transition-colors">Salvar</button>
            </div>
        </form>

    );
}