'use client'
import Link from "next/link";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";

export default function Sidebar(){

const usuario = useSelector((state: RootState) => state.auth.usuario);
const Gerente = usuario?.role === "ROLE_GERENTE";
const Admin = usuario?.role === "ROLE_ADMIN";

const totalCarrinho = useSelector((state: RootState) => state.carrinho.itens.length);

return(<aside className="w-64 bg-blue-900 min-h-screen flex flex-col">
    <div className="text-white text-xl font-bold px-6 py-5 border-b border-blue-800">
        CineTicket
    </div>
    <nav className="flex flex-col gap-1 p-4">
        <Link href="/home" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Home</Link>

        {(Gerente || Admin) && (
            <Link href="/usuarios" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Usuários</Link>
        )}

        <Link href="/ingressos" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Ingressos</Link>
        <Link href="/sessoes" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Sessões</Link>

        {Gerente && (
            <Link href="/gerentes" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Gerentes</Link>
        )}

        <Link href="/filmes" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Filmes</Link>

        <Link href="/carrinho" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors flex items-center justify-between">
            Carrinho
            {totalCarrinho > 0 && (
                <span className="bg-white text-blue-900 text-xs font-bold rounded-full px-2 py-0.5">
                    {totalCarrinho}
                </span>
            )}
        </Link>
    </nav>
</aside>);
}