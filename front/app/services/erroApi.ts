import axios from "axios";

export function mensagemErro(error: unknown): string {
if (axios.isAxiosError(error)) {
    if (!error.response) {
        return "Não foi possível conectar ao servidor.";
    }
    const status = error.response.status;
    const dados = error.response.data;

    // mensagem que o backend mandou (badRequest().body(e.getMessage()))
    if (typeof dados === "string" && dados.trim() !== "") {
        return dados;
    }
    if (status === 401) return "Sessão expirada. Faça login novamente.";
    if (status === 403) return "Você não tem permissão para esta ação.";
    if (status === 404) return "Registro não encontrado.";
    if (status === 400) return "Dados inválidos. Confira os campos.";
}

if (error instanceof Error) {
    return error.message;
}
return "Erro inesperado. Tente novamente.";
}