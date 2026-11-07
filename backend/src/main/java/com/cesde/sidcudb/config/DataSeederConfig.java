package com.cesde.sidcudb.config;

import com.cesde.sidcudb.model.embeddable.InteresId;
import com.cesde.sidcudb.model.embeddable.InteresUniversidadId;
import com.cesde.sidcudb.model.embeddable.Ubicacion;
import com.cesde.sidcudb.model.embeddable.UniversidadCarreraId;
import com.cesde.sidcudb.model.entity.Carrera;
import com.cesde.sidcudb.model.entity.Estudiante;
import com.cesde.sidcudb.model.entity.Habilidad;
import com.cesde.sidcudb.model.entity.Interes;
import com.cesde.sidcudb.model.entity.InteresUniversidad;
import com.cesde.sidcudb.model.entity.PerfilEstudiante;
import com.cesde.sidcudb.model.entity.Universidad;
import com.cesde.sidcudb.model.entity.UniversidadCarrera;
import com.cesde.sidcudb.model.enums.NivelAcademico;
import com.cesde.sidcudb.repository.CarreraRepository;
import com.cesde.sidcudb.repository.EstudianteRepository;
import com.cesde.sidcudb.repository.HabilidadRepository;
import com.cesde.sidcudb.repository.InteresRepository;
import com.cesde.sidcudb.repository.InteresUniversidadRepository;
import com.cesde.sidcudb.repository.PerfilEstudianteRepository;
import com.cesde.sidcudb.repository.UniversidadCarreraRepository;
import com.cesde.sidcudb.repository.UniversidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * Carga datos de ejemplo al iniciar la aplicación, solo cuando la base de datos está vacía.
 * Se puede desactivar con {@code SEED_ENABLED=false}.
 */
