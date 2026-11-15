import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { logout } from "./authSlice";

export interface ItemCarrinho {
    sessaoId: number
    filme: string
    data: string
    sala: string
    preco: number
    quantidade: number
    assentosDisponiveis: number
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
        adicionarItem: (state, action: PayloadAction<Omit<ItemCarrinho, 'quantidade'>>) => {
            const existente = state.itens.find(i => i.sessaoId === action.payload.sessaoId);
            if (existente) {
                if (existente.quantidade < existente.assentosDisponiveis) {
                    existente.quantidade += 1;
                }
            } else {
                state.itens.push({ ...action.payload, quantidade: 1 });
            }
        },
        alterarQuantidade: (state, action: PayloadAction<{ sessaoId: number, quantidade: number }>) => {
            const item = state.itens.find(i => i.sessaoId === action.payload.sessaoId);
            if (!item) return;
            if (action.payload.quantidade <= 0) {
                state.itens = state.itens.filter(i => i.sessaoId !== action.payload.sessaoId);
            } else {
                item.quantidade = Math.min(action.payload.quantidade, item.assentosDisponiveis);
            }
        },
        removerItem: (state, action: PayloadAction<number>) => {
            state.itens = state.itens.filter(i => i.sessaoId !== action.payload);
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

export const { adicionarItem, alterarQuantidade, removerItem, limparCarrinho } = carrinhoSlice.actions;
export default carrinhoSlice.reducer;