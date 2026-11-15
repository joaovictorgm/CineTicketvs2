import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../redux/slices/authSlice"
import carrinhoReducer from "./slices/carrinhoSlice";

export const store = configureStore({
    reducer:{
        auth: authReducer,
        carrinho: carrinhoReducer
    }
});

export type RootState = ReturnType<typeof store.getState >;
export type AppDispath = typeof store.dispatch;