@Configuration
public class DataSeederConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSeederConfig.class);

    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
    CommandLineRunner cargarDatosIniciales(HabilidadRepository habilidadRepository,
                                           CarreraRepository carreraRepository,
                                           UniversidadRepository universidadRepository,
                                           UniversidadCarreraRepository universidadCarreraRepository,
                                           EstudianteRepository estudianteRepository,
                                           PerfilEstudianteRepository perfilEstudianteRepository,
                                           InteresRepository interesRepository,
                                           InteresUniversidadRepository interesUniversidadRepository) {
        return args -> {
            if (carreraRepository.count() > 0 || universidadRepository.count() > 0) {
                log.info("La base de datos ya contiene información; se omite la carga inicial.");
                return;
            }

            // Habilidades
            Habilidad logica = habilidadRepository.save(habilidad("Pensamiento lógico", "Resolver problemas de forma estructurada"));
            Habilidad equipo = habilidadRepository.save(habilidad("Trabajo en equipo", "Colaborar para alcanzar objetivos comunes"));
            Habilidad comunicacion = habilidadRepository.save(habilidad("Comunicación efectiva", "Transmitir ideas con claridad"));
            Habilidad creatividad = habilidadRepository.save(habilidad("Creatividad", "Generar ideas y soluciones novedosas"));
            Habilidad datos = habilidadRepository.save(habilidad("Análisis de datos", "Interpretar información para decidir"));

            // Carreras
            Carrera sistemas = carreraRepository.save(carrera("Ingeniería de Sistemas", "Tecnología", NivelAcademico.PREGRADO, logica, datos, equipo));
            Carrera software = carreraRepository.save(carrera("Desarrollo de Software", "Tecnología", NivelAcademico.TECNOLOGICO, logica, equipo));
            Carrera medicina = carreraRepository.save(carrera("Medicina", "Salud", NivelAcademico.PREGRADO, comunicacion, equipo));
            Carrera diseno = carreraRepository.save(carrera("Diseño Gráfico", "Diseño", NivelAcademico.TECNICO, creatividad, comunicacion));
            Carrera administracion = carreraRepository.save(carrera("Administración de Empresas", "Negocios", NivelAcademico.PREGRADO, comunicacion, datos));
            Carrera cienciaDatos = carreraRepository.save(carrera("Maestría en Ciencia de Datos", "Tecnología", NivelAcademico.POSTGRADO, datos, logica));

            // Universidades
            Universidad udea = universidadRepository.save(universidad("Universidad de Antioquia", "Medellín", "PUBLICA", "Calle 67 # 53-108", "050010"));
            Universidad unal = universidadRepository.save(universidad("Universidad Nacional de Colombia - Sede Medellín", "Medellín", "PUBLICA", "Carrera 65 # 59A-110", "050034"));
            Universidad eafit = universidadRepository.save(universidad("Universidad EAFIT", "Medellín", "PRIVADA", "Carrera 49 # 7 Sur-50", "050022"));
            Universidad cesde = universidadRepository.save(universidad("CESDE", "Medellín", "PRIVADA", null, null));
            Universidad itm = universidadRepository.save(universidad("Instituto Tecnológico Metropolitano", "Medellín", "PUBLICA", null, null));

            // Oferta académica
            oferta(universidadCarreraRepository, udea, sistemas, medicina, administracion);
            oferta(universidadCarreraRepository, unal, sistemas, medicina, cienciaDatos);
            oferta(universidadCarreraRepository, eafit, sistemas, administracion, diseno, cienciaDatos);
            oferta(universidadCarreraRepository, cesde, software, diseno);
            oferta(universidadCarreraRepository, itm, software, administracion);

            // Estudiantes, perfiles e intereses
            Estudiante ana = estudianteRepository.save(estudiante("1000000001", "Ana Gómez", "Bello"));
            Estudiante luis = estudianteRepository.save(estudiante("1000000002", "Luis Restrepo", "Medellín"));
            estudianteRepository.save(estudiante("1000000003", "Camila Torres", "Envigado"));

            PerfilEstudiante perfil = new PerfilEstudiante();
            perfil.setEstudiante(ana);
            perfil.setBiografia("Apasionada por la tecnología y la resolución de problemas.");
            perfil.setSitioWeb("https://ejemplo.com/ana");
            perfilEstudianteRepository.save(perfil);

            interesRepository.save(new Interes(new InteresId(ana.getId(), sistemas.getId()), ana, sistemas));
            interesRepository.save(new Interes(new InteresId(ana.getId(), software.getId()), ana, software));
            interesRepository.save(new Interes(new InteresId(luis.getId(), diseno.getId()), luis, diseno));
            interesUniversidadRepository.save(new InteresUniversidad(
                    new InteresUniversidadId(ana.getId(), udea.getId()), ana, udea));

            log.info("Carga inicial completada: datos de ejemplo registrados.");
        };
    }

    private Habilidad habilidad(String nombre, String descripcion) {
        Habilidad habilidad = new Habilidad();
        habilidad.setNombre(nombre);
        habilidad.setDescripcion(descripcion);
        return habilidad;
    }

    private Carrera carrera(String nombre, String area, NivelAcademico nivel, Habilidad... habilidades) {
        Carrera carrera = new Carrera();
        carrera.setNombre(nombre);
        carrera.setArea(area);
        carrera.setNivel(nivel);
        Set<Habilidad> conjunto = new HashSet<>(Set.of(habilidades));
        carrera.setHabilidades(conjunto);
        return carrera;
    }

    private Universidad universidad(String nombre, String ciudad, String tipo, String direccion, String codigoPostal) {
        Universidad universidad = new Universidad();
        universidad.setNombre(nombre);
        universidad.setCiudad(ciudad);
        universidad.setTipo(tipo);
        if (direccion != null || codigoPostal != null) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setDireccion(direccion);
            ubicacion.setCodigoPostal(codigoPostal);
            universidad.setUbicacion(ubicacion);
        }
        return universidad;
    }

    private Estudiante estudiante(String documento, String nombre, String ciudad) {
        Estudiante estudiante = new Estudiante();
        estudiante.setDocumento(documento);
        estudiante.setNombre(nombre);
        estudiante.setCiudad(ciudad);
        return estudiante;
    }

    private void oferta(UniversidadCarreraRepository repository, Universidad universidad, Carrera... carreras) {
        for (Carrera carrera : carreras) {
            repository.save(new UniversidadCarrera(
                    new UniversidadCarreraId(universidad.getId(), carrera.getId()), universidad, carrera));
        }
    }
}
