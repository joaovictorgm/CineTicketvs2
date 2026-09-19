import Link from "next/link";

export default function Sidebar(){

    return(<aside className="w-64 bg-blue-900 min-h-screen flex flex-col">
        <div className="text-white text-xl font-bold px-6 py-5 border-b border-blue-800">
            CineTicket
        </div>
        <nav className="flex flex-col gap-1 p-4">
            <Link href="/home" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Home</Link>
            <Link href="/usuarios" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Usuários</Link>
            <Link href="/ingressos" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Ingressos</Link>
            <Link href="/sessoes" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Sessões</Link>
            <Link href="/gerentes" className="text-blue-100 px-4 py-2 rounded-lg hover:bg-blue-800 hover:text-white transition-colors">Gerentes</Link>
        </nav>
    </aside>);
}