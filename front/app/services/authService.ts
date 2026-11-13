import { LoginResponse } from "../(sistema)/types/auth";
import api from "./api";

export async function loginBack(email:string,senha) : Promise<LoginResponse>{
    var loginResposta = await api.post<LoginResponse>(
        "http://localhost:8080/auth/login",
        { email: email, senha: senha });
        if(loginResposta.status!=200){
            alert("Login ou senha invalido")
        }
    

    return loginResposta.data;

}