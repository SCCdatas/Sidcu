import "./Nosotros.css";

const Nosotros = () => {
    return (
        <main className="nosotros-main">
            <section className="info">

                {/* PRIMERA TARJETA */}
                <div className="info-box">

                    <div className="info-text">
                        <h2>¿Quiénes somos?</h2>

                        <p className="P1">
                            En SIDCU (Simulador de Carreras Universitarias)
                            somos un equipo comprometido con el futuro
                            académico de los jóvenes. Nuestra misión es
                            ayudar a los estudiantes de secundaria a
                            descubrir su vocación mediante una experiencia
                            interactiva, moderna y personalizada.
                        </p>
                    </div>

                    <img src="img/biblioteca.jpg" alt="Biblioteca" />

                </div>

                {/* SEGUNDA TARJETA */}
                <div className="info-box">

                    <img src="/img/ia.jpg" alt="Inteligencia artificial" />

                    <div className="info-text">
                        <h2>¿Qué ofrecemos?</h2>

                        <p className="P2">
                            Explorar diferentes carreras universitarias
                            y conocer sus características principales.
                            Realizar tests de autoestima y habilidades,
                            para descubrir qué programas se ajustan mejor
                            a tus intereses. Simular la experiencia
                            universitaria.
                        </p>
                    </div>

                </div>

                {/* TERCERA TARJETA */}
                <div className="info-box">

                    <div className="info-text">
                        <h2>Mejores Oportunidades</h2>

                        <p className="P3">
                            Nuevas universidades aliadas que ofrecen
                            programas innovadores. Promociones académicas
                            y becas exclusivas. Noticias y actualizaciones
                            sobre carreras emergentes y tendencias laborales.
                        </p>
                    </div>

                    <img src="/img/pelaos.jpg" alt="Estudiantes" />

                </div>

            </section>
        </main>
    );
};

export default Nosotros;
