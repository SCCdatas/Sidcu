import "./Colaboradores.css"

const Colaboradores = ()=> {
    return (

        <main>
            <section class="colaboradores-intro">
                <h1>Universidades Colaboradoras de SIDCU</h1>
                <p>
                    Nos enorgullece contar con el apoyo y la colaboración de importantes instituciones educativas de Colombia. Juntos construimos mejores oportunidades académicas para los jóvenes.
                </p>
            </section>
            <section class="universidades-lista">
                <div class="universidad">
                    <img src="./img/andes.jpg" alt="Universidad de los Andes" />
                    <h2>Universidad de los Andes</h2>
                    <p>Bogotá, D.C.</p>
                    <a href="https://uniandes.edu.co/" target="_blank" class="ver-mas">Ver más</a>
                </div>
                <div class="universidad">
                    <img src="./img/javeriana.jpg" alt="Pontificia Universidad Javeriana"/>
                    <h2>Pontificia Universidad Javeriana</h2>
                    <p>Bogotá, D.C.</p>
                    <a href="https://www.javeriana.edu.co/" target="_blank" class="ver-mas">Ver más</a>
                </div>
                <div class="universidad">
                    <img src="./img/nacional.jpg" alt="Universidad Nacional de Colombia"/>
                    <h2>Universidad Nacional de Colombia</h2>
                    <p>Bogotá y sedes regionales</p>
                    <a href="https://unal.edu.co/" target="_blank" class="ver-mas">Ver más</a>
                </div>
                <div class="universidad">
                    <img src="./img/eafit.jpg" alt="Universidad EAFIT"/>
                    <h2>Universidad EAFIT</h2>
                    <p>Medellín, Antioquia</p>
                    <a href="https://www.eafit.edu.co/" target="_blank" class="ver-mas">Ver más</a>
                </div>
                <div class="universidad">
                    <img src="./img/uvalle.jpg" alt="Universidad del Valle"/>
                    <h2>Universidad del Valle</h2>
                    <p>Cali, Valle del Cauca</p>
                    <a href="https://www.univalle.edu.co/" target="_blank" class="ver-mas">Ver más</a>
                </div>
            </section>
        </main>
    );
}

export default Colaboradores;