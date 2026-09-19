export default function Footer(){

    const anoAtual = new Date().getFullYear();
    return(

        
        <footer className="bg-white border-t border-blue-100 mt-auto">
            <div className="px-8 py-4">
                <div>
                    <p className="text-sm text-blue-800 text-center">&copy;{anoAtual} 
                    <span className="font-semibold"> CineTicket </span>
                    Todos os direitos reservados</p>
                </div>
            </div>
        </footer>
    )
}