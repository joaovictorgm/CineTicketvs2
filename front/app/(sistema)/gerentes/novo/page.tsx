import Link from "next/link";
import GerenteForm from "../components/GerenteForm";

export default function CadastroGerente(){

    return(
        <div className="min-h-screen bg-blue-100 p-8">
          <div className="max-w-4xl mx-auto">
            <div>
                <Link href="/gerentes" className="text-sm text-blue-700 hover:underline">
                    ← Voltar para listagem
                </Link>
            <div className="flex items-center justify-between mt-3 mb-6 border-b border-blue-200 pb-4">
            <h1 className="text-xl font-semibold text-blue-900">
                Novo Gerente
            </h1>
            <p className="text-sm text-blue-600">Prencha os dados para registrar um novo gerente</p>
          </div>

        </div>
        <div>
            <GerenteForm/>
        </div>
        </div>
        </div>
    )
}