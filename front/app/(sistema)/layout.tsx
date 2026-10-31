import { useSelector } from "react-redux";
import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import { RootState } from "../redux/store";


export default function SistemaLayout({children}){

    const usuario = useSelector((state:RootState)=> state.auth.usuario)
    return (
    <div className="flex min-h-screen bg-blue-50">
         <Sidebar/>
        <div className="flex-1 flex flex-col">
        <Header/>

        <main className="flex-1 p-8">
            {children}
        </main>
        <Footer/>
        </div>
    </div>);
}