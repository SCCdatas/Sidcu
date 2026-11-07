import { Link } from "react-router-dom";
import "./Error.css";

const Error = () => {

    return (
        <main className="error-page">

            <h1>404</h1>

            <h2>Página no encontrada</h2>

            <p>
                La página que estás buscando no existe.
            </p>

            <Link to="/">
                Volver al inicio
            </Link>

        </main>
    );
};

export default Error;