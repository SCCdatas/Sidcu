
import "./Registro.css";

const Registro = () => {
    return (
        <main className="login-main">
            <div className="login-container">

                <div id="tituloRegistro">
                    <h2>REGISTRO</h2>
                </div>

                <div id="formularioRegistro">

                    <label htmlFor="usuario">
                        USUARIO
                    </label>

                    <input
                        id="usuario"
                        type="text"
                        placeholder="Ingresa tu usuario"
                    />

                    <label htmlFor="contraseña">
                        CONTRASEÑA
                    </label>

                    <input
                        type="password"
                        name="contraseña"
                        id="contraseña"
                        placeholder="Ingresa la contraseña"
                    />

                    <button id="botonRegistro">
                        REGISTRARSE
                    </button>

                    <p>
                        ¿Ya tienes una cuenta?{" "}
                        <a href="/login">
                            Inicia aquí
                        </a>
                    </p>

                </div>
            </div>
        </main>
    );
};

export default Registro;
