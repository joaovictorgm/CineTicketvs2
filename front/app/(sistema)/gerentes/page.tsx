import Link from "@/node_modules/next/link";


export default function Gerentes() {

    return (
        <div>
            <div>
                <h1>
                    Gestao de gerentes
                </h1>
                <Link href="/gerentes/novo"></Link>
            </div>

            <div>
                <div>
                    <table>
                        <thead>
                            <tr>
                                <th>Nome</th>
                            </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                JoãoVictor
                            </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            

           </div> )
}