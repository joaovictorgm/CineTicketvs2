'use client'

import Link from "next/link";
import GerenteForm from "../../components/GerenteForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Gerente } from "@/app/(sistema)/types/gerente";
import axios from "axios";

export default function EditarGerente(){

    const parametro = useParams();


    const codigo = Number(parametro.codigo);
    const [gerente, setGerente] = useState<Gerente | null>(null)
    const router = useRouter();

    useEffect(()=>{
        buscarDados();
    },[]);

    const buscarDados = async()=>{
        const valorGerenteBack = await axios.get<Gerente>('http://localhost:8080/gerentes/'+codigo)

        if(valorGerenteBack.status===200){
            setGerente(valorGerenteBack.data);
        }else{
            router.push("/gerentes")
        }
    } 
    if(!gerente) return(<div className="p-2">Carregando Dados</div>)
    return(
        <div>
          <div>
            <div>
                <Link href="/gerentes" className="text-sm text-blue-600 hover:underline">
                    ← Voltar para listagem
                </Link>
            <div className="flex items-center justify-between mt-3 mb-6 border-b border-blue-100 pb-4">
            <h1 className="text-xl font-semibold text-blue-900">
                <span>Editar Gerente {codigo}</span>
            </h1>
            <p className="text-sm text-blue-500">Prencha os dados para  editar um  gerente</p>
          </div>

        </div>
        <div>
            <GerenteForm gerenteExistente={gerente}/>
        </div>
        </div>
        </div>
    )
}