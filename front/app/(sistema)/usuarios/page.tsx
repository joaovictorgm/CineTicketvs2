// npx create-next-app@latest front --typescript --tailwind --eslint
'use client'

import axios from "axios";
import Link from "next/link"; 
import { Usuario } from "../types/usuario";
import { useEffect, useState } from "react";

export default function Usuarios(){

    const [usuarios,setUsuario] = useState<Usuario[]>([]);

    useEffect(() =>{
        carregarDados();
    },[])

    const carregarDados = async ()=> {
        try {
        const dados =  axios.get<Usuario[]>("http://localhost:8080/usuarios");
        console.log("Resposta da API:", (await dados).data);
        setUsuario((await dados).data);

    } catch (error) {
        alert("Erro ao carregar dados")
    }
    }

    return (
    <div className="min-h-screen bg-blue-50 p-8">
        <div className="flex items-center justify-between mb-6">
            <h1 className="text-2xl font-bold text-blue-900">
               Gestao de usuarios
            </h1>
            <Link href="/usuarios/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"></Link>
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
                        </tr>
                        ))}

                        { usuarios.length ===0 &&(
                            <tr>
                                <td colSpan={5} className="px-6 py-12 text-center text">
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