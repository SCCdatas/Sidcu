import App from "../App";
import Home from "../pages/homePage/Home";
import Nosotros from "../pages/nosotrosPage/Nosotros";
import Colaboradores from "../pages/colaboradoresPage/Colaboradores"
import Carreras from "../pages/carrerasPage/Carreras";
import Comparador from "../pages/comparadorPage/Comparador";
import Test from "../pages/testPage/Test";
import Error from "../pages/errorPage/Error";
import Login from "../pages/loginPage/Login";
import Registro from "../pages/registroPage/Registro";
import InformacionCarrera from "../pages/informacionCarrerasPage/InformacionCarreras";
import TestIntereses from "../pages/testPage/TestIntereses";
import TestHabilidades from "../pages/testPage/TestHabilidades";
import TestPersonalidad from "../pages/testPage/TestPersonalidad";
import TestMotivaciones from "../pages/testPage/TestMotivaciones";
import TestAcademico from "../pages/testPage/TestAcademico.jsx";

export const routerApp = [
    {
        path: "/",
        element: <App />,
        children: [
            {
                path: "home",
                element: <Home />
            },
            {
                path: "nosotros",
                element: <Nosotros />
            },
            {
                path: "colaboradores",
                element: <Colaboradores />
            },
            {
                path: "carreras",
                element: <Carreras />
            },
            {
                path: "comparador",
                element: <Comparador />
            },
            {
                path: "test",
                element: <Test />
            },
            {
                path: "*",
                element: <Error />
            },
            {
                path: "login",
                element: <Login />
            },
            {
                path: "registro",
                element: <Registro />
            },
            {
                path: "informacion",
                element: <InformacionCarrera />
            },
            {
                path: "intereses",
                element: <TestIntereses />
            },
            {
                path: "habilidades",
                element: <TestHabilidades />
            },
            {
                path: "personalidad",
                element: <TestPersonalidad />
            },
            {
                path: "motivaciones",
                element: <TestMotivaciones />
            },
            {
                path: "academico",
                element: <TestAcademico />
            }
        ]
    }
]