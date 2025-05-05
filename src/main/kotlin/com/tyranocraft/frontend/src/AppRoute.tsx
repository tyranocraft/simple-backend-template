import React from "react";
import {Route, Routes} from 'react-router-dom';
import Home from "./pages/home/Home";
import {VIEW_HOME} from "@/constants/ViewUrlPath";

export default function AppRoute() {
    return (
        <>
            <Routes>
                <Route path={VIEW_HOME} element={<Home/>} />
            </Routes>
        </>
    );
}