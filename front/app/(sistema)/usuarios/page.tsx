// npx create-next-app@latest front --typescript --tailwind --eslint

import Link from "@/node_modules/next/link"; 

export default function Usuarios(){

    return (
    <div className="min-h-screen bg-blue-50 p-8">
        <div className="flex items-center justify-between mb-6">
            <h1 className="text-2xl font-bold text-blue-900">
               Gestao de usuarios
            </h1>
            <Link href="/usuarios/novo" className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"></Link>
        </div>
        <div>
            <div className="bg-white rounded-xl shadow-md overflow-hidden">
                <table className="w-full text-left">
                    <thead className="bg-blue-600 text-white">
                        <tr>
                            <th className="px-4 py-3 font-semibold">Nome</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-blue-100">
                        <tr className="hover:bg-blue-50">
                            <td className="px-4 py-3 text-blue-900">
                                João Victor
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    
    
    
   </div> )
}