import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "./Home.css";
import Actualizaciones from "./Actualizaciones.jsx";

const Home = () => {
    const [usuario, setUsuario] = useState(() => localStorage.getItem("usuario"));

    const cerrarSesion = () => {
        localStorage.removeItem("usuario");
        setUsuario(null);
    };


    const navigate = useNavigate();
        const [busqueda, setBusqueda] = useState("");

        const handleBuscar = () => {
            if (busqueda.trim() === "") {
                return;
            }

            navigate(`/carreras?buscar=${encodeURIComponent(busqueda.trim())}`);
        };


    return (
        <main>
            <h1 id="tituloPrincipal">
                {usuario ? (
                    <>
                        ¡Bienvenido, {usuario}! <br />
                        Encuentra la mejor opción para <span>estudiar</span>
                    </>
                ) : (
                    <>
                        Encuentra la mejor opción para <span>estudiar</span>
                    </>
                )}
            </h1>
            <p id="subtituloPrincipal">
                Explora diferentes carreras universitarias, conoce las
                universidades y descubre las oportunidades que te acercan
                a tu futuro profesional.
            </p>

            
            <div className="buscador">
                <input
                    type="text"
                    placeholder="¿Qué carrera te gustaría estudiar?"
                    value={busqueda}
                    onChange={(e) => setBusqueda(e.target.value)}
                    onKeyDown={(e) => {
                        if (e.key === "Enter") handleBuscar();
                    }}
                />

                <button className="buscar" onClick={handleBuscar}>
                    Buscar
                </button>
            </div>


            <div className="registro">
                {usuario ? (
                    <button
                        className="registrarse"
                        type="button"
                        onClick={cerrarSesion}
                    >
                        Cerrar sesión
                    </button>
                ) : (
                    <>
                        <Link to="/login" className="iniciarsesion">
                            Iniciar sesión
                        </Link>
                        <Link to="/registro" className="registrarse">
                            Registrarse
                        </Link>
                    </>
                )}
            </div>

            <Actualizaciones />

            <section className="suscripcion">
                <h3>Sé el primero en enterarte</h3>

                <p>
                    ¿Deseas que te informemos por correo sobre oportunidades
                    de carreras o universidades que guardaste en la búsqueda?
                </p>

                <div className="suscripcion-form">
                    <input
                        type="email"
                        placeholder="Correo Electrónico"
                    />
                    <button className="suscribirse">Suscribirse</button>
                </div>
            </section>
        </main>
    );
};

export default Home;
