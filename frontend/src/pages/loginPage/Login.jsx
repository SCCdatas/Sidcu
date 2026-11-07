import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { admins } from "../../data/admins.js";
import "./Login.css";

const Login = () => {
    const [usuario, setUsuario] = useState("");
    const [contrasena, setContrasena] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const iniciarSesion = (event) => {
        event.preventDefault();
        setError("");

        const administradorValido = admins.find(
            (admin) =>
                admin.username === usuario.trim() &&
                admin.password === contrasena
        );

        if (!administradorValido) {
            setError("Usuario o contraseña incorrectos.");
            return;
        }

        localStorage.setItem("usuario", administradorValido.username);
        navigate("/home");
    };

    return (
        <main className="login-main">
            <div className="login-container">
                <div id="tituloLogin">
                    <h2>LOGIN</h2>
                </div>

                <form id="formularioLogin" onSubmit={iniciarSesion}>
                    <label htmlFor="usuarioLogin">USUARIO</label>
                    <input
                        id="usuarioLogin"
                        type="text"
                        placeholder="Ingresa tu usuario"
                        autoComplete="username"
                        value={usuario}
                        onChange={(event) => setUsuario(event.target.value)}
                        required
                    />

                    <label htmlFor="contrasenaLogin">CONTRASEÑA</label>
                    <input
                        id="contrasenaLogin"
                        type="password"
                        placeholder="Ingresa la contraseña"
                        autoComplete="current-password"
                        value={contrasena}
                        onChange={(event) => setContrasena(event.target.value)}
                        required
                    />

                    {error && <p role="alert">{error}</p>}

                    <button id="botonLogin" type="submit">
                        INGRESAR
                    </button>

                    <p>
                        ¿No tienes cuenta? <Link to="/registro">Regístrate aquí</Link>
                    </p>
                </form>
            </div>
        </main>
    );
};

export default Login;
