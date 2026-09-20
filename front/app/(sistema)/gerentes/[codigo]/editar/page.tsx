'use client'

import Link from "next/link";
import GerenteForm from "../../components/GerenteForm";
import { useParams } from "next/navigation";

export default function EditarGerente(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);
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
            <GerenteForm/>
        </div>
        </div>
        </div>
    )
}