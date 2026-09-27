export class Filme{
    constructor(
        public id :number | null,
        public titulo: string,
        public duracaoMinutos: string,
        public classificacaoEtaria:string,
        public dataEstreia: string



    ){}

   
}

export interface FilmeFormProps{
    filmeExistente?:Filme
}