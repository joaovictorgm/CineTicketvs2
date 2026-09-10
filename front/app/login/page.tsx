'use client'

import { useRouter } from "@/node_modules/next/navigation";
import axios from "axios";
import { LoginResponse } from "../(sistema)/types/auth";

export default function Login() {
   
        const router = useRouter();

      

        const handleLogin = async (formData: FormData) => {

              const emailTela = formData.get("email")?.toString() ?? "";
              const senhaTela = formData.get("senha")?.toString() ?? "";



              var loginResposta = await axios.post<LoginResponse>("http://localhost:8080/auth/login",
              
              {email:emailTela, senha:senhaTela});
 
              if(loginResposta.status==200){
                router.push("/home")

              }
              alert("Login ou senha inválida!")
 
              router.push("/home")
    }


    return (
        <div className="min-h-screen flex items-center justify-center bg-blue-50">
            <div className="w-full max-w-sm bg-white rounded-xl shadow-md p-8 flex flex-col items-center gap-6">
                <div>
                    <h1 className="text-2xl font-bold text-blue-900 text-center">
                        Entrar no sistema
                    </h1>
                </div>
                <form action={handleLogin} className="w-full flex flex-col gap-4">
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">
                            E-Mail
                        </label>
                        <input
                            name="email"
                            className="border border-blue-200 rounded-lg px-3 py-2 text-blue-900 focus:outline-none focus:ring-2 focus:ring-blue-400">

                        </input>
                    </div>
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-blue-800">
                            Senha
                        </label>
                        <input
                            name="senha"
                            className="border border-blue-200 rounded-lg px-3 py-2 text-blue-900 focus:outline-none focus:ring-2 focus:ring-blue-400">

                        </input>
                    </div>
                    <button type="submit" className="mt-2 w-full bg-blue-600 text-white font-semibold py-2 rounded-lg hover:bg-blue-700 transition-colors">
                        Entrar
                    </button>
                </form>
            </div>
        </div>
    );
}