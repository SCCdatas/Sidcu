import CuestionarioVocacional from "./CuestionarioVocacional";

const preguntas = [
    { categoria: "Realista", texto: "¿Te gusta construir, reparar o armar objetos?" },
    { categoria: "Realista", texto: "¿Disfrutas realizar actividades prácticas?" },
    { categoria: "Realista", texto: "¿Te interesa trabajar con herramientas o equipos?" },
    { categoria: "Realista", texto: "¿Te gustan las actividades al aire libre?" },

    { categoria: "Investigativa", texto: "¿Te gusta investigar por qué suceden las cosas?" },
    { categoria: "Investigativa", texto: "¿Disfrutas resolver problemas difíciles?" },
    { categoria: "Investigativa", texto: "¿Te interesan los experimentos científicos?" },
    { categoria: "Investigativa", texto: "¿Te gusta analizar información y encontrar respuestas?" },

    { categoria: "Artística", texto: "¿Disfrutas dibujar, diseñar o crear?" },
    { categoria: "Artística", texto: "¿Te gusta expresar tus ideas de manera creativa?" },
    { categoria: "Artística", texto: "¿Te interesan la música, el arte o la escritura?" },
    { categoria: "Artística", texto: "¿Te gusta crear cosas originales?" },

    { categoria: "Social", texto: "¿Te gusta ayudar a otras personas?" },
    { categoria: "Social", texto: "¿Disfrutas enseñar o explicar temas?" },
    { categoria: "Social", texto: "¿Te gusta escuchar y apoyar a los demás?" },
    { categoria: "Social", texto: "¿Prefieres trabajar en equipo?" },

    { categoria: "Emprendedora", texto: "¿Te gusta dirigir o liderar actividades?" },
    { categoria: "Emprendedora", texto: "¿Disfrutas presentar y defender tus ideas?" },
    { categoria: "Emprendedora", texto: "¿Te gustaría crear tu propio negocio?" },
    { categoria: "Emprendedora", texto: "¿Te gusta tomar decisiones y asumir responsabilidades?" },

    { categoria: "Convencional", texto: "¿Te gusta organizar documentos e información?" },
    { categoria: "Convencional", texto: "¿Disfrutas trabajar siguiendo instrucciones?" },
    { categoria: "Convencional", texto: "¿Te gusta revisar detalles para evitar errores?" },
    { categoria: "Convencional", texto: "¿Te interesa trabajar con números y registros?" }
];

const TestIntereses = () => {
    return (
        <CuestionarioVocacional
            titulo="Test de Intereses Vocacionales"
            descripcion="Descubre qué actividades y campos profesionales llaman más tu atención."
            preguntas={preguntas}
            clave="sidcu_test_intereses"
        />
    );
};

export default TestIntereses;