

export default function Header(){

    return(
    <header className="bg-white shadow-md border-b border-blue-100">
    <div className="flex items-center justify-between px-8 py-4">
        <div className="flex items-center gap-2">
            <div className="bg-blue-100 text-blue-600 rounded-full p-2">
                <svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5">
                    <path d="M19 21v"/>
                    <circle/>                  
                </svg>
            </div>
            <span className="text-blue-900 font-medium"> Usuário Teste</span>
        </div>
        <button className="bg-blue-600 text-white font-semibold px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">Sair</button>
    </div>
    </header>
    );
}