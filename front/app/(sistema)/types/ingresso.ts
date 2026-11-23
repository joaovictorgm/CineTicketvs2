export class Ingresso{
static statusIngresso: string;
constructor(
    public id: number | null,
    public sessao: string,
    public filme: string,
    public assento: string,
    public valorPago:string,
    public statusTipo: string,
    public statusIngresso: string,
    public dataCompra: string,
    public sessaoId?: number | null,
    public usuarioId?: number | null,

){}
}

export interface IngressoFormProps{
    ingressoExistente?:Ingresso
}
