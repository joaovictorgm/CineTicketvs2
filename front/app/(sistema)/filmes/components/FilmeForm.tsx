import Link from "next/link";

export default function FilmeForm() {
    return (
        <form className="bg-white rounded-lg border border-blue-100">
            <div className="grid grid-cols-2 gap-x-6 gap-y-4 p-6">
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                     Titulo
                    </label>
                    <input name="Titulo" className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                     Duração
                    </label>
                    <input name="Duração" className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                      Classificação Indicatica
                    </label>
                    <input name="Classificao" className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-medium text-blue-700 uppercase tracking-wide">
                     Data de estreia
                    </label>
                    <input name = "DataEstreia"className="border border-blue-200 rounded px-3 py-2 text-sm text-blue-900 focus:outline-none focus:ring-1 focus:ring-blue-400">
                    </input>
                </div>
                
            </div>
            <div className="flex justify-end gap-3 border-t border-blue-100 px-6 py-4">
                <Link href="/filmes" className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50 transition-colors">Cancelar</Link>
                <button type="submit" className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700 transition-colors">Salvar</button>
            </div>
        </form>

    );
}