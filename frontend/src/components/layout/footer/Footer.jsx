
import { Link } from 'react-router-dom';
import './Footer.css';

function Footer() {
    return (
        <footer className="footer">

            <div className="footer-container">

                {/* INFORMACIÓN DE SIDCU */}
                <div className="footer-section footer-about">
                    <h2 className="footer-logo">
                        SID<span>CU</span>
                    </h2>

                    <p>
                        Simulador de Carreras Universitarias.
                        Te ayudamos a descubrir tus intereses,
                        conocer tus habilidades y encontrar
                        el camino profesional ideal para ti.
                    </p>
                </div>

                {/* ENLACES RÁPIDOS */}
                <div className="footer-section">
                    <h3>Explora SIDCU</h3>

                    <ul>
                        <li><Link to="/home">Inicio</Link></li>
                        <li><Link to="/carreras">Carreras</Link></li>
                        <li><Link to="/comparador">Comparador</Link></li>
                        <li><Link to="/nosotros">Nosotros</Link></li>
                    </ul>
                </div>

                {/* CONTACTO */}
                <div className="footer-section">
                    <h3>Contáctanos</h3>

                    <ul className="footer-contact">

                        <li>
                            <span>WhatsApp 1:</span>
                            <a href="https://wa.me/573053595876" target="_blank" rel="noreferrer">
                                +57 305 359 5876
                            </a>
                        </li>

                        <li>
                            <span>WhatsApp 2:</span>
                            <a href="https://wa.me/57XXXXXXXXXX" target="_blank" rel="noreferrer">
                                +57 XXX XXX XXXX
                            </a>
                        </li>

                        <li>
                            <span>Correo:</span>
                            <a href="mailto:correo@ejemplo.com">
                                correo@ejemplo.com
                            </a>
                        </li>

                        <li>
                            <span>Instagram:</span>
                            <a href="https://www.instagram.com/tu_cuenta"
                                target="_blank"
                                rel="noreferrer">
                                @tu_cuenta
                            </a>
                        </li>

                    </ul>
                </div>

                {/* INFORMACIÓN LEGAL */}
                <div className="footer-section">
                    <h3>Información</h3>

                    <ul>
                        <li><Link to="/privacidad">Política de privacidad</Link></li>
                        <li><Link to="/terminos">Términos de uso</Link></li>
                        <li><Link to="/nosotros">Sobre el proyecto</Link></li>
                    </ul>
                </div>

            </div>

            {/* PARTE INFERIOR */}
            <div className="footer-bottom">

                <p>
                    © {new Date().getFullYear()} SIDCU - Simulador de Carreras Universitarias.
                    Todos los derechos reservados.
                </p>

                <p className="footer-slogan">
                    Construye tu futuro, descubre tu camino.
                </p>

            </div>

        </footer>
    );
}

export default Footer;
