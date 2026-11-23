'use client'

import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@/app/redux/store";
import { removerItem, limparCarrinho } from "@/app/redux/slices/carrinhoSlice";
import { pagarIngresso } from "@/app/services/ingressoService";

export default function CarrinhoPage() {

const itens = useSelector((state: RootState) => state.carrinho.itens);
const dispatch = useDispatch();

const total = itens.reduce((soma, item) => soma + item.valorPago, 0);
const finalizarCompra = async () => {
let erros = 0;
for (const item of itens) {
    const ok = await pagarIngresso(item.ingressoId);
    if (ok) {
        dispatch(removerItem(item.ingressoId));   // sai do carrinho só o que foi pago
    } else {
        erros++;
    }
}
if (erros === 0) {
    alert("Compra finalizada!");
} else {
    alert(erros + " ingresso(s) não puderam ser pagos");
}
};

return (
    <div className="p-8">
        <h1 className="text-2xl font-bold text-blue-900 mb-6">Carrinho de Ingressos</h1>

        {itens.length === 0 ? (
            <p className="text-blue-400">Seu carrinho está vazio.</p>
        ) : (
            <>
                <div className="bg-white rounded-lg border border-blue-100 overflow-hidden">
                    <table className="w-full text-left">
                        <thead className="bg-blue-600 text-white">
                            <tr>
                                <th className="px-4 py-3">Ingresso</th>
                                <th className="px-4 py-3">Sessão</th>
                                <th className="px-4 py-3">Filme</th>
                                <th className="px-4 py-3">Assento</th>
                                <th className="px-4 py-3">Tipo</th>
                                <th className="px-4 py-3">Valor</th>
                                <th className="px-4 py-3">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {itens.map((item) => (
                                <tr key={item.ingressoId} className="border-t border-blue-100 text-blue-900">
                                    <td className="px-4 py-3">{item.ingressoId}</td>
                                    <td className="px-4 py-3">{item.sessao}</td>
                                    <td className="px-4 py-3">{item.filme}</td>
                                    <td className="px-4 py-3">{item.assento}</td>
                                    <td className="px-4 py-3">{item.statusTipo}</td>
                                    <td className="px-4 py-3">R$ {item.valorPago.toFixed(2)}</td>
                                    <td className="px-4 py-3">
                                        <button
                                            onClick={() => dispatch(removerItem(item.ingressoId))}
                                            className="text-red-600 hover:underline">Remover</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                <div className="flex items-center justify-between mt-6">
                    <span className="text-xl font-bold text-blue-900">Total: R$ {total.toFixed(2)}</span>
                    <div className="flex gap-3">
                        <button
                            onClick={() => dispatch(limparCarrinho())}
                            className="px-4 py-2 text-sm rounded text-blue-700 border border-blue-200 hover:bg-blue-50">Limpar</button>
                        <button
                            onClick={finalizarCompra}
                            className="bg-blue-600 text-white text-sm font-semibold px-4 py-2 rounded hover:bg-blue-700">Finalizar compra</button>
                    </div>
                </div>
            </>
        )}
    </div>
);
}