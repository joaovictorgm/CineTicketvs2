import Link from "next/link";
import FilmeForm from "../components/FilmeForm";

export default function CadastroFilme(){

    return(
        <div className="min-h-screen bg-blue-100 p-8">
          <div className="max-w-4xl mx-auto">
            <div>
                <Link href="/filmes" className="text-sm text-blue-700 hover:underline">
                    ← Voltar para listagem
                </Link>
            <div className="flex items-center justify-between mt-3 mb-6 border-b border-blue-200 pb-4">
            <h1 className="text-xl font-semibold text-blue-900">
                Novo Filme
            </h1>
            <p className="text-sm text-blue-600">Prencha os dados para registrar um novo filme</p>
          </div>

        </div>
        <div>
            <FilmeForm/>
        </div>
        </div>
        </div>
    )
}