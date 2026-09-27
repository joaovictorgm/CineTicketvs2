'use client'

import Link from "next/link";
import IngressoForm from "../../components/IngressoForm";
import { useParams, useRouter } from "next/navigation";
import axios from "axios";
import { useEffect, useState } from "react";
import { Ingresso } from "@/app/(sistema)/types/ingresso";

export default function EditarIngresso(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [ingresso,setIngresso] = useState<Ingresso |null>(null)

    const router = useRouter();

    useEffect(()=>{
        buscarDados();
    },[])

    const buscarDados = async() =>{
        const valorIngressoBack = await axios.get<Ingresso>('http://localhost:8080/ingressos/'+codigo)
    
        if(valorIngressoBack.status==200){
            setIngresso(valorIngressoBack.data);
        }else{
            router.push("/ingressos")
        }
    
    
    }

    if(!ingresso) return(<div className="p-2">Carregando Dados</div>)



    return(
        <div>
          <div>
            <div>
                <Link href="/ingressos" className="text-sm text-blue-600 hover:underline">
                    ← Voltar para listagem
                </Link>
            <div className="flex items-center justify-between mt-3 mb-6 border-b border-blue-100 pb-4">
            <h1 className="text-xl font-semibold text-blue-900">
                <span>Editar Ingresso {codigo}</span>
            </h1>
            <p className="text-sm text-blue-500">Prencha os dados para um editar usuário</p>
          </div>

        </div>
        <div>
            <IngressoForm ingressoExistente={ingresso}/>
        </div>
        </div>
        </div>
    )
}