export class Sessao{
    constructor(
        public id: number | null,
        public filme: string,
        public data: string,
        public sala: string,
        public status: string,
        public statusSessao:string,
        public preco:string,
        public assentosDisponiveis: number,
    ){}
}

export interface SessaoFormProps{
    sessaoExistente?:Sessao
}