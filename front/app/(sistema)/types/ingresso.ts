export class Ingresso{
    constructor(
        public id: number | null,
        public sessao: String,
        public filme: String,
        public assento: number,
        public valorPago:number,
        public status: string,
        public statusIngresso: string,
        public dataCompra: string,


    ){}
}