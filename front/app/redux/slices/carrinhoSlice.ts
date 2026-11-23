import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { logout } from "./authSlice";

export interface ItemCarrinho {
ingressoId: number
sessao: number
filme: number
assento: string
valorPago: number
statusTipo: string
}

interface CarrinhoState {
itens: ItemCarrinho[]
}

const initialState: CarrinhoState = {
itens: []
}

const carrinhoSlice = createSlice({
name: 'carrinho',
initialState,
reducers: {
    adicionarItem: (state, action: PayloadAction<ItemCarrinho>) => {
        const jaExiste = state.itens.some(i => i.ingressoId === action.payload.ingressoId);
        if (!jaExiste) {
            state.itens.push(action.payload);
        }
    },
    removerItem: (state, action: PayloadAction<number>) => {
        state.itens = state.itens.filter(i => i.ingressoId !== action.payload);
    },
    limparCarrinho: (state) => {
        state.itens = [];
    },
},
extraReducers: (builder) => {
    builder.addCase(logout, (state) => {
        state.itens = [];
    });
},
});

export const { adicionarItem, removerItem, limparCarrinho } = carrinhoSlice.actions;
export default carrinhoSlice.reducer;