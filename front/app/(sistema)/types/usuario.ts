export class Usuario{
    constructor(
       public id: number| null,
       public nome:string,
       public telefone:string,
       public email: string,
       public senha: string,
       public status: string,


    ){}
}

export interface UsuarioFormProps{
    usuarioExistente?:Usuario
}