'use client'
import { useSelector } from "react-redux";
import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import { RootState } from "../redux/store";
import Login from "../login/page"


export default function SistemaLayout({children}: { children: React.ReactNode}){



const usuario = useSelector((state: RootState) => state.auth.usuario)

if (!usuario) {
return <Login/>
}
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