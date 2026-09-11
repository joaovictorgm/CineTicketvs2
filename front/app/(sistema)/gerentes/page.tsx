import Link from "@/node_modules/next/link";


export default function Gerentes() {

    return (
        <div className="min-h-screen bg-blue-50 p-8">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold text-blue-900">
                    Gestao de gerentes
                </h1>
                <Link href="/gerentes/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"></Link>
            </div>

            <div>
                <div className="bg-white rounded-xl shadow-md overflow-hidden">
                    <table className="w-full text-left">
                        <thead className="bg-blue-600 text-white">
                            <tr>
                                <th className="px-4 py-3 font-semibold">Codigo</th>
                                <th className="px-4 py-3 font-semibold">Nome</th>
                                <th className="px-4 py-3 font-semibold">Email</th>
                                <th className="px-4 py-3 font-semibold">Senha</th>
                                <th className="px-4 py-3 font-semibold">Status</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-blue-100">
                        {gerentes.map((gerentes)=>(
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
                        </tbody>
                    </table>
                </div>
            </div>
            

           </div> )
}