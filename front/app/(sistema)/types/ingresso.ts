export class Ingresso{
    constructor(
        public id: number | null,
        public sessao: string,
        public filme: string,
        public assento: string,
        public valorPago:string,
        public status: string,
        public statusIngresso: string,
        public dataCompra: string,


    ){}
}

    export interface IngressoFormProps{
        ingressoExistente?:Ingresso
    }
