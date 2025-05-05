import { create } from 'zustand'

interface AuthState {
    isLoggedIn: boolean
    username: string | null
    login: (name: string) => void
    logout: () => void
}

export const useAuthStore = create<AuthState>((set) => ({
    isLoggedIn: false,
    username: null,
    login: (name: string) =>
        set(() => ({ isLoggedIn: true, username: name })),
    logout: () =>
        set(() => ({ isLoggedIn: false, username: null })),
}))
