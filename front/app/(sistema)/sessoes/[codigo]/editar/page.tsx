'use client'

import Link from "next/link";
import SessaoForm from "../../components/SessaoForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import axios from "axios";
import { Sessao } from "@/app/(sistema)/types/sessao";

export default function EditarSessao() {

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [sessao, setSessao] = useState<Sessao | null>(null)

    const router = useRouter();

    useEffect(() => {
        buscarDados();
    }, []);

    const buscarDados = async () => {

        const valorSessaoBack = await axios.get<Sessao>('http://localhost:8080/sessoes/' + codigo)

        if (valorSessaoBack.status == 200) {
            setSessao(valorSessaoBack.data);
        } else {
            router.push("/sessoes")
        }

    }

    if (!sessao) return (<div className="p-5">Carregando Dados</div>)
    return (
        <div>
            <div>
                <div>
                    <Link href="/sessoes" className="text-sm text-blue-600 hover:underline">
                        ← Voltar para listagem
                    </Link>
                    <div className="flex items-center justify-between mt-3 mb-6 border-b border-blue-100 pb-4">
                        <h1 className="text-xl font-semibold text-blue-900">
                            <span>Editar Sessão {codigo}</span>
                        </h1>
                        <p className="text-sm text-blue-500">Prencha os dados para registrar uma sessão</p>
                    </div>

                </div>
                <div>
                    <SessaoForm sessaoExistente={sessao} />
                </div>
            </div>
        </div>
    )
}