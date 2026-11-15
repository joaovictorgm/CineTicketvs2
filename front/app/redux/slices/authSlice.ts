import { AuthState } from "@/app/(sistema)/types/auth";
import { Usuario } from "@/app/(sistema)/types/usuario"
import { PayloadAction, createSlice } from "@reduxjs/toolkit"

const initialState: AuthState = {
    usuario: null,
    token: ""
}

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setToken: (state, action: PayloadAction<string>) => {
        state.token = action.payload;
    },
    login: (state, action: PayloadAction<{token: string, usuario: Usuario}>) => {
        state.token = action.payload.token;
        state.usuario = action.payload.usuario;
    },
    logout: (state) => {
        state.token = "";
        state.usuario = null;
    },
  }
});

export const { setToken, login, logout } = authSlice.actions;
export default authSlice.reducer;