// npx create-next-app@latest front --typescript --tailwind --eslint
'use client'


import Link from "next/link"; 
import { Usuario } from "../types/usuario";
import { useEffect, useState } from "react";
import { atualizarStatusUsuario, buscarListaUsuarios, deletarUsuario } from "@/app/services/usuarioService";

export default function Usuarios(){

    const [usuarios,setUsuario] = useState<Usuario[]>([]);

    useEffect(() =>{
        carregarDados();
    },[]);

    const carregarDados = async ()=> {
        try {
        const dados =  await buscarListaUsuarios();
       
        setUsuario( dados);

    } catch (error) {
        alert("Erro ao carregar dados")
    }
    }

    const handlerDeletarUsuario = async(usuario:Usuario) =>{
        var dadosRetorno = await deletarUsuario(usuario.id || 0);

        if(dadosRetorno == 200){
            alert("Excluido com sucesso!");
        } else {
            alert("Erro ao excluir");
            return;
        }

        carregarDados();
    }

    const handleAlterarStatusUsuario = async(usuario:Usuario) =>{

        await atualizarStatusUsuario(usuario);

        carregarDados();

    }

    

    return (
    <div className=" bg-blue-50 p-8">
        <div className="flex items-center justify-between mb-6">
            <h1 className="text-2xl font-bold text-blue-900">
               Gestao de usuarios
            </h1>
            <Link href="/usuarios/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">Novo Usuario</Link>
        </div>
        <div>
            <div className="bg-white rounded-xl shadow-md overflow-hidden">
                <table className="w-full text-left">
                    <thead className="bg-blue-600 text-white">
                        <tr>
                            <th className="px-4 py-3 font-semibold">Codigo</th>
                        
                            <th className="px-4 py-3 font-semibold">Nome</th>

                            <th className="px-4 py-3 font-semibold">E-mail</th>

                        
                            <th className="px-4 py-3 font-semibold">Status</th>

                            <th className="px-4 py-3 font-semibold">Ações</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-blue-100">
                        {usuarios.map((usuario)=>(
                        <tr key={usuario.id} className="hover:bg-blue-50">
                            <td className="px-4 py-3 text-blue-900">
                               {usuario.id}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                               {usuario.nome}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
                                {usuario.email}
                            </td>
                           
                            <td className="px-4 py-3 text-blue-900">
                                {usuario.status}
                            </td>
                            <td className="px-4 py-3 text-blue-900">
    <div className="flex flex-col gap-1 items-start">
   <Link href={`/usuarios/${usuario.id}/editar`} className="text-blue-600 hover:underline">
            EDITAR
        </Link>
        <button onClick={() => handlerDeletarUsuario(usuario)} className="text-red-600 hover:text-red-800 font-medium transition-colors text-left">
            DELETAR
        </button>
        <button
            onClick={() => atualizarStatusUsuario(usuario)}
            className={`font-medium transition-colors text-left ${
                usuario.status === 'BLOQUEADO'
                    ? 'text-orange-600 hover:text-orange-800'
                    : 'text-green-600 hover:text-green-800'
            }`}
        >
            {usuario.status}
        </button>
    </div>
</td>
                        </tr>
                        ))}

                        { usuarios.length ===0 &&(
                            <tr>
                                <td colSpan={4} className="px-6 py-12 text-center text">
                                    Nenhum usuario Encontrado
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    
    
    
   </div> )
}