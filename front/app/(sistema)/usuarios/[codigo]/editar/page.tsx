'use client'

import Link from "next/link";
import UsuarioForm from "../../components/UsuarioForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Usuario } from "@/app/(sistema)/types/usuario";
import axios from "axios";

export default function EditarUsuario(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [usuario, setUsuario] = useState<Usuario | null>(null)
    const router = useRouter();

    useEffect(()=>{

        buscarDados();

    },[]);

    const buscarDados = async() =>{
    
     const valorUsuarioBack = await axios.get<Usuario>('http://localhost:8080/usuarios/'+codigo)

     if(valorUsuarioBack.status==200){
        setUsuario(valorUsuarioBack.data);
     }else{
        router.push("/usuarios")
        
     }

     

    }

    if(!usuario) return(<div className="p-1">Carregando Dados</div>)
    return(
        <div>
          <div>
            <div>
                <Link href="/usuarios" className="text-sm text-blue-600 hover:underline">
                    ← Voltar para listagem
                </Link>
            <div className="flex items-center justify-between mt-3 mb-6 border-b border-blue-100 pb-4">
            <h1 className="text-xl font-semibold text-blue-900">
                <span>Editar Usuário {codigo}</span>
            </h1>
            <p className="text-sm text-blue-500">Prencha os dados para registrar  editar o  usuário</p>
          </div>

        </div>
        <div>
            <UsuarioForm usuarioExistente={usuario}/>
        </div>
        </div>
        </div>
    )
}