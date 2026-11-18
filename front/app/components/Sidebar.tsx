'use client'                                   
import Link from "next/link";
import { useSelector } from "react-redux";      
import { RootState } from "../redux/store";     

export default function Sidebar(){

    const usuario = useSelector((state: RootState) => state.auth.usuario);   // <- adicionar
    const ehGerente = usuario?.role === "ROLE_GERENTE";                       // <- adicionar
    const ehAdmin = usuario?.role === "ROLE_ADMIN";                           // <- adicionar

    return(<aside className="w-64 bg-blue-900 min-h-screen flex flex-col">
        <div className="text-white text-xl font-bold px-6 py-5 border-b border-blue-800">
            CineTicket
        </div>
        <nav className="flex flex-col gap-1 p-4">
            <Link href="/home" className="...">Home</Link>

            {(ehGerente || ehAdmin) && (                                      // <- envolver
                <Link href="/usuarios" className="...">Usuários</Link>
            )}

            <Link href="/ingressos" className="...">Ingressos</Link>
            <Link href="/sessoes" className="...">Sessões</Link>

            {ehGerente && (                                                   // <- envolver
                <Link href="/gerentes" className="...">Gerentes</Link>
            )}

            <Link href="/filmes" className="...">Filmes</Link>
        </nav>
    </aside>);
}