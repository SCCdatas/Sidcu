import "./InformacionCarreras.css";
import { carreras } from "./CarrerasData";

const InformacionCarrera = () => {

    const parametros = new URLSearchParams(window.location.search);
    const codigo = parametros.get("carrera");

    const carrera = carreras[codigo];

    if (!carrera) {
        return (
            <main className="info-main">
                <h1>No se encontró la carrera</h1>
                <p>Selecciona una carrera para consultar su información.</p>
            </main>
        );
    }

    return (
        <main className="info-main">

            <div className="info-container">

                <img
                    src={carrera.imagen}
                    alt={carrera.nombre}
                />

                <div className="info-texto">

                    <h1>{carrera.nombre}</h1>

                    <span className="categoria">
                        {carrera.categoria}
                    </span>

                    <p>{carrera.descripcion}</p>

                    <div className="info-grid">

                        <div className="info-card">
                            <h3>Duración</h3>
                            <p>{carrera.duracion}</p>
                        </div>

                        <div className="info-card">
                            <h3>Costo aproximado</h3>
                            <p>{carrera.costo}</p>
                        </div>

                        <div className="info-card">
                            <h3>Salario promedio</h3>
                            <p>{carrera.salario}</p>
                        </div>

                        <div className="info-card">
                            <h3>Ocupabilidad</h3>
                            <p>{carrera.ocupabilidad}</p>
                        </div>

                    </div>

                    <section className="habilidades">
                        <h2>Habilidades necesarias</h2>

                        <div id="habilidadesLista">
                            {carrera.habilidades.map((habilidad, index) => (
                                <span className="habilidad" key={index}>
                                    {habilidad}
                                </span>
                            ))}
                        </div>
                    </section>

                    <section className="campo-laboral">
                        <h2>Campo laboral</h2>
                        <p>{carrera.campo}</p>
                    </section>

                    <section className="universidades">
                        <h2>Universidades donde puedes estudiar</h2>

                        <ul id="universidadesLista">
                            {carrera.universidades.map((universidad, index) => (
                                <li key={index}>{universidad}</li>
                            ))}
                        </ul>
                    </section>

                </div>
            </div>

        </main>
    );
};

export default InformacionCarrera;
