'use client'

import Link from "next/link";
import FilmeForm from "../../components/FilmeForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Filme } from "@/app/(sistema)/types/filmes";
import axios from "axios";

export default function EditarFilme() {

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [filme, setFilme] = useState<Filme | null>(null)
    const router = useRouter();

    useEffect(() => {
        buscarDados();

    }, []);

    const buscarDados = async () => {
        const valorFilmeBack = await axios.get<Filme>('http://localhost:8080/filmes/' + codigo)

        if (valorFilmeBack.status == 200) {
            setFilme(valorFilmeBack.data);
        } else {
            router.push("/filmes")
        }

    }

    if (!filme) return (<div className="p-3">Carregando Dados</div>)
    return (
        <div>
            <div>
                <div>
                    <Link href="/filmes" className="text-sm text-blue-600 hover:underline">
                        ← Voltar para listagem
                    </Link>
                    <div className="flex items-center justify-between mt-3 mb-6 border-b border-blue-100 pb-4">
                        <h1 className="text-xl font-semibold text-blue-900">
                            <span>Editar Filme {codigo}</span>
                        </h1>
                        <p className="text-sm text-blue-500">Prencha os dados para editar o filme</p>
                    </div>

                </div>
                <div>
                    <FilmeForm filmeExistente={filme} />
                </div>
            </div>
        </div>
    )
